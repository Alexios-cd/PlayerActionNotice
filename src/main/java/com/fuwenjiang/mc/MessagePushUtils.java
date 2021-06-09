package com.fuwenjiang.mc;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * <p>
 * 时间： 2021/5/8  10:20
 * <p>
 * 描述：
 * <p>
 * 作者： alexios
 * <p>
 * 版权： 版权： Copyright © 2021 alexios
 **/
public class MessagePushUtils {
    public static void pushWXLoginMessage(PlayerJoinEvent event, String appToken, List<String> topicIds) {
        String content = "【玩家上线通知:】\n" +
                "玩家名称：" + event.getPlayer().getName() + "\n" +
                "当前时间：" + new DateTime().toString(DatePattern.NORM_DATETIME_FORMAT) + "\n" +
                "IP地址：" + event.getPlayer().getAddress() + "\n" +
                "世界名称：" + event.getPlayer().getWorld().getName() + "\n" +
                "世界时间：" + new DateTime(event.getPlayer().getWorld().getTime()).toString(DatePattern.NORM_DATETIME_FORMAT);
        String summary = "【玩家上线通知:】\n" + event.getPlayer().getName() + "已上线" + "\n" +
                new DateTime().toString(DatePattern.NORM_DATETIME_FORMAT) + "\n";
        MessagePushUtils.pushWXMessage(appToken, summary, topicIds, content);
    }

    public static void pushWXMessage(String appToken, String summary, List<String> topicId, String content) {
        try {
            Map<String, Object> pMap = new HashMap<>();
            String url = "http://wxpusher.zjiecode.com/api/send/message";
            pMap.put("appToken", appToken);
            pMap.put("content", content);
            pMap.put("summary", summary);
            pMap.put("topicIds", topicId);
            HttpResponse execute = HttpUtil.createPost(url).contentType("application/json").body(JSON.toJSONString(pMap)).execute();
            System.out.println(execute.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pushWXQuitMessage(PlayerQuitEvent event, String appToken, List<String> topicIds) {
        String content = "【玩家下线通知:】\n" +
                "玩家名称：" + event.getPlayer().getName() + "\n" +
                "当前时间：" + new DateTime().toString(DatePattern.NORM_DATETIME_FORMAT) + "\n" +
                "IP地址：" + event.getPlayer().getAddress() + "\n" +
                "世界名称：" + event.getPlayer().getWorld().getName() + "\n" +
                "世界时间：" + new DateTime(event.getPlayer().getWorld().getTime()).toString(DatePattern.NORM_DATETIME_FORMAT);
        String summary = "【玩家下线通知:】\n" + event.getPlayer().getName() + "已下线" + "\n" +
                new DateTime().toString(DatePattern.NORM_DATETIME_FORMAT) + "\n";
        MessagePushUtils.pushWXMessage(appToken, summary, topicIds, content);
    }

    public static void pushMessageDelay(PlayerEvent event, Collection<? extends Player> onlinePlayers, String wxAppToken, List<String> topicIds, Logger logger) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String playerName = event.getPlayer().getName();
                List<String> collect = onlinePlayers.stream().map(Player::getName).collect(Collectors.toList());
                if (event instanceof PlayerQuitEvent) {
                    if (collect.contains(playerName)) {
                        logger.info("用户短时间内退出登陆后重新登录，不予推送退出登录消息");
                    } else {
                        logger.info("用户退出登录后指定时间内依然没有登录，开始推送退出登录消息");
                        pushWXQuitMessage((PlayerQuitEvent) event, wxAppToken, topicIds);
                    }

                } else if (event instanceof PlayerJoinEvent) {
                    if (collect.contains(playerName)) {
                        logger.info("用户登录后，过了指定时间，依然在线，开始推送登录服务器消息");
                        pushWXLoginMessage((PlayerJoinEvent) event, wxAppToken, topicIds);
                    } else {
                        logger.info("用户登录服务器后指定时间内退出了服务器，不予推送登录服务器消息");
                    }
                } else {
                    logger.warning("未识别用户动作，不予推送");
                }
            }
        };
        timer.schedule(timerTask, 15 * 1000);
    }

}
