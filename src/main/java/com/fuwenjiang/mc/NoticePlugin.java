package com.fuwenjiang.mc;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.List;

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

    private String wxAppToken;
    private List<String> topicIds;
    private boolean isOpNotice;

    @Override
    public void onEnable() {
        getLogger().info("玩家事件插件已经启动");
        this.saveDefaultConfig();
        wxAppToken = this.getConfig().getString("wxpusher.appToken");
        topicIds = this.getConfig().getStringList("wxpusher.topicIds");
        isOpNotice = this.getConfig().getBoolean("wxpusher.isOpNotice", false);
        if (StrUtil.isNotBlank(wxAppToken) && CollUtil.isNotEmpty(topicIds)) {
            getLogger().info("获取到wxAppToken：" + wxAppToken);
            getLogger().info("topicIds：" + topicIds);
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            getLogger().warning("配置文件错误，功能失效，各种事件将不予监听");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        getLogger().info(player.getDisplayName() + " 登录服务器");
        if (player.isOp() && !isOpNotice) {
            getLogger().info("管理员：" + player.getDisplayName() + "登录服务器，不予推送");
        } else {
            Collection<? extends Player> onlinePlayers = getServer().getOnlinePlayers();
//            MessagePushUtils.pushWXLoginMessage(event, wxAppToken, topicIds);
            MessagePushUtils.pushMessageDelay(event, onlinePlayers, wxAppToken, topicIds, getLogger());
            getLogger().info("已推送微信通知");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        getLogger().info(player.getDisplayName() + " 退出服务器");
        if (player.isOp() && !isOpNotice) {
            getLogger().info("管理员：" + player.getDisplayName() + "退出服务器，不予推送");
        } else {
//            MessagePushUtils.pushWXQuitMessage(event, wxAppToken, topicIds);
            Collection<? extends Player> onlinePlayers = getServer().getOnlinePlayers();
            MessagePushUtils.pushMessageDelay(event, onlinePlayers, wxAppToken, topicIds, getLogger());
            getLogger().info("已推送微信通知");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("玩家事件插件卸载完成");
    }


}
