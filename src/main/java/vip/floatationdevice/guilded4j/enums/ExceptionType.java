/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.enums;

//TODO: use it when Guilded improves the exception handling section of the API doc

/**
 * The type of {@link vip.floatationdevice.guilded4j.exception.GuildedException} that was thrown.
 */
@SuppressWarnings("unused")
public enum ExceptionType
{
    /** 400 */ BAD_REQUEST,
    /** 401 */ UNAUTHORIZED,
    /** 403 */ FORBIDDEN,
    /** 404 */ NOT_FOUND,
    /** 409 */ CONFLICTED,
    /** 500-504 */ INTERNAL_SERVER_ERROR,
    OTHER
}
