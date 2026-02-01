//? if fabric {
package pigcart.puddleflood.loaders.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import pigcart.puddleflood.config.ConfigManager;

public class ModMenuEntrypoint implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigManager::screenPlease;
    }

}
//?}