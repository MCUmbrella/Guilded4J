package vip.floatationdevice.guilded4j.event;

import vip.floatationdevice.guilded4j.ChatMessage;

public class ChatMessageCreatedEvent extends GuildedEvent// https://www.guilded.gg/docs/api/websockets/ChatMessageCreated
{
    ChatMessage msgObj;

    public ChatMessageCreatedEvent(Object source, ChatMessage msgObject)
    {
        super(source);
        this.msgObj=msgObject;
    }

    public ChatMessage getChatMessageObject(){return this.msgObj;}
}
