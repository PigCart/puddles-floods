package pigcart.puddleflood.mixin.iris;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.irisshaders.iris.shaderpack.IdMap;
import net.irisshaders.iris.shaderpack.materialmap.BlockEntry;
import net.irisshaders.iris.shaderpack.materialmap.NamespacedId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collections;
import java.util.List;

import static pigcart.puddleflood.PuddleFlood.MOD_ID;
import static pigcart.puddleflood.config.ConfigManager.config;

@Mixin(IdMap.class)
public class IdMapMixin {

    //~ if >=26.1 'lambda$parseBlockMap$2' -> 'lambda$parseBlockMap$0'
    @WrapOperation(method = "lambda$parseBlockMap$2", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"), remap = false)
    private static boolean addBlockEntry(List instance, Object entry, Operation<Boolean> original) {
        if (config.useShaderpackWater && entry instanceof BlockEntry blockEntry) {
            if (blockEntry.id().getNamespace().equals("minecraft") && blockEntry.id().getName().equals("water")) {
                original.call(instance, new BlockEntry(new NamespacedId(MOD_ID, "puddle"), Collections.emptyMap()));
            }
        }
        return original.call(instance, entry);
    }
}
