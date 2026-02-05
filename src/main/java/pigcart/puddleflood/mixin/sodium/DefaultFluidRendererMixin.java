//? <26.1 {
package pigcart.puddleflood.mixin.sodium;


import me.jellysquid.mods.sodium.client.model.color.ColorProvider;
import me.jellysquid.mods.sodium.client.model.light.LightMode;
import me.jellysquid.mods.sodium.client.model.light.LightPipeline;
import me.jellysquid.mods.sodium.client.model.light.LightPipelineProvider;
import me.jellysquid.mods.sodium.client.model.quad.ModelQuadView;
import me.jellysquid.mods.sodium.client.model.quad.ModelQuadViewMutable;
import me.jellysquid.mods.sodium.client.model.quad.properties.ModelQuadFacing;
import me.jellysquid.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import me.jellysquid.mods.sodium.client.render.chunk.terrain.material.Material;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pigcart.puddleflood.PuddleFlood;
import pigcart.puddleflood.duck.Soduckium;
//? >=1.21.1 {
/*import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.DefaultFluidRenderer;
import me.jellysquid.mods.sodium.client.world.LevelSlice;
import me.jellysquid.mods.sodium.client.render.chunk.translucent_sorting.TranslucentGeometryCollector;
*///?} else {
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.FluidRenderer;
import me.jellysquid.mods.sodium.client.world.WorldSlice;
import me.jellysquid.mods.sodium.client.model.color.ColorProviderRegistry;
//?}
//? >=1.21.9 {
/*import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
*///?} else {
import net.minecraft.client.resources.model.BakedModel;
//?}

@Mixin(/*? >=1.21.1 {*//*DefaultFluidRenderer*//*?}else{*/FluidRenderer/*?}*/.class)
public abstract class DefaultFluidRendererMixin implements Soduckium {

    @Shadow(remap = false)
    //? >=1.21.1 {
    /*protected abstract void writeQuad(ChunkModelBuilder builder, TranslucentGeometryCollector collector, Material material, BlockPos offset, ModelQuadView quad, ModelQuadFacing facing, boolean flip);
    *///?} else {
    protected abstract void writeQuad(ChunkModelBuilder builder, Material material, BlockPos offset, ModelQuadView quad, ModelQuadFacing facing, boolean flip);
    //?}

    @Shadow(remap = false) @Final private ModelQuadViewMutable quad;

    @Shadow(remap = false) private static void setVertex(ModelQuadViewMutable quad, int i, float x, float y, float z, float u, float v) {}

    @Shadow(remap = false) @Final private LightPipelineProvider lighters;

    @Shadow(remap = false)
    //? >=1.21.1 {
    /*protected abstract void updateQuad(ModelQuadViewMutable quad, LevelSlice level, BlockPos pos, LightPipeline lighter, Direction dir, ModelQuadFacing facing, float brightness, ColorProvider<FluidState> colorProvider, FluidState fluidState);
    *///?} else {
    protected abstract void updateQuad(ModelQuadView quad, WorldSlice world, BlockPos pos, LightPipeline lighter, Direction dir, float brightness, ColorProvider<FluidState> colorProvider, FluidState fluidState);

    @Shadow(remap = false) @Final private ColorProviderRegistry colorProviderRegistry;
    //?}

    @Unique
    RandomSource random = new SingleThreadedRandomSource(42L);

