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

        //Our inventory
        this.addSlot(new DogFoodSlot(inv, 0, 80, 17));

        //The player inventory
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlot(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 48 + y * 18));
            }
        }

        //The player Hotbar
        for (int x = 0; x < 9; x++) {
            this.addSlot(new Slot(playerInv, x, 8 + x * 18, 106));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inv.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        Slot slot = this.slots.get(invSlot);

        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            ItemStack newStack = originalStack.copy();

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

            return newStack;
        }

        return ItemStack.EMPTY;
    }

    static class DogFoodSlot extends Slot {

        public DogFoodSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
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
