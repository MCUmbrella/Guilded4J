/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * The comment of a calendar event.<br>
 * <a href="https://www.guilded.gg/docs/api/calendarEventComments/CalendarEventComment" targer=_blank>https://www.guilded.gg/docs/api/calendarEventComments/CalendarEventComment</a>
 */
public class CalendarEventComment
{
    private int id, calendarEventId;
    private String channelId, content, createdAt, updatedAt, createdBy;

    /**
     * Get the ID of the calendar event comment.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Get the ID of the calendar event that the comment belongs to.
     */
    public int getCalendarEventId()
    {
        return calendarEventId;
    }

    /**
     * Get the UUID of the channel that the calendar event belongs to.
     */
    public String getChannelId()
    {
        return channelId;
    }

    /**
     * Get the content of the comment.
     */
    public String getContent()
    {
        return content;
    }

    /**
     * Get the ISO 8601 timestamp that the calendar event comment was created at.
     */
    public String getCreatedAt()
    {
        return createdAt;
    }

    /**
     * Get the ID of the user who created this calendar event comment.
     */
    public String getCreatedBy()
    {
        return createdBy;
    }

    /**
     * Get the ISO 8601 timestamp that the calendar event comment was updated at.
     * If the comment hasn't benn updated, return null.
     */
    public String getUpdatedAt()
    {
        return updatedAt;
    }

    public CalendarEventComment setId(int id)
    {
        this.id = id;
        return this;
    }

    public CalendarEventComment setCalendarEventId(int calendarEventId)
    {
        this.calendarEventId = calendarEventId;
        return this;
    }

    public CalendarEventComment setChannelId(String channelId)
    {
        this.channelId = channelId;
        return this;
    }

    public CalendarEventComment setContent(String content)
    {
        this.content = content;
        return this;
    }


    public CalendarEventComment setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    public CalendarEventComment setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    public CalendarEventComment setUpdatedAt(String updatedAt)
    {
        this.updatedAt = updatedAt;
        return this;
    }

    public static CalendarEventComment fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getInt("id"),
                json.getInt("calendarEventId"),
                json.getStr("channelId"),
                json.getStr("content"),
                json.getStr("createdAt"),
                json.getStr("createdBy")
        );
        return new CalendarEventComment()
                .setId(json.getInt("id"))
                .setCalendarEventId(json.getInt("calendarEventId"))
                .setChannelId(json.getStr("channelId"))
                .setContent(json.getStr("content"))
                .setCreatedAt(json.getStr("createdAt"))
                .setCreatedBy(json.getStr("createdBy"))
                .setUpdatedAt(json.getStr("updatedAt"));
    }

    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("calendarEventId", calendarEventId)
                .set("channelId", channelId)
                .set("content", content)
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("updatedAt", updatedAt)
                .toString();
    }
}
