package net.andreasdarsa.horsemod.mixin;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AbstractHorse.class)
public abstract class HorseDataSaverMixin {
    @Shadow protected SimpleContainer inventory;

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void horsemod$saveCustomSlots(CompoundTag compound, CallbackInfo ci) {
        // We get the registry provider from the entity's level
        HolderLookup.Provider registries = ((AbstractHorse)(Object)this).level().registryAccess();
        ListTag listtag = new ListTag();
        // We loop through the ENTIRE inventory, including our new slots
        for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
            net.minecraft.world.item.ItemStack itemstack = this.inventory.getItem(i);
            if (!itemstack.isEmpty()) {
                CompoundTag slotTag = new CompoundTag();
                slotTag.putByte("Slot", (byte)i);
                // In 1.21, we use save(holderLookup) for the new Data Component system
                // But for simplicity in NBT, saveWithoutMetadata or the standard save works
                itemstack.save(registries, slotTag);
                listtag.add(slotTag);
            }
        }
        compound.put("MedievalItems", listtag);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void horsemod$loadCustomSlots(CompoundTag compound, CallbackInfo ci) {
        if (compound.contains("MedievalItems", 9)) {
            // We get the registry provider from the entity's level
            HolderLookup.Provider registries = ((AbstractHorse)(Object)this).level().registryAccess();
            ListTag listtag = compound.getList("MedievalItems", 10);
            for(int i = 0; i < listtag.size(); ++i) {
                CompoundTag slotTag = listtag.getCompound(i);
                int j = slotTag.getByte("Slot") & 255;
                // Only load into slots that are valid for our expanded container
                if (j < this.inventory.getContainerSize()) {
                    // Modern 1.21 way to load an ItemStack from NBT
                    Optional<ItemStack> parsed = ItemStack.parse(registries, slotTag);
                    this.inventory.setItem(j, parsed.orElse(ItemStack.EMPTY));
                }
            }
        }
    }
}