// Copyright (c) 2021 MCUmbrella & contributors

package vip.floatationdevice.guilded4j.event;

import vip.floatationdevice.guilded4j.object.ChatMessage;

/**
 * A message was updated.
 * <a>https://www.guilded.gg/docs/api/websockets/ChatMessageUpdated</a>
 */
public class ChatMessageUpdatedEvent extends GuildedEvent
{
    private ChatMessage msgObj;

    /**
     * Generate ChatMessageUpdatedEvent using the given ChatMessage object.
     * @param msgObject The newly created message object.
     */
    public ChatMessageUpdatedEvent(Object source, ChatMessage msgObject)
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
