package vip.floatationdevice.guilded4j.event;

import vip.floatationdevice.guilded4j.ChatMessage;

/**
 * A new message was created.
 */
public class ChatMessageCreatedEvent extends GuildedEvent// https://www.guilded.gg/docs/api/websockets/ChatMessageCreated
{
    ChatMessage msgObj;

    /**
     * Generate ChatMessageCreatedEvent using the given ChatMessage object.
     * @param msgObject The newly created message object.
     */
    public ChatMessageCreatedEvent(Object source, ChatMessage msgObject)
    {
        super(source);
        this.msgObj=msgObject;
    }

    /**
     * Get the ChatMessage object of the event.
     * @return A ChatMessage object.
     */
    public ChatMessage getChatMessageObject(){return this.msgObj;}
}
