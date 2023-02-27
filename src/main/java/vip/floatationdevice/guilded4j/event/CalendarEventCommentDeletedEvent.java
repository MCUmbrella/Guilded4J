/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.CalendarEventComment;

/**
 * Event fired when a calendar event comment is deleted.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ForumTopicCommentReactionDeleted" target=_blank>https://www.guilded.gg/docs/api/websockets/ForumTopicCommentReactionDeleted</a>
 */
public class CalendarEventCommentDeletedEvent extends GuildedEvent
{
    private final CalendarEventComment calendarEventComment;

    public CalendarEventCommentDeletedEvent(Object source, String json)
    {
        super(source, json);
        calendarEventComment = CalendarEventComment.fromJSON((JSONObject) new JSONObject(json).getByPath("d.calendarEventComment"));
    }

    /**
     * Get the CalendarEventComment object of the event.
     */
    public CalendarEventComment getCalendarEventComment()
    {
        return calendarEventComment;
    }
}
