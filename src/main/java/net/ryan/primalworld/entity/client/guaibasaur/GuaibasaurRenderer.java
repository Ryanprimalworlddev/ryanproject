package net.ryan.primalworld.entity.client.guaibasaur;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.ryan.primalworld.entity.client.guaibasaur.ModModelLayers;
import net.ryan.primalworld.entity.custom.GuaibasaurEntity;
import net.ryan.primalworld.primalworld;

public class GuaibasaurRenderer extends MobRenderer<GuaibasaurEntity, GuaibasaurModel<GuaibasaurEntity>> {
    public GuaibasaurRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GuaibasaurModel<>(pContext.bakeLayer(ModModelLayers.GUAIBASAUR_LAYER)), 0.5f);
    }

    // Texturas para macho e fêmea do adulto
    private static final ResourceLocation MALE_TEXTURE = new ResourceLocation(primalworld.MOD_ID, "textures/entity/guaibasaur/guaibasaur_male.png");
    private static final ResourceLocation FEMALE_TEXTURE = new ResourceLocation(primalworld.MOD_ID, "textures/entity/guaibasaur/guaibasaur_female.png");

    // Texturas para macho e fêmea do bebê
    private static final ResourceLocation MALE_BABY_TEXTURE = new ResourceLocation(primalworld.MOD_ID, "textures/entity/guaibasaur/guaibasaur_male_savanna.png");
    private static final ResourceLocation FEMALE_BABY_TEXTURE = new ResourceLocation(primalworld.MOD_ID, "textures/entity/guaibasaur/guaibasaur_female_savanna.png");

    @Override
    public ResourceLocation getTextureLocation(GuaibasaurEntity guaibasaurEntity) {
        if (guaibasaurEntity.isBaby()) {
            return guaibasaurEntity.isMale() ? MALE_BABY_TEXTURE : FEMALE_BABY_TEXTURE;
        } else {
            return guaibasaurEntity.isMale() ? MALE_TEXTURE : FEMALE_TEXTURE;
        }
    }

    @Override
    public void render(GuaibasaurEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        // Se for bebê, escala para deixá-lo menor
        if (pEntity.isBaby()) {
            pPoseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
