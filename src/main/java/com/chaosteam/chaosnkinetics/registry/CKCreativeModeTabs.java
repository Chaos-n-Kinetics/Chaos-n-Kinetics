package com.chaosteam.chaosnkinetics.registry;

import com.chaosteam.chaosnkinetics.ChaosKinetics;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CKCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB,
            ChaosKinetics.MODID
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register(
            ChaosKinetics.MODID,
            () -> CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(CKItems.REFINED_BRASS.get()::getDefaultInstance)
                    .title(Component.translatable("itemGroup.chaosnkinetics"))
                    .displayItems((parameters, output) -> ChaosKinetics.REGISTRATE
                            .getAll(Registries.ITEM)
                            .forEach(item -> output.accept(item.get())))
                    .build()
    );

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
