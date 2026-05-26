package com.chaosteam.chaosnkinetics.compat.jei;

import com.chaosteam.chaosnkinetics.ChaosKinetics;
import com.chaosteam.chaosnkinetics.content.passiveheat.PassiveBasinRecipe;
import com.chaosteam.chaosnkinetics.content.passiveheat.PassiveMixingRecipe;
import com.chaosteam.chaosnkinetics.registry.CKRecipeTypes;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class ChaosKineticsJEI implements IModPlugin {
    private static final ResourceLocation ID = ChaosKinetics.asResource("jei_plugin");

    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return ID;
    }

    public IIngredientManager ingredientManager;
    static final List<CreateRecipeCategory<?>> ALL = new ArrayList<>();

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        ALL.clear();

        builder(PassiveBasinRecipe.class)
                .addTypedRecipes(CKRecipeTypes.PASSIVE_BASIN_TYPE)
                .catalyst(AllBlocks.BASIN::get)
                .itemIcon(AllBlocks.BASIN.get())
                .emptyBackground(177, 53)
                .build("passive_basin", PassiveBasinCategory::new);

        builder(PassiveBasinRecipe.class)
                .addTypedRecipes(CKRecipeTypes.PASSIVE_MIXING_TYPE)
                .catalyst(AllBlocks.MECHANICAL_MIXER::get)
                .itemIcon(AllBlocks.MECHANICAL_MIXER.get())
                .emptyBackground(177, 53)
                .build("passive_mixing", PassiveMixingCategory::new);

        ALL.forEach(registration::addRecipeCategories);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ingredientManager = registration.getIngredientManager();
        ALL.forEach(c -> c.registerRecipes(registration));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        ALL.forEach(c -> c.registerCatalysts(registration));
    }

    private <T extends Recipe<?>> CategoryBuilder<T> builder(Class<? extends T> recipeClass) {
        return new CategoryBuilder<>(recipeClass);
    }

    private static class CategoryBuilder<T extends Recipe<?>> extends CreateRecipeCategory.Builder<T> {
        public CategoryBuilder(Class<? extends T> recipeClass) {
            super(recipeClass);
        }

        @Override
        public CreateRecipeCategory<T> build(ResourceLocation id, CreateRecipeCategory.Factory<T> factory) {
            CreateRecipeCategory<T> category = super.build(id, factory);
            ALL.add(category);
            return category;
        }

        @Override
        public CreateRecipeCategory<T> build(String name, CreateRecipeCategory.Factory<T> factory) {
            return build(ChaosKinetics.asResource(name), factory);
        }
    }
}
