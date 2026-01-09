package net.wetstudios.horsemod.mixin;

import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractChestedHorse.class)
public class DonkeyInventoryMixin {

    /**
     * Minecraft calculates the donkey/mule inventory size based on
     * a constant for rows/columns. We inject here to change that value.
     */
    @Inject(method = "getInventorySize", at = @At("HEAD"), cancellable = true)
    private void horsemod$increaseDonkeyInventory(CallbackInfoReturnable<Integer> cir) {
        // Vanilla is 15. We want 18.
        // Formula: 2 (base) + 1 (armor) + (3 * columns)
        // To get 18 usable chest slots + the base horse slots, we set the total to 21.
        cir.setReturnValue(21);
    }

    @Inject(method = "getInventoryColumns", at = @At("HEAD"), cancellable = true)
    private void horsemod$changeColumns(CallbackInfoReturnable<Integer> cir) {
        // Changing this to 6 makes the math for slot placement
        // align with our 18-slot (6x3) goal.
        cir.setReturnValue(6);
    }
}