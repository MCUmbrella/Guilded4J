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
        c.ws.connect();
        Thread.sleep(10000);
        ForumTopic f = c.getForumManager().createForumTopic("6284cada-9d78-4941-803a-3bc38e3de9aa", "title", "**content**");
        Thread.sleep(5000);
        c.getForumManager().updateForumTopic("6284cada-9d78-4941-803a-3bc38e3de9aa", f.getId(), "title2", "_content2_");
        Thread.sleep(5000);
        c.getForumManager().getForumTopic("6284cada-9d78-4941-803a-3bc38e3de9aa", f.getId());
        Thread.sleep(5000);
        ForumTopicSummary[] fs = c.getForumManager().getForumTopics("6284cada-9d78-4941-803a-3bc38e3de9aa", null);
        for(ForumTopicSummary fts : fs)
            System.out.println(fts.getId()+"\n"+fts.getTitle());
        Thread.sleep(10000);
        c.getForumManager().deleteForumTopic("6284cada-9d78-4941-803a-3bc38e3de9aa", f.getId());
        System.out.println("OK");
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
    public void onForumTopicCreatedEvent(ForumTopicCreatedEvent e)
    {
        System.out.println("ForumTopicCreatedEvent: " + e.getForumTopic().toString());
    }

    @Subscribe
    public void onForumTopicUpdatedEvent(ForumTopicUpdatedEvent e)
    {
        System.out.println("ForumTopicUpdatedEvent: " + e.getForumTopic().toString());
    }

    @Subscribe
    public void onForumTopicDeletedEvent(ForumTopicDeletedEvent e)
    {
        System.out.println("ForumTopicDeletedEvent: " + e.getForumTopic().toString());
    }
}
