// Copyright (c) 2021 MCUmbrella & contributors

package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The basic forum thread object in a 'forum' type channel.
 * <a>https://www.guilded.gg/docs/api/forums/ForumThread</a>
 */
public class ForumThread
{
    int id;
    String channelId, title, content, createdAt, createdBy, createdByBotId, createdByWebhookId;

    public int getId(){return id;}
    @Nullable public String getChannelId(){return channelId;}//TODO: get an accurate answer on whether its optional or not
    @Nullable public String getTitle(){return title;}//TODO: get an accurate answer on whether its optional or not
    @Nullable public String getContent(){return content;}//TODO: get an accurate answer on whether its optional or not
    public String getCreationTime(){return createdAt;}
    public String getCreatorId(){return createdBy;}
    @Nullable public String getBotCreatorId(){return createdByBotId;}
    @Nullable public String getWebhookCreatorId(){return createdByWebhookId;}

    public ForumThread setId(int id){this.id=id;return this;}
    public ForumThread setChannelId(String channelId){this.channelId=channelId;return this;}
    public ForumThread setTitle(String title){this.title=title;return this;}
    public ForumThread setContent(String content){this.content=content;return this;}
    public ForumThread setCreationTime(@Nonnull String createdAt){this.createdAt=createdAt;return this;}
    public ForumThread setCreatorId(@Nonnull String createdBy){this.createdBy=createdBy;return this;}
    public ForumThread setBotCreatorId(String createdByBotId){this.createdByBotId=createdByBotId;return this;}
    public ForumThread setWebhookCreatorId(String createdByWebhookId){this.createdByWebhookId=createdByWebhookId;return this;}

    public ForumThread(int id, String createdAt, String createdBy)
    {
        this.setId(id)
        .setCreationTime(createdAt)
        .setCreatorId(createdBy);
    }
    public ForumThread(){}

    @Nullable public ForumThread fromString(String rawString)
    {
        JSONObject json=new JSONObject(rawString);
        if(json.getStr("id")==null||json.getStr("createdAt")==null||json.getStr("createdBy")==null)
            throw new IllegalArgumentException("At least 1 basic key of ForumThread is missing");
        return this.setId(json.getInt("id"))
                .setChannelId(json.getStr("channelId"))
                .setTitle(json.getStr("title"))
                .setContent(json.getStr("content"))
                .setCreationTime(json.getStr("createdAt"))
                .setCreatorId(json.getStr("createdBy"))
                .setBotCreatorId(json.getStr("createdByBotId"))
                .setWebhookCreatorId(json.getStr("createdByWebhookId"));
    }

    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id",id)
                .set("channelId",channelId)
                .set("title",title)
                .set("content",content)
                .set("createdAt",createdAt)
                .set("createdBy",createdBy)
                .set("createdByBotId",createdByBotId)
                .set("createdByWebhookId",createdByWebhookId)
                .toString();
    }
}
