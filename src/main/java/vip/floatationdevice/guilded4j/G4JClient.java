/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.enums.ServerChannelType;
import vip.floatationdevice.guilded4j.enums.SocialMedia;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.object.*;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Guilded4J's main component, can send HTTP requests to the API, receive WebSocket events and post them as {@link vip.floatationdevice.guilded4j.event.GuildedEvent}s.
 * @see G4JWebSocketClient
 * @see vip.floatationdevice.guilded4j.event.GuildedEvent
 */
public class G4JClient
{
    public static final String
            CHANNELS_URL = "https://www.guilded.gg/api/v1/channels",
            MSG_CHANNEL_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/messages",
            NICKNAME_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members/{userId}/nickname",
            MEMBERS_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members",
            BANS_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/bans",
            FORUM_CHANNEL_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/forum",
            LIST_CHANNEL_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/items",
            DOC_CHANNEL_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/docs",
            USER_XP_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members/{userId}/xp",
            ROLE_XP_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/roles/{roleId}/xp",
            SOCIAL_LINK_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members/{userId}/social-links/{type}",
            GROUP_URL = "https://www.guilded.gg/api/v1/groups/{groupId}/members/{userId}",
            ROLES_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members/{userId}/roles",
            REACTION_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/content/{contentId}/emotes/{emoteId}",
            WEBHOOKS_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/webhooks";

    String authToken;
    int httpTimeout = 20000;

    /**
     * Built-in WebSocket event manager ({@link G4JWebSocketClient}).
     */
    public G4JWebSocketClient ws;

    public G4JClient(String authToken)
    {
        this.authToken = authToken;
        ws = new G4JWebSocketClient(authToken);
    }

    public G4JClient(String authToken, String lastMessageId)
    {
        this.authToken = authToken;
        ws = new G4JWebSocketClient(authToken, lastMessageId);
    }
//============================== API FUNCTIONS START ==============================
////////////////////////////// Channels //////////////////////////////

