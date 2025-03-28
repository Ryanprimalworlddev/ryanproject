package net.ryan.primalworld.world;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.ryan.primalworld.primalworld;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_AMBER_ORE = registerKey("add_amber_ore");
    public static final ResourceKey<BiomeModifier> ADD_FOSSIL = registerKey("add_fossil");
    public static final ResourceKey<BiomeModifier> ADD_DIFFERENT_FOSSIL = registerKey("add_different_fossil");
    public static final ResourceKey<BiomeModifier> ADD_TITANIUM = registerKey("add_titanium");
    public static final ResourceKey<BiomeModifier> ADD_BROWN_SEAGRASS = registerKey("add_brown_seagrass"); // Mudado para BROWN_SEAGRASS

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_AMBER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.AMBER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_FOSSIL, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.FOSSIL_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_DIFFERENT_FOSSIL, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.DIFFERENT_FOSSIL_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_TITANIUM, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.TITANIUM_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_BROWN_SEAGRASS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier( // Mudado para BROWN_SEAGRASS
                biomes.getOrThrow(BiomeTags.IS_OCEAN),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.BROWN_SEAGRASS_PLACED_KEY)), // Mudado para BROWN_SEAGRASS
                GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(primalworld.MOD_ID, name));
    }
}