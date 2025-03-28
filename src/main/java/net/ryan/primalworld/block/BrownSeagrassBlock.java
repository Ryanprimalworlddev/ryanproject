package net.ryan.primalworld.block;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;

public class BrownSeagrassBlock extends BushBlock implements BonemealableBlock, LiquidBlockContainer, IForgeShearable {
    protected static final float AABB_OFFSET = 6.0F;
    protected static final VoxelShape SHAPE = Block.box(2.0F, 0.0F, 2.0F, 14.0F, 12.0F, 14.0F);

    // Adicionando a propriedade waterlogged
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BrownSeagrassBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, true)); // Define como submerso por padrão
    }

    // Forma do bloco
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    // Verificação para permitir a colocação do bloco
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.isFaceSturdy(level, pos, Direction.UP) && !state.is(Blocks.MAGMA_BLOCK);
    }

    // Define o estado de colocação com base no nível de água
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    // Atualiza a forma do bloco quando vizinhos mudam
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    // Checa se o bloco pode usar Bonemeal
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.above()).is(Blocks.WATER);
    }

    // Define se o Bonemeal é bem-sucedido
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    // Ação do Bonemeal (crescimento para dois blocos de altura)
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BlockState lowerBlockState = ModBlocks.TALL_BROWN_SEAGRASS.get().defaultBlockState().setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.LOWER);
        BlockState upperBlockState = ModBlocks.TALL_BROWN_SEAGRASS.get().defaultBlockState().setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
        BlockPos abovePos = pos.above();
        if (world.getBlockState(abovePos).is(Blocks.WATER)) {
            world.setBlock(pos, lowerBlockState, 2);
            world.setBlock(abovePos, upperBlockState, 2);
        }
    }

    // Estado do líquido (água)
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    // Definir os estados de blocos possíveis
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    public boolean canPlaceLiquid(BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public static void setRenderLayer(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BROWN_SEAGRASS.get(), RenderType.cutout());
    }
}
