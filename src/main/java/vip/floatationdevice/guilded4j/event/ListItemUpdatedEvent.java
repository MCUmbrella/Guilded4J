/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.ListItem;

/**
 * Event fired when a list item is updated.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ListItemUpdated" target=_blank>https://www.guilded.gg/docs/api/websockets/ListItemUpdated</a>
 */
public class ListItemUpdatedEvent extends GuildedEvent
{
    private final ListItem listItem;

    public ListItemUpdatedEvent(Object source, String json)
    {
        super(source, json);
        this.listItem = ListItem.fromJSON((JSONObject) new JSONObject(json).getByPath("d.listItem"));
    }

    /**
     * Get the list item that was updated.
     * @return The item's ListItem object.
     */
    public ListItem getListItem(){return listItem;}
}
