/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

/**
 * Event that is fired when XP is added to user(s).
 */
public class TeamXpAddedEvent extends GuildedEvent
{
    private final int xpAmount;
    private final String[] userIds;

    /**
     * Default constructor.
     * @param xpAmount The amount of XP added.
     * @param userIds The IDs of the users that received XP.
     */
    public TeamXpAddedEvent(Object source, int xpAmount, String[] userIds)
    {
        super(source);
        this.xpAmount = xpAmount;
        this.userIds = userIds;
    }

    /**
     * Get the amount of XP added.
     * @return The amount of XP.
     */
    public int getXpAmount(){return this.xpAmount;}

    /**
     * Get the IDs of the users that received XP.
     * @return A String[] containing the IDs of the users.
     */
    public String[] getUserIds(){return this.userIds;}
}
