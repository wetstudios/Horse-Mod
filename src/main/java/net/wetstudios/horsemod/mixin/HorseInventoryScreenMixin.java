package net.wetstudios.horsemod.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.wetstudios.horsemod.HorseMod;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseInventoryScreen.class)
public class HorseInventoryScreenMixin extends AbstractContainerScreen<HorseInventoryMenu> {
    @Shadow
    @Final
    private AbstractHorse horse;
    private static final ResourceLocation WIDER_GUI = ResourceLocation.fromNamespaceAndPath(HorseMod.MODID, "textures/gui/container/expanded_donkey.png");

    public HorseInventoryScreenMixin(HorseInventoryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @ModifyVariable(method = "renderBg", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private ResourceLocation horsemod$useWiderTexture(ResourceLocation texture) {
        // If the horse is a Donkey or Mule (Chested Horse), swap to our new texture
        if (this.horse instanceof AbstractChestedHorse chestedHorse && chestedHorse.hasChest()) {
            return WIDER_GUI;
        }
        return texture;
    }

    @ModifyConstant(method = "renderBg", constant = @Constant(intValue = 51))
    private int horsemod$nudgeHorseModel(int originalX) {
        // If it's a donkey/mule with our wide 6-column chest
        if (this.horse instanceof AbstractChestedHorse chested && chested.hasChest()) {
            // Move it right by 9 pixels (half of the 18px we added)
            return originalX + 9;
        }
        return originalX;
    }

    @Inject(method = "renderBg", at = @At("TAIL"))
    protected void horsemod$renderExpandedSlots(GuiGraphics graphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci){
        if (this.horse instanceof AbstractChestedHorse chested && chested.hasChest()){
            // Safe cast logic
            if (this.horse instanceof AbstractHorseAccessor accessor){
                SimpleContainer inventory = accessor.getInventory();
                int x = this.leftPos;
                int y = this.topPos;
                graphics.blit(WIDER_GUI, x + 79, y + 17, 0, 0, 108, 54, 108, 54);
            }
            else{
                System.err.println("Failed to cast " + this.horse.getClass().getName() + " to AbstractHorseAccessor!");
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

    }
}
