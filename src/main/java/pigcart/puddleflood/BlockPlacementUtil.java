package pigcart.puddleflood;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluids;

import static pigcart.puddleflood.config.ConfigManager.config;

public class BlockPlacementUtil {

    public static boolean canFlood(Level level, BlockPos blockPos) {
        return config.doFloods
                && blockPos.getY() == level.getSeaLevel()
                && !level.getBiome(blockPos).is(Biomes.BEACH)
                && isUnobstructedFlatSurface(level, blockPos);
    }

    public static boolean isHighestBlock(Level level, BlockPos blockPos) {
        final BlockPos heightmapPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos);
        return (heightmapPos.getY() <= blockPos.getY());
    }

    public static boolean isUnobstructedFlatSurface(Level level, BlockPos blockPos) {
        return level.getBlockState(blockPos).isAir() &&
                isFlatSurface(level, blockPos);
    }
    public static boolean isFlatSurface(Level level, BlockPos blockPos) {
        return isSolidSurface(level, blockPos.below()) &&
                wouldntDrainTo(level, blockPos.offset(1, -1, 0)) &&
                wouldntDrainTo(level, blockPos.offset(-1,-1, 0)) &&
                wouldntDrainTo(level, blockPos.offset(0, -1,-1)) &&
                wouldntDrainTo(level, blockPos.offset(0, -1, 1));
    }

    public static boolean isSolidSurface(BlockGetter level, BlockPos blockPos) {
        return level.getBlockState(blockPos).isFaceSturdy(level, blockPos, Direction.UP);
    }

    public static boolean wouldntDrainTo(BlockGetter level, BlockPos blockPos) {
        final BlockState state = level.getBlockState(blockPos);
        return state.isCollisionShapeFullBlock(level, blockPos)
                || state.getFluidState().is(Fluids.WATER);
    }

}
