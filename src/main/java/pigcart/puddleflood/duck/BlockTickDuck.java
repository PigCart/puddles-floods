package pigcart.puddleflood.duck;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface BlockTickDuck {
    void puddleflood_clientRandomTick(Level level, BlockPos pos);
}
