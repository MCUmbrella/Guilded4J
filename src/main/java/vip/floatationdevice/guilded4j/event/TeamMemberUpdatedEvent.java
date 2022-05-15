/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.MemberNicknameSummary;

/**
 * Event fired when a member's nickname is updated.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/TeamMemberUpdated" target=_blank>https://www.guilded.gg/docs/api/websockets/TeamMemberUpdated</a>
 */
public class TeamMemberUpdatedEvent extends GuildedEvent
{
    private final MemberNicknameSummary userInfo;

    public TeamMemberUpdatedEvent(Object source, String json)
    {
        super(source, json);
        JSONObject j = new JSONObject(json);
        this.userInfo = new MemberNicknameSummary(
                j.getByPath("d.userInfo.id").toString(),
                j.getByPath("d.userInfo.nickname") instanceof cn.hutool.json.JSONNull ? null : j.getByPath("d.userInfo.nickname").toString()
        );
    }

    /**
     * Get the member's information as User object.
     * @return the ServerMember object representing the member whose nickname was updated.
     */
    public MemberNicknameSummary getUserInfo(){return userInfo;}
}
