package vip.floatationdevice.g4j.event;

import vip.floatationdevice.g4j.ChatMessage;

import java.util.Date;
import java.util.UUID;

public class ChatMessageCreatedEvent extends GuildedEvent
{
    int op;
    UUID id, channelId;
    String type, content, createdBy;
    Date createdAt;
    ChatMessage msgObj;
    public ChatMessageCreatedEvent(Object source, ChatMessage msgObject)
    {
        super(source);
        this.msgObj=msgObject;
    }
    @Deprecated
    public ChatMessageCreatedEvent(Object source)
    {
        super(source);
    }
    //TODO: migrate msg members into ChatMessage object
    //'get' functions
    public int getOpCode(){return op;}
    public ChatMessage getChatMessageObject(){return this.msgObj;}
    public UUID getMsgId(){return id;}
    public UUID getChannelId(){return channelId;}
    public String getType(){return type;}
    public String getContent(){return content;}
    public Date getCreationTime(){return createdAt;}
    public String getCreator(){return createdBy;}
    //'set' functions
    public ChatMessageCreatedEvent setOpCode(int opCode){this.op=opCode;return this;}
    public ChatMessageCreatedEvent setMsgId(UUID msgId){this.id=msgId;return this;}
    public ChatMessageCreatedEvent setChannelId(UUID channelId){this.channelId=channelId;return this;}
    public ChatMessageCreatedEvent setType(String type){this.type=type;return this;}
    public ChatMessageCreatedEvent setContent(String content){this.content=content;return this;}
    public ChatMessageCreatedEvent setCreationTime(Date creationTime){this.createdAt=creationTime;return this;}
    public ChatMessageCreatedEvent setCreator(String creator){this.createdBy=creator;return this;}
}
