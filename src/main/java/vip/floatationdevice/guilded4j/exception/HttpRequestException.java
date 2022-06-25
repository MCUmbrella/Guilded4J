/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.exception;

/**
 * Exception thrown when a HTTP request fails, probably caused by a network error.
 * By using getCause(), you will get cn.hutool.core.io.IORuntimeException, which contains the actual cause of the error.
 */
public class HttpRequestException extends RuntimeException
{
    public HttpRequestException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
