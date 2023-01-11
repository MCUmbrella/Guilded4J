/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * Represents a short summary of a server member.<br>
 * <a href="https://www.guilded.gg/docs/api/members/ServerMemberSummary" target=_blank>https://www.guilded.gg/docs/api/members/ServerMemberSummary</a>
 */
public class ServerMemberSummary
{
    UserSummary user;
    int[] roleIds;

    /**
     * Generate a ServerMemberSummary object from a JSON object.
     * @throws IllegalArgumentException when the essential fields are not set.
     */
    public static ServerMemberSummary fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.get("user"),
                json.get("roleIds")
        );
        Object[] rawRoleIds = json.getJSONArray("roleIds").toArray();
        int[] roleIds = new int[rawRoleIds.length];
        for(int i = 0; i < rawRoleIds.length; i++) roleIds[i] = (int) rawRoleIds[i];
        return new ServerMemberSummary()
                .setUser(UserSummary.fromJSON(json.getJSONObject("user")))
                .setRoleIds(roleIds);
    }

    /**
     * Get the user summary of the member.
     * @return The user summary.
     */
    public UserSummary getUser(){return user;}

    public ServerMemberSummary setUser(UserSummary user)
    {
        this.user = user;
        return this;
    }

    /**
     * Get the role IDs of the member.
     * @return The role IDs.
     */
    public int[] getRoleIds(){return roleIds;}

    public ServerMemberSummary setRoleIds(int[] roleIds)
    {
        this.roleIds = roleIds;
        return this;
    }

    /**
     * Convert the ServerMemberSummary object to a JSON string.
     * @return JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("user", new JSONObject(user.toString()))
                .set("roleIds", roleIds)
                .toString();
    }
}
