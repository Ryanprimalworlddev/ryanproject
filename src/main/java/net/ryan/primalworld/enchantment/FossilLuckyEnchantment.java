package net.ryan.primalworld.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.Tags;

public class FossilLuckyEnchantment extends Enchantment {

    public FossilLuckyEnchantment(Rarity rarity, EquipmentSlot... slots) {
        super(rarity, EnchantmentCategory.DIGGER, slots); // O encantamento é para ferramentas de mineração (digger).
    }

    @Override
    public int getMinCost(int level) {
        return 10 + (level - 1) * 5; // Defina o custo mínimo de XP para encantar.
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 15; // Custo máximo para encantar.
    }

    @Override
    public int getMaxLevel() {
        return 3; // Defina o nível máximo do encantamento (1 a 3).
    }

    // Defina aqui quais itens podem receber o encantamento, como picaretas, pás, etc.
    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.is(Tags.Items.TOOLS) || super.canEnchant(stack);
    }
}
