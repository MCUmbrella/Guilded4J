/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.Util;

/**
 * The basic forum thread object in a 'forum' type channel.<br>
 * <a href="https://www.guilded.gg/docs/api/forums/ForumThread" target=_blank>https://www.guilded.gg/docs/api/forums/ForumThread</a>
 */
public class ForumThread
{
    int id;
    String serverId, channelId, title, content, createdAt, createdBy, createdByWebhookId;

    /**
     * Get the thread's ID (it is not UUID).
     */
    public int getId(){return id;}

    /**
     * Get the ID of the server to which the thread belongs.
     */
    public String getServerId(){return serverId;}

    /**
     * Get the UUID of the channel to which the thread belongs.
     */
    public String getChannelId(){return channelId;}

    /**
     * Get the title of the thread.
     */
    public String getTitle(){return title;}//TODO: it has been marked as 'optional' for a few months but I still can't believe it

    /**
     * Get the content of the thread.
     */
    public String getContent(){return content;}//TODO: ↑

    /**
     * Get the ISO 8601 timestamp string that the forum thread was created at.
     */
    public String getCreationTime(){return createdAt;}

    /**
     * The ID of the user who created this forum thread.
     */
    public String getCreatorId(){return createdBy;}

    /**
     * Get the ID of the webhook who created this forum thread.
     * @return A UUID string of the webhook who created the thread. If the creator isn't webhook, return {@code null}.
     */
    public String getWebhookCreatorId(){return createdByWebhookId;}

    public ForumThread setId(int id)
    {
        this.id = id;
        return this;
    }

    public ForumThread setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    public ForumThread setChannelId(String channelId)
    {
        this.channelId = channelId;
        return this;
    }

    public ForumThread setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public ForumThread setContent(String content)
    {
        this.content = content;
        return this;
    }

    public ForumThread setCreationTime(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    public ForumThread setCreatorId(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    public ForumThread setWebhookCreatorId(String createdByWebhookId)
    {
        this.createdByWebhookId = createdByWebhookId;
        return this;
    }

    /**
     * Use the given JSON object to generate ForumThread object.
     * @param json The JSON object.
     * @return ForumThread object.
     * @throws IllegalArgumentException when the essential fields are not set.
     */
    public static ForumThread fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getStr("id"),
                json.getStr("serverId"),
                json.getStr("channelId"),
                json.getStr("createdAt"),
                json.getStr("createdBy")
        );
        return new ForumThread()
                .setId(json.getInt("id"))
                .setServerId(json.getStr("serverId"))
                .setChannelId(json.getStr("channelId"))
                .setTitle(json.getStr("title"))
                .setContent(json.getStr("content"))
                .setCreationTime(json.getStr("createdAt"))
                .setCreatorId(json.getStr("createdBy"))
                .setWebhookCreatorId(json.getStr("createdByWebhookId"));
    }

    /**
     * Convert the ForumThread object to JSON string.
     * @return A JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("serverId", serverId)
                .set("channelId", channelId)
                .set("title", title)
                .set("content", content)
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("createdByWebhookId", createdByWebhookId)
                .toString();
    }
}
