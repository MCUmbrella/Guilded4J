package vip.floatationdevice.guilded4j;

import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.misc.*;
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
        G4JClient c = new G4JClient(s.savedToken).setVerbose(false);
        c.ws.eventBus.register(new G4JTest());
        c.ws.connect();
        ForumTopic f = c.getForumManager().createForumTopic(s.savedChannelId, "HHHHHH", "H");
        System.out.println("created forum topic");
        Thread.sleep(5000);
        c.getForumManager().lockForumTopic(s.savedChannelId, f.getId());
        System.out.println("locked");
        Thread.sleep(5000);
        c.getForumManager().unlockForumTopic(s.savedChannelId, f.getId());
        System.out.println("unlocked");
        Thread.sleep(5000);
        c.getForumManager().deleteForumTopic(s.savedChannelId, f.getId());
        System.out.println("deleted");
        System.exit(0);
        //==============================================================
    }

    @Subscribe
    public void onForumTopicLockedEvent(ForumTopicLockedEvent e)
    {
        System.out.println(e.getClass().getName()+"\n"+e.getForumTopic().toString());
    }

    @Subscribe
    public void onForumTopicUnlockedEvent(ForumTopicUnlockedEvent e)
    {
        System.out.println(e.getClass().getName()+"\n"+e.getForumTopic().toString());
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
