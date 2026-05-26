package com.chaosteam.chaosnkinetics.content.kinetics.torsion_motor;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TorsionMotorBlockEntity extends GeneratingKineticBlockEntity {
    public static final float MAX_INPUT_RPM = 64;
    public static final float MAX_OUTPUT_RPM = 16;
    public static final float STRESS_CAPACITY = 64;
    public static final int FULL_OUTPUT_TICKS = 20 * 60 * 5;
    public static final int FULL_WIND_TICKS_AT_MAX_RPM = 20 * 75;

    private float charge;
    private float windingSign = 1;
    private float lastGeneratedSpeed;

    public TorsionMotorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.putFloat("Charge", charge);
        compound.putFloat("WindingSign", windingSign);
        super.write(compound, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        charge = compound.getFloat("Charge");
        windingSign = compound.contains("WindingSign") ? compound.getFloat("WindingSign") : 1;
        super.read(compound, registries, clientPacket);
    }

    @Override
    public void tick() {
        super.tick();

        if (level == null || level.isClientSide)
            return;

        float inputSpeed = getBackInputSpeed();
        if (Math.abs(inputSpeed) > 0) {
            if (Math.abs(inputSpeed) > MAX_INPUT_RPM) {
                breakIntoItem();
                return;
            }

            if (setMode(TorsionMotorMode.WINDING))
                return;

            wind(inputSpeed);
            return;
        }

        if (charge > 0) {
            if (setMode(TorsionMotorMode.OUTPUT))
                return;

            discharge();
            return;
        }

        if (setMode(TorsionMotorMode.IDLE))
            return;

        updateOutputSpeed(true);
    }

    private void breakIntoItem() {
        BlockState state = getBlockState();
        Block.popResource(level, worldPosition, new ItemStack(state.getBlock()));
        level.removeBlock(worldPosition, false);
    }

    private void wind(float inputSpeed) {
        windingSign = Math.signum(inputSpeed);
        if (windingSign == 0)
            windingSign = 1;

        float previousCharge = charge;
        float windingRate = Math.abs(inputSpeed) / MAX_INPUT_RPM / FULL_WIND_TICKS_AT_MAX_RPM;
        charge = Mth.clamp(charge + windingRate, 0, 1);

        if (previousCharge != charge) {
            setChanged();
            if (level.getGameTime() % 20 == 0)
                sendData();
        }
    }

    private void discharge() {
        float previousCharge = charge;
        charge = Mth.clamp(charge - 1f / FULL_OUTPUT_TICKS, 0, 1);

        if (previousCharge != charge) {
            setChanged();
            updateOutputSpeed(level.getGameTime() % 20 == 0 || charge == 0);
            if (level.getGameTime() % 20 == 0 || charge == 0)
                sendData();
        }
    }

    private float getBackInputSpeed() {
        TorsionMotorMode mode = getBlockState().getValue(TorsionMotorBlock.MODE);
        if (mode != TorsionMotorMode.OUTPUT && hasSource())
            return getSpeed();

        Direction back = getBlockState().getValue(TorsionMotorBlock.FACING).getOpposite();
        BlockPos inputPos = worldPosition.relative(back);
        BlockState inputState = level.getBlockState(inputPos);
        if (!(inputState.getBlock() instanceof IRotate rotate))
            return 0;
        if (!rotate.hasShaftTowards(level, inputPos, inputState, back.getOpposite()))
            return 0;

        BlockEntity blockEntity = level.getBlockEntity(inputPos);
        if (!(blockEntity instanceof KineticBlockEntity kineticBlockEntity))
            return 0;
        return kineticBlockEntity.getSpeed();
    }

    private boolean setMode(TorsionMotorMode mode) {
        BlockState state = getBlockState();
        if (state.getValue(TorsionMotorBlock.MODE) == mode)
            return false;

        KineticBlockEntity.switchToBlockState(level, worldPosition, state.setValue(TorsionMotorBlock.MODE, mode));
        return true;
    }

    private void updateOutputSpeed(boolean force) {
        float generatedSpeed = getGeneratedSpeed();
        if (!force && Math.abs(generatedSpeed - lastGeneratedSpeed) < 0.25f)
            return;

        lastGeneratedSpeed = generatedSpeed;
        updateGeneratedRotation();
    }

    @Override
    public float getGeneratedSpeed() {
        if (!getBlockState().hasProperty(TorsionMotorBlock.MODE))
            return 0;
        if (getBlockState().getValue(TorsionMotorBlock.MODE) != TorsionMotorMode.OUTPUT)
            return 0;
        if (charge <= 0)
            return 0;

        Direction facing = getBlockState().getValue(TorsionMotorBlock.FACING);
        return convertToDirection(-windingSign * charge * MAX_OUTPUT_RPM, facing);
    }

    @Override
    public float calculateAddedStressCapacity() {
        float capacity = getGeneratedSpeed() == 0 ? 0 : STRESS_CAPACITY;
        lastCapacityProvided = capacity;
        return capacity;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        tooltip.add(Component.literal(" ").append(Component.literal("Charge: ").withStyle(ChatFormatting.GRAY))
                .append(Component.literal(Math.round(charge * 100) + "%").withStyle(ChatFormatting.AQUA)));
        return true;
    }

    public float getCharge() {
        return charge;
    }
}
