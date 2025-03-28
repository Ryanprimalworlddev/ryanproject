package net.ryan.primalworld.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CustomFossilItem extends Item {

    private final FossilRarity rarity;

    public CustomFossilItem(Properties properties, FossilRarity rarity) {
        super(properties);
        this.rarity = rarity;
    }

    @Override
    public Component getName(ItemStack stack) {
        // Escolhe a cor com base na raridade do fóssil
        int color;
        switch (rarity) {
            case RARE:
                color = 0x00FFFF; // Azul
                break;
            case UNCOMMON:
                color = 0x00FF00; // Verde
                break;
            case COMMON:
                color = 0xFFFF00; // Amarelo
                break;
            default:
                color = 0xFFFFFF; // Cor padrão (branco)
        }

        return Component.translatable("item.primalworld.mesozoic_fossil_" + rarity.name().toLowerCase())
                .setStyle(Style.EMPTY.withColor(color));
    }

    public enum FossilRarity {
        RARE, UNCOMMON, COMMON
    }
}
