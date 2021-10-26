package vip.floatationdevice.guilded4j.exception;

public class GuildedException extends RuntimeException
{
    private final String code;

    /**
     * Default constructor.
     * @param code The error code ("code" key in the JSON).
     * @param message The error description ("message" key in the JSON).
     */
    public GuildedException(String code, String message)
    {
        super(code + " - " + message);
        this.code = code;
    }

    /**
     * Get the error code.
     * @return The error code in the JSON returned by Guilded API.
     */
    public String getCode() {return code;}
}
