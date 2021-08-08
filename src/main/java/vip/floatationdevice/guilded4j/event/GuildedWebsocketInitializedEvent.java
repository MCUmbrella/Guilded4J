package vip.floatationdevice.guilded4j.event;

public class GuildedWebsocketInitializedEvent extends GuildedEvent
{
    String lastMessageId;
    int heartbeatIntervalMs;

    public GuildedWebsocketInitializedEvent(Object source, String lastMessageId, int heartbeatIntervalMs)
    {
        super(source);
        this.lastMessageId=lastMessageId;
        this.heartbeatIntervalMs=heartbeatIntervalMs;
    }

    public String getLastMessageId(){return this.lastMessageId;}
    public int getHeartbeatInterval(){return heartbeatIntervalMs;}
}
