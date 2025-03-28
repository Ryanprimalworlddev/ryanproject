package net.ryan.primalworld.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ryan.primalworld.item.ModItems;
import net.ryan.primalworld.primalworld;
import net.ryan.primalworld.villager.PaleontologistVillager;

import java.util.List;

@Mod.EventBusSubscriber(modid = primalworld.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {


    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == PaleontologistVillager.PALEONTOLOGIST.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            int villagerLevel = 1;  // Nível do Villager para as trocas

            // Primeira troca: 10 esmeraldas por 1 mesozoic tissue
            ItemStack tissueStack = new ItemStack(ModItems.MESOZOIC_TISSUE.get(), 1);
            trades.get(villagerLevel).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10), tissueStack, 2, 5, 0.06f
            ));

            // Segunda troca: 3 esmeraldas por 1 brush
            ItemStack brushStack = new ItemStack(Items.BRUSH, 1);
            trades.get(villagerLevel).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 3), brushStack, 5, 10, 0.05f
            ));
        }
    }
}



