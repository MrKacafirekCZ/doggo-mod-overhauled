package net.lordmrk.dmo;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class PlayerUtils {

    public static void decrementItemStackIfNotCreative(PlayerEntity player, ItemStack itemStack) {
        if (player.getAbilities().creativeMode) {
            return;
        }

        itemStack.decrement(1);
    }
}
