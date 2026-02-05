package pigcart.puddleflood;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import pigcart.puddleflood.duck.BlockTickDuck;

import java.io.IOException;
import java.io.InputStream;

import static pigcart.puddleflood.PuddleFlood.PUDDLE_BLOCK;
import static pigcart.puddleflood.config.ConfigManager.config;

public class ClientBlockPlacementUtil extends BlockPlacementUtil {

    static NativeImage puddleMap;
    static {
        try {
            puddleMap = loadTexture(VersionUtil.getId("textures/puddles.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

    public static void tick(Level level) {
        if (level.isRaining()) {
            for (int i = 0; i < config.collectionSpeed; i++) {
                final BlockState state = getRandomHeightmapPos(level, pos);
                if (!state.is(PUDDLE_BLOCK) && VersionUtil.getPrecipitationAt(level, pos) == Biome.Precipitation.RAIN) {
                    if (hasRainPuddle(level, pos) || canFlood(level, pos)) {
                        level.setBlock(pos, PUDDLE_BLOCK.getStateAt(pos, level), 0);
                    }
                }
            }
        } else {
            for (int i = 0; i < config.evaporationSpeed; i++) {
                ((BlockTickDuck) getRandomHeightmapPos(level, pos)).puddleflood_clientRandomTick(level, pos);
                ((BlockTickDuck) getRandomPosition(level, pos, 32)).puddleflood_clientRandomTick(level, pos);
            }
        }
    }

    public static BlockState getRandomHeightmapPos(Level level, BlockPos.MutableBlockPos destination) {
        Position cameraPos = net.minecraft.client.Minecraft.getInstance().player.position();
        int x = (int) level.random.triangle(cameraPos.x(), config.range);
        int z = (int) level.random.triangle(cameraPos.z(), config.range);
        int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
        destination.set(x, y, z);
        return level.getBlockState(destination);
    }

    public static BlockState getRandomPosition(Level level, BlockPos.MutableBlockPos destination, int range) {
        Position cameraPos = net.minecraft.client.Minecraft.getInstance().player.position();
        int x = (int) level.random.triangle(cameraPos.x(), range);
        int z = (int) level.random.triangle(cameraPos.z(), range);
        int y = (int) level.random.triangle(cameraPos.y(), range);
        destination.set(x, y, z);
        return level.getBlockState(destination);
    }

    public static boolean hasRainPuddle(Level level, BlockPos pos) {
        return config.doRainPuddles
                && isHighestBlock(level, pos)
                && withinPuddleThreshold(level, pos)
                && isUnobstructedFlatSurface(level, pos);
    }

    public static boolean withinPuddleThreshold(Level level, BlockPos pos) {
        int x = Mth.abs(pos.getX() % puddleMap.getWidth());
        int z = Mth.abs(pos.getZ() % puddleMap.getHeight());
        int puddleLevel = VersionUtil.getBlue(VersionUtil.getPixel(puddleMap, x, z));
        return puddleLevel < (level.isThundering() ? config.stormLevel : config.rainLevel);
    }

    public static NativeImage loadTexture(ResourceLocation location) throws IOException {
        return loadTexture(net.minecraft.client.Minecraft.getInstance().getResourceManager().getResourceOrThrow(location));
    }

    public static NativeImage loadTexture(Resource resource) throws IOException {
        InputStream inputStream = resource.open();
        NativeImage nativeImage;
        try {
            nativeImage = NativeImage.read(inputStream);
        } catch (Throwable owo) {
            try {
                inputStream.close();
            } catch (Throwable uwu) {
                owo.addSuppressed(uwu);
            }
            throw owo;
        }
        inputStream.close();
        return nativeImage;
    }
}
