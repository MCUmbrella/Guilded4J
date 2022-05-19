/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * The basic document object in a 'doc' type channel.<br>
 * <a href="https://www.guilded.gg/docs/api/docs/Doc" target=_blank>https://www.guilded.gg/docs/api/docs/Doc</a>
 */
public class Doc
{
    int id;
    String serverId, channelId, title, content, createdAt, createdBy, updatedAt, updatedBy;

    /**
     * Get the ID of the document.
     */
    public int getId(){return id;}

    /**
     * Get the server ID of the document.
     */
    public String getServerId(){return serverId;}

    /**
     * Get the channel ID of the document.
     */
    public String getChannelId(){return channelId;}

    /**
     * Get the title of the document.
     */
    public String getTitle(){return title;}

    /**
     * Get the content of the document.
     */
    public String getContent(){return content;}

    /**
     * Get the ISO 8601 timestamp string that the doc was created at.
     */
    public String getCreationTime(){return createdAt;}

    /**
     * Get the user ID of the user who created the document.
     */
    public String getCreatorId(){return createdBy;}

    /**
     * Get the ISO 8601 timestamp string that the doc was updated at.
     * @return The timestamp string. If the document has not been updated, return {@code null}.
     */
    public String getUpdateTime(){return updatedAt;}

    /**
     * Get the user ID of the user who last updated the document.
     */
    public String getUpdatedBy(){return updatedBy;}

    public Doc setId(int id)
    {
        this.id = id;
        return this;
    }

    public Doc setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    public Doc setChannelId(String channelId)
    {
        this.channelId = channelId;
        return this;
    }

    public Doc setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public Doc setContent(String content)
    {
        this.content = content;
        return this;
    }

    public Doc setCreationTime(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    public Doc setCreatorId(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    public Doc setUpdateTime(String updatedAt)
    {
        this.updatedAt = updatedAt;
        return this;
    }

    public Doc setUpdatedBy(String updatedBy)
    {
        this.updatedBy = updatedBy;
        return this;
    }

    /**
     * Use the given JSON object to generate Doc object.
     * @param json The JSON object.
     * @return Doc object.
     * @throws IllegalArgumentException when the essential fields are not set.
     */
    public static Doc fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getStr("id"),
                json.getStr("serverId"),
                json.getStr("channelId"),
                json.getStr("title"),
                json.getStr("content"),
                json.getStr("createdAt"),
                json.getStr("createdBy")
        );
        return new Doc()
                .setId(json.getInt("id"))
                .setServerId(json.getStr("serverId"))
                .setChannelId(json.getStr("channelId"))
                .setTitle(json.getStr("title"))
                .setContent(json.getStr("content"))
                .setCreationTime(json.getStr("createdAt"))
                .setCreatorId(json.getStr("createdBy"))
                .setUpdateTime(json.getStr("updatedAt"))
                .setUpdatedBy(json.getStr("updatedBy"));
    }

    /**
     * Convert the ForumThread object to JSON string.
     * @return A JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("serverId", serverId)
                .set("channelId", channelId)
                .set("title", title)
                .set("content", content)
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("updatedAt", updatedAt)
                .set("updatedBy", updatedBy)
                .toString();
    }
}
