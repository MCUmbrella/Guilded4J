/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

/**
 * Event fired when a chat message is deleted.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ChatMessageDeleted" target=_blank>https://www.guilded.gg/docs/api/websockets/ChatMessageDeleted</a>
 */
public class ChatMessageDeletedEvent extends GuildedEvent
{
    private final String deletedAt;
    private final String id;
    private final String channelId;

    /**
     * Generate ChatMessageDeletedEvent using 3 given essential keys.
     * @param id The UUID of the deleted message.
     * @param channelId The UUID of the channel which the message belongs.
     * @param deletedAt The deletion time of the message.
     */
    public ChatMessageDeletedEvent(Object source, String id, String channelId, String deletedAt)
    {
        super(source);
        this.id=id;
        this.channelId=channelId;
        this.deletedAt=deletedAt;
    }

    /**
     * Get the deletion time.
     * @return A string that contains the deletion date.
     */
    public String getDeletionTime(){return this.deletedAt;}

    /**
     * Get the deleted message's UUID.
     * @return A UUID string. Remember that the message referred to by this UUID no longer exists.
     */
    public String getMsgId(){return this.id;}

    /**
     * Get the UUID of the deleted message's channel.
     * @return A UUID string of the channel which the message belongs.
     */
    public String getChannelId(){return this.channelId;}
}
