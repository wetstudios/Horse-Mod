package net.andreasdarsa.horsemod.item;

import net.andreasdarsa.horsemod.HorseMod;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HorseMod.MODID);

    // Registering the Horseshoe with durability, so it can be enchanted with Unbreaking/Mending later
    public static final DeferredHolder<Item, Item> HORSESHOES = ITEMS.register("horseshoes",
            () -> new Item(new Item.Properties()
                    .stacksTo(1)
                    .durability(240))); // Same durability as iron boots
}
