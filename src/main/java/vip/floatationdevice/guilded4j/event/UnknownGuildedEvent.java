/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

/**
 * Event that Guilded4J has not implemented or doesn't know how to handle.
 */
public class UnknownGuildedEvent extends GuildedEvent
{
    private Exception reason = null;

    public UnknownGuildedEvent(Object source, String json){super(source, json);}

    /**
     * Gets the reason for the GuildedEvent being unknown.
     * @return The exception that caused the event to be unknown (only set if an exception was thrown in the reflection process).
     */
    public Exception getReason(){return this.reason;}

    public UnknownGuildedEvent setReason(Exception reason)
    {
        this.reason = reason;
        return this;
    }
}
