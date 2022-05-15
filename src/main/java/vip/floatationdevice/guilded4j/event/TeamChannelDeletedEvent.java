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
public class TeamChannelDeletedEvent extends GuildedEvent
{
    private final ServerChannel channel;

    public TeamChannelDeletedEvent(Object source, String json)
    {
        super(source, json);
        JSONObject j = new JSONObject(json);
        this.channel = ServerChannel.fromString(j.getByPath("d.channel").toString());
    }

    /**
     * Gets the channel that was deleted.
     * @return The channel's ServerChannel object.
     */
    public ServerChannel getChannel(){return channel;}

}
