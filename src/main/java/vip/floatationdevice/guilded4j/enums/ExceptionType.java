/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.enums;

/**
 * The type of {@link vip.floatationdevice.guilded4j.exception.GuildedException} that was thrown.
 */
@SuppressWarnings("unused")
public enum ExceptionType
{
    /**
     * 400
     */
    BAD_REQUEST,
    /**
     * 401
     */
    UNAUTHORIZED,
    /**
     * 403
     */
    FORBIDDEN,
    /**
     * 404
     */
    NOT_FOUND,
    /**
     * 409
     */
    CONFLICTED,
    /**
     * 429
     */
    RATE_LIMITED,
    /**
     * 500-504
     */
    INTERNAL_SERVER_ERROR,
    OTHER;

    /**
     * Gets the {@link ExceptionType} from the specified HTTP status code.
     * @param i The HTTP status code.
     * @return The {@link ExceptionType} corresponding to the specified HTTP status code.
     */
    public static ExceptionType fromInt(int i)
    {
        switch(i)
        {
            case 400:
                return BAD_REQUEST;
            case 401:
                return UNAUTHORIZED;
            case 403:
                return FORBIDDEN;
            case 404:
                return NOT_FOUND;
            case 409:
                return CONFLICTED;
            case 429:
                return RATE_LIMITED;
            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
                return INTERNAL_SERVER_ERROR;
            default:
                return OTHER;
        }
    }
}
