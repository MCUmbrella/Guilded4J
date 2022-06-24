package vip.floatationdevice.guilded4j;

import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.misc.ChatMessageQuery;
import vip.floatationdevice.guilded4j.object.CalendarEvent;
import vip.floatationdevice.guilded4j.rest.CalendarEventManager;

/**
 * Some temporary test code will go here.
 */
public class G4JTest
{
    public static void main(String[] args) throws Exception
    {
        //==============================================================
        G4JDebugger.G4JSession s = new G4JDebugger.G4JSession();
        s.restore();
        G4JWebSocketClient ws = new G4JWebSocketClient(s.savedToken);
        ws.eventBus.register(new G4JTest());
        ws.connect();
        //==============================================================
    }
    @Subscribe
    public void onConnect(GuildedWebSocketWelcomeEvent e)
    {
        System.out.println("Connected to Guilded WebSocket server.");
    }
    @Subscribe
    public void onDisconnect(GuildedWebSocketClosedEvent e)
    {
        System.out.println("Disconnected from Guilded WebSocket server.");
    }
    @Subscribe
    public void onUnknownGuildedEvent(UnknownGuildedEvent e)
    {
        System.err.println("===== Unknown Guilded event =====\nRaw: " + e.getRawString() + "\nReason: " + e.getReason());
        if(e.getReason() != null) e.getReason().printStackTrace();
    }
    @Subscribe
    public void onCalEventCreate(CalendarEventCreatedEvent e)
    {
        System.out.println("Created: " + e.getCalendarEvent().toString());
    }
    @Subscribe
    public void onCalEventUpdate(CalendarEventUpdatedEvent e)
    {
        System.out.println("Updated: " + e.getCalendarEvent().toString());
    }
    @Subscribe
    public void onCalEventDelete(CalendarEventDeletedEvent e)
    {
        System.out.println("Deleted: " + e.getCalendarEvent().toString());
    }
}
