/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.misc;

/**
 * The configuration for the chat message query. Will be converted to HTTP query parameters.
 * This is not part of the official Guilded API.
 */
public class ChatMessageQuery
{
    private String before, after;
    private int limit = 50;
    private boolean includePrivate = false;

    /**
     * Set the starting time of the query. Default is null.
     * @param before An ISO 8601 timestamp that will be used to filter out results for the current page.
     */
    public ChatMessageQuery before(String before)
    {
        this.before = before;
        return this;
    }

    /**
     * Set the ending time of the query. Default is null.
     * @param after An ISO 8601 timestamp that will be used to filter out results for the current page.
     */
    public ChatMessageQuery after(String after)
    {
        this.after = after;
        return this;
    }

    /**
     * Limit the number of results returned.
     * @param limit The maximum number of results to return (1 to 100, default 50).
     */
    public ChatMessageQuery limit(int limit)
    {
        this.limit = limit;
        return this;
    }

    /**
     * Include private messages in the query.
     */
    public ChatMessageQuery includePrivate()
    {
        this.includePrivate = true;
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder().append("?limit=").append(limit);
        if(includePrivate)
            sb.append("&includePrivate=true");
        if(before != null)
            sb.append("&before=").append(before);
        if(after != null)
            sb.append("&after=").append(after);
        return sb.toString();
    }
}
