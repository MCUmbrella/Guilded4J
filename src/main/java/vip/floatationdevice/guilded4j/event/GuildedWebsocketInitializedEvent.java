// Copyright (c) 2021 MCUmbrella & contributors

package vip.floatationdevice.guilded4j.event;

/**
 * WebSocket connection opened and successfully logged in.
 * This is a Guilded4J's custom event, meaning that there is no event in the Guilded API called this name.
 */
public class GuildedWebsocketInitializedEvent extends GuildedEvent
{
    private String lastMessageId;
    private int heartbeatIntervalMs;

    /**
     * Generate GuildedWebsocketInitializedEvent using the given keys.
     * @param lastMessageId Message ID used for replaying events after a disconnect.
     * @param heartbeatIntervalMs Heartbeat / keepalive interval (milliseconds).
     */
    public GuildedWebsocketInitializedEvent(Object source, String lastMessageId, int heartbeatIntervalMs)
    {
        super(source);
        this.lastMessageId=lastMessageId;
        this.heartbeatIntervalMs=heartbeatIntervalMs;
    }

    /**
     * Get the ID used in replaying events (not implemented by Guilded4J yet).
     * @return An ID string.
     */
    public String getLastMessageId(){return this.lastMessageId;}

    /**
     * Get the keepalive interval.
     * @return Interval (integer, in milliseconds).
     */
    public int getHeartbeatInterval(){return heartbeatIntervalMs;}
}
