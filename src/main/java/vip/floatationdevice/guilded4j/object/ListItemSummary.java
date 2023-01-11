/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;
import vip.floatationdevice.guilded4j.object.misc.ListItemNote;

/**
 * A summary of a list item.<br>
 * <a href="https://www.guilded.gg/docs/api/listItems/ListItemSummary" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemSummary</a>
 */
public class ListItemSummary
{
    private String
            id, serverId, channelId, message, createdAt, createdBy, createdByWebhookId,
            updatedAt, updatedBy, parentListItemId, completedAt, completedBy;
    private ListItemNote note;

    /**
     * Use the given JSON object to generate ListItemSummary object.
     * @param json The JSON object.
     * @return ListItemSummary object.
     * @throws IllegalArgumentException when the essential fields are not set.
     * @throws ClassCastException when the provided String's content isn't JSON format.
     */
    public static ListItemSummary fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getStr("id"),
                json.getStr("serverId"),
                json.getStr("channelId"),
                json.getStr("message"),
                json.getStr("createdAt"),
                json.getStr("createdBy")
        );
        return new ListItemSummary()
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
     * Get the UUID of this list item.
     */
    public String getId(){return id;}

    public ListItemSummary setId(String id)
    {
        this.id = id;
        return this;
    }

    /**
     * Get the UUID of the server this list item is in.
     */
    public String getChannelId(){return channelId;}

    public ListItemSummary setChannelId(String channelId)
    {
        this.channelId = channelId;
        return this;
    }

    /**
     * Get the message of the list item.
     */
    public String getMessage(){return message;}

    public ListItemSummary setMessage(String message)
    {
        this.message = message;
        return this;
    }

    /**
     * Get the note of this list item.
     * @return The note's ListItemNote object (with no content).
     */
    public ListItemNote getNote(){return note;}

    public ListItemSummary setNote(ListItemNote note)
    {
        this.note = note;
        return this;
    }

    /**
     * Get the time this list item was created.
     * @return The ISO 8601 timestamp that the list item was created at.
     */
    public String getCreationTime(){return createdAt;}

    public ListItemSummary setCreationTime(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Get the ID of the user who created this list item.
     * @return The ID of the user who created this list item.<br>
     * <b>NOTE:</b><br>
     * If this event has createdByWebhookId present, this field will still be populated, but can be ignored.<br>
     * In this case, the value of this field will always be 'Ann6LewA'.
     */
    public String getCreatorId(){return createdBy;}

    public ListItemSummary setCreatorId(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * Get the ID of the webhook that created this list item.
     * @return The ID of the webhook who created this list item. If this list item was not created by a webhook, return {@code null}.
     */
    public String getWebhookCreatorId(){return createdByWebhookId;}

    public ListItemSummary setWebhookCreatorId(String createdByWebhookId)
    {
        this.createdByWebhookId = createdByWebhookId;
        return this;
    }

    /**
     * Get the time this list item was last updated.
     * @return The ISO 8601 timestamp that the list item was last updated at. If this list item has never been updated, return {@code null}.
     */
    public String getUpdateTime(){return updatedAt;}

    public ListItemSummary setUpdateTime(String updatedAt)
    {
        this.updatedAt = updatedAt;
        return this;
    }

    /**
     * Get the ID of the user who updated this list item.
     * @return The ID of the user who updated this list item. If this list item has never been updated, return {@code null}.
     */
    public String getUpdaterId(){return updatedBy;}

    public ListItemSummary setUpdaterId(String updatedBy)
    {
        this.updatedBy = updatedBy;
        return this;
    }

    /**
     * Get the ID of the parent list item.
     * @return The ID of the parent list item if this list item is nested, otherwise {@code null}.
     */
    public String getParentListItemId(){return parentListItemId;}

    public ListItemSummary setParentListItemId(String parentListItemId)
    {
        this.parentListItemId = parentListItemId;
        return this;
    }

    /**
     * Get the time this list item was completed.
     * @return The ISO 8601 timestamp that the list item was completed at. If this list item is not completed, return {@code null}.
     */
    public String getCompletionTime(){return completedAt;}

    public ListItemSummary setCompletionTime(String completedAt)
    {
        this.completedAt = completedAt;
        return this;
    }

    /**
     * Get the ID of the user who completed this list item.
     * @return The ID of the user who completed this list item. If this list item is not completed, return {@code null}.
     */
    public String getCompleterId(){return completedBy;}

    public ListItemSummary setCompleterId(String completedBy)
    {
        this.completedBy = completedBy;
        return this;
    }

    public ListItemSummary setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    /**
     * Convert the ListItemSummary object to JSON string.
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
