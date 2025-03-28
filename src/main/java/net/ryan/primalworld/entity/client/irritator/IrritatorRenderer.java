package net.ryan.primalworld.entity.client.irritator;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.ryan.primalworld.entity.custom.IrritatorEntity;
import net.ryan.primalworld.primalworld;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class IrritatorRenderer extends GeoEntityRenderer<IrritatorEntity> {
    public IrritatorRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new IrritatorModel());
        this.shadowRadius = 0.7f; // Ajuste o tamanho da sombra

    }

    @Override
    protected void applyRotations(IrritatorEntity entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks);

        // Aumente a escala para ajustar o tamanho do irritator
        float scale = 1.5f;  // Ajuste este valor para o tamanho desejado
        poseStack.scale(scale, scale, scale);
    }

    @Override
    public void render(IrritatorEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) { // Usando MultiBufferSource
        if (entity.isBaby()) {
            poseStack.pushPose();
            poseStack.scale(0.2f, 0.2f, 0.2f);
            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
            poseStack.popPose();
        } else {
            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        }
    }
}

