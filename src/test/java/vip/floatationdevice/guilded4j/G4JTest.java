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
    public static void main(String[] args)
    {
        G4JDebugger.G4JSession s = new G4JDebugger.G4JSession();
        s.restore();
        CalendarEvent ce = new CalendarEventManager(s.savedToken).createCalendarEvent(
                s.savedChannelId,
                "Test Event",
                "This is a test event.",
                null,
                Util.calendarToIso8601(Calendar.getInstance()),
                null,
                null,
                1,
                null
        );
        System.out.println(new JSONObject(ce.toString()).toStringPretty());
    }
}
