package net.ryan.primalworld;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.ryan.primalworld.block.BrownSeagrassBlock;
import net.ryan.primalworld.block.FossilCleaner;
import net.ryan.primalworld.block.ModBlocks;
import net.ryan.primalworld.block.TallBrownSeagrassBlock;
import net.ryan.primalworld.block.entity.ModBlockEntities;
import net.ryan.primalworld.enchantment.ModEnchantments;
import net.ryan.primalworld.entity.ModEntities;
import net.ryan.primalworld.entity.client.guaibasaur.GuaibasaurRenderer;
import net.ryan.primalworld.entity.client.irritator.IrritatorRenderer;
import net.ryan.primalworld.entity.client.pirarucu.PirarucuRenderer;
import net.ryan.primalworld.item.ModCreativeModTabs;
import net.ryan.primalworld.item.ModItems;
import net.ryan.primalworld.recipe.ModRecipes;
import net.ryan.primalworld.screen.FossilCleanerScreen;
import net.ryan.primalworld.screen.ModMenuTypes;
import net.ryan.primalworld.villager.PaleontologistVillager;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(primalworld.MOD_ID)
public class primalworld {
    public static final String MOD_ID = "primalworld";
    public static final Logger LOGGER = LogUtils.getLogger();




    public primalworld(FMLJavaModLoadingContext context){
        IEventBus modEventBus = context.getModEventBus();

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        FossilCleaner.register(modEventBus);

        ModEnchantments.register(modEventBus);

        ModEntities.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        ModMenuTypes.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        ModRecipes.register(modEventBus);

        GeckoLib.initialize();

        PaleontologistVillager.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event)  {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.AMBER);
            event.accept(ModItems.RAW_AMBER);
            event.accept(ModItems.MESOZOIC_FOSSIL_RARE);
            event.accept(ModItems.MESOZOIC_FOSSIL_UNCOMMON);
            event.accept(ModItems.MESOZOIC_FOSSIL_COMMON);
            event.accept(ModItems.PLANT_FOSSIL);
            event.accept(ModItems.FULL_DRIVE);
            event.accept(ModItems.DRIVE);
            event.accept(ModItems.MESOZOIC_TISSUE);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }



    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                EntityRenderers.register(ModEntities.GUAIBASAUR.get(), GuaibasaurRenderer::new);
                EntityRenderers.register(ModEntities.IRRITATOR.get(), IrritatorRenderer::new);
                EntityRenderers.register(ModEntities.PIRARUCU.get(), PirarucuRenderer::new);

                BrownSeagrassBlock.setRenderLayer(event);
                TallBrownSeagrassBlock.setRenderLayer(event);

                MenuScreens.register(ModMenuTypes.FOSSIL_CLEANER_MENU.get(), FossilCleanerScreen::new);
            });
        }
    }
}