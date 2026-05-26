package com.chaosteam.chaosnkinetics.datagen.tags;

import com.chaosteam.chaosnkinetics.ChaosKinetics;
import com.chaosteam.chaosnkinetics.content.item.CKItems;
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

        prov.tag(CommonTags.Items.INGOTS)
                .add(CKItems.CRUDE_BRASS.get())
                .add(CKItems.REFINED_BRASS.get())
                .add(CKItems.LEAD.get());
        prov.tag(CommonTags.Items.RAW_MATERIALS)
                .add(CKItems.UNREFINED_LEAD.get());
    }
}
