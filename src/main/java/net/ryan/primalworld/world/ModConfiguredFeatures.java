package net.ryan.primalworld.world;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.ryan.primalworld.block.ModBlocks;
import net.ryan.primalworld.primalworld;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMBER_ORE_KEY = registerKey("amber_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FOSSIL_KEY = registerKey("fossil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIFFERENT_FOSSIL_KEY = registerKey("different_fossil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TITANIUM_ORE_KEY = registerKey("titanium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BROWN_SEAGRASS_KEY = registerKey("brown_seagrass"); // Mudado para BROWN_SEAGRASS



    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {

        RuleTest stoneReplaceabeles = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceabeles = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest dioriteReplaceables = new BlockMatchTest(Blocks.DIORITE);
        RuleTest graniteReplaceables = new BlockMatchTest(Blocks.GRANITE);
        RuleTest andesiteReplaceables = new BlockMatchTest(Blocks.ANDESITE);



        List<OreConfiguration.TargetBlockState> amberOre = List.of(OreConfiguration.target(stoneReplaceabeles,
                        ModBlocks.AMBER_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceabeles, ModBlocks.DEEPSLATE_AMBER_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> fossilOre = List.of(
                OreConfiguration.target(stoneReplaceabeles, ModBlocks.FOSSIL_BLOCK.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceabeles, ModBlocks.DEEPSLATE_FOSSIL_BLOCK.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> fossilDifferent = List.of(
                OreConfiguration.target(dioriteReplaceables, ModBlocks.DIORITE_FOSSIL_BLOCK.get().defaultBlockState()),
                OreConfiguration.target(graniteReplaceables, ModBlocks.GRANITE_FOSSIL_BLOCK.get().defaultBlockState()),
                OreConfiguration.target(andesiteReplaceables, ModBlocks.ANDESITE_FOSSIL_BLOCK.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> titaniumOre = List.of(
                OreConfiguration.target(deepslateReplaceabeles, ModBlocks.DEEPSLATE_TITANIUM_ORE.get().defaultBlockState()));

        register(context, AMBER_ORE_KEY, Feature.ORE, new OreConfiguration(amberOre, 5));
        register(context, FOSSIL_KEY, Feature.ORE, new OreConfiguration(fossilOre, 9));
        register(context, DIFFERENT_FOSSIL_KEY, Feature.ORE, new OreConfiguration(fossilDifferent, 10));
        register(context, TITANIUM_ORE_KEY, Feature.ORE, new OreConfiguration(titaniumOre, 2));



        register(context, BROWN_SEAGRASS_KEY, Feature.SEAGRASS,
                new ProbabilityFeatureConfiguration(0.8F)); // Define a probabilidade conforme necessário
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(primalworld.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}