package pigcart.puddleflood.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import pigcart.puddleflood.duck.BlockTickDuck;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin implements BlockTickDuck {

    @Shadow
    public abstract Block getBlock();

    @Override
    public void puddleflood_clientRandomTick(ClientLevel level, BlockPos pos) {
        ((BlockTickDuck)this.getBlock()).puddleflood_clientRandomTick(level, pos);
    }
}
