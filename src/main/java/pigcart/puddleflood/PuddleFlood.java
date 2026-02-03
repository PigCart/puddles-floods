package pigcart.puddleflood;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pigcart.puddleflood.block.PuddleBlock;
import pigcart.puddleflood.config.ConfigManager;
//? if >=1.21.9 {
/*import net.minecraft.client.gui.components.debug.DebugScreenEntries;
*///?}

import java.util.List;

public class PuddleFlood {

    public static final String MOD_ID = "puddleflood";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static PuddleBlock PUDDLE_BLOCK;

    public static SoundEvent PUDDLE_STEP_SOUND;

    private static List<String> getDebugLines() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return List.of("no level loaded :3");
        BlockPos playerBlockPos = BlockPos.containing(Minecraft.getInstance().player.position());
        final Holder<Biome> holder = level.getBiome(playerBlockPos);
        String biomeStr = holder.unwrap().map(
                (resourceKey) -> VersionUtil.getKeyId(resourceKey).toString(),
                (biome) -> "[unregistered " + biome + "]"
        );
        return List.of(
                "Biome: " + biomeStr,
                "could have puddle: " + BlockPlacementUtil.withinPuddleThreshold(level, playerBlockPos),
                "is flat surface: " + BlockPlacementUtil.isUnobstructedFlatSurface(level, playerBlockPos),
                "can flood here: " + BlockPlacementUtil.canFlood(level, playerBlockPos),
                "Collection speed: " + ConfigManager.config.collectionSpeed,
                "Evaporation speed: " + ConfigManager.config.evaporationSpeed,
                "Rain level: " + ConfigManager.config.rainLevel,
                "Storm level: " + ConfigManager.config.stormLevel
        );
    }

    ///  sets up features that dont require a specific modloader
    public static void onInitializeClient() {
        ConfigManager.load();

        //? if >=1.21.9 {
        /*DebugScreenEntries.register(VersionUtil.getId("debug"),
                (display, level, levelChunk, levelChunk2) ->
                        display.addToGroup(VersionUtil.getId("debuglines"), getDebugLines())
        );
        *///?}

        //TODO
        PUDDLE_STEP_SOUND = createSoundEvent("puddle.step");
    }

    @SuppressWarnings("unchecked")
    public static <S> LiteralArgumentBuilder<S> getCommands() {
        return (LiteralArgumentBuilder<S>) LiteralArgumentBuilder.literal(MOD_ID)
                .executes(ctx -> {
                    // give minecraft a tick to close the chat screen
                    VersionUtil.schedule(() -> Minecraft.getInstance().setScreen(ConfigManager.screenPlease(null)));
                    return 0;
                })
                .then(LiteralArgumentBuilder.literal("debug")
                        .executes(ctx -> {
                            getDebugLines().forEach(PuddleFlood::addChatMsg);
                            return 0;
                        })
                );
    }

    public static void onTick(Minecraft client) {
        if (!client.isPaused() && client.level != null && client.getCameraEntity() != null) {
            BlockPlacementUtil.tick(client.level);
        }
    }

    //TODO
    private static SoundEvent createSoundEvent(String name) {
        return SoundEvent.createVariableRangeEvent(VersionUtil.getId(name));
    }

    private static void addChatMsg(String message) {
        Minecraft.getInstance().gui.getChat().addMessage(Component.literal(message));
    }

    public static int puddleTintProvider(BlockState blockState, BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, int tintIndex) {
        if (blockAndTintGetter != null && blockPos != null) {
            return blockAndTintGetter.getBlockTint(blockPos, BiomeColors.WATER_COLOR_RESOLVER);
        }
        return 0xFF3F76E4; // default water color listed on minecraft.wiki
    }
    public static int puddleItemTintProvider(ItemStack itemStack, int tintIndex) {
        return 0xFF3F76E4;
    }
}