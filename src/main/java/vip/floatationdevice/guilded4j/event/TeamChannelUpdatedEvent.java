/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.ServerChannel;

/**
 * Event fired when a server channel is updated.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/TeamChannelUpdated" target=_blank>https://www.guilded.gg/docs/api/websockets/TeamChannelUpdated</a>
 */
public class TeamChannelUpdatedEvent extends GuildedEvent
{
    private final ServerChannel channel;

    public TeamChannelUpdatedEvent(Object source, String json)
    {
        super(source, json);
        JSONObject j = new JSONObject(json);
        this.channel = ServerChannel.fromString(j.getByPath("d.channel").toString());
    }

    /**
     * Gets the channel that was updated.
     * @return The channel's ServerChannel object.
     */
    public ServerChannel getChannel(){return channel;}
}
