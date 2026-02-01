package pigcart.puddleflood;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;
//? >=1.21.11 {
/*import net.minecraft.world.attribute.EnvironmentAttributes;
*///?}
//? >=1.21.9 {
/*import net.minecraft.data.AtlasIds;
import net.minecraft.world.phys.shapes.CollisionContext;
*///?}

import java.awt.Color;
import java.net.URI;
import java.util.stream.Stream;

public class VersionUtil {

    // FastColor removed in modern versions
    public static int getBlue(int packedColor) {
        return packedColor & 255;
    }

    public static boolean isModLoaded(String id) {
        //? fabric {
        return net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded(id);
        //?} forge {
        /*return net.minecraftforge.fml.ModList.get().isLoaded(id);
        *///?} neoforge {
        /*return net.neoforged.fml.ModList.get().isLoaded(id);
        *///?}
    }

    @SuppressWarnings("removal")
    public static ResourceLocation getId(String path) {
        //? if <=1.20.1 {
        return new ResourceLocation(PuddleFlood.MOD_ID, path);
        //?} else {
        /*return ResourceLocation.fromNamespaceAndPath(PuddleFlood.MOD_ID, path);
        *///?}
    }
    @SuppressWarnings("removal")
    public static ResourceLocation getMcId(String path) {
        //? if <=1.20.1 {
        return new ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, path);
        //?} else {
        /*return ResourceLocation.withDefaultNamespace(path);
        *///?}
    }

    public static Biome.Precipitation getPrecipitationAt(Level level, BlockPos blockPos) {
        return getPrecipitationAt(level, level.getBiome(blockPos), blockPos);
    }
    public static Biome.Precipitation getPrecipitationAt(Level level, Holder<Biome> biome, BlockPos blockPos) {
        //? if >=1.21.4 {
        /*return biome.value().getPrecipitationAt(blockPos, level.getSeaLevel());
         *///?} else {
        return biome.value().getPrecipitationAt(blockPos);
        //?}
    }

    public static void schedule(Runnable task) {
        //? if >=1.21.4 {
        /*Minecraft.getInstance().schedule(task);
         *///?} else {
        Minecraft.getInstance().tell(task);
        //?}
    }

    public static <T> Stream<TagKey<T>> getTagIds(Registry<T> registry) {
        //? if >=1.21.4 {
        /*return registry.listTagIds();
        *///?} else {
        return registry.getTagNames();
        //?}
    }

    public static <T> Registry<T> getRegistry(ResourceKey<Registry<T>> key) {
        //? if >=1.21.4 {
        /*return Minecraft.getInstance().level.registryAccess().lookupOrThrow(key);
        *///?} else {
        return Minecraft.getInstance().level.registryAccess().registryOrThrow(key);
        //?}
    }

    public static int getPixel(NativeImage img, int x, int y) {
        //? >=1.21.9 {
        /*return img.getPixel(x, y);
        *///?} else {
        return img.getPixelRGBA(x, y);
        //?}
    }

    public static void openUri(URI uri) {
        //? >=1.21.11 {
        /*net.minecraft.util.Util.getPlatform().openUri(uri);
        *///?} else {
        net.minecraft.Util.getPlatform().openUri(uri);
        //?}
    }

    public static Vec3 camPos(Camera cam) {
        //? >=1.21.11 {
        /*return cam.position();
        *///?} else {
        return cam.getPosition();
        //?}
    }

    public static ResourceLocation getKeyId(ResourceKey key) {
        //? >=1.21.11 {
        /*return key.identifier();
        *///?} else {
        return key.location();
         //?}
    }
}
