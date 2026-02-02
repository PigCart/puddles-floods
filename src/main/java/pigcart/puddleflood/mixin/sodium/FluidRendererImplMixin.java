//? >=1.21.1 && <26.1 {
/*package pigcart.puddleflood.mixin.sodium;

import me.jellysquid.mods.sodium.client.model.color.ColorProvider;
import me.jellysquid.mods.sodium.client.model.color.ColorProviderRegistry;
import me.jellysquid.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.DefaultFluidRenderer;
import me.jellysquid.mods.sodium.client.render.chunk.terrain.material.Material;
import me.jellysquid.mods.sodium.client.render.chunk.translucent_sorting.TranslucentGeometryCollector;
import me.jellysquid.mods.sodium.client.world.LevelSlice;
import me.jellysquid.mods.sodium./^?fabric{^/fabric/^?}else{^//^neoforge^//^?}^/.render.FluidRendererImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import pigcart.puddleflood.duck.Soduckium;
//? >=1.21.9 {
/^import net.minecraft.client.renderer.block.model.BlockStateModel;
^///?} else {
import net.minecraft.client.resources.model.BakedModel;
 //?}

@Mixin(FluidRendererImpl.class)
public class FluidRendererImplMixin implements Soduckium {

    @Shadow @Final private DefaultFluidRenderer defaultRenderer;

    @Shadow @Final private ColorProviderRegistry colorProviderRegistry;

    @Override
    public void puddle_flood$renderBlockModel(/^? >=1.21.9 {^//^BlockStateModel^//^?}else{^/BakedModel/^?}^/ model,
                                              LevelSlice level,
                                              BlockState blockState,
                                              FluidState fluidState,
                                              Material material,
                                              BlockPos blockPos,
                                              BlockPos offset,
                                              TranslucentGeometryCollector collector,
                                              ChunkModelBuilder meshBuilder,
                                              ColorProvider<FluidState> ignored)
    {
        ((Soduckium)this.defaultRenderer).puddle_flood$renderBlockModel(model,
                level,
                blockState,
                fluidState,
                material,
                blockPos,
                offset,
                collector,
                meshBuilder,
                this.colorProviderRegistry.getColorProvider(Fluids.WATER));
    }
}
*///?}
