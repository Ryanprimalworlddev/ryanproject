package net.ryan.primalworld.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ryan.primalworld.block.entity.FossilCleanerBlockEntity;
import net.ryan.primalworld.block.entity.ModBlockEntities;
import net.ryan.primalworld.item.ModItems;
import net.ryan.primalworld.primalworld;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.rmi.registry.Registry;
import java.util.Random;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, primalworld.MOD_ID);

    public static final RegistryObject<Block> AMBER_BLOCK = registerBlock("amber_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANCIENT_DEBRIS)));

    public static final RegistryObject<Block> AMBER_ORE = registerBlock("amber_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));

    public static final RegistryObject<Block> DEEPSLATE_AMBER_ORE = registerBlock("deepslate_amber_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));

    public static final RegistryObject<Block> FOSSIL_BLOCK = registerBlock("fossil_block",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));

    public static final RegistryObject<Block> GRANITE_FOSSIL_BLOCK = registerBlock("granite_fossil_block",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));

    public static final RegistryObject<Block> ANDESITE_FOSSIL_BLOCK = registerBlock("andesite_fossil_block",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));

    public static final RegistryObject<Block> DIORITE_FOSSIL_BLOCK = registerBlock("diorite_fossil_block",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));

    public static final RegistryObject<Block> DEEPSLATE_FOSSIL_BLOCK = registerBlock("deepslate_fossil_block",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));

    public static final RegistryObject<Block> DEEPSLATE_TITANIUM_ORE = registerBlock("deepslate_titanium_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));

    public static final RegistryObject<Block> BROWN_SEAGRASS = registerBlock("brown_seagrass",
            () -> new BrownSeagrassBlock(BlockBehaviour.Properties.copy(Blocks.SEAGRASS)));

public static final RegistryObject<Block> TALL_BROWN_SEAGRASS = registerBlock("tall_brown_seagrass",
            () -> new TallBrownSeagrassBlock(BlockBehaviour.Properties.copy(Blocks.TALL_SEAGRASS)));






    public static final RegistryObject<Block> PALEO_TABLE = registerBlock("paleo_table",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)
                    .strength(1f).requiresCorrectToolForDrops()));

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }


}