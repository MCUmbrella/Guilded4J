// Copyright (c) 2021 MCUmbrella & contributors

package vip.floatationdevice.guilded4j;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import com.google.common.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.object.ChatMessage;

import javax.annotation.Nullable;

/**
 * The Guilded4J client that can receive WebSocket events and send HTTP requests to the API.
 * <p>NOTE:</p>
 * <p>- After creating G4JClient object you need to manually call connect() or disconnect() to start/stop receiving WebSocket events. These operations doesn't affect sending HTTP requests.</p>
 * <p>- When an exception occurs in the operation of sending HTTP request, the method will return a JSON string (like {"Exception":" result of Exception.toString()"}) instead of throwing an Exception.</p>
 */
public class G4JClient extends WebSocketClient
{
    private static URI initURI(){try{return new URI("wss://api.guilded.gg/v1/websocket");}catch(URISyntaxException e){/*this is impossible*/return null;}}
    private static final URI WEBSOCKET_URI=initURI();
    private static final String MSG_CHANNEL_URL="https://www.guilded.gg/api/v1/channels/{channelId}/messages";
    private static final String FORUM_CHANNEL_URL="https://www.guilded.gg/api/v1/channels/{channelId}/forum";
    private static final String LIST_CHANNEL_URL="https://www.guilded.gg/api/v1/channels/{channelId}/list";
    private static final String USER_XP_URL="https://www.guilded.gg/api/v1/members/{userId}/xp";
    private static final String ROLE_XP_URL="https://www.guilded.gg/api/v1/roles/{roleId}/xp";
    private static final String GROUP_URL="https://www.guilded.gg/api/v1/groups/{groupId}/members/{userId}";
    private static final String ROLE_URL="https://www.guilded.gg/api/v1/members/{userId}/roles/{roleId}";
    private static final String REACTION_URL="https://www.guilded.gg/api/v1/channels/{channelId}/content/{contentId}/emotes/{emoteId}";
    protected String authToken;
    private boolean dump=false;

    /**
     * Used to post events or register an event listener class.
     * Write your own event listener class and use {@code bus.register()} to receive events.
     * See {@link G4JDebugger} for example uses.
     */
    public static EventBus bus = new EventBus();

    /**
     * Generate a Guilded4J client using the given access token.
     * @param token The bot API access token (without "Bearer" prefix).
     */
    public G4JClient(String token)
    {
        super(WEBSOCKET_URI);
        this.setAuthToken(token);
    }

    /**
     * Toggle printing the received JSON string from WebSocket to stdout.
     * Disabled (dump=false) by default.
     * @return Dumping status after changed.
     */
    public boolean toggleDump(){dump=!dump;return dump;}

    /**
     * No current use. Implements WebSocketClient.onOpen() (org.java_websocket.client).
     * @param h No current use.
     */
    @Override
    public void onOpen(ServerHandshake h){}

//============================== EVENT MANAGER START ==============================
    /**
     * Parse the received JSON string to various event objects. Implements WebSocketClient.onMessage() (org.java_websocket.client).
     * @param rawMessage The original WebSocket message received (should be in JSON format).
     */
    @Override
    public void onMessage(String rawMessage)
    {
        JSONObject json=new JSONObject(rawMessage);
        if(dump) System.out.println("\n"+json.toStringPretty());
        if(json.getByPath("d.heartbeatIntervalMs")!=null)
        {
            bus.post(
                    new GuildedWebsocketInitializedEvent(this,(String)json.getByPath("d.lastMessageId"),(Integer)json.getByPath("d.heartbeatIntervalMs"))
            );return;
        }
        String eventType=json.getStr("t");//hope they wont change this key name in the future
        if(eventType!=null)//has "t" key
        {
            switch (eventType)
            {
                case "ChatMessageCreated":
                {
                    JSONObject msgObj=(JSONObject)new JSONObject(rawMessage).getByPath("d.message");
                    bus.post(
                            new ChatMessageCreatedEvent(this, new ChatMessage().fromString(msgObj.toString())).setOpCode(json.getInt("op"))
                    );
                    break;
                }
                case "ChatMessageUpdated":
                {
                    JSONObject msgObj=(JSONObject)new JSONObject(rawMessage).getByPath("d.message");
                    bus.post(
                            new ChatMessageUpdatedEvent(this, new ChatMessage().fromString(msgObj.toString())).setOpCode(json.getInt("op"))
                    );
                    break;
                }
                case "ChatMessageDeleted":
                {
                    bus.post(
                            new ChatMessageDeletedEvent(this,
                                    (String)json.getByPath("d.message.deletedAt"),
                                    (String)json.getByPath("d.message.id"),
                                    (String)json.getByPath("d.message.channelId")
                            ).setOpCode(json.getInt("op"))
                    );
                    break;
                }
                case "TeamXpAdded":
                {
                    ArrayList<String> userIds=new ArrayList<String>();
                    JSONArray array=(JSONArray)new JSONObject(rawMessage).getByPath("d.userIds");
                    Object[] converted=array.toArray();
                    for(int i=0;i!=converted.length;i++) userIds.add((String)converted[i]);
                    bus.post(
                            new TeamXpAddedEvent(this)
                                    .setXpAmount((Integer) json.getByPath("d.amount"))
                                    .setUserIds(userIds)
                                    .setOpCode(json.getInt("op"))
                    );
                    break;
                }
                case "TeamMemberUpdated": //TODO
                case "teamRolesUpdated": //TODO
                case "TeamRolesUpdated":
                default: //no implemented GuildedEvents matched? post raw event with the event name and original string
                    bus.post(new GuildedEvent(this).setOpCode(json.getInt("op")).setEventType(eventType).setRawString(rawMessage));
            }
        }
        else if(json.getInt("op")!=null)
            bus.post(new GuildedEvent(this).setOpCode(json.getInt("op")).setRawString(rawMessage));//at least we have opcode
        else bus.post(new GuildedEvent(this).setRawString(rawMessage));//bruh moment
    }
//============================== EVENT MANAGER END ==============================

//============================== API FUNCTIONS START ==============================
////////////////////////////// Chat & messaging //////////////////////////////

