package com.chaosteam.chaosnkinetics.registry;

import com.chaosteam.chaosnkinetics.ChaosKinetics;
import com.chaosteam.chaosnkinetics.content.kinetics.torsion_motor.TorsionMotorBlock;
import com.chaosteam.chaosnkinetics.content.kinetics.torsion_motor.TorsionMotorBlockEntity;
import com.chaosteam.chaosnkinetics.content.kinetics.torsion_motor.TorsionMotorGenerator;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class CKBlocks {
    static {
        ChaosKinetics.REGISTRATE.setCreativeTab(CKCreativeModeTabs.MAIN_TAB);
    }

    public static final BlockEntry<TorsionMotorBlock> TORSION_MOTOR = ChaosKinetics.REGISTRATE
            .block("torsion_motor", TorsionMotorBlock::new)
            .lang("Torsion Motor")
            .initialProperties(SharedProperties::stone)
            .properties(properties -> properties.noOcclusion().mapColor(MapColor.PODZOL))
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(pickaxeOnly())
            .blockstate(new TorsionMotorGenerator()::generate)
            .onRegister(block -> BlockStressValues.CAPACITIES.register(block, () -> TorsionMotorBlockEntity.STRESS_CAPACITY))
            .onRegister(BlockStressValues.setGeneratorSpeed((int) TorsionMotorBlockEntity.MAX_OUTPUT_RPM, true))
            .item()
            .model((context, provider) -> provider.withExistingParent(context.getName(), provider.modLoc("block/torsion_motor")))
            .build()
            .register();

    public static void register() {
    }
}
