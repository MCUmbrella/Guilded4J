/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.ServerChannel;

/**
 * Event fired when a server channel is deleted.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/TeamChannelDeleted" target=_blank>https://www.guilded.gg/docs/api/websockets/TeamChannelDeleted</a>
 */
public class ServerChannelDeletedEvent extends GuildedEvent
{
    private final ServerChannel channel;

    public ServerChannelDeletedEvent(Object source, String json)
    {
        super(source, json);
        this.channel = ServerChannel.fromJSON((JSONObject) new JSONObject(json).getByPath("d.channel"));
    }

    /**
     * Gets the channel that was deleted.
     * @return The channel's ServerChannel object.
     */
    public ServerChannel getChannel(){return channel;}
}
