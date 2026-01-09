package net.wetstudios.horsemod.mixin;

import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorse.class)
public abstract class HorseInventorySizeMixin {
    @Inject(method = "getInventorySize", at = @At("HEAD"), cancellable = true)
    private void medievalmod$finalInventorySize(CallbackInfoReturnable<Integer> cir) {
        // If it's a donkey/mule with a chest, we need 21 slots total
        // (3 for equipment + 18 for chest)
        if ((Object)this instanceof net.minecraft.world.entity.animal.horse.AbstractChestedHorse chested && chested.hasChest()) {
            cir.setReturnValue(21);
        } else {
            // Standard horse: 3 slots (Saddle, Armor, Horseshoes)
            cir.setReturnValue(3);
        }
    }
}
