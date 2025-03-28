package net.ryan.primalworld.entity.client.pirarucu;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.ryan.primalworld.entity.custom.IrritatorEntity;
import net.ryan.primalworld.entity.custom.PirarucuEntity;
import software.bernie.geckolib.model.GeoModel;
import net.ryan.primalworld.primalworld;

public class PirarucuModel<VertexConsumerProvider> extends GeoModel<PirarucuEntity> {
    @Override
    public ResourceLocation getModelResource(PirarucuEntity object) {
        return new ResourceLocation(primalworld.MOD_ID, "geo/pirarucu/pirarucu.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PirarucuEntity entity) {
            return new ResourceLocation(primalworld.MOD_ID, "textures/entity/pirarucu/pirarucu.png");
        }

    @Override
    public ResourceLocation getAnimationResource(PirarucuEntity animatable) {
        return new ResourceLocation(primalworld.MOD_ID, "animations/pirarucu/pirarucu.animation.json");
    }
}