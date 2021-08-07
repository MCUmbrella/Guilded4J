package vip.floatationdevice.g4j.event;

public class ChatMessageDeletedEvent extends GuildedEvent
{
    String deletedAt;
    String id;
    String channelId;

    public ChatMessageDeletedEvent(Object source)
    {
        super(source);
    }

    public String getDeletionTime(){return this.deletedAt;}
    public String getMsgId(){return this.id;}
    public String getChannelId(){return this.channelId;}

    public ChatMessageDeletedEvent setDeletionTime(String deletedAt){this.deletedAt=deletedAt;return this;}
    public ChatMessageDeletedEvent setMsgId(String msgId){this.id=msgId;return this;}
    public ChatMessageDeletedEvent setChannelId(String channelId){this.channelId=channelId;return this;}
}
