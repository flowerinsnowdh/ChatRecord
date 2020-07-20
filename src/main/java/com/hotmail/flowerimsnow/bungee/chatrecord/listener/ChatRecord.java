package com.hotmail.flowerimsnow.bungee.chatrecord.listener;

import com.hotmail.flowerimsnow.bungee.chatrecord.manager.SQLManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatRecord implements Listener {
    @EventHandler
    public void onChat(ChatEvent e) {
        if (e.getSender() instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) e.getSender();
            if (needNotRecord(e.getMessage())) return; // 不需要记录的指令
            SQLManager.recordAsync(player,e.getMessage());
        }
    }
    private boolean needNotRecord(String msg) {
        return isCmd(msg,"l") || isCmd(msg,"login") ||
                isCmd(msg,"reg") || isCmd(msg,"register") ||
                isCmd(msg,"captcha");
    }
    private boolean isCmd(String msg,String cmd) {
        return msg.substring(1).equalsIgnoreCase(cmd) || msg.substring(1).toLowerCase().startsWith(cmd.toLowerCase() + " ");
    }
}
