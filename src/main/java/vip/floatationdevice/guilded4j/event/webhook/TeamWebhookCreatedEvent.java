/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event.webhook;

import vip.floatationdevice.guilded4j.event.GuildedEvent;
import vip.floatationdevice.guilded4j.object.Webhook;

/**
 * Event fired when a webhook is created.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/TeamWebhookCreated" target=_blank>https://www.guilded.gg/docs/api/websockets/TeamWebhookCreated</a>
 */
public class TeamWebhookCreatedEvent extends GuildedEvent
{
    private final Webhook webhook;

    public TeamWebhookCreatedEvent(Object source, Webhook webhook)
    {
        super(source);
        this.webhook = webhook;
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
