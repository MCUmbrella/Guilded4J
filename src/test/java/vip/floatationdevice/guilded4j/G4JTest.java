package vip.floatationdevice.guilded4j;

import vip.floatationdevice.guilded4j.misc.ChatMessageQuery;
import vip.floatationdevice.guilded4j.object.ChatMessage;
import vip.floatationdevice.guilded4j.rest.ChatMessageManager;

import java.util.Calendar;

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
        Calendar after = Calendar.getInstance();
        after.set(Calendar.HOUR_OF_DAY, 5);
        after.set(Calendar.MINUTE, 30);
        System.out.println(after.getTime() + " -> " + calendarToIso8601(after));
        ChatMessage[] msgs = new ChatMessageManager(session.savedToken)
                .getChannelMessages(
                        session.savedChannelId,
                        new ChatMessageQuery()
                                .includePrivate()
                                .after(calendarToIso8601(after))
                );
        for(int i = 0; i != msgs.length; i++)
            System.out.println(msgs[i].getCreationTime() + " -> " + iso8601ToCalendar(msgs[i].getCreationTime()).getTime() + ": " + msgs[i].getContent() + "\t" + msgs[i]);
    }
}
