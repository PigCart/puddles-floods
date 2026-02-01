//? <26.1 {
package pigcart.puddleflood.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
//?>=1.21.9{
/*import net.minecraft.client.multiplayer.ClientLevel;
*///?} else {
import net.minecraft.client.particle.ParticleEngine;
//?}

@Mixin(/*?>=1.21.9{*//*ClientLevel*//*?} else {*/ParticleEngine/*?}*/.class)
public class BreakingBlockEffectMixin {

    @WrapMethod(method = /*?>=1.21.9{*//*"addBreakingBlockEffect"*//*?} else {*/"crack"/*?}*/)
    public void catchUnsupportedBounds(BlockPos pos, Direction side, Operation<Void> original) {
        try {
            original.call(pos, side);
        } catch (UnsupportedOperationException ignored) {

        }
    }
}
//?}
