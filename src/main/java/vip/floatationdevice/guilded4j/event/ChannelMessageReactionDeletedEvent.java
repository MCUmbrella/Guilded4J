/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

public class ChannelMessageReactionDeletedEvent extends GuildedEvent // TODO: implement
{
    public ChannelMessageReactionDeletedEvent(Object source, String json)
    {
        super(source, json);
    }
}
