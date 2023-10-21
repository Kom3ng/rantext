package org.abstruck.mirai.runtime;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.ExternalResource;
import org.abstruck.mirai.Rantext;
import org.abstruck.mirai.config.Config;
import org.abstruck.mirai.util.DatabaseUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SenderThread implements Runnable{
    private boolean enabled;
    public SenderThread(){
    }
    @Override
    public void run() {
        while (enabled){
            try {
                TimeUnit.MILLISECONDS.sleep((System.currentTimeMillis() - Config.startTimeStamp.get())%Config.interval.get());
                String path = DatabaseUtils.selectSingleImage();
                String message = DatabaseUtils.selectSingleMessage();
                if (path == null || message == null){
                    continue;
                }
                Config.allowBotIds.get().stream()
                        .map(Bot::getInstanceOrNull)
                        .filter(Objects::nonNull)
                        .forEach(bot -> Config.allowGroupIds.get().stream()
                                .map(bot::getGroup)
                                .filter(Objects::nonNull)
                                .forEach(g -> {
                                    try (ExternalResource externalResource = ExternalResource.create(new File(path))) {
                                        Image image = g.uploadImage(externalResource);
                                        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
                                        messageChainBuilder.add(message);
                                        messageChainBuilder.add(image);
                                        MessageChain messageChain = messageChainBuilder.build();
                                        g.sendMessage(messageChain);
                                    } catch (IOException e){
                                        Rantext.INSTANCE.getLogger().warning(e);
                                    }
                                }));
            } catch (InterruptedException e) {
                return;
            }
        }
    }
    public void disable(){
        enabled = false;
    }
    public void enable(){
        enabled = true;
    }
}
