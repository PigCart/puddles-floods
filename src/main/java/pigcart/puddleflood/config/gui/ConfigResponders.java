package pigcart.puddleflood.config.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.text.NumberFormat;
import java.util.function.Function;

/// contains functions that are instantiated and run in response to config screen interactions
public class ConfigResponders {

    public static class Percent implements Function<Object, Component> {
        public Component apply(Object value) {
            return Component.literal(NumberFormat.getPercentInstance().format(value));
        }
    }

    public static class PercentOrOff implements Function<Object, Component> {
        public Component apply(Object value) {
            return ((Number)value).floatValue() == 0 ? CommonComponents.OPTION_OFF.copy().withStyle(ChatFormatting.RED) : Component.literal(NumberFormat.getPercentInstance().format(value));
        }
    }

    public static class ZeroIsAutomatic implements Function<Object, Component> {
        public Component apply(Object stringValue) {
            final int value = Integer.parseInt((String) stringValue);
            return value == 0 ? Component.translatable("puddleflood.auto") : Component.literal((String) stringValue);
        }
    }

    public static class ReloadResources implements Runnable {
        public void run() {
            Minecraft.getInstance().reloadResourcePacks();
        }
    }

    public static class ReloadChunks implements Runnable {
        public void run() {
            Minecraft.getInstance().levelRenderer.allChanged();
        }
    }

    public static class RefreshScreen implements Runnable {
        public void run() {
            ((ConfigScreen)Minecraft.getInstance().screen).refresh();
        }
    }

    public static class ClientHasAuthority implements Function<Object, Boolean> {
        public Boolean apply(Object configContext) {
            if (Minecraft.getInstance().level == null) return true;
            return Minecraft.getInstance().getSingleplayerServer() != null;
        }
    }
}
