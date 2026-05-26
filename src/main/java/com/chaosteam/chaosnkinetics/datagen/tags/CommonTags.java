package com.chaosteam.chaosnkinetics.datagen.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CommonTags {
    public static class Items {
        public static final TagKey<Item> INGOTS = makeTag("c", "ingots");

        public static TagKey<Item> makeTag(String ns, String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(ns, name));
        }

        public static TagKey<Item> makeIngot(String ingot) {
            return makeTag("c", "ingots/"+ingot);
        }
    }
}
