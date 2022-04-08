/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import vip.floatationdevice.guilded4j.object.ServerMemberBan;

/**
 * Event fired when a server member is unbanned.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/TeamMemberUnbanned">https://www.guilded.gg/docs/api/websockets/TeamMemberUnbanned</a>
 */
public class TeamMemberUnbannedEvent extends GuildedEvent
{
    private final ServerMemberBan serverMemberBan;

    public TeamMemberUnbannedEvent(Object source, ServerMemberBan serverMemberBan)
    {
        super(source);
        this.serverMemberBan = serverMemberBan;
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
