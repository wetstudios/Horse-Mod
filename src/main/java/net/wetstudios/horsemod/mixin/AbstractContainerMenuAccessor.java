package net.wetstudios.horsemod.mixin;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuAccessor {
    @Invoker("addSlot")
    abstract Slot invokeAddSlot(Slot slot);
}
