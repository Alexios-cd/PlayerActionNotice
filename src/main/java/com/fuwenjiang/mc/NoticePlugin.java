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

    @Override
    public void onEnable() {

        getLogger().info("玩家事件插件已经启动");
        Bukkit.getPluginManager().registerEvents(this, this);
        this.saveDefaultConfig();
        wxAppToken = this.getConfig().getString("wxpusher.appToken");
        topicIds = this.getConfig().getStringList("wxpusher.topicIds");
        if (StrUtil.isBlank(wxAppToken) || CollUtil.isEmpty(topicIds)) {
            getLogger().warning("配置文件错误，功能失效");
        } else {
            getLogger().info("获取到wxAppToken：" + wxAppToken);
            getLogger().info("topicIds：" + topicIds);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        getLogger().info(player.getDisplayName() + " 登录服务器");
        if (StrUtil.isBlank(wxAppToken) || CollUtil.isEmpty(topicIds)) {
            getLogger().info("配置参数错误，不予推送。");
        } else {
            MessagePushUtils.pushWXLoginMessage(event, wxAppToken, topicIds);
            getLogger().info("已推送微信通知");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        getLogger().info(player.getDisplayName() + " 退出服务器");
        if (StrUtil.isBlank(wxAppToken) || CollUtil.isEmpty(topicIds)) {
            getLogger().info("配置参数错误，不予推送。");
        } else {
            MessagePushUtils.pushWXQuitMessage(event, wxAppToken, topicIds);
            getLogger().info("已推送微信通知");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("玩家事件插件卸载完成");
    }


}
