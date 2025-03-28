package net.ryan.primalworld.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ryan.primalworld.entity.client.guaibasaur.GuaibasaurModel;
import net.ryan.primalworld.entity.client.guaibasaur.ModModelLayers;
import net.ryan.primalworld.primalworld;

@Mod.EventBusSubscriber(modid = primalworld.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.GUAIBASAUR_LAYER, GuaibasaurModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.GUAIBASAUR_BABY_LAYER, GuaibasaurModel::createBodyLayer);

    }
}
