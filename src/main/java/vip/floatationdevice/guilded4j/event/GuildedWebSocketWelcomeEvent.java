/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.misc.Bot;

/**
 * Event that is fired when the event manager's WebSocket connection has been opened and successfully logged in.
 * This is a Guilded4J's custom event, meaning that there is no event in the Guilded API called this name.
 * NOTE: trying to find the bot's home server ID by GuildedWebSocketWelcomeEvent.getServerId() is not possible, because
 * this event's serverId is always null. There's currently no way to get the bot's home server ID by using the API.
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
        lastMessageId = j.getByPath("d.lastMessageId").toString();
        heartbeatIntervalMs = Integer.parseInt(j.getByPath("d.heartbeatIntervalMs").toString());
        self = Bot.fromJSON((JSONObject) j.getByPath("d.user"));
    }

    /**
     * Get the ID used in replaying events.
     * @return An ID string.
     */
    public String getLastMessageId(){return lastMessageId;}

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

    @Override
    public String getEventID(){return lastMessageId;}
}
