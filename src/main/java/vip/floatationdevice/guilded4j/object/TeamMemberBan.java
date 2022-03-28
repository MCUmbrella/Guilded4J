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
 * Represents a ban on a member.
 * <a href="https://www.guilded.gg/docs/api/members/TeamMemberBan" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberBan</a>
 */
public class TeamMemberBan
{
    private UserSummary user;
    private String reason, createdAt, createdBy;

    /**
     * Get the UserSummary object of the user who was banned.
     * @return The banned user's UserSummary object.
     */
    public UserSummary getUser(){return user;}

    /**
     * Get the reason for the ban.
     * @return The reason for the ban as submitted by the banner.
     */
    public String getReason(){return reason;}

    /**
     * Get the date the ban was created.
     * @return The ISO 8601 timestamp that the server member ban was created at.
     */
    public String getCreationTime(){return createdAt;}

    /**
     * Get the ID of the user created the ban.
     * @return The ID of the user who created this server member ban.
     */
    public String getCreatorId(){return createdBy;}

    public TeamMemberBan setUser(UserSummary user)
    {
        this.user = user;
        return this;
    }

    public TeamMemberBan setReason(String reason)
    {
        this.reason = reason;
        return this;
    }

    public TeamMemberBan setCreationTime(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    public TeamMemberBan setCreatorId(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * Generate a TeamMemberBan object from the given JSON string.
     * @return TeamMemberBan object.
     * @throws IllegalArgumentException when the essential fields are not set.
     * @throws ClassCastException when the provided String's content isn't JSON format.
     */
    public static TeamMemberBan fromString(String jsonString)
    {
        if(JSONUtil.isTypeJSON(jsonString))
        {
            JSONObject json = new JSONObject(jsonString);
            Util.checkNullArgument(
                    json.get("user"),
                    json.get("createdAt"),
                    json.get("createdBy")
            );
            return new TeamMemberBan()
                    .setUser(UserSummary.fromString(json.getStr("user")))
                    .setReason(json.getStr("reason"))
                    .setCreationTime(json.getStr("createdAt"))
                    .setCreatorId(json.getStr("createdBy"));
        }
        else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }

    /**
     * Convert the TeamMemberBan object to a JSON string.
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
