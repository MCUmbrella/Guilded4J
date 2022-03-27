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
import vip.floatationdevice.guilded4j.enums.SocialMedia;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.object.*;

import java.util.HashMap;

/**
 * Guilded4J's main component, can send HTTP requests to the API, receive WebSocket events and post them as {@link vip.floatationdevice.guilded4j.event.GuildedEvent}s.
 * @see G4JWebSocketClient
 * @see vip.floatationdevice.guilded4j.event.GuildedEvent
 */
public class G4JClient
{
    public static final String
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
            ROLE_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members/{userId}/roles/{roleId}",
            REACTION_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/content/{contentId}/emotes/{emoteId}";

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
////////////////////////////// Chat & messaging //////////////////////////////

    /**
     * Create a channel message.<br>
     * <a href="https://www.guilded.gg/docs/api/chat/ChannelMessageCreate" target=_blank>https://www.guilded.gg/docs/api/chat/ChannelMessageCreate</a>
     * @param channelId The ID of the channel.
     * @param content The message content to create.
     * @param replyMessageIds The ID of the message(s) to reply to.
     * @param isPrivate If set, this message will only be seen by those mentioned or replied to.
     * @return The newly created message's ChatMessage object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ChatMessage createChannelMessage(String channelId, String content, String[] replyMessageIds, Boolean isPrivate)
    {
        JSONObject result = new JSONObject(HttpRequest.post(MSG_CHANNEL_URL.replace("{channelId}", channelId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                        .set("content", content)
                        .set("replyMessageIds", replyMessageIds == null ? null : new JSONArray(replyMessageIds))
                        .set("isPrivate", isPrivate)
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
     * @return the updated message's ChatMessage object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ChatMessage updateChannelMessage(String channelId, String messageId, String content)
    {
        JSONObject result = new JSONObject(HttpRequest.put(MSG_CHANNEL_URL.replace("{channelId}", channelId) + "/" + messageId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject().set("content", content).toString()).
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
        Object[] converted = result.getJSONArray("messages").toArray();
        ChatMessage[] messages = new ChatMessage[converted.length];
        for(int i = 0; i != converted.length; i++)
            messages[i] = ChatMessage.fromString(converted[i].toString());
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

    public TeamMember getServerMember(String serverId, String userId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(MEMBERS_URL.replace("{serverId}", serverId) + "/" + userId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return TeamMember.fromString(result.get("member").toString());
    }

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

    public TeamMemberSummary[] getServerMembers(String serverId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(MEMBERS_URL.replace("{serverId}", serverId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        Object[] converted = result.getJSONArray("members").toArray();
        TeamMemberSummary[] members = new TeamMemberSummary[converted.length];
        for(int i = 0; i < converted.length; i++) members[i] = TeamMemberSummary.fromString(converted[i].toString());
        return members;
    }

    public TeamMemberBan getServerMemberBan(String serverId, String userId) //TODO: implement
    {
        return null;
    }

    public TeamMemberBan banServerMember(String serverId, String userId, String reason) //TODO: implement
    {
        return null;
    }

    public void unbanServerMember(String serverId, String userId) //TODO: implement
    {
        return;
    }

    public void getServerMemberBans(String serverId) //TODO: implement
    {
        return;
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

    public ListItemSummary[] getListItems(String channelId) //TODO: implement
    {
        return null;
    }

    public ListItem getListItem(String channelId, String listItemId) //TODO: implement
    {
        return null;
    }

    public ListItem updateListItem(String channelId, String listItemId, String message, String note) //TODO: implement
    {
        return null;
    }

    public void deleteListItem(String channelId, String listItemId) //TODO: implement
    {
        return;
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
        Object[] converted = result.getJSONArray("docs").toArray();
        Doc[] docs = new Doc[converted.length];
        for(int i = 0; i != converted.length; i++)
            docs[i] = Doc.fromString(converted[i].toString());
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
        String result = HttpRequest.put(ROLE_URL.replace("{serverId}", serverId).replace("{userId}", userId).replace("{roleId}", String.valueOf(roleId))).
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
        String result = HttpRequest.delete(ROLE_URL.replace("{serverId}", serverId).replace("{userId}", userId).replace("{roleId}", String.valueOf(roleId))).
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
        Object[] converted = result.getJSONArray("roleIds").toArray();
        int[] roles = new int[converted.length];
        for(int i = 0; i != converted.length; i++) roles[i] = ((int) converted[i]);
        return roles;
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
