package com.chaosteam.chaosnkinetics.registry;

import com.chaosteam.chaosnkinetics.ChaosKinetics;
import com.chaosteam.chaosnkinetics.content.kinetics.torsion_motor.TorsionMotorBlockEntity;
import com.chaosteam.chaosnkinetics.content.kinetics.torsion_motor.TorsionMotorRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class CKBlockEntityTypes {
    public static final BlockEntityEntry<TorsionMotorBlockEntity> TORSION_MOTOR = ChaosKinetics.REGISTRATE
            .blockEntity("torsion_motor", TorsionMotorBlockEntity::new)
            .validBlocks(CKBlocks.TORSION_MOTOR)
            .renderer(() -> TorsionMotorRenderer::new)
            .register();

    public static void register() {
    }
}
