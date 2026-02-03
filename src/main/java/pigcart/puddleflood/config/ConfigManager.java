package pigcart.puddleflood.config;

import com.google.gson.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import pigcart.puddleflood.PuddleFlood;
import pigcart.puddleflood.config.gui.ConfigScreen;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.function.Function;

public class ConfigManager {
    static final Gson GSON = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(Color.class, new ColorTypeAdapter())
            .create();
    static final String CONFIG_PATH = "config/" + PuddleFlood.MOD_ID + ".json";
    public static ConfigData config = new ConfigData();

    public static Screen screenPlease(Screen lastScreen) {
        return new ConfigScreen(lastScreen, config, getDefaultConfig(), Component.translatable("puddleflood.title"));
    }

    public static ConfigData getDefaultConfig() {
        return new ConfigData();
    }

    public static void load() {
        File file = new File(CONFIG_PATH);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                config = GSON.fromJson(reader, ConfigData.class);
            } catch (Exception e) {
                PuddleFlood.LOGGER.error("Error loading config: {}", e.getMessage());
                config = getDefaultConfig();
                save();
            }
        } else {
            PuddleFlood.LOGGER.info("Creating config file at " + CONFIG_PATH);
            config = getDefaultConfig();
            save();
        }
        if (config == null || config.configVersion < getDefaultConfig().configVersion) {
            PuddleFlood.LOGGER.info("Overwriting old config file");
            config = getDefaultConfig();
            save();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_PATH)) {
            GSON.toJson(config, writer);
        } catch (Exception e) {
            PuddleFlood.LOGGER.error(e.getMessage());
        }
    }

    public static class ColorTypeAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {
        public static Color getColor(String string) {
            return Color.decode(string);
        }
        public static String getString(Color color) {
            return String.join("",
                    "#",
                    String.format("%02X", color.getRed()),
                    String.format("%02X", color.getGreen()),
                    String.format("%02X", color.getBlue()));
        }

        @Override
        public JsonElement serialize(Color color, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(getString(color));
        }
        @Override
        public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return getColor(json.getAsString());
        }
    }

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