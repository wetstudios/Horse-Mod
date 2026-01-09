package net.wetstudios.horsemod.event;

import net.wetstudios.horsemod.HorseMod;
import net.wetstudios.horsemod.mixin.AbstractHorseAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.world.level.Level;
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

    private static void applyFrostWalkerLogic(AbstractHorse horse, int level){
        if (level <= 0) return;

        float radius = (float)Math.min(16, 2+level);
        BlockPos pos = horse.blockPosition();
        Level levelObj = horse.level();

        // We iterate in a circle around the horse
        for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset((int)-radius, -1, (int)-radius), pos.offset((int)radius, -1, (int)radius))) {
            if (blockpos.closerToCenterThan(horse.position(), (double)radius)) {
                // Check if the block is water and can be frozen
                if (levelObj.getBlockState(blockpos).is(net.minecraft.world.level.block.Blocks.WATER) &&
                        levelObj.getBlockState(blockpos.above()).isAir()) {

                    // Set the block to Frosted Ice (which melts over time)
                    levelObj.setBlockAndUpdate(blockpos, net.minecraft.world.level.block.Blocks.FROSTED_ICE.defaultBlockState());
                    // Schedule a tick so the ice knows when to start melting
                    levelObj.scheduleTick(blockpos, net.minecraft.world.level.block.Blocks.FROSTED_ICE, net.minecraft.util.Mth.nextInt(horse.getRandom(), 60, 120));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onHorseMove(EntityTickEvent.Post event){
        if (event.getEntity() instanceof AbstractHorse horse && !horse.level().isClientSide){
            // Step 1: Get the horseshoes, at the 2nd inventory slot
            ItemStack shoes = ((AbstractHorseAccessor) horse).getInventory().getItem(2);
            if (!shoes.isEmpty()){
                // Step 2: Get the frost walker level
                var registry = horse.level().holderLookup(Registries.ENCHANTMENT);
                int frostLevel= shoes.getEnchantmentLevel(registry.getOrThrow(Enchantments.FROST_WALKER));
                if (frostLevel > 0 && horse.onGround()){
                    // Step 3: Freeze the water if the horse is on ground and has the enchantment
                    applyFrostWalkerLogic(horse, frostLevel);
                }
            }
        }
    }
}
