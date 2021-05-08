package com.fuwenjiang.mc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * <p>
 * 时间： 2021/5/7  18:22
 * <p>
 * 描述：
 * <p>
 * 作者： alexios
 * <p>
 * 版权： Copyright © 2021 alexios
 **/
public class NoticePlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getLogger().info("玩家事件插件已经启动");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        getLogger().info(player.getDisplayName() + " 登录服务器");

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        getLogger().info(player.getDisplayName() + " 退出服务器");
    }

    @Override
    public void onDisable() {
        getLogger().info("玩家事件插件卸载完成");
    }
}
