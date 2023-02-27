/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

public class CalendarEventComment
{
    private int id, calendarEventId;
    private String channelId, content, createdAt, updatedAt, createdBy;

    public int getId()
    {
        return id;
    }

    public int getCalendarEventId()
    {
        return calendarEventId;
    }

    public String getChannelId()
    {
        return channelId;
    }

    public String getContent()
    {
        return content;
    }

    public String getCreatedAt()
    {
        return createdAt;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

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
