package pigcart.puddleflood.duck;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;

public interface BlockTickDuck {
    void puddleflood_clientRandomTick(ClientLevel level, BlockPos pos);
}
