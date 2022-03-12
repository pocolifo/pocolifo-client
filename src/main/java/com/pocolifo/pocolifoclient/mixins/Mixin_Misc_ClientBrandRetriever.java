package com.pocolifo.pocolifoclient.mixins;

import com.pocolifo.pocolifoclient.launch.BuildProperties;
import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientBrandRetriever.class)
public class Mixin_Misc_ClientBrandRetriever {
    /**
     * @author youngermax
     */
    @Overwrite
    public static String getClientModName() {
        return BuildProperties.VERSION;
    }
}
