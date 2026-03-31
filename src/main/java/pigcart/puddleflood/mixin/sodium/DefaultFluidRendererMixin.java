package pigcart.puddleflood.mixin.sodium;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
//~ if >=26.1 'world.level' -> 'client.renderer.block'
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pigcart.puddleflood.PuddleFlood;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline./*?>=1.21.1{*//*DefaultFluidRenderer*//*?}else{*/FluidRenderer/*?}*/;


@Mixin(/*? >=1.21.1 {*//*DefaultFluidRenderer*//*?}else{*/FluidRenderer/*?}*/.class)
public abstract class DefaultFluidRendererMixin {
    
    @Inject(method = /*?<1.21.11{*/"fluidHeight"
            /*?}else if <26.1{*//*"sampleFluidHeight(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/material/Fluid;Lnet/minecraft/core/BlockPos;)F"
            *//*?}else{*//*"sampleFluidHeight(Lnet/minecraft/client/renderer/block/BlockAndTintGetter;Lnet/minecraft/world/level/material/Fluid;Lnet/minecraft/core/BlockPos;)F"
            *//*?}*/, at = @At("HEAD"), cancellable = true, remap = false)
    public void fluidHeight(BlockAndTintGetter level,
                            Fluid fluid,
                            BlockPos pos,
                            /*?<1.21.11{*/Direction direction,/*?}*/
                            CallbackInfoReturnable<Float> cir
    ) {
        if (level.getBlockState(pos.above()).is(PuddleFlood.PUDDLE_BLOCK)) {
            cir.setReturnValue(1.0F);
        }
    }

    //? >=1.21.11 {
    /*@Inject(method = "fluidCornerHeight", at = @At("HEAD"), cancellable = true, remap = false)
    public void fluidCornerHeight(BlockAndTintGetter level, BlockPos origin, Fluid fluid, float fluidHeight, Direction dirA, Direction dirB, float fluidHeightA, float fluidHeightB, boolean exposedA, boolean exposedB, CallbackInfoReturnable<Float> cir
    ) {
        if (level.getBlockState(origin.above().relative(dirA)).is(PuddleFlood.PUDDLE_BLOCK)) {
            cir.setReturnValue(1.0F);
        }
        if (level.getBlockState(origin.above().relative(dirB)).is(PuddleFlood.PUDDLE_BLOCK)) {
            cir.setReturnValue(1.0F);
        }
    }
    *///?}
}