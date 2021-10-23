package com.teamaurora.fruitful.client;

import co.eltrut.differentiate.core.util.DataUtil;
import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;

public class FruitfulClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Block Colors //
        DataUtil.registerBlockColor((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(),
                FruitfulBlocks.BUDDING_OAK_LEAVES,
                FruitfulBlocks.FLOWERING_OAK_LEAVES,
                FruitfulBlocks.BLOSSOMING_OAK_LEAVES,
                FruitfulBlocks.APPLE_OAK_LEAVES
        );

        // Item Colors //
        DataUtil.registerItemColor((color, items) -> FoliageColors.getDefaultColor(),
                FruitfulBlocks.BUDDING_OAK_LEAVES,
                FruitfulBlocks.FLOWERING_OAK_LEAVES,
                FruitfulBlocks.BLOSSOMING_OAK_LEAVES,
                FruitfulBlocks.APPLE_OAK_LEAVES
        );

        // Render Layers //
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(),
                FruitfulBlocks.APPLE_OAK_LEAVES,
                FruitfulBlocks.FLOWERING_OAK_LEAVES,
                FruitfulBlocks.BUDDING_OAK_LEAVES,
                FruitfulBlocks.BLOSSOMING_OAK_LEAVES,
                FruitfulBlocks.FLOWERING_OAK_SAPLING,
                FruitfulBlocks.POTTED_FLOWERING_OAK_SAPLING
        );
    }
}