/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.Server;

/**
 * Emitted when a bot is added to a server.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/BotTeamMembershipCreated" target=_blank>https://www.guilded.gg/docs/api/websockets/BotTeamMembershipCreated</a>
 */
public class BotTeamMembershipCreatedEvent extends GuildedEvent
{
    private final Server server;
    private final String createdBy;

    public BotTeamMembershipCreatedEvent(Object source, String json)
    {
        super(source, json);
        JSONObject j = new JSONObject(json);
        server = Server.fromJSON((JSONObject) j.getByPath("d.server"));
        createdBy = (String) j.getByPath("d.createdBy");
    }

    /**
     * Get the Server object of the server that the bot was invited to.
     */
    public Server getServer(){return server;}

    /**
     * Get the ID of the user who invited the bot.
     */
    public String getCreatedBy(){return createdBy;}
}
