package net.wetstudios.horsemod.mixin;

import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractChestedHorse.class)
public class DonkeyChestIndexMixin {
    /**
     * Since minecraft starts the chest at index 2
     * We move it to 3 to make room for the horseshoe slot
     */
    private int horsemod$shiftChestStart(int original){
        return 3;
    }
}
