package vip.floatationdevice.guilded4j.event;

import java.util.EventObject;

public class GuildedWebsocketClosedEvent extends EventObject
{
    int code=Integer.MIN_VALUE;
    String reason="";
    Boolean remote=false;

    public GuildedWebsocketClosedEvent(Object source, int code, String reason, Boolean remote)
    {
        super(source);
        this.code=code;
        this.reason=reason;
        this.remote=remote;
    }

    public int getCode(){return code;}
    public String getReason(){return reason;}
    public Boolean isRemote(){return remote;}

}
