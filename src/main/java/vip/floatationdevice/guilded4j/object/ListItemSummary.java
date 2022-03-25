/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import java.util.HashMap;

/**
 * A summary of a list item.
 * <a href="https://www.guilded.gg/docs/api/listItems/ListItemSummary" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItemSummary</a>
 */
public class ListItemSummary //TODO: implement
{
    private String
            id, serverId, channelId, message, createdAt, createdBy, createdByBotId, createdByWebhookId,
            updatedAt, updatedBy, parentListItemId, completedAt, completedBy;
    private HashMap<String, String> note;
}
