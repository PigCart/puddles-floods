//? if forge {
/*package pigcart.puddleflood.loaders.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import pigcart.puddleflood.block.PuddleBlock;
import pigcart.puddleflood.config.gui.ConfigScreen;

import static pigcart.puddleflood.PuddleFlood.MOD_ID;
import static pigcart.puddleflood.PuddleFlood.PUDDLE_BLOCK;

@Mod(MOD_ID)
public class ForgeEntrypoint {

    public ForgeEntrypoint() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(ForgeEntrypoint::onRegisterEvent);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            MinecraftForge.EVENT_BUS.addListener(ForgeClientEntrypoint::onTickEvent);
            MinecraftForge.EVENT_BUS.addListener(ForgeClientEntrypoint::onRegisterCommandsEvent);
            eventBus.addListener(ForgeClientEntrypoint::onBuildCreativeTabEvent);
            eventBus.addListener(ForgeClientEntrypoint::onRegisterBlockColorEvent);
            eventBus.addListener(ForgeClientEntrypoint::onRegisterItemColorEvent);
            eventBus.addListener(ForgeClientEntrypoint::onSetupEvent);

            ModLoadingContext.get().registerExtensionPoint(
                    ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory(
                            (client, parent) -> ConfigScreen.get(parent)
                    )
            );
        }
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
}
*///?}