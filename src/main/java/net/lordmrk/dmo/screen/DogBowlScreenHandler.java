package net.lordmrk.dmo.screen;

import net.lordmrk.dmo.DoggoModOverhauled;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class DogBowlScreenHandler extends ScreenHandler {

    private final Inventory inv;

    public DogBowlScreenHandler(int syncId, PlayerInventory playerInv) {
        this(syncId, playerInv, new SimpleInventory(1));
    }

    public DogBowlScreenHandler(int syncId, PlayerInventory playerInv, Inventory inv) {
        super(DoggoModOverhauled.DOG_BOWL_SCREEN_HANDLER, syncId);

        checkSize(inv, 1);
        this.inv = inv;

        inv.onOpen(playerInv.player);

        int m;
        int l;
        //Our inventory
        this.addSlot(new DogFoodSlot(inv, 0, 80, 17));
        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInv, l + m * 9 + 9, 8 + l * 18, 48 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInv, m, 8 + m * 18, 106));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inv.canPlayerUse(player);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inv.size()) {
                if (!this.insertItem(originalStack, this.inv.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inv.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    static class DogFoodSlot extends Slot {

        public DogFoodSlot(Inventory inventory, int i, int j, int k) {
            super(inventory, i, j, k);
        }

        public boolean canInsert(ItemStack stack) {
            return matches(stack);
        }

        public static boolean matches(ItemStack stack) {
            return stack.getItem().isFood() && stack.getItem().getFoodComponent().isMeat();
        }

        public int getMaxItemCount() {
            return 64;
        }
    }
}
