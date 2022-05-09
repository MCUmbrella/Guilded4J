/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event.member;

import vip.floatationdevice.guilded4j.event.GuildedEvent;
import vip.floatationdevice.guilded4j.object.ServerMember;

/**
 * Event that is fired when a user joins a server.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/TeamMemberJoined", target=_blank>https://www.guilded.gg/docs/api/websockets/TeamMemberJoined</a>
 */
public class TeamMemberJoinedEvent extends GuildedEvent
{
    private final ServerMember member;

    /**
     * Generate TeamMemberJoinedEvent with given ServerMember object.
     */
    public TeamMemberJoinedEvent(Object source, ServerMember member)
    {
        super(source);
        this.member = member;
    }

    /**
     * Get the member that joined the server.
     * @return The member's ServerMember object.
     */
    public ServerMember getMember(){return member;}
}
