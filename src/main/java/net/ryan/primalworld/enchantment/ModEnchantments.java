package net.ryan.primalworld.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ryan.primalworld.primalworld;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, primalworld.MOD_ID);

    public static final RegistryObject<Enchantment> FOSSIL_LUCKY = ENCHANTMENTS.register("fossil_lucky",
            () -> new FossilLuckyEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
