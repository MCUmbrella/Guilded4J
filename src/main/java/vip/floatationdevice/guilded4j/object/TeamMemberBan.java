/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

/**
 * Represents a ban on a member.
 * <a href="https://www.guilded.gg/docs/api/members/TeamMemberBan" target=_blank>https://www.guilded.gg/docs/api/members/TeamMemberBan</a>
 */
public class TeamMemberBan //TODO: implement
{
    UserSummary user;
    String reason;
    String createdBy;
    String createdAt;
}