    /**
     * Create a channel in the specified server.<br>
     * <a href="https://www.guilded.gg/docs/api/channels/ChannelCreate" target=_blank>https://www.guilded.gg/docs/api/channels/ChannelCreate</a>
     * @param name The name of the channel (min length 1; max length 100).
     * @param topic The topic of the channel (max length 512).
     * @param isPublic Whether the channel can be accessed from users who are not member of the server (default false).
     * @param type The type of channel to create.
     * @param serverId The server that the channel should be created in.
     * @param groupId The group that the channel should be created in (optional). If not provided, channel will be created in the "Server home" group.
     * @param categoryId The category the channel should go in (optional). If not provided, channel will be a top-level channel.
     * @return The created channel's ServerChannel object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ServerChannel createServerChannel(String name, String topic, Boolean isPublic, ServerChannelType type, String serverId, String groupId, Integer categoryId)
    {
        JSONObject result = new JSONObject(HttpRequest.post(CHANNELS_URL).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                        .set("name", name)
                        .set("topic", topic)
                        .set("isPublic", isPublic)
                        .set("type", type.toString().toLowerCase())
                        .set("serverId", serverId)
                        .set("groupId", groupId)
                        .set("categoryId", categoryId)
                        .toString()).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ServerChannel.fromString(result.get("channel").toString());

    }

    /**
     * Get a channel by its UUID.<br>
     * <a href="https://www.guilded.gg/docs/api/channels/ChannelRead" target=_blank>https://www.guilded.gg/docs/api/channels/ChannelRead</a>
     * @param channelId The ID of the channel.
     * @return The channel's ServerChannel object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ServerChannel getServerChannel(String channelId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(CHANNELS_URL + "/" + channelId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ServerChannel.fromString(result.get("channel").toString());
    }

    /**
     * Delete a channel by its UUID.
     * <a href="https://www.guilded.gg/docs/api/channels/ChannelDelete" target=_blank>https://www.guilded.gg/docs/api/channels/ChannelDelete</a>
     * @param channelId The ID of the channel.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void deleteServerChannel(String channelId)
    {
        String result = HttpRequest.delete(CHANNELS_URL + "/" + channelId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("ChannelDelete returned an unexpected JSON string");
        }
    }

    public ServerChannel updateServerChannel()
    {
        return null; //TODO: wait
    }

    public ServerChannel[] getServerChannels()
    {
        return null; //TODO: wait
    }

////////////////////////////// Chat & messaging //////////////////////////////

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

////////////////////////////// Members //////////////////////////////

    /**
     * Update/delete a member's nickname.<br>
     * <a href="https://www.guilded.gg/docs/api/members/MemberNicknameUpdate" target=_blank>https://www.guilded.gg/docs/api/members/MemberNicknameUpdate</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the member.
     * @param nickname The nickname to assign to the member (use {@code null} to delete nickname).
     * @return The nickname to be set when setting nickname, {@code null} when deleting nickname.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public String setMemberNickname(String serverId, String userId, String nickname)
    {
        JSONObject result;
        if(nickname == null) // delete nickname
        {
            String rawString = HttpRequest.delete(NICKNAME_URL.replace("{serverId}", serverId).replace("{userId}", userId)).
                    header("Authorization", "Bearer " + authToken).
                    header("Accept", "application/json").
                    header("Content-type", "application/json").
                    timeout(httpTimeout).execute().body();
            if(!JSONUtil.isTypeJSON(rawString)) return null;
            else
            {
                result = new JSONObject(rawString);
                throw new GuildedException(result.getStr("code"), result.getStr("message"));
            }
        }
        else // update nickname
        {
            result = new JSONObject(HttpRequest.put(NICKNAME_URL.replace("{serverId}", serverId).replace("{userId}", userId)).
                    header("Authorization", "Bearer " + authToken).
                    header("Accept", "application/json").
                    header("Content-type", "application/json").
                    body(new JSONObject().set("nickname", nickname).toString()).
                    timeout(httpTimeout).execute().body());
            if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
            return result.get("nickname").toString();
        }
    }

    /**
     * Get a server member by ID.<br>
     * <a href="https://www.guilded.gg/docs/api/members/TeamMemberRead" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberRead/a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the member.
     * @return The member's ServerMember object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ServerMember getServerMember(String serverId, String userId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(MEMBERS_URL.replace("{serverId}", serverId) + "/" + userId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ServerMember.fromString(result.get("member").toString());
    }

    /**
     * Kick a member from the server.<br>
     * <a href="https://www.guilded.gg/docs/api/members/TeamMemberDelete" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberDelete</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the member.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void kickServerMember(String serverId, String userId)
    {
        String result = HttpRequest.delete(MEMBERS_URL.replace("{serverId}", serverId) + "/" + userId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("TeamMemberDelete returned an unexpected JSON string");
        }
    }

    /**
     * Get a list of all members in the server.<br>
     * <a href="https://www.guilded.gg/docs/api/members/TeamMemberReadMany" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberReadMany</a>
     * @param serverId The ID of the server where the members are.
     * @return A list of ServerMemberSummary objects for each member in the server.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ServerMemberSummary[] getServerMembers(String serverId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(MEMBERS_URL.replace("{serverId}", serverId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        JSONArray membersJson = result.getJSONArray("members");
        ServerMemberSummary[] members = new ServerMemberSummary[membersJson.size()];
        for(int i = 0; i < membersJson.size(); i++)
            members[i] = ServerMemberSummary.fromString(membersJson.get(i).toString());
        return members;
    }

    /**
     * Get a ban information of the member.<br>
     * <a href="https://www.guilded.gg/docs/api/members/TeamMemberBanRead" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberBanRead</a>
     * NOTE: If the member is not banned, a GuildedException will be thrown.
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the member.
     * @return A ServerMemberBan object of the banned member.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ServerMemberBan getServerMemberBan(String serverId, String userId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(BANS_URL.replace("{serverId}", serverId) + "/" + userId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ServerMemberBan.fromString(result.get("serverMemberBan").toString());
    }

    /**
     * Ban a server member.<br>
     * <a href="https://www.guilded.gg/docs/api/members/TeamMemberBanCreate" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberBanCreate</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the user to ban from this server.
     * @param reason The reason for the ban.
     * @return A ServerMemberBan object of the banned member.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ServerMemberBan banServerMember(String serverId, String userId, String reason)
    {
        JSONObject result = new JSONObject(HttpRequest.post(BANS_URL.replace("{serverId}", serverId) + "/" + userId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(reason == null ? "{}" : new JSONObject().set("reason", reason).toString()).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ServerMemberBan.fromString(result.get("serverMemberBan").toString());
    }

    /**
     * Unban a server member.<br>
     * <a href="https://www.guilded.gg/docs/api/members/TeamMemberBanDelete" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberBanDelete</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the user to unban from this server.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void unbanServerMember(String serverId, String userId)
    {
        String result = HttpRequest.delete(BANS_URL.replace("{serverId}", serverId) + "/" + userId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("TeamMemberDelete returned an unexpected JSON string");
        }
    }

    /**
     * Get all ban information of a server.<br>
     * <a href="https://www.guilded.gg/docs/api/members/TeamMemberBanReadMany" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberBanReadMany</a>
     * @param serverId The ID of the server to get ban information of.
     * @return A list of ServerMemberBan objects of the banned members.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ServerMemberBan[] getServerMemberBans(String serverId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(BANS_URL.replace("{serverId}", serverId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        JSONArray bansJson = result.getJSONArray("serverMemberBans");
        ServerMemberBan[] bans = new ServerMemberBan[bansJson.size()];
        for(int i = 0; i < bansJson.size(); i++)
            bans[i] = ServerMemberBan.fromString(bansJson.get(i).toString());
        return bans;
    }

////////////////////////////// Forums //////////////////////////////

    /**
     * Create a thread in a forum.<br>
     * <a href="https://www.guilded.gg/docs/api/forums/ForumThreadCreate" target=_blank>https://www.guilded.gg/docs/api/forums/ForumThreadCreate</a>
     * @param title The title of the thread.
     * @param content The thread's content.
     * @return The newly created thread's ForumThread object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ForumThread createForumThread(String channelId, String title, String content)
    {
        JSONObject result = new JSONObject(HttpRequest.post(FORUM_CHANNEL_URL.replace("{channelId}", channelId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject().set("title", title).set("content", content).toString()).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ForumThread.fromString(result.get("forumThread").toString());
    }

////////////////////////////// List items //////////////////////////////

    /**
     * Create a list item.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemCreate" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemCreate</a>
     * @param message The item's name.
     * @param note The item's note (can be null).
     * @return The newly created item's ListItem object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ListItem createListItem(String channelId, String message, String note)
    {
        JSONObject result = new JSONObject(HttpRequest.post(LIST_CHANNEL_URL.replace("{channelId}", channelId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject(new JSONConfig().setIgnoreNullValue(true)).set("message", message).set("note", note).toString()).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ListItem.fromString(result.get("listItem").toString());
    }

    /**
     * Get list items within a channel.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemReadMany" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemReadMany</a>
     * @param channelId The UUID of the channel.
     * @return A list of ListItemSummary objects. (the maximum number of items returned is not mentioned in the API documentation for now)
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ListItemSummary[] getListItems(String channelId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(LIST_CHANNEL_URL.replace("{channelId}", channelId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        JSONArray itemsJson = result.getJSONArray("listItems");
        ListItemSummary[] items = new ListItemSummary[itemsJson.size()];
        for(int i = 0; i < itemsJson.size(); i++)
            items[i] = ListItemSummary.fromString(itemsJson.get(i).toString());
        return items;
    }

    /**
     * Get a list item.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemRead" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemRead</a>
     * @param channelId The UUID of the channel.
     * @param listItemId The UUID of the list item.
     * @return The item's ListItem object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ListItem getListItem(String channelId, String listItemId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(LIST_CHANNEL_URL.replace("{channelId}", channelId) + "/" + listItemId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ListItem.fromString(result.get("listItem").toString());
    }

    /**
     * Update a list item.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemUpdate" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemUpdate</a>
     * @param channelId The UUID of the channel.
     * @param listItemId The UUID of the list item.
     * @param message The item's new name.
     * @param note The item's new note text (can be null).
     * @return The updated item's ListItem object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ListItem updateListItem(String channelId, String listItemId, String message, String note)
    {
        JSONObject result = new JSONObject(HttpRequest.put(LIST_CHANNEL_URL.replace("{channelId}", channelId) + "/" + listItemId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                        .set("message", message)
                        .set("note", new ListItemNote(null, null, note))
                        .toString()
                ).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ListItem.fromString(result.get("listItem").toString());
    }

    /**
     * Delete a list item.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemDelete" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemDelete</a>
     * @param channelId The UUID of the channel.
     * @param listItemId The UUID of the list item.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void deleteListItem(String channelId, String listItemId)
    {
        String result = HttpRequest.delete(LIST_CHANNEL_URL.replace("{channelId}", channelId) + "/" + listItemId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("ListItemDelete returned an unexpected JSON string");
        }
    }

////////////////////////////// Docs //////////////////////////////

    /**
     * Create a new document.<br>
     * <a href="https://www.guilded.gg/docs/api/docs/DocCreate" target=_blank>https://www.guilded.gg/docs/api/docs/DocCreate</a>
     * @param title The title of the document.
     * @param content The content of the document.
     * @return The newly created doc's Doc object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public Doc createDoc(String channelId, String title, String content)
    {
        JSONObject result = new JSONObject(HttpRequest.post(DOC_CHANNEL_URL.replace("{channelId}", channelId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject().set("title", title).set("content", content).toString()).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return Doc.fromString(result.get("doc").toString());
    }

    /**
     * Update a document.<br>
     * <a href="https://www.guilded.gg/docs/api/docs/DocUpdate" target=_blank>https://www.guilded.gg/docs/api/docs/DocUpdate</a>
     * @param docId The id of the document to update.
     * @param title The new title of the document.
     * @param content The new content of the document.
     * @return The updated doc's Doc object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public Doc updateDoc(String channelId, int docId, String title, String content)
    {
        JSONObject result = new JSONObject(HttpRequest.put(DOC_CHANNEL_URL.replace("{channelId}", channelId) + "/" + docId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject().set("title", title).set("content", content).toString()).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return Doc.fromString(result.get("doc").toString());
    }

    /**
     * Delete a document.<br>
     * <a href="https://www.guilded.gg/docs/api/docs/DocDelete" target=_blank>https://www.guilded.gg/docs/api/docs/DocDelete</a>
     * @param channelId The id of the channel the document is in.
     * @param docId The id of the document to delete.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void deleteDoc(String channelId, int docId)
    {
        String result = HttpRequest.delete(DOC_CHANNEL_URL.replace("{channelId}", channelId) + "/" + docId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("DocDelete returned an unexpected JSON string");
        }
    }

    /**
     * Get a document.<br>
     * <a href="https://www.guilded.gg/docs/api/docs/DocRead" target=_blank>https://www.guilded.gg/docs/api/docs/DocRead</a>
     * @param docId The id of the document.
     * @return The doc's Doc object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public Doc getDoc(String channelId, int docId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(DOC_CHANNEL_URL.replace("{channelId}", channelId) + "/" + docId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return Doc.fromString(result.get("doc").toString());
    }

    /**
     * Get a list of the latest 50 docs from a channel.<br>
     * <a href="https://www.guilded.gg/docs/api/docs/DocReadMany" target=_blank>https://www.guilded.gg/docs/api/docs/DocReadMany</a>
     * @param channelId The id of the channel.
     * @return A list of the latest 50 docs from the channel.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public Doc[] getChannelDocs(String channelId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(DOC_CHANNEL_URL.replace("{channelId}", channelId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        System.out.println(result);
        JSONArray docsJson = result.getJSONArray("docs");
        Doc[] docs = new Doc[docsJson.size()];
        for(int i = 0; i != docsJson.size(); i++)
            docs[i] = Doc.fromString(docsJson.get(i).toString());
        return docs;
    }

////////////////////////////// Reactions //////////////////////////////

    /**
     * Add a reaction emote.<br>
     * <a href="https://www.guilded.gg/docs/api/reactions/ContentReactionCreate" target=_blank>https://www.guilded.gg/docs/api/reactions/ContentReactionCreate</a>
     * @param channelId The ID of the channel.
     * @param contentId Content ID of the content.
     * @param emoteId Emote ID to apply.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void createContentReaction(String channelId, String contentId, int emoteId)
    {
        String result = HttpRequest.put(REACTION_URL.replace("{channelId}", channelId).replace("{contentId}", contentId).replace("{emoteId}", Integer.toString(emoteId))).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("ContentReactionCreate returned an unexpected JSON string");
        }
    }

////////////////////////////// Team XP //////////////////////////////

    /**
     * Award XP to a member.<br>
     * <a href="https://www.guilded.gg/docs/api/teamXP/TeamXpForUserCreate" target=_blank>https://www.guilded.gg/docs/api/teamXP/TeamXpForUserCreate</a>
     * @param serverId The ID of the server where the member is.
     * @param userId Member ID to award XP to.
     * @param amount The amount of XP to award.
     * @return The total XP after this operation.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public int awardUserXp(String serverId, String userId, int amount)
    {
        JSONObject result = new JSONObject(HttpRequest.post(USER_XP_URL.replace("{serverId}", serverId).replace("{userId}", userId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body("{\"amount\":" + amount + "}").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return result.getInt("total");
    }

    /**
     * Award XP to all members with a particular role.<br>
     * <a href="https://www.guilded.gg/docs/api/teamXP/TeamXpForRoleCreate" target=_blank>https://www.guilded.gg/docs/api/teamXP/TeamXpForRoleCreate</a>
     * @param serverId The ID of the server where the member is.
     * @param roleId Role ID to award XP to.
     * @param amount The amount of XP to award.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void awardRoleXp(String serverId, int roleId, int amount)
    {
        String result = HttpRequest.post(ROLE_XP_URL.replace("{serverId}", serverId).replace("{roleId}", String.valueOf(roleId))).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body("{\"amount\":" + amount + "}").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("TeamXpForRoleCreate returned an unexpected JSON string");
        }
    }

////////////////////////////// Social links //////////////////////////////

    /**
     * Retrieves a member's public social links.<br>
     * <a href="https://www.guilded.gg/docs/api/socialLinks/MemberSocialLinkRead" target=_blank>https://www.guilded.gg/docs/api/socialLinks/MemberSocialLinkRead</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The target user's ID.
     * @param type The type of social link to retrieve (see {@link SocialMedia} for available types).
     * @return A HashMap with "type", "handle", "serviceId(nullable)" keys.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public HashMap<String, String> getSocialLink(String serverId, String userId, SocialMedia type)
    {
        JSONObject result = new JSONObject(HttpRequest.get(SOCIAL_LINK_URL.replace("{serverId}", serverId).replace("{userId}", userId).replace("{type}", type.toString().toLowerCase())).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", (String) result.getByPath("socialLink.type"));
        map.put("handle", (String) result.getByPath("socialLink.handle"));
        map.put("serviceId", (String) result.getByPath("socialLink.serviceId"));
        return map;
    }

////////////////////////////// Group membership //////////////////////////////

    /**
     * Add member to group.<br>
     * <a href="https://www.guilded.gg/docs/api/groupMembership/GroupMembershipCreate" target=_blank>https://www.guilded.gg/docs/api/groupMembership/GroupMembershipCreate</a>
     * @param groupId Group ID to add the member to.
     * @param userId Member ID to add to the group.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void addGroupMember(String groupId, String userId)
    {
        String result = HttpRequest.put(GROUP_URL.replace("{groupId}", groupId).replace("{userId}", userId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("GroupMembershipCreate returned an unexpected JSON string");
        }
    }

    /**
     * Remove member from group.<br>
     * <a href="https://www.guilded.gg/docs/api/groupMembership/GroupMembershipDelete" target=_blank>https://www.guilded.gg/docs/api/groupMembership/GroupMembershipDelete</a>
     * @param groupId Group ID to remove the member from.
     * @param userId Member ID to remove from the group.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void removeGroupMember(String groupId, String userId)
    {
        String result = HttpRequest.delete(GROUP_URL.replace("{groupId}", groupId).replace("{userId}", userId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("GroupMembershipDelete returned an unexpected JSON string");
        }
    }

////////////////////////////// Role membership //////////////////////////////

    /**
     * Assign role to member.<br>
     * <a href="https://www.guilded.gg/docs/api/roleMembership/RoleMembershipCreate" target=_blank>https://www.guilded.gg/docs/api/roleMembership/RoleMembershipCreate</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the member that the role should be assigned to.
     * @param roleId The role ID to apply to the user.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void addRoleMember(String serverId, int roleId, String userId)
    {
        String result = HttpRequest.put(ROLES_URL.replace("{serverId}", serverId).replace("{userId}", userId) + "/" + roleId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("RoleMembershipCreate returned an unexpected JSON string");
        }
    }

    /**
     * Remove role from member.<br>
     * <a href="https://www.guilded.gg/docs/api/roleMembership/RoleMembershipDelete" target=_blank>https://www.guilded.gg/docs/api/roleMembership/RoleMembershipDelete</a>
     * @param userId The ID of the member that the role should be removed from.
     * @param roleId The role ID to remove from the user.
     */
    public void removeRoleMember(String serverId, int roleId, String userId)
    {
        String result = HttpRequest.delete(ROLES_URL.replace("{serverId}", serverId).replace("{userId}", userId) + "/" + roleId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("RoleMembershipDelete returned an unexpected JSON string");
        }
    }

