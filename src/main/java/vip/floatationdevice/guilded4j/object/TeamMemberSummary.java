/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.Util;

/**
 * Represents a short summary of a server member.
 * <a href="https://www.guilded.gg/docs/api/members/TeamMemberSummary" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberSummary</a>
 */
public class TeamMemberSummary
{
    UserSummary user;
    int[] roleIds;

    /**
     * Generate empty TeamMemberSummary object.
     */
    public TeamMemberSummary() {}

    /**
     * Get the user summary of the member.
     * @return The user summary.
     */
    public UserSummary getUserSummary(){return user;}

    /**
     * Get the role IDs of the member.
     * @return The role IDs.
     */
    public int[] getRoleIds(){return roleIds;}

    public TeamMemberSummary setUser(UserSummary user)
    {
        this.user = user;
        return this;
    }

    public TeamMemberSummary setRoleIds(int[] roleIds)
    {
        this.roleIds = roleIds;
        return this;
    }

    /**
     * Generate a TeamMemberSummary object from a JSON string.
     * @param jsonString The JSON string.
     */
    public static TeamMemberSummary fromString(String jsonString)
    {
        if(JSONUtil.isJson(jsonString))
        {
            JSONObject json = new JSONObject(jsonString);
            Util.checkNullArgument(
                    json.get("user"),
                    json.get("roleIds")
            );
            Object[] rawRoleIds = json.getJSONArray("roleIds").toArray();
            int[] roleIds = new int[rawRoleIds.length];
            for(int i = 0; i < rawRoleIds.length; i++) roleIds[i] = (int)rawRoleIds[i];
            return new TeamMemberSummary()
                    .setUser(UserSummary.fromString(json.get("user").toString()))
                    .setRoleIds(roleIds);
        }
        else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }

    /**
     * Convert the TeamMemberSummary object to a JSON string.
     * @return JSON string.
     */
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("user", user)
                .set("roleIds", roleIds)
                .toString();
    }
}
