package com.chaosteam.chaosnkinetics.content.mobeffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class LeadPoisoning extends MobEffect {
    protected LeadPoisoning() {
        super(MobEffectCategory.HARMFUL, 0x0E0516);
    }

    @Override
    public void onEffectAdded(@NotNull LivingEntity entity, int amplifier) {
        entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, amplifier));
    }
}
