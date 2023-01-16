/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
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
public class ServerMember extends User
{
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
        JSONObject user = json.getJSONObject("user");
        Util.checkNullArgument(
                user.getStr("id"),
                user.getStr("name"),
                user.getStr("createdAt")
        );
        Object[] rawRoleIds = json.getJSONArray("roleIds").toArray();
        int[] roleIds = new int[rawRoleIds.length];
        for(int i = 0; i < rawRoleIds.length; i++) roleIds[i] = (int) rawRoleIds[i];
        ServerMember m = new ServerMember();
        m.setId(user.getStr("id"));
        m.setType(user.getStr("type"));
        m.setName(user.getStr("name"));
        m.setAvatar(user.getStr("avatar"));
        m.setBanner(user.getStr("banner"));
        m.setCreationTime(user.getStr("createdAt"));
        m.setRoleIds(roleIds);
        m.setNickname(json.getStr("nickname"));
        m.setJoinTime(json.getStr("joinedAt"));
        m.setIsOwner(json.getBool("isOwner"));
        return m;
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
     * Check if the member has the specified role.
     */
    public boolean hasRole(int roleId)
    {
        for(int i : roleIds)
            if(i == roleId) return true;
        return false;
    }

    /**
     * Convert ServerMember object to JSON string.
     * @return JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("user", new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                        .set("id", getId())
                        .set("type", getType().toString().toLowerCase())
                        .set("name", getName())
                        .set("avatar", getAvatar())
                        .set("banner", getBanner())
                        .set("createdAt", getCreationTime()))
                .set("roleIds", roleIds)
                .set("nickname", nickname)
                .set("joinedAt", joinedAt)
                .set("isOwner", isOwner)
                .toString();
    }
}
