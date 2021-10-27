// Copyright (c) 2021 MCUmbrella & contributors

package vip.floatationdevice.guilded4j;
import java.util.ArrayList;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.exception.*;
import vip.floatationdevice.guilded4j.object.*;

import javax.annotation.Nullable;

/**
 * The Guilded4J client that can send HTTP requests to the API.
 */
public class G4JClient
{
    public static final String MSG_CHANNEL_URL="https://www.guilded.gg/api/v1/channels/{channelId}/messages";
    public static final String ROLES_URL="https://www.guilded.gg/api/v1/members/{userId}/roles";
    public static final String NICKNAME_URL="https://www.guilded.gg/api/v1/members/{userId}/nickname";
    public static final String FORUM_CHANNEL_URL="https://www.guilded.gg/api/v1/channels/{channelId}/forum";
    public static final String LIST_CHANNEL_URL="https://www.guilded.gg/api/v1/channels/{channelId}/list";
    public static final String USER_XP_URL="https://www.guilded.gg/api/v1/members/{userId}/xp";
    public static final String ROLE_XP_URL="https://www.guilded.gg/api/v1/roles/{roleId}/xp";
    public static final String SOCIAL_LINK_URL="https://www.guilded.gg/api/v1/members/{userId}/social-links/{type}";
    public static final String GROUP_URL="https://www.guilded.gg/api/v1/groups/{groupId}/members/{userId}";
    public static final String ROLE_URL="https://www.guilded.gg/api/v1/members/{userId}/roles/{roleId}";
    public static final String REACTION_URL="https://www.guilded.gg/api/v1/channels/{channelId}/content/{contentId}/emotes/{emoteId}";
    public static final String[] socialMedias={"twitch", "bnet", "psn", "xbox", "steam", "origin", "youtube", "twitter", "facebook", "switch", "patreon", "roblox"};
    protected String authToken;

    public G4JClient(String authToken)
    {
        this.authToken=authToken;
    }
//============================== API FUNCTIONS START ============================== //TODO: rewrite exception handling
////////////////////////////// Chat & messaging //////////////////////////////

    /**
     * Create a channel message.<br>
     * <a>https://www.guilded.gg/docs/api/chat/ChannelMessageCreate</a>
     * @param content The content of the message.
     * @return The newly created message's ChatMessage object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    //TODO: createChannelMessage(String channelId, String content, @Nullable String[] replyMessageIds, @Nullable Boolean isPrivate)
    public ChatMessage createChannelMessage(String channelId, String content)
    {
        JSONObject result=new JSONObject(HttpRequest.post(MSG_CHANNEL_URL.replace("{channelId}",channelId)).
                header("Authorization","Bearer "+authToken).
                header("Accept","application/json").
                header("Content-type","application/json").
                body(new JSONObject().set("content",content).toString()).
                timeout(20000).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"),result.getStr("message"));
        return new ChatMessage().fromString(result.get("message").toString());
    }

    /**
     * Delete a channel message.<br>
     * <a>https://www.guilded.gg/docs/api/chat/ChannelMessageDelete</a>
     * @param channelId The ID of the channel.
     * @param messageId The ID of the message.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void deleteChannelMessage(String channelId, String messageId)
    {
        String result=HttpRequest.delete(MSG_CHANNEL_URL.replace("{channelId}",channelId)+"/"+messageId).
                header("Authorization","Bearer "+authToken).
                header("Accept","application/json").
                timeout(20000).execute().body();
        if(JSONUtil.isJson(result))
        {
            JSONObject json=new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"),json.getStr("message"));
        }
    }

    /**
     * Update a channel message.<br>
     * <a>https://www.guilded.gg/docs/api/chat/ChannelMessageUpdate</a>
     * @param channelId The ID of the channel.
     * @param messageId The ID of the message.
     * @param content The message content to update.
     * @return the updated message's ChatMessage object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ChatMessage updateChannelMessage(String channelId, String messageId, String content)
    {
        JSONObject result=new JSONObject(HttpRequest.put(MSG_CHANNEL_URL.replace("{channelId}",channelId)+"/"+messageId).
                header("Authorization","Bearer "+authToken).
                header("Accept","application/json").
                header("Content-type","application/json").
                body(new JSONObject().set("content",content).toString()).
                timeout(20000).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"),result.getStr("message"));
        return new ChatMessage().fromString(result.get("message").toString());
    }

    /**
     * Get details for a specific chat message from a chat channel.<br>
     * <a>https://www.guilded.gg/docs/api/chat/ChannelMessageRead</a>
     * @param channelId The ID of the channel.
     * @param messageId The ID of the message.
     * @return ChatMessage object of the message with the given UUID.
     */
    public ChatMessage getMessage(String channelId, String messageId)
    {
        JSONObject result=new JSONObject(new JSONObject(
                HttpRequest.get(MSG_CHANNEL_URL.replace("{channelId}",channelId)+"/"+messageId).
                        header("Authorization","Bearer "+authToken).
                        header("Accept","application/json").
                        header("Content-type","application/json").
                        timeout(20000).execute().body()
        ));
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"),result.getStr("message"));
        return new ChatMessage().fromString(result.get("message").toString());
    }

