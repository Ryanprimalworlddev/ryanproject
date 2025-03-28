package net.ryan.primalworld.entity.client.irritator;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.ryan.primalworld.entity.custom.IrritatorEntity;
import software.bernie.geckolib.model.GeoModel;
import net.ryan.primalworld.primalworld;

public class IrritatorModel<VertexConsumerProvider> extends GeoModel<IrritatorEntity> {
    @Override
    public ResourceLocation getModelResource(IrritatorEntity object) {
        return new ResourceLocation(primalworld.MOD_ID, "geo/irritator/irritator.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(IrritatorEntity entity) {
        if (entity.isBaby()) {
            return new ResourceLocation(primalworld.MOD_ID, "textures/entity/irritator/irritator_baby.png");
        } else if (entity.getGenero() == IrritatorEntity.Genero.MACHO) {
            return new ResourceLocation(primalworld.MOD_ID, "textures/entity/irritator/irritator_male.png");
        } else {
            return new ResourceLocation(primalworld.MOD_ID, "textures/entity/irritator/irritator_female.png");
        }
    }
    @Override
    public ResourceLocation getAnimationResource(IrritatorEntity animatable) {
        return new ResourceLocation(primalworld.MOD_ID, "animations/irritator/irritator.animation.json");
    }
}