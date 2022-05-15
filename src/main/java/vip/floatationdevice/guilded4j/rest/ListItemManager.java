/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.object.ListItem;
import vip.floatationdevice.guilded4j.object.ListItemNote;
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
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ListItem createListItem(String channelId, String message, String note)
    {
        JSONObject result = new JSONObject(HttpRequest.post(LIST_CHANNEL_URL.replace("{channelId}", channelId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject(new JSONConfig().setIgnoreNullValue(true)).set("message", message).set("note", note).toString()).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ListItem.fromString(result.get("listItem").toString());
    }

    /**
     * Get list items within a channel.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemReadMany" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemReadMany</a>
     * @param channelId The UUID of the channel.
     * @return A list of ListItemSummary objects. (the maximum number of items returned is not mentioned in the API documentation for now)
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ListItemSummary[] getListItems(String channelId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(LIST_CHANNEL_URL.replace("{channelId}", channelId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        JSONArray itemsJson = result.getJSONArray("listItems");
        ListItemSummary[] items = new ListItemSummary[itemsJson.size()];
        for(int i = 0; i < itemsJson.size(); i++)
            items[i] = ListItemSummary.fromString(itemsJson.get(i).toString());
        return items;
    }

    /**
     * Get a list item.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemRead" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemRead</a>
     * @param channelId The UUID of the channel.
     * @param listItemId The UUID of the list item.
     * @return The item's ListItem object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ListItem getListItem(String channelId, String listItemId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(LIST_CHANNEL_URL.replace("{channelId}", channelId) + "/" + listItemId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ListItem.fromString(result.get("listItem").toString());
    }

    /**
     * Update a list item.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemUpdate" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemUpdate</a>
     * @param channelId The UUID of the channel.
     * @param listItemId The UUID of the list item.
     * @param message The item's new name.
     * @param note The item's new note text (can be null).
     * @return The updated item's ListItem object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ListItem updateListItem(String channelId, String listItemId, String message, String note)
    {
        JSONObject result = new JSONObject(HttpRequest.put(LIST_CHANNEL_URL.replace("{channelId}", channelId) + "/" + listItemId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                        .set("message", message)
                        .set("note", new ListItemNote(null, null, note))
                        .toString()
                ).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ListItem.fromString(result.get("listItem").toString());
    }

    /**
     * Delete a list item.<br>
     * <a href="https://www.guilded.gg/docs/api/listItems/ListItemDelete" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemDelete</a>
     * @param channelId The UUID of the channel.
     * @param listItemId The UUID of the list item.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void deleteListItem(String channelId, String listItemId)
    {
        String result = HttpRequest.delete(LIST_CHANNEL_URL.replace("{channelId}", channelId) + "/" + listItemId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("ListItemDelete returned an unexpected JSON string");
        }
    }
}
