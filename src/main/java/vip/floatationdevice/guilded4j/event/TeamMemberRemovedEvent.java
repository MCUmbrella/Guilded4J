/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import vip.floatationdevice.guilded4j.enums.MemberRemoveReason;

/**
 * Event fired when a member is removed from a server.
 * <a href="https://www.guilded.gg/docs/api/websockets/TeamMemberRemoved", target=_blank>https://www.guilded.gg/docs/api/websockets/TeamMemberRemoved</a>
 */
public class TeamMemberRemovedEvent extends GuildedEvent
{
    private final String userId;
    private final MemberRemoveReason reason;

    /**
     * Generate TeamMemberRemovedEvent with given parameters.
     */
    public TeamMemberRemovedEvent(Object source, String userId, boolean isKick, boolean isBan)
    {
        super(source);
        this.userId = userId;
        if (isBan) reason = MemberRemoveReason.BAN;
        else if (isKick) reason = MemberRemoveReason.KICK;
        else reason = MemberRemoveReason.LEAVE;
    }

    /**
     * Get the ID of the member who is removed from the server.
     */
    public String getUserId(){return userId;}

    /**
     * Get the reason of the member removal.
     * @return MemberRemoveReason: KICK, BAN, LEAVE
     */
    public MemberRemoveReason getReason(){return reason;}
}
