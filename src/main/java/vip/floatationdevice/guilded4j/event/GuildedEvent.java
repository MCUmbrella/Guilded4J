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
    int op=Integer.MIN_VALUE;
    String eventType;
    String rawString;

    /**
     * Default constructor.
     */
    public GuildedEvent(Object source)
    {
        super(source);
    }

    /**
     * Generate GuildedEvent with a given operation code.
     * @param op An operation code corresponding to the nature of the sent message (for example, success, failure, etc.).
     */
    public GuildedEvent(Object source, int op)
    {
        super(source);
        this.op=op;
    }

    /**
     * Generate GuildedEvent with a given operation code and a WebSocket event name.
     * @param op An operation code corresponding to the nature of the sent message (for example, success, failure, etc.).
     * @param eventType WebSocket event's name.
     */
    public GuildedEvent(Object source, int op, String eventType)
    {
        super(source);
        this.op=op;
        this.eventType=eventType;
    }

    public int getOpcode(){return this.op;}
    @Nullable public String getEventType(){return this.eventType;}

    /**
     * Get the original WebSocket message of the event.
     * @return A JSON string that contains the original data received.
     */
    @Nullable public String getRawString(){return this.rawString;}

    public GuildedEvent setOpCode(int opCode){this.op=opCode;return this;}
    public GuildedEvent setEventType(String t){this.eventType=t;return this;}

    /**
     * Set the original WebSocket message of the event.
     * @param rawString A JSON string that contains the original data received.
     */
    public GuildedEvent setRawString(String rawString){this.rawString=rawString;return this;}
}
