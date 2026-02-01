//? <26.1 {
package pigcart.puddleflood.mixin.sodium;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import me.jellysquid.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderCache;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import me.jellysquid.mods.sodium.client.render.chunk.compile.tasks.ChunkBuilderMeshingTask;
import me.jellysquid.mods.sodium.client.render.chunk.terrain.material.DefaultMaterials;
import me.jellysquid.mods.sodium.client.render.chunk.terrain.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pigcart.puddleflood.PuddleFlood;
import pigcart.puddleflood.duck.Soduckium;
import pigcart.puddleflood.config.ConfigManager;
//? >=1.21.1 {
/*import me.jellysquid.mods.sodium.client.render.chunk.translucent_sorting.TranslucentGeometryCollector;
*///?}
//? >=1.21.9 {
/*import net.minecraft.client.renderer.block.model.BlockStateModel;
*///?} else {
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderContext;
import net.minecraft.client.resources.model.BakedModel;
//?}

@Mixin(ChunkBuilderMeshingTask.class)
public class ChunkBuilderMeshingTaskMixin {

    // if block is a puddle, render it with the fluid renderer instead
    //? >=1.21.1 {
    /*@WrapOperation(method = "execute(Lme/jellysquid/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lme/jellysquid/mods/sodium/client/util/task/CancellationToken;)Lme/jellysquid/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;",
        at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderer;renderModel(" +
                        /^? >=1.21.9 {^//^"Lnet/minecraft/client/renderer/block/model/BlockStateModel;"^//^?}else{^/"Lnet/minecraft/client/resources/model/BakedModel;"/^?}^/ +
                        "Lnet/minecraft/world/level/block/state/BlockState;" +
                        "Lnet/minecraft/core/BlockPos;" +
                        "Lnet/minecraft/core/BlockPos;)V"), remap = false)
    private void renderModel(BlockRenderer instance,
                             /^? >=1.21.9 {^//^BlockStateModel^//^?}else{^/BakedModel/^?}^/ model,
                             BlockState state,
                             BlockPos blockPos,
                             BlockPos modelOffset,
                             Operation<Void> original,
                             @Local(name = "cache") BlockRenderCache cache,
                             @Local(name = "collector") TranslucentGeometryCollector collector,
                             @Local(name = "buffers") ChunkBuildBuffers buffers)
    {
        if (state.is(PuddleFlood.PUDDLE_BLOCK) && ConfigManager.config.useShaderpackWater) { //&& irisapi.shaderpackenabled
            final FluidState fluidState = Fluids.WATER.defaultFluidState();
            final Material material = DefaultMaterials.forFluidState(fluidState);
            final ChunkModelBuilder meshBuilder = buffers.get(material);
            ((Soduckium)cache.getFluidRenderer()).puddle_flood$renderBlockModel(model, cache.getWorldSlice(), state, fluidState, material, blockPos, modelOffset, collector, meshBuilder, null);
        } else {
            original.call(instance, model, state, blockPos, modelOffset);
        }
    }

    *///?} else {
    @WrapOperation(method = "execute(Lme/jellysquid/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lme/jellysquid/mods/sodium/client/util/task/CancellationToken;)Lme/jellysquid/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;",
            at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderer;renderModel(" +
                    "Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderContext;" +
                    "Lme/jellysquid/mods/sodium/client/render/chunk/compile/ChunkBuildBuffers;)V"), remap = false)
    private void renderModel(BlockRenderer instance,
                             BlockRenderContext blockRenderContext,
                             ChunkBuildBuffers chunkBuildBuffers,
                             Operation<Void> original,
                             @Local(name = "cache") BlockRenderCache cache,
                             @Local(name = "buffers") ChunkBuildBuffers buffers,
                             @Local(name = "model") BakedModel model,
                             @Local(name = "blockPos") BlockPos.MutableBlockPos blockPos,
                             @Local(name = "modelOffset") BlockPos.MutableBlockPos modelOffset,
                             @Local(name = "blockState") BlockState state)
    {
        if (state.is(PuddleFlood.PUDDLE_BLOCK) && ConfigManager.config.useShaderpackWater) { //&& irisapi.shaderpackenabled
            final FluidState fluidState = Fluids.WATER.defaultFluidState();
            final Material material = DefaultMaterials.forFluidState(fluidState);
            final ChunkModelBuilder meshBuilder = buffers.get(material);
            ((Soduckium)cache.getFluidRenderer()).puddle_flood$renderBlockModel(model,
                    cache.getWorldSlice(),
                    state,
                    fluidState,
                    material,
                    blockPos,
                    modelOffset,
                    meshBuilder,
                    null);
        } else {
            original.call(instance, blockRenderContext, chunkBuildBuffers);
        }
    }
    //?}
}
//?}