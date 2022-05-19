/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.ChatMessage;

/**
 * Event fired when a chat message is updated.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ChatMessageUpdated" target=_blank>https://www.guilded.gg/docs/api/websockets/ChatMessageUpdated</a>
 */
public class ChatMessageUpdatedEvent extends GuildedEvent
{
    private final ChatMessage message;

    public ChatMessageUpdatedEvent(Object source, String json)
    {
        super(source, json);
        this.message = ChatMessage.fromJSON((JSONObject) new JSONObject(json).getByPath("d.message"));
    }

    /**
     * Get the ChatMessage object of the event.
     * @return A ChatMessage object.
     */
    public ChatMessage getChatMessageObject(){return message;}
}