    /**
     * Get a list of the latest 100 messages from a channel.<br>
     * <a>https://www.guilded.gg/docs/api/chat/ChannelMessageReadMany</a>
     * @param channelId The ID of the channel.
     * @return A ChatMessage type ArrayList that contains up to 100 ChatMessage objects.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    //TODO: public ArrayList<ChatMessage> getChannelMessages(String channelId, Boolean includePrivate)
    public ArrayList<ChatMessage> getChannelMessages(String channelId)
    {
        JSONObject result=new JSONObject(HttpRequest.get(MSG_CHANNEL_URL.replace("{channelId}",channelId)).
                header("Authorization","Bearer "+authToken).
                header("Accept","application/json").
                header("Content-type","application/json").
                timeout(20000).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"),result.getStr("message"));
        ArrayList<ChatMessage> messages=new ArrayList<ChatMessage>();
        JSONArray array=result.getJSONArray("messages");
        Object[] converted=array.toArray();
        for(int i=0;i!=converted.length;i++) messages.add(new ChatMessage().fromString((new JSONObject(converted[i]).toString())));
        return messages;
    }

////////////////////////////// Members //////////////////////////////

    /**
     * Get a list of the roles assigned to a member.<br>
     * <a>https://www.guilded.gg/docs/api/members/RoleMembershipReadMany</a>
     * @param userId The ID of the member to obtain roles from.
     * @return An Integer type ArrayList contains the IDs of the roles that the member currently has.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ArrayList<Integer> getMemberRoles(String userId)
    {
        ArrayList<Integer> roles=new ArrayList<Integer>();
        JSONObject result=new JSONObject(HttpRequest.get(ROLES_URL.replace("{userId}",userId)).
                header("Authorization","Bearer "+authToken).
                header("Accept","application/json").
                timeout(20000).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"),result.getStr("message"));
        if(!result.containsKey("roleIds")) return roles;
        JSONArray array=result.getJSONArray("roleIds");
        Object[] converted=array.toArray();
        for(int i=0;i!=converted.length;i++) roles.add((int)converted[i]);
        return roles;
    }

    /**
     * Update/delete a member's nickname.<br>
     * <a>https://www.guilded.gg/docs/api/members/MemberNicknameUpdate</a>
     * @param userId The ID of the member.
     * @param nickname The nickname to assign to the member (use {@code null} to delete nickname).
     * @return The nickname to be set when setting nickname, {@code null} when deleting nickname.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public String setMemberNickname(String userId, @Nullable String nickname)
    {
        JSONObject result;
        if(nickname==null)
        {
            String rawString=HttpRequest.delete(NICKNAME_URL.replace("{userId}",userId)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    timeout(20000).execute().body();
            if(!JSONUtil.isJson(rawString)) return null;
            else
            {
                result=new JSONObject(rawString);
                throw new GuildedException(result.getStr("code"),result.getStr("message"));
            }
        }
        else
        {
            result=new JSONObject(HttpRequest.put(NICKNAME_URL.replace("{userId}",userId)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    header("Content-type","application/json").
                    body(new JSONObject().set("nickname",nickname).toString()).
                    timeout(20000).execute().body());
            if(result.containsKey("code")) throw new GuildedException(result.getStr("code"),result.getStr("message"));
            return result.get("nickname").toString();
        }
    }

////////////////////////////// Forums //////////////////////////////

    /**
     * Create a thread in a forum.<br>
     * <a>https://www.guilded.gg/docs/api/forums/ForumThreadCreate</a>
     * @param title The title of the thread.
     * @param content The thread's content.
     * @return The newly created thread's ForumThread object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ForumThread createForumThread(String channelId, String title, String content)
    {
        JSONObject result=new JSONObject(HttpRequest.post(FORUM_CHANNEL_URL.replace("{channelId}",channelId)).
                header("Authorization","Bearer "+authToken).
                header("Accept","application/json").
                header("Content-type","application/json").
                body(new JSONObject().set("title",title).set("content",content).toString()).
                timeout(20000).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"),result.getStr("message"));
        return new ForumThread().fromString(result.get("forumThread").toString());
    }

////////////////////////////// List items //////////////////////////////

    /**
     * Create a list item.<br>
     * <a>https://www.guilded.gg/docs/api/listItems/ListItemCreate</a>
     * @param message The item's name.
     * @param note The item's note (can be null).
     * @return The newly created item's ListItem object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ListItem createListItem(String channelId, String message, @Nullable String note)
    {
        JSONObject result=new JSONObject(HttpRequest.post(LIST_CHANNEL_URL.replace("{channelId}",channelId)).
                header("Authorization","Bearer "+authToken).
                header("Accept","application/json").
                header("Content-type","application/json").
                body(new JSONObject(new JSONConfig().setIgnoreNullValue(true)).set("message",message).set("note",note).toString()).
                timeout(20000).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"),result.getStr("message"));
        return new ListItem().fromString(result.get("listItem").toString());
    }

////////////////////////////// Reactions //////////////////////////////

    /**
     * Add a reaction emote.<br>
     * <a>https://www.guilded.gg/docs/api/reactions/ContentReactionCreate</a>
     * @param emoteId The ID of the emote.
     * @return A JSON string that contains a ContentReaction object called "emote" if succeeded, else return a JSON string with an "Exception" key (Guilded4J's exception), or a "code" key and a "message" key (API's exception).
     */
    public String createContentReaction(String channelId, String contentId, int emoteId)
    {
        try
        {
            return HttpRequest.put(REACTION_URL.replace("{channelId}",channelId).replace("{contentId}",contentId).replace("{emoteId}",Integer.toString(emoteId))).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return new JSONObject().set("Exception",e.toString()).set("ExceptionName",e.getClass().getName()).set("ExceptionMessage",e.getMessage()).toString();
        }
    }

////////////////////////////// Team XP //////////////////////////////

