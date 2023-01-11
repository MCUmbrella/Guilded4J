/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;

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
     * <a href="https://www.guilded.gg/docs/api/server-xp/ServerXpForUserCreate" target=_blank>https://www.guilded.gg/docs/api/server-xp/ServerXpForUserCreate</a>
     * @param serverId The ID of the server where the member is.
     * @param userId Member ID to award XP to.
     * @param amount The amount of XP to award.
     * @return The total XP after this operation.
     */
    public int awardUserXp(String serverId, String userId, int amount)
    {
        return execute(Method.POST,
                USER_XP_URL.replace("{serverId}", serverId).replace("{userId}", userId),
                new JSONObject().set("amount", amount)
        ).getInt("total");
    }

    /**
     * Award XP to all members with a particular role.<br>
     * <a href="https://www.guilded.gg/docs/api/server-xp/ServerXpForRoleCreate" target=_blank>https://www.guilded.gg/docs/api/server-xp/ServerXpForRoleCreate</a>
     * @param serverId The ID of the server where the member is.
     * @param roleId Role ID to award XP to.
     * @param amount The amount of XP to award.
     */
    public void awardRoleXp(String serverId, int roleId, int amount)
    {
        execute(Method.POST,
                ROLE_XP_URL.replace("{serverId}", serverId).replace("{roleId}", String.valueOf(roleId)),
                new JSONObject().set("amount", amount)
        );
    }

    /**
     * Set the XP of a server member.<br>
     * <a href="https://www.guilded.gg/docs/api/server-xp/ServerXpForUserUpdate" target=_blank>https://www.guilded.gg/docs/api/server-xp/ServerXpForUserUpdate</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the member to set the XP.
     * @param total The total XP to set on the user (min -1000000000; max 1000000000).
     * @return The total XP after this operation.
     */
    public int setUserXp(String serverId, String userId, int total)
    {
        return execute(Method.PUT,
                USER_XP_URL.replace("{serverId}", serverId).replace("{userId}", userId),
                new JSONObject().set("total", total)
        ).getInt("total");
    }
}
