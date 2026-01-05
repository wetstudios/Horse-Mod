package net.andreasdarsa.horsemod.event;

import net.andreasdarsa.horsemod.HorseMod;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = HorseMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ModGuiHandler {
    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        // We only care about the Horse/Donkey inventory screen
        if (event.getScreen() instanceof HorseInventoryScreen screen) {
            // Logic to adjust slot positions will go here.
            // Because we increased columns to 6 in the Mixin,
            // the game will naturally try to draw 6 slots across.
        }
    }

    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Post event) {
        if (event.getScreen() instanceof HorseInventoryScreen screen) {
            // Logic to shift the horse model rendering slightly to the left
            // to account for the extra 18 pixels on the right side.
        }
    }
}
