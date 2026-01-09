package net.wetstudios.horsemod.mixin;

import net.wetstudios.horsemod.HorseMod;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HorseInventoryScreen.class)
public class HorseInventoryScreenMixin {
    @Shadow
    @Final
    private AbstractHorse horse;

    private static final ResourceLocation WIDER_GUI = ResourceLocation.fromNamespaceAndPath(HorseMod.MODID, "textures/gui/container/horse_wider.png");

    @ModifyVariable(method = "renderBg", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private ResourceLocation medievalmod$useWiderTexture(ResourceLocation texture) {
        // If the horse is a Donkey or Mule (Chested Horse), swap to our new texture
        if (this.horse instanceof AbstractChestedHorse chestedHorse && chestedHorse.hasChest()) {
            return WIDER_GUI;
        }
        return texture;
    }
}
