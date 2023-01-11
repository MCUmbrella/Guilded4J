/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.CalendarEventRsvp;

/**
 * Event fired when a calendar event RSVP is created or updated.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/CalendarEventRsvpUpdated" target=_blank>https://www.guilded.gg/docs/api/websockets/CalendarEventRsvpUpdated</a>
 */
public class CalendarEventRsvpUpdatedEvent extends GuildedEvent
{
    private final CalendarEventRsvp calendarEventRsvp;

    public CalendarEventRsvpUpdatedEvent(Object source, String json)
    {
        super(source, json);
        this.calendarEventRsvp = CalendarEventRsvp.fromJSON((JSONObject) new JSONObject(json).getByPath("d.calendarEventRsvp"));
    }

    /**
     * Get the CalendarEventRsvp object of the event.
     */
    public CalendarEventRsvp getCalendarEventRsvp(){return calendarEventRsvp;}
}
