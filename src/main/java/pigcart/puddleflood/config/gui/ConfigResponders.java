package pigcart.puddleflood.config.gui;

import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.util.function.Function;

import static pigcart.puddleflood.VersionUtil.shadersEnabled;

/// contains functions that are instantiated and run in response to config screen interactions
public class ConfigResponders {

    public static class ReloadShaders implements Runnable {
        public void run() {
            if (shadersEnabled()) {
                try {
                    net.irisshaders.iris.Iris.reload();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static class ClientHasAuthority implements Function<Object, Boolean> {
        public Boolean apply(Object configContext) {
            if (Minecraft.getInstance().level == null) return true;
            return Minecraft.getInstance().getSingleplayerServer() != null;
        }
    }

    public static class ShadersEnabled implements Function<Object, Boolean> {
        public Boolean apply(Object configContext) {
            return shadersEnabled();
        }
    }
}
