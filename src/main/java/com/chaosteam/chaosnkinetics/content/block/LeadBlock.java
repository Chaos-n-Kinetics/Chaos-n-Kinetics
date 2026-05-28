package com.chaosteam.chaosnkinetics.content.block;

import com.chaosteam.chaosnkinetics.content.mobeffects.CKMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class LeadBlock extends Block {
    public LeadBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack tool) {
        MobEffectInstance current_effect = player.getEffect(CKMobEffects.LEAD_LUNG);
        byte amplifier;
        if (current_effect == null) {
            amplifier = 0;
        } else {
            amplifier = (byte) (current_effect.getAmplifier() + 1);
        }
        if (amplifier > 5) amplifier = 5;
        player.addEffect(new MobEffectInstance(CKMobEffects.LEAD_LUNG, 200 * (amplifier + 1), amplifier));
        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 160, amplifier));
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    @Override
    public void stepOn(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, @NotNull Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(CKMobEffects.LEAD_LUNG, 400, 0));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 160, 0));
        }
    }

    @Override
    protected void attack(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player) {
        player.addEffect(new MobEffectInstance(CKMobEffects.LEAD_LUNG, 400, 0));
        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 160, 0));
    }
}
