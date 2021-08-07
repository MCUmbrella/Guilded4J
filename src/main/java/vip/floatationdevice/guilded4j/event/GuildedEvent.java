package vip.floatationdevice.guilded4j.event;

import javax.annotation.Nullable;
import java.util.EventObject;

public class GuildedEvent extends EventObject
{
    int op=Integer.MIN_VALUE;
    String eventType;
    String rawString;

    public GuildedEvent(Object source)
    {
        super(source);
    }
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

    public int getOpcode(){return this.op;}
    @Nullable public String getEventType(){return this.eventType;}
    @Nullable public String getRawString(){return this.rawString;}

    public GuildedEvent setOpCode(int opCode){this.op=opCode;return this;}
    public GuildedEvent setRawString(String rawString){this.rawString=rawString;return this;}
    public GuildedEvent setEventType(String t){this.eventType=t;return this;}
}
