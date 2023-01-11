/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object.misc;

import cn.hutool.json.JSONObject;

/**
 * The class that represents a calendar event cancellation.
 * This is not part of the official Guilded API.
 */
public class CalendarEventCancellation
{
    private String description, createdBy;

    public CalendarEventCancellation(String description, String createdBy)
    {
        this.description = description;
        this.createdBy = createdBy;
    }

    public static CalendarEventCancellation fromJSON(JSONObject json)
    {
        return new CalendarEventCancellation(json.getStr("description"), json.getStr("createdBy"));
    }

    /**
     * Get the description of event cancellation.
     */
    public String getDescription(){return description;}

    /**
     * Get the ID of the user who created this event cancellation.
     */
    public String getCreatedBy(){return createdBy;}

    @Override
    public String toString()
    {
        return new JSONObject()
                .set("description", description)
                .set("createdBy", createdBy)
                .toString();
    }
}