    /**
     * Create a channel message.
     * <a>https://www.guilded.gg/docs/api/chat/ChannelMessageCreate</a>
     * @param content The content of the message.
     * @return A JSON string that contains a ChatMessage object called "message" if succeeded, else return a JSON string with an "Exception" key (Guilded4J's exception), or a "code" key and a "message" key (API's exception).
     */
    //TODO: createChannelMessage(String channelId, String content, @Nullable String[] replyMessageIds, @Nullable Boolean isPrivate)
    public String createChannelMessage(String channelId, String content)
    {
        try
        {
            return HttpRequest.post(MSG_CHANNEL_URL.replace("{channelId}",channelId)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    header("Content-type","application/json").
                    body(new JSONObject().set("content",content).toString()).
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return new JSONObject().set("Exception",e.toString()).set("ExceptionName",e.getClass().getName()).set("ExceptionMessage",e.getMessage()).toString();
        }
    }

    /**
     * Delete a channel message.
     * <a>https://www.guilded.gg/docs/api/chat/ChannelMessageDelete</a>
     * @return {@code null} if succeeded, else return a JSON string with an "Exception" key (Guilded4J's exception), or a "code" key and a "message" key (API's exception).
     */
    public String deleteChannelMessage(String channelId, String msgId)
    {
        try
        {
            return HttpRequest.delete(MSG_CHANNEL_URL.replace("{channelId}",channelId)+"/"+msgId).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return new JSONObject().set("Exception",e.toString()).set("ExceptionName",e.getClass().getName()).set("ExceptionMessage",e.getMessage()).toString();
        }
    }

    /**
     * Update a channel message.
     * <a>https://www.guilded.gg/docs/api/chat/ChannelMessageUpdate</a>
     * @return A JSON string that contains the updated ChatMessage object (same UUID but new content) called "message" if succeeded, else return a JSON string with an "Exception" key (Guilded4J's exception), or a "code" key and a "message" key (API's exception).
     */
    public String updateChannelMessage(String channelId, String msgId, String content)
    {
        try
        {
            return HttpRequest.put(MSG_CHANNEL_URL.replace("{channelId}",channelId)+"/"+msgId).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    header("Content-type","application/json").
                    body(new JSONObject().set("content",content).toString()).
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return new JSONObject().set("Exception",e.toString()).set("ExceptionName",e.getClass().getName()).set("ExceptionMessage",e.getMessage()).toString();
        }
    }

    /**
     * Get details for a specific chat message from a chat channel.
     * <a>https://www.guilded.gg/docs/api/chat/ChannelMessageRead</a>
     * @return A JSON string that directly contains the ChatMessage object with the given UUID if succeeded, else return a JSON string with an "Exception" key (Guilded4J's exception), or a "code" key and a "message" key (API's exception).
     */
    public String getMessage(String channelId, String msgId)
    {
        try
        {
            return new JSONObject(
                    HttpRequest.get(MSG_CHANNEL_URL.replace("{channelId}",channelId)+"/"+msgId).
                            header("Authorization","Bearer "+authToken).
                            header("Accept","application/json").
                            header("Content-type","application/json").
                            timeout(20000).execute().body()
            ).get("message").toString();
        }catch (Exception e)
        {
            return new JSONObject().set("Exception",e.toString()).set("ExceptionName",e.getClass().getName()).set("ExceptionMessage",e.getMessage()).toString();
        }
    }

    /**
     * Get a list of the latest 50 messages from a channel.
     * <a>https://www.guilded.gg/docs/api/chat/ChannelMessageReadMany</a>
     * @return An ChatMessage type ArrayList that contains up to 50 ChatMessage objects if succeeded, else return {@code null}
     */
    public ArrayList<ChatMessage> getChannelMessages(String channelId)
    {
        ArrayList<ChatMessage> messages=new ArrayList<ChatMessage>();
        try
        {
            String rawResult= HttpRequest.get(MSG_CHANNEL_URL.replace("{channelId}",channelId)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    header("Content-type","application/json").
                    timeout(20000).execute().body();
            JSONArray array=new JSONObject(rawResult).getJSONArray("messages");
            Object[] converted=array.toArray();
            for(int i=0;i!=converted.length;i++) messages.add(new ChatMessage().fromString((new JSONObject(converted[i]).toString())));
            return messages;
        }catch (Exception e)
        {
            return null;
        }
    }

////////////////////////////// Members //////////////////////////////

    public String[] getMemberRoles(String userId) //TODO
    {
        return null;
    }

    public String updateMemberNickname(String userId, String nickname) //TODO
    {
        return null;
    }

    public String deleteMemberNickname(String userId) //TODO
    {
        return null;
    }

////////////////////////////// Forums //////////////////////////////

    /**
     * Create a thread in a forum.
     * <a>https://www.guilded.gg/docs/api/forums/ForumThreadCreate</a>
     * @param title The title of the thread.
     * @param content The thread's content.
     * @return A JSON string that contains a ForumThread object called "forumThread" if succeeded, else return a JSON string with an "Exception" key (Guilded4J's exception), or a "code" key and a "message" key (API's exception).
     */
    public String createForumThread(String channelId, String title, String content)
    {
        try
        {
            return HttpRequest.post(FORUM_CHANNEL_URL.replace("{channelId}",channelId)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    header("Content-type","application/json").
                    body(new JSONObject().set("title",title).set("content",content).toString()).
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return new JSONObject().set("Exception",e.toString()).set("ExceptionName",e.getClass().getName()).set("ExceptionMessage",e.getMessage()).toString();
        }
    }

////////////////////////////// List items //////////////////////////////

    /**
     * Create a list item.
     * <a>https://www.guilded.gg/docs/api/listItems/ListItemCreate</a>
     * @param message The item's name.
     * @param note The item's note (can be null).
     * @return A JSON string that contains a ListItem object called "listItem" if succeeded, else return a JSON string with an "Exception" key (Guilded4J's exception), or a "code" key and a "message" key (API's exception).
     */
    public String createListItem(String channelId, String message, @Nullable String note)
    {
        try
        {
            return HttpRequest.post(LIST_CHANNEL_URL.replace("{channelId}",channelId)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    header("Content-type","application/json").
                    body(new JSONObject(new JSONConfig().setIgnoreNullValue(true)).set("message",message).set("note",note).toString()).
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return new JSONObject().set("Exception",e.toString()).set("ExceptionName",e.getClass().getName()).set("ExceptionMessage",e.getMessage()).toString();
        }
    }

////////////////////////////// Reactions //////////////////////////////

    /**
     * Add a reaction emote.
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
     * Award XP to a member.
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
     * Award XP to all members with a particular role.
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

    public String getSocialLink(String type) //TODO
    {
        return null;
    }

////////////////////////////// Group membership //////////////////////////////

    /**
     * Add member to group.
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
     * Remove member from group.
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
     * Assign role to member.
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
     * Remove role from member.
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
        this.clearHeaders();
        this.addHeader("Authorization","Bearer "+authToken);
    }

    /**
     * Posts {@link GuildedWebsocketClosedEvent}. Implements WebSocketClient.onOpen() (org.java_websocket.client).
     * @param remote Is connection closed by remote peer? If so, remote=true.
     */
    @Override
    public void onClose(int code, String reason, boolean remote)
    {
        bus.post(new GuildedWebsocketClosedEvent(this,code,reason,remote));
    }

    /**
     * No current use. Implements WebSocketClient.onError (org.java_websocket.client).
     */
    @Override
    public void onError(Exception e){e.printStackTrace();}
}
