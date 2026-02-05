package pigcart.puddleflood;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pigcart.puddleflood.block.PuddleBlock;
import pigcart.puddleflood.config.ConfigManager;

public class PuddleFlood {

    public static final String MOD_ID = "puddleflood";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static PuddleBlock PUDDLE_BLOCK;

    ///  sets up features that dont require a specific modloader
    public static void onInitialize() {
        ConfigManager.load();
    }
}