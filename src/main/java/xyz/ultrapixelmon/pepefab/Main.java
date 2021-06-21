package xyz.ultrapixelmon.pepefab;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION, serverSideOnly = true, acceptableRemoteVersions = "*")
public class Main
{
    public static final String MODID = "ranchdittodittohourglassdisable";
    public static final String NAME = "RanchDittoDittoHourglassDisable";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        Config.load();
        MinecraftForge.EVENT_BUS.register(new xyz.ultrapixelmon.pepefab.Listeners());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("[RanchDittoDittoHourglassDisable] Initialisation effectue avec succes. Version: " + Main.VERSION);

        //PermissionAPI.registerNode("ranchdittodittohourglassdisable.bypass", DefaultPermissionLevel.OP, "Permet de ne pas avoir de restriction d'utilisation sur les couples de Ditto");
    }
}
