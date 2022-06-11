/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.object.Server;

import static vip.floatationdevice.guilded4j.G4JClient.SERVER_URL;

/**
 * Manages servers.
 */
public class ServerManager extends RestManager
{
    public ServerManager(String authToken){super(authToken);}

    /**
     * Fetch various information about a given server.<br>
     * <a href="https://www.guilded.gg/docs/api/servers/ServerRead" target=_blank>https://www.guilded.gg/docs/api/servers/ServerRead</a><br>
     * Currently, the bot must be a member of the server in order to fetch its information.<br>
     * @param serverId The ID of the server.
     * @return The server's Server object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public Server getServer(String serverId)
    {
        return Server.fromJSON(
                execute(Method.GET,
                        SERVER_URL.replace("{serverId}", serverId),
                        null
                ).getJSONObject("server")
        );
    }
}
