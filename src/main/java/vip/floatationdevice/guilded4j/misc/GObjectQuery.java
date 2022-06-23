/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.misc;

/**
 * The common configuration for the Guilded object query request. Will be converted to HTTP query parameters.
 * This is not part of the official Guilded API.
 */
public class GObjectQuery
{
    private String before, after;
    private int limit = 25;

    /**
     * Set the starting time of the query. Default is null.
     * @param before An ISO 8601 timestamp that will be used to filter out results for the current page.
     */
    public GObjectQuery before(String before)
    {
        this.before = before;
        return this;
    }

    /**
     * Set the ending time of the query. Default is null.
     * @param after An ISO 8601 timestamp that will be used to filter out results for the current page.
     */
    public GObjectQuery after(String after)
    {
        this.after = after;
        return this;
    }

    /**
     * Limit the number of results returned.
     * @param limit The maximum number of results to return (1 to 100, default 25).
     */
    public GObjectQuery limit(int limit)
    {
        this.limit = limit;
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("?limit=").append(limit);
        if(before != null) sb.append("&before=").append(before);
        if(after != null) sb.append("&after=").append(after);
        return sb.toString();
    }
}
