/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.Util;

/**
 * The Webhook object.<br>
 * <a href="https://www.guilded.gg/docs/api/webhook/Webhook">https://www.guilded.gg/docs/api/webhook/Webhook</a>
 */
public class Webhook
{
    private String id, name, serverId, channelId, createdAt, createdBy, deletedAt, token;

    /**
     * Get the ID of the webhook.
     */
    public String getId()
    {
        return id;
    }

    /**
     * Get the name of the webhook.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get the ID of the server that the webhook belongs to.
     */
    public String getServerId()
    {
        return serverId;
    }

    /**
     * Get the ID of the channel that the webhook belongs to.
     */
    public String getChannelId()
    {
        return channelId;
    }

    /**
     * Get the ISO 8601 timestamp that the webhook was created at.
     */
    public String getCreationTime()
    {
        return createdAt;
    }

    /**
     * Get the ID of the user who created this webhook.
     */
    public String getCreator()
    {
        return createdBy;
    }

    /**
     * Get the ISO 8601 timestamp that the webhook was deleted at.
     */
    public String getDeletionTime()
    {
        return deletedAt;
    }

    /**
     * Get the token of the webhook.
     */
    public String getToken()
    {
        return token;
    }

    public Webhook setId(String id)
    {
        this.id = id;
        return this;
    }

    public Webhook setName(String name)
    {
        this.name = name;
        return this;
    }

    public Webhook setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    public Webhook setChannelId(String channelId)
    {
        this.channelId = channelId;
        return this;
    }

    public Webhook setCreationTime(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    public Webhook setCreator(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    public Webhook setDeletionTime(String deletedAt)
    {
        this.deletedAt = deletedAt;
        return this;
    }

    public Webhook setToken(String token)
    {
        this.token = token;
        return this;
    }

    /**
     * Generate a Webhook object from the given JSON string.
     * @return Webhook object.
     * @throws IllegalArgumentException when the essential fields are not set.
     * @throws ClassCastException when the provided String's content isn't JSON format.
     */
    public static Webhook fromString(String jsonString)
    {
        if(JSONUtil.isTypeJSON(jsonString))
        {
            JSONObject json = new JSONObject(jsonString);
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
        else throw new ClassCastException("The provided String's content can't be converted to JSON object");
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
