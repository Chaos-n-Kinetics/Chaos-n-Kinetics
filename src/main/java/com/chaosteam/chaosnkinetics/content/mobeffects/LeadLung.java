package com.chaosteam.chaosnkinetics.content.mobeffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class LeadLung extends MobEffect {
    protected LeadLung() {
        super(MobEffectCategory.HARMFUL, 0x1d3f96);
    }

    @Override
    public void onEffectAdded(@NotNull LivingEntity entity, int amplifier) {
    }
}
