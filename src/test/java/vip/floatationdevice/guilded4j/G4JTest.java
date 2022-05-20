package vip.floatationdevice.guilded4j;

import vip.floatationdevice.guilded4j.object.ChatMessage;
import vip.floatationdevice.guilded4j.rest.ChatMessageManager;

import java.util.Calendar;
import java.util.UUID;

import static vip.floatationdevice.guilded4j.Util.*;

/**
 * Some temporary test code will go here.
 */
public class G4JTest
{
    public static void main(String[] args)
    {
        G4JDebugger.G4JSession session = new G4JDebugger.G4JSession();
        session.restore();
        String iso = "2022-05-20T06:29:31.508Z";
        System.out.println(iso);
        Calendar c = iso8601ToCalendar(iso);
        System.out.println(dateToIso8601(c));
        System.out.println(c.getTimeZone().getDisplayName());
        System.out.println(c.getTime());
        System.out.println(isUUID(UUID.randomUUID()));
        ChatMessage[] msgs = new ChatMessageManager(session.savedToken).getChannelMessages(session.savedChannelId);
        for (ChatMessage msg : msgs)
            System.out.println(iso8601ToCalendar(msg.getCreationTime()).getTime());
    }
}
