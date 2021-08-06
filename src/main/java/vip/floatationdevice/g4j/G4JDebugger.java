package vip.floatationdevice.g4j;

import cn.hutool.core.date.DateUtil;
import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.g4j.event.*;

import java.io.*;
import java.util.*;

public class G4JDebugger
{
    static class GuildedEventListener
    {
        @Subscribe
        public void onMsg(ChatMessageCreatedEvent e)
        {
            ChatMessage m=e.getChatMessageObject();
            System.out.print("\n["+DateUtil.parse(m.getCreatedAt())+"] ["+m.getChannelId()+"] <"+m.getCreatedBy()+"> "+m.getContent()+"\n["+workdir+"] #");
        }
    }
    static class G4JSession implements Serializable
    {
        public String savedToken="Bearer 0";
        public String savedWorkdir="(init)";
        public Boolean save(String token, String workdir)
        {
            try {
                this.savedToken=token;
                this.savedWorkdir=workdir;
                ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("G4JSession.dat"));
                o.writeObject(this);
                o.close();
                return true;
            }catch(Throwable e){System.out.println("[X] Failed to save session: "+e.toString());return false;}
        }
        public Boolean restore()
        {
            try {
                ObjectInputStream i=new ObjectInputStream(new FileInputStream(new File("G4JSession.dat")));
                G4JSession session=(G4JSession)i.readObject();
                this.savedToken=session.savedToken;
                this.savedWorkdir=session.savedWorkdir;
                i.close();
                return true;
            }catch(Throwable e){System.out.println("[X] Failed to restore session: "+e.toString());return false;}
        }
    }
    final static Scanner scanner=new Scanner(System.in);
    static String workdir="(init)";
    static G4JClient client;
    public static void main(String[] args)
    {
        G4JSession session=new G4JSession();
        if(session.restore())
        {
            client=new G4JClient(session.savedToken);
            workdir=session.savedWorkdir;
            System.out.println("[i] Restoring session");
        }
        else
        {
            System.out.print("Enter AuthToken:");
            client=new G4JClient(scanner.nextLine());
            System.out.println("[i] Logging in");
        }
        client.connect();
        String text;//typed command
        String textCache="";//previous command
        G4JClient.bus.register(new GuildedEventListener());
        for(;;System.out.print("\n["+workdir+"] #"))
        {
            text=scanner.nextLine();
            if(text.equals("!!")){text=textCache;}
            if(text.equals("test")){client.sendMessage("1166f4e4-b9b2-4b98-9d70-301662403cb3","【爱发电专属Bukkit插件-全版本】TRMonitor —— 技术侦测插件，找出服务器崩溃/卡顿/内存不足/遭攻击的原因！https://www.relatev.com/forum.php?mod=viewthread&tid=2451");}
            else if(text.equals("save")){if(session.save(client.authToken,workdir)){System.out.print("[i] G4JSession saved");}}
            else if(text.startsWith("token ")&&text.length()>6){System.out.print("[i] Updated AuthToken");client.setAuthToken(text.substring(6));}
            else if(text.equals("disconnect")){System.out.print("[i] Disconnecting");client.close();}
            else if(text.equals("pwd")){System.out.print("[i] Currently in channel: "+workdir);}
            else if(text.startsWith("cd ")&&text.length()>3){System.out.print("[i] Change target channel to "+text.substring(3));workdir=text.substring(3);}
            else if(text.startsWith("send ")&&text.length()>5)
            {
                if(workdir.equals("(init)")) System.out.print("[X] Specify a channel UUID first");
                else client.sendMessage(workdir,text.substring(5));
            }
            else if(text.equals("reconnect")){System.out.print("[i] Reconnecting");client.reconnect();}
            else if(text.equals("exit")){System.out.println("[i] Exiting");client.close();session.save(client.authToken,workdir);break;}
            else if(text.equals("help"))
            {
                System.out.print(
                        "COMMANDS:\n" +
                        " > token <AuthToken>\n" +
                        "    Update AuthToken\n" +
                        " > disconnect\n" +
                        " > reconnect\n" +
                        " > pwd\n" +
                        "    Print the current channel UUID\n" +
                        " > cd <UUID>\n" +
                        "    Change the target channel UUID\n" +
                        " > send <string>\n" +
                        "    Send the typed string\n" +
                        " > exit\n" +
                        "    Log out and exit"
                );
            }
            else{System.out.print("[!] Type 'help' to get available commands and usages");}
            textCache=text;
        }
    }
}
