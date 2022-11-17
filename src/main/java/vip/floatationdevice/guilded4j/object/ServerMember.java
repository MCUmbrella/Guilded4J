/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * Represents a member of a server.<br>
 * <a href="https://www.guilded.gg/docs/api/members/ServerMember" target=_blank>https://www.guilded.gg/docs/api/members/ServerMember</a>
 */
public class ServerMember
{
    User user;
    int[] roleIds;
    String nickname;
    String joinedAt;
    Boolean isOwner;

    /**
     * Generate ServerMember object from JSON object.
     * @throws IllegalArgumentException when the essential fields are not set.
     */
    public static ServerMember fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.get("user"),
                json.get("roleIds"),
                json.get("joinedAt")
        );
        Object[] rawRoleIds = json.getJSONArray("roleIds").toArray();
        int[] roleIds = new int[rawRoleIds.length];
        for(int i = 0; i < rawRoleIds.length; i++) roleIds[i] = (int) rawRoleIds[i];
        return new ServerMember()
                .setUser(User.fromJSON(json.getJSONObject("user")))
                .setRoleIds(roleIds)
                .setNickname(json.getStr("nickname"))
                .setJoinTime(json.getStr("joinedAt"))
                .setIsOwner(json.getBool("isOwner"));
    }

    /**
     * Get the member's user object.
     * @return The member's user object.
     */
    public User getUser(){return user;}

    public ServerMember setUser(User user)
    {
        this.user = user;
        return this;
    }

    /**
     * Get the member's role IDs.
     * @return The member's role IDs.
     */
    public int[] getRoleIds(){return roleIds;}

    public ServerMember setRoleIds(int[] roleIds)
    {
        this.roleIds = roleIds;
        return this;
    }

    /**
     * Get the member's nickname.
     * @return The member's nickname.
     */
    public String getNickname(){return nickname;}

    public ServerMember setNickname(String nickname)
    {
        this.nickname = nickname;
        return this;
    }

    /**
     * Get the member's join time.
     * @return The ISO 8601 timestamp of when the member joined.
     */
    public String getJoinTime(){return joinedAt;}

    public ServerMember setJoinTime(String joinedAt)
    {
        this.joinedAt = joinedAt;
        return this;
    }

    /**
     * Is the member the server owner?
     * @return True if the member is the server owner, false otherwise.
     */
    public boolean isOwner(){return isOwner != null && isOwner;}

    public ServerMember setIsOwner(Boolean isOwner)
    {
        this.isOwner = isOwner;
        return this;
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
                .set("isOwner", isOwner)
                .toString();
    }
}
