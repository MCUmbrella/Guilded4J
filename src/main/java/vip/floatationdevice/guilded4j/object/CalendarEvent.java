/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;
import vip.floatationdevice.guilded4j.object.misc.CalendarEventCancellation;

/**
 * The class that represents a calendar event.<br>
 * <a href="https://www.guilded.gg/docs/api/calendarEvents/CalendarEvent" target=_blank>https://www.guilded.gg/docs/api/calendarEvents/CalendarEvent</a>
 */
public class CalendarEvent
{
    private int id;
    private String serverId, channelId, name, description, location, url, startsAt, createdAt, createdBy;
    private Integer color, duration;
    private Boolean isPrivate;
    private Mention[] mentions;
    private CalendarEventCancellation cancellation;

    /**
     * Get the ID of the calendar event.
     */
    public int getId(){return id;}

    /**
     * Get the ID of the server that the calendar event belongs to.
     */
    public String getServerId(){return serverId;}

    /**
     * Get the ID of the channel that the calendar event belongs to.
     */
    public String getChannelId(){return channelId;}

    /**
     * Get the name of the event.
     */
    public String getName(){return name;}

    /**
     * Get the description of the event.
     */
    public String getDescription(){return description;}

    /**
     * Get the location of the event.
     */
    public String getLocation(){return location;}

    /**
     * Get the URL associated with the event.
     */
    public String getUrl(){return url;}

    /**
     * color of the event when viewing in the calendar.
     */
    public Integer getColor(){return color;}

    /**
     * Get the ISO 8601 timestamp that the event starts at.
     */
    public String getStartsAt(){return startsAt;}

    /**
     * duration of the event in minutes.
     */
    public Integer getDuration(){return duration;}

    /**
     * No description available in API documentation.
     */
    public Boolean getIsPrivate(){return isPrivate;}

    /**
     * No description available in API documentation.
     * Also, mentions will not be implemented in Guilded4J - yet.
     */
    public Mention[] getMentions(){throw new UnsupportedOperationException("https://www.guilded.gg/Guilded4J-Cafe/blog/Announcements/About-the-APIs-new-Mentions-feature");}

    /**
     * Get the ISO 8601 timestamp that the event was created at.
     */
    public String getCreatedAt(){return createdAt;}

    /**
     * Get the ID of the user who created this event.
     */
    public String getCreatedBy(){return createdBy;}

    /**
     * No description available in API documentation.
     */
    public CalendarEventCancellation getCancellation(){return cancellation;}

    public CalendarEvent setId(int id)
    {
        this.id = id;
        return this;
    }

    public CalendarEvent setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    public CalendarEvent setChannelId(String channelId)
    {
        this.channelId = channelId;
        return this;
    }

    public CalendarEvent setName(String name)
    {
        this.name = name;
        return this;
    }

    public CalendarEvent setDescription(String description)
    {
        this.description = description;
        return this;
    }

    public CalendarEvent setLocation(String location)
    {
        this.location = location;
        return this;
    }

    public CalendarEvent setUrl(String url)
    {
        this.url = url;
        return this;
    }

    public CalendarEvent setColor(Integer color)
    {
        this.color = color;
        return this;
    }

    public CalendarEvent setStartsAt(String startsAt)
    {
        this.startsAt = startsAt;
        return this;
    }

    public CalendarEvent setDuration(Integer duration)
    {
        this.duration = duration;
        return this;
    }

    public CalendarEvent setIsPrivate(Boolean isPrivate)
    {
        this.isPrivate = isPrivate;
        return this;
    }

    public CalendarEvent setMentions(Mention[] mentions)
    {
        this.mentions = mentions;
        return this;
    }

    public CalendarEvent setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    public CalendarEvent setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    public CalendarEvent setCancellation(CalendarEventCancellation cancellation)
    {
        this.cancellation = cancellation;
        return this;
    }

    /**
     * Use the given JSON object to generate CalendarEvent object.
     * @param json The JSON object.
     * @return CalendarEvent object.
     * @throws IllegalArgumentException when the essential fields are not set.
     */
    public static CalendarEvent fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getInt("id"),
                json.getStr("serverId"),
                json.getStr("channelId"),
                json.getStr("name"),
                json.getStr("startsAt"),
                json.getStr("createdAt"),
                json.getStr("createdBy")
        );
        return new CalendarEvent()
                .setId(json.getInt("id"))
                .setServerId(json.getStr("serverId"))
                .setChannelId(json.getStr("channelId"))
                .setName(json.getStr("name"))
                .setDescription(json.getStr("description"))
                .setLocation(json.getStr("location"))
                .setUrl(json.getStr("url"))
                .setColor(json.getInt("color"))
                .setStartsAt(json.getStr("startsAt"))
                .setDuration(json.getInt("duration"))
                .setIsPrivate(json.getBool("isPrivate"))
                .setMentions(null)
                .setCreatedAt(json.getStr("createdAt"))
                .setCreatedBy(json.getStr("createdBy"))
                .setCancellation(json.getJSONObject("cancellation") != null ? CalendarEventCancellation.fromJSON(json.getJSONObject("cancellation")) : null);
    }

    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("serverId", serverId)
                .set("channelId", channelId)
                .set("name", name)
                .set("description", description)
                .set("location", location)
                .set("url", url)
                .set("color", color)
                .set("startsAt", startsAt)
                .set("duration", duration)
                .set("isPrivate", isPrivate)
                .set("mentions", mentions)
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("cancellation", cancellation)
                .toString();
    }
}
