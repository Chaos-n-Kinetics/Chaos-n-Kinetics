package com.chaosteam.chaosnkinetics.content.passiveheat;

import com.chaosteam.chaosnkinetics.ChaosKinetics;
import com.chaosteam.chaosnkinetics.registry.CKRecipeTypes;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.recipe.DummyCraftingContainer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.createmod.catnip.data.Iterate;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PassiveBasinRecipe extends StandardProcessingRecipe<RecipeInput> {
    public static final IRecipeTypeInfo TYPE_INFO = new IRecipeTypeInfo() {
        @Override
        public ResourceLocation getId() {
            return CKRecipeTypes.PASSIVE_BASIN.getId();
        }

        @Override
        public <T extends RecipeSerializer<?>> T getSerializer() {
            return (T) CKRecipeTypes.PASSIVE_BASIN.get();
        }

        @Override
        public <V extends RecipeInput, R extends Recipe<V>> RecipeType<R> getType() {
            return (RecipeType<R>) CKRecipeTypes.PASSIVE_BASIN_TYPE.get();
        }
    };

    public static boolean match(BasinBlockEntity basin, Recipe<?> recipe) {
        FilteringBehaviour filter = basin.getFilter();
        if (filter == null)
            return false;

        boolean filterTest = filter.test(recipe.getResultItem(basin.getLevel()
                .registryAccess()));
        if (recipe instanceof com.simibubi.create.content.processing.basin.BasinRecipe basinRecipe) {
            if (basinRecipe.getRollableResults()
                    .isEmpty()
                    && !basinRecipe.getFluidResults()
                    .isEmpty())
                filterTest = filter.test(basinRecipe.getFluidResults()
                        .get(0));
        }

        if (!filterTest)
            return false;

        return apply(basin, recipe, true);
    }

    public static boolean apply(BasinBlockEntity basin, Recipe<?> recipe) {
        return apply(basin, recipe, false);
    }

    private static boolean apply(BasinBlockEntity basin, Recipe<?> recipe, boolean test) {
        boolean isBasinRecipe = recipe instanceof PassiveBasinRecipe;
        IItemHandler availableItems = basin.getLevel().getCapability(Capabilities.ItemHandler.BLOCK, basin.getBlockPos(), null);
        IFluidHandler availableFluids = basin.getLevel().getCapability(Capabilities.FluidHandler.BLOCK, basin.getBlockPos(), null);

        if (availableItems == null || availableFluids == null)
            return false;

        BlazeBurnerBlock.HeatLevel heat = BasinBlockEntity.getHeatLevelOf(basin.getLevel().getBlockState(basin.getBlockPos().below(1)));
        if (heat != BlazeBurnerBlock.HeatLevel.SMOULDERING)
            return false;

        List<ItemStack> recipeOutputItems = new ArrayList<>();
        List<FluidStack> recipeOutputFluids = new ArrayList<>();

        List<Ingredient> ingredients = new LinkedList<>(recipe.getIngredients());
        List<SizedFluidIngredient> fluidIngredients =
                isBasinRecipe ? ((PassiveBasinRecipe) recipe).getFluidIngredients() : Collections.emptyList();

        for (boolean simulate : Iterate.trueAndFalse) {

            if (!simulate && test)
                return true;

            int[] extractedItemsFromSlot = new int[availableItems.getSlots()];
            int[] extractedFluidsFromTank = new int[availableFluids.getTanks()];

            Ingredients:
            for (Ingredient ingredient : ingredients) {
                for (int slot = 0; slot < availableItems.getSlots(); slot++) {
                    if (simulate && availableItems.getStackInSlot(slot)
                            .getCount() <= extractedItemsFromSlot[slot])
                        continue;
                    ItemStack extracted = availableItems.extractItem(slot, 1, true);
                    if (!ingredient.test(extracted))
                        continue;
                    if (!simulate)
                        availableItems.extractItem(slot, 1, false);
                    extractedItemsFromSlot[slot]++;
                    continue Ingredients;
                }

                // something wasn't found
                return false;
            }

            boolean fluidsAffected = false;
            FluidIngredients:
            for (SizedFluidIngredient fluidIngredient : fluidIngredients) {
                int amountRequired = fluidIngredient.amount();

                for (int tank = 0; tank < availableFluids.getTanks(); tank++) {
                    FluidStack fluidStack = availableFluids.getFluidInTank(tank);
                    if (simulate && fluidStack.getAmount() <= extractedFluidsFromTank[tank])
                        continue;
                    if (!fluidIngredient.test(fluidStack))
                        continue;
                    int drainedAmount = Math.min(amountRequired, fluidStack.getAmount());
                    if (!simulate) {
                        fluidStack.shrink(drainedAmount);
                        fluidsAffected = true;
                    }
                    amountRequired -= drainedAmount;
                    if (amountRequired != 0)
                        continue;
                    extractedFluidsFromTank[tank] += drainedAmount;
                    continue FluidIngredients;
                }

                // something wasn't found
                return false;
            }

            if (fluidsAffected) {
                basin.getBehaviour(SmartFluidTankBehaviour.INPUT)
                        .forEach(SmartFluidTankBehaviour.TankSegment::onFluidStackChanged);
                basin.getBehaviour(SmartFluidTankBehaviour.OUTPUT)
                        .forEach(SmartFluidTankBehaviour.TankSegment::onFluidStackChanged);
            }

            if (simulate) {
                CraftingInput remainderInput = new DummyCraftingContainer(availableItems, extractedItemsFromSlot)
                        .asCraftInput();

                if (recipe instanceof PassiveBasinRecipe basinRecipe) {
                    recipeOutputItems.addAll(basinRecipe.rollResults(basin.getLevel().random));

                    for (FluidStack fluidStack : basinRecipe.getFluidResults())
                        if (!fluidStack.isEmpty())
                            recipeOutputFluids.add(fluidStack);
                    for (ItemStack stack : basinRecipe.getRemainingItems(remainderInput))
                        if (!stack.isEmpty())
                            recipeOutputItems.add(stack);

                } else {
                    recipeOutputItems.add(recipe.getResultItem(basin.getLevel()
                            .registryAccess()));

                    if (recipe instanceof CraftingRecipe craftingRecipe) {
                        for (ItemStack stack : craftingRecipe.getRemainingItems(remainderInput))
                            if (!stack.isEmpty())
                                recipeOutputItems.add(stack);
                    }
                }
            }

            if (!basin.acceptOutputs(recipeOutputItems, recipeOutputFluids, simulate))
                return false;
        }

        return true;
    }

    protected PassiveBasinRecipe(IRecipeTypeInfo type, ProcessingRecipeParams params) {
        super(type, params);
    }

    public PassiveBasinRecipe(ProcessingRecipeParams params) {
        this(TYPE_INFO, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 64;
    }

    @Override
    protected int getMaxOutputCount() {
        return 4;
    }

    @Override
    protected int getMaxFluidInputCount() {
        return 2;
    }

    @Override
    protected int getMaxFluidOutputCount() {
        return 2;
    }

    @Override
    protected boolean canRequireHeat() {
        return true;
    }

    @Override
    protected boolean canSpecifyDuration() {
        return true;
    }

    @Override
    public boolean matches(RecipeInput input, @NotNull Level worldIn) {
            return false;
        }
}
