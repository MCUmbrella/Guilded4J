/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.google.common.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.object.ChatMessage;
import vip.floatationdevice.guilded4j.object.User;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Guilded4J WebSocket event manager, built in by G4JClient, also can be used independently.
 * <p>NOTE:</p>
 * <p>- After creating G4JWebSocketClient object you need to manually call connect(), close() or reconnect() to start/stop receiving WebSocket events.</p>
 */
public class G4JWebSocketClient extends WebSocketClient
{

    protected String authToken, lastMessageId;
    private boolean dump=false;
    private static URI initURI(){try{return new URI("wss://api.guilded.gg/v1/websocket");}catch(URISyntaxException e){/*this is impossible*/return null;}}

    /**
     * Guilded API's WebSocket URI (<a>wss://api.guilded.gg/v1/websocket</a>)
     */
    public static final URI WEBSOCKET_URI=initURI();

    /**
     * Generate a G4JWebSocketClient using the given access token.
     * @param token The bot API access token (without "Bearer" prefix).
     */
    public G4JWebSocketClient(String token)
    {
        super(WEBSOCKET_URI);
        this.setAuthToken(token);
    }

    /**
     * Generate a G4JWebSocketClient using the given access token and request a replay.
     * @param token The bot API access token (without "Bearer" prefix).
     * @param lastMessageId The ID used in replaying events.
     */
    public G4JWebSocketClient(String token, String lastMessageId)
    {
        super(WEBSOCKET_URI);
        this.lastMessageId=lastMessageId;
        this.setAuthToken(token);
    }

    /**
     * Used to post events or register an event listener class.<br>
     * Write your own event listener class and use {@code eventBus.register()} to receive events.
     * See {@link G4JDebugger} for example uses.
     */
    public EventBus eventBus=new EventBus();

    /**
     * Toggle printing the received JSON string from WebSocket to stdout.
     * Disabled (dump=false) by default.
     * @return Dumping status after changed.
     */
    public boolean toggleDump(){dump=!dump;return dump;}

