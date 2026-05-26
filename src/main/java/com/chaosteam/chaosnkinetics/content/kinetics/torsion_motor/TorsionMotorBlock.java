package com.chaosteam.chaosnkinetics.content.kinetics.torsion_motor;

import com.chaosteam.chaosnkinetics.registry.CKBlockEntityTypes;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TorsionMotorBlock extends DirectionalKineticBlock implements IBE<TorsionMotorBlockEntity> {
    public static final EnumProperty<TorsionMotorMode> MODE = EnumProperty.create("mode", TorsionMotorMode.class);

    public TorsionMotorBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(MODE, TorsionMotorMode.IDLE));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection();
        if (context.getPlayer() == null || !context.getPlayer().isShiftKeyDown())
            facing = facing.getOpposite();
        return defaultBlockState()
                .setValue(FACING, facing)
                .setValue(MODE, TorsionMotorMode.IDLE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MODE);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction.Axis axis = state.getValue(FACING).getAxis();
        if (axis == Direction.Axis.X)
            return Block.box(0, 0, 2, 16, 16, 14);
        if (axis == Direction.Axis.Z)
            return Block.box(2, 0, 0, 14, 16, 16);
        return Block.box(2, 0, 0, 14, 16, 16);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        Direction facing = state.getValue(FACING);
        return state.getValue(MODE) == TorsionMotorMode.OUTPUT
                ? face == facing
                : face == facing.getOpposite();
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getAxis();
    }

    @Override
    public boolean hideStressImpact() {
        return true;
    }

    @Override
    protected boolean areStatesKineticallyEquivalent(BlockState oldState, BlockState newState) {
        return super.areStatesKineticallyEquivalent(oldState, newState)
                && oldState.getValue(MODE) == newState.getValue(MODE);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public Class<TorsionMotorBlockEntity> getBlockEntityClass() {
        return TorsionMotorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends TorsionMotorBlockEntity> getBlockEntityType() {
        return CKBlockEntityTypes.TORSION_MOTOR.get();
    }
}
