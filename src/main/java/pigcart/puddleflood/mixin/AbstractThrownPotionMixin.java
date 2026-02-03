package pigcart.puddleflood.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
//? >=1.21.11 {
/*import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
*///?} else if >=1.21.9 {
/*import net.minecraft.world.entity.projectile.AbstractThrownPotion;
*///?} else {
import net.minecraft.world.entity.projectile.ThrownPotion;
//?}
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pigcart.puddleflood.BlockPlacementUtil;
import pigcart.puddleflood.PuddleFlood;
import pigcart.puddleflood.config.ConfigManager;

@Mixin(/*?>=1.21.9{*//*AbstractThrownPotion.class*//*?}else{*/ThrownPotion .class/*?}*/)
public abstract class AbstractThrownPotionMixin {

    @Inject(method = "onHitBlock", at = @At("HEAD"))
    public void onHitBlock(BlockHitResult result, CallbackInfo ci) {
        if (ConfigManager.config.doPotionPuddles && result.getDirection().equals(Direction.UP)) {
            final BlockPos pos = result.getBlockPos().above();
            Level level = ((Entity) (Object) this).level();
            if (BlockPlacementUtil.isSolidSurface(level, pos.below()) && level.getBlockState(pos).isAir()) {
                level.setBlockAndUpdate(pos, PuddleFlood.PUDDLE_BLOCK.getStateAt(pos, level));
            }
        }
    }

}
