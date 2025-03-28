package net.ryan.primalworld.event;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.ryan.primalworld.entity.ModEntities;
import net.ryan.primalworld.entity.client.irritator.IrritatorRenderer;
import net.ryan.primalworld.entity.client.pirarucu.PirarucuRenderer;

public class ModClientEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.IRRITATOR.get(), IrritatorRenderer::new);
        event.registerEntityRenderer(ModEntities.PIRARUCU.get(), PirarucuRenderer::new);
    }
}
