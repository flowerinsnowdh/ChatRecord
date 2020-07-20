package com.hotmail.flowerimsnow.bungee.chatrecord;

import com.hotmail.flowerimsnow.bungee.chatrecord.listener.ChatRecord;
import com.hotmail.flowerimsnow.bungee.chatrecord.manager.ConfigManager;
import com.hotmail.flowerimsnow.bungee.chatrecord.manager.SQLManager;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {
    private static Main instance;
    @Override
    public void onEnable() {
        instance = this;
        ConfigManager.saveDefaultConfig();
        ConfigManager.reloadConfig();

        SQLManager.connect();
        SQLManager.initTable();

        regListeners(new ChatRecord());
    }
    @Override
    public void onDisable() {
        getProxy().getScheduler().cancel(this);
        instance = null;
    }
    private void regListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            getProxy().getPluginManager().registerListener(this,listener);
        }
    }
    public static Main getInstance() {
        return instance;
    }
}
