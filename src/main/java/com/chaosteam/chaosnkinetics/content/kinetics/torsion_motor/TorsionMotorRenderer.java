package com.chaosteam.chaosnkinetics.content.kinetics.torsion_motor;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TorsionMotorRenderer extends KineticBlockEntityRenderer<TorsionMotorBlockEntity> {
    private static final RenderType SHAFT_RENDER_TYPE = RenderType.create("chaosnkinetics_torsion_motor_shaft",
            DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 4194304, true, false,
            RenderType.CompositeState.builder()
                    .setLightmapState(RenderStateShard.LIGHTMAP)
                    .setShaderState(RenderStateShard.RENDERTYPE_CUTOUT_MIPPED_SHADER)
                    .setTextureState(RenderStateShard.BLOCK_SHEET_MIPPED)
                    .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                    .createCompositeState(true));

    public TorsionMotorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(TorsionMotorBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        BlockState state = be.getBlockState();
        Direction.Axis axis = getRotationAxisOf(be);
        Direction facing = state.getValue(TorsionMotorBlock.FACING);
        BlockPos pos = be.getBlockPos();

        for (Direction direction : Iterate.directions) {
            if (direction.getAxis() != axis)
                continue;

            boolean outputSide = direction == facing;
            if (!outputSide)
                continue;
            float angle = getShaftAngle(be, pos, axis, direction, outputSide);
            SuperByteBuffer shaft = CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, state, direction);
            kineticRotationTransform(shaft, be, axis, angle, light);
            shaft.renderInto(ms, buffer.getBuffer(SHAFT_RENDER_TYPE));
        }
    }

    private float getShaftAngle(TorsionMotorBlockEntity be, BlockPos pos, Direction.Axis axis, Direction direction, boolean outputSide) {
        TorsionMotorMode mode = be.getBlockState().getValue(TorsionMotorBlock.MODE);
        boolean activeSide = mode == TorsionMotorMode.OUTPUT ? outputSide : !outputSide;
        // todo: this rotation still has a weird smoothing bug; it can stutter and needs a proper create-style fix
        if (activeSide)
            return getAngleForBe(be, pos, axis);

        BlockEntity neighbour = be.getLevel().getBlockEntity(pos.relative(direction));
        if (neighbour instanceof KineticBlockEntity kinetic)
            return getAngleForBe(kinetic, kinetic.getBlockPos(), axis);

        return getRotationOffsetForPosition(be, pos, axis) / 180f * (float) Math.PI;
    }
}
