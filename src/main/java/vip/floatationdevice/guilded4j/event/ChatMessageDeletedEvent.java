/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;

/**
 * Event fired when a chat message is deleted.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ChatMessageDeleted" target=_blank>https://www.guilded.gg/docs/api/websockets/ChatMessageDeleted</a>
 */
public class ChatMessageDeletedEvent extends GuildedEvent
{
    private final String deletedAt;
    private final String id;
    private final String channelId;

    public ChatMessageDeletedEvent(Object source, String json)
    {
        super(source, json);
        JSONObject j = new JSONObject(json);
        this.deletedAt = j.getByPath("d.message.deletedAt").toString();
        this.id = j.getByPath("d.message.id").toString();
        this.channelId = j.getByPath("d.message.channelId").toString();
    }

    /**
     * Get the deletion time.
     * @return A string that contains the deletion date.
     */
    public String getDeletionTime(){return this.deletedAt;}

    /**
     * Get the deleted message's UUID.
     * @return A UUID string. Remember that the message referred to by this UUID no longer exists.
     */
    public String getMessageId(){return this.id;}

    /**
     * Get the UUID of the deleted message's channel.
     * @return A UUID string of the channel which the message belongs.
     */
    public String getChannelId(){return this.channelId;}
}
