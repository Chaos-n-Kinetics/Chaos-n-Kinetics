package com.chaosteam.chaosnkinetics.content.block;

import com.chaosteam.chaosnkinetics.content.mobeffects.CKMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
        player.addEffect(new MobEffectInstance(CKMobEffects.LEAD_LUNG, 200, 2)); //todo: actually spawn an areaeffectcloud here, instead of directly adding the effect
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }
}
