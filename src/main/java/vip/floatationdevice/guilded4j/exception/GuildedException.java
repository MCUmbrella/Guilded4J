/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.exception;

import vip.floatationdevice.guilded4j.enums.ExceptionType;

/**
 * The exception object converted from the error JSON returned by Guilded API.
 */
public class GuildedException extends RuntimeException {
    private final String code;
    private final String description;
    private final ExceptionType type;//TODO

    /**
     * Default constructor.
     *
     * @param code    The error code ("code" key in the JSON).
     * @param message The error description ("message" key in the JSON).
     */
    public GuildedException(String code, String message) {
        super(code + " - " + message);
        this.code = code;
        this.description = message;
        this.type = ExceptionType.OTHER;
    }

    /**
     * Get the error code.
     *
     * @return The error code in the JSON returned by Guilded API.
     */
    public String getCode() {
        return code;
    }

    /**
     * Get the description of the error.
     *
     * @return The error's description in the JSON returned by Guilded API.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the type of the error (TODO).
     *
     * @return The error's type.
     */
    public ExceptionType getType() {
        return type;
    }
}
