package vip.floatationdevice.guilded4j;

import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.object.CalendarEventRsvp;
import vip.floatationdevice.guilded4j.rest.RestManager;

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
        G4JWebSocketClient ws = new G4JWebSocketClient(s.savedToken);
        ws.eventBus.register(new G4JTest());
        //ws.connect();
        System.out.println(CalendarEventRsvp.fromJSON(new JSONObject("{\n" +
                "  \"calendarEventId\": 1,\n" +
                "  \"channelId\": \"00000000-0000-0000-0000-000000000000\",\n" +
                "  \"serverId\": \"wlVr3Ggl\",\n" +
                "  \"userId\": \"Ann6LewA\",\n" +
                "  \"status\": \"going\",\n" +
                "  \"createdAt\": \"2021-06-15T20:15:00.706Z\",\n" +
                "  \"createdBy\": \"Ann6LewA\",\n" +
                "  \"updatedAt\": \"2021-06-15T20:15:00.706Z\",\n" +
                "  \"updatedBy\": \"Ann6LewA\"\n" +
                "}")));
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
    public void onReaction(ChannelMessageReactionCreatedEvent e)
    {
        System.out.println("Reaction created: " + e.getEmote().getName() + " on message " + e.getMessageId() + " in channel " + e.getChannelId() + " by " + e.getCreatedBy());
    }

    @Subscribe
    public void onReaction(ChannelMessageReactionDeletedEvent e)
    {
        System.out.println("Reaction deleted: " + e.getEmote().getName() + " on message " + e.getMessageId() + " in channel " + e.getChannelId() + " by " + e.getCreatedBy());
    }
}
