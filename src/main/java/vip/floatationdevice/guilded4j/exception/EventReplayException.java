/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.exception;

public class EventReplayException extends GuildedException
{
    public EventReplayException(String code, String message)
    {
        super(code, message);
    }
}
