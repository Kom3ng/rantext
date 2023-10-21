package org.abstruck.mirai;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import org.abstruck.mirai.config.Config;
import org.abstruck.mirai.runtime.ThreadManager;
import org.abstruck.mirai.util.DatabaseUtils;

import java.sql.SQLException;

public final class Rantext extends JavaPlugin {
    public static final Rantext INSTANCE = new Rantext();

    private Rantext() {
        super(new JvmPluginDescriptionBuilder("org.abstruck.mirai.rantext", "0.1.0")
                .name("Rantext")
                .author("Astrack")

                .build());
    }

    @Override
    public void onEnable() {
        this.reloadPluginConfig(Config.INSTANCE);
        DatabaseUtils.init();
        ThreadManager.start();
        getLogger().info("Plugin enabled");

    }

    @Override
    public void onDisable() {
        ThreadManager.stop();
        try {
            DatabaseUtils.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getLogger().info("Plugin disabled");
    }
}