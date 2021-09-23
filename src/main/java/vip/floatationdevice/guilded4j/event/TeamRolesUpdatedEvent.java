package vip.floatationdevice.guilded4j.event;

/**
 *
 * <a>https://www.guilded.gg/docs/api/websockets/teamRolesUpdated</a>
 */
public class TeamRolesUpdatedEvent extends GuildedEvent //TODO
{
    private Object[] memberRoleIds;

    @Deprecated public TeamRolesUpdatedEvent(Object source){super(source);}
}
