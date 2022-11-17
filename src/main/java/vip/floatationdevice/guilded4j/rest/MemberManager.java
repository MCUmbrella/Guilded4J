/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.enums.SocialMedia;
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
     */
    public String setMemberNickname(String serverId, String userId, String nickname)
    {
        if(nickname == null) // delete nickname
        {
            execute(Method.DELETE,
                    NICKNAME_URL.replace("{serverId}", serverId).replace("{userId}", userId),
                    null
            );
            return null;
        }
        else // update nickname
        {
            return execute(Method.PUT,
                    NICKNAME_URL.replace("{serverId}", serverId).replace("{userId}", userId),
                    new JSONObject().set("nickname", nickname)
            ).getStr("nickname");
        }
    }

    /**
     * Get a server member by ID.<br>
     * <a href="https://www.guilded.gg/docs/api/members/ServerMemberRead" target=_blank>https://www.guilded.gg/docs/api/members/ServerMemberRead</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the member.
     * @return The member's ServerMember object.
     */
    public ServerMember getServerMember(String serverId, String userId)
    {
        return ServerMember.fromJSON(execute(Method.GET,
                MEMBERS_URL.replace("{serverId}", serverId) + "/" + userId,
                null
        ).getJSONObject("member"));
    }

    /**
     * Kick a member from the server.<br>
     * <a href="https://www.guilded.gg/docs/api/members/ServerMemberDelete" target=_blank>https://www.guilded.gg/docs/api/members/ServerMemberDelete</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the member.
     */
    public void kickServerMember(String serverId, String userId)
    {
        execute(Method.DELETE, MEMBERS_URL.replace("{serverId}", serverId) + "/" + userId, null);
    }

    /**
     * Get a list of all members in the server.<br>
     * <a href="https://www.guilded.gg/docs/api/members/ServerMemberReadMany" target=_blank>https://www.guilded.gg/docs/api/members/ServerMemberReadMany</a>
     * @param serverId The ID of the server where the members are.
     * @return A list of ServerMemberSummary objects for each member in the server.
     */
    public ServerMemberSummary[] getServerMembers(String serverId)
    {
        JSONArray membersJson = execute(Method.GET, MEMBERS_URL.replace("{serverId}", serverId), null).getJSONArray("members");
        ServerMemberSummary[] members = new ServerMemberSummary[membersJson.size()];
        for(int i = 0; i < membersJson.size(); i++)
            members[i] = ServerMemberSummary.fromJSON(membersJson.getJSONObject(i));
        return members;
    }

    /**
     * Get a ban information of the member.<br>
     * <a href="https://www.guilded.gg/docs/api/member-bans/ServerMemberBanRead" target=_blank>https://www.guilded.gg/docs/api/member-bans/ServerMemberBanRead</a>
     * NOTE: If the member is not banned, a GuildedException will be thrown.
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the member.
     * @return A ServerMemberBan object of the banned member.
     */
    public ServerMemberBan getServerMemberBan(String serverId, String userId)
    {
        return ServerMemberBan.fromJSON(
                execute(Method.GET,
                        BANS_URL.replace("{serverId}", serverId) + "/" + userId,
                        null
                ).getJSONObject("serverMemberBan")
        );
    }

    /**
     * Ban a server member.<br>
     * <a href="https://www.guilded.gg/docs/api/member-bans/ServerMemberBanCreate" target=_blank>https://www.guilded.gg/docs/api/member-bans/ServerMemberBanCreate</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the user to ban from this server.
     * @param reason The reason for the ban.
     * @return A ServerMemberBan object of the banned member.
     */
    public ServerMemberBan banServerMember(String serverId, String userId, String reason)
    {
        return ServerMemberBan.fromJSON(
                execute(Method.POST,
                        BANS_URL.replace("{serverId}", serverId) + "/" + userId,
                        new JSONObject().set("reason", reason)
                ).getJSONObject("serverMemberBan")
        );
    }

    /**
     * Unban a server member.<br>
     * <a href="https://www.guilded.gg/docs/api/member-bans/ServerMemberBanDelete" target=_blank>https://www.guilded.gg/docs/api/member-bans/ServerMemberBanDelete</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the user to unban from this server.
     */
    public void unbanServerMember(String serverId, String userId)
    {
        execute(Method.DELETE, BANS_URL.replace("{serverId}", serverId) + "/" + userId, null);
    }

    /**
     * Get all ban information of a server.<br>
     * <a href="https://www.guilded.gg/docs/api/member-bans/ServerMemberBanReadMany" target=_blank>https://www.guilded.gg/docs/api/member-bans/ServerMemberBanReadMany</a>
     * @param serverId The ID of the server to get ban information of.
     * @return A list of ServerMemberBan objects of the banned members.
     */
    public ServerMemberBan[] getServerMemberBans(String serverId)
    {
        JSONArray bansJson = execute(Method.GET, BANS_URL.replace("{serverId}", serverId), null).getJSONArray("serverMemberBans");
        ServerMemberBan[] bans = new ServerMemberBan[bansJson.size()];
        for(int i = 0; i < bansJson.size(); i++) bans[i] = ServerMemberBan.fromJSON(bansJson.getJSONObject(i));
        return bans;
    }

    /**
     * Retrieves a member's public social links.<br>
     * <a href="https://www.guilded.gg/docs/api/socialLinks/MemberSocialLinkRead" target=_blank>https://www.guilded.gg/docs/api/socialLinks/MemberSocialLinkRead</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The target user's ID.
     * @param type The type of social link to retrieve (see {@link SocialMedia} for available types).
     * @return A HashMap with "type", "handle", "serviceId(nullable)" keys.
     */
    public HashMap<String, String> getSocialLink(String serverId, String userId, SocialMedia type)
    {
        JSONObject result = execute(Method.GET,
                SOCIAL_LINK_URL
                        .replace("{serverId}", serverId)
                        .replace("{userId}", userId)
                        .replace("{type}", type.toString().toLowerCase()),
                null
        ).getJSONObject("socialLink");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", result.getStr("type"));
        map.put("handle", result.getStr("handle"));
        map.put("serviceId", result.getStr("serviceId"));
        return map;
    }
}
