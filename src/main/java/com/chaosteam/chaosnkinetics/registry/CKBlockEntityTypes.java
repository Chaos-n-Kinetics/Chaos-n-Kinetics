package com.chaosteam.chaosnkinetics.registry;

import com.chaosteam.chaosnkinetics.ChaosKinetics;
import com.chaosteam.chaosnkinetics.content.kinetics.torsion_motor.TorsionMotorBlockEntity;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class CKBlockEntityTypes {
    public static final BlockEntityEntry<TorsionMotorBlockEntity> TORSION_MOTOR = ChaosKinetics.REGISTRATE
            .blockEntity("torsion_motor", TorsionMotorBlockEntity::new)
            .visual(() -> SingleAxisRotatingVisual::shaft, false)
            .validBlocks(CKBlocks.TORSION_MOTOR)
            .renderer(() -> ShaftRenderer::new)
            .register();

    public static void register() {
    }
}
