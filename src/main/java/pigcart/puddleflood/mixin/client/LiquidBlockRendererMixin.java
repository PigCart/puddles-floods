package pigcart.puddleflood.mixin.client;

import net.minecraft./*?>=26.1{*//*client.renderer.block*//*?}else{*/world.level/*?}*/.BlockAndTintGetter;
import net.minecraft.client.renderer.block./*?>=26.1{*//*FluidRenderer*//*?}else{*/LiquidBlockRenderer/*?}*/;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pigcart.puddleflood.PuddleFlood;

@Mixin(/*?>=26.1{*//*FluidRenderer*//*?}else{*/LiquidBlockRenderer/*?}*/.class)
public class LiquidBlockRendererMixin {

    @Inject(method = "getHeight(" +
            //? >=26.1 {
            /*"Lnet/minecraft/client/renderer/block/BlockAndTintGetter;" +
            *///?} else {
            "Lnet/minecraft/world/level/BlockAndTintGetter;" +
            //?}
            "Lnet/minecraft/world/level/material/Fluid;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)F",at = @At("HEAD"), cancellable = true)
    public void getHeight(BlockAndTintGetter level, Fluid fluid, BlockPos pos, BlockState blockState, FluidState fluidState, CallbackInfoReturnable<Float> cir) {
        if (level.getBlockState(pos.above()).is(PuddleFlood.PUDDLE_BLOCK)) {
            cir.setReturnValue(1.0F);
        }
    }
}
