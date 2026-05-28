package com.chaosteam.chaosnkinetics.registry;

import com.chaosteam.chaosnkinetics.ChaosKinetics;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

public class CKItems {
    static {
        ChaosKinetics.REGISTRATE.setCreativeTab(CKCreativeModeTabs.MAIN_TAB);
    }

    public static final ItemEntry<Item> CRUDE_BRASS = ChaosKinetics.REGISTRATE
            .item("crude_brass", Item::new)
            .register();

    public static final ItemEntry<Item> REFINED_BRASS = ChaosKinetics.REGISTRATE
            .item("refined_brass", Item::new)
            .register();

    public static final ItemEntry<Item> LEAD = ChaosKinetics.REGISTRATE
            .item("lead", Item::new)
            .register();

    public static final ItemEntry<Item> COAL_COKE = ChaosKinetics.REGISTRATE
            .item("coal_coke", Item::new)
            .register();

    public static final ItemEntry<Item> UNREFINED_LEAD = ChaosKinetics.REGISTRATE
            .item("unrefined_lead", Item::new)
            .register();

    public static final ItemEntry<Item> BIOCHAR = ChaosKinetics.REGISTRATE
            .item("biochar", Item::new)
            .register();

    public static final ItemEntry<Item> SPRING = ChaosKinetics.REGISTRATE
            .item("spring", Item::new)
            .register();

    public static final ItemEntry<Item> ANDSITE_ALLOY_SHEET = ChaosKinetics.REGISTRATE
            .item("andesite_alloy_sheet", Item::new)
            .register();

    public static void register() {
    }
}
