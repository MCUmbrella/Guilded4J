/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.misc;

/**
 * The configuration for the chat message query.
 * This is not part of the official Guilded API.
 */
public class ChatMessageQuery extends GObjectQuery
{
    private boolean includePrivate = false;

    public ChatMessageQuery()
    {
        super();
        limit(50); // This is the default limit for the chat message query.
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
        if(includePrivate) return super.toString() + "&includePrivate=true";
        return super.toString();
    }
}
