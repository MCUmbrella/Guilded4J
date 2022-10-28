/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.enums;

/**
 * The cause of a member being removed from a server.
 */
public enum MemberRemoveCause
{
    /**
     * The member was kicked.
     */
    KICK,
    /**
     * The member was banned.
     */
    BAN,
    /**
     * The member left the server by themselves.
     */
    LEAVE
}
