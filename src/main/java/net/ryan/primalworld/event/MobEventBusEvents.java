package net.ryan.primalworld.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ryan.primalworld.entity.ModEntities;
import net.ryan.primalworld.entity.custom.GuaibasaurEntity;
import net.ryan.primalworld.entity.custom.IrritatorEntity;
import net.ryan.primalworld.entity.custom.PirarucuEntity;
import net.ryan.primalworld.primalworld;

@Mod.EventBusSubscriber(modid = primalworld.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GUAIBASAUR.get(), GuaibasaurEntity.createAttributes().build());
        event.put(ModEntities.IRRITATOR.get(), IrritatorEntity.createAttributes().build());
    }
}
