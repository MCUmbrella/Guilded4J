/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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
     * Generate empty ContentReaction object - make sure to set all the essential fields before using it.
     */
    public ContentReaction(){}

    /**
     * Generate ContentReaction object from JSON string.
     * @param jsonString The JSON string.
     */
    public ContentReaction(String jsonString){fromString(jsonString);}

    /**
     * Use the given JSON string to set up ContentReaction object.
     * @param rawString A JSON string.
     * @return ContentReaction object.
     * @throws IllegalArgumentException when the string is missing at least 1 of the 3 essential keys.
     * @throws ClassCastException when the provided String's content isn't JSON format.
     */
    public static ContentReaction fromString(String rawString)
    {
        if(JSONUtil.isTypeJSON(rawString))
        {
            JSONObject json = new JSONObject(rawString);
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
        else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }

    /**
     * Convert the ContentReaction object to JSON string.
     * @return A JSON string.
     */
    @Override public String toString()
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
