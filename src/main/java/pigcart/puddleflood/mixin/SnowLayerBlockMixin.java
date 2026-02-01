package pigcart.puddleflood.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.SnowLayerBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pigcart.puddleflood.BlockPlacementUtil;
import pigcart.puddleflood.PuddleFlood;

@Mixin(SnowLayerBlock.class)
public abstract class SnowLayerBlockMixin {

    @WrapOperation(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"))
    public boolean removeBlockOrPlacePuddle(ServerLevel level, BlockPos blockPos, boolean movedByPiston, Operation<Boolean> original) {
        if (level.random.nextBoolean()) return original.call(level, blockPos, movedByPiston);
        if (BlockPlacementUtil.isFlatSurface(level, blockPos)) {
            level.setBlockAndUpdate(blockPos, PuddleFlood.PUDDLE_BLOCK.getStateAt(blockPos, level));
        }
        return false;
    }

}
