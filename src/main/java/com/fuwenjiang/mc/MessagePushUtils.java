package com.fuwenjiang.mc;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

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
    public static boolean pushWXLoginMessage(PlayerJoinEvent event, String appToken, List<String> topicIds) {
        String content = "【玩家上线通知:】\n" +
                "玩家名称：" + event.getPlayer().getName() + "\n" +
                "当前时间：" + new DateTime().toString(DatePattern.NORM_DATETIME_FORMAT) + "\n" +
                "IP地址：" + event.getPlayer().getAddress() + "\n" +
                "世界名称：" + event.getPlayer().getWorld().getName() + "\n" +
                "世界时间：" + new DateTime(event.getPlayer().getWorld().getTime()).toString(DatePattern.NORM_DATETIME_FORMAT);
        String summary = "【玩家上线通知:】\n" + event.getPlayer().getName() + "已上线" + "\n" +
                new DateTime().toString(DatePattern.NORM_DATETIME_FORMAT) + "\n";
        return MessagePushUtils.pushWXMessage(appToken, summary, topicIds, content);
    }

    public static boolean pushWXMessage(String appToken, String summary, List<String> topicId, String content) {
        try {
            Map<String, Object> pMap = new HashMap<>();
            String url = "http://wxpusher.zjiecode.com/api/send/message";
            pMap.put("appToken", appToken);
            pMap.put("content", content);
            pMap.put("summary", summary);
            pMap.put("topicIds", topicId);
            HttpResponse execute = HttpUtil.createPost(url).contentType("application/json").body(JSON.toJSONString(pMap)).execute();
            System.out.println(execute.body());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean pushWXQuitMessage(PlayerQuitEvent event, String appToken, List<String> topicIds) {
        String content = "【玩家下线通知:】\n" +
                "玩家名称：" + event.getPlayer().getName() + "\n" +
                "当前时间：" + new DateTime().toString(DatePattern.NORM_DATETIME_FORMAT) + "\n" +
                "IP地址：" + event.getPlayer().getAddress() + "\n" +
                "世界名称：" + event.getPlayer().getWorld().getName() + "\n" +
                "世界时间：" + new DateTime(event.getPlayer().getWorld().getTime()).toString(DatePattern.NORM_DATETIME_FORMAT);
        String summary = "【玩家下线通知:】\n" + event.getPlayer().getName() + "已下线" + "\n" +
                new DateTime().toString(DatePattern.NORM_DATETIME_FORMAT) + "\n";
        return MessagePushUtils.pushWXMessage(appToken, summary, topicIds, content);
    }
}
