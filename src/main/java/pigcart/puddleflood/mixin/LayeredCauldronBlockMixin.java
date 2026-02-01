package pigcart.puddleflood.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pigcart.puddleflood.BlockPlacementUtil;
import pigcart.puddleflood.PuddleFlood;

@Mixin(LayeredCauldronBlock.class)
public abstract class LayeredCauldronBlockMixin extends BlockMixin {
    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (BlockPlacementUtil.isSolidSurface(level, pos.below())) {
            level.setBlock(pos, PuddleFlood.PUDDLE_BLOCK.getStateAt(pos, level), 0);
        }
    }
}
