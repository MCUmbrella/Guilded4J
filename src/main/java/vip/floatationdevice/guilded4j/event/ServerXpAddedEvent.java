/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

/**
 * Event that is fired when XP is added to user(s).
 */
public class ServerXpAddedEvent extends GuildedEvent
{
    private final int xpAmount;
    private final String[] userIds;

    public ServerXpAddedEvent(Object source, String json)
    {
        super(source, json);
        JSONObject j = new JSONObject(json);
        JSONArray userIdsArray = (JSONArray) j.getByPath("d.userIds");
        String[] userIds = new String[userIdsArray.size()];
        for(int i = 0; i < userIdsArray.size(); i++)
            userIds[i] = userIdsArray.get(i).toString();
        this.userIds = userIds;
        this.xpAmount = (int) j.getByPath("d.xpAmount");
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
