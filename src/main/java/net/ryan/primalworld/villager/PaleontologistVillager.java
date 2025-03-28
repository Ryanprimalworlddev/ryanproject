package net.ryan.primalworld.villager;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ryan.primalworld.block.ModBlocks;
import net.ryan.primalworld.primalworld;


public class PaleontologistVillager {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, primalworld.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSION =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, primalworld.MOD_ID);

    public static final RegistryObject<PoiType> PALEOTABLE_POI = POI_TYPES.register("paleotable_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.PALEO_TABLE.get().getStateDefinition().getPossibleStates()),
                    1, 1));


    public static final RegistryObject<VillagerProfession> PALEONTOLOGIST =
            VILLAGER_PROFESSION.register("paleontologist",
                    () -> new VillagerProfession("paleontologist",
                            holder -> holder.get() == PALEOTABLE_POI.get(), // Predicado que checa o POI
                            holder -> holder.get() == PALEOTABLE_POI.get(), // Predicado de segunda condição
                            ImmutableSet.of(), ImmutableSet.of(),
                            SoundEvents.VILLAGER_WORK_WEAPONSMITH));







    public static void register (IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSION.register(eventBus);
    }
}
