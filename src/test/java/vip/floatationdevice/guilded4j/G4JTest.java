package vip.floatationdevice.guilded4j;

import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.object.CalendarEventRsvp;
import vip.floatationdevice.guilded4j.rest.RestManager;

import java.util.Arrays;

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
        G4JClient c = new G4JClient(s.savedToken).setVerbose(true);
        c.ws.eventBus.register(new G4JTest());
        c.ws.connect();
        Thread.sleep(10000);
        c.getCalendarEventManager().updateCalendarEventRsvp("fd40acc7-10c6-486c-8a13-6747e9e30d7c", 5481006, "8412wg5d", "maybe");
        Thread.sleep(1000);
        c.getCalendarEventManager().updateCalendarEventRsvp("fd40acc7-10c6-486c-8a13-6747e9e30d7c", 5481006, "8412wg5d", "going");
        Thread.sleep(30000);
        c.getCalendarEventManager().deleteCalendarEventRsvp("fd40acc7-10c6-486c-8a13-6747e9e30d7c", 5481006, "8412wg5d");
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
    public void onReaction(ChannelMessageReactionCreatedEvent e)
    {
        System.out.println("Reaction created: " + e.getEmote().getName() + " on message " + e.getMessageId() + " in channel " + e.getChannelId() + " by " + e.getCreatedBy());
    }

    @Subscribe
    public void onReaction(ChannelMessageReactionDeletedEvent e)
    {
        System.out.println("Reaction deleted: " + e.getEmote().getName() + " on message " + e.getMessageId() + " in channel " + e.getChannelId() + " by " + e.getCreatedBy());
    }

    @Subscribe
    public void onCalendarEventRsvpUpdatedEvent(CalendarEventRsvpUpdatedEvent e)
    {
        System.out.println("CalendarEventRsvpUpdatedEvent: " + e.getCalendarEventRsvp());
    }

    @Subscribe
    public void onCalendarEventRsvpDeletedEvent(CalendarEventRsvpDeletedEvent e)
    {
        System.out.println("CalendarEventRsvpDeletedEvent: " + e.getCalendarEventRsvp());
    }

    @Subscribe
    public void onCalendarEventRsvpManyUpdatedEvent(CalendarEventRsvpManyUpdatedEvent e)
    {
        System.out.println("CalendarEventRsvpManyUpdatedEvent: " + Arrays.toString(e.getCalendarEventRsvps()));
    }
}
