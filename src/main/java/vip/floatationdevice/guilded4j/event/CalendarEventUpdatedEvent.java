/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.CalendarEvent;

/**
 * Event fired when a calendar event is updated.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/CalendarEventUpdated" target=_blank>https://www.guilded.gg/docs/api/websockets/CalendarEventUpdated</a>
 */
public class CalendarEventUpdatedEvent extends GuildedEvent
{
    private final CalendarEvent calendarEvent;

    public CalendarEventUpdatedEvent(Object source, String json)
    {
        super(source, json);
        this.calendarEvent = CalendarEvent.fromJSON((JSONObject) new JSONObject(json).getByPath("d.calendarEvent"));
    }

    /**
     * Get the CalendarEvent object of the event.
     * @return A CalendarEvent object.
     */
    public CalendarEvent getCalendarEvent(){return calendarEvent;}
}