    @Override
    public void puddle_flood$renderBlockModel(/*? >=1.21.9 {*//*BlockStateModel*//*?}else{*/BakedModel/*?}*/ model,
                                              /*? >=1.21.1 {*//*LevelSlice*//*?}else{*/WorldSlice/*?}*/ level,
                                              BlockState blockState,
                                              FluidState fluidState,
                                              Material material,
                                              BlockPos blockPos,
                                              BlockPos offset,
                                              /*? >=1.21.1 {*//*TranslucentGeometryCollector collector,*//*?}*/
                                              ChunkModelBuilder meshBuilder,
                                              ColorProvider<FluidState> colorProvider)
    {
        ModelQuadViewMutable quad = this.quad;
        LightMode lightMode = LightMode.FLAT;
        LightPipeline lighter = this.lighters.getLighter(lightMode);

        random.setSeed(blockState.getSeed(blockPos));

        // should probably be using quad emitting but idk how it works and cant find docs on it
        //? >=1.21.9 {
        /*for (BlockModelPart modelPart : model.collectParts(random)) {
            // loop over quads with no associated direction (i think this relates to culling)
            // puddle model has no directional culling so probably dont need to loop over the directions
            for (BakedQuad bakedQuad : modelPart.getQuads(null)) {
        *///?} else {
            for (BakedQuad bakedQuad : model.getQuads(blockState, null, random)) {
        //?}
                // cant figure out how to get the quad uvs so lets recalculate them
                // can combine with the dimensions of the quad with the sprite's atlas location
                // not quite right but looks passable, at least with vanilla textures
                final TextureAtlasSprite sprite = bakedQuad./*? >=1.21.9 {*//*sprite()*//*?}else{*/getSprite()/*?}*/;
                //? >=1.21.11 {
                /*// uv problem solved in 1.21.11 thanks mojang
                float u0 = net.minecraft.client.model.geom.builders.UVPair.unpackU(bakedQuad.packedUV0());
                float u1 = net.minecraft.client.model.geom.builders.UVPair.unpackU(bakedQuad.packedUV2());
                float v0 = net.minecraft.client.model.geom.builders.UVPair.unpackV(bakedQuad.packedUV0());
                float v1 = net.minecraft.client.model.geom.builders.UVPair.unpackV(bakedQuad.packedUV2());
                org.joml.Vector3fc northwest = bakedQuad.position0();
                org.joml.Vector3fc southwest = bakedQuad.position1();
                org.joml.Vector3fc southeast = bakedQuad.position2();
                org.joml.Vector3fc northeast = bakedQuad.position3();
                *///?} else {
                float u0 = sprite.getU0();
                float u1 = sprite.getU1();
                float v0 = sprite.getV0();
                float v1 = sprite.getV1();
                // FaceBakery.extractPositions not available before ~1.21.9
                Vector3f[] verts = new Vector3f[4];
                int[] faceData = bakedQuad./*?>=1.21.9 {*//*vertices()*//*?}else{*/getVertices()/*?}*/;
                for(int i = 0; i < 4; ++i) {
                    verts[i] = new Vector3f(
                                        Float.intBitsToFloat(faceData[8 * i]),
                                        Float.intBitsToFloat(faceData[8 * i + 1]),
                                        Float.intBitsToFloat(faceData[8 * i + 2])
                    );
                }
                Vector3f northwest = verts[0];
                Vector3f southwest = verts[1];
                Vector3f southeast = verts[2];
                Vector3f northeast = verts[3];

                u1 = Mth.lerp(northeast.x - northwest.x, u0, u1);
                v1 = Mth.lerp(southwest.z - northwest.z, v0, v1);

                // this should be correct world-space uvs but has modulo issues with quads bordering block edges ??
                //float x0 = Mth.lerp(Mth.positiveModulo(northwest.x, 1.001F), u0, u1);
                //float x1 = Mth.lerp(Mth.positiveModulo(northeast.x, 1.001F), u0, u1);
                //float z0 = Mth.lerp(Mth.positiveModulo(northwest.z, 1.001F), v0, v1);
                //float z1 = Mth.lerp(Mth.positiveModulo(southwest.z, 1.001F), v0, v1);

                //?}

                setVertex(quad, 0, northwest.x(), northwest.y(), northwest.z(), u0, v0);
                setVertex(quad, 1, southwest.x(), southwest.y(), southwest.z(), u0, v1);
                setVertex(quad, 2, southeast.x(), southeast.y(), southeast.z(), u1, v1);
                setVertex(quad, 3, northeast.x(), northeast.y(), northeast.z(), u1, v0);
                quad.setSprite(sprite);
                this.updateQuad(quad,
                        level,
                        blockPos,
                        lighter,
                        Direction.UP,
                        /*? >=1.21.1 {*//*ModelQuadFacing.UNASSIGNED,*//*?}*/ 1.0F,
                        /*? >=1.21.1 {*//*colorProvider*//*?}else{*/this.colorProviderRegistry.getColorProvider(fluidState.getType())/*?}*/,
                        fluidState);
                this.writeQuad(meshBuilder,
                        /*? >=1.21.1 {*//*collector,*//*?}*/ material,
                        offset,
                        quad,
                        ModelQuadFacing.UNASSIGNED,
                        false);
            }
        //? >=1.21.9 {
        /*}
        *///?}
    }

    @Inject(method = "fluidHeight", at = @At("HEAD"), cancellable = true, remap = false)
    public void fluidHeight(BlockAndTintGetter level, Fluid fluid, BlockPos pos, Direction direction, CallbackInfoReturnable<Float> cir) {
        if (level.getBlockState(pos.above()).is(PuddleFlood.PUDDLE_BLOCK)) {
            cir.setReturnValue(1.0F);
        }
    }
}
//?}