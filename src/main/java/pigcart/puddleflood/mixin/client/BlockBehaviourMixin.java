package pigcart.puddleflood.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import pigcart.puddleflood.duck.BlockTickDuck;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin implements BlockTickDuck {
    @Override
    public void puddleflood_clientRandomTick(ClientLevel level, BlockPos pos) {

    }
}
