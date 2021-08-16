package vip.floatationdevice.guilded4j.event;

import vip.floatationdevice.guilded4j.ChatMessage;

public class ChatMessageUpdatedEvent extends GuildedEvent// https://www.guilded.gg/docs/api/websockets/ChatMessageUpdated
{
    ChatMessage msgObj;

    public ChatMessageUpdatedEvent(Object source, ChatMessage msgObject)
    {
        super(source);
        this.msgObj=msgObject;
    }

    public ChatMessage getChatMessageObject(){return this.msgObj;}
}
