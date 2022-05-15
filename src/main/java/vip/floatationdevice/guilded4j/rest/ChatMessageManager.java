/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.object.ChatMessage;
import vip.floatationdevice.guilded4j.object.Embed;

import java.util.Arrays;

import static vip.floatationdevice.guilded4j.G4JClient.MSG_CHANNEL_URL;

/**
 * Manages the chat messages.
 */
public class ChatMessageManager extends RestManager
{
    public ChatMessageManager(String authToken)
    {
        super(authToken);
    }

    /**
     * Create a channel message.<br>
     * <a href="https://www.guilded.gg/docs/api/chat/ChannelMessageCreate" target=_blank>https://www.guilded.gg/docs/api/chat/ChannelMessageCreate</a>
     * @param channelId The ID of the channel.
     * @param content The message content to create.
     * @param embeds Rich content sections associated with the message (can be null).
     * @param replyMessageIds The ID of the message(s) to reply to (maximum of 5, null if not replying).
     * @param isPrivate If set, this message will only be seen by those mentioned or replied to.
     * @param isSilent If set, this message will not notify any mentioned users or roles.
     * @return The newly created message's ChatMessage object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ChatMessage createChannelMessage(String channelId, String content, Embed[] embeds, String[] replyMessageIds, Boolean isPrivate, Boolean isSilent)
    {
        JSONObject result = new JSONObject(HttpRequest.post(MSG_CHANNEL_URL.replace("{channelId}", channelId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                        .set("content", content)
                        .set("embeds", embeds == null ? null : new JSONArray(Arrays.stream(embeds).map(embed -> new JSONObject(embed.toString())).toArray()))//fuck this shit
                        .set("replyMessageIds", replyMessageIds == null ? null : new JSONArray(replyMessageIds))
                        .set("isPrivate", isPrivate)
                        .set("isSilent", isSilent)
                        .toString()).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ChatMessage.fromString(result.get("message").toString());
    }

    /**
     * Delete a channel message.<br>
     * <a href="https://www.guilded.gg/docs/api/chat/ChannelMessageDelete" target=_blank>https://www.guilded.gg/docs/api/chat/ChannelMessageDelete</a>
     * @param channelId The ID of the channel.
     * @param messageId The ID of the message.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void deleteChannelMessage(String channelId, String messageId)
    {
        String result = HttpRequest.delete(MSG_CHANNEL_URL.replace("{channelId}", channelId) + "/" + messageId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("ChannelMessageDelete returned an unexpected JSON string");
        }
    }

    /**
     * Update a channel message.<br>
     * <a href="https://www.guilded.gg/docs/api/chat/ChannelMessageUpdate" target=_blank>https://www.guilded.gg/docs/api/chat/ChannelMessageUpdate</a>
     * @param channelId The ID of the channel.
     * @param messageId The ID of the message.
     * @param content The message content to update.
     * @param embeds The message embeds to update.
     * @return the updated message's ChatMessage object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ChatMessage updateChannelMessage(String channelId, String messageId, String content, Embed[] embeds)
    {
        JSONObject result = new JSONObject(HttpRequest.put(MSG_CHANNEL_URL.replace("{channelId}", channelId) + "/" + messageId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                        .set("content", content)
                        .set("embeds", embeds == null ? null : new JSONArray(Arrays.stream(embeds).map(embed -> new JSONObject(embed.toString())).toArray()))
                        .toString()
                ).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ChatMessage.fromString(result.get("message").toString());
    }

    /**
     * Get details for a specific chat message from a chat channel.<br>
     * <a href="https://www.guilded.gg/docs/api/chat/ChannelMessageRead" target=_blank>https://www.guilded.gg/docs/api/chat/ChannelMessageRead</a>
     * @param channelId The ID of the channel.
     * @param messageId The ID of the message.
     * @return ChatMessage object of the message with the given UUID.
     */
    public ChatMessage getMessage(String channelId, String messageId)
    {
        JSONObject result = new JSONObject(new JSONObject(
                HttpRequest.get(MSG_CHANNEL_URL.replace("{channelId}", channelId) + "/" + messageId).
                        header("Authorization", "Bearer " + authToken).
                        header("Accept", "application/json").
                        header("Content-type", "application/json").
                        timeout(httpTimeout).execute().body()
        ));
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ChatMessage.fromString(result.get("message").toString());
    }

    /**
     * Get a list of the latest 100 messages from a channel.<br>
     * <a href="https://www.guilded.gg/docs/api/chat/ChannelMessageReadMany" target=_blank>https://www.guilded.gg/docs/api/chat/ChannelMessageReadMany</a>
     * @param channelId The ID of the channel.
     * @return A ChatMessage[] that contains up to 100 ChatMessage objects.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ChatMessage[] getChannelMessages(String channelId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(MSG_CHANNEL_URL.replace("{channelId}", channelId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        JSONArray messagesJson = result.getJSONArray("messages");
        ChatMessage[] messages = new ChatMessage[messagesJson.size()];
        for(int i = 0; i != messagesJson.size(); i++)
            messages[i] = ChatMessage.fromString(messagesJson.get(i).toString());
        return messages;
    }

}
