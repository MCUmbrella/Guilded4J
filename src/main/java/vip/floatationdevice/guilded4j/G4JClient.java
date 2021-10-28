// Copyright (c) 2021 MCUmbrella & contributors

package vip.floatationdevice.guilded4j;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            else throw new ClassCastException("ChannelMessageDelete returned an unexpected JSON string");
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
     * @param channelId The ID of the channel.
     * @param contentId Content ID of the content.
     * @param emoteId Emote ID to apply.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void createContentReaction(String channelId, String contentId, int emoteId)
    {
        String result=HttpRequest.put(REACTION_URL.replace("{channelId}",channelId).replace("{contentId}",contentId).replace("{emoteId}",Integer.toString(emoteId))).
                header("Authorization","Bearer "+authToken).
                header("Accept","application/json").
                timeout(20000).execute().body();
        if(JSONUtil.isJson(result))
        {
            JSONObject json=new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"),json.getStr("message"));
            else throw new ClassCastException("ContentReactionCreate returned an unexpected JSON string");
        }
    }

////////////////////////////// Team XP //////////////////////////////

    /**
     * Award XP to a member.<br>
     * <a>https://www.guilded.gg/docs/api/teamXP/TeamXpForUserCreate</a>
     * @param userId Member ID to award XP to.
     * @param amount The amount of XP to award.
     * @return The total XP after this operation.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public int awardUserXp(String userId, int amount)
    {
        JSONObject result=new JSONObject(HttpRequest.post(USER_XP_URL.replace("{userId}",userId)).
                header("Authorization","Bearer "+authToken).
                header("Accept","application/json").
                header("Content-type","application/json").
                body("{\"amount\":"+amount+"}").
                timeout(20000).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"),result.getStr("message"));
        return result.getInt("total");
    }

    /**
     * Award XP to all members with a particular role.<br>
     * <a>https://www.guilded.gg/docs/api/teamXP/TeamXpForRoleCreate</a>
     * @param roleId Role ID to award XP to.
     * @param amount The amount of XP to award.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void awardRoleXp(int roleId, int amount)
    {
        String result=HttpRequest.post(ROLE_XP_URL.replace("{roleId}",String.valueOf(roleId))).
                header("Authorization","Bearer "+authToken).
                header("Accept","application/json").
                header("Content-type","application/json").
                body("{\"amount\":"+amount+"}").
                timeout(20000).execute().body();
        if(JSONUtil.isJson(result))
        {
            JSONObject json=new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"),json.getStr("message"));
            else throw new ClassCastException("TeamXpForRoleCreate returned an unexpected JSON string");
        }
    }

////////////////////////////// Social links //////////////////////////////

    /**
     * Retrieves a member's public social links.<br>
     * <a>https://www.guilded.gg/docs/api/socialLinks/MemberSocialLinkRead</a>
     * @param userId The target user's ID.
     * @param type The type of social link to retrieve.<br>- should be "twitch", "bnet", "psn", "xbox", "steam", "origin", "youtube", "twitter", "facebook", "switch", "patreon", or "roblox".
     * @return A HashMap with "type", "handle", "serviceId(nullable)" keys.
     * @throws IllegalArgumentException If the value of "type" argument is not listed.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public HashMap<String, String> getSocialLink(String userId, String type)
    {
        boolean found = false;
        for(String s:socialMedias) if(s.equals(type.toLowerCase())){found=true;break;}
        if(found)
        {
            JSONObject result=new JSONObject(HttpRequest.get(SOCIAL_LINK_URL.replace("{userId}",userId).replace("{type}",type)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    header("Content-type","application/json").
                    timeout(20000).execute().body());
            if(result.containsKey("code")) throw new GuildedException(result.getStr("code"),result.getStr("message"));
            HashMap<String, String> map=new HashMap<>();
            map.put("type",(String)result.getByPath("socialLink.type"));
            map.put("handle",(String)result.getByPath("socialLink.handle"));
            map.put("serviceId",(String)result.getByPath("socialLink.serviceId"));
            return map;
        }
        else throw new IllegalArgumentException("The specified social media '"+type+"' is not available");
    }

////////////////////////////// Group membership //////////////////////////////

    /**
     * Add member to group.<br>
     * <a>https://www.guilded.gg/docs/api/groupMembership/GroupMembershipCreate</a>
     * @param groupId Group ID to add the member to.
     * @param userId Member ID to add to the group.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void addGroupMember(String groupId, String userId)
    {
        String result=HttpRequest.put(GROUP_URL.replace("{groupId}",groupId).replace("{userId}",userId)).
                header("Authorization","Bearer "+authToken).
                header("Accept","application/json").
                timeout(20000).execute().body();
        if(JSONUtil.isJson(result))
        {
            JSONObject json=new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"),json.getStr("message"));
            else throw new ClassCastException("GroupMembershipCreate returned an unexpected JSON string");
        }
    }

    /**
     * Remove member from group.<br>
     * <a>https://www.guilded.gg/docs/api/groupMembership/GroupMembershipDelete</a>
     * @param groupId Group ID to remove the member from.
     * @param userId Member ID to remove from the group.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void removeGroupMember(String groupId, String userId)
    {
        String result=HttpRequest.delete(GROUP_URL.replace("{groupId}",groupId).replace("{userId}",userId)).
                header("Authorization","Bearer "+authToken).
                header("Accept","application/json").
                timeout(20000).execute().body();
        if(JSONUtil.isJson(result))
        {
            JSONObject json=new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"),json.getStr("message"));
            else throw new ClassCastException("GroupMembershipDelete returned an unexpected JSON string");
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
