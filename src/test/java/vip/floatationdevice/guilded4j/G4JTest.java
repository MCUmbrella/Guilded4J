package vip.floatationdevice.guilded4j;

public class G4JTest
{
    /**
     * Some temporary test code will go here.
     */
    public static void main(String[] args)
    {
        G4JDebugger.G4JSession session = new G4JDebugger.G4JSession();
        session.restore();
        new G4JClient(session.savedToken).getChatMessageManager().createChannelMessage(session.savedChannelId, "Hello, world!", null, null, null, null);
    }
}
