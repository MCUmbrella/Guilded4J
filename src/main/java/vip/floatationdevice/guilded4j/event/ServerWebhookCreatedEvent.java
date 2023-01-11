/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.Webhook;

/**
 * Event fired when a webhook is created.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ServerWebhookCreated" target=_blank>https://www.guilded.gg/docs/api/websockets/ServerWebhookCreated</a>
 */
public class ServerWebhookCreatedEvent extends GuildedEvent
{
    private final Webhook webhook;

    public ServerWebhookCreatedEvent(Object source, String json)
    {
        super(source, json);
        this.webhook = Webhook.fromJSON((JSONObject) new JSONObject(json).getByPath("d.webhook"));
    }

    /**
     * Gets the webhook that was created.
     * @return The Webhook object.
     */
    public Webhook getWebhook()
    {
        return webhook;
    }
}
