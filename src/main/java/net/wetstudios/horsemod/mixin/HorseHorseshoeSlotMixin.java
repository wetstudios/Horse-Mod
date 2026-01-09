package net.wetstudios.horsemod.mixin;

import net.wetstudios.horsemod.HorseMod;
import net.wetstudios.horsemod.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.entity.player.Inventory;

@Mixin(HorseInventoryMenu.class)
public class HorseHorseshoeSlotMixin {

    @Shadow @Final private AbstractHorse horse;

    private static final ResourceLocation EMPTY_HORSESHOE_SLOT = ResourceLocation.fromNamespaceAndPath(HorseMod.MODID, "item/empty_horseshoe_slot");

    @Inject(method = "<init>", at = @At("RETURN"))
    private void horsemod$addHorseshoeSlot(int containerId, Inventory playerInventory, Container animalContainer, final AbstractHorse horse, CallbackInfo ci) {
        // We add a custom slot.
        // 8 is the X coordinate (aligned with saddle/armor)
        // 54 is the Y coordinate (placed below the armor slot)
        AbstractContainerMenuAccessor accessor = (AbstractContainerMenuAccessor) (Object) this;

        accessor.invokeAddSlot(new Slot(animalContainer, 2, 8, 54) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItems.HORSESHOES.get());
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        }).setBackground(InventoryMenu.BLOCK_ATLAS, EMPTY_HORSESHOE_SLOT);
    }
}
