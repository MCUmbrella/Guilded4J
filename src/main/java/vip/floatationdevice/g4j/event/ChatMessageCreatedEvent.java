package vip.floatationdevice.g4j.event;

import vip.floatationdevice.g4j.ChatMessage;

public class ChatMessageCreatedEvent extends GuildedEvent
{
    int op;
    ChatMessage msgObj;
    public ChatMessageCreatedEvent(Object source, ChatMessage msgObject)
    {
        super(source);
        this.msgObj=msgObject;
    }
    public int getOpCode(){return op;}
    public ChatMessage getChatMessageObject(){return this.msgObj;}
    public ChatMessageCreatedEvent setOpCode(int opCode){this.op=opCode;return this;}
}
