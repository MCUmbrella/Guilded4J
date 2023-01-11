/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.ListItem;

/**
 * Event fired when a list item is completed.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ListItemCompleted" target=_blank>https://www.guilded.gg/docs/api/websockets/ListItemCompleted</a>
 */
public class ListItemCompletedEvent extends GuildedEvent
{
    private final ListItem listItem;

    public ListItemCompletedEvent(Object source, String json)
    {
        super(source, json);
        this.listItem = ListItem.fromJSON((JSONObject) new JSONObject(json).getByPath("d.listItem"));
    }

    /**
     * Get the list item that was completed.
     * @return The item's ListItem object.
     */
    public ListItem getListItem(){return listItem;}
}
