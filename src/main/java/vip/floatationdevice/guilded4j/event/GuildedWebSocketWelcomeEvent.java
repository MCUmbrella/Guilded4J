/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import vip.floatationdevice.guilded4j.object.Bot;

/**
 * Event that is fired when the event manager's WebSocket connection has been opened and successfully logged in.
 * This is a Guilded4J's custom event, meaning that there is no event in the Guilded API called this name.
 */
public class GuildedWebSocketWelcomeEvent extends GuildedEvent
{
    private final String lastMessageId;
    private final int heartbeatIntervalMs;
    private final Bot self;

    /**
     * Generate GuildedWebSocketWelcomeEvent using the given keys.
     * @param lastMessageId Message ID used for replaying events after a disconnect.
     * @param heartbeatIntervalMs Heartbeat / keepalive interval (milliseconds).
     */
    public GuildedWebSocketWelcomeEvent(Object source, String lastMessageId, int heartbeatIntervalMs, Bot self)
    {
        super(source);
        this.lastMessageId = lastMessageId;
        this.heartbeatIntervalMs = heartbeatIntervalMs;
        this.self = self;
        super.setEventID(lastMessageId);
    }

    /**
     * Get the ID used in replaying events.
     * @return An ID string.
     */
    public String getLastMessageId(){return this.lastMessageId;}

    /**
     * Get the keepalive interval.
     * @return Interval (integer, in milliseconds).
     */
    public int getHeartbeatInterval(){return heartbeatIntervalMs;}

    /**
     * Get the bot's Bot object.
     * @return Bot object.
     */
    public Bot getSelf(){return self;}
}
