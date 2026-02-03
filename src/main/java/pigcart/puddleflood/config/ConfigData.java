package pigcart.puddleflood.config;

import pigcart.puddleflood.config.ConfigManager.ClientHasAuthority;
import pigcart.puddleflood.config.ConfigManager.ReloadChunks;
import pigcart.puddleflood.config.gui.Annotations.*;


public class ConfigData {
    @NoGUI public byte configVersion = 0;

    @OnChange(ReloadChunks.class)
    public boolean useShaderpackWater = true;

    public boolean doFloods = true;
    public boolean doRainPuddles = true;
    public boolean doDripPuddles = true;
    public boolean doPotionPuddles = true;
    public boolean doCauldronPuddles = true;
    @OnlyEditableIf(ClientHasAuthority.class)
    public boolean doFlowingWaterPuddles = true;
    @OnlyEditableIf(ClientHasAuthority.class)
    public boolean doMeltingSnowPuddles = true;

    public int range = 128;
    public int collectionSpeed = 4;
    public int evaporationSpeed = 8;

    @Slider(min = 1, max = 255, step = 1)
    public int rainLevel = 90;
    @Slider(min = 1, max = 255, step = 1)
    public int stormLevel = 130;
}