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
import vip.floatationdevice.guilded4j.object.*;

import java.net.URI;

/**
 * Guilded4J WebSocket event manager, built in by G4JClient, also can be used independently.
 * <p>NOTE:</p>
 * <p>- After creating G4JWebSocketClient object you need to manually call connect(), close() or reconnect() to start/stop receiving WebSocket events.</p>
 */
public class G4JWebSocketClient extends WebSocketClient
{

    String authToken, lastMessageId;
    int heartbeatIntervalMs = 20000;
    private boolean dump = false;

    /**
     * Used to post events or register an event listener class.<br>
     * Write your own event listener class and use {@code eventBus.register()} to receive events.
     */
    public EventBus eventBus = new EventBus();

    /**
     * Guilded API's WebSocket URI (<a>wss://api.guilded.gg/v1/websocket</a>)
     */
    public static final URI WEBSOCKET_URI = URI.create("wss://api.guilded.gg/v1/websocket");

    /**
     * Generate a G4JWebSocketClient using the given access token.
     * @param token The bot API access token (without "Bearer" prefix).
     */
    public G4JWebSocketClient(String token)
    {
        super(WEBSOCKET_URI);
        this.setAuthToken(token).setHeartbeatInterval(heartbeatIntervalMs);
    }

    /**
     * Generate a G4JWebSocketClient using the given access token and request a replay.
     * @param token The bot API access token (without "Bearer" prefix).
     * @param lastMessageId The ID used in replaying events.
     */
    public G4JWebSocketClient(String token, String lastMessageId)
    {
        super(WEBSOCKET_URI);
        this.lastMessageId = lastMessageId;
        this.setAuthToken(token).setHeartbeatInterval(heartbeatIntervalMs);
    }

    /**
     * Toggle printing the received JSON string from WebSocket to stdout.
     * Disabled (dump=false) by default.
     * @return Dumping status after changed.
     */
    public boolean toggleDump()
    {
        dump = !dump;
        return dump;
    }

//============================== EVENT MANAGER START ==============================

