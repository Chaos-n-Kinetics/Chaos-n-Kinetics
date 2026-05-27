package com.chaosteam.chaosnkinetics.content.kinetics.torsion_motor;

import net.minecraft.util.StringRepresentable;

public enum TorsionMotorMode implements StringRepresentable {
    IDLE("idle"),
    WINDING("winding"),
    OUTPUT("output");

    private final String serializedName;

    TorsionMotorMode(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }
}
