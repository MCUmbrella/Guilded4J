/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.CalendarEventRsvp;

/**
 * Event fired when some people was bulk-invited to a calendar event.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/CalendarEventRsvpManyUpdated" target=_blank>https://www.guilded.gg/docs/api/websockets/CalendarEventRsvpManyUpdated</a>
 */
public class CalendarEventRsvpManyUpdatedEvent extends GuildedEvent
{
    private final CalendarEventRsvp[] calendarEventRsvps;

    public CalendarEventRsvpManyUpdatedEvent(Object source, String json)
    {
        super(source, json);
        JSONArray jsonArray = (JSONArray) new JSONObject(json).getByPath("d.calendarEventRsvps");
        this.calendarEventRsvps = new CalendarEventRsvp[jsonArray.size()];
        for(int i = 0; i < jsonArray.size(); i++)
            this.calendarEventRsvps[i] = CalendarEventRsvp.fromJSON((JSONObject) jsonArray.get(i));
    }

    /**
     * Get the CalendarEventRsvp objects of the event.
     */
    public CalendarEventRsvp[] getCalendarEventRsvps(){return calendarEventRsvps;}
}
