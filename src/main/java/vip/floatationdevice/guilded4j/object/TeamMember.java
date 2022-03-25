/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

/**
 * Represents a member of a server.
 * <a href="https://www.guilded.gg/docs/api/members/TeamMember" target=_blank>https://www.guilded.gg/docs/api/members/TeamMember</a>
 */
public class TeamMember //TODO: implement
{
    User user;
    int[] roleIds;
    String nickname;
    String joinedAt;
}
