/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.misc.GObjectQuery;
import vip.floatationdevice.guilded4j.object.CalendarEvent;
import vip.floatationdevice.guilded4j.object.CalendarEventRsvp;

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
     * @param rsvpLimit The number of RSVPs to allow before waitlisting RSVPs (min 1, null if you don't want to limit).
     * @return The created calendar event.
     */
    public CalendarEvent createCalendarEvent(String channelId, String name, String description, String location, String startsAt, String url, Integer color, Integer duration, Boolean isPrivate, Integer rsvpLimit)
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
                                .set("rsvpLimit", rsvpLimit)
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
        JSONArray eventsJson = execute(Method.GET,
                CALENDAR_CHANNEL_URL.replace("{channelId}", channelId),
                null
        ).getJSONArray("calendarEvents");
        CalendarEvent[] events = new CalendarEvent[eventsJson.size()];
        for(int i = 0; i != eventsJson.size(); i++)
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
        JSONArray eventsJson = execute(
                Method.GET,
                CALENDAR_CHANNEL_URL.replace("{channelId}", channelId) + query.toString(),
                null
        ).getJSONArray("calendarEvents");
        CalendarEvent[] events = new CalendarEvent[eventsJson.size()];
        for(int i = 0; i != eventsJson.size(); i++)
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
                execute(Method.GET,
                        CALENDAR_CHANNEL_URL.replace("{channelId}", channelId) + "/" + calendarEventId,
                        null
                ).getJSONObject("calendarEvent")
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
        execute(Method.DELETE,
                CALENDAR_CHANNEL_URL.replace("{channelId}", channelId) + "/" + calendarEventId,
                null
        );
    }

    /**
     * Get a calendar event RSVP.<br>
     * <a href="https://www.guilded.gg/docs/api/calendarEvents/CalendarEventRsvpRead" target=_blank>https://www.guilded.gg/docs/api/calendarEvents/CalendarEventRsvpRead</a>
     * @param channelId The ID of the channel that the calendar event will be retrieved from.
     * @param calendarEventId The ID of the calendar event.
     * @param userId The ID of the user that the RSVP will be retrieved from.
     * @return The calendar event RSVP.
     */
    public CalendarEventRsvp getCalendarEventRsvp(String channelId, int calendarEventId, String userId)
    {
        return CalendarEventRsvp.fromJSON(
                execute(
                        Method.GET,
                        CALENDAR_RSVP_URL.replace("{channelId}", channelId).replace("{calendarEventId}", String.valueOf(calendarEventId)) + "/" + userId,
                        null
                ).getJSONObject("calendarEventRsvp")
        );
    }

    /**
     * Create or update a calendar event RSVP.<br>
     * <a href="https://www.guilded.gg/docs/api/calendarEvents/CalendarEventRsvpUpdate" target=_blank>https://www.guilded.gg/docs/api/calendarEvents/CalendarEventRsvpUpdate</a>
     * @param channelId The ID of the channel that the calendar event will be updated in.
     * @param calendarEventId The ID of the calendar event.
     * @param userId The ID of the user that the RSVP will be updated for.
     * @param status The status of the RSSVP.
     */
    public CalendarEventRsvp updateCalendarEventRsvp(String channelId, int calendarEventId, String userId, String status)
    {
        return CalendarEventRsvp.fromJSON(
                execute(
                        Method.PUT,
                        CALENDAR_RSVP_URL.replace("{channelId}", channelId).replace("{calendarEventId}", String.valueOf(calendarEventId)) + "/" + userId,
                        new JSONObject().set("status", status)
                ).getJSONObject("calendarEventRsvp")
        );
    }

    /**
     * Delete a calendar event RSVP.<br>
     * <a href="https://www.guilded.gg/docs/api/calendarEvents/CalendarEventRsvpDelete" target=_blank>https://www.guilded.gg/docs/api/calendarEvents/CalendarEventRsvpDelete</a>
     * @param channelId The ID of the channel that the calendar event will be deleted from.
     * @param calendarEventId The ID of the calendar event.
     * @param userId The ID of the user that the RSVP will be deleted for.
     */
    public void deleteCalendarEventRsvp(String channelId, int calendarEventId, String userId)
    {
        execute(Method.DELETE,
                CALENDAR_RSVP_URL.replace("{channelId}", channelId).replace("{calendarEventId}", String.valueOf(calendarEventId)) + "/" + userId,
                null
        );
    }

    /**
     * Get a list of calendar event RSVPs.<br>
     * <a href="https://www.guilded.gg/docs/api/calendarEvents/CalendarEventRsvpReadMany" target=_blank>https://www.guilded.gg/docs/api/calendarEvents/CalendarEventRsvpReadMany</a>
     * @param channelId The ID of the channel that the calendar event will be retrieved from.
     * @param calendarEventId The ID of the calendar event.
     * @return The array of calendar event RSVPs.
     */
    public CalendarEventRsvp[] getCalendarEventRsvps(String channelId, int calendarEventId)
    {
        JSONArray jsonArray = execute(
                Method.GET,
                CALENDAR_RSVP_URL.replace("{channelId}", channelId).replace("{calendarEventId}", String.valueOf(calendarEventId)),
                null
        ).getJSONArray("calendarEventRsvps");
        CalendarEventRsvp[] calendarEventRsvps = new CalendarEventRsvp[jsonArray.size()];
        for(int i = 0; i < jsonArray.size(); i++)
            calendarEventRsvps[i] = CalendarEventRsvp.fromJSON(jsonArray.getJSONObject(i));
        return calendarEventRsvps;
    }

    public void addReaction(String channelId, int calendarEventId, int emoteId) //TODO
    {
    }

    public void removeReaction(String channelId, int calendarEventId, int emoteId) //TODO
    {
    }

    public void addReaction(String channelId, int calendarEventId, int calendarEventCommentId, int emoteId) //TODO
    {
    }

    public void removeReaction(String channelId, int calendarEventId, int calendarEventCommentId,int emoteId) //TODO
    {
    }
}
