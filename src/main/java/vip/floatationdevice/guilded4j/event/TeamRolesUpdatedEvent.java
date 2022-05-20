/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.misc.MemberRoleSummary;

/**
 * Event fired when one or more member's roles are updated.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/teamRolesUpdated" target=_blank>https://www.guilded.gg/docs/api/websockets/teamRolesUpdated</a>
 */
public class TeamRolesUpdatedEvent extends GuildedEvent
{
    private final MemberRoleSummary[] memberRoleIds;

    public TeamRolesUpdatedEvent(Object source, String json)
    {
        super(source, json);
        JSONArray memberRoleIdsArray = (JSONArray) new JSONObject(json).getByPath("d.memberRoleIds");
        this.memberRoleIds = new MemberRoleSummary[memberRoleIdsArray.size()];
        for(int i = 0; i != memberRoleIdsArray.size(); i++)
            this.memberRoleIds[i] = MemberRoleSummary.fromJSON(memberRoleIdsArray.getJSONObject(i));
    }

    /**
     * Get the list of user(s) updated roles.
     * @return A {@link MemberRoleSummary}[] containing the user(s) whose roles have been updated.
     */
    public MemberRoleSummary[] getMembers(){return this.memberRoleIds;}
}
