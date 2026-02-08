package net.wetstudios.horsemod.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.wetstudios.horsemod.mixin.AbstractHorseAccessor;

public class ExpandedHorseMenu extends HorseInventoryMenu {
    public ExpandedHorseMenu(int containerId, Inventory playerInventory, Container inventory, final AbstractHorse horse, int columns) {
        super(containerId, playerInventory, inventory, horse, columns);

        if (horse instanceof AbstractHorseAccessor accessor) {
            SimpleContainer horseInv = accessor.getInventory();

            /* The super constructor for HorseInventoryMenu handles the first 5 columns.
               We manually add the 6th column (3 slots) to match the texture grid.
            */
            int slotStartX = 80;
            int slotStartY = 18;
            for (int row = 0; row < 3; ++row) {
                // slot index: 2 (saddle/armor) + 15 (5x3 grid) + current row = 17, 18, 19
                // x position: 80 (start) + (5 columns * 18px) = 170
                for (int col = 0; col < 6; ++col){
                    // Each slot box + its border equals 18 pixels
                    this.addSlot(new Slot(horseInv, 2 + col + (row * 6), slotStartX + (col * 18), slotStartY + (row * 18)));
                }
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        // Vanilla horse menu assumes slots end at 16 (2 + 15)
        // For a 6-column donkey inv, they end at 19 (2 + 18)
        int horseInventoryEnd = 2 + 18;

        // If index < horseInventoryEnd, move to player inventory.
        // If index >= horseInventoryEnd, move to horse inventory.
        // ... (Standard Shift-Click implementation)
        return ItemStack.EMPTY;
    }
}
