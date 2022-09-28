/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * Simple message object.<br>
 * <a href="https://www.guilded.gg/docs/api/chat/ChatMessage" target=_blank>https://www.guilded.gg/docs/api/chat/ChatMessage</a>
 * <p>A valid ChatMessage object contains 6 essential keys: id, type, channelId, content, createdAt, createdBy.</p>
 */
public class ChatMessage
{
    private String id, type, serverId, channelId, content, createdAt, createdBy, createdByWebhookId, updatedAt;
    private Boolean isPrivate;
    private String[] replyMessageIds;
    private Embed[] embeds;

    /**
     * Use the given JSON object to generate ChatMessage object.
     * @return ChatMessage object.
     * @throws IllegalArgumentException when the essential fields are not set.
     */
    public static ChatMessage fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getStr("id"),
                json.getStr("type"),
                json.getStr("channelId"),
                json.getStr("content"),
                json.getStr("createdAt"),
                json.getStr("createdBy")
        );
        JSONArray replyMessageIdsArray = json.getJSONArray("replyMessageIds");
        String[] replyMessageIds = replyMessageIdsArray != null ? new String[replyMessageIdsArray.size()] : null;
        if(replyMessageIdsArray != null) for(int i = 0; i < replyMessageIdsArray.size(); i++)
            replyMessageIds[i] = replyMessageIdsArray.get(i).toString();
        JSONArray embedsArray = json.getJSONArray("embeds");
        Embed[] embeds = embedsArray != null ? new Embed[embedsArray.size()] : null;
        if(embedsArray != null) for(int i = 0; i < embedsArray.size(); i++)
            embeds[i] = Embed.fromJSON(embedsArray.getJSONObject(i));
        return new ChatMessage()
                .setId(json.getStr("id"))
                .setType(json.getStr("type"))
                .setServerId(json.getStr("serverId"))
                .setChannelId(json.getStr("channelId"))
                .setContent(json.getStr("content"))
                .setCreationTime(json.getStr("createdAt"))
                .setCreatorId(json.getStr("createdBy"))
                .setWebhookCreatorId(json.getStr("createdByWebhookId"))
                .setUpdateTime(json.getStr("updatedAt"))
                .setPrivateReply(json.getBool("isPrivate"))
                .setReplyMessageIds(replyMessageIds)
                .setEmbeds(embeds);
    }

    /**
     * Get the message's UUID.
     * @return A UUID string that contains the UUID of the message.
     */
    public String getId(){return id;}

    public ChatMessage setId(String id)
    {
        this.id = id;
        return this;
    }

    /**
     * Get the message's type.
     * @return "{@code system}" if the message is created by system(e.g. the "Channel created" message), else return "{@code default}".
     */
    public String getType(){return type;}

    public ChatMessage setType(String type)
    {
        this.type = type;
        return this;
    }

    /**
     * Get the ID of the server to which the message belongs.
     * @return The ID of the server.
     */
    public String getServerId(){return serverId;}

    public ChatMessage setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    /**
     * Get the UUID of the channel to which the message belongs.
     * @return A string that contains the channel's UUID of the message.
     */
    public String getChannelId(){return channelId;}

    public ChatMessage setChannelId(String channelId)
    {
        this.channelId = channelId;
        return this;
    }

    /**
     * Get the content of the message.
     * @return A string contains the content of the message.
     */
    public String getContent(){return content;}

    public ChatMessage setContent(String content)
    {
        this.content = content;
        return this;
    }

    /**
     * Get the ISO 8601 timestamp string that the message was created at.
     * @return A string that contains a time formatted like "2021-08-06T14:30:13.614Z" (UTC+-0)
     */
    public String getCreationTime(){return createdAt;}

    public ChatMessage setCreationTime(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Get the ID of the message's creator (not UUID).
     * @return An 8-char-long string looks like "Ann6LewA", "8414gw5d" or some other familiar things. If the creator isn't a user, it will always be "Ann6LewA"
     */
    public String getCreatorId(){return createdBy;}

    public ChatMessage setCreatorId(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * Get the UUID of the webhook who created the message.
     * @return A UUID string of the webhook who created the message. If the creator isn't webhook, return {@code null}.
     */
    public String getWebhookCreatorId(){return createdByWebhookId;}

    public ChatMessage setWebhookCreatorId(String createdByWebhookId)
    {
        this.createdByWebhookId = createdByWebhookId;
        return this;
    }

    /**
     * Get the last update time of the message.
     * @return A string looks like the return value of getCreationTime(). If the message has never been changed, return {@code null}.
     */
    public String getUpdateTime(){return updatedAt;}

    public ChatMessage setUpdateTime(String updatedAt)
    {
        this.updatedAt = updatedAt;
        return this;
    }

    /**
     * Get a list of UUIDs of the messages that this message replies to.
     * @return An array of UUID Strings, if the message isn't a reply, return {@code null}.
     */
    public String[] getReplyMessageIds(){return replyMessageIds;}

    public ChatMessage setReplyMessageIds(String[] replyMessageIds)
    {
        this.replyMessageIds = replyMessageIds;
        return this;
    }

    /**
     * Get the embeds of the message.
     * @return An array of {@link Embed} objects.
     */
    public Embed[] getEmbeds(){return embeds;}

    public ChatMessage setEmbeds(Embed[] embeds)
    {
        this.embeds = embeds;
        return this;
    }

    /**
     * Check if the message is a private reply.
     * @return {@code true} if the message is a private reply, {@code false} if not,
     * {@code null} if the message is not replying to any messages.
     */
    public Boolean isPrivate()
    {
        return replyMessageIds != null ?
                isPrivate != null
                        ? isPrivate
                        : false
                : null;
    }

    /**
     * Check if the message is a system message.
     * @return {@code true} if the message is created by system, {@code false} if not.
     */
    public Boolean isSystemMessage(){return type.equals("system");}

    /**
     * Check if the message is created by a webhook.
     * @return {@code true} if the message is created by webhook, {@code false} if not.
     */
    public Boolean isWebhookMessage(){return createdByWebhookId != null;}

    public ChatMessage setPrivateReply(Boolean isPrivate)
    {
        this.isPrivate = isPrivate;
        return this;
    }

    /**
     * Convert the ChatMessage object to JSON string.
     * @return A JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("type", type)
                .set("serverId", serverId)
                .set("channelId", channelId)
                .set("content", content)
                .set("replyMessageIds", replyMessageIds == null ? null : new JSONArray(replyMessageIds))
                .set("isPrivate", isPrivate)
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("createdByWebhookId", createdByWebhookId)
                .set("updatedAt", updatedAt)
                .set("embeds", embeds == null ? null : new JSONArray(embeds))
                .toString();
    }
}