    /**
     * Award XP to a member.<br>
     * <a>https://www.guilded.gg/docs/api/teamXP/TeamXpForUserCreate</a>
     * @param userId The target user's ID.
     * @param amount The amount of xp to add.
     * @return A JSON string contains a "total" key if succeeded, else return a JSON string with an "Exception" key (Guilded4J's exception), or a "code" key and a "message" key (API's exception).
     */
    public String awardUserXp(String userId, int amount)
    {
        try
        {
            return HttpRequest.post(USER_XP_URL.replace("{userId}",userId)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    header("Content-type","application/json").
                    body("{\"amount\":"+amount+"}").
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return new JSONObject().set("Exception",e.toString()).set("ExceptionName",e.getClass().getName()).set("ExceptionMessage",e.getMessage()).toString();
        }
    }

    /**
     * Award XP to all members with a particular role.<br>
     * <a>https://www.guilded.gg/docs/api/teamXP/TeamXpForRoleCreate</a>
     * @param roleId The ID of the role.
     * @param amount The amount of xp to add.
     * @return {@code null} if succeeded, else return a JSON string with an "Exception" key (Guilded4J's exception), or a "code" key and a "message" key (API's exception).
     */
    public String awardRoleXp(int roleId, int amount)
    {
        try
        {
            return HttpRequest.post(ROLE_XP_URL.replace("{roleId}",String.valueOf(roleId))).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    header("Content-type","application/json").
                    body("{\"amount\":"+amount+"}").
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return new JSONObject().set("Exception",e.toString()).set("ExceptionName",e.getClass().getName()).set("ExceptionMessage",e.getMessage()).toString();
        }
    }

////////////////////////////// Social links //////////////////////////////

    /**
     * Retrieves a member's public social links.<br>
     * <a>https://www.guilded.gg/docs/api/socialLinks/MemberSocialLinkRead</a>
     * @param userId The target user's ID.
     * @param type The type of social link to retrieve.<br>- should be "twitch", "bnet", "psn", "xbox", "steam", "origin", "youtube", "twitter", "facebook", "switch", "patreon", or "roblox".
     * @throws IllegalArgumentException If the value of "type" argument is not listed.
     * @return A JSON String with a "type" key and a "handle" key if succeeded, else return a JSON string with an "Exception" key (Guilded4J's exception), or a "code" key and a "message" key (API's exception).
     */
    public String getSocialLink(String userId, String type)
    {
        String result;
        boolean found = false;
        for(String s:socialMedias) if(s.equals(type.toLowerCase())){found=true;break;}
        if(found)
        {
            try
            {
                result=HttpRequest.get(SOCIAL_LINK_URL.replace("{userId}",userId).replace("{type}",type)).
                        header("Authorization","Bearer "+authToken).
                        header("Accept","application/json").
                        header("Content-type","application/json").
                        timeout(20000).execute().body();
                return result;
            }catch (Exception e)
            {
                return new JSONObject().set("Exception",e.toString()).set("ExceptionName",e.getClass().getName()).set("ExceptionMessage",e.getMessage()).toString();
            }
        }
        else throw new IllegalArgumentException("The specified social media '"+type+"' is not available");
    }

////////////////////////////// Group membership //////////////////////////////

    /**
     * Add member to group.<br>
     * <a>https://www.guilded.gg/docs/api/groupMembership/GroupMembershipCreate</a>
     * @param groupId The target group's ID.
     * @param userId The target user's ID.
     * @return {@code null} if succeeded, else return a JSON string with an "Exception" key (Guilded4J's exception), or a "code" key and a "message" key (API's exception).
     */
    public String addGroupMember(String groupId, String userId)
    {
        try
        {
            return HttpRequest.put(GROUP_URL.replace("{groupId}",groupId).replace("{userId}",userId)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return new JSONObject().set("Exception",e.toString()).set("ExceptionName",e.getClass().getName()).set("ExceptionMessage",e.getMessage()).toString();
        }
    }

    /**
     * Remove member from group.<br>
     * <a>https://www.guilded.gg/docs/api/groupMembership/GroupMembershipDelete</a>
     * @param groupId The target group's ID.
     * @param userId The target user's ID.
     * @return {@code null} if succeeded, else return a JSON string with an "Exception" key (Guilded4J's exception), or a "code" key and a "message" key (API's exception).
     */
    public String removeGroupMember(String groupId, String userId)
    {
        try
        {
            return HttpRequest.delete(GROUP_URL.replace("{groupId}",groupId).replace("{userId}",userId)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return new JSONObject().set("Exception",e.toString()).set("ExceptionName",e.getClass().getName()).set("ExceptionMessage",e.getMessage()).toString();
        }
    }

////////////////////////////// Role membership //////////////////////////////

    /**
     * Assign role to member.<br>
     * <a>https://www.guilded.gg/docs/api/roleMembership/RoleMembershipCreate</a>
     * @param userId The target user's ID.
     * @param roleId The target role's ID.
     * @return {@code null} if succeeded, else return a JSON string with an "Exception" key (Guilded4J's exception), or a "code" key and a "message" key (API's exception).
     */
    public String addRoleMember(String userId, String roleId)
    {
        try
        {
            return HttpRequest.put(ROLE_URL.replace("{userId}",userId).replace("{roleId}",roleId)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return new JSONObject().set("Exception",e.toString()).set("ExceptionName",e.getClass().getName()).set("ExceptionMessage",e.getMessage()).toString();
        }
    }

    /**
     * Remove role from member.<br>
     * <a>https://www.guilded.gg/docs/api/roleMembership/RoleMembershipDelete</a>
     * @param userId The target user's ID.
     * @param roleId The target role's ID.
     * @return {@code null} if succeeded, else return a JSON string with an "Exception" key (Guilded4J's exception), or a "code" key and a "message" key (API's exception).
     */
    public String removeRoleMember(String userId, String roleId)
    {
        try
        {
            return HttpRequest.delete(ROLE_URL.replace("{userId}",userId).replace("{roleId}",roleId)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return new JSONObject().set("Exception",e.toString()).set("ExceptionName",e.getClass().getName()).set("ExceptionMessage",e.getMessage()).toString();
        }
    }

//============================== API FUNCTIONS END ==============================

    /**
     * Initialize or reset Guilded bot access token.
     * @param token The bot API access token (without "Bearer" prefix).
     */
    public void setAuthToken(String token)
    {
        authToken=token;
    }
}
