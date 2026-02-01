package pigcart.puddleflood.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pigcart.puddleflood.BlockPlacementUtil;
import pigcart.puddleflood.PuddleFlood;

@Mixin(DripParticle.class)
public abstract class DripParticleMixin extends SingleQuadParticle {

    @Shadow @Final private Fluid type;

    protected DripParticleMixin(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
        super(level, x, y, z /*?>=1.21.9{*//*,sprite*//*?}*/);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/DripParticle;postMoveUpdate()V"))
    public void tickAddPuddlePostMove(CallbackInfo ci) {
        if (this.onGround && this.type.isSame(Fluids.WATER) && this.level.random.nextBoolean()) {
            BlockPos pos = BlockPos.containing(x, y, z);
            if (BlockPlacementUtil.isUnobstructedFlatSurface(this.level, pos)) {
                this.level.setBlock(pos, PuddleFlood.PUDDLE_BLOCK.getStateAt(pos, this.level), 0);
            }
        }
    }
}
