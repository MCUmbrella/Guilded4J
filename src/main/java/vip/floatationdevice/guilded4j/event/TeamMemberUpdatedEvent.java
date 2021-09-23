package vip.floatationdevice.guilded4j.event;

/**
 *
 * <a>https://www.guilded.gg/docs/api/websockets/TeamMemberUpdated</a>
 */
public class TeamMemberUpdatedEvent extends GuildedEvent //TODO
{
    private Object userInfo;
    private String id, nickname;

    @Deprecated public TeamMemberUpdatedEvent(Object source){super(source);}
}
