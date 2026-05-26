package com.chaosteam.chaosnkinetics.compat.jei;

import com.chaosteam.chaosnkinetics.content.passiveheat.PassiveBasinRecipe;
import com.chaosteam.chaosnkinetics.content.passiveheat.PassiveMixingRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.compat.jei.category.animations.AnimatedMixer;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.block.Blocks;

import static com.simibubi.create.compat.jei.category.animations.AnimatedKinetics.DEFAULT_LIGHTING;

public class PassiveMixingCategory extends PassiveBasinCategory {
    private final AnimatedMixer mixer = new AnimatedMixer();
    private final AnimatedCampfire campfire = new AnimatedCampfire();

    public PassiveMixingCategory(Info<PassiveBasinRecipe> info) {
        super(info);
    }

    @Override
    public void draw(PassiveBasinRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        super.draw(recipe, iRecipeSlotsView, graphics, mouseX, mouseY);

        campfire.draw(graphics, getBackground().getWidth() / 2 + 3, 55);
        mixer.draw(graphics, getBackground().getWidth() / 2 + 3, 34);
    }
}