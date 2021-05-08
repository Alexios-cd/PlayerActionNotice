package com.fuwenjiang.mc;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 时间： 2021/5/8  10:25
 * <p>
 * 描述：
 * <p>
 * 作者： alexios
 * <p>
 * 版权： 版权： Copyright © 2021 alexios
 **/
public class MessagePushUtilsTest {
    @Test
    public void testPushWxMessage() {
        List<String> topicIds =new ArrayList<>();
        topicIds.add("xxxxx");
        MessagePushUtils.pushWXMessage("xxxx", "XXX上线了", topicIds, "xxx");
    }
    @Test
    public void pushWxMessage2() {
        String content = "玩家名称：" + "alxieos" + "\n" +
                "当前时间：" + new DateTime().toString(DatePattern.NORM_DATETIME_FORMAT) + "\n" +
                "IP地址：" + "127.0.0.1" + "\n" +
                "世界名称：" + "ender" + "\n" +
                "世界时间：" + new DateTime(System.currentTimeMillis()).toString(DatePattern.NORM_DATETIME_FORMAT);
        String summary ="alexios" + "已上线" + "\n" +
                new DateTime().toString(DatePattern.NORM_DATETIME_FORMAT) + "\n";
        List<String> topicIds =new ArrayList<>();
        topicIds.add("xxx");
        MessagePushUtils.pushWXMessage("xxxx", summary, topicIds, content);
    }

}