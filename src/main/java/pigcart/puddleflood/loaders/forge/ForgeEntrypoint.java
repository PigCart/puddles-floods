//? if forge {
/*package pigcart.puddleflood.loaders.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import pigcart.puddleflood.PuddleFlood;
import pigcart.puddleflood.block.PuddleBlock;
import pigcart.puddleflood.config.ConfigManager;

import static pigcart.puddleflood.PuddleFlood.*;

@Mod(MOD_ID)
public class ForgeEntrypoint {

    public ForgeEntrypoint() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // @SubscribeEvent is supposed to do this but it doesnt work
        // idk what the deal is with the two different instances of IEventBus used here
        MinecraftForge.EVENT_BUS.addListener(ForgeEntrypoint::onTickEvent);
        MinecraftForge.EVENT_BUS.addListener(ForgeEntrypoint::onRegisterCommandsEvent);
        eventBus.addListener(ForgeEntrypoint::onRegisterEvent);
        eventBus.addListener(ForgeEntrypoint::onBuildCreativeTabEvent);
        eventBus.addListener(ForgeEntrypoint::onRegisterBlockColorEvent);
        eventBus.addListener(ForgeEntrypoint::onRegisterItemColorEvent);
        eventBus.addListener(ForgeEntrypoint::onSetupEvent);

        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (client, parent) -> ConfigManager.screenPlease(parent)
                )
        );
    }

    public static void onTickEvent(TickEvent.ClientTickEvent event) {
        PuddleFlood.onTick(Minecraft.getInstance());
    }

    public static void onRegisterCommandsEvent(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(getCommands());
    }

    public static void onRegisterEvent(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.BLOCKS,
                h -> {
                    PUDDLE_BLOCK = new PuddleBlock(BlockBehaviour.Properties.copy((Blocks.WATER)));
                    h.register(new ResourceLocation(MOD_ID, "puddle"), PUDDLE_BLOCK);
                }
        );
        event.register(ForgeRegistries.Keys.ITEMS,
                h -> {
                    BlockItem blockItem = new BlockItem(PUDDLE_BLOCK, new Item.Properties());
                    h.register(new ResourceLocation(MOD_ID, "puddle"), blockItem);
                }
        );
    }

    public static void onBuildCreativeTabEvent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(PUDDLE_BLOCK);
        }
    }

    public static void onRegisterBlockColorEvent(RegisterColorHandlersEvent.Block event){
        event.register(PuddleFlood::puddleTintProvider, PUDDLE_BLOCK);
    }

    public static void onRegisterItemColorEvent(RegisterColorHandlersEvent.Item event){
        event.register(PuddleFlood::puddleItemTintProvider, PUDDLE_BLOCK);
    }

    public static void onSetupEvent(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(PUDDLE_BLOCK, RenderType.translucent());
    }
}
*///?}