package vip.floatationdevice.g4j.event;

import java.util.EventObject;

public class GuildedEvent extends EventObject
{
    public GuildedEvent(Object source)
    {
        super(source);
    }
    int op=Integer.MIN_VALUE;
    String eventType;
    String rawString;
    public GuildedEvent(Object source, int op)
    {
        super(source);
        this.op=op;
    }
    public GuildedEvent(Object source, int op, String eventType)
    {
        super(source);
        this.op=op;
        this.eventType=eventType;
    }
    public GuildedEvent setRawString(String rawString){this.rawString=rawString;return this;}
    public int getOpcode(){return this.op;}
    public String getEventType(){return this.eventType;}
    public String getRawString(){return this.rawString;}
}
