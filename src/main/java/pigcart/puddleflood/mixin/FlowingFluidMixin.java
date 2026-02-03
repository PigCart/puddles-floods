package pigcart.puddleflood.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pigcart.puddleflood.BlockPlacementUtil;
import pigcart.puddleflood.PuddleFlood;
import pigcart.puddleflood.config.ConfigManager;
//?>=1.21.9{
/*import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
*///?} else {
import net.minecraft.world.level.Level;
//?}

@Mixin(FlowingFluid.class)
public abstract class FlowingFluidMixin {

    @Inject(method = "spread", at = @At("HEAD"))
    protected void spread(
            /*?>=1.21.9{*/ /*ServerLevel level*//*?}else{*/Level level/*?}*/,
            BlockPos pos,
            /*?>=1.21.9{*/ /*BlockState blockState,*//*?}*/
            FluidState fluidState,
            CallbackInfo ci
    ) {
        if (ConfigManager.config.doFlowingWaterPuddles && level.random.nextBoolean() && BlockPlacementUtil.isUnobstructedFlatSurface(level, pos)) {
            level.setBlockAndUpdate(pos, PuddleFlood.PUDDLE_BLOCK.getStateAt(pos, level));
        }
    }
}
