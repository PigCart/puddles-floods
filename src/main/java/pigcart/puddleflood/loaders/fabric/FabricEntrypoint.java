package pigcart.puddleflood.loaders.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import pigcart.puddleflood.PuddleFlood;
import pigcart.puddleflood.VersionUtil;
import pigcart.puddleflood.block.PuddleBlock;

//? >=26.1 {
/*import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
*///?} else {
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
//?}

//? if >=26.1 {
/*import net.fabricmc.fabric.api.client.rendering.v1.ChunkSectionLayerMap;
*///?} else if >=1.21.9 {
/*import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
*///? } else {
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
//?}

//? >=1.21.9 {
/*import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
*///?} else {
import net.minecraft.client.renderer.RenderType;
//?}

import java.util.function.Function;

import static pigcart.puddleflood.PuddleFlood.PUDDLE_BLOCK;


public class FabricEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        init();
    }

    // used also by NeoforgeEntrypoint
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(PuddleFlood::onTick);
        //ClientPlayConnectionEvents.JOIN.register(PuddleFlood::onJoin);
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(PuddleFlood.getCommands());
        });

        PUDDLE_BLOCK = (PuddleBlock) registerBlock("puddle", PuddleBlock::new, BlockBehaviour.Properties./*?>=1.21.1{*//*ofFullCopy*//*?}else{*/copy/*?}*/(Blocks.WATER));

        //? if >=26.1 {
        /*ChunkSectionLayerMap.putBlock(PUDDLE_BLOCK, ChunkSectionLayer.TRANSLUCENT);
        *///?} else if >=1.21.9 {
        /*BlockRenderLayerMap.putBlock(PUDDLE_BLOCK, ChunkSectionLayer.TRANSLUCENT);
         *///?} else {
        BlockRenderLayerMap.INSTANCE.putBlock(PUDDLE_BLOCK, RenderType.translucent());
        //?}

        //? if >=26.1 {
        /*BlockColorRegistry.register(PuddleFlood::puddleTintProvider, PUDDLE_BLOCK);
        *///?} else {
        ColorProviderRegistry.BLOCK.register(PuddleFlood::puddleTintProvider, PUDDLE_BLOCK);
        //?}

        // 1.21.4 added JSON item definition that handles tint
        //? <1.21.4 {
        ColorProviderRegistry.ITEM.register(PuddleFlood::puddleItemTintProvider, PUDDLE_BLOCK); //ARGB
        //?}

        PuddleFlood.onInitializeClient();
    }

    /// register a block with a corresponding BlockItem in the natural blocks creative inventory tab
    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties) {
        ResourceKey<Block> blockKey = createBlockKey(name);
        ResourceKey<Item> itemKey = createItemKey(name);

        Block block = blockFactory.apply(properties/*?>=1.21.9{*//*.setId(blockKey)*//*?}*/);
        BlockItem blockItem = new BlockItem(block, new Item.Properties()/*?>=1.21.9{*//*.setId(itemKey)*//*?}*/);

        Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        // put the block items next to the vanilla weather blocks in the creative inv
        //? >=26.1 {
        /*CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register((itemGroup) -> {
            itemGroup.insertBefore(Blocks.SNOW_BLOCK.asItem(), blockItem);
        });
        *///?} else {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register((itemGroup) -> {
            itemGroup.addBefore(Blocks.SNOW_BLOCK.asItem(), blockItem);
        });
        //?}

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    private static ResourceKey<Block> createBlockKey(String name) {
        return ResourceKey.create(Registries.BLOCK, VersionUtil.getId(name));
    }

    private static ResourceKey<Item> createItemKey(String name) {
        return ResourceKey.create(Registries.ITEM, VersionUtil.getId(name));
    }
}