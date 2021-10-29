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
import vip.floatationdevice.guilded4j.object.ChatMessage;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Guilded4J WebSocket event manager, built in by G4JClient, also can be used independently.
 * <p>NOTE:</p>
 * <p>- After creating G4JWebSocketClient object you need to manually call connect(), close() or reconnect() to start/stop receiving WebSocket events.</p>
 */
public class G4JWebSocketClient extends WebSocketClient
{

    protected String authToken;
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
        if(json.getByPath("d.heartbeatIntervalMs")!=null)
        {
            eventBus.post(
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
                    eventBus.post(
                            new ChatMessageCreatedEvent(this, new ChatMessage().fromString(msgObj.toString())).setOpCode(json.getInt("op"))
                    );
                    break;
                }
                case "ChatMessageUpdated":
                {
                    JSONObject msgObj=(JSONObject)new JSONObject(rawMessage).getByPath("d.message");
                    eventBus.post(
                            new ChatMessageUpdatedEvent(this, new ChatMessage().fromString(msgObj.toString())).setOpCode(json.getInt("op"))
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
                            ).setOpCode(json.getInt("op"))
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
                                    .setOpCode(json.getInt("op"))
                    );
                    break;
                }
                case "TeamMemberUpdated": //TODO
                case "teamRolesUpdated": //TODO
                case "TeamRolesUpdated":
                default: //no implemented GuildedEvents matched? post raw event with the event name and original string
                    eventBus.post(new GuildedEvent(this).setOpCode(json.getInt("op")).setEventType(eventType).setRawString(rawMessage));
            }
        }
        else if(json.getInt("op")!=null)
            eventBus.post(new GuildedEvent(this).setOpCode(json.getInt("op")).setRawString(rawMessage));//at least we have opcode
        else eventBus.post(new GuildedEvent(this).setRawString(rawMessage));//bruh moment
    }
//============================== EVENT MANAGER END ==============================

    /**
     * Posts {@link GuildedWebsocketClosedEvent}.
     * @param remote Is connection closed by remote peer? If so, remote=true.
     */
    @Override
    public void onClose(int code, String reason, boolean remote)
    {
        eventBus.post(new GuildedWebsocketClosedEvent(this,code,reason,remote));
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
    }
}
