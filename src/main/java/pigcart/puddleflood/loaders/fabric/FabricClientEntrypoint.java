package pigcart.puddleflood.loaders.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import pigcart.puddleflood.PuddleFloodClient;

//? >=26.1 {
/*import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.color.block.BlockTintSources;
*///?} else {
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
//?}

//? <26.1 {
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
 //?} <1.21.9 {
/*import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
*///?}

//? >=1.21.9 {
/*import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import pigcart.puddleflood.VersionUtil;
*///?} else {
import net.minecraft.client.renderer.RenderType;
//?}

import java.util.List;

import static pigcart.puddleflood.PuddleFlood.PUDDLE_BLOCK;
import static pigcart.puddleflood.PuddleFloodClient.getDebugLines;


public class FabricClientEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        init();
    }

    // used also by NeoforgeEntrypoint
    public static void init() {
        //? if >=1.21.9 {
        /*DebugScreenEntries.register(VersionUtil.getId("debug"),
                (display, level, levelChunk, levelChunk2) ->
                        display.addToGroup(VersionUtil.getId("debuglines"), getDebugLines())
        );
        *///?}

        ClientTickEvents.END_CLIENT_TICK.register(PuddleFloodClient::onTick);

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(PuddleFloodClient.getCommands());
        });

        // since 26.1 render layers are automatically assigned on model load based on texture
        //? <26.1 {
        BlockRenderLayerMap.putBlock(PUDDLE_BLOCK, ChunkSectionLayer.TRANSLUCENT);
         //?} <1.21.9 {
        /*BlockRenderLayerMap.INSTANCE.putBlock(PUDDLE_BLOCK, RenderType.translucent());
        *///?}

        //? if >=26.1 {
        /*BlockColorRegistry.register(List.of(BlockTintSources.water()), PUDDLE_BLOCK);
        *///?} else {
        ColorProviderRegistry.BLOCK.register(PuddleFloodClient::puddleTintProvider, PUDDLE_BLOCK);
        //?}

        // since 1.21.4 tint is in JSON item definition
        //? <1.21.4 {
        ColorProviderRegistry.ITEM.register(PuddleFloodClient::puddleItemTintProvider, PUDDLE_BLOCK); //ARGB
        //?}
    }
}