/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.Util;

/**
 * Represents a summary of a member's roles. This class is not present in the official API
 * (as teamRolesUpdated's property of this is described as type "object" instead of something else).
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

    public String getUserId(){return userId;}

    public int[] getRoleIds(){return roleIds;}

    public static MemberRoleSummary fromString(String jsonString)
    {
        if(JSONUtil.isTypeJSON(jsonString))
        {
            JSONObject json = new JSONObject(jsonString);
            Util.checkNullArgument(
                    json.get("userId"),
                    json.get("roleIds")
            );
            Object[] rawRoleIds = json.getJSONArray("roleIds").toArray();
            int[] roleIds = new int[rawRoleIds.length];
            for(int i = 0; i < rawRoleIds.length; i++) roleIds[i] = (int) rawRoleIds[i];
            return new MemberRoleSummary(json.getStr("userId"), roleIds);
        }
        else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }
}
