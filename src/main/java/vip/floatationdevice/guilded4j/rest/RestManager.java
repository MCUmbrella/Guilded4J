/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.enums.ExceptionType;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.exception.HttpRequestException;

import java.net.Proxy;
import java.util.HashMap;

/**
 * Class for interacting with the Guilded REST API.
 */
public abstract class RestManager
{
    public static final String
            CHANNELS_URL = "https://www.guilded.gg/api/v1/channels",
            CALENDAR_CHANNEL_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/events",
            CALENDAR_RSVP_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/events/{calendarEventId}/rsvps",
            SERVER_URL = "https://www.guilded.gg/api/v1/servers/{serverId}",
            MSG_CHANNEL_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/messages",
            NICKNAME_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members/{userId}/nickname",
            MEMBERS_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members",
            BANS_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/bans",
            FORUM_CHANNEL_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/topics",
            LIST_CHANNEL_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/items",
            DOC_CHANNEL_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/docs",
            USER_XP_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members/{userId}/xp",
            ROLE_XP_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/roles/{roleId}/xp",
            SOCIAL_LINK_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members/{userId}/social-links/{type}",
            GROUP_URL = "https://www.guilded.gg/api/v1/groups/{groupId}/members/{userId}",
            ROLES_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members/{userId}/roles",
            REACTION_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/content/{contentId}/emotes/{emoteId}",
            WEBHOOKS_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/webhooks",
            USERS_URL = "https://www.guilded.gg/api/v1/users/{userId}";
    public boolean verboseEnabled = false;
    String authToken;
    int httpTimeout = 20000;
    Proxy proxy;

    public RestManager(String authToken){this.authToken = authToken;}

    /**
     * Set the authorization token to be used for HTTP requests.
     * @param token The auth token.
     */
    public void setAuthToken(String token){this.authToken = token;}

    /**
     * Set the timeout of the HTTP request.
     * @param timeoutMs The timeout in milliseconds.
     */
    public RestManager setHttpTimeout(int timeoutMs)
    {
        this.httpTimeout = timeoutMs;
        return this;
    }

    /**
     * Set the proxy to be used for HTTP requests.
     * @param proxy The proxy to use. If null or Proxy.NO_PROXY, no proxy will be used.
     */
    public RestManager setProxy(Proxy proxy)
    {
        this.proxy = proxy == null ? Proxy.NO_PROXY : proxy;
        return this;
    }

    /**
     * Toggle verbose mode.
     * @param status If set to true, the HTTP requests and responses will be printed to stdout.
     */
    public RestManager setVerbose(boolean status)
    {
        this.verboseEnabled = status;
        return this;
    }

    /**
     * Execute a HTTP request.
     * @param method The HTTP method.
     * @param url The URL to request.
     * @param body The body of the request (if any).
     * @return The response JSON object. Null if no response body was returned.
     * @throws GuildedException If Guilded returns an error.
     * @throws HttpRequestException if an error occurred while sending HTTP request.
     */
    public JSONObject execute(Method method, String url, JSONObject body)
    {
        if(verboseEnabled) System.out.println("[Guilded4J/RestManager] " + method.toString() + ' ' + url);
        HttpResponse res;
        try
        {
            HttpRequest req = new HttpRequest(UrlBuilder.of(url))
                    .method(method)
                    .header("User-Agent", "Guilded4J/0.9.14 Hutool/5.8.11")
                    .header("Authorization", "[REDACTED]")
                    .header("Accept", "application/json")
                    .header("Content-type", "application/json")
                    .timeout(httpTimeout);
            if(body != null) req.body(body.toString());
            if(proxy != null) req.setProxy(proxy);
            if(verboseEnabled) System.out.println("[Guilded4J/RestManager] " + req.toString());
            req.header("Authorization", "Bearer " + authToken);
            res = req.execute();
            if(verboseEnabled) System.out.println("[Guilded4J/RestManager] " + res.toString());
        }
        catch(IORuntimeException e)
        {
            throw new HttpRequestException(e.getCause().toString(), e);
        }
        switch(res.getStatus())
        {
            case 200:
            case 201:
                return new JSONObject(res.body());
            case 204:
                return null;
            case 429:
            {
                HashMap<String, Object> map = new HashMap<>();
                map.put("retryAfter", res.header("Retry-After"));
                throw GuildedException.fromString(res.body()).setType(ExceptionType.RATE_LIMITED).setMeta(map);
            }
            default:
                throw GuildedException.fromString(res.body()).setType(ExceptionType.fromInt(res.getStatus()));
        }
    }
}
