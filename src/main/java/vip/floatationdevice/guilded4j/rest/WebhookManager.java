/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.Webhook;

import static vip.floatationdevice.guilded4j.G4JClient.WEBHOOKS_URL;

/**
 * Manages webhooks.
 */
public class WebhookManager extends RestManager
{
    public WebhookManager(String authToken)
    {
        super(authToken);
    }

    /**
     * Create a webhook.<br>
     * <a href="https://www.guilded.gg/docs/api/webhook/WebhookCreate" target=_blank>https://www.guilded.gg/docs/api/webhook/WebhookCreate</a>
     * @param serverId The ID of the server where the webhook should be created.
     * @param name The name of the webhook (min length 1).
     * @param channelId Channel ID to create the webhook in.
     * @return The newly created webhook's Webhook object.
     */
    public Webhook createWebhook(String serverId, String name, String channelId)
    {
        return Webhook.fromJSON(
                execute(Method.POST,
                        WEBHOOKS_URL.replace("{serverId}", serverId),
                        new JSONObject().set("name", name).set("channelId", channelId)
                ).getJSONObject("webhook")
        );
    }

    /**
     * Get a list of webhooks from a server.<br>
     * <a href="https://www.guilded.gg/docs/api/webhook/WebhookReadMany" target=_blank>https://www.guilded.gg/docs/api/webhook/WebhookReadMany</a>
     * @param serverId The ID of the server to get webhooks from.
     * @param channelId The ID of the channel to filter webhooks by.
     * @return A list of Webhook objects.
     */
    public Webhook[] getWebhooks(String serverId, String channelId)
    {
        JSONArray webhooksJson = execute(Method.GET,
                WEBHOOKS_URL.replace("{serverId}", serverId) + "?channelId=" + channelId,
                null
        ).getJSONArray("webhooks");
        Webhook[] webhooks = new Webhook[webhooksJson.size()];
        for(int i = 0; i != webhooksJson.size(); i++) webhooks[i] = Webhook.fromJSON(webhooksJson.getJSONObject(i));
        return webhooks;
    }

    /**
     * Update a webhook.<br>
     * <a href="https://www.guilded.gg/docs/api/webhook/WebhookUpdate" target=_blank>https://www.guilded.gg/docs/api/webhook/WebhookUpdate</a>
     * @param serverId The ID of the server the webhook is in.
     * @param webhookId The ID of the webhook to update.
     * @param name The new name of the webhook.
     * @param channelId The new channel ID of the webhook (null to keep the same channel).
     * @return The updated Webhook object.
     */
    public Webhook updateWebhook(String serverId, String webhookId, String name, String channelId)
    {
        return Webhook.fromJSON(execute(Method.PUT,
                WEBHOOKS_URL.replace("{serverId}", serverId) + "/" + webhookId,
                new JSONObject(new JSONConfig().setIgnoreNullValue(true)).set("name", name).set("channelId", channelId)
        ).getJSONObject("webhook"));
    }

    /**
     * Delete a webhook.<br>
     * <a href="https://www.guilded.gg/docs/api/webhook/WebhookDelete" target=_blank>https://www.guilded.gg/docs/api/webhook/WebhookDelete</a>
     * @param serverId The ID of the server the webhook is in.
     * @param webhookId The ID of the webhook to delete.
     */
    public void deleteWebhook(String serverId, String webhookId)
    {
        execute(Method.DELETE, WEBHOOKS_URL.replace("{serverId}", serverId) + "/" + webhookId, null);
    }

    /**
     * Get a server's webhook.<br>
     * <a href="https://www.guilded.gg/docs/api/webhook/WebhookRead" target=_blank>https://www.guilded.gg/docs/api/webhook/WebhookRead</a>
     * @param serverId The ID of the server the webhook is in.
     * @param webhookId The ID of the webhook to get.
     * @return The Webhook object.
     */
    public Webhook getWebhook(String serverId, String webhookId)
    {
        return Webhook.fromJSON(
                execute(Method.GET,
                        WEBHOOKS_URL.replace("{serverId}", serverId) + "/" + webhookId,
                        null
                ).getJSONObject("webhook")
        );
    }
}