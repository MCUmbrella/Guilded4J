/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import java.util.EventObject;

/**
 * Event that is fired when the event manager's WebSocket connection is closed.
 * This is a Guilded4J's custom event, meaning that there is no event in the Guilded API called this name.
 */
public class GuildedWebSocketClosedEvent extends EventObject
{
    private final int code;
    private final String reason;
    private final boolean remote;

    /**
     * Generate a GuildedWebSocketClosedEvent using the given keys.
     * @param code The WebSocket status code.
     * @param reason The reason of the disconnection (can be null).
     * @param remote Is connection closed by remote peer? If so, remote=true.
     */
    public GuildedWebSocketClosedEvent(Object source, int code, String reason, boolean remote)
    {
        super(source);
        this.code = code;
        this.reason = reason;
        this.remote = remote;
    }

    /**
     * Get the WebSocket status code.
     * @return The WebSocket status code.
     */
    public int getCode(){return code;}

    /**
     * Get the reason of disconnect.
     * @return The reason of the disconnection (can be null).
     */
    public String getReason(){return reason;}

    /**
     * Get a boolean which indicates that the connection is closed by remote.
     * @return {@code true} if remote closed the connection, else return {@code false}.
     */
    public boolean isRemote(){return remote;}

    /**
     * Check if the connection was closed because of problems connecting to Guilded
     * (e.g. error in replaying WebSocket events because of invalid lastMessageId).
     */
    public boolean isUnexpected(){return code != 1000;}
}
