/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;

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
     */
    public void addGroupMember(String groupId, String userId)
    {
        execute(Method.PUT,
                GROUP_URL.replace("{groupId}", groupId).replace("{userId}", userId),
                null
        );
    }

    /**
     * Remove member from group.<br>
     * <a href="https://www.guilded.gg/docs/api/groupMembership/GroupMembershipDelete" target=_blank>https://www.guilded.gg/docs/api/groupMembership/GroupMembershipDelete</a>
     * @param groupId Group ID to remove the member from.
     * @param userId Member ID to remove from the group.
     */
    public void removeGroupMember(String groupId, String userId)
    {
        execute(Method.DELETE,
                GROUP_URL.replace("{groupId}", groupId).replace("{userId}", userId),
                null
        );
    }
}
