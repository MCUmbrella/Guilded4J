/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONObject;
import com.google.common.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import vip.floatationdevice.guilded4j.event.GuildedWebSocketClosedEvent;
import vip.floatationdevice.guilded4j.event.GuildedWebSocketWelcomeEvent;
import vip.floatationdevice.guilded4j.event.ResumeEvent;
import vip.floatationdevice.guilded4j.event.UnknownGuildedEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;

/**
 * Guilded4J WebSocket event manager, built in by G4JClient, also can be used independently.
 * <p>NOTE:</p>
 * <p>- After creating G4JWebSocketClient object you need to manually call connect(), close() or reconnect() to start/stop receiving WebSocket events.</p>
 */
public class G4JWebSocketClient extends WebSocketClient
{
    public static final String USER_AGENT = "Guilded4J/0.9.15 Java-WebSocket/1.5.3";
    /**
     * Guilded API's WebSocket URI (<a>wss://api.guilded.gg/v1/websocket</a>)
     */
    public static final URI WEBSOCKET_URI = URI.create("wss://www.guilded.gg/websocket/v1");
    public boolean verboseEnabled = false;
    /**
     * Used to post events or register an event listener class.<br>
     * Write your own event listener class and use {@code eventBus.register()} to receive events.
     */
    public EventBus eventBus = new EventBus();
    int heartbeatIntervalMs = 10000;

    /**
     * Generate a G4JWebSocketClient using the given access token.
     * @param token The bot API access token (without "Bearer" prefix).
     */
    public G4JWebSocketClient(String token)
    {
        super(WEBSOCKET_URI);
        clearHeaders();
        addHeader("User-Agent", USER_AGENT);
        addHeader("Authorization", "Bearer " + token);
        setHeartbeatInterval(heartbeatIntervalMs);
    }

    /**
     * Generate a G4JWebSocketClient using the given access token and request a replay.
     * @param token The bot API access token (without "Bearer" prefix).
     * @param lastMessageId The ID used in replaying events.
     */
    public G4JWebSocketClient(String token, String lastMessageId)
    {
        super(WEBSOCKET_URI);
        clearHeaders();
        addHeader("User-Agent", USER_AGENT);
        addHeader("Authorization", "Bearer " + token);
        if(lastMessageId != null) addHeader("guilded-last-message-id", lastMessageId);
        setHeartbeatInterval(heartbeatIntervalMs);
    }

    /**
     * Toggle verbose mode.
     * @param status If set to true, the received messages will be printed to stdout.
     */
    public G4JWebSocketClient setVerbose(boolean status)
    {
        verboseEnabled = status;
        return this;
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
        if(verboseEnabled) System.out.println("[Guilded4J/G4JWebsocketClient] " + json.toStringPretty());
        Integer op = json.getInt("op");//operation code: 0, 1, 2, 8, 9
        String eventType = json.getStr("t");//hope they wont change this key name in the future

        switch(op)
        {
            case 1: //welcome event
            {
                eventBus.post(new GuildedWebSocketWelcomeEvent(this, rawMessage));
                break;
            }
            case 2: //resume event
            {
                eventBus.post(new ResumeEvent(this, rawMessage));
                break;
            }
            case 8: //error replaying
            {
                // Guilded will automatically close the connection
                break;
            }
            case 0: //normal event
            {
                try
                {// eventBus.post(new xxxEvent(this, rawMessage)) in reflection
                    Class<?> eventClass = Class.forName("vip.floatationdevice.guilded4j.event." + eventType + "Event");
                    Constructor<?> constructor = eventClass.getConstructor(Object.class, String.class);
                    Object event = constructor.newInstance(this, rawMessage);
                    eventBus.post(event);
                }
                catch(ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                      InstantiationException | IllegalAccessException e)
                {
                    eventBus.post(new UnknownGuildedEvent(this, rawMessage).setReason(e));
                }
                break;
            }
            default: //unknown opcode or opcode not present
            {
                eventBus.post(new UnknownGuildedEvent(this, rawMessage));
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
     * Get the heartbeat interval.
     * @return The interval in milliseconds.
     */
    public int getHeartbeatInterval()
    {
        return heartbeatIntervalMs;
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
}
