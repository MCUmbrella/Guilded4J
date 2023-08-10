package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONObject;
import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.object.*;
import vip.floatationdevice.guilded4j.rest.*;

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
        c = new G4JClient(s.savedToken).setVerbose(true).setAutoReconnect(true);
        //c.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 9910)));
        c.registerEventListener(new G4JTest()).connectWebSocket(true, null);
        //==============================================================
        sc.nextLine();
        DocManager dm = c.getDocManager();
        Doc d = dm.createDoc(s.savedChannelId, "TITLE", "CONTENT");
        System.out.println("\n====================\nTESTING: DocCommentCreate");
        DocComment dc1 = dm.createComment(d.getChannelId(), d.getId(), "DocComment 1");
        DocComment dc2 = dm.createComment(d.getChannelId(), d.getId(), "DocComment 2");
        System.out.println("\n====================\nTESTING: DocCommentReadMany");
        for(DocComment dc : dm.getComments(d.getChannelId(), d.getId()))
            System.out.println(new JSONObject(dc.toString()).toStringPretty());
        System.out.println("\n====================\nTESTING: DocCommentUpdate");
        dc2 = dm.updateComment(dc2.getChannelId(), dc2.getDocId(), dc2.getId(), "DocComment2 updated");
        System.out.println("\n====================\nTESTING: DocCommentRead");
        System.out.println(new JSONObject(dm.getComment(dc2.getChannelId(), dc2.getDocId(), dc2.getId()).toString()).toStringPretty());
        System.out.println("\n====================\nTESTING: DocCommentDelete");
        dm.deleteComment(dc1.getChannelId(), dc1.getDocId(), dc1.getId());
        System.out.println("\n====================\nTESTING: DocCommentReadMany");
        for(DocComment dc : dm.getComments(d.getChannelId(), d.getId()))
            System.out.println(new JSONObject(dc.toString()).toStringPretty());
        dm.deleteDoc(d.getChannelId(), d.getId());
        System.out.println("\n====================\nTESTING: completed");
        System.exit(0);
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

    @Subscribe
    public void onDocCommentCreate(DocCommentCreatedEvent e)
    {
        System.out.println("DocCommentCreatedEvent:");
        System.out.println(new JSONObject(e.getDocComment().toString()).toStringPretty());
    }

    @Subscribe
    public void onDocCommentUpdate(DocCommentUpdatedEvent e)
    {
        System.out.println("DocCommentUpdatedEvent:");
        System.out.println(new JSONObject(e.getDocComment().toString()).toStringPretty());
    }

    @Subscribe
    public void onDocCommentDeleted(DocCommentDeletedEvent e)
    {
        System.out.println("DocCommentDeletedEvent:");
        System.out.println(new JSONObject(e.getDocComment().toString()).toStringPretty());
    }
}
