// Copyright (c) 2021 MCUmbrella & contributors

package vip.floatationdevice.guilded4j.event;

/**
 * A message was deleted.
 * <a>https://www.guilded.gg/docs/api/websockets/ChatMessageDeleted</a>
 */
public class ChatMessageDeletedEvent extends GuildedEvent
{
    String deletedAt;
    String id;
    String channelId;

    /**
     * Default constructor (not recommended to use).
     * Remember to set the 3 essential keys if really need to use.
     */
    @Deprecated public ChatMessageDeletedEvent(Object source)
    {
        super(source);
    }

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
     * Get the UUID of the deleted message.
     * @return A UUID string of the channel which the message belongs.
     */
    public String getChannelId(){return this.channelId;}

    public ChatMessageDeletedEvent setDeletionTime(String deletedAt){this.deletedAt=deletedAt;return this;}
    public ChatMessageDeletedEvent setMsgId(String msgId){this.id=msgId;return this;}
    public ChatMessageDeletedEvent setChannelId(String channelId){this.channelId=channelId;return this;}
}
