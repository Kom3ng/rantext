package org.abstruck.mirai.config;

import net.mamoe.mirai.console.data.Value;
import net.mamoe.mirai.console.data.java.JavaAutoSavePluginConfig;

import java.util.List;

public class Config extends JavaAutoSavePluginConfig {
    public static final Config INSTANCE = new Config();

    public static final Value<List<Long>> allowGroupIds = INSTANCE.typedValue("allowGroupIds",createKType(List.class, createKType(Long.class)));
    public static final Value<List<Long>> allowBotIds = INSTANCE.typedValue("allowBotIds",createKType(List.class, createKType(Long.class)));
    public static final Value<Long> startTimeStamp = INSTANCE.value("startTimeStamp",0L);
    public static final Value<Long> interval = INSTANCE.value("interval",1000*60*60*24L);
    public static final Value<Long> cooldown = INSTANCE.value("cooldown",1000*60*60*72L);
    public static final Value<String> sqlUrl = INSTANCE.value("sqlUrl","");
    public static final Value<String> sqlUsername = INSTANCE.value("sqlUsername","");
    public static final Value<String> sqlPassword = INSTANCE.value("sqlPassword","");

    public Config() {
        super("config");
    }
}
