package net.ryan.primalworld.entity.client.guaibasaur;// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.ryan.primalworld.entity.animations.guaibasaur.GuaibasurAnimationDefinitions;
import net.ryan.primalworld.entity.custom.GuaibasaurEntity;

public class GuaibasaurModel<T extends Entity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "guaibassauro"), "main");
    private final ModelPart Guaiba;
    private final ModelPart corpo;
    private final ModelPart rabo;
    private final ModelPart rabo1;
    private final ModelPart rabo2;
    private final ModelPart rabo3;
    private final ModelPart rabo4;
    private final ModelPart pernaE;
    private final ModelPart SUPERIOR;
    private final ModelPart INFERIOR;
    private final ModelPart PE;
    private final ModelPart DEDO;
    private final ModelPart pernaD;
    private final ModelPart SUPERIORD;
    private final ModelPart INFERIORD;
    private final ModelPart PED;
    private final ModelPart DEDOD;
    private final ModelPart bracicoE;
    private final ModelPart SUPERIORBRACICOESQ;
    private final ModelPart INFERIORBRACICOESQ;
    private final ModelPart bracicoD;
    private final ModelPart SUPERIORBRACICODIR;
    private final ModelPart INFERIORBRACICODIR;
    private final ModelPart pescocito;
    private final ModelPart cabea;
    private final ModelPart SUP;
    private final ModelPart MANDIBULA;

    public GuaibasaurModel(ModelPart root) {
        this.Guaiba = root.getChild("Guaiba");
        this.corpo = this.Guaiba.getChild("corpo");
        this.rabo = this.corpo.getChild("rabo");
        this.rabo1 = this.rabo.getChild("rabo1");
        this.rabo2 = this.rabo.getChild("rabo2");
        this.rabo3 = this.rabo.getChild("rabo3");
        this.rabo4 = this.rabo.getChild("rabo4");
        this.pernaE = this.Guaiba.getChild("pernaE");
        this.SUPERIOR = this.pernaE.getChild("SUPERIOR");
        this.INFERIOR = this.pernaE.getChild("INFERIOR");
        this.PE = this.pernaE.getChild("PE");
        this.DEDO = this.pernaE.getChild("DEDO");
        this.pernaD = this.Guaiba.getChild("pernaD");
        this.SUPERIORD = this.pernaD.getChild("SUPERIORD");
        this.INFERIORD = this.pernaD.getChild("INFERIORD");
        this.PED = this.pernaD.getChild("PED");
        this.DEDOD = this.pernaD.getChild("DEDOD");
        this.bracicoE = this.Guaiba.getChild("bracicoE");
        this.SUPERIORBRACICOESQ = this.bracicoE.getChild("SUPERIORBRACICOESQ");
        this.INFERIORBRACICOESQ = this.bracicoE.getChild("INFERIORBRACICOESQ");
        this.bracicoD = this.Guaiba.getChild("bracicoD");
        this.SUPERIORBRACICODIR = this.bracicoD.getChild("SUPERIORBRACICODIR");
        this.INFERIORBRACICODIR = this.bracicoD.getChild("INFERIORBRACICODIR");
        this.pescocito = this.Guaiba.getChild("pescocito");
        this.cabea = this.Guaiba.getChild("cabea");
        this.SUP = this.cabea.getChild("SUP");
        this.MANDIBULA = this.cabea.getChild("MANDIBULA");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Guaiba = partdefinition.addOrReplaceChild("Guaiba", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition corpo = Guaiba.addOrReplaceChild("corpo", CubeListBuilder.create(), PartPose.offset(0.0F, -11.5F, 0.0F));

        PartDefinition cube_r1 = corpo.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.0F, -8.0F, 7.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition rabo = corpo.addOrReplaceChild("rabo", CubeListBuilder.create(), PartPose.offset(0.0F, 11.5F, 0.0F));

        PartDefinition rabo1 = rabo.addOrReplaceChild("rabo1", CubeListBuilder.create().texOffs(6, 49).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, 9.5F, 0.0436F, 0.0F, 0.0F));

        PartDefinition rabo2 = rabo.addOrReplaceChild("rabo2", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 13.0F));

        PartDefinition cube_r2 = rabo2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 37).addBox(-1.5F, -1.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition rabo3 = rabo.addOrReplaceChild("rabo3", CubeListBuilder.create(), PartPose.offset(-0.01F, -13.5F, 17.0F));

        PartDefinition cube_r3 = rabo3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 41).addBox(-0.51F, -1.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.99F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition rabo4 = rabo.addOrReplaceChild("rabo4", CubeListBuilder.create(), PartPose.offset(0.0F, -14.1946F, 21.9673F));

        PartDefinition cube_r4 = rabo4.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(32, 37).addBox(-0.5F, -0.6522F, -2.5251F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.1946F, -0.9673F, 0.0436F, 0.0F, 0.0F));

        PartDefinition pernaE = Guaiba.addOrReplaceChild("pernaE", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition SUPERIOR = pernaE.addOrReplaceChild("SUPERIOR", CubeListBuilder.create(), PartPose.offset(3.5F, -10.5F, 3.0F));

        PartDefinition cube_r5 = SUPERIOR.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(24, 53).addBox(-1.0F, -3.0F, -2.5F, 2.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition INFERIOR = pernaE.addOrReplaceChild("INFERIOR", CubeListBuilder.create(), PartPose.offset(3.499F, -5.9511F, 3.9306F));

        PartDefinition cube_r6 = INFERIOR.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 8).addBox(-1.001F, -2.5F, 0.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.001F, 0.4511F, -1.4306F, 0.3054F, 0.0F, 0.0F));

        PartDefinition PE = pernaE.addOrReplaceChild("PE", CubeListBuilder.create(), PartPose.offset(3.5F, -2.0F, 3.5F));

        PartDefinition cube_r7 = PE.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(56, 19).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 1.5F, -0.2618F, 0.0F, 0.0F));

        PartDefinition DEDO = pernaE.addOrReplaceChild("DEDO", CubeListBuilder.create().texOffs(55, 55).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.49F, -0.5F, 2.0F));

        PartDefinition pernaD = Guaiba.addOrReplaceChild("pernaD", CubeListBuilder.create(), PartPose.offset(-7.0F, 0.5F, 0.0F));

        PartDefinition SUPERIORD = pernaD.addOrReplaceChild("SUPERIORD", CubeListBuilder.create(), PartPose.offset(3.5F, -11.0F, 3.0F));

        PartDefinition cube_r8 = SUPERIORD.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(24, 53).addBox(-1.0F, -3.0F, -2.5F, 2.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition INFERIORD = pernaD.addOrReplaceChild("INFERIORD", CubeListBuilder.create(), PartPose.offset(3.499F, -6.4511F, 3.9306F));

        PartDefinition cube_r9 = INFERIORD.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 8).addBox(-1.001F, -2.5F, 0.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.001F, 0.4511F, -1.4306F, 0.3054F, 0.0F, 0.0F));

        PartDefinition PED = pernaD.addOrReplaceChild("PED", CubeListBuilder.create(), PartPose.offset(3.5F, -2.5F, 3.5F));

        PartDefinition cube_r10 = PED.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(56, 19).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5941F, 1.4186F, -0.2618F, 0.0F, 0.0F));

        PartDefinition DEDOD = pernaD.addOrReplaceChild("DEDOD", CubeListBuilder.create().texOffs(55, 55).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.49F, -1.0F, 2.0F));

        PartDefinition bracicoE = Guaiba.addOrReplaceChild("bracicoE", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition SUPERIORBRACICOESQ = bracicoE.addOrReplaceChild("SUPERIORBRACICOESQ", CubeListBuilder.create(), PartPose.offset(4.0F, -11.6735F, -5.3728F));

        PartDefinition cube_r11 = SUPERIORBRACICOESQ.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(42, 57).addBox(-0.5F, -2.0F, -1.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition INFERIORBRACICOESQ = bracicoE.addOrReplaceChild("INFERIORBRACICOESQ", CubeListBuilder.create(), PartPose.offset(3.99F, -8.5F, -4.5F));

        PartDefinition cube_r12 = INFERIORBRACICOESQ.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(58, 47).addBox(-0.51F, -2.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.01F, 0.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition bracicoD = Guaiba.addOrReplaceChild("bracicoD", CubeListBuilder.create(), PartPose.offset(-8.0F, 1.0F, 0.0F));

        PartDefinition SUPERIORBRACICODIR = bracicoD.addOrReplaceChild("SUPERIORBRACICODIR", CubeListBuilder.create(), PartPose.offset(4.0F, -11.6735F, -5.3728F));

        PartDefinition cube_r13 = SUPERIORBRACICODIR.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(42, 57).addBox(-0.5F, -2.0F, -1.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition INFERIORBRACICODIR = bracicoD.addOrReplaceChild("INFERIORBRACICODIR", CubeListBuilder.create(), PartPose.offset(3.99F, -8.5F, -4.5F));

        PartDefinition cube_r14 = INFERIORBRACICODIR.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(58, 33).addBox(-0.51F, -2.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.01F, 0.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition pescocito = Guaiba.addOrReplaceChild("pescocito", CubeListBuilder.create(), PartPose.offset(0.0F, -14.1489F, -14.2952F));

        PartDefinition cube_r15 = pescocito.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(1, 54).addBox(0.0F, -2.3262F, -1.9492F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.7111F, -1.305F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r16 = pescocito.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(33, 1).mirror().addBox(-2.5F, -1.0F, -7.0F, 4.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 1.2889F, 3.695F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cabea = Guaiba.addOrReplaceChild("cabea", CubeListBuilder.create(), PartPose.offset(0.0F, -15.5F, -18.0F));

        PartDefinition SUP = cabea.addOrReplaceChild("SUP", CubeListBuilder.create().texOffs(0, 22).addBox(-2.51F, -15.5F, -20.5F, 5.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(14, 45).addBox(-2.51F, -12.5F, -15.5F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 54).addBox(0.0F, -18.5F, -19.5F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.5218F, 13.4995F));

        PartDefinition MANDIBULA = cabea.addOrReplaceChild("MANDIBULA", CubeListBuilder.create().texOffs(26, 31).addBox(-2.5F, 0.1667F, -2.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.5F, -1.3333F, -1.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -1.3333F, -1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.8551F, -5.0005F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animateWalk(GuaibasurAnimationDefinitions.GUAIBAWALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(((GuaibasaurEntity) entity).idleAnimationState, GuaibasurAnimationDefinitions.GUAIBAIDLE, ageInTicks, 3f);
        this.animate(((GuaibasaurEntity) entity).runAnimationState, GuaibasurAnimationDefinitions.GUAIBARUN, ageInTicks, 4f);
        this.animate(((GuaibasaurEntity) entity).wakingUpAnimationState, GuaibasurAnimationDefinitions.GUAIBAWAKINGKUP, ageInTicks, 4f);
        this.animate(((GuaibasaurEntity) entity).sleepAnimationState, GuaibasurAnimationDefinitions.GUAIBASLEEP, ageInTicks, 4f);
        this.animate(((GuaibasaurEntity) entity).sleepingAnimationState, GuaibasurAnimationDefinitions.GUAIBASLEEPING, ageInTicks, 4f);
    }

    private void applyHeadRotation (float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.cabea.yRot = pNetHeadYaw * ((float)Math.PI/ 180F);
        this.cabea.xRot = pHeadPitch * ((float)Math.PI/ 180F);

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Guaiba.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }


    @Override
    public ModelPart root() {
        return Guaiba;
    }
}