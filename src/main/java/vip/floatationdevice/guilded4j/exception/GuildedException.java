package vip.floatationdevice.guilded4j.exception;

/**
 * The exception object converted from the error JSON returned by Guilded API.
 */
public class GuildedException extends RuntimeException
{
    private final String code;
    private final String description;

    /**
     * Default constructor.
     * @param code The error code ("code" key in the JSON).
     * @param message The error description ("message" key in the JSON).
     */
    public GuildedException(String code, String message)
    {
        super(code + " - " + message);
        this.code = code;
        this.description = message;
    }

    /**
     * Get the error code.
     * @return The error code in the JSON returned by Guilded API.
     */
    public String getCode() {return code;}

    /**
     * Get the description of the error.
     * @return The error's description in the JSON returned by Guilded API.
     */
    public String getDescription() {return description;}
}
