package com.teamaurora.fruitful.common.block;

import co.eltrut.differentiate.core.util.GroupUtil;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class FollowSaplingBlock extends SaplingBlock {
    private final Item followItem;

    public FollowSaplingBlock(SaplingGenerator generator, Settings settings, Item followItem) {
        super(generator, settings);
        this.followItem = followItem;
    }

    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> items) {
        GroupUtil.fillItem(this.asItem(), this.followItem, group, items);
    }
}