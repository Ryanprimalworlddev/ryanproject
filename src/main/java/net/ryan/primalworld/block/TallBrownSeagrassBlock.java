package net.ryan.primalworld.block;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class TallBrownSeagrassBlock extends DoublePlantBlock {
    public TallBrownSeagrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getSource(false); // Este bloco estará sempre em água
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.isFaceSturdy(worldIn, pos, Direction.UP);
    }

    // Se uma parte do bloco for quebrada, quebra a outra parte
    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf half = state.getValue(HALF);
        BlockPos otherHalfPos = (half == DoubleBlockHalf.LOWER) ? pos.above() : pos.below();
        BlockState otherHalfState = worldIn.getBlockState(otherHalfPos);

        if (otherHalfState.is(this) && otherHalfState.getValue(HALF) != half) {
            worldIn.destroyBlock(otherHalfPos, false);
            worldIn.levelEvent(player, 2001, otherHalfPos, Block.getId(otherHalfState));
        }

        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @OnlyIn(Dist.CLIENT)
    public static void setRenderLayer(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TALL_BROWN_SEAGRASS.get(), RenderType.cutout());
    }
}
