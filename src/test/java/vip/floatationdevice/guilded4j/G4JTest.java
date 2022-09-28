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
        //c.ws.setHeartbeatInterval(20000);
        c.ws.connect();

        new Thread()
        {
            int i = 0;
            @Override
            public void run()
            {
                try
                {
                    for(;;)
                    {
                        i++;
                        System.out.println(i);
                        sleep(1000);
                    }
                }
                catch(InterruptedException e){}
            }
        }.start();
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
        System.out.println("Disconnected from Guilded WebSocket server.\nCode: "+e.getCode()+", reason: "+e.getReason());
    }

    @Subscribe
    public void onUnknownGuildedEvent(UnknownGuildedEvent e)
    {
        System.err.println("===== Unknown Guilded event =====\nRaw: " + e.getRawString() + "\nReason: " + e.getReason());
        if(e.getReason() != null) e.getReason().printStackTrace();
    }
}
