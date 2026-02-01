package pigcart.puddleflood.mixin.client;

import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pigcart.puddleflood.PuddleFlood;

@Mixin(LiquidBlockRenderer.class)
public class LiquidBlockRendererMixin {

    @Inject(method = "getHeight(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/material/Fluid;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)F", at = @At("HEAD"), cancellable = true)
    public void getHeight(BlockAndTintGetter level, Fluid fluid, BlockPos pos, BlockState blockState, FluidState fluidState, CallbackInfoReturnable<Float> cir) {
        if (level.getBlockState(pos.above()).is(PuddleFlood.PUDDLE_BLOCK)) {
            cir.setReturnValue(1.0F);
        }
    }

    /*@Inject(method = "isFaceOccludedByState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/shapes/Shapes;empty()Lnet/minecraft/world/phys/shapes/VoxelShape;"), cancellable = true)
    private static void isFaceOccludedByPuddle(Direction direction, float height, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (direction == Direction.UP && state.is(PuddleFlood.PUDDLE_BLOCK)) {
            cir.setReturnValue(true);
        }
    }*/
}
