package com.teamaurora.fruitful.client;

import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FruitfulClient implements ClientModInitializer {
    public void onInitializeClient() {
        registerBlockColors();
        setupRenderLayer();
    }

    public static void registerBlockColors() {
        FruitfulClient.registerBlockColor((x, world, pos, u) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), Arrays.asList(
                FruitfulBlocks.BUDDING_OAK_LEAVES,
                FruitfulBlocks.FLOWERING_OAK_LEAVES,
                FruitfulBlocks.BLOSSOMING_OAK_LEAVES,
                FruitfulBlocks.APPLE_OAK_LEAVES
        ));

        FruitfulClient.registerBlockItemColor((color, items) -> FoliageColors.getDefaultColor(), Arrays.asList(
                FruitfulBlocks.BUDDING_OAK_LEAVES,
                FruitfulBlocks.FLOWERING_OAK_LEAVES,
                FruitfulBlocks.BLOSSOMING_OAK_LEAVES,
                FruitfulBlocks.APPLE_OAK_LEAVES
        ));
    }

    public static void setupRenderLayer() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(),
                FruitfulBlocks.APPLE_OAK_LEAVES,
                FruitfulBlocks.FLOWERING_OAK_LEAVES,
                FruitfulBlocks.BUDDING_OAK_LEAVES,
                FruitfulBlocks.BLOSSOMING_OAK_LEAVES,
                FruitfulBlocks.FLOWERING_OAK_SAPLING,
                FruitfulBlocks.POTTED_FLOWERING_OAK_SAPLING
        );
    }

    public static void registerBlockColor(BlockColorProvider color, List<Block> blocksIn) {
        blocksIn.removeIf(Objects::isNull);
        if (blocksIn.size() > 0) {
            Block[] blocks = new Block[blocksIn.size()];
            for (int i = 0; i < blocksIn.size(); i++) {
                blocks[i] = blocksIn.get(i);
            }
            ColorProviderRegistry.BLOCK.register(color, blocks);
        }
    }

    public static void registerBlockItemColor(ItemColorProvider color, List<Block> blocksIn) {
        blocksIn.removeIf(Objects::isNull);
        if (blocksIn.size() > 0) {
            Block[] blocks = new Block[blocksIn.size()];

            for(int i = 0; i < blocksIn.size(); ++i) {
                blocks[i] = blocksIn.get(i);
            }

            ColorProviderRegistry.ITEM.register(color, blocks);
        }
    }
}