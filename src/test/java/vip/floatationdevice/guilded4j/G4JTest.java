package vip.floatationdevice.guilded4j;

import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.object.CalendarEventRsvp;
import vip.floatationdevice.guilded4j.object.ForumTopic;
import vip.floatationdevice.guilded4j.object.ForumTopicSummary;
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
        //c.ws.connect();
        int i = c.getXPManager().setUserXp(s.savedServerId, "8412wg5d", 1);
        System.out.println(i);
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
}
