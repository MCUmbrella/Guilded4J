/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * The basic forum thread object in a 'forum' type channel.<br>
 * <a href="https://www.guilded.gg/docs/api/forums/ForumThread" target=_blank>https://www.guilded.gg/docs/api/forums/ForumThread</a>
 */
public class ForumTopic
{
    int id;
    String serverId, channelId, title, content, createdAt, createdBy, createdByWebhookId, updatedAt, bumpedAt;
    boolean isPinned = false, isLocked = false;
    Mention mentions;

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
    public String getTitle(){return title;}

    /**
     * Get the content of the thread.
     */
    public String getContent(){return content;}

    /**
     * Get the ISO 8601 timestamp string that the forum thread was created at.
     */
    public String getCreatedAt(){return createdAt;}

    /**
     * The ID of the user who created this forum thread.
     */
    public String getCreatedBy(){return createdBy;}

    /**
     * Get the ID of the webhook who created this forum thread (if it was created by a webhook).
     * @return A UUID string of the webhook who created the thread. If the creator isn't webhook, return {@code null}.
     */
    public String getCreatedByWebhookId(){return createdByWebhookId;}

    /**
     * Get the ISO 8601 timestamp that the forum topic was updated at, if relevant.
     */
    public String getUpdatedAt(){return updatedAt;}

    /**
     * Get the ISO 8601 timestamp that the forum topic was bumped at.
     * This timestamp is updated whenever there is any activity on the posts within the forum topic.
     */
    public String getBumpedAt(){return bumpedAt;}

    public Mention getMentions(){throw new UnsupportedOperationException("https://www.guilded.gg/Guilded4J-Cafe/blog/Announcements/About-the-APIs-new-Mentions-feature");}

    /**
     * Check if the topic is pinned.
     */
    public boolean isPinned(){return isPinned;}

    /**
     * Check if the topic is locked.
     */
    public boolean isLocked(){return isLocked;}

    public ForumTopic setId(int id)
    {
        this.id = id;
        return this;
    }

    public ForumTopic setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    public ForumTopic setChannelId(String channelId)
    {
        this.channelId = channelId;
        return this;
    }

    public ForumTopic setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public ForumTopic setContent(String content)
    {
        this.content = content;
        return this;
    }

    public ForumTopic setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    public ForumTopic setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    public ForumTopic setCreatedByWebhookId(String createdByWebhookId)
    {
        this.createdByWebhookId = createdByWebhookId;
        return this;
    }

    public ForumTopic setUpdatedAt(String updatedAt)
    {
        this.updatedAt = updatedAt;
        return this;
    }

    public ForumTopic setBumpedAt(String bumpedAt)
    {
        this.bumpedAt = bumpedAt;
        return this;
    }

    public ForumTopic setIsPinned(Boolean isPinned)
    {
        this.isPinned = isPinned != null ? isPinned : false;
        return this;
    }

    public ForumTopic setIsLocked(Boolean isLocked)
    {
        this.isPinned = isLocked != null ? isLocked : false;
        return this;
    }

    /**
     * Use the given JSON object to generate ForumTopic object.
     * @param json The JSON object.
     * @return ForumTopic object.
     * @throws IllegalArgumentException when the essential fields are not set.
     */
    public static ForumTopic fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getStr("id"),
                json.getStr("serverId"),
                json.getStr("channelId"),
                json.getStr("title"),
                json.getStr("createdAt"),
                json.getStr("createdBy"),
                json.getStr("content")
        );
        return new ForumTopic()
                .setId(json.getInt("id"))
                .setServerId(json.getStr("serverId"))
                .setChannelId(json.getStr("channelId"))
                .setTitle(json.getStr("title"))
                .setContent(json.getStr("content"))
                .setCreatedAt(json.getStr("createdAt"))
                .setCreatedBy(json.getStr("createdBy"))
                .setCreatedByWebhookId(json.getStr("createdByWebhookId"))
                .setUpdatedAt(json.getStr("updatedAt"))
                .setBumpedAt(json.getStr("bumpedAt"))
                .setIsPinned(json.getBool("isPinned"))
                .setIsLocked(json.getBool("isLocked"));
    }

    /**
     * Convert the ForumTopic object to JSON string.
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
                .set("updatedAt", updatedAt)
                .set("bumpedAt", bumpedAt)
                .set("isPinned", isPinned)
                .set("isLocked", isLocked)
                .toString();
    }
}
