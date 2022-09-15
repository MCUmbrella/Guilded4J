package vip.floatationdevice.guilded4j;

import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.object.*;

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
        String ch="6284cada-9d78-4941-803a-3bc38e3de9aa";
        Thread.sleep(10000);
        ForumTopic f = c.getForumManager().createForumTopic(ch, "HHHHHH", "H");
        System.out.println(f.getId());
        Thread.sleep(10000);
        c.getForumManager().pinForumTopic(ch, f.getId());
        System.out.println("PINNED");
        Thread.sleep(10000);
        c.getForumManager().unpinForumTopic(ch, f.getId());
        System.out.println("UNPINNED");
        Thread.sleep(10000);
        c.getForumManager().deleteForumTopic(ch, f.getId());
        System.out.println("DELETED");
        Thread.sleep(10000);
        System.exit(0);
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
