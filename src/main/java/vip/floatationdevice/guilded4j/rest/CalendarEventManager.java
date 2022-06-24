/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.CalendarEvent;

import static vip.floatationdevice.guilded4j.G4JClient.CALENDAR_CHANNEL_URL;

/**
 * Manages the calendar events.
 */
public class CalendarEventManager extends RestManager
{
    public CalendarEventManager(String authToken)
    {
        super(authToken);
    }

    /**
     * Create a new calendar event.<br>
     * <a href="https://www.guilded.gg/docs/api/calendarEvents/CalendarEventCreate" target=_blank>https://www.guilded.gg/docs/api/calendarEvents/CalendarEventCreate</a>
     * @param channelId The ID of the channel that the calendar event will be created in.
     * @param name The name of the event (min length 1, max length 60).
     * @param description The description of the event (min length 1, max length 8000, null if no description).
     * @param location The location of the event (min length 1, max length 8000, null if no location).
     * @param startsAt The ISO 8601 timestamp that the event starts at.
     * @param url A URL to associate with the event (optional).
     * @param color The color of the event when viewing in the calendar (from 0x000000 or 0, to 0xffffff or 16777215).
     * @param duration The duration of the event in minutes (min 1).
     * @param isPrivate No description available in the API documentation.
     * @return The created calendar event.
     */
    public CalendarEvent createCalendarEvent(String channelId, String name, String description, String location, String startsAt, String url, Integer color, Integer duration, Boolean isPrivate)
    {
        return CalendarEvent.fromJSON(
                execute(Method.POST,
                        CALENDAR_CHANNEL_URL.replace("{channelId}", channelId),
                        new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                                .set("name", name)
                                .set("description", description)
                                .set("location", location)
                                .set("startsAt", startsAt)
                                .set("url", url)
                                .set("color", color)
                                .set("duration", duration)
                                .set("isPrivate", isPrivate)
                ).getJSONObject("calendarEvent")
        );
    }

    public CalendarEvent[] getCalendarEvents(String channelId)
    {
        //TODO: implement
        return null;
    }

    public CalendarEvent getCalendarEvent(String channelId, int calendarEventId)
    {
        //TODO: implement
        return null;
    }

    public CalendarEvent updateCalendarEvent(String channelId, int calendarEventId, String name, String description, String location, String url, Integer color, Integer duration, Boolean isPrivate)
    {
        //TODO: implement
        return null;
    }

    public void deleteCalendarEvent(String channelId, int calendarEventId)
    {
        //TODO: implement
    }
}
