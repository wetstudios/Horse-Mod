package net.andreasdarsa.horsemod.event;

import net.andreasdarsa.horsemod.HorseMod;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.checkerframework.checker.units.qual.A;

@EventBusSubscriber(modid = HorseMod.MODID)
public class MountAttributeHandler {
    @SubscribeEvent
    public static void onMountSpawn(EntityJoinLevelEvent event){
        if (event.getLevel().isClientSide()) return;

        // HORSE LOGIC
        if (event.getEntity() instanceof Horse horse){
            // Health: 15 to 35
            double health = 15.0 + (event.getLevel().random.nextDouble() * 20.0);
            horse.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
            horse.setHealth((float)health);

            // Speed: 0.2 to 0.494
            double speed = 0.2 + (event.getLevel().random.nextDouble() * 0.294);
            horse.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(speed);

            // Jump: 0.5 to 1.2
            double jump = 0.5 + (event.getLevel().random.nextDouble() * 0.7);
            horse.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(jump);
        }

        // DONKEY LOGIC
        if (event.getEntity() instanceof Donkey donkey){
            // Health: 15 to 30
            double health = 15.0 + (event.getLevel().random.nextDouble() * 20.0);
            donkey.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
            donkey.setHealth((float)health);

            // Speed: 0.16 to 0.36
            double speed = 0.16 + (event.getLevel().random.nextDouble() * 0.20);
            donkey.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(speed);

            // Jump: 0.35 to 0.9
            double jump = 0.35 + (event.getLevel().random.nextDouble() * 0.55);
            donkey.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(jump);
        }
    }
}
