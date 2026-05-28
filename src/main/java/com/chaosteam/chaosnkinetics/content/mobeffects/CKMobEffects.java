package com.chaosteam.chaosnkinetics.content.mobeffects;

import com.chaosteam.chaosnkinetics.ChaosKinetics;
import com.chaosteam.chaosnkinetics.content.item.CKItems;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

public class CKMobEffects {

    public static final RegistryEntry<MobEffect, MobEffect> LEAD_POISONING = ChaosKinetics.REGISTRATE.generic("lead_poisoning", Registries.MOB_EFFECT, () -> new LeadPoisoning().addAttributeModifier(Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath(ChaosKinetics.MODID, "effect.lead_poisoning"), -4, AttributeModifier.Operation.ADD_VALUE)
    ).register();

    //registering potions
    public static  final RegistryEntry<Potion, Potion> LEAD_POTION = ChaosKinetics.REGISTRATE.generic("lead_poisoning", Registries.POTION, () -> new Potion("lead_potion", new MobEffectInstance(LEAD_POISONING, 3600))).register();

    public static void register() {
    }

    public static void registerPotions(final RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();
        builder.addMix(Potions.AWKWARD, CKItems.LEAD.get(), LEAD_POTION);
    }
}
