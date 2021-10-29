/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

/**
 * Not implemented yet.<br>
 * <a>https://www.guilded.gg/docs/api/websockets/teamRolesUpdated</a>
 */
public class TeamRolesUpdatedEvent extends GuildedEvent //TODO
{
    private Object[] memberRoleIds;

    @Deprecated public TeamRolesUpdatedEvent(Object source){super(source);}
}
