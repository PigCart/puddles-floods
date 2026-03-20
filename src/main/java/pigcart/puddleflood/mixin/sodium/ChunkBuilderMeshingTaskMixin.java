//? <26.1 {
package pigcart.puddleflood.mixin.sodium;

import com.llamalad7.mixinextras.sugar.Local;
import me.jellysquid.mods.sodium.client.render.chunk.compile.tasks.ChunkBuilderMeshingTask;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? >=1.21.1 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import net.irisshaders.iris.shaderpack.materialmap.WorldRenderingSettings;
import net.minecraft.core.BlockPos;
*///?} else {
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildContext;
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildOutput;
import me.jellysquid.mods.sodium.client.util.task.CancellationToken;
import net.irisshaders.iris.compat.sodium.impl.block_context.ChunkBuildBuffersExt;
import net.irisshaders.iris.vertices.ExtendedDataHelper;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//?}
import static pigcart.puddleflood.VersionUtil.forceWaterRender;

@Mixin(ChunkBuilderMeshingTask.class)
public class ChunkBuilderMeshingTaskMixin {

    //? >=1.21.1 {
    /*@WrapOperation(method = "execute(Lme/jellysquid/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lme/jellysquid/mods/sodium/client/util/task/CancellationToken;)Lme/jellysquid/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;",
        at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderer;renderModel(" +
                        /^? >=1.21.9 {^//^"Lnet/minecraft/client/renderer/block/model/BlockStateModel;"^//^?}else{^/"Lnet/minecraft/client/resources/model/BakedModel;"/^?}^/ +
                        "Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)V"), remap = false)
    private void renderModel(BlockRenderer instance,
                             //? >=1.21.9 {
                             /^net.minecraft.client.renderer.block.model.BlockStateModel model,
                             ^///?} else {
                             net.minecraft.client.resources.model.BakedModel model,
                             //?}
                             BlockState state,
                             BlockPos blockPos,
                             BlockPos modelOffset,
                             Operation<Void> original,
                             @Local ChunkBuildBuffers buffers)
    {
        if (forceWaterRender(state)) {
            // copy of iris$onRenderModel
            if (WorldRenderingSettings.INSTANCE.getBlockStateIds() != null) {
                //? >=1.21.9 {
                /^((net.irisshaders.iris.vertices.sodium.terrain.VertexEncoderInterface) instance).beginBlock(WorldRenderingSettings.INSTANCE.getBlockStateIds().getInt(Fluids.WATER.defaultFluidState().createLegacyBlock()), (byte) 0, (byte) state.getLightEmission(), blockPos.getX(), blockPos.getY(), blockPos.getZ());
                ^///?} else {
                // 1.21.1 does not work for unknown reason
                ((net.irisshaders.iris.vertices.BlockSensitiveBufferBuilder)buffers).beginBlock(WorldRenderingSettings.INSTANCE.getBlockStateIds().getInt(Fluids.WATER.defaultFluidState().createLegacyBlock()), (byte)0, (byte)state.getLightEmission(), blockPos.getX(), blockPos.getY(), blockPos.getZ());
                //?}
            }
            // passing in a water blockstate makes iris render water while still letting sodium render the block model
            // iris still injects onRenderModel just before this Wrap so i hope that doesnt break anything
        }
        original.call(instance, model, state, blockPos, modelOffset);
    }

    *///?} else {
    @Inject(method = "execute(Lme/jellysquid/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lme/jellysquid/mods/sodium/client/util/task/CancellationToken;)Lme/jellysquid/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;",
            at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderer;renderModel(Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderContext;Lme/jellysquid/mods/sodium/client/render/chunk/compile/ChunkBuildBuffers;)V"), remap = false)
    private void renderModel(ChunkBuildContext context,
                             CancellationToken cancellationToken,
                             CallbackInfoReturnable<ChunkBuildOutput> cir,
                             @Local(name = "blockState") BlockState blockState)
    {
        if (forceWaterRender(blockState)) {
            // copy of iris$wrapGetBlockLayer
            if (context.buffers instanceof ChunkBuildBuffersExt buffersExt) {
                buffersExt.iris$setMaterialId(
                        Fluids.WATER.defaultFluidState().createLegacyBlock(),
                        ExtendedDataHelper.BLOCK_RENDER_TYPE,
                        (byte) blockState.getLightEmission()
                );
            }
            // passing in a water blockstate makes iris render water while still letting sodium render the block model
            // iris still injects wrapGetBlockLayer just before this Inject so i hope that doesnt break anything
        }
    }
    //?}
}
//?}