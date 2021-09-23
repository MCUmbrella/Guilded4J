// Copyright (c) 2021 MCUmbrella & contributors

package vip.floatationdevice.guilded4j.event;

/**
 * Not implemented yet.
 * <a>https://www.guilded.gg/docs/api/websockets/teamRolesUpdated</a>
 */
public class TeamRolesUpdatedEvent extends GuildedEvent //TODO
{
    private Object[] memberRoleIds;

    @Deprecated public TeamRolesUpdatedEvent(Object source){super(source);}
}
