/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.Emote;

/**
 * Event fired when a reaction is deleted from a chat message.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ChannelMessageReactionDeleted", target=_blank>https://www.guilded.gg/docs/api/websockets/ChannelMessageReactionDeleted</a>
 */
public class ChannelMessageReactionDeletedEvent extends GuildedEvent // TODO: wait
{
    private final String channelId, messageId, createdBy;
    private final Emote emote;
    public ChannelMessageReactionDeletedEvent(Object source, String json)
    {
        super(source, json);
        JSONObject j = (JSONObject) new JSONObject(json).getByPath("d.reaction");
        this.channelId = j.getStr("channelId");
        this.messageId = j.getStr("messageId");
        this.createdBy = j.getStr("createdBy");
        this.emote = Emote.fromJSON(j.getJSONObject("emote"));
    }

    public String getChannelId() {return channelId;}
    public String getMessageId() {return messageId;}
    public String getCreatedBy() {return createdBy;}
    public Emote getEmote() {return emote;}
}
