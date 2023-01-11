/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.CalendarEventRsvp;

/**
 * Event fired when a calendar event RSVP is deleted.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/CalendarEventRsvpDeleted" target=_blank>https://www.guilded.gg/docs/api/websockets/CalendarEventRsvpDeleted</a>
 */
public class CalendarEventRsvpDeletedEvent extends GuildedEvent
{
    private final CalendarEventRsvp calendarEventRsvp;

    public CalendarEventRsvpDeletedEvent(Object source, String json)
    {
        super(source, json);
        this.calendarEventRsvp = CalendarEventRsvp.fromJSON((JSONObject) new JSONObject(json).getByPath("d.calendarEventRsvp"));
    }

    /**
     * Get the CalendarEventRsvp object of the event.
     */
    public CalendarEventRsvp getCalendarEventRsvp(){return calendarEventRsvp;}
}
