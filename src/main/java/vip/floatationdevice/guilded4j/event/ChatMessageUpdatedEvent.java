/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import vip.floatationdevice.guilded4j.object.ChatMessage;

/**
 * Event fired when a chat message is updated.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ChatMessageUpdated" target=_blank>https://www.guilded.gg/docs/api/websockets/ChatMessageUpdated</a>
 */
public class ChatMessageUpdatedEvent extends GuildedEvent
{
    private final ChatMessage msgObj;

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
