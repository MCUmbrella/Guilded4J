package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONObject;
import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.misc.*;
import vip.floatationdevice.guilded4j.object.*;

import java.net.InetSocketAddress;
import java.net.Proxy;
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
        c = new G4JClient(s.savedToken).setVerbose(false).setAutoReconnect(true);
        //c.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 59909)));
        c.registerEventListener(new G4JTest()).connectWebSocket(true, null);
        System.out.println("forum comment test start in 5s");
        Thread.sleep(5000);
        System.out.println("create test forum topic");
        ForumTopic topic = c.getForumManager().createForumTopic(s.savedChannelId, "test", "testtest");
        System.out.println(topic);
        Thread.sleep(10000L);
        System.out.println("create 3 comments");
        System.out.println(c.getForumManager().createForumTopicComment(s.savedChannelId, topic.getId(), "comment1"));
        System.out.println(c.getForumManager().createForumTopicComment(s.savedChannelId, topic.getId(), "comment2"));
        ForumTopicComment comment3 = c.getForumManager().createForumTopicComment(s.savedChannelId, topic.getId(), "comment3");
        System.out.println(comment3);
        Thread.sleep(10000L);
        System.out.println("list comments:");
        for(ForumTopicComment co : c.getForumManager().getForumTopicComments(s.savedChannelId, topic.getId()))
            System.out.println(co);
        Thread.sleep(10000L);
        System.out.println("update comment3");
        System.out.println(c.getForumManager().updateForumTopicComment(s.savedChannelId, topic.getId(), comment3.getId(), "comment3 updated"));
        Thread.sleep(10000L);
        System.out.println("get comment3");
        System.out.println(c.getForumManager().getForumTopicComment(s.savedChannelId, topic.getId(), comment3.getId()));
        Thread.sleep(10000L);
        System.out.println("delete comment3");
        c.getForumManager().deleteForumTopicComment(s.savedChannelId, topic.getId(), comment3.getId());
        Thread.sleep(10000L);
        System.out.println("list comments:");
        for(ForumTopicComment co : c.getForumManager().getForumTopicComments(s.savedChannelId, topic.getId()))
            System.out.println(co);
        Thread.sleep(10000L);
        System.out.println("delete test forum topic");
        c.getForumManager().deleteForumTopic(s.savedChannelId, topic.getId());
        System.out.println("completed. exit in 10s");
        Thread.sleep(10000L);
        System.exit(0);
        //==============================================================
    }

    @Subscribe
    public void onForumTopicCommentCreatedEvent(ForumTopicCommentCreatedEvent e)
    {
        System.out.println("ForumTopicCommentCreatedEvent: " + e.getForumTopicComment().toString());
    }

    @Subscribe
    public void onForumTopicCommentUpdatedEvent(ForumTopicCommentUpdatedEvent e)
    {
        System.out.println("ForumTopicCommentUpdatedEvent: " + e.getForumTopicComment().toString());
    }

    @Subscribe
    public void onForumTopicCommentDeletedEvent(ForumTopicCommentDeletedEvent e)
    {
        System.out.println("ForumTopicCommentDeletedEvent: " + e.getForumTopicComment().toString());
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
}
