package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.CalendarEvent;
import vip.floatationdevice.guilded4j.rest.CalendarEventManager;

import java.util.Calendar;

/**
 * Some temporary test code will go here.
 */
public class G4JTest
{
    public static void main(String[] args) throws Exception
    {
        G4JDebugger.G4JSession s = new G4JDebugger.G4JSession();
        s.restore();
        System.out.println("CALENDAR EVENT MANAGER TEST START\nTesting getCalendarEvents");
        CalendarEventManager manager = new CalendarEventManager(s.savedToken);
        CalendarEvent[] events = manager.getCalendarEvents(s.savedChannelId);
        System.out.println("Existing events: " + events.length);
        for (CalendarEvent event : events) System.out.println("  - " + event.getName());
        System.out.println("Testing createCalendarEvent");
        CalendarEvent ce = manager.createCalendarEvent(
                s.savedChannelId,
                "Test Event " + System.currentTimeMillis(),
                "This is a test event.",
                "Test Location",
                Util.calendarToIso8601(Calendar.getInstance()),
                "https://www.guilded.gg/",
                0xabcdef,
                1,
                null
        );
        System.out.println(new JSONObject(ce.toString()).toStringPretty());
        System.out.println("Getting events again");
        events = manager.getCalendarEvents(s.savedChannelId);
        System.out.println("Existing events: " + events.length);
        for (CalendarEvent event : events) System.out.println("  - " + event.getName());
        System.out.println("Testing updateCalendarEvent");
        CalendarEvent ce2 = manager.updateCalendarEvent(
                s.savedChannelId,
                ce.getId(),
                "Updated Test Event " + System.currentTimeMillis(),
                null,
                null,
                null,
                null,
                null,
                null
        );
        System.out.println(new JSONObject(ce2.toString()).toStringPretty());
        System.out.println("Getting events again");
        events = manager.getCalendarEvents(s.savedChannelId);
        System.out.println("Existing events: " + events.length);
        for (CalendarEvent event : events) System.out.println("  - " + event.getName());
        System.out.println("Press ENTER to continue (you may need to refresh the page to see the changes)");
        new java.util.Scanner(System.in).nextLine();
        System.out.println("Testing getCalendarEvent");
        System.out.println(new JSONObject(manager.getCalendarEvent(s.savedChannelId, ce.getId()).toString()).toStringPretty());
        System.out.println("Testing deleteCalendarEvent");
        manager.deleteCalendarEvent(s.savedChannelId, ce2.getId());
        System.out.println("Event deleted\nGetting events again");
        events = manager.getCalendarEvents(s.savedChannelId);
        System.out.println("Existing events: " + events.length);
        for (CalendarEvent event : events) System.out.println("  - " + event.getName());
        System.out.println("CALENDAR EVENT MANAGER TEST END");
    }
}
