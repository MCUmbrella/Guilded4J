/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * The reaction emote object.<br>
 * <a href="https://www.guilded.gg/docs/api/reactions/ContentReaction" target=_blank>https://www.guilded.gg/docs/api/reactions/ContentReaction</a>
 */
public class ContentReaction
{
    private int id;
    private String serverId, createdAt, createdBy, createdByWebhookId;

    /**
     * Get the ID of the reaction emote.
     */
    public int getId(){return id;}

    /**
     * Get the server ID of the reaction emote.
     */
    public String getServerId(){return serverId;}

    /**
     * Get the reaction's creation time.
     */
    public String getCreationTime(){return createdAt;}

    /**
     * Get the ID of the reaction's creator.
     */
    public String getCreatorId(){return createdBy;}

    /**
     * Get the UUID of the webhook who created the reaction.
     * @return A UUID string of the webhook who created the reaction. If the creator isn't webhook, return {@code null}.
     */
    public String getWebhookCreatorId(){return createdByWebhookId;}

    public ContentReaction setId(int id)
    {
        this.id = id;
        return this;
    }

    public ContentReaction setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    public ContentReaction setCreationTime(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    public ContentReaction setCreatorId(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    public ContentReaction setWebhookCreatorId(String createdByWebhookId)
    {
        this.createdByWebhookId = createdByWebhookId;
        return this;
    }

    /**
     * Use the given JSON object to set up ContentReaction object.
     * @throws IllegalArgumentException when the essential fields are missing.
     */
    public static ContentReaction fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getStr("id"),
                json.getStr("createdAt"),
                json.getStr("createdBy")
        );
        return new ContentReaction()
                .setId(json.getInt("id"))
                .setServerId(json.getStr("serverId"))
                .setCreationTime(json.getStr("createdAt"))
                .setCreatorId(json.getStr("createdBy"))
                .setWebhookCreatorId(json.getStr("createdByWebhookId"));
    }

    /**
     * Convert the ContentReaction object to JSON string.
     * @return A JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("serverId", serverId)
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("createdByWebhookId", createdByWebhookId)
                .toString();
    }
}
