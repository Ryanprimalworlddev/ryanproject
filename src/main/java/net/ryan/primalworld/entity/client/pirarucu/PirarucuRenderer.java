package net.ryan.primalworld.entity.client.pirarucu;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.ryan.primalworld.entity.custom.IrritatorEntity;
import net.ryan.primalworld.entity.custom.PirarucuEntity;
import net.ryan.primalworld.primalworld;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PirarucuRenderer extends GeoEntityRenderer<PirarucuEntity> {
    public PirarucuRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PirarucuModel<>());
        this.shadowRadius = 0.1f; // Ajuste o tamanho da sombra

    }

    @Override
    protected void applyRotations(PirarucuEntity entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks);

        // Aumente a escala para ajustar o tamanho do irritator
        float scale = 1.0f;  // Ajuste este valor para o tamanho desejado
        poseStack.scale(scale, scale, scale);
    }

    @Override
    public void render(PirarucuEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) { // Usando MultiBufferSource
            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        }
    }

