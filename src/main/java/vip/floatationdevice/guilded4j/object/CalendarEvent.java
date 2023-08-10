/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
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
    private Boolean isPrivate, repeats;
    private Mentions mentions;
    private CalendarEventCancellation cancellation;

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
                .setRepeats(json.getBool("repeats"))
                .setStartsAt(json.getStr("startsAt"))
                .setDuration(json.getInt("duration"))
                .setIsPrivate(json.getBool("isPrivate"))
                .setMentions(null)
                .setCreatedAt(json.getStr("createdAt"))
                .setCreatedBy(json.getStr("createdBy"))
                .setCancellation(json.getJSONObject("cancellation") != null ? CalendarEventCancellation.fromJSON(json.getJSONObject("cancellation")) : null);
    }

    /**
     * Get the ID of the calendar event.
     */
    public int getId(){return id;}

    public CalendarEvent setId(int id)
    {
        this.id = id;
        return this;
    }

    /**
     * Get the ID of the server that the calendar event belongs to.
     */
    public String getServerId(){return serverId;}

    public CalendarEvent setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    /**
     * Get the ID of the channel that the calendar event belongs to.
     */
    public String getChannelId(){return channelId;}

    public CalendarEvent setChannelId(String channelId)
    {
        this.channelId = channelId;
        return this;
    }

    /**
     * Get the name of the event.
     */
    public String getName(){return name;}

    public CalendarEvent setName(String name)
    {
        this.name = name;
        return this;
    }

    /**
     * Get the description of the event.
     */
    public String getDescription(){return description;}

    public CalendarEvent setDescription(String description)
    {
        this.description = description;
        return this;
    }

    /**
     * Get the location of the event.
     */
    public String getLocation(){return location;}

    public CalendarEvent setLocation(String location)
    {
        this.location = location;
        return this;
    }

    /**
     * Get the URL associated with the event.
     */
    public String getUrl(){return url;}

    public CalendarEvent setUrl(String url)
    {
        this.url = url;
        return this;
    }

    /**
     * color of the event when viewing in the calendar.
     */
    public Integer getColor(){return color;}

    public CalendarEvent setColor(Integer color)
    {
        this.color = color;
        return this;
    }

    /**
     * Is this event a repeating event?
     */
    public boolean isRepeating(){return repeats != null && repeats;}

    public CalendarEvent setRepeats(Boolean repeats)
    {
        this.repeats = repeats;
        return this;
    }

    /**
     * Get the ISO 8601 timestamp that the event starts at.
     */
    public String getStartsAt(){return startsAt;}

    public CalendarEvent setStartsAt(String startsAt)
    {
        this.startsAt = startsAt;
        return this;
    }

    /**
     * duration of the event in minutes.
     */
    public Integer getDuration(){return duration;}

    public CalendarEvent setDuration(Integer duration)
    {
        this.duration = duration;
        return this;
    }

    /**
     * No description available in API documentation.
     */
    public boolean isPrivate(){return isPrivate != null && isPrivate;}

    public CalendarEvent setIsPrivate(Boolean isPrivate)
    {
        this.isPrivate = isPrivate;
        return this;
    }

    /**
     * No description available in API documentation.
     * Also, mentions will not be implemented in Guilded4J - yet.
     */
    public Mentions[] getMentions(){throw new UnsupportedOperationException("https://www.guilded.gg/Guilded4J-Cafe/blog/Announcements/About-the-APIs-new-Mentions-feature");}

    public CalendarEvent setMentions(Mentions mentions)
    {
        this.mentions = mentions;
        return this;
    }

    /**
     * Get the ISO 8601 timestamp that the event was created at.
     */
    public String getCreatedAt(){return createdAt;}

    public CalendarEvent setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Get the ID of the user who created this event.
     */
    public String getCreatedBy(){return createdBy;}

    public CalendarEvent setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * No description available in API documentation.
     */
    public CalendarEventCancellation getCancellation(){return cancellation;}

    public CalendarEvent setCancellation(CalendarEventCancellation cancellation)
    {
        this.cancellation = cancellation;
        return this;
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
                .set("repeats", repeats)
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
