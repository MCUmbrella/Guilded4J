package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.misc.ChatMessageQuery;
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
        CalendarEvent[] events = manager.getCalendarEvents(s.savedChannelId, new ChatMessageQuery().includePrivate().limit(100));
        for (CalendarEvent event : events)
        {
            System.out.println(event.toString());
        }
    }
}
