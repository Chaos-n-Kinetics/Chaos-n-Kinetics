package com.chaosteam.chaosnkinetics.compat.ponder;

import com.chaosteam.chaosnkinetics.ChaosKinetics;
import net.createmod.ponder.api.registration.PonderPlugin;
import org.jetbrains.annotations.NotNull;

public class CKPonderPlugin implements PonderPlugin {
    @Override
    public @NotNull String getModId() {
        return ChaosKinetics.MODID;
    }
}
