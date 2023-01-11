/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.misc.GObjectQuery;
import vip.floatationdevice.guilded4j.object.ChatMessage;
import vip.floatationdevice.guilded4j.object.Embed;

import java.util.Arrays;
import java.util.function.Function;

import static vip.floatationdevice.guilded4j.G4JClient.MSG_CHANNEL_URL;
import static vip.floatationdevice.guilded4j.G4JClient.REACTION_URL;

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
    public ChatMessage getChannelMessage(String channelId, String messageId)
    {
        return ChatMessage.fromJSON(
                execute(Method.GET, MSG_CHANNEL_URL.replace("{channelId}", channelId) + "/" + messageId, null)
                        .getJSONObject("message")
        );
    }

    /**
     * Get a list of the latest 50 messages from a channel.<br>
     * <a href="https://www.guilded.gg/docs/api/chat/ChannelMessageReadMany" target=_blank>https://www.guilded.gg/docs/api/chat/ChannelMessageReadMany</a>
     * @param channelId The ID of the channel.
     * @return A ChatMessage[] that contains up to 100 ChatMessage objects.
     */
    public ChatMessage[] getChannelMessages(String channelId)
    {
        JSONArray messagesJson = execute(Method.GET, MSG_CHANNEL_URL.replace("{channelId}", channelId), null).getJSONArray("messages");
        ChatMessage[] messages = new ChatMessage[messagesJson.size()];
        for(int i = 0; i != messagesJson.size(); i++)
            messages[i] = ChatMessage.fromJSON(messagesJson.getJSONObject(i));
        return messages;
    }

    /**
     * Get a list of the messages using a query.
     * @param channelId ID of the channel that the messages exist in.
     * @param query The query to search for.
     * @return ChatMessage[]
     */
    public ChatMessage[] getChannelMessages(String channelId, GObjectQuery query)
    {
        JSONArray messagesJson = execute(Method.GET, MSG_CHANNEL_URL.replace("{channelId}", channelId) + query, null).getJSONArray("messages");
        ChatMessage[] messages = new ChatMessage[messagesJson.size()];
        for(int i = 0; i != messagesJson.size(); i++)
            messages[i] = ChatMessage.fromJSON(messagesJson.getJSONObject(i));
        return messages;
    }

    /**
     * Add a reaction emote to a chat message.<br>
     * <a href="https://www.guilded.gg/docs/api/reactions/ContentReactionCreate" target=_blank>https://www.guilded.gg/docs/api/reactions/ContentReactionCreate</a>
     * @param channelId Channel UUID where the chat message exists.
     * @param messageId The UUID of the chat message.
     * @param emoteId The ID of the emote to add.
     */
    public void addReaction(String channelId, String messageId, int emoteId)
    {
        execute(Method.PUT,
                REACTION_URL
                        .replace("{channelId}", channelId)
                        .replace("{contentId}", messageId)
                        .replace("{emoteId}", Integer.toString(emoteId)),
                null
        );
    }

    /**
     * Remove a reaction emote of a chat message.<br>
     * <a href="https://www.guilded.gg/docs/api/reactions/ContentReactionDelete" target=_blank>https://www.guilded.gg/docs/api/reactions/ContentReactionDelete</a>
     * @param channelId Channel UUID where the chat message exists.
     * @param messageId The UUID of the chat message.
     * @param emoteId The ID of the emote to delete.
     */
    public void removeReaction(String channelId, String messageId, int emoteId)
    {
        execute(Method.DELETE,
                REACTION_URL
                        .replace("{channelId}", channelId)
                        .replace("{contentId}", messageId)
                        .replace("{emoteId}", Integer.toString(emoteId)),
                null
        );
    }
}
