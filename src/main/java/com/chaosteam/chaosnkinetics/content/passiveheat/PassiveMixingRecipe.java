package com.chaosteam.chaosnkinetics.content.passiveheat;

import com.chaosteam.chaosnkinetics.registry.CKRecipeTypes;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class PassiveMixingRecipe extends PassiveBasinRecipe {
    public static final IRecipeTypeInfo TYPE_INFO = new IRecipeTypeInfo() {
        @Override
        public ResourceLocation getId() {
            return CKRecipeTypes.PASSIVE_MIXING.getId();
        }

        @Override
        public <T extends RecipeSerializer<?>> T getSerializer() {
            return (T) CKRecipeTypes.PASSIVE_MIXING.get();
        }

        @Override
        public <V extends RecipeInput, R extends Recipe<V>> RecipeType<R> getType() {
            return (RecipeType<R>) CKRecipeTypes.PASSIVE_MIXING_TYPE.get();
        }
    };

    public PassiveMixingRecipe(ProcessingRecipeParams params) {
        super(TYPE_INFO, params);
    }
}
