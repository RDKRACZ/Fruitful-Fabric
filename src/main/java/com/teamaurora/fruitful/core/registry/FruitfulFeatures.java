package com.teamaurora.fruitful.core.registry;

import com.google.common.collect.ImmutableList;
import com.teamaurora.fruitful.core.Fruitful;
import com.teamaurora.fruitful.core.config.FruitfulConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import org.apache.logging.log4j.Level;

import java.util.Arrays;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Predicate;

public class FruitfulFeatures {
    public static void registerFeatures(){
        FruitfulFeatures.Configured.registerConfiguredFeatures();
        FruitfulFeatures.registerModifications();
    }

    public static void registerModifications() {
        String[] flowerBiomes = FruitfulConfig.get().flowerBiomes.toString().replace("[", "").replace("]", "").replace(" ", "").split(",", FruitfulConfig.get().flowerBiomes.size());
        for (int flowerBiome = 0; flowerBiome < flowerBiomes.length; flowerBiome++) {
            Predicate<BiomeSelectionContext> biomeSelector = BiomeSelectors.includeByKey(RegistryKey.of(Registry.BIOME_KEY, new Identifier(flowerBiomes[flowerBiome])));
            BiomeModifications.addFeature(biomeSelector, GenerationStep.Feature.VEGETAL_DECORATION, Objects.requireNonNull(registryKey(Configured.FLOWERING_OAK_INFREQUENT)));
        }

        if (FruitfulConfig.get().flowerBiomes.toString().contains(BiomeKeys.FLOWER_FOREST.getValue().toString())){
            BiomeModifications.create(Fruitful.id("remove_flower_forest_trees"))
                    .add(ModificationPhase.REPLACEMENTS, BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), (c)-> {
                        if(c.getGenerationSettings().removeBuiltInFeature(ConfiguredFeatures.FOREST_FLOWER_TREES))
                        {
                            c.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, registryKey(Configured.FOREST_FLOWER_TREES));
                        }
                    });
        }
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registryKey(ConfiguredFeature<?, ?> carver)
    {
        if (BuiltinRegistries.CONFIGURED_FEATURE.getKey(carver).isPresent()) {
            return BuiltinRegistries.CONFIGURED_FEATURE.getKey(carver).get();
        } else {
            Fruitful.LOGGER.log(Level.INFO, "Something Went Wrong Registering Carver!");
            return null;
        }
    }

    public static final class BlockStates {
        public static final BlockState OAK_LOG = Blocks.OAK_LOG.getDefaultState();
        public static final BlockState FLOWERING_OAK_LEAVES = FruitfulBlocks.FLOWERING_OAK_LEAVES.getDefaultState();
        public static final BlockState BUDDING_OAK_LEAVES = FruitfulBlocks.BUDDING_OAK_LEAVES.getDefaultState();
    }

    public static final class Configs {
        public static final TreeFeatureConfig FLOWERING_OAK = (new TreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(BlockStates.OAK_LOG),
                new WeightedBlockStateProvider().addState(BlockStates.BUDDING_OAK_LEAVES, 2).addState(BlockStates.FLOWERING_OAK_LEAVES, 1),
                new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3),
                new StraightTrunkPlacer(4, 2, 0),
                new TwoLayersFeatureSize(1, 0, 1))
        ).ignoreVines().build();
        public static final TreeFeatureConfig FLOWERING_FANCY_OAK = (new TreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(BlockStates.OAK_LOG),
                new WeightedBlockStateProvider().addState(BlockStates.BUDDING_OAK_LEAVES, 2).addState(BlockStates.FLOWERING_OAK_LEAVES, 1),
                new LargeOakFoliagePlacer(UniformIntDistribution.of(2),
                UniformIntDistribution.of(4), 4), new LargeOakTrunkPlacer(3, 11, 0),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
        ).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build();
    }

    public static final class Configured {
        public static final ConfiguredFeature<TreeFeatureConfig, ?> FLOWERING_OAK = Feature.TREE.configure(Configs.FLOWERING_OAK);
        public static final ConfiguredFeature<TreeFeatureConfig, ?> FLOWERING_FANCY_OAK = Feature.TREE.configure(Configs.FLOWERING_FANCY_OAK);
        public static final ConfiguredFeature<TreeFeatureConfig, ?> FLOWERING_OAK_BEES_005 = Feature.TREE.configure(Configs.FLOWERING_OAK.setTreeDecorators(ImmutableList.of(ConfiguredFeatures.Decorators.MORE_BEEHIVES_TREES)));
        public static final ConfiguredFeature<TreeFeatureConfig, ?> FLOWERING_FANCY_OAK_BEES_005 = Feature.TREE.configure(Configs.FLOWERING_FANCY_OAK.setTreeDecorators(ImmutableList.of(ConfiguredFeatures.Decorators.MORE_BEEHIVES_TREES)));
        public static final ConfiguredFeature<TreeFeatureConfig, ?> FLOWERING_OAK_BEES_002 = Feature.TREE.configure(Configs.FLOWERING_OAK.setTreeDecorators(ImmutableList.of(ConfiguredFeatures.Decorators.REGULAR_BEEHIVES_TREES)));
        public static final ConfiguredFeature<TreeFeatureConfig, ?> FLOWERING_FANCY_OAK_BEES_002 = Feature.TREE.configure(Configs.FLOWERING_FANCY_OAK.setTreeDecorators(ImmutableList.of(ConfiguredFeatures.Decorators.REGULAR_BEEHIVES_TREES)));

        public static final ConfiguredFeature<?, ?> FLOWERING_OAK_INFREQUENT = FLOWERING_OAK.decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.12F, 3)));
        public static final ConfiguredFeature<?, ?> FOREST_FLOWER_TREES = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(ConfiguredFeatures.BIRCH_BEES_002.withChance(0.2F), FLOWERING_FANCY_OAK_BEES_002.withChance(0.05F), ConfiguredFeatures.FANCY_OAK_BEES_002.withChance(0.053F), ConfiguredFeatures.OAK_BEES_002.withChance(0.5F)), FLOWERING_OAK_BEES_002)).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(6, 0.1F, 1)));

        private static <FC extends FeatureConfig> void register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Fruitful.MODID, name), configuredFeature);
        }

        public static void registerConfiguredFeatures() {
            register("flowering_oak", FLOWERING_OAK);
            register("flowering_fancy_oak", FLOWERING_FANCY_OAK);
            register("flowering_oak_bees_005", FLOWERING_OAK_BEES_005);
            register("flowering_fancy_oak_bees_005", FLOWERING_FANCY_OAK_BEES_005);
            register("flowering_oak_bees_002", FLOWERING_OAK_BEES_002);
            register("flowering_fancy_oak_bees_002", FLOWERING_FANCY_OAK_BEES_002);

            register("flowering_oak_infrequent", FLOWERING_OAK_INFREQUENT);
            register("forest_flower_trees", FOREST_FLOWER_TREES);
        }
    }
}