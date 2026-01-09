package net.wetstudios.horsemod.event;

import net.wetstudios.horsemod.HorseMod;
import net.wetstudios.horsemod.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = HorseMod.MODID)
public class HorseHorseshoeLogic {

    // Unique ID for the horseshoe speed modifier
    private static final ResourceLocation HORSESHOE_SPEED_ID = ResourceLocation.fromNamespaceAndPath(HorseMod.MODID, "horseshoe_speed");

    // We'll give a 15% speed boost.
    private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(HORSESHOE_SPEED_ID, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);

    @SubscribeEvent
    public static void onHorseTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof AbstractHorse horse && !horse.level().isClientSide) {

            // In our previous Mixin, we put the Horseshoe Slot at Index 2 of the animalContainer.
            // However, inside the horse entity, the 'inventory' field is protected.
            // We'll assume you have an accessor or use the item slot index.

            // For now, we check the container directly (requires a Mixin or Accessor to 'inventory')
            // Let's use the most compatible method:
            ItemStack horseshoeStack = horse.getInventory().getItem(2);

            var speedAttribute = horse.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttribute != null) {
                boolean hasShoes = horseshoeStack.is(ModItems.HORSESHOES.get());
                boolean hasModifier = speedAttribute.hasModifier(HORSESHOE_SPEED_ID);

                if (hasShoes && !hasModifier) {
                    // Equipping: Add the speed boost
                    speedAttribute.addPermanentModifier(SPEED_MODIFIER);
                } else if (!hasShoes && hasModifier) {
                    // Unequipping: Remove the speed boost
                    speedAttribute.removeModifier(HORSESHOE_SPEED_ID);
                }
            }
        }
    }
}