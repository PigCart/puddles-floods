package pigcart.puddleflood.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import pigcart.puddleflood.BlockPlacementUtil;
import pigcart.puddleflood.VersionUtil;
import pigcart.puddleflood.duck.BlockTickDuck;

@SuppressWarnings("NullableProblems")
public class PuddleBlock extends Block implements BlockTickDuck {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    //public static final BooleanProperty NORTHEAST = BooleanProperty.create("northeast");
    //public static final BooleanProperty SOUTHEAST = BooleanProperty.create("southeast");
    //public static final BooleanProperty SOUTHWEST = BooleanProperty.create("southwest");
    //public static final BooleanProperty NORTHWEST = BooleanProperty.create("northwest");

    public PuddleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.getStateAt(context.getClickedPos(), context.getLevel());
    }

    public BlockState getStateAt(BlockPos pos, BlockGetter blockGetter) {
        //BlockState northEastBlock = blockGetter.getBlockState(pos.offset(-1, 0, -1));
        //BlockState southEastBlock = blockGetter.getBlockState(pos.offset(-1, 0, 1));
        //BlockState southWestBlock = blockGetter.getBlockState(pos.offset(1, 0, 1));
        //BlockState northWestBlock = blockGetter.getBlockState(pos.offset(1, 0, -1));
        return this.defaultBlockState()
                .setValue(NORTH, attachesTo(pos.north(), blockGetter))
                .setValue(SOUTH, attachesTo(pos.south(), blockGetter))
                .setValue(WEST, attachesTo(pos.west(), blockGetter))
                .setValue(EAST, attachesTo(pos.east(), blockGetter));
                /*.setValue(NORTHEAST, attachesTo(northEastBlock))
                .setValue(SOUTHEAST, attachesTo(southEastBlock))
                .setValue(SOUTHWEST, attachesTo(southWestBlock))
                .setValue(NORTHWEST, attachesTo(northWestBlock));*/
    }

    @Override
    //? >=1.21.9 {
    /*protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return updateBlockState(state, level, pos, direction, neighborState);
    }
    *///?} else {
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return updateBlockState(state, level, pos, direction, neighborState);
    }
    //?}
    private BlockState updateBlockState(BlockState state, LevelReader level, BlockPos pos, Direction direction, BlockState neighborState) {
        if (direction.getAxis().isHorizontal()) {
            return getStateAt(pos, level);
        } else if (direction == Direction.DOWN && neighborState.isAir()) {
            VersionUtil.schedule(()-> Minecraft.getInstance().level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0));
            return state;
        } else {
            return state;
        }
    }

    public final boolean attachesTo(BlockPos pos, BlockGetter level) {
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof PuddleBlock
                || state.getOcclusionShape(/*?<1.21.9{*/level, pos/*?}*/).equals(Shapes.block())
                || !BlockPlacementUtil.isSolidSurface(level, pos.below());
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.randomTick(level, pos);
    }

    @Override
    public void puddleflood_clientRandomTick(Level level, BlockPos pos) {
        this.randomTick(level, pos);
    }

    public void randomTick(Level level, BlockPos pos) {
        if (!level.isRaining()) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
        }
    }

    //? >=1.21.9 {
    /*@Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }
    *///?} else {
    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }
    //?}

    @Override
    public boolean isPathfindable(BlockState state,/*?<=1.20.1{*/BlockGetter level, BlockPos pos,/*?}*/ PathComputationType type) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH/*, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST*/);
    }
}
