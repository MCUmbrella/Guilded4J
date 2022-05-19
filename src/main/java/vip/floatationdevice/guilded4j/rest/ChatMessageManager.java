/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.object.ChatMessage;
import vip.floatationdevice.guilded4j.object.Embed;

import java.util.Arrays;
import java.util.function.Function;

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
        return ChatMessage.fromJSON(
                execute(Method.POST,
                        MSG_CHANNEL_URL.replace("{channelId}", channelId),
                        new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                                .set("content", content)
                                .set("embeds", embeds == null ? null : new JSONArray(Arrays.stream(embeds).map(new Function<Embed, JSONObject>()
                                {
                                    @Override
                                    public JSONObject apply(Embed embed){return new JSONObject(embed.toString());}
                                }).toArray()))
                                .set("replyMessageIds", replyMessageIds == null ? null : new JSONArray(replyMessageIds))
                                .set("isPrivate", isPrivate)
                                .set("isSilent", isSilent)
                ).getJSONObject("message")
        );
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
        execute(Method.DELETE, MSG_CHANNEL_URL.replace("{channelId}", channelId) + "/" + messageId, null);
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
        return ChatMessage.fromJSON(
                execute(Method.PUT,
                        MSG_CHANNEL_URL.replace("{channelId}", channelId) + "/" + messageId,
                        new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                                .set("content", content)
                                .set("embeds", embeds == null ? null : new JSONArray(Arrays.stream(embeds).map(new Function<Embed, JSONObject>()
                                {
                                    @Override
                                    public JSONObject apply(Embed embed){return new JSONObject(embed.toString());}
                                }).toArray()))
                ).getJSONObject("message")
        );
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
        return ChatMessage.fromJSON(
                execute(Method.GET, MSG_CHANNEL_URL.replace("{channelId}", channelId) + "/" + messageId, null)
                        .getJSONObject("message")
        );
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
        JSONArray messagesJson = execute(Method.GET, MSG_CHANNEL_URL.replace("{channelId}", channelId), null).getJSONArray("messages");
        ChatMessage[] messages = new ChatMessage[messagesJson.size()];
        for(int i = 0; i != messagesJson.size(); i++)
            messages[i] = ChatMessage.fromJSON(messagesJson.getJSONObject(i));
        return messages;
    }
}
