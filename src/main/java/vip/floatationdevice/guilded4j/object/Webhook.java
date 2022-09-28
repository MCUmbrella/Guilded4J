/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * The Webhook object.<br>
 * <a href="https://www.guilded.gg/docs/api/webhook/Webhook" target=_blank>https://www.guilded.gg/docs/api/webhook/Webhook</a>
 */
public class Webhook
{
    private String id, name, serverId, channelId, createdAt, createdBy, deletedAt, token;

    /**
     * Generate a Webhook object from the given JSON object.
     * @throws IllegalArgumentException when the essential fields are not set.
     */
    public static Webhook fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getStr("id"),
                json.getStr("name"),
                json.getStr("serverId"),
                json.getStr("channelId"),
                json.getStr("createdAt"),
                json.getStr("createdBy")
        );
        return new Webhook()
                .setId(json.getStr("id"))
                .setName(json.getStr("name"))
                .setServerId(json.getStr("serverId"))
                .setChannelId(json.getStr("channelId"))
                .setCreationTime(json.getStr("createdAt"))
                .setCreator(json.getStr("createdBy"))
                .setDeletionTime(json.getStr("deletedAt"))
                .setToken(json.getStr("token"));
    }

    /**
     * Get the ID of the webhook.
     */
    public String getId()
    {
        return id;
    }

    public Webhook setId(String id)
    {
        this.id = id;
        return this;
    }

    /**
     * Get the name of the webhook.
     */
    public String getName()
    {
        return name;
    }

    public Webhook setName(String name)
    {
        this.name = name;
        return this;
    }

    /**
     * Get the ID of the server that the webhook belongs to.
     */
    public String getServerId()
    {
        return serverId;
    }

    public Webhook setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    /**
     * Get the ID of the channel that the webhook belongs to.
     */
    public String getChannelId()
    {
        return channelId;
    }

    public Webhook setChannelId(String channelId)
    {
        this.channelId = channelId;
        return this;
    }

    /**
     * Get the ISO 8601 timestamp that the webhook was created at.
     */
    public String getCreationTime()
    {
        return createdAt;
    }

    public Webhook setCreationTime(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Get the ID of the user who created this webhook.
     */
    public String getCreator()
    {
        return createdBy;
    }

    public Webhook setCreator(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * Get the ISO 8601 timestamp that the webhook was deleted at.
     */
    public String getDeletionTime()
    {
        return deletedAt;
    }

    public Webhook setDeletionTime(String deletedAt)
    {
        this.deletedAt = deletedAt;
        return this;
    }

    /**
     * Get the token of the webhook.
     */
    public String getToken()
    {
        return token;
    }

    public Webhook setToken(String token)
    {
        this.token = token;
        return this;
    }

    /**
     * Convert the Webhook object to a JSON string.
     * @return JSON string.
     */
    public String toString()
    {
        return new JSONObject()
                .set("id", id)
                .set("name", name)
                .set("serverId", serverId)
                .set("channelId", channelId)
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("deletedAt", deletedAt)
                .set("token", token)
                .toString();
    }
}