    /**
     * No current use.
     */
    @Override
    public void onOpen(ServerHandshake h) {}

//============================== EVENT MANAGER START ==============================
    /**
     * Parse the received JSON string to various event objects.
     * @param rawMessage The original WebSocket message received (should be in JSON format).
     */
    @Override
    public void onMessage(String rawMessage)
    {
        JSONObject json=new JSONObject(rawMessage);
        if(dump) System.out.println("\n"+json.toStringPretty());
        int op=json.getInt("op");//operation code: 0, 1, 2, 8, 9
        String eventID=json.getStr("s");//event id (aka. lastMessageId)
        String eventType=json.getStr("t");//hope they wont change this key name in the future

        switch(op)
        {
            case 1: //welcome event
            {
                eventBus.post(
                        new GuildedWebSocketInitializedEvent(this,
                                (String)json.getByPath("d.lastMessageId"),
                                (Integer)json.getByPath("d.heartbeatIntervalMs")
                        ).setOpCode(json.getInt("op"))
                );
                break;
            }
            case 2: //resume event
            {
                eventBus.post(
                        new ResumeEvent(this,
                                (String)json.getByPath("d.s")
                        ).setOpCode(op)
                );
                break;
            }
            case 8: //error replaying
            {
                throw new GuildedException("EventReplayError",(String)json.getByPath("d.message"));
                //i guess its called this
            }
            case 0: //normal event
            {
                switch (eventType)
                {
                    case "ChatMessageCreated":
                    {
                        JSONObject msgObj=(JSONObject)new JSONObject(rawMessage).getByPath("d.message");
                        eventBus.post(
                                new ChatMessageCreatedEvent(this, new ChatMessage().fromString(msgObj.toString()))
                                        .setOpCode(op)
                                        .setEventID(eventID)
                        );
                        break;
                    }
                    case "ChatMessageUpdated":
                    {
                        JSONObject msgObj=(JSONObject)new JSONObject(rawMessage).getByPath("d.message");
                        eventBus.post(
                                new ChatMessageUpdatedEvent(this, new ChatMessage().fromString(msgObj.toString()))
                                        .setOpCode(op)
                                        .setEventID(eventID)
                        );
                        break;
                    }
                    case "ChatMessageDeleted":
                    {
                        eventBus.post(
                                new ChatMessageDeletedEvent(this,
                                        (String)json.getByPath("d.message.deletedAt"),
                                        (String)json.getByPath("d.message.id"),
                                        (String)json.getByPath("d.message.channelId")
                                ).setOpCode(op).setEventID(eventID)
                        );
                        break;
                    }
                    case "TeamXpAdded":
                    {
                        JSONArray array=(JSONArray)new JSONObject(rawMessage).getByPath("d.userIds");
                        Object[] converted=array.toArray();
                        String[] userIds=new String[converted.length];
                        for(int i=0;i!=converted.length;i++) userIds[i]=((String)converted[i]);
                        eventBus.post(
                                new TeamXpAddedEvent(this, (Integer)json.getByPath("d.amount"), userIds)
                                        .setOpCode(op)
                                        .setEventID(eventID)
                        );
                        break;
                    }
                    case "TeamMemberUpdated":
                    {
                        eventBus.post(
                                new TeamMemberUpdatedEvent(this,
                                        (String)json.getByPath("d.userInfo.id"),
                                        json.getByPath("d.userInfo.nickname") instanceof cn.hutool.json.JSONNull?null:(String)json.getByPath("d.userInfo.nickname"))
                                        .setOpCode(op)
                                        .setEventID(eventID)
                        );
                    }
                    case "teamRolesUpdated":
                    case "TeamRolesUpdated":
                    {
                        JSONArray memberRoleIds=(JSONArray) json.getByPath("d.memberRoleIds");
                        User[] users=new User[memberRoleIds.size()];
                        for (int i=0;i!=memberRoleIds.size();i++)
                        {
                            JSONObject temp=(JSONObject)memberRoleIds.get(i);
                            Object[] rawRoles=temp.getJSONArray("roleIds").toArray();
                            int[] roles=new int[rawRoles.length];
                            for(int a=0;a!=rawRoles.length;a++) roles[a]=(int)rawRoles[a];
                            users[i]=new User(temp.getStr("userId")).setRoleIds(roles);
                        }
                        eventBus.post(
                                new TeamRolesUpdatedEvent(this, users)
                                        .setOpCode(op)
                                        .setEventID(eventID)
                        );
                    }
                    default: //no implemented GuildedEvents matched? post raw event with the event name and original string
                        eventBus.post(new GuildedEvent(this)
                                .setOpCode(op)
                                .setEventID(eventID)
                                .setEventType(eventType).setRawString(rawMessage));
                }
                break;
            }
            default: //unknown
            {
                eventBus.post(new GuildedEvent(this)
                        .setOpCode(op)
                        .setRawString(rawMessage));
                break;
            }
        }
    }
//============================== EVENT MANAGER END ==============================

    /**
     * Posts {@link GuildedWebSocketClosedEvent}.
     * @param remote Is connection closed by remote peer? If so, remote=true.
     */
    @Override
    public void onClose(int code, String reason, boolean remote)
    {
        eventBus.post(new GuildedWebSocketClosedEvent(this,code,reason,remote));
    }

    /**
     * No current use.
     */
    @Override
    public void onError(Exception e) {}

    /**
     * Initialize or reset Guilded bot access token.
     * @param token The bot API access token (without "Bearer" prefix).
     */
    public void setAuthToken(String token)
    {
        authToken=token;
        this.clearHeaders();
        this.addHeader("Authorization","Bearer "+authToken);
        if(lastMessageId!=null) this.addHeader("guilded-last-message-id",lastMessageId);
    }
}
