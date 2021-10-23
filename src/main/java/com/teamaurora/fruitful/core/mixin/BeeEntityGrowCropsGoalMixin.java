package com.teamaurora.fruitful.core.mixin;

import com.teamaurora.fruitful.common.block.OakBlossomBlock;
import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeeEntity.GrowCropsGoal.class)
public abstract class BeeEntityGrowCropsGoalMixin {
    @Dynamic(value = "This is from a Fruitful mixin :0")
    private BeeEntity beeEntity;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void onConstructed(BeeEntity outer, CallbackInfo ci) {
        beeEntity = outer;
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void onTick(CallbackInfo ci) {
        if (beeEntity.getRandom().nextInt(15) == 0) {
            var blockpos$mutable = new BlockPos.Mutable();
            for (var x = -1; x <= 1; x++) {
                for (var y = -1; y <= 1; y++) {
                    for (var z = -1; z <= 1; z++) {
                        if (Math.abs(x) != 1 || Math.abs(y) != 1 || Math.abs(z) != 1) {
                            blockpos$mutable.set(beeEntity.getBlockPos().add(x, y, z));
                            var blockstate = beeEntity.world.getBlockState(blockpos$mutable);
                            var block = blockstate.getBlock();

                            if (block == FruitfulBlocks.BLOSSOMING_OAK_LEAVES && !blockstate.get(LeavesBlock.PERSISTENT) && !blockstate.get(OakBlossomBlock.POLLINATED)) {
                                beeEntity.world.syncWorldEvent(2005, blockpos$mutable, 0);
                                beeEntity.world.setBlockState(blockpos$mutable, blockstate.with(OakBlossomBlock.POLLINATED, true));
                                beeEntity.addCropCounter();
                            }
                        }
                    }
                }
            }
        }
    }
}