package pigcart.puddleflood.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.WaterFluid;
import org.spongepowered.asm.mixin.Mixin;
import pigcart.puddleflood.BlockPlacementUtil;
import pigcart.puddleflood.PuddleFlood;
import pigcart.puddleflood.config.ConfigManager;

@Mixin(WaterFluid.class)
public abstract class WaterFluidMixin extends FlowingFluidMixin {

    @Override
    public void placePuddle(Level level, BlockPos pos) {
        if (ConfigManager.config.doFlowingWaterPuddles
                && level.random.nextBoolean()
                && BlockPlacementUtil.isUnobstructedFlatSurface(level, pos)
        ) {
            level.setBlockAndUpdate(pos, PuddleFlood.PUDDLE_BLOCK.getStateAt(pos, level));
        }
    }
}
