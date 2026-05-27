package com.chaosteam.chaosnkinetics.mixin;

import com.chaosteam.chaosnkinetics.content.passiveheat.PassiveBasinRecipe;
import com.simibubi.create.foundation.advancement.CreateAdvancement;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinOperatingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(BasinOperatingBlockEntity.class)
public abstract class BasinOperatingBlockEntityMixin {

    @Shadow
    protected abstract Optional<BasinBlockEntity> getBasin();

    @Shadow
    protected Recipe<?> currentRecipe;

    @Shadow
    protected abstract Optional<CreateAdvancement> getProcessedRecipeTrigger();

    @Shadow
    public abstract boolean continueWithPreviousRecipe();

    @Shadow
    protected abstract <I extends RecipeInput> boolean matchBasinRecipe(Recipe<I> recipe);

    @Inject(method = "applyBasinRecipe", at = @At("HEAD"), cancellable = true)
    private void onApplyBasinRecipe(CallbackInfo ci) {
        if (!(currentRecipe instanceof PassiveBasinRecipe passiveRecipe))
            return;

        ci.cancel();

        Optional<BasinBlockEntity> optionalBasin = getBasin();
        if (optionalBasin.isEmpty())
            return;

        BasinBlockEntity basin = optionalBasin.get();
        boolean wasEmpty = basin.canContinueProcessing();
        if (!PassiveBasinRecipe.apply(basin, passiveRecipe))
            return;

        BasinOperatingBlockEntity self = (BasinOperatingBlockEntity) (Object) this;
        getProcessedRecipeTrigger().ifPresent(self::award);
        basin.inputTank.sendDataImmediately();

        if (wasEmpty && matchBasinRecipe(currentRecipe)) {
            continueWithPreviousRecipe();
            self.sendData();
        }

        basin.notifyChangeOfContents();
    }

    @Inject(method = "matchBasinRecipe", at = @At("HEAD"), cancellable = true)
    private <I extends RecipeInput> void onMatchBasinRecipe(Recipe<I> recipe, CallbackInfoReturnable<Boolean> cir) {
        if (recipe instanceof PassiveBasinRecipe passiveRecipe) {
            Optional<BasinBlockEntity> basin = getBasin();
            if (basin.isPresent()) {
                cir.setReturnValue(passiveRecipe.match(basin.get(), recipe));
            } else {
                cir.setReturnValue(false);
            }
        }
    }

}
