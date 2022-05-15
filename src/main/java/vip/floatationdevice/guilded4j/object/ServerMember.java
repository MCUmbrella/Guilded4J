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
 * Represents a member of a server.<br>
 * <a href="https://www.guilded.gg/docs/api/members/TeamMember" target=_blank>https://www.guilded.gg/docs/api/members/TeamMember</a>
 */
public class ServerMember
{
    User user;
    int[] roleIds;
    String nickname;
    String joinedAt;

    /**
     * Get the member's user object.
     * @return The member's user object.
     */
    public User getUser(){return user;}

    /**
     * Get the member's role IDs.
     * @return The member's role IDs.
     */
    public int[] getRoleIds(){return roleIds;}

    /**
     * Get the member's nickname.
     * @return The member's nickname.
     */
    public String getNickname(){return nickname;}

    /**
     * Get the member's join time.
     * @return The ISO 8601 timestamp of when the member joined.
     */
    public String getJoinTime(){return joinedAt;}

    public ServerMember setUser(User user)
    {
        this.user = user;
        return this;
    }

    public ServerMember setRoleIds(int[] roleIds)
    {
        this.roleIds = roleIds;
        return this;
    }

    public ServerMember setNickname(String nickname)
    {
        this.nickname = nickname;
        return this;
    }

    public ServerMember setJoinTime(String joinedAt)
    {
        this.joinedAt = joinedAt;
        return this;
    }

    /**
     * Generate ServerMember object from JSON string.
     * @param jsonString The JSON data.
     */
    public static ServerMember fromString(String jsonString)
    {
        if(JSONUtil.isTypeJSON(jsonString))
        {
            JSONObject json = new JSONObject(jsonString);
            Util.checkNullArgument(
                    json.get("user"),
                    json.get("roleIds"),
                    json.get("joinedAt")
            );
            Object[] rawRoleIds = json.getJSONArray("roleIds").toArray();
            int[] roleIds = new int[rawRoleIds.length];
            for(int i = 0; i < rawRoleIds.length; i++) roleIds[i] = (int) rawRoleIds[i];
            return new ServerMember()
                    .setUser(User.fromString(json.getJSONObject("user").toString()))
                    .setRoleIds(roleIds)
                    .setNickname(json.getStr("nickname"))
                    .setJoinTime(json.getStr("joinedAt"));
        }
        else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }

    /**
     * Convert ServerMember object to JSON string.
     * @return JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("user", new JSONObject(user.toString()))
                .set("roleIds", roleIds)
                .set("nickname", nickname)
                .set("joinedAt", joinedAt)
                .toString();
    }
}
