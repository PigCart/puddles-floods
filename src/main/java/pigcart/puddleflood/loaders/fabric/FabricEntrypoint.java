package pigcart.puddleflood.loaders.fabric;

import net.fabricmc.api.ModInitializer;
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
/*import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
*///?} else {
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
//?}

import java.util.function.Function;

import static pigcart.puddleflood.PuddleFlood.PUDDLE_BLOCK;


public class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        init();
    }

    // used also by NeoforgeEntrypoint
    public static void init() {

        PUDDLE_BLOCK = (PuddleBlock) registerBlock("puddle", PuddleBlock::new, BlockBehaviour.Properties./*?>=1.21.1{*//*ofFullCopy*//*?}else{*/copy/*?}*/(Blocks.WATER));

        PuddleFlood.onInitialize();
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