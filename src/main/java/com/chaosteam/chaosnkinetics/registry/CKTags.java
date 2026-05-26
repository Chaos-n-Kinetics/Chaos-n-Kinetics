package com.chaosteam.chaosnkinetics.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class CKTags {
    public static class Items {
        public static final TagKey<Item> INGOTS = commonTag("ingots");

        public static TagKey<Item> commonTag(String name) {
            return tag("c", name);
        }

        public static TagKey<Item> ingot(String ingot) {
            return commonTag("ingots/" + ingot);
        }

        public static TagKey<Item> tag(String namespace, String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(namespace, name));
        }
    }

    public static class Blocks {
        public static TagKey<Block> commonTag(String name) {
            return tag("c", name);
        }

        public static TagKey<Block> tag(String namespace, String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(namespace, name));
        }
    }

    public static class Fluids {
        public static TagKey<Fluid> commonTag(String name) {
            return tag("c", name);
        }

        public static TagKey<Fluid> tag(String namespace, String name) {
            return FluidTags.create(ResourceLocation.fromNamespaceAndPath(namespace, name));
        }
    }
}
