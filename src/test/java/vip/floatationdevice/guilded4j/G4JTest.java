package vip.floatationdevice.guilded4j;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.object.Embed;
import vip.floatationdevice.guilded4j.rest.ChatMessageManager;

public class G4JTest
{
    /**
     * Some temporary test code will go here.
     */
    public static void main(String[] args)
    {
        G4JDebugger.G4JSession session = new G4JDebugger.G4JSession();
        session.restore();
        ChatMessageManager cmm = new ChatMessageManager(session.savedToken);
        boolean isSuccess = false;
        do
        {
            try
            {
                cmm.createChannelMessage(session.savedChannelId,
                        "Hello, world!",
                        new Embed[]{new Embed().setColor(114514).setTitle("Test").setDescription("Test description")},
                        null,
                        null,
                        null
                );
                isSuccess = true;
            }catch(GuildedException e)
            {
                System.err.println(e+" - "+e.getType());
                isSuccess = true;
            }catch(IORuntimeException e)
            {
                System.err.println(e);
            }
        }
        while(!isSuccess);
    }
}
