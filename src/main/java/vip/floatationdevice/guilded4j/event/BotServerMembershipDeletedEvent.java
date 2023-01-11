/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.Server;

/**
 * Emitted when a bot is removed from a server.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/BotServerMembershipDeleted" target=_blank>https://www.guilded.gg/docs/api/websockets/BotServerMembershipDeleted</a>
 */
public class BotServerMembershipDeletedEvent extends GuildedEvent
{
    private final Server server;
    private final String deletedBy;

    public BotServerMembershipDeletedEvent(Object source, String json)
    {
        super(source, json);
        JSONObject j = new JSONObject(json);
        server = Server.fromJSON((JSONObject) j.getByPath("d.server"));
        deletedBy = (String) j.getByPath("d.deletedBy");
    }

    /**
     * Get the Server object of the server that the bot was removed from.
     */
    public Server getServer(){return server;}

    /**
     * Get the ID of the user who removed the bot.
     */
    public String getDeletedBy(){return deletedBy;}
}
