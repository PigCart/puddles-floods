//? if forge {
/*package pigcart.puddleflood.loaders.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import pigcart.puddleflood.PuddleFloodClient;

import static pigcart.puddleflood.PuddleFlood.MOD_ID;
import static pigcart.puddleflood.PuddleFlood.PUDDLE_BLOCK;
import static pigcart.puddleflood.PuddleFloodClient.getCommands;

public class ForgeClientEntrypoint {

    public static void onTickEvent(TickEvent.ClientTickEvent event) {
        PuddleFloodClient.onTick(Minecraft.getInstance());
    }

    public static void onRegisterCommandsEvent(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(getCommands());
    }

    public static void onBuildCreativeTabEvent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(PUDDLE_BLOCK);
        }
    }

    public static void onRegisterBlockColorEvent(RegisterColorHandlersEvent.Block event){
        event.register(PuddleFloodClient::puddleTintProvider, PUDDLE_BLOCK);
    }

    public static void onRegisterItemColorEvent(RegisterColorHandlersEvent.Item event){
        event.register(PuddleFloodClient::puddleItemTintProvider, PUDDLE_BLOCK);
    }

    public static void onSetupEvent(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(PUDDLE_BLOCK, RenderType.translucent());
    }
}
*///?}