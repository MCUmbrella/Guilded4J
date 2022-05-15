/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.exception.GuildedException;

import static vip.floatationdevice.guilded4j.G4JClient.ROLE_XP_URL;
import static vip.floatationdevice.guilded4j.G4JClient.USER_XP_URL;

/**
 * Manages the XP of users or groups.
 */
public class XPManager extends RestManager
{
    public XPManager(String authToken)
    {
        super(authToken);
    }

    /**
     * Award XP to a member.<br>
     * <a href="https://www.guilded.gg/docs/api/teamXP/TeamXpForUserCreate" target=_blank>https://www.guilded.gg/docs/api/teamXP/TeamXpForUserCreate</a>
     * @param serverId The ID of the server where the member is.
     * @param userId Member ID to award XP to.
     * @param amount The amount of XP to award.
     * @return The total XP after this operation.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public int awardUserXp(String serverId, String userId, int amount)
    {
        JSONObject result = new JSONObject(HttpRequest.post(USER_XP_URL.replace("{serverId}", serverId).replace("{userId}", userId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body("{\"amount\":" + amount + "}").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return result.getInt("total");
    }

    /**
     * Award XP to all members with a particular role.<br>
     * <a href="https://www.guilded.gg/docs/api/teamXP/TeamXpForRoleCreate" target=_blank>https://www.guilded.gg/docs/api/teamXP/TeamXpForRoleCreate</a>
     * @param serverId The ID of the server where the member is.
     * @param roleId Role ID to award XP to.
     * @param amount The amount of XP to award.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void awardRoleXp(String serverId, int roleId, int amount)
    {
        String result = HttpRequest.post(ROLE_XP_URL.replace("{serverId}", serverId).replace("{roleId}", String.valueOf(roleId))).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body("{\"amount\":" + amount + "}").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("TeamXpForRoleCreate returned an unexpected JSON string");
        }
    }
}
