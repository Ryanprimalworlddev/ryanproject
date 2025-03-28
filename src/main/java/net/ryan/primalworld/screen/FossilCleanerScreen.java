package net.ryan.primalworld.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.ryan.primalworld.screen.renderer.FluidTankRenderer;
import net.ryan.primalworld.util.MouseUtil;
import org.openjdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;

import java.util.Optional;

public class FossilCleanerScreen extends AbstractContainerScreen<FossilCleanerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("primalworld", "textures/gui/fossil_cleaner.png");

    private FluidTankRenderer fluidRenderer;


    public FossilCleanerScreen(FossilCleanerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelX = 10000;

        assignFluidRenderer();
    }

    private void assignFluidRenderer() {
        fluidRenderer = new FluidTankRenderer(8000, true, 14, 56);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height- imageHeight) / 2-1;

        renderFluidToolTipArea(guiGraphics, pMouseX, pMouseY, x, y, menu.blockEntity.getFluid(), 13, 13, fluidRenderer);
    }

    private void renderFluidToolTipArea(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y,
                                        FluidStack stack, int offsetX, int offsetY, FluidTankRenderer renderer) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, renderer)) {
            guiGraphics.renderTooltip(this.font, renderer.getTooltip(stack, TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height- imageHeight) / 2-1;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y );

        fluidRenderer.render(guiGraphics, x + 13, y +13 , menu.blockEntity.getFluid());
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            // Desenha a seta de progresso da esquerda para a direita
            guiGraphics.blit(TEXTURE, x + 69, y + 51, 178, 0, menu.getScaledProgress(), 40);
        }
    }


    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(guiGraphics, pMouseX, pMouseY);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidTankRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }
}
