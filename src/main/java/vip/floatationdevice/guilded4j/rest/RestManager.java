/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
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

/**
 * Class for interacting with the Guilded REST API.
 */
public abstract class RestManager
{
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
        HttpResponse res;
        try
        {
            HttpRequest req = new HttpRequest(UrlBuilder.of(url))
                    .method(method)
                    .header("Authorization", "Bearer " + authToken)
                    .header("Accept", "application/json")
                    .header("Content-type", "application/json")
                    .timeout(httpTimeout);
            if(body != null) req.body(body.toString());
            if(proxy != Proxy.NO_PROXY) req.setProxy(proxy);
            res = req.execute();
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
            default:
                throw GuildedException.fromString(res.body()).setType(ExceptionType.fromInt(res.getStatus()));
        }
    }
}
