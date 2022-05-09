/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event.message;

import vip.floatationdevice.guilded4j.event.GuildedEvent;
import vip.floatationdevice.guilded4j.object.ChatMessage;

/**
 * Event fired when a chat message is created.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ChatMessageCreated" target=_blank>https://www.guilded.gg/docs/api/websockets/ChatMessageCreated</a>
 */
public class ChatMessageCreatedEvent extends GuildedEvent
{
    private final ChatMessage message;

    /**
     * Generate ChatMessageCreatedEvent using the given ChatMessage object.
     * @param message The newly created message object.
     */
    public ChatMessageCreatedEvent(Object source, ChatMessage message)
    {
        super(source);
        this.message = message;
    }

    /**
     * Get the ChatMessage object of the event.
     * @return A ChatMessage object.
     */
    public ChatMessage getChatMessageObject(){return message;}
}
