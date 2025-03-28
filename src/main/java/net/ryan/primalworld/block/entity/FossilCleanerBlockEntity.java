package net.ryan.primalworld.block.entity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.ryan.primalworld.block.entity.ModBlockEntities;
import net.ryan.primalworld.item.ModItems;
import net.ryan.primalworld.screen.FossilCleanerMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.util.Random;

public class FossilCleanerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(7){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot){
                case 0 -> stack.getItem() == ModItems.MESOZOIC_FOSSIL_UNCOMMON.get() ||
                        stack.getItem() == ModItems.MESOZOIC_FOSSIL_COMMON.get() ||
                        stack.getItem() == ModItems.MESOZOIC_FOSSIL_RARE.get();
                case 1 -> stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
                case 2, 6, 5 -> false;
                case 3 -> stack.getItem() == Items.BRUSH;
                case 4 -> stack.getItem() == Items.BUCKET;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private final FluidTank FLUID_TANK = createFluidTank();

    public FluidStack getFluid() {
        return FLUID_TANK.getFluid();
    }

    private FluidTank createFluidTank() {
        return new FluidTank(8000) {
            @Override
            protected void onContentsChanged() {
                setChanged(); // Notifica que algo mudou no BlockEntity
                if (!level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return stack.getFluid() == Fluids.WATER;
            }
        };
    }



    private static final int INPUT_SLOT = 0;
    private static final int FLUID_INPUT_SLOT = 1;
    private static final int OUTPUT = 2;
    private static final int OUTPUT_1= 5;
    private static final int OUTPUT_2 = 6;
    private static final int BRUSH = 3;
    private static final int BUCKET_OUTPUT = 4;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;



    public FossilCleanerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FOSSIL_CLEANER_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> FossilCleanerBlockEntity.this.progress;
                    case 1 -> FossilCleanerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 -> FossilCleanerBlockEntity.this.progress = pValue;
                    case 1 -> FossilCleanerBlockEntity.this.maxProgress = pValue;
                }

            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Fossil Cleaner");
    }



    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FossilCleanerMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }

        if(cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag = FLUID_TANK.writeToNBT(pTag);  // Aqui o fluxo é salvo, mas é importante verificar se o NBT está sendo manipulado corretamente.
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        FLUID_TANK.readFromNBT(pTag);
    }

    public void tick(Level level, BlockPos pPos, BlockState pState) {
        fillUpOnFluid();

        // Verifica se há uma receita e se o slot de saída está vazio ou pode receber o item
        if (isOutputSlotEmptyOrReceivable() && hasRecipe()) {
            // Define o estado LIT como verdadeiro (bloco aceso)
            if (!pState.getValue(BlockStateProperties.LIT)) {
                level.setBlock(pPos, pState.setValue(BlockStateProperties.LIT, true), 3);
            }

            increaseCraftingProcess();
            setChanged(level, pPos, pState);

            if (level.isClientSide()) {
                spawnProcessingParticles(level, pPos);
            }

            // Se o progresso tiver sido concluído, faz a receita
            if (hasProgressFinished()) {
                craftItem();
                extractFluid();
                resetProgress();
            }
        } else {
            // Se não estiver processando, redefine o progresso e desliga o estado LIT
            if (pState.getValue(BlockStateProperties.LIT)) {
                level.setBlock(pPos, pState.setValue(BlockStateProperties.LIT, false), 3);
            }
            resetProgress();
        }
    }

    private void spawnProcessingParticles(Level level, BlockPos pPos) {
        Random random = (Random) level.random;

        // A quantidade de partículas a serem geradas
        int particleCount = 30;

        for (int i = 0; i < particleCount; i++) {
            // Gera as coordenadas aleatórias ao redor do bloco
            double x = pPos.getX() + 0.5 + (random.nextDouble() - 0.5);
            double y = pPos.getY() + 1.0 + (random.nextDouble() - 1.0);
            double z = pPos.getZ() + 0.5 + (random.nextDouble() - 0.5);

            // Gera a partícula. Substitua 'ParticleTypes.FLAME' pelo tipo de partícula que deseja

            level.addParticle(ParticleTypes.SMOKE,  // Trocar por outro tipo de partícula se necessário
                    x, y, z, 0.0D, 0.1D, 0.0D);// Configuração de movimento

            level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.STONE.defaultBlockState()),
                    x + (random.nextDouble() - 0.5D) * 0.7D, // Dispersão lateral
                    y,
                    z + (random.nextDouble() - 0.5D) * 0.7D, // Dispersão lateral
                    0.0D, -0.1D, 0.0D);

            level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.BASALT.defaultBlockState()),
                    x + (random.nextDouble() - 0.5D) * 0.7D, // Dispersão lateral
                    y,
                    z + (random.nextDouble() - 0.5D) * 0.7D, // Dispersão lateral
                    0.0D, -0.1D, 0.0D);
        }
    }






    private void extractFluid() {
        this.FLUID_TANK.drain(500, IFluidHandler.FluidAction.EXECUTE);
    }

    private void fillUpOnFluid() {
        if(hasFluidSourceInSlot(FLUID_INPUT_SLOT)) {
            transferItemFluidToTank(FLUID_INPUT_SLOT);
        }
    }

    private void transferItemFluidToTank(int fluidInputSlot) {
        this.itemHandler.getStackInSlot(fluidInputSlot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(iFluidHandlerItem -> {
            int drainAmount = Math.min(this.FLUID_TANK.getSpace(), 1000);

            FluidStack stack = iFluidHandlerItem.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if(stack.getFluid() == Fluids.WATER) {
                stack = iFluidHandlerItem.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                fillTankWithFluid(stack, iFluidHandlerItem.getContainer());
            }
        });
    }

    private void fillTankWithFluid(FluidStack stack, ItemStack container) {
        this.FLUID_TANK.fill(new FluidStack(stack.getFluid(), stack.getAmount()), IFluidHandler.FluidAction.EXECUTE);

        this.itemHandler.extractItem(FLUID_INPUT_SLOT, 1, false);
        this.itemHandler.insertItem(BUCKET_OUTPUT, container, false);
    }

    private boolean hasFluidSourceInSlot(int fluidInputSlot) {
        return this.itemHandler.getStackInSlot(fluidInputSlot).getCount() > 0 &&
                this.itemHandler.getStackInSlot(fluidInputSlot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();

    }

    private void craftItem() {
        // Remove 1 item do slot de entrada (fóssil)
        this.itemHandler.extractItem(INPUT_SLOT, 1, false);

        double randomChance = level.random.nextDouble();  // Gera um número aleatório entre 0 e 1

        // Verifica o fóssil comum e aplica as probabilidades
        if (this.itemHandler.getStackInSlot(INPUT_SLOT).getItem() == ModItems.MESOZOIC_FOSSIL_COMMON.get()) {
            if (randomChance < 0.10) { // 10% de chance de gerar mesozoic_tissue no fóssil comum
                this.itemHandler.setStackInSlot(OUTPUT_2, new ItemStack(ModItems.MESOZOIC_TISSUE.get(),
                        this.itemHandler.getStackInSlot(OUTPUT_2).getCount() + 1));
            } else if (randomChance < 0.50) { // 40% de chance de gerar osso
                this.itemHandler.setStackInSlot(OUTPUT, new ItemStack(Items.BONE,
                        this.itemHandler.getStackInSlot(OUTPUT).getCount() + 1));
            } else { // 50% de chance de gerar bone meal
                this.itemHandler.setStackInSlot(OUTPUT_1, new ItemStack(Items.BONE_MEAL,
                        this.itemHandler.getStackInSlot(OUTPUT_1).getCount() + 1));
            }
        }

        // Verifica o fóssil incomum e aplica as probabilidades
        if (this.itemHandler.getStackInSlot(INPUT_SLOT).getItem() == ModItems.MESOZOIC_FOSSIL_UNCOMMON.get()) {
            if (randomChance < 0.30) { // 30% de chance de gerar mesozoic_tissue no fóssil incomum
                this.itemHandler.setStackInSlot(OUTPUT_2, new ItemStack(ModItems.MESOZOIC_TISSUE.get(),
                        this.itemHandler.getStackInSlot(OUTPUT_2).getCount() + 1));
            } else if (randomChance < 0.60) { // 30% de chance de gerar osso
                this.itemHandler.setStackInSlot(OUTPUT, new ItemStack(Items.BONE,
                        this.itemHandler.getStackInSlot(OUTPUT).getCount() + 1));
            } else { // 40% de chance de gerar bone meal
                this.itemHandler.setStackInSlot(OUTPUT_1, new ItemStack(Items.BONE_MEAL,
                        this.itemHandler.getStackInSlot(OUTPUT_1).getCount() + 1));
            }
        }

        // Verifica o fóssil raro e aplica as probabilidades
        if (this.itemHandler.getStackInSlot(INPUT_SLOT).getItem() == ModItems.MESOZOIC_FOSSIL_RARE.get()) {
            if (randomChance < 0.50) { // 50% de chance de gerar mesozoic_tissue no fóssil raro
                this.itemHandler.setStackInSlot(OUTPUT_2, new ItemStack(ModItems.MESOZOIC_TISSUE.get(),
                        this.itemHandler.getStackInSlot(OUTPUT_2).getCount() + 1));
            } else if (randomChance < 0.80) { // 30% de chance de gerar osso
                this.itemHandler.setStackInSlot(OUTPUT, new ItemStack(Items.BONE,
                        this.itemHandler.getStackInSlot(OUTPUT).getCount() + 1));
            } else { // 20% de chance de gerar bone meal
                this.itemHandler.setStackInSlot(OUTPUT_1, new ItemStack(Items.BONE_MEAL,
                        this.itemHandler.getStackInSlot(OUTPUT_1).getCount() + 1));
            }
        }


        // Reduz a durabilidade do brush
        ItemStack brushStack = this.itemHandler.getStackInSlot(BRUSH);  // Pega o item no slot de brush
        if (!brushStack.isEmpty()) {  // Se o brush estiver no slot
            brushStack.hurt(1, level.random, null);  // Diminui a durabilidade do brush em 1, sem entidade
        }
    }




    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasProgressFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProcess() {
        this.progress++;
    }

    private boolean hasRecipe() {
        return canInsertAmountIntoOutputSlot(1)
                && (canInsertItemIntoOutputSlot(Items.BONE) || canInsertItemIntoOutputSlot(Items.BONE_MEAL))
                && hasRecipeItemInInputSlot()
                && hasBrushInSlot()
                && hasEnoughFluidToCraft();
    }

    private boolean hasEnoughFluidToCraft() {
        return this.FLUID_TANK.getFluidAmount() >= 500;
    }

    private boolean hasBrushInSlot() {
        // Verifica diretamente se o item no slot BRUSH é o Brush e se ainda tem durabilidade
        return this.itemHandler.getStackInSlot(BRUSH).getItem() == Items.BRUSH &&
                this.itemHandler.getStackInSlot(BRUSH).getDamageValue() <
                        this.itemHandler.getStackInSlot(BRUSH).getMaxDamage();
    }


    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT).is(item);
    }

    private boolean hasRecipeItemInInputSlot() {
        Item inputItem = this.itemHandler.getStackInSlot(INPUT_SLOT).getItem();
        return inputItem == ModItems.MESOZOIC_FOSSIL_UNCOMMON.get() ||
                inputItem == ModItems.MESOZOIC_FOSSIL_RARE.get() ||
                inputItem == ModItems.MESOZOIC_FOSSIL_COMMON.get();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT).getMaxStackSize() >=
                this.itemHandler.getStackInSlot(OUTPUT).getCount() + count;

    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.itemHandler.getStackInSlot(OUTPUT).isEmpty() ||
                this.itemHandler.getStackInSlot(OUTPUT).getCount() < this.itemHandler.getStackInSlot(OUTPUT).getMaxStackSize();
    }

    public LazyOptional<IFluidHandler> getLazyFluidHandler() {
        return lazyFluidHandler;
    }

    public void setLazyFluidHandler(LazyOptional<IFluidHandler> lazyFluidHandler) {
        this.lazyFluidHandler = lazyFluidHandler;
    }


    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }
}
