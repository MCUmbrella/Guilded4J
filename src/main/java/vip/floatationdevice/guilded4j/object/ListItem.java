/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;
import vip.floatationdevice.guilded4j.object.misc.ListItemNote;

/**
 * The basic item object in a 'list' type channel.<br>
 * <a href="https://www.guilded.gg/docs/api/listItems/ListItem" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItem</a>
 */
public class ListItem
{
    private String
            id, serverId, channelId, message, createdAt, createdBy, createdByWebhookId,
            updatedAt, updatedBy, parentListItemId, completedAt, completedBy;
    private ListItemNote note;

    /**
     * Get the UUID of this list item.
     */
    public String getId(){return id;}

    /**
     * Get the UUID of the server this list item is in.
     */
    public String getChannelId(){return channelId;}

    /**
     * Get the message of the list item.
     */
    public String getMessage(){return message;}

    /**
     * Get the note of this list item.
     * @return The note's ListItemNote object.
     */
    public ListItemNote getNote(){return note;}

    /**
     * Get the time this list item was created.
     * @return The ISO 8601 timestamp that the list item was created at.
     */
    public String getCreationTime(){return createdAt;}

    /**
     * Get the ID of the user who created this list item.
     * @return The ID of the user who created this list item.<br>
     * <b>NOTE:</b><br>
     * If this event has createdByWebhookId present, this field will still be populated, but can be ignored.<br>
     * In this case, the value of this field will always be 'Ann6LewA'.
     */
    public String getCreatorId(){return createdBy;}

    /**
     * Get the ID of the webhook that created this list item.
     * @return The ID of the webhook who created this list item. If this list item was not created by a webhook, return {@code null}.
     */
    public String getWebhookCreatorId(){return createdByWebhookId;}

    /**
     * Get the time this list item was last updated.
     * @return The ISO 8601 timestamp that the list item was last updated at. If this list item has never been updated, return {@code null}.
     */
    public String getUpdateTime(){return updatedAt;}

    /**
     * Get the ID of the user who updated this list item.
     * @return The ID of the user who updated this list item. If this list item has never been updated, return {@code null}.
     */
    public String getUpdaterId(){return updatedBy;}

    /**
     * Get the ID of the parent list item.
     * @return The ID of the parent list item if this list item is nested, otherwise {@code null}.
     */
    public String getParentListItemId(){return parentListItemId;}

    /**
     * Get the time this list item was completed.
     * @return The ISO 8601 timestamp that the list item was completed at. If this list item is not completed, return {@code null}.
     */
    public String getCompletionTime(){return completedAt;}

    /**
     * Get the ID of the user who completed this list item.
     * @return The ID of the user who completed this list item. If this list item is not completed, return {@code null}.
     */
    public String getCompletedBy(){return completedBy;}

    public ListItem setId(String id)
    {
        this.id = id;
        return this;
    }

    public ListItem setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    public ListItem setChannelId(String channelId)
    {
        this.channelId = channelId;
        return this;
    }

    public ListItem setMessage(String message)
    {
        this.message = message;
        return this;
    }

    public ListItem setNote(ListItemNote note)
    {
        this.note = note;
        return this;
    }

    public ListItem setCreationTime(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    public ListItem setCreatorId(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    public ListItem setWebhookCreatorId(String createdByWebhookId)
    {
        this.createdByWebhookId = createdByWebhookId;
        return this;
    }

    public ListItem setUpdateTime(String updatedAt)
    {
        this.updatedAt = updatedAt;
        return this;
    }

    public ListItem setUpdaterId(String updatedBy)
    {
        this.updatedBy = updatedBy;
        return this;
    }

    public ListItem setParentListItemId(String parentListItemId)
    {
        this.parentListItemId = parentListItemId;
        return this;
    }

    public ListItem setCompletionTime(String completedAt)
    {
        this.completedAt = completedAt;
        return this;
    }

    public ListItem setCompleterId(String completedBy)
    {
        this.completedBy = completedBy;
        return this;
    }

    /**
     * Use the given JSON object to generate ListItem object.
     * @param json The JSON object.
     * @return ListItem object.
     * @throws IllegalArgumentException when the essential fields are not set.
     */
    public static ListItem fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getStr("id"),
                json.getStr("serverId"),
                json.getStr("channelId"),
                json.getStr("message"),
                json.getStr("createdAt"),
                json.getStr("createdBy")
        );
        return new ListItem()
                .setId(json.getStr("id"))
                .setServerId(json.getStr("serverId"))
                .setChannelId(json.getStr("channelId"))
                .setMessage(json.getStr("message"))
                .setNote(json.get("note") == null ? null : ListItemNote.fromJSON(json.getJSONObject("note")))
                .setCreationTime(json.getStr("createdAt"))
                .setCreatorId(json.getStr("createdBy"))
                .setWebhookCreatorId(json.getStr("createdByWebhookId"))
                .setUpdateTime(json.getStr("updatedAt"))
                .setUpdaterId(json.getStr("updatedBy"))
                .setParentListItemId(json.getStr("parentListItemId"))
                .setCompletionTime(json.getStr("completedAt"))
                .setCompleterId(json.getStr("completedBy"));
    }

    /**
     * Convert the ListItem object to JSON string.
     * @return A JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("serverId", serverId)
                .set("channelId", channelId)
                .set("message", message)
                .set("note", new JSONObject(note.toString()))
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("createdByWebhookId", createdByWebhookId)
                .set("updatedAt", updatedAt)
                .set("updatedBy", updatedBy)
                .set("parentListItemId", parentListItemId)
                .set("completedAt", completedAt)
                .set("completedBy", completedBy)
                .toString();
    }
}