    /**
     * Parse the received JSON string to various event objects.
     * @param rawMessage The original WebSocket message received (should be in JSON format).
     */
    @Override
    public void onMessage(String rawMessage)
    {
        JSONObject json = new JSONObject(rawMessage);
        if(dump) System.out.println("\n" + json.toStringPretty());
        int op = json.getInt("op");//operation code: 0, 1, 2, 8, 9
        String eventID = json.getStr("s");//event id (aka. lastMessageId)
        String eventType = json.getStr("t");//hope they wont change this key name in the future
        String serverID = (String) json.getByPath("d.serverId");// null if op != 0

        switch(op)
        {
            case 1: //welcome event
            {
                eventBus.post(
                        new GuildedWebSocketInitializedEvent(this,
                                (String) json.getByPath("d.lastMessageId"),
                                (Integer) json.getByPath("d.heartbeatIntervalMs"),
                                Bot.fromString(json.getByPath("d.user").toString())
                        ).setOpCode(op)
                );
                break;
            }
            case 2: //resume event
            {
                eventBus.post(
                        new ResumeEvent(this,
                                (String) json.getByPath("d.s")
                        ).setOpCode(op)
                );
                break;
            }
            case 8: //error replaying
            {
                throw new GuildedException("EventReplayError", (String) json.getByPath("d.message"));
                //i guess its called this
            }
            case 0: //normal event
            {
                switch(eventType)
                {
                    case "ChatMessageCreated":
                    {
                        eventBus.post(
                                new ChatMessageCreatedEvent(this, ChatMessage.fromString(new JSONObject(rawMessage).getByPath("d.message").toString())
                                ).setOpCode(op).setEventID(eventID).setServerID(serverID)
                        );
                        break;
                    }
                    case "ChatMessageUpdated":
                    {
                        eventBus.post(
                                new ChatMessageUpdatedEvent(this, ChatMessage.fromString(new JSONObject(rawMessage).getByPath("d.message").toString())
                                ).setOpCode(op).setEventID(eventID).setServerID(serverID)
                        );
                        break;
                    }
                    case "ChatMessageDeleted":
                    {
                        eventBus.post(
                                new ChatMessageDeletedEvent(this,
                                        json.getByPath("d.message.deletedAt").toString(),
                                        json.getByPath("d.message.id").toString(),
                                        json.getByPath("d.message.channelId").toString()
                                ).setOpCode(op).setEventID(eventID).setServerID(serverID)
                        );
                        break;
                    }
                    case "TeamXpAdded":
                    {
                        Object[] converted = ((JSONArray) new JSONObject(rawMessage).getByPath("d.userIds")).toArray();
                        String[] userIds = new String[converted.length];
                        for(int i = 0; i != converted.length; i++) userIds[i] = (converted[i].toString());
                        eventBus.post(
                                new TeamXpAddedEvent(this, (Integer) json.getByPath("d.amount"), userIds)
                                        .setOpCode(op).setEventID(eventID).setServerID(serverID)
                        );
                        break;
                    }
                    case "TeamMemberUpdated":
                    {
                        eventBus.post(
                                new TeamMemberUpdatedEvent(this,
                                        json.getByPath("d.userInfo.id").toString(),
                                        json.getByPath("d.userInfo.nickname") instanceof cn.hutool.json.JSONNull ? null : json.getByPath("d.userInfo.nickname").toString()
                                ).setOpCode(op).setEventID(eventID).setServerID(serverID)
                        );
                         break;
                    }
                    case "teamRolesUpdated":
                    case "TeamRolesUpdated":
                    {
                        Object[] memberRoleIds = ((JSONArray) json.getByPath("d.memberRoleIds")).toArray();
                        MemberRoleSummary[] users = new MemberRoleSummary[memberRoleIds.length];
                        for(int i = 0; i != memberRoleIds.length; i++) users[i] = MemberRoleSummary.fromString(memberRoleIds[i].toString());
                        eventBus.post(
                                new TeamRolesUpdatedEvent(this, users)
                                        .setOpCode(op).setEventID(eventID).setServerID(serverID)
                        );
                        break;
                    }
                    case "TeamMemberJoined":
                    {
                        eventBus.post(
                                new TeamMemberJoinedEvent(this,
                                        ServerMember.fromString(json.getByPath("d.member").toString())
                                ).setOpCode(op).setEventID(eventID).setServerID(serverID)
                        );
                        break;
                    }
                    case "TeamMemberRemoved":
                    {
                        Object isKick = json.getByPath("d.isKick");
                        Object isBan = json.getByPath("d.isBan");
                        eventBus.post(
                                new TeamMemberRemovedEvent(this,
                                        json.getByPath("d.userId").toString(),
                                        Boolean.parseBoolean(isKick == null ? "false" : isKick.toString()),
                                        Boolean.parseBoolean(isBan == null ? "false" : isBan.toString())
                                ).setOpCode(op).setEventID(eventID).setServerID(serverID)
                        );
                        break;
                    }
                    case "TeamMemberBanned":
                    {
                        eventBus.post(
                                new TeamMemberBannedEvent(this,
                                        ServerMemberBan.fromString(json.getByPath("d.serverMemberBan").toString())
                                ).setOpCode(op).setEventID(eventID).setServerID(serverID)
                        );
                        break;
                    }
                    case "TeamMemberUnbanned":
                    {
                        eventBus.post(
                                new TeamMemberUnbannedEvent(this,
                                        ServerMemberBan.fromString(json.getByPath("d.serverMemberBan").toString())
                                ).setOpCode(op).setEventID(eventID).setServerID(serverID)
                        );
                        break;
                    }
                    case "TeamWebhookCreated":
                    {
                        eventBus.post(
                                new TeamWebhookCreatedEvent(this,
                                        Webhook.fromString(json.getByPath("d.webhook").toString())
                                ).setOpCode(op).setEventID(eventID).setServerID(serverID)
                        );
                        break;
                    }
                    case "TeamWebhookUpdated":
                    {
                        eventBus.post(
                                new TeamWebhookUpdatedEvent(this,
                                        Webhook.fromString(json.getByPath("d.webhook").toString())
                                ).setOpCode(op).setEventID(eventID).setServerID(serverID)
                        );
                        break;
                    }
                    default: //no implemented GuildedEvents matched? post raw event with the event name and original string
                    {
                        eventBus.post(new UnknownGuildedEvent(this)
                                .setOpCode(op)
                                .setEventID(eventID)
                                .setEventType(eventType)
                                .setServerID(serverID)
                                .setRawString(rawMessage));
                        break;
                    }
                }
                break;
            }
            default: //unknown opcode or opcode not present
            {
                eventBus.post(new UnknownGuildedEvent(this)
                        .setOpCode(op)
                        .setRawString(rawMessage));
                break;
            }
        }
    }
//============================== EVENT MANAGER END ==============================

    /**
     * No current use.
     */
    @Override
    public void onOpen(ServerHandshake h){/* do nothing */}

    /**
     * Posts {@link GuildedWebSocketClosedEvent}.
     * @param remote Is connection closed by remote peer? If so, remote=true.
     */
    @Override
    public void onClose(int code, String reason, boolean remote)
    {
        eventBus.post(new GuildedWebSocketClosedEvent(this, code, reason, remote));
    }

    /**
     * No current use.
     */
    @Override
    public void onError(Exception e){/* do nothing */}

    /**
     * Initialize or reset Guilded bot access token.
     * @param token The bot API access token (without "Bearer" prefix).
     */
    public G4JWebSocketClient setAuthToken(String token)
    {
        authToken = token;
        this.clearHeaders();
        this.addHeader("Authorization", "Bearer " + authToken);
        if(lastMessageId != null) this.addHeader("guilded-last-message-id", lastMessageId);
        return this;
    }

    /**
     * Set heartbeat interval.
     * @param ms The interval in milliseconds.
     */
    public G4JWebSocketClient setHeartbeatInterval(int ms)
    {
        if(ms < 1000) throw new IllegalArgumentException("Heartbeat interval must be at least 1000ms");
        this.heartbeatIntervalMs = ms;
        this.setConnectionLostTimeout(ms / 1000);
        return this;
    }

    /**
     * Get the heartbeat interval.
     * @return The interval in milliseconds.
     */
    public int getHeartbeatInterval()
    {
        return heartbeatIntervalMs;
    }
}
