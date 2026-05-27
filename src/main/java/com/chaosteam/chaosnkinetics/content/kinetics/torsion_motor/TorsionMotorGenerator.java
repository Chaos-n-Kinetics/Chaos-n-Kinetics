package com.chaosteam.chaosnkinetics.content.kinetics.torsion_motor;

import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.client.model.generators.ModelFile;

public class TorsionMotorGenerator extends SpecialBlockStateGen {
    @Override
    protected Property<?>[] getIgnoredProperties() {
        return new Property<?>[] { TorsionMotorBlock.MODE };
    }

    @Override
    protected int getXRotation(BlockState state) {
        Direction facing = state.getValue(TorsionMotorBlock.FACING);
        return facing == Direction.DOWN ? 180 : 0;
    }

    @Override
    protected int getYRotation(BlockState state) {
        Direction facing = state.getValue(TorsionMotorBlock.FACING);
        return facing.getAxis().isVertical() ? 0 : horizontalAngle(facing) + 180;
    }

    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        return prov.models().getExistingFile(prov.modLoc("block/torsion_motor"));
    }
}
