package xyz.ultrapixelmon.pepefab;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

    public static String message;

    public static void load(){
        Configuration config = new Configuration(new File("config/RanchDittoDittoHourglassDisable.cfg"));
        config.load();

        message = config.getString(Configuration.CATEGORY_GENERAL, "Message", "Impossible d'utiliser de sablier sur un couple de Ditto.","");
        config.save();
    }
}
