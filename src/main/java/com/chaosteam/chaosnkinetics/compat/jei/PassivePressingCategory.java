package com.chaosteam.chaosnkinetics.compat.jei;

import com.chaosteam.chaosnkinetics.content.passiveheat.PassiveBasinRecipe;
import com.simibubi.create.compat.jei.category.animations.AnimatedPress;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.client.gui.GuiGraphics;

public class PassivePressingCategory extends PassiveBasinCategory {
    private final AnimatedPress press = new AnimatedPress(true);
    private final AnimatedCampfire campfire = new AnimatedCampfire();

    public PassivePressingCategory(Info<PassiveBasinRecipe> info) {
        super(info);
    }

    @Override
    public void draw(PassiveBasinRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        super.draw(recipe, iRecipeSlotsView, graphics, mouseX, mouseY);

        campfire.draw(graphics, getBackground().getWidth() / 2 + 3, 55);
        press.draw(graphics, getBackground().getWidth() / 2 + 3, 34);
    }
}