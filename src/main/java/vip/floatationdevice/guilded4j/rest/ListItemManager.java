/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.ListItem;
import vip.floatationdevice.guilded4j.object.ListItemSummary;

import static vip.floatationdevice.guilded4j.G4JClient.LIST_CHANNEL_URL;

/**
 * Manages the list items.
 */
public class ListItemManager extends RestManager
{
    public ListItemManager(String authToken)
    {
        super(authToken);
    }

    /**
     * Create a list item.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemCreate" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemCreate</a>
     * @param message The item's name.
     * @param note The item's note (can be null).
     * @return The newly created item's ListItem object.
     */
    public ListItem createListItem(String channelId, String message, String note)
    {
        return ListItem.fromJSON(
                execute(Method.POST,
                        LIST_CHANNEL_URL.replace("{channelId}", channelId),
                        new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                                .set("message", message)
                                .set("note", new JSONObject().set("content", note))
                ).getJSONObject("listItem")
        );
    }

    /**
     * Get list items within a channel.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemReadMany" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemReadMany</a>
     * @param channelId The UUID of the channel.
     * @return A list of ListItemSummary objects. (the maximum number of items returned is not mentioned in the API documentation for now)
     */
    public ListItemSummary[] getListItems(String channelId)
    {
        JSONArray itemsJson = execute(Method.GET, LIST_CHANNEL_URL.replace("{channelId}", channelId), null).getJSONArray("listItems");
        ListItemSummary[] items = new ListItemSummary[itemsJson.size()];
        for(int i = 0; i < itemsJson.size(); i++) items[i] = ListItemSummary.fromJSON(itemsJson.getJSONObject(i));
        return items;
    }

    /**
     * Get a list item.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemRead" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemRead</a>
     * @param channelId The UUID of the channel.
     * @param listItemId The UUID of the list item.
     * @return The item's ListItem object.
     */
    public ListItem getListItem(String channelId, String listItemId)
    {
        return ListItem.fromJSON(
                execute(Method.GET, LIST_CHANNEL_URL.replace("{channelId}", channelId) + "/" + listItemId, null)
                        .getJSONObject("listItem")
        );
    }

    /**
     * Update a list item.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemUpdate" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemUpdate</a>
     * @param channelId The UUID of the channel.
     * @param listItemId The UUID of the list item.
     * @param message The item's new name.
     * @param note The item's new note text (can be null).
     * @return The updated item's ListItem object.
     */
    public ListItem updateListItem(String channelId, String listItemId, String message, String note)
    {
        return ListItem.fromJSON(
                execute(Method.PUT, LIST_CHANNEL_URL.replace("{channelId}", channelId) + "/" + listItemId,
                        new JSONObject()
                                .set("message", message)
                                .set("note", new JSONObject().set("content", note))
                ).getJSONObject("listItem")
        );
    }

    /**
     * Delete a list item.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemDelete" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemDelete</a>
     * @param channelId The UUID of the channel.
     * @param listItemId The UUID of the list item.
     */
    public void deleteListItem(String channelId, String listItemId)
    {
        execute(Method.DELETE, LIST_CHANNEL_URL.replace("{channelId}", channelId) + "/" + listItemId, null);
    }

    /**
     * Complete a list item.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemCompleteCreate" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemCompleteCreate</a>
     * @param channelId The UUID of the channel.
     * @param listItemId The UUID of the list item.
     */
    public void completeListItem(String channelId, String listItemId)
    {
        execute(Method.POST, LIST_CHANNEL_URL.replace("{channelId}", channelId) + "/" + listItemId + "/complete", null);
    }

    /**
     * Uncomplete a list item.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemCompleteDelete" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemCompleteDelete</a>
     * @param channelId The UUID of the channel.
     * @param listItemId The UUID of the list item.
     */
    public void uncompleteListItem(String channelId, String listItemId)
    {
        execute(Method.DELETE, LIST_CHANNEL_URL.replace("{channelId}", channelId) + "/" + listItemId + "/complete", null);
    }
}
