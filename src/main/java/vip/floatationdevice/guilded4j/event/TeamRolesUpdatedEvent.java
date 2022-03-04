/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import vip.floatationdevice.guilded4j.object.User;

/**
 * Event fired when one or more member's roles are updated.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/teamRolesUpdated" target=_blank>https://www.guilded.gg/docs/api/websockets/teamRolesUpdated</a>
 */
public class TeamRolesUpdatedEvent extends GuildedEvent
{
    private final User[] memberRoleIds;

    /**
     * Generate TeamRolesUpdatedEvent with a list of user(s) updated roles.
     * @param memberRoleIds A {@link User}[] containing the user(s) whose roles have been updated.
     */
    public TeamRolesUpdatedEvent(Object source, User[] memberRoleIds)
    {
        super(source);
        this.memberRoleIds = memberRoleIds;
    }

    /**
     * Get the list of user(s) updated roles.
     * @return A {@link User}[] containing the user(s) whose roles have been updated.
     */
    public User[] getMembers()
    {
        return this.memberRoleIds;
    }
}
