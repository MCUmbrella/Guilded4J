/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.ServerMemberBan;

/**
 * Event fired when a server member gets banned.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/TeamMemberBanned" target=_blank>https://www.guilded.gg/docs/api/websockets/TeamMemberBanned</a>
 */
public class TeamMemberBannedEvent extends GuildedEvent
{
    private final ServerMemberBan serverMemberBan;

    public TeamMemberBannedEvent(Object source, String json)
    {
        super(source, json);
        this.serverMemberBan = ServerMemberBan.fromJSON((JSONObject) new JSONObject(json).getByPath("d.serverMemberBan"));
    }

    /**
     * Get the ban that was applied to the member.
     * @return The ServerMemberBan object.
     */
    public ServerMemberBan getServerMemberBan()
    {
        return serverMemberBan;
    }
}
