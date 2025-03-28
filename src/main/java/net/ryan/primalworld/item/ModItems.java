package net.ryan.primalworld.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ryan.primalworld.entity.ModEntities;
import net.ryan.primalworld.primalworld;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, primalworld.MOD_ID);

    public static final RegistryObject<Item> AMBER = ITEMS.register("amber",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_AMBER = ITEMS.register("raw_amber",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MESOZOIC_FOSSIL_RARE = ITEMS.register("mesozoic_fossil_rare",
            () -> new CustomFossilItem(new Item.Properties(), CustomFossilItem.FossilRarity.RARE));

    public static final RegistryObject<Item> MESOZOIC_FOSSIL_UNCOMMON = ITEMS.register("mesozoic_fossil_uncommon",
            () -> new CustomFossilItem(new Item.Properties(), CustomFossilItem.FossilRarity.UNCOMMON));

    public static final RegistryObject<Item> MESOZOIC_FOSSIL_COMMON = ITEMS.register("mesozoic_fossil_common",
            () -> new CustomFossilItem(new Item.Properties(), CustomFossilItem.FossilRarity.COMMON));

    public static final RegistryObject<Item> PLANT_FOSSIL = ITEMS.register("plant_fossil",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GUAIBASAUR_SPAWN_EGG = ITEMS.register("guaibasaur_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GUAIBASAUR, 0xb7773b, 0x737171, new Item.Properties()));

    public static final RegistryObject<Item> IRRITATOR_SPAWN_EGG = ITEMS.register("irritator_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.IRRITATOR, 0x808080, 0x333333,
                    new Item.Properties()));



    //ITEMS EXTRAÇÂO

    public static final RegistryObject<Item> FULL_DRIVE = ITEMS.register("full_drive",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DRIVE = ITEMS.register("drive",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MESOZOIC_TISSUE = ITEMS.register("mesozoic_tissue",
            () -> new Item(new Item.Properties()));


    //DATA GEN
    public static final RegistryObject<Item> RAW_TITANIUM = ITEMS.register("raw_titanium",
            () -> new Item(new Item.Properties()));

    public static void register (IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