    /**
     * Get a list of the roles assigned to a member.<br>
     * <a href="https://www.guilded.gg/docs/api/members/RoleMembershipReadMany" target=_blank>https://www.guilded.gg/docs/api/members/RoleMembershipReadMany</a>
     * @param userId The ID of the member to obtain roles from.
     * @param serverId The ID of the server where the member is.
     * @return An int[] contains the IDs of the roles that the member currently has.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public int[] getMemberRoles(String serverId, String userId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(ROLES_URL.replace("{serverId}", serverId).replace("{userId}", userId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        if(!result.containsKey("roleIds")) return new int[0];
        JSONArray rolesJson = result.getJSONArray("roleIds");
        int[] roles = new int[rolesJson.size()];
        for(int i = 0; i != rolesJson.size(); i++) roles[i] = ((int) rolesJson.get(i));
        return roles;
    }

////////////////////////////// Webhooks //////////////////////////////

    /**
     * Create a webhook.<br>
     * <a href="https://www.guilded.gg/docs/api/webhook/WebhookCreate" target=_blank>https://www.guilded.gg/docs/api/webhook/WebhookCreate</a>
     * @param serverId The ID of the server where the webhook should be created.
     * @param name The name of the webhook (min length 1).
     * @param channelId Channel ID to create the webhook in.
     * @return The newly created webhook's Webhook object.
     */
    public Webhook createWebhook(String serverId, String name, String channelId)
    {
        JSONObject result = new JSONObject(HttpRequest.post(WEBHOOKS_URL.replace("{serverId}", serverId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject()
                        .set("name", name)
                        .set("channelId", channelId).toString()
                ).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return Webhook.fromString(result.get("webhook").toString());
    }

    /**
     * Get a list of webhooks from a server.<br>
     * <a href="https://www.guilded.gg/docs/api/webhook/WebhookReadMany" target=_blank>https://www.guilded.gg/docs/api/webhook/WebhookReadMany</a>
     * @param serverId The ID of the server to get webhooks from.
     * @param channelId The ID of the channel to filter webhooks by.
     * @return A list of Webhook objects.
     */
    public Webhook[] getWebhooks(String serverId, String channelId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(WEBHOOKS_URL.replace("{serverId}", serverId) + "?channelId=" + channelId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        JSONArray webhooksJson = result.getJSONArray("webhooks");
        Webhook[] webhooks = new Webhook[webhooksJson.size()];
        for(int i = 0; i != webhooksJson.size(); i++) webhooks[i] = Webhook.fromString(webhooksJson.get(i).toString());
        return webhooks;
    }

    /**
     * Update a webhook.<br>
     * <a href="https://www.guilded.gg/docs/api/webhook/WebhookUpdate" target=_blank>https://www.guilded.gg/docs/api/webhook/WebhookUpdate</a>
     * @param serverId The ID of the server the webhook is in.
     * @param webhookId The ID of the webhook to update.
     * @param name The new name of the webhook.
     * @param channelId The new channel ID of the webhook (null to keep the same channel).
     * @return The updated Webhook object.
     */
    public Webhook updateWebhook(String serverId, String webhookId, String name, String channelId)
    {
        JSONObject result = new JSONObject(HttpRequest.put(WEBHOOKS_URL.replace("{serverId}", serverId) + "/" + webhookId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                        .set("name", name)
                        .set("channelId", channelId)
                        .toString()
                ).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return Webhook.fromString(result.get("webhook").toString());
    }

    /**
     * Delete a webhook.<br>
     * <a href="https://www.guilded.gg/docs/api/webhook/WebhookDelete" target=_blank>https://www.guilded.gg/docs/api/webhook/WebhookDelete</a>
     * @param serverId The ID of the server the webhook is in.
     * @param webhookId The ID of the webhook to delete.
     */
    public void deleteWebhook(String serverId, String webhookId)
    {
        String result = HttpRequest.delete(WEBHOOKS_URL.replace("{serverId}", serverId) + "/" + webhookId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("WebhookDelete returned an unexpected JSON string");
        }
    }

    /**
     * Get a server's webhook.<br>
     * <a href="https://www.guilded.gg/docs/api/webhook/WebhookRead" target=_blank>https://www.guilded.gg/docs/api/webhook/WebhookRead</a>
     * @param serverId The ID of the server the webhook is in.
     * @param webhookId The ID of the webhook to get.
     * @return The Webhook object.
     */
    public Webhook getWebhook(String serverId, String webhookId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(WEBHOOKS_URL.replace("{serverId}", serverId) + "/" + webhookId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return Webhook.fromString(result.get("webhook").toString());
    }

//============================== API FUNCTIONS END ==============================

    /**
     * Initialize or reset Guilded bot access token.
     * @param token The bot API access token (without "Bearer" prefix).
     */
    public void setAuthToken(String token)
    {
        authToken = token;
        ws.setAuthToken(authToken);
    }

    /**
     * Set the timeout of the HTTP request.
     * @param timeoutMs The timeout in milliseconds.
     */
    public void setHttpTimeout(int timeoutMs){this.httpTimeout = timeoutMs;}
}
