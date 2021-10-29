/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The reaction emote object.
 * <a>https://www.guilded.gg/docs/api/reactions/ContentReaction</a>
 */
public class ContentReaction
{
    private int id;
    private String createdAt, createdBy, createdByBotId, createdByWebhookId;

    /**
     * Get the ID of the reaction emote.
     */
    public int getId(){return id;}

    /**
     * Get the reaction's creation time.
     */
    public String getCreationTime(){return createdAt;}

    /**
     * Get the ID of the reaction's creator.
     */
    public String getCreatorId(){return createdBy;}

    /**
     * Get the UUID of the bot who created the reaction.
     * @return A UUID string of the bot who created the reaction. If the creator isn't bot, return {@code null}.
     */
    @Nullable public String getBotCreatorId(){return createdByBotId;}

    /**
     * Get the UUID of the webhook who created the reaction.
     * @return A UUID string of the webhook who created the reaction. If the creator isn't webhook, return {@code null}.
     */
    @Nullable public String getWebhookCreatorId(){return createdByWebhookId;}

    public ContentReaction setId(int id){this.id=id;return this;}
    public ContentReaction setCreationTime(@Nonnull String createdAt){this.createdAt=createdAt;return this;}
    public ContentReaction setCreatorId(@Nonnull String createdBy){this.createdBy=createdBy;return this;}
    public ContentReaction setBotCreatorId(String createdByBotId){this.createdByBotId=createdByBotId;return this;}
    public ContentReaction setWebhookCreatorId(String createdByWebhookId){this.createdByWebhookId=createdByWebhookId;return this;}

    /**
     * Create ContentReaction object using 3 given essential keys.
     * @param id The ID of the emote.
     * @param createdAt The creation time.
     * @param createdBy The ID of the emote's creator.
     */
    public ContentReaction(int id, String createdAt, String createdBy)
    {
        this.id=id;
        this.createdAt=createdAt;
        this.createdBy=createdBy;
    }

    /**
     * Default constructor.
     */
    public ContentReaction(){}

    /**
     * Use the given JSON string to set up ContentReaction object.
     * @param rawString A JSON string.
     * @return ContentReaction object.
     * @throws IllegalArgumentException when the string is missing at least 1 of the 3 essential keys.
     * @throws ClassCastException when the provided String's content isn't JSON format.
     */
    public ContentReaction fromString(String rawString)
    {
        if(JSONUtil.isJson(rawString))
        {
            JSONObject json=new JSONObject(rawString);
            if(json.getStr("id")==null||json.getStr("createdAt")==null||json.getStr("createdBy")==null)
                throw new IllegalArgumentException("At least 1 basic key of ContentReaction is missing");
            return this.setId(json.getInt("id"))
                    .setCreationTime(json.getStr("createdAt"))
                    .setCreatorId(json.getStr("createdBy"))
                    .setBotCreatorId(json.getStr("createdByBotId"))
                    .setWebhookCreatorId(json.getStr("createdByWebhookId"));
        }else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }

    /**
     * Convert the ContentReaction object to JSON string.
     * @return A JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id",id)
                .set("createdAt",createdAt)
                .set("createdBy",createdBy)
                .set("createdByBotId",createdByBotId)
                .set("createdByWebhookId",createdByWebhookId)
                .toString();
    }
}
