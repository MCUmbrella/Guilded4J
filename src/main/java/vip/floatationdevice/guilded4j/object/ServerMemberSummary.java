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
 * Represents a short summary of a server member.<br>
 * <a href="https://www.guilded.gg/docs/api/members/TeamMemberSummary" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberSummary</a>
 */
public class ServerMemberSummary
{
    UserSummary user;
    int[] roleIds;

    /**
     * Get the user summary of the member.
     * @return The user summary.
     */
    public UserSummary getUser(){return user;}

    /**
     * Get the role IDs of the member.
     * @return The role IDs.
     */
    public int[] getRoleIds(){return roleIds;}

    public ServerMemberSummary setUser(UserSummary user)
    {
        this.user = user;
        return this;
    }

    public ServerMemberSummary setRoleIds(int[] roleIds)
    {
        this.roleIds = roleIds;
        return this;
    }

    /**
     * Generate a ServerMemberSummary object from a JSON string.
     * @param jsonString The JSON string.
     */
    public static ServerMemberSummary fromString(String jsonString)
    {
        if(JSONUtil.isTypeJSON(jsonString))
        {
            JSONObject json = new JSONObject(jsonString);
            Util.checkNullArgument(
                    json.get("user"),
                    json.get("roleIds")
            );
            Object[] rawRoleIds = json.getJSONArray("roleIds").toArray();
            int[] roleIds = new int[rawRoleIds.length];
            for(int i = 0; i < rawRoleIds.length; i++) roleIds[i] = (int) rawRoleIds[i];
            return new ServerMemberSummary()
                    .setUser(UserSummary.fromString(json.get("user").toString()))
                    .setRoleIds(roleIds);
        }
        else throw new ClassCastException("The provided String's content can't be converted to JSON object");
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
