/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import vip.floatationdevice.guilded4j.object.MemberNicknameSummary;

/**
 * Event fired when a member's nickname is updated.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/TeamMemberUpdated" target=_blank>https://www.guilded.gg/docs/api/websockets/TeamMemberUpdated</a>
 */
public class TeamMemberUpdatedEvent extends GuildedEvent
{
    private final MemberNicknameSummary userInfo;

    /**
     * Generate TeamMemberUpdatedEvent using the 2 given essential keys.
     * @param id The ID of the member.
     * @param nickname The nickname that was just updated for the user.
     */
    public TeamMemberUpdatedEvent(Object source, String id, String nickname)
    {
        super(source);
        this.userInfo = new MemberNicknameSummary(id, nickname);
    }

    /**
     * Get the member's information as User object.
     * @return the ServerMember object representing the member whose nickname was updated.
     */
    public MemberNicknameSummary getUserInfo(){return userInfo;}
}
