package com.chaosteam.chaosnkinetics;

import com.chaosteam.chaosnkinetics.datagen.CKDatagen;
import com.chaosteam.chaosnkinetics.registry.CKBlockEntityTypes;
import com.chaosteam.chaosnkinetics.registry.CKBlocks;
import com.chaosteam.chaosnkinetics.registry.CKCreativeModeTabs;
import com.chaosteam.chaosnkinetics.registry.CKFluids;
import com.chaosteam.chaosnkinetics.registry.CKItems;
import com.chaosteam.chaosnkinetics.registry.CKMobEffects;
import com.chaosteam.chaosnkinetics.registry.CKPartialModels;
import com.chaosteam.chaosnkinetics.registry.CKRecipeTypes;
import com.mojang.logging.LogUtils;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(ChaosKinetics.MODID)
public class ChaosKinetics {
    public static final String MODID = "chaosnkinetics";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ChaosKinetics.MODID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item))));

    public ChaosKinetics(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(EventPriority.HIGHEST, CKDatagen::gatherDataHighPriority);

        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, CKConfig.SPEC);

        REGISTRATE.registerEventListeners(modEventBus);
        registerContent(modEventBus);
    }

    private void registerContent(IEventBus modEventBus) {
        CKFluids.register();
        CKMobEffects.register();
        CKBlocks.register();
        CKItems.register();
        CKBlockEntityTypes.register();
        CKRecipeTypes.register(modEventBus);
        CKPartialModels.register();
        CKCreativeModeTabs.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }

    public static ResourceLocation asResource(String name) {
        return ResourceLocation.fromNamespaceAndPath(ChaosKinetics.MODID, name);
    }
}
