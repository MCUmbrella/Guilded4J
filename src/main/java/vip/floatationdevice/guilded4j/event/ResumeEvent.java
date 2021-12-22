/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

/**
 * Event that is fired when the replay process completes.
 * This is a Guilded4J's custom event, meaning that there is no event in the Guilded API called this name.
 */
public class ResumeEvent extends GuildedEvent
{
    private final String lastMessageId;

    /**
     * Generate ResumeEvent using the given keys.
     * @param lastMessageId Message ID used for replaying events after a disconnect.
     */
    public ResumeEvent(Object source, String lastMessageId)
    {
        super(source);
        this.lastMessageId=lastMessageId;
        super.setEventID(lastMessageId);
    }

    /**
     * Get the ID used in replaying events.
     * @return An ID string.
     */
    public String getLastMessageId(){return this.lastMessageId;}
}
