package com.chaosteam.chaosnkinetics.datagen.tags;

import com.chaosteam.chaosnkinetics.ChaosKinetics;
import com.chaosteam.chaosnkinetics.registry.CKItems;
import com.chaosteam.chaosnkinetics.registry.CKTags;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ItemTagProvider {
    public static void addGen() {
        ChaosKinetics.REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, ItemTagProvider::genItemTags);
        ChaosKinetics.REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, ItemTagProvider::genBlockTags);
    }

    private static void genBlockTags(RegistrateTagsProvider<Block> provIn) {

    }

    private static void genItemTags(RegistrateTagsProvider<Item> provIn) {
        TagGen.CreateTagsProvider<Item> prov = new TagGen.CreateTagsProvider<>(provIn, Item::builtInRegistryHolder);

        prov.tag(CKTags.Items.INGOTS)
                .add(CKItems.CRUDE_BRASS.get()).add(CKItems.REFINED_BRASS.get())
                .add(CKItems.LEAD.get());
        prov.tag(CKTags.Items.ingot("refined_brass"))
                .add(CKItems.REFINED_BRASS.get());
        prov.tag(CKTags.Items.ingot("crude_brass"))
                .add(CKItems.CRUDE_BRASS.get());
        prov.tag(CKTags.Items.ingot("lead"))
                .add(CKItems.LEAD.get());

    }
}
