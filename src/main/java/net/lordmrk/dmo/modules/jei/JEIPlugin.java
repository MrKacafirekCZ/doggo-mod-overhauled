package net.lordmrk.dmo.modules.jei;

public class JEIPlugin {}
/*

###################################################
#                                                 #
#   WAITING FOR JEI TO UPDATE TO VERSION 1.19.3   #
#                                                 #
###################################################

package net.lordmrk.dmo.modules.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.lordmrk.dmo.DoggoModOverhauled;
import net.minecraft.item.Item;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return null;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registerDescriptions(registration);
    }

    private void registerDescriptions(IRecipeRegistration registration) {
        addDescription(registration, DoggoModOverhauled.TENNIS_BALL, "Right-click to throw the ball.", "Though be careful when you're near glass panes.");
    }

    private void addDescription(IRecipeRegistration registration, Item item, String... lines) {

    }
}

*/