package net.andreasdarsa.horsemod.event;

import net.andreasdarsa.horsemod.HorseMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = HorseMod.MODID)
public class EnchantmentEffectHandler {
    @SubscribeEvent
    public static void onHorseTick(EntityTickEvent.Post event){
        // Abstract Horse in order to apply logic to all horse types (horse, donkey, mule)
        if (event.getEntity() instanceof AbstractHorse horse && !horse.level().isClientSide){
            ItemStack armorStack = horse.getItemBySlot(EquipmentSlot.BODY);
            if (!armorStack.isEmpty()){
                // Check for frost walker level
                int frostLevel = EnchantmentHelper.getItemEnchantmentLevel(
                        horse.level().holderLookup(Registries.ENCHANTMENT).getOrThrow(Enchantments.FROST_WALKER),
                        armorStack
                );
                if (frostLevel > 0){
                    // This is where we will call the ice-making logic
                    // In 1.21, we have to manually trigger the block transformation
                }
            }
        }
    }
}
