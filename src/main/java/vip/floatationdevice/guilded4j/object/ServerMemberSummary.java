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
public class ServerMemberSummary extends UserSummary
{
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
        JSONObject user = json.getJSONObject("user");
        Util.checkNullArgument(
                user.getStr("id"),
                user.getStr("name")
        );
        Object[] rawRoleIds = json.getJSONArray("roleIds").toArray();
        int[] roleIds = new int[rawRoleIds.length];
        for(int i = 0; i < rawRoleIds.length; i++) roleIds[i] = (int) rawRoleIds[i];
        ServerMemberSummary s = new ServerMemberSummary();
        s.setId(user.getStr("id"));
        s.setType(user.getStr("type"));
        s.setName(user.getStr("name"));
        s.setAvatar(user.getStr("avatar"));
        s.setRoleIds(roleIds);
        return s;
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
     * Check if the member has the specified role.
     */
    public boolean hasRole(int roleId)
    {
        for(int i : roleIds)
            if(i == roleId) return true;
        return false;
    }

    /**
     * Convert the ServerMemberSummary object to a JSON string.
     * @return JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("user", new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                        .set("id", id)
                        .set("type", type)
                        .set("name", name)
                        .set("avatar", avatar))
                .set("roleIds", roleIds)
                .toString();
    }
}
