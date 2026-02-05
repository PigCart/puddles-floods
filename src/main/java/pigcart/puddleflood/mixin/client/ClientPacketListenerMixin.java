package pigcart.puddleflood.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pigcart.puddleflood.PuddleFlood;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Inject(method = "handleBlockUpdate", at = @At("HEAD"), cancellable = true)
    public void suppressPuddleRemoval(ClientboundBlockUpdatePacket packet, CallbackInfo ci) {
        if (packet.getBlockState().isAir()
                && Minecraft.getInstance().level.getBlockState(packet.getPos()).is(PuddleFlood.PUDDLE_BLOCK)
        ) {
            ci.cancel();
        }
    }
}
