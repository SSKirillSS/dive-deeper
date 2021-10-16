package it.hurts.sskirillss.divedeeper;

import it.hurts.sskirillss.divedeeper.utils.DDConfig;
import it.hurts.sskirillss.divedeeper.utils.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(Reference.MODID)
public class DiveDeeper {
    public DiveDeeper() {
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DDConfig.DD_CONFIG);
    }
}