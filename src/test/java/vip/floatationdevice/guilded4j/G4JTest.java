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
        G4JWebSocketClient ws = new G4JWebSocketClient(session.savedToken);
        ws.toggleDump();
        ws.connect();
    }
}
