package vip.floatationdevice.guilded4j.exception;

public class GuildedException extends RuntimeException // TODO
{
    public String code;
    public GuildedException(String code, String message)
    {
        super(message);
        this.code = code;
    }
    public String getCode() {return code;}
}
