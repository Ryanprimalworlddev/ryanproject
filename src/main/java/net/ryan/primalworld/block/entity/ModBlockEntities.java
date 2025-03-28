package net.ryan.primalworld.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ryan.primalworld.block.FossilCleaner;
import net.ryan.primalworld.primalworld;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, primalworld.MOD_ID);

    public static final RegistryObject<BlockEntityType<FossilCleanerBlockEntity>> FOSSIL_CLEANER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fossil_cleaner_block_entity", () ->
                    BlockEntityType.Builder.of(FossilCleanerBlockEntity::new,
                            FossilCleaner.FOSSIL_CLEANER.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
