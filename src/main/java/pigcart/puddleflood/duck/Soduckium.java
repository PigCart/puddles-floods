//? <26.1 {
package pigcart.puddleflood.duck;

import me.jellysquid.mods.sodium.client.model.color.ColorProvider;
import me.jellysquid.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import me.jellysquid.mods.sodium.client.render.chunk.terrain.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
//? >=1.21.1 {
/*import me.jellysquid.mods.sodium.client.render.chunk.translucent_sorting.TranslucentGeometryCollector;
import me.jellysquid.mods.sodium.client.world.LevelSlice;
*///?} else {
import me.jellysquid.mods.sodium.client.world.WorldSlice;
//?}
//? >=1.21.9 {
/*import net.minecraft.client.renderer.block.model.BlockStateModel;
*///?} else {
import net.minecraft.client.resources.model.BakedModel;
//?}

/// Duck interface for fluid rendering with Sodium
public interface Soduckium {
    /// copy of DefaultFluidRenderer$render that takes a block model instead
    void puddle_flood$renderBlockModel(/*? >=1.21.9 {*//*BlockStateModel*//*?}else{*/BakedModel/*?}*/ model,
                                       /*? >=1.21.1 {*//*LevelSlice*//*?}else{*/WorldSlice/*?}*/ level,
                                       BlockState blockState,
                                       FluidState fluidState,
                                       Material material,
                                       BlockPos blockPos,
                                       BlockPos offset,
                                       /*? >=1.21.1 {*//*TranslucentGeometryCollector collector,*//*?}*/
                                       ChunkModelBuilder meshBuilder,
                                       ColorProvider<FluidState> colorProvider);
}
//?}