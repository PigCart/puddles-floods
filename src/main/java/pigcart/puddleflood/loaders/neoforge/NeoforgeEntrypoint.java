//? if neoforge {
/*package pigcart.puddleflood.loaders.neoforge;

import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.registries.RegisterEvent;
import pigcart.puddleflood.loaders.fabric.FabricEntrypoint;
import pigcart.puddleflood.config.ConfigManager;

import static pigcart.puddleflood.PuddleFlood.MOD_ID;

@Mod(MOD_ID)
public class NeoforgeEntrypoint {

    public NeoforgeEntrypoint(IEventBus eventBus) {
        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (modContainer, parent) -> ConfigManager.screenPlease(parent)
        );
        eventBus.addListener(RegisterEvent.class, NeoforgeEntrypoint::register);
    }

    public static void register(RegisterEvent event) {
        // yeah blocks whatever ffapi has it covered
        event.register(Registries.BLOCK, h -> {
            FabricEntrypoint.init();
        });
    }
}
*///?}