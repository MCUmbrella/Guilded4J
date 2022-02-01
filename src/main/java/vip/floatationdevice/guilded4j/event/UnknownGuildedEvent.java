package vip.floatationdevice.guilded4j.event;

/**
 * Event that Guilded4J has not implemented or doesn't know how to handle.
 */
public class UnknownGuildedEvent extends GuildedEvent
{
    /**
     * Default constructor.
     */
    public UnknownGuildedEvent(Object source) {super(source);}
}
