/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.enums.SocialMedia;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.object.ServerMember;
import vip.floatationdevice.guilded4j.object.ServerMemberBan;
import vip.floatationdevice.guilded4j.object.ServerMemberSummary;

import java.util.HashMap;

import static vip.floatationdevice.guilded4j.G4JClient.*;

/**
 * Manages the server members.
 */
public class MemberManager extends RestManager
{
    public MemberManager(String authToken)
    {
        super(authToken);
    }

    /**
     * Update/delete a member's nickname.<br>
     * <a href="https://www.guilded.gg/docs/api/members/MemberNicknameUpdate" target=_blank>https://www.guilded.gg/docs/api/members/MemberNicknameUpdate</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the member.
     * @param nickname The nickname to assign to the member (use {@code null} to delete nickname).
     * @return The nickname to be set when setting nickname, {@code null} when deleting nickname.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public String setMemberNickname(String serverId, String userId, String nickname)
    {
        JSONObject result;
        if(nickname == null) // delete nickname
        {
            String rawString = HttpRequest.delete(NICKNAME_URL.replace("{serverId}", serverId).replace("{userId}", userId)).
                    header("Authorization", "Bearer " + authToken).
                    header("Accept", "application/json").
                    header("Content-type", "application/json").
                    timeout(httpTimeout).execute().body();
            if(!JSONUtil.isTypeJSON(rawString)) return null;
            else
            {
                result = new JSONObject(rawString);
                throw new GuildedException(result.getStr("code"), result.getStr("message"));
            }
        }
        else // update nickname
        {
            result = new JSONObject(HttpRequest.put(NICKNAME_URL.replace("{serverId}", serverId).replace("{userId}", userId)).
                    header("Authorization", "Bearer " + authToken).
                    header("Accept", "application/json").
                    header("Content-type", "application/json").
                    body(new JSONObject().set("nickname", nickname).toString()).
                    timeout(httpTimeout).execute().body());
            if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
            return result.get("nickname").toString();
        }
    }

    /**
     * Get a server member by ID.<br>
     * <a href="https://www.guilded.gg/docs/api/members/TeamMemberRead" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberRead/a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the member.
     * @return The member's ServerMember object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ServerMember getServerMember(String serverId, String userId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(MEMBERS_URL.replace("{serverId}", serverId) + "/" + userId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ServerMember.fromString(result.get("member").toString());
    }

    /**
     * Kick a member from the server.<br>
     * <a href="https://www.guilded.gg/docs/api/members/TeamMemberDelete" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberDelete</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the member.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void kickServerMember(String serverId, String userId)
    {
        String result = HttpRequest.delete(MEMBERS_URL.replace("{serverId}", serverId) + "/" + userId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("TeamMemberDelete returned an unexpected JSON string");
        }
    }

    /**
     * Get a list of all members in the server.<br>
     * <a href="https://www.guilded.gg/docs/api/members/TeamMemberReadMany" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberReadMany</a>
     * @param serverId The ID of the server where the members are.
     * @return A list of ServerMemberSummary objects for each member in the server.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ServerMemberSummary[] getServerMembers(String serverId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(MEMBERS_URL.replace("{serverId}", serverId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        JSONArray membersJson = result.getJSONArray("members");
        ServerMemberSummary[] members = new ServerMemberSummary[membersJson.size()];
        for(int i = 0; i < membersJson.size(); i++)
            members[i] = ServerMemberSummary.fromString(membersJson.get(i).toString());
        return members;
    }

    /**
     * Get a ban information of the member.<br>
     * <a href="https://www.guilded.gg/docs/api/members/TeamMemberBanRead" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberBanRead</a>
     * NOTE: If the member is not banned, a GuildedException will be thrown.
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the member.
     * @return A ServerMemberBan object of the banned member.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ServerMemberBan getServerMemberBan(String serverId, String userId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(BANS_URL.replace("{serverId}", serverId) + "/" + userId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ServerMemberBan.fromString(result.get("serverMemberBan").toString());
    }

    /**
     * Ban a server member.<br>
     * <a href="https://www.guilded.gg/docs/api/members/TeamMemberBanCreate" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberBanCreate</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the user to ban from this server.
     * @param reason The reason for the ban.
     * @return A ServerMemberBan object of the banned member.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ServerMemberBan banServerMember(String serverId, String userId, String reason)
    {
        JSONObject result = new JSONObject(HttpRequest.post(BANS_URL.replace("{serverId}", serverId) + "/" + userId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(reason == null ? "{}" : new JSONObject().set("reason", reason).toString()).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ServerMemberBan.fromString(result.get("serverMemberBan").toString());
    }

    /**
     * Unban a server member.<br>
     * <a href="https://www.guilded.gg/docs/api/members/TeamMemberBanDelete" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberBanDelete</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the user to unban from this server.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void unbanServerMember(String serverId, String userId)
    {
        String result = HttpRequest.delete(BANS_URL.replace("{serverId}", serverId) + "/" + userId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("TeamMemberDelete returned an unexpected JSON string");
        }
    }

    /**
     * Get all ban information of a server.<br>
     * <a href="https://www.guilded.gg/docs/api/members/TeamMemberBanReadMany" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberBanReadMany</a>
     * @param serverId The ID of the server to get ban information of.
     * @return A list of ServerMemberBan objects of the banned members.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ServerMemberBan[] getServerMemberBans(String serverId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(BANS_URL.replace("{serverId}", serverId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        JSONArray bansJson = result.getJSONArray("serverMemberBans");
        ServerMemberBan[] bans = new ServerMemberBan[bansJson.size()];
        for(int i = 0; i < bansJson.size(); i++)
            bans[i] = ServerMemberBan.fromString(bansJson.get(i).toString());
        return bans;
    }

    /**
     * Retrieves a member's public social links.<br>
     * <a href="https://www.guilded.gg/docs/api/socialLinks/MemberSocialLinkRead" target=_blank>https://www.guilded.gg/docs/api/socialLinks/MemberSocialLinkRead</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The target user's ID.
     * @param type The type of social link to retrieve (see {@link SocialMedia} for available types).
     * @return A HashMap with "type", "handle", "serviceId(nullable)" keys.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public HashMap<String, String> getSocialLink(String serverId, String userId, SocialMedia type)
    {
        JSONObject result = new JSONObject(HttpRequest.get(SOCIAL_LINK_URL.replace("{serverId}", serverId).replace("{userId}", userId).replace("{type}", type.toString().toLowerCase())).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", (String) result.getByPath("socialLink.type"));
        map.put("handle", (String) result.getByPath("socialLink.handle"));
        map.put("serviceId", (String) result.getByPath("socialLink.serviceId"));
        return map;
    }
}
