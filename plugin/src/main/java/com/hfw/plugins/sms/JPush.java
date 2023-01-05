package com.hfw.plugins.sms;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import lombok.Data;
import org.apache.http.HttpStatus;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 极光推送
 */
@Data
public class JPush {
    private String appkey;
    private String secret;
    private Boolean debug;

    /**
     * 发送通知
     *
     * @param title  通知标题
     * @param desc   通知内容
     * @param alias  通知别名
     * @param extras 附加数据
     */
    private void pushNotification(String title, String desc, Set<String> alias, Map<String, String> extras) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(secret, appkey, null, clientConfig);
        PushPayload pushPayLoad = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                //.setNotification(Notification.android(desc, title, extras))
                //.setNotification(Notification.ios(Paramap.create().put("title", title).put("body", desc), extras))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder().setAlert(desc).setTitle(title).addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder().setAlert(IosAlert.newBuilder().setTitleAndBody(title, "", desc).build()).incrBadge(1).setSound("default").addExtras(extras).build())
                        .build())
                .setAudience(Audience.alias(alias))
                .setOptions(Options.newBuilder().setApnsProduction(!debug).build())
                .build();
        PushResult result;
        try {
            result = jpushClient.sendPush(pushPayLoad);
            if (result.getResponseCode() != HttpStatus.SC_OK) {
                throw new RuntimeException(result.getOriginalContent());
            }
        } catch (APIConnectionException e) {
            throw new RuntimeException(e.getMessage());
        } catch (APIRequestException e) {
            e.printStackTrace();
            //没有设置对应的别名会抛出该异常
            //throw new RuntimeException(e.getMessage());
        }
    }

    public void pushAllNotification(String title, String desc, Map<String, String> extras) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(secret, appkey, null, clientConfig);
        PushPayload pushPayLoad = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder().setAlert(desc).setTitle(title).addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder().setAlert(IosAlert.newBuilder().setTitleAndBody(title, "", desc).build()).incrBadge(1).setSound("default").addExtras(extras).build())
                        .build())
                .setAudience(Audience.all())
                .setOptions(Options.newBuilder().setApnsProduction(!debug).build())
                .build();
        PushResult result;
        try {
            result = jpushClient.sendPush(pushPayLoad);
            if (result.getResponseCode() != HttpStatus.SC_OK) {
                throw new RuntimeException(result.getOriginalContent());
            }
        } catch (APIConnectionException e) {
            throw new RuntimeException(e.getMessage());
        } catch (APIRequestException e) {
            e.printStackTrace();
            //throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 发送通知给某个用户
     * 需要APP设置用户id为别名
     *
     * @param title  通知标题
     * @param desc   通知内容
     * @param userId 用户id
     * @param extras 附加数据
     */
    public void notification(String title, String desc, String userId, Map<String, String> extras) {
        Set<String> set = new HashSet<String>();
        set.add(userId);
        pushNotification(title, desc, set, extras);
    }

    /**
     * 群发通知给用户集合
     * 需要APP设置用户id为别名
     *
     * @param title   通知标题
     * @param desc    通知内容
     * @param userIds 用户id集合
     * @param extras  附加数据
     */
    public void notification(String title, String desc, Set<String> userIds, Map<String, String> extras) {
        pushNotification(title, desc, userIds, extras);
    }

    /**
     * 发送自定义消息
     *
     * @param msg    消息内容
     * @param extras 附加数据
     * @param alias  通知别名
     */
    public void pushMessage(String msg, Map<String, String> extras, String alias) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(secret, appkey, null, clientConfig);
        Message.Builder builder = Message.newBuilder().setMsgContent(msg);
        if (extras != null) {
            builder.addExtras(extras);
        }
        PushPayload pushPayLoad = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setMessage(builder.build())
                .setOptions(Options.newBuilder().setApnsProduction(!debug).build())
                .build();
        PushResult result;
        try {
            result = jpushClient.sendPush(pushPayLoad);
            if (result.getResponseCode() != HttpStatus.SC_OK) {
                throw new RuntimeException(result.getOriginalContent());
            }
        } catch (APIConnectionException e) {
            throw new RuntimeException(e.getMessage());
        } catch (APIRequestException e) {
            e.printStackTrace();
            //throw new GeneralException(e.getMessage());
        }
    }

    /**
     * 发送自定义消息
     * 需要APP设置用户id为别名
     *
     * @param msg    消息内容
     * @param extras 附加数据
     * @param userId 用户id
     */
    public void message(String msg, Map<String, String> extras, String userId) {
        pushMessage(msg, extras, userId);
    }

    /**
     * 发送自定义消息
     * 需要APP设置用户id为别名
     *
     * @param msg    消息内容
     * @param userId 用户id
     */
    public void message(String msg, String userId) {
        pushMessage(msg, null, userId);
    }

    /**
     * 群发自定义消息给用户集合
     * 需要APP设置用户id为别名
     * @param msg 消息内容
     * @param from 附加数据
     * @param userIds 用户id集合

    public static void message(String msg, Map<String,String> extras, Set<String> userIds) {
    pushMessage(msg, extras, JSON.toJSONString(userIds));
    }*/

}