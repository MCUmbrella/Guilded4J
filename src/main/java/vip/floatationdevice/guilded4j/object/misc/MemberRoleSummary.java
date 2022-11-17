/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object.misc;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * Represents a summary of a member's roles.
 * This class is not present in the official API
 */
public class MemberRoleSummary
{
    String userId;
    int[] roleIds;

    public MemberRoleSummary(String userId, int[] roleIds)
    {
        this.userId = userId;
        this.roleIds = roleIds;
    }

    public static MemberRoleSummary fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.get("userId"),
                json.get("roleIds")
        );
        Object[] rawRoleIds = json.getJSONArray("roleIds").toArray();
        int[] roleIds = new int[rawRoleIds.length];
        for(int i = 0; i < rawRoleIds.length; i++) roleIds[i] = (int) rawRoleIds[i];
        return new MemberRoleSummary(json.getStr("userId"), roleIds);
    }

    public String getUserId(){return userId;}

    public int[] getRoleIds(){return roleIds;}
}
