/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.enums.MemberRemoveCause;

/**
 * Event fired when a member is removed from a server.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/TeamMemberRemoved" target=_blank>https://www.guilded.gg/docs/api/websockets/TeamMemberRemoved</a>
 */
public class TeamMemberRemovedEvent extends GuildedEvent
{
    private final String userId;
    private final MemberRemoveCause cause;

    public TeamMemberRemovedEvent(Object source, String json)
    {
        super(source, json);
        JSONObject j = new JSONObject(json);
        Object isKick = j.getByPath("d.isKick");
        Object isBan = j.getByPath("d.isBan");
        this.userId = j.getByPath("d.userId").toString();
        if(Boolean.parseBoolean(isKick == null ? "false" : isKick.toString())) cause = MemberRemoveCause.BAN;
        else if(Boolean.parseBoolean(isBan == null ? "false" : isBan.toString())) cause = MemberRemoveCause.KICK;
        else cause = MemberRemoveCause.LEAVE;
    }

    /**
     * Get the ID of the member who is removed from the server.
     */
    public String getUserId(){return userId;}

    /**
     * Get the cause of the member removal.
     * @return MemberRemoveCause: KICK, BAN, LEAVE
     */
    public MemberRemoveCause getCause(){return cause;}
}
