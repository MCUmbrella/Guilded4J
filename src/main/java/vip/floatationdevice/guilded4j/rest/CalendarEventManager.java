/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.misc.GObjectQuery;
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

    /**
     * Get last 25 calendar events in the channel.<br>
     * <a href="https://www.guilded.gg/docs/api/calendarEvents/CalendarEventReadMany" target=_blank>https://www.guilded.gg/docs/api/calendarEvents/CalendarEventReadMany</a>
     * @param channelId The ID of the channel that the calendar events will be retrieved from.
     * @return A list of calendar events.
     */
    public CalendarEvent[] getCalendarEvents(String channelId)
    {
        JSONArray eventsJson = execute(Method.GET, CALENDAR_CHANNEL_URL.replace("{channelId}", channelId), null).getJSONArray("calendarEvents");
        CalendarEvent[] events = new CalendarEvent[eventsJson.size()];
        for (int i = 0; i != eventsJson.size(); i++)
            events[i] = CalendarEvent.fromJSON(eventsJson.getJSONObject(i));
        return events;
    }

    /**
     * Get a list of calendar events using a query configuration.<br>
     * <a href="https://www.guilded.gg/docs/api/calendarEvents/CalendarEventReadMany" target=_blank>https://www.guilded.gg/docs/api/calendarEvents/CalendarEventReadMany</a>
     * @param channelId The ID of the channel that the calendar events will be retrieved from.
     * @param query The query configuration.
     * @return A list of calendar events.
     */
    public CalendarEvent[] getCalendarEvents(String channelId, GObjectQuery query)
    {
        JSONArray eventsJson = execute(Method.GET, CALENDAR_CHANNEL_URL.replace("{channelId}", channelId) + query.toString(), null).getJSONArray("calendarEvents");
        CalendarEvent[] events = new CalendarEvent[eventsJson.size()];
        for (int i = 0; i != eventsJson.size(); i++)
            events[i] = CalendarEvent.fromJSON(eventsJson.getJSONObject(i));
        return events;
    }

    /**
     * Get a calendar event by ID.<br>
     * <a href="https://www.guilded.gg/docs/api/calendarEvents/CalendarEventRead" target=_blank>https://www.guilded.gg/docs/api/calendarEvents/CalendarEventRead</a>
     * @param channelId The ID of the channel that the calendar event will be retrieved from.
     * @param calendarEventId The ID of the calendar event.
     * @return The calendar event.
     */
    public CalendarEvent getCalendarEvent(String channelId, int calendarEventId)
    {
        return CalendarEvent.fromJSON(
                execute(Method.GET, CALENDAR_CHANNEL_URL.replace("{channelId}", channelId) + "/" + calendarEventId, null)
                        .getJSONObject("calendarEvent")
        );
    }

    /**
     * Update the properties of a calendar event.<br>
     * <a href="https://www.guilded.gg/docs/api/calendarEvents/CalendarEventUpdate" target=_blank>https://www.guilded.gg/docs/api/calendarEvents/CalendarEventUpdate</a>
     * @param channelId The ID of the channel that the calendar event will be updated in.
     * @param calendarEventId The ID of the calendar event.
     * @param name The name of the event (min length 1, max length 60).
     * @param description The description of the event (min length 1, max length 8000).
     * @param location The location of the event (min length 1, max length 8000).
     * @param url A URL to associate with the event.
     * @param color The color of the event when viewing in the calendar (from 0x000000 or 0, to 0xffffff or 16777215).
     * @param duration The duration of the event in minutes (min 1).
     * @param isPrivate No description available in the API documentation.
     * @return The updated calendar event.
     */
    public CalendarEvent updateCalendarEvent(String channelId, int calendarEventId, String name, String description, String location, String url, Integer color, Integer duration, Boolean isPrivate)
    {
        return CalendarEvent.fromJSON(
                execute(Method.PATCH,
                        CALENDAR_CHANNEL_URL.replace("{channelId}", channelId) + "/" + calendarEventId,
                        new JSONObject()
                                .set("name", name)
                                .set("description", description)
                                .set("location", location)
                                .set("url", url)
                                .set("color", color)
                                .set("duration", duration)
                                .set("isPrivate", isPrivate)
                ).getJSONObject("calendarEvent")
        );
    }

    /**
     * Delete a calendar event.<br>
     * <a href="https://www.guilded.gg/docs/api/calendarEvents/CalendarEventDelete" target=_blank>https://www.guilded.gg/docs/api/calendarEvents/CalendarEventDelete</a>
     * @param channelId The ID of the channel that the calendar event will be deleted from.
     * @param calendarEventId The ID of the calendar event.
     */
    public void deleteCalendarEvent(String channelId, int calendarEventId)
    {
        execute(Method.DELETE, CALENDAR_CHANNEL_URL.replace("{channelId}", channelId) + "/" + calendarEventId, null);
    }
}
