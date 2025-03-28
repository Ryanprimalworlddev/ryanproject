package net.ryan.primalworld.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.phys.BlockHitResult;
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

import java.util.function.Supplier;

import static net.ryan.primalworld.block.ModBlocks.registerBlock;

public class FossilCleaner {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, primalworld.MOD_ID);

    //BLOCOS MAQUINA//
//BLOCOS MAQUINA//
    public static final RegistryObject<Block> FOSSIL_CLEANER = registerBlock("fossil_cleaner",
            () -> new BaseEntityBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.BASALT)
                    .strength(1f).requiresCorrectToolForDrops()) {

                // Adiciona a direção e a propriedade LIT
                @Override
                public BlockState getStateForPlacement(BlockPlaceContext context) {
                    return this.defaultBlockState()
                            .setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite())
                            .setValue(BlockStateProperties.LIT, false);  // Inicializa o LIT como falso
                }

                @Override
                public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
                    // Verifica se o bloco está aceso (LIT), se estiver, retorna um valor de emissão de luz
                    return state.getValue(BlockStateProperties.LIT) ? 12 : 0; // Aumente ou diminua o nível de luz conforme desejar
                }

                @Override
                public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
                    if (state.getValue(BlockStateProperties.LIT)) { // Se o bloco estiver 'ligado'
                        double x = pos.getX() + 0.5D;
                        double y = pos.getY() + 1.0D;
                        double z = pos.getZ() + 0.5D;

                        for (int i = 0; i < 2; i++) {
                            level.addParticle(ParticleTypes.SMOKE,
                                    x + (random.nextDouble() - 0.5D) * 0.5D,
                                    y,
                                    z + (random.nextDouble() - 0.5D) * 0.5D,
                                    0.0D, 0.02D, 0.0D);
                        }

                        for (int i = 0; i < 4; i++) {
                            level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.STONE.defaultBlockState()),
                                    x + (random.nextDouble() - 0.5D) * 0.7D, // Dispersão lateral
                                    y,
                                    z + (random.nextDouble() - 0.5D) * 0.7D, // Dispersão lateral
                                    0.0D, -0.1D, 0.0D); // Velocidade negativa (caindo)
                        }

                        for (int i = 0; i < 4; i++) {
                            level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.BASALT.defaultBlockState()),
                                    x + (random.nextDouble() - 0.5D) * 0.7D, // Dispersão lateral
                                    y,
                                    z + (random.nextDouble() - 0.5D) * 0.7D, // Dispersão lateral
                                    0.0D, -0.1D, 0.0D); // Velocidade negativa (caindo)
                        }
                    }
                }




                @Override
                protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
                    builder.add(BlockStateProperties.HORIZONTAL_FACING);
                    builder.add(BlockStateProperties.LIT);// Adiciona a propriedade LIT
                }

                @Override
                public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
                    return new FossilCleanerBlockEntity(pPos, pState);
                }

                @Override
                public RenderShape getRenderShape(BlockState pState) {
                    return RenderShape.MODEL;
                }

                @Override
                public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
                    if (pState.getBlock() != pNewState.getBlock()) {
                        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                        if (blockEntity instanceof FossilCleanerBlockEntity) {
                            ((FossilCleanerBlockEntity) blockEntity).drops();
                        }
                    }

                    super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
                }

                @Override
                public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
                    if (!pLevel.isClientSide()) {
                        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                        if (blockEntity instanceof FossilCleanerBlockEntity) {
                            NetworkHooks.openScreen(((ServerPlayer) pPlayer), (FossilCleanerBlockEntity) blockEntity, pPos);
                        } else {
                            throw new IllegalStateException("Our named container provider is missing!");
                        }
                    }

                    return InteractionResult.sidedSuccess(pLevel.isClientSide());
                }

                @Override
                public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
                    if(pLevel.isClientSide()){
                        return null;
                    }
                    return createTickerHelper(pBlockEntityType, ModBlockEntities.FOSSIL_CLEANER_BLOCK_ENTITY.get(),
                            (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
                }
            });

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
