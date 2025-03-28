package net.ryan.primalworld.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.ryan.primalworld.block.FossilCleaner;
import net.ryan.primalworld.block.ModBlocks;
import net.ryan.primalworld.primalworld;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, primalworld.MOD_ID);

    public static final RegistryObject<CreativeModeTab> PRIMAL_TAB = CREATIVE_MODE_TABS.register("primal_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.AMBER.get()))
                    .title(Component.translatable("Primal Tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.AMBER.get());
                        pOutput.accept(ModItems.RAW_AMBER.get());
                        pOutput.accept(ModItems.RAW_TITANIUM.get());
                        pOutput.accept(ModBlocks.AMBER_BLOCK.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_TITANIUM_ORE.get());

                        pOutput.accept(ModBlocks.BROWN_SEAGRASS.get());


                        pOutput.accept(ModBlocks.FOSSIL_BLOCK.get());
                        pOutput.accept(ModBlocks.GRANITE_FOSSIL_BLOCK.get());
                        pOutput.accept(ModBlocks.DIORITE_FOSSIL_BLOCK.get());
                        pOutput.accept(ModBlocks.ANDESITE_FOSSIL_BLOCK.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_FOSSIL_BLOCK.get());
                        pOutput.accept(ModBlocks.AMBER_ORE.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_AMBER_ORE.get());

                        pOutput.accept(FossilCleaner.FOSSIL_CLEANER.get());
                        pOutput.accept(ModBlocks.PALEO_TABLE.get());


                        pOutput.accept(ModItems.MESOZOIC_FOSSIL_RARE.get());
                        pOutput.accept(ModItems.MESOZOIC_FOSSIL_UNCOMMON.get());
                        pOutput.accept(ModItems.MESOZOIC_FOSSIL_COMMON.get());
                        pOutput.accept(ModItems.PLANT_FOSSIL.get());

                        //ITEM EXTRAÇÃO

                        pOutput.accept(ModItems.FULL_DRIVE.get());
                        pOutput.accept(ModItems.DRIVE.get());
                        pOutput.accept(ModItems.MESOZOIC_TISSUE.get());


                        pOutput.accept(ModItems.GUAIBASAUR_SPAWN_EGG.get());
                        pOutput.accept(ModItems.IRRITATOR_SPAWN_EGG.get());
                    })
                    .build());


    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
