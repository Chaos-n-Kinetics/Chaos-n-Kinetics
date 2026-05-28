package com.chaosteam.chaosnkinetics.content.block;

import com.chaosteam.chaosnkinetics.ChaosKinetics;
import com.chaosteam.chaosnkinetics.content.item.CKItems;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.materials.ExperienceBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.Tags;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class CKBlocks {
    static {
        ChaosKinetics.REGISTRATE.setCreativeTab(ChaosKinetics.MAIN_TAB);
    }

    public static final BlockEntry<LeadBlock> UNREFINED_LEAD_BLOCK = ChaosKinetics.REGISTRATE
            .block("unrefined_lead_block", LeadBlock::new)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().strength(5F, 6.0F))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .tag(BlockTags.NEEDS_IRON_TOOL)
            .loot((lt, b) -> {
                lt.add(b, lt.applyExplosionDecay(b,lt.createSingleItemTableWithSilkTouch(b, CKItems.UNREFINED_LEAD, ConstantValue.exactly(4))));
            })
            .simpleItem()
            .register();

    public static final BlockEntry<LeadBlock> LEAD_ORE = ChaosKinetics.REGISTRATE
            .block("lead_ore", LeadBlock::new)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().strength(4.5F, 3.0F))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .tag(BlockTags.NEEDS_IRON_TOOL)
            .tag(Tags.Blocks.ORES)
            .loot((lt, b) ->  {
                HolderLookup.RegistryLookup<Enchantment> enchantmentRegistryLookup = lt.getRegistries().lookupOrThrow(Registries.ENCHANTMENT);
                lt.add(b,
                lt.applyExplosionDecay(b, lt.createSingleItemTableWithSilkTouch(b, CKItems.UNREFINED_LEAD, UniformGenerator.between(1, 2))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantmentRegistryLookup.getOrThrow(Enchantments.FORTUNE)))
                ));
            })
            .simpleItem()
            .register();

    public static void register() {
    }
}
