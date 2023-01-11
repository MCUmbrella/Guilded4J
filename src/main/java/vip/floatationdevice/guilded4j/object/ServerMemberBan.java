/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * Represents a ban on a member.<br>
 * <a href="https://www.guilded.gg/docs/api/member-bans/ServerMemberBan" target=_blank>https://www.guilded.gg/docs/api/member-bans/ServerMemberBan</a>
 */
public class ServerMemberBan
{
    private UserSummary user;
    private String reason, createdAt, createdBy;

    /**
     * Generate a ServerMemberBan object from the given JSON object.
     * @throws IllegalArgumentException when the essential fields are not set.
     */
    public static ServerMemberBan fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.get("user"),
                json.get("createdAt"),
                json.get("createdBy")
        );
        return new ServerMemberBan()
                .setUser(UserSummary.fromJSON(json.getJSONObject("user")))
                .setReason(json.getStr("reason"))
                .setCreationTime(json.getStr("createdAt"))
                .setCreatorId(json.getStr("createdBy"));
    }

    /**
     * Get the UserSummary object of the user who was banned.
     * @return The banned user's UserSummary object.
     */
    public UserSummary getUser(){return user;}

    public ServerMemberBan setUser(UserSummary user)
    {
        this.user = user;
        return this;
    }

    /**
     * Get the reason for the ban.
     * @return The reason for the ban as submitted by the banner.
     */
    public String getReason(){return reason;}

    public ServerMemberBan setReason(String reason)
    {
        this.reason = reason;
        return this;
    }

    /**
     * Get the date the ban was created.
     * @return The ISO 8601 timestamp that the server member ban was created at.
     */
    public String getCreationTime(){return createdAt;}

    public ServerMemberBan setCreationTime(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Get the ID of the user created the ban.
     * @return The ID of the user who created this server member ban.
     */
    public String getCreatorId(){return createdBy;}

    public ServerMemberBan setCreatorId(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * Convert the ServerMemberBan object to a JSON string.
     * @return JSON string.
     */
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("user", user)
                .set("reason", reason)
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .toString();
    }
}
