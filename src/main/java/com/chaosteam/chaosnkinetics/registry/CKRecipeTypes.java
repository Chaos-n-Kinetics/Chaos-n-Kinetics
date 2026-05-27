package com.chaosteam.chaosnkinetics.registry;

import com.chaosteam.chaosnkinetics.ChaosKinetics;
import com.chaosteam.chaosnkinetics.content.passiveheat.PassiveBasinRecipe;
import com.chaosteam.chaosnkinetics.content.passiveheat.PassiveMixingRecipe;
import com.chaosteam.chaosnkinetics.content.passiveheat.PassivePressingRecipe;
import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class CKRecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, ChaosKinetics.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, ChaosKinetics.MODID);
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.CONDITION_SERIALIZERS, ChaosKinetics.MODID);

    private static <T extends Recipe<?>> Supplier<RecipeType<T>> registerRecipeType(String id) {
        return RECIPE_TYPES.register(id, () -> new RecipeType<T>() {
            public String toString() { return id; }
        });
    }

    public static final Supplier<RecipeType<PassiveMixingRecipe>> PASSIVE_MIXING_TYPE = registerRecipeType("passive_mixing");
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PassiveMixingRecipe>> PASSIVE_MIXING = SERIALIZERS.register("passive_mixing", () -> new StandardProcessingRecipe.Serializer<>(PassiveMixingRecipe::new));

    public static final Supplier<RecipeType<PassivePressingRecipe>> PASSIVE_PRESSING_TYPE = registerRecipeType("passive_pressing");
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PassivePressingRecipe>> PASSIVE_PRESSING = SERIALIZERS.register("passive_pressing", () -> new StandardProcessingRecipe.Serializer<>(PassivePressingRecipe::new));

    public static final Supplier<RecipeType<PassiveBasinRecipe>> PASSIVE_BASIN_TYPE = registerRecipeType("passive_basin");
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PassiveBasinRecipe>> PASSIVE_BASIN = SERIALIZERS.register("passive_basin", () -> new StandardProcessingRecipe.Serializer<>(PassiveBasinRecipe::new));

    public static void register(IEventBus event) {
        SERIALIZERS.register(event);
        RECIPE_TYPES.register(event);
        CONDITION_CODECS.register(event);
    }
}
