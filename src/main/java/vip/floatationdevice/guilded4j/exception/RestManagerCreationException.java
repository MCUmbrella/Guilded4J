/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.exception;

/**
 * The exception that is thrown when the REST manager creation fails.
 */
public class RestManagerCreationException extends RuntimeException
{
    public RestManagerCreationException(String s, ReflectiveOperationException e)
    {
        super(s, e);
    }
}
