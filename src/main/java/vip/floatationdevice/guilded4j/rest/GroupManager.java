/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.exception.GuildedException;

import static vip.floatationdevice.guilded4j.G4JClient.GROUP_URL;

/**
 * Manages groups of a server.
 */
public class GroupManager extends RestManager
{
    public GroupManager(String authToken)
    {
        super(authToken);
    }

    /**
     * Add member to group.<br>
     * <a href="https://www.guilded.gg/docs/api/groupMembership/GroupMembershipCreate" target=_blank>https://www.guilded.gg/docs/api/groupMembership/GroupMembershipCreate</a>
     * @param groupId Group ID to add the member to.
     * @param userId Member ID to add to the group.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void addGroupMember(String groupId, String userId)
    {
        String result = HttpRequest.put(GROUP_URL.replace("{groupId}", groupId).replace("{userId}", userId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("GroupMembershipCreate returned an unexpected JSON string");
        }
    }

    /**
     * Remove member from group.<br>
     * <a href="https://www.guilded.gg/docs/api/groupMembership/GroupMembershipDelete" target=_blank>https://www.guilded.gg/docs/api/groupMembership/GroupMembershipDelete</a>
     * @param groupId Group ID to remove the member from.
     * @param userId Member ID to remove from the group.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void removeGroupMember(String groupId, String userId)
    {
        String result = HttpRequest.delete(GROUP_URL.replace("{groupId}", groupId).replace("{userId}", userId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("GroupMembershipDelete returned an unexpected JSON string");
        }
    }
}
