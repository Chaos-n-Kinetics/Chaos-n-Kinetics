package com.chaosteam.chaosnkinetics.mixin;

import com.chaosteam.chaosnkinetics.registry.CKRecipeTypes;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MechanicalMixerBlockEntity.class)
public abstract class MechanicalMixerBlockEntityMixin {
    @Inject(method = "matchStaticFilters", at = @At("HEAD"), cancellable = true)
    private void onMatchStaticFilters(RecipeHolder<? extends Recipe<?>> recipe, CallbackInfoReturnable<Boolean> cir) {
        if (recipe.value().getType() == CKRecipeTypes.PASSIVE_MIXING_TYPE.get())
            cir.setReturnValue(true);
    }
}
