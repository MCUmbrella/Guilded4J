/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

/**
 * Class for interacting with the Guilded REST API.
 */
public abstract class RestManager
{
    String authToken;
    int httpTimeout = 20000;

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
}
