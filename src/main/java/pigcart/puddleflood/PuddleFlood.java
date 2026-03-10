package pigcart.puddleflood;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pigcart.puddleflood.block.PuddleBlock;
import pigcart.puddleflood.config.ConfigManager;

public class PuddleFlood {

    public static final String MOD_ID = "puddleflood";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static PuddleBlock PUDDLE_BLOCK;

    public static final SoundEvent STEP_SOUND_EVENT = SoundEvent.createVariableRangeEvent(VersionUtil.getId("puddle.step"));

    public static final SoundType SOUND_TYPE = new SoundType(1.0F,
            1.0F,
            STEP_SOUND_EVENT,
            STEP_SOUND_EVENT,
            STEP_SOUND_EVENT,
            STEP_SOUND_EVENT,
            STEP_SOUND_EVENT);

    public static final BlockBehaviour.Properties PUDDLE_PROPERTIES = BlockBehaviour.Properties
            ./*?>=1.21.1{*//*ofFullCopy*//*?}else{*/copy/*?}*/(Blocks.WATER)
            .mapColor(MapColor.NONE)
            .sound(SOUND_TYPE);

    ///  sets up features that dont require a specific modloader
    public static void onInitialize() {
        ConfigManager.load();
    }
}