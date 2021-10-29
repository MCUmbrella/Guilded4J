/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import java.util.HashMap;

/**
 * Not implemented yet.<br>
 * <a>https://www.guilded.gg/docs/api/websockets/TeamMemberUpdated</a>
 */
public class TeamMemberUpdatedEvent extends GuildedEvent //TODO
{
    private HashMap<String,String> userInfo;
    private String id, nickname;

    public TeamMemberUpdatedEvent(Object source){super(source);}
}
