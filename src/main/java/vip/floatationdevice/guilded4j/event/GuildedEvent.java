/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import javax.annotation.Nullable;
import java.util.EventObject;

/**
 * Basic unprocessed Guilded WebSocket event.
 */
public class GuildedEvent extends EventObject
{
    int op;// "op" key in the WebSocket message
    String eventID;// "s" key in the WebSocket message
    String eventType;// "t" key in the WebSocket message
    String rawString;

    /**
     * Default constructor.
     */
    public GuildedEvent(Object source)
    {
        super(source);
    }

    /**
     * Get the operation code corresponding to the nature of the sent message.
     * @return The operation code:
     * 0(normal), 1(WebSocket connection opened), 2(replay done and ready to resume), 8(invalid lastMessageId), or 9(unknown)
     */
    public int getOpCode(){return this.op;}

    /**
     * Get the WebSocket message ID.
     * @return The WebSocket message ID used for replaying events after a disconnect.
     */
    @Nullable public String getEventID(){return this.eventID;}

    /**
     * Get the WebSocket event's name.
     * @return Event name for the given WebSocket message.
     */
    @Nullable public String getEventType(){return this.eventType;}

    /**
     * Get the original WebSocket message of the event.
     * @return A JSON string that contains the original data received.
     */
    @Nullable public String getRawString(){return this.rawString;}

    public GuildedEvent setOpCode(int op){this.op=op;return this;}
    public GuildedEvent setEventID(String s){this.eventID=s;return this;}
    public GuildedEvent setEventType(String t){this.eventType=t;return this;}

    /**
     * Set the original WebSocket message of the event.
     * @param rawString A JSON string that contains the original data received.
     */
    public GuildedEvent setRawString(String rawString){this.rawString=rawString;return this;}
}
