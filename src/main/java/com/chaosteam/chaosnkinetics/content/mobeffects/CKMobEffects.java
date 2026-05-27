package com.chaosteam.chaosnkinetics.content.mobeffects;

import com.chaosteam.chaosnkinetics.ChaosKinetics;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class CKMobEffects {

    public static final RegistryEntry<MobEffect, MobEffect> LEAD_LUNG = ChaosKinetics.REGISTRATE.generic("lead_lung", Registries.MOB_EFFECT, () -> new LeadLung().addAttributeModifier(Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath(ChaosKinetics.MODID, "effect.lead_lung"), -4, AttributeModifier.Operation.ADD_VALUE)
    ).register();

    //addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.withDefaultNamespace("effect.speed"), 0.20000000298023224, Operation.ADD_MULTIPLIED_TOTAL));

    public static void register() {
    }
}
