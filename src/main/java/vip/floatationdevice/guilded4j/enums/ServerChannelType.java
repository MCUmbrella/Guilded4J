/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.enums;

/**
 * The type of server channel.
 * This object is not part of the official Guilded API.
 */
public enum ServerChannelType
{
    /**
     * Make official posts for members to see.
     */
    ANNOUNCEMENTS,
    /**
     * A place for everyone to have conversation over messages.
     */
    CHAT,
    /**
     * Plan out and schedule events.
     */
    CALENDAR,
    /**
     * Post topics and reply to each other in a structured format.
     */
    FORUMS,
    /**
     * A collection of pics and videos.
     */
    MEDIA,
    /**
     * Write documents for others to read and comment on.
     */
    DOCS,
    /**
     * Talk to each other using voice chat.
     */
    VOICE,
    /**
     * Keep track of reminders, to-do's, and everything in-between.
     */
    LIST,
    /**
     * Slot in time to coordinate availability with others.
     */
    SCHEDULING,
    /**
     * Share your screen or cam in real-time.
     */
    STREAM,
    /**
     * The channel type that is not implemented.
     */
    UNKNOWN;

    /**
     * Gets the ServerChannelType from a string.
     * @param s The name of the ServerChannelType.
     * @return The ServerChannelType.
     */
    public static ServerChannelType fromString(String s)
    {
        if(s == null)
            return UNKNOWN;
        for(ServerChannelType type : values())
            if(type.name().equalsIgnoreCase(s))
                return type;
        return UNKNOWN;
    }
}
