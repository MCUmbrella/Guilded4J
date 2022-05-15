/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;

import java.util.EventObject;

/**
 * Basic unprocessed Guilded WebSocket event.
 */
public class GuildedEvent extends EventObject
{
    Integer op;// "op" key in the WebSocket message
    String eventID;// "s" key in the WebSocket message
    String eventType;// "t" key in the WebSocket message
    String serverID;// usually located in "d.serverId"
    String rawString;

    //public GuildedEvent(Object source){super(source);}
    public GuildedEvent(Object source, String json)
    {
        super(source);
        setRawString(json);
        JSONObject j = new JSONObject(json);
        setOpCode(j.getInt("op"));
        setEventID(j.getStr("s"));
        setEventType(j.getStr("t"));
        setServerID((String) j.getByPath("d.serverId"));
    }

    /**
     * Get the operation code corresponding to the nature of the sent message.
     * @return The operation code:
     * 0(normal), 1(WebSocket connection opened), 2(replay done and ready to resume), 8(invalid lastMessageId), or 9(unknown)
     */
    public Integer getOpCode(){return this.op;}

    /**
     * Get the WebSocket message ID.
     * @return The WebSocket message ID used for replaying events after a disconnect.
     */
    public String getEventID(){return this.eventID;}

    /**
     * Get the WebSocket event's name.
     * @return Event name for the given WebSocket message.
     */
    public String getEventType(){return this.eventType;}

    /**
     * Get the ID of the server the event was sent to.
     * @return The server ID.
     */
    public String getServerID(){return this.serverID;}

    /**
     * Get the original WebSocket message of the event.
     * @return A JSON string that contains the original data received.
     */
    public String getRawString(){return this.rawString;}

    public GuildedEvent setOpCode(Integer op)
    {
        this.op = op;
        return this;
    }

    public GuildedEvent setEventID(String s)
    {
        this.eventID = s;
        return this;
    }

    public GuildedEvent setServerID(String serverID)
    {
        this.serverID = serverID;
        return this;
    }

    public GuildedEvent setEventType(String t)
    {
        this.eventType = t;
        return this;
    }

    /**
     * Set the original WebSocket message of the event.
     * @param rawString A JSON string that contains the original data received.
     */
    public GuildedEvent setRawString(String rawString)
    {
        this.rawString = rawString;
        return this;
    }
}
