/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.ListItem;

/**
 * Event fired when a list item is created.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ListItemDeleted" target=_blank>https://www.guilded.gg/docs/api/websockets/ListItemDeleted</a>
 */
public class ListItemDeletedEvent extends GuildedEvent
{
    private final ListItem listItem;

    public ListItemDeletedEvent(Object source, String json)
    {
        super(source, json);
        JSONObject j = new JSONObject(json);
        this.listItem = ListItem.fromString(j.getByPath("d.listItem").toString());
    }

    /**
     * Get the list item that was deleted.
     * @return The item's ListItem object.
     */
    public ListItem getListItem(){return listItem;}
}
