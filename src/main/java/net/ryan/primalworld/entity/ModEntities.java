package net.ryan.primalworld.entity;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ryan.primalworld.entity.custom.GuaibasaurEntity;
import net.ryan.primalworld.entity.custom.IrritatorEntity;
import net.ryan.primalworld.entity.custom.PirarucuEntity;
import net.ryan.primalworld.primalworld;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, primalworld.MOD_ID);

    public static final RegistryObject<EntityType<GuaibasaurEntity>> GUAIBASAUR =
            ENTITY_TYPES.register("guaibasaur", () -> EntityType.Builder.of(GuaibasaurEntity::new, MobCategory.CREATURE)
                    .sized(2.5f,2.5f).build("guaibasaur"));

    public static final RegistryObject<EntityType<IrritatorEntity>> IRRITATOR =
            ENTITY_TYPES.register("irritator", () -> EntityType.Builder.of(IrritatorEntity::new, MobCategory.CREATURE)
                    .sized(2f,2f).build("irritator"));

    public static final RegistryObject<EntityType<PirarucuEntity>> PIRARUCU =
            ENTITY_TYPES.register("pirarucu", () -> EntityType.Builder.of(PirarucuEntity::new, MobCategory.WATER_CREATURE)
                    .sized(2f,2f).build("pirarucu"));



    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);

    }
}
