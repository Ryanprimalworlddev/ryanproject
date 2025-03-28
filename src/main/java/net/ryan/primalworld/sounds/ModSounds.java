package net.ryan.primalworld.sounds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ryan.primalworld.primalworld;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, primalworld.MOD_ID);

    public static final RegistryObject<SoundEvent> GUAIBASAUR_SEARCHING = registerSoundEvents("guaibasaur_searching");



    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        ResourceLocation id = new ResourceLocation(primalworld.MOD_ID, name);
        return SOUND_EVENTS.register(name, ()-> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
