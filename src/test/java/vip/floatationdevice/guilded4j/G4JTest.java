package vip.floatationdevice.guilded4j;

import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.object.CalendarEventComment;

import java.util.Scanner;

/**
 * Some temporary test code will go here.
 */
public class G4JTest
{
    static G4JClient c;
    static G4JDebugger.G4JSession s;
    static String lastMessageId;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception
    {
        //==============================================================
        s = new G4JDebugger.G4JSession();
        s.restore();
        c = new G4JClient(s.savedToken).setVerbose(true).setAutoReconnect(true);
        //c.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 9910)));
        c.registerEventListener(new G4JTest()).connectWebSocket(true, null);
        //==============================================================
        CalendarEventComment c1 = c.getCalendarEventManager().createCalendarEventComment("fd40acc7-10c6-486c-8a13-6747e9e30d7c", 5963732, "comment1");
        CalendarEventComment c2 = c.getCalendarEventManager().createCalendarEventComment("fd40acc7-10c6-486c-8a13-6747e9e30d7c", 5963732, "comment2");
        sc.nextLine();
        System.out.println("c1: " + c.getCalendarEventManager().getCalendarEventComment("fd40acc7-10c6-486c-8a13-6747e9e30d7c", 5963732, c1.getId()));
        sc.nextLine();
        c.getCalendarEventManager().updateCalendarEventComment("fd40acc7-10c6-486c-8a13-6747e9e30d7c", 5963732, c1.getId(), "comment1 new");
        c.getCalendarEventManager().updateCalendarEventComment("fd40acc7-10c6-486c-8a13-6747e9e30d7c", 5963732, c2.getId(), "comment2 new");
        sc.nextLine();
        for(CalendarEventComment c0 : c.getCalendarEventManager().getCalendarEventComments("fd40acc7-10c6-486c-8a13-6747e9e30d7c", 5963732))
            System.out.println(c0);
        sc.nextLine();
        c.getCalendarEventManager().deleteCalendarEventComment("fd40acc7-10c6-486c-8a13-6747e9e30d7c", 5963732, c1.getId());
        c.getCalendarEventManager().deleteCalendarEventComment("fd40acc7-10c6-486c-8a13-6747e9e30d7c", 5963732, c2.getId());
        sc.nextLine();
        System.exit(0);

    }

    @Subscribe
    public void onConnect(GuildedWebSocketWelcomeEvent e)
    {
        System.out.println("Connected");
    }

    @Subscribe
    public void onDisconnect(GuildedWebSocketClosedEvent e)
    {
        System.out.println("Disconnected");
    }

    @Subscribe
    public void onUnknownGuildedEvent(UnknownGuildedEvent e)
    {
        System.err.println("===== Unknown Guilded event =====\nRaw: " + e.getRawString() + "\nReason: " + e.getReason());
        if(e.getReason() != null) e.getReason().printStackTrace();
    }

    @Subscribe
    public void onGuildedEvent(GuildedEvent e)
    {
        lastMessageId = e.getEventID();
    }

    @Subscribe
    public void onCalenderEventCommentCreated(CalendarEventCommentCreatedEvent e)
    {
        System.out.println("created\n" + e.getCalendarEventComment());
    }

    @Subscribe
    public void onCalenderEventCommentUpdated(CalendarEventCommentUpdatedEvent e)
    {
        System.out.println("updated\n" + e.getCalendarEventComment());
    }

    @Subscribe
    public void onCalenderEventCommentDeleted(CalendarEventCommentDeletedEvent e)
    {
        System.out.println("deleted\n" + e.getCalendarEventComment());
    }
}
