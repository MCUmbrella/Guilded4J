/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.CalendarEventComment;

public class CalendarEventCommentCreatedEvent extends GuildedEvent
{
    private final CalendarEventComment calendarEventComment;
    public CalendarEventCommentCreatedEvent(Object source, String json)
    {
        super(source, json);
        calendarEventComment = CalendarEventComment.fromJSON((JSONObject) new JSONObject(json).getByPath("d.calendarEventComment"));
    }

    public CalendarEventComment getCalendarEventComment()
    {
        return calendarEventComment;
    }
}
