/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
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

    public GuildedWebSocketWelcomeEvent(Object source, String json)
    {
        super(source, json);
        JSONObject j = new JSONObject(json);
        this.lastMessageId = j.getByPath("d.lastMessageId").toString();
        this.heartbeatIntervalMs = Integer.parseInt(j.getByPath("d.heartbeatIntervalMs").toString());
        this.self = Bot.fromString(j.getByPath("d.user").toString());
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
