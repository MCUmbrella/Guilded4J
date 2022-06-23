/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import vip.floatationdevice.guilded4j.object.CalendarEvent;

/**
 * Manages the calendar events.
 */
public class CalendarEventManager extends RestManager
{
    public CalendarEventManager(String authToken)
    {
        super(authToken);
    }

    public CalendarEvent createCalendarEvent(String channelId, String name, String description, String location, String startsAt, String url, Integer color, Integer duration, Boolean isPrivate)
    {
        //TODO: implement
        return null;
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
