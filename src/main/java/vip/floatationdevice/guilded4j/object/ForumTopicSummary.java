/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * The summary of a forum topic.<br>
 * <a href="https://www.guilded.gg/docs/api/forums/ForumTopicSummary" target=_blank>https://www.guilded.gg/docs/api/forums/ForumTopicSummary</a>
 */
public class ForumTopicSummary
{
    int id = 0;
    String serverId, channelId, title, createdAt, createdBy, updatedAt, bumpedAt, createdByWebhookId;

    public static ForumTopicSummary fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getInt("id"),
                json.getStr("serverId"),
                json.getStr("channelId"),
                json.getStr("title"),
                json.getStr("createdAt"),
                json.getStr("createdBy")
        );

        return new ForumTopicSummary()
                .setId(json.getInt("id"))
                .setServerId(json.getStr("serverId"))
                .setChannelId(json.getStr("channelId"))
                .setTitle(json.getStr("title"))
                .setCreatedAt(json.getStr("createdAt"))
                .setCreatedBy(json.getStr("createdBy"))
                .setCreatedByWebhookId(json.getStr("createdByWebhookId"))
                .setUpdatedAt(json.getStr("updatedAt"))
                .setBumpedAt(json.getStr("bumpedAt"));
    }

    public int getId(){return id;}

    public ForumTopicSummary setId(int id)
    {
        this.id = id;
        return this;
    }

    public String getServerId(){return serverId;}

    public ForumTopicSummary setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    public String getChannelId(){return channelId;}

    public ForumTopicSummary setChannelId(String channelId)
    {
        this.channelId = channelId;
        return this;
    }

    public String getTitle(){return title;}

    public ForumTopicSummary setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public String getCreatedAt(){return createdAt;}

    public ForumTopicSummary setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    public String getCreatedBy(){return createdBy;}

    public ForumTopicSummary setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    public String getUpdatedAt(){return updatedAt;}

    public ForumTopicSummary setUpdatedAt(String updatedAt)
    {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getBumpedAt(){return bumpedAt;}

    public ForumTopicSummary setBumpedAt(String bumpedAt)
    {
        this.bumpedAt = bumpedAt;
        return this;
    }

    public String getCreatedByWebhookId(){return createdByWebhookId;}

    public ForumTopicSummary setCreatedByWebhookId(String createdByWebhookId)
    {
        this.createdByWebhookId = createdByWebhookId;
        return this;
    }

    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("serverId", serverId)
                .set("channelId", channelId)
                .set("title", title)
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("createdByWebhookId", createdByWebhookId)
                .set("updatedAt", updatedAt)
                .set("bumpedAt", bumpedAt)
                .toString();
    }
}
