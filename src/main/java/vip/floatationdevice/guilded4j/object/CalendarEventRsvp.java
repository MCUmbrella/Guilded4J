/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * Represents a RSVP to a calendar event.<br>
 * <a href="https://www.guilded.gg/docs/api/calendarEvents/CalendarEventRsvp" target=_blank>https://www.guilded.gg/docs/api/calendarEvents/CalendarEventRsvp</a>
 */
public class CalendarEventRsvp
{
    int calendarEventId = 0;
    String channelId, serverId, userId, status, createdAt, updatedAt, createdBy, updatedBy;

    /**
     * Get the ID of the calendar event.
     */
    public int getCalendarEventId(){return calendarEventId;}

    /**
     * Get the ID of the channel that the calendar event belongs to.
     */
    public String getChannelId(){return channelId;}

    /**
     * Get the ID of the server that the calendar event belongs to.
     */
    public String getServerId(){return serverId;}

    /**
     * Get the ID of the user that RSVP'd to the calendar event.
     */
    public String getUserId(){return userId;}

    /**
     * Get the status of the RSVP.
     * @return "going", "maybe", "declined", "invited", "waitlisted", or "not responded".
     */
    public String getStatus(){return status;}

    /**
     * Get the ISO 8601 timestamp that the RSVP was created.
     */
    public String getCreatedAt(){return createdAt;}

    /**
     * Get the ISO 8601 timestamp that the RSVP was last updated.
     */
    public String getUpdatedAt(){return updatedAt;}

    /**
     * Get the ID of the user that created the RSVP.
     */
    public String getCreatedBy(){return createdBy;}

    /**
     * Get the ID of the user that last updated the RSVP.
     */
    public String getUpdatedBy(){return updatedBy;}

    public CalendarEventRsvp setCalendarEventId(int calendarEventId){this.calendarEventId = calendarEventId; return this;}
    public CalendarEventRsvp setChannelId(String channelId){this.channelId = channelId; return this;}
    public CalendarEventRsvp setServerId(String serverId){this.serverId = serverId; return this;}
    public CalendarEventRsvp setUserId(String userId){this.userId = userId; return this;}
    public CalendarEventRsvp setStatus(String status){this.status = status; return this;}
    public CalendarEventRsvp setCreatedAt(String createdAt){this.createdAt = createdAt; return this;}
    public CalendarEventRsvp setUpdatedAt(String updatedAt){this.updatedAt = updatedAt; return this;}
    public CalendarEventRsvp setCreatedBy(String createdBy){this.createdBy = createdBy; return this;}
    public CalendarEventRsvp setUpdatedBy(String updatedBy){this.updatedBy = updatedBy; return this;}

    /**
     * Use the given JSON object to generate CalendarEventRsvp object.
     * @param json The JSON object.
     * @return The CalendarEventRsvp object.
     * @throws IllegalArgumentException when the essential fields are not set.
     */
    public static CalendarEventRsvp fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getInt("calendarEventId"),
                json.getStr("serverId"),
                json.getStr("channelId"),
                json.getStr("userId"),
                json.getStr("status"),
                json.getStr("createdBy"),
                json.getStr("createdAt")
        );
        return new CalendarEventRsvp()
                .setCalendarEventId(json.getInt("calendarEventId"))
                .setChannelId(json.getStr("channelId"))
                .setServerId(json.getStr("serverId"))
                .setUserId(json.getStr("userId"))
                .setStatus(json.getStr("status"))
                .setCreatedAt(json.getStr("createdAt"))
                .setCreatedBy(json.getStr("createdBy"))
                .setUpdatedAt(json.getStr("updatedAt"))
                .setUpdatedBy(json.getStr("updatedBy"));
    }

    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("calendarEventId", calendarEventId)
                .set("channelId", channelId)
                .set("serverId", serverId)
                .set("userId", userId)
                .set("status", status)
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("updatedAt", updatedAt)
                .set("updatedBy", updatedBy)
                .toString();
    }
}
