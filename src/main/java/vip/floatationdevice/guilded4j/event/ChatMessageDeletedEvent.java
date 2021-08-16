package vip.floatationdevice.guilded4j.event;

public class ChatMessageDeletedEvent extends GuildedEvent// https://www.guilded.gg/docs/api/websockets/ChatMessageDeleted
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
