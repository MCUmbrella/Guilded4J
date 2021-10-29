// Copyright (c) 2021 MCUmbrella & contributors

package vip.floatationdevice.guilded4j.event;

import java.util.HashMap;

/**
 * Not implemented yet.
 * <a>https://www.guilded.gg/docs/api/websockets/TeamMemberUpdated</a>
 */
public class TeamMemberUpdatedEvent extends GuildedEvent //TODO
{
    private HashMap<String,String> userInfo;
    private String id, nickname;

    public TeamMemberUpdatedEvent(Object source){super(source);}
}
