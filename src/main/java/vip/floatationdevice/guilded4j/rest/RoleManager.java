/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import vip.floatationdevice.guilded4j.exception.GuildedException;

import static vip.floatationdevice.guilded4j.G4JClient.ROLES_URL;

/**
 * Manages the roles of a server.
 */
public class RoleManager extends RestManager
{
    public RoleManager(String authToken)
    {
        super(authToken);
    }

    /**
     * Assign role to member.<br>
     * <a href="https://www.guilded.gg/docs/api/roleMembership/RoleMembershipCreate" target=_blank>https://www.guilded.gg/docs/api/roleMembership/RoleMembershipCreate</a>
     * @param serverId The ID of the server where the member is.
     * @param userId The ID of the member that the role should be assigned to.
     * @param roleId The role ID to apply to the user.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void addRoleMember(String serverId, int roleId, String userId)
    {
        execute(Method.PUT, ROLES_URL.replace("{serverId}", serverId).replace("{userId}", userId) + "/" + roleId, null);
    }

    /**
     * Remove role from member.<br>
     * <a href="https://www.guilded.gg/docs/api/roleMembership/RoleMembershipDelete" target=_blank>https://www.guilded.gg/docs/api/roleMembership/RoleMembershipDelete</a>
     * @param userId The ID of the member that the role should be removed from.
     * @param roleId The role ID to remove from the user.
     */
    public void removeRoleMember(String serverId, int roleId, String userId)
    {
        execute(Method.DELETE, ROLES_URL.replace("{serverId}", serverId).replace("{userId}", userId) + "/" + roleId, null);
    }

    /**
     * Get a list of the roles assigned to a member.<br>
     * <a href="https://www.guilded.gg/docs/api/members/RoleMembershipReadMany" target=_blank>https://www.guilded.gg/docs/api/members/RoleMembershipReadMany</a>
     * @param userId The ID of the member to obtain roles from.
     * @param serverId The ID of the server where the member is.
     * @return An int[] contains the IDs of the roles that the member currently has.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public int[] getMemberRoles(String serverId, String userId)
    {
        JSONArray rolesJson = execute(Method.GET, ROLES_URL.replace("{serverId}", serverId).replace("{userId}", userId), null).getJSONArray("roleIds");
        int[] roles = new int[rolesJson.size()];
        for(int i = 0; i != rolesJson.size(); i++) roles[i] = ((int) rolesJson.get(i));
        return roles;
    }
}
