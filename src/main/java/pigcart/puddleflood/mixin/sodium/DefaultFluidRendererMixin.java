//? <26.1 {
package pigcart.puddleflood.mixin.sodium;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pigcart.puddleflood.PuddleFlood;
//? >=1.21.1 {
/*import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.DefaultFluidRenderer;
*///?} else {
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.FluidRenderer;
//?}

@Mixin(/*? >=1.21.1 {*//*DefaultFluidRenderer*//*?}else{*/FluidRenderer/*?}*/.class)
public abstract class DefaultFluidRendererMixin {

    @Inject(method = "fluidHeight", at = @At("HEAD"), cancellable = true, remap = false)
    public void fluidHeight(BlockAndTintGetter level, Fluid fluid, BlockPos pos, Direction direction, CallbackInfoReturnable<Float> cir) {
        if (level.getBlockState(pos.above()).is(PuddleFlood.PUDDLE_BLOCK)) {
            cir.setReturnValue(1.0F);
        }
    }
}
//?}