// Copyright (c) 2021 MCUmbrella & contributors

package vip.floatationdevice.guilded4j;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.*;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class G4JDebugger
{
    final static String helpText="COMMANDS:\n" +
            " > token <AuthToken>\n" +
            "    Update AuthToken\n" +
            " > disconnect\n" +
            " > reconnect\n" +
            " > dump\n" +
            "    Toggle dump command result & WebSocket events\n" +
            " > pwd\n" +
            "    Print the current channel UUID\n" +
            " > cd [UUID]\n" +
            "    Change/clear the target channel UUID\n" +
            " > send <string>\n" +
            "    Send the typed string\n" +
            " > delete <UUID>\n"+
            "    Delete a message with specified UUID\n"+
            " > update <UUID> <string>\n"+
            "    Update a message with specified UUID\n"+
            " > get <UUID>\n"+
            "    Get the raw message object string from specified UUID\n"+
            " > newitem <string>\n"+
            "    Create a list item\n"+
            " > addxp <(string)userId> <(int)amount>\n"+
            "    Add XP to specified user\n"+
            " > addrolexp <(int)roleId> <(int)amount>\n"+
            "    Add XP to all users with specified role"+
            " > react <(?)contentId> <(int)emoteId>\n"+
            "    Add a reaction to specified content\n"+
            " > exit\n" +
            "    Log out and exit";
    static Boolean dumpEnabled=false;
    static String parseMessage(ChatMessage m)
    {
        return "\n["+DateUtil.parse(m.getCreationTime())+"] ["+m.getChannelId()+"] ("+m.getMsgId()+") <"+m.getCreatorId()+"> "+m.getContent()+"\n["+workdir+"] #";
    }
    static class GuildedEventListener
    {
        @Subscribe
        public void onInit(GuildedWebsocketInitializedEvent e)
        {
            System.out.print("[i] Logged in (last message ID: "+e.getLastMessageId()+", heartbeat: "+e.getHeartbeatInterval()+"ms)"+"\n["+workdir+"] #");
        }
        @Subscribe
        public void onDisconnect(GuildedWebsocketClosedEvent e)
        {
            System.out.print("\n[i] Connection closed " + (e.isRemote() ? "by remote peer (" : "(") + e.getCode() + ")\n    " + e.getReason());
        }
        @Subscribe
        public void onMsg(ChatMessageCreatedEvent e)
        {
            ChatMessage m=e.getChatMessageObject();
            System.out.print(parseMessage(m));
        }
    }
    static class G4JSession implements Serializable
    {
        public String savedToken="";
        public String savedWorkdir="";
        public Boolean save()
        {
            try {
                this.savedToken=client.authToken;
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
            System.out.print("Enter AuthToken: ");
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
            if(text.equals("save")){if(session.save()){System.out.print("[i] G4JSession saved");}}
            else if(text.equals("dump"))
            {
                dumpEnabled=!dumpEnabled;
                client.toggleDump();
            }
            else if(text.startsWith("token ")&&text.length()>6){System.out.print("[i] Updated AuthToken");client.setAuthToken(text.substring(6));}
            else if(text.equals("disconnect")){System.out.print("[i] Disconnecting");client.close();}
            else if(text.equals("pwd")){System.out.print("[i] Currently in channel: "+workdir);}
            else if(text.startsWith("cd ")&&text.length()==39){System.out.print("[i] Change target channel to "+text.substring(3));workdir=text.substring(3);}
            else if(text.equals("cd")){System.out.print("[i] Clear target channel");workdir="(init)";}
            else if(text.equals("ls"))
            {
                if(workdir.length()!=36) System.out.print("[X] Specify a channel UUID first");
                else {
                    ArrayList<ChatMessage> msgs=client.getChannelMessages(workdir);
                    ChatMessage m;
                    Collections.reverse(msgs);
                    for (int i=0;i!=msgs.size();i++)
                    {
                        m=msgs.get(i);
                        System.out.print("\n["+DateUtil.parse(m.getCreationTime())+"] ["+m.getChannelId()+"] ("+m.getMsgId()+") <"+m.getCreatorId()+"> "+m.getContent());
                    }
                }
            }
            else if(text.startsWith("send ")&&text.length()>5)
            {
                if(workdir.length()!=36) System.out.print("[X] Specify a channel UUID first");
                else{
                    String result=client.createChannelMessage(workdir,text.substring(5));
                    if(dumpEnabled) System.out.print("\n[D] Result:\n"+new JSONObject(result).toStringPretty());
                }
            }
            else if(text.startsWith("delete ")&&text.length()==43)
            {
                if(workdir.length()!=36) System.out.print("[X] Specify a channel UUID first");
                else{
                    String result=client.deleteChannelMessage(workdir,text.substring(7));
                    if(dumpEnabled)
                        if(result.startsWith("{")&&result.endsWith("}")) System.out.print("\n[D] Result:\n"+new JSONObject(result).toStringPretty());
                        else System.out.print("\n[D] Result:\n"+result);
                }
            }
            else if(text.startsWith("update ")&&text.length()>44)
            {
                if(workdir.length()!=36) System.out.print("[X] Specify a channel UUID first");
                else{
                    String result=client.updateChannelMessage(workdir,text.substring(7,43),text.substring(44));
                    if(dumpEnabled) System.out.print("\n[D] Result:\n"+new JSONObject(result).toStringPretty());
                }
            }
            else if(text.startsWith("get ")&&text.length()==40){System.out.print(new JSONObject(client.getMessage(workdir,text.substring(4))).toStringPretty());}
            else if(text.startsWith("newitem ")&&text.length()>8)
            {
                if(workdir.length()!=36) System.out.print("[X] Specify a list channel UUID first");
                else{
                    String result=client.createListItem(workdir,text.substring(8),null);
                    if(dumpEnabled) System.out.print("\n[D] Result:\n"+new JSONObject(result).toStringPretty());
                }
            }
            else if(text.startsWith("addxp ")&&text.length()>15)
            {
                String[] parsed=text.split(" ");
                if(parsed.length==3&&parsed[1].length()==8&&Pattern.compile("[0-9]*").matcher(parsed[2]).matches())
                {
                    String result=client.awardUserXp(parsed[1],Integer.parseInt(parsed[2]));
                    if(dumpEnabled) System.out.print("\n[D] Result:\n"+new JSONObject(result).toStringPretty());
                }
                else System.out.print("[X] Usage: addxp <(string)userId> <(int)amount>");
            }
            else if(text.startsWith("addrolexp ")&&text.length()>12)
            {
                String[] parsed=text.split(" ");
                if(parsed.length==3&&Pattern.compile("[0-9]*").matcher(parsed[1]).matches()&&Pattern.compile("[0-9]*").matcher(parsed[2]).matches())
                {
                    String result=client.awardRoleXp(Integer.parseInt(parsed[1]),Integer.parseInt(parsed[2]));
                    if(dumpEnabled)
                        if(result.startsWith("{")&&result.endsWith("}")) System.out.print("\n[D] Result:\n"+new JSONObject(result).toStringPretty());
                        else System.out.print("\n[D] Result:\n"+result);
                }
                else System.out.print("[X] Usage: addrolexp <(int)roleId> <(int)amount>");
            }
            else if(text.startsWith("react ")&&text.length()>8)
            {
                if(workdir.length()!=36) System.out.print("[X] Specify a list channel UUID first");
                String[] parsed=text.split(" ");
                if(parsed.length==3&&Pattern.compile("[0-9]*").matcher(parsed[2]).matches())
                {
                    String result=client.createContentReaction(workdir,parsed[1],Integer.parseInt(parsed[2]));
                    if(dumpEnabled)
                        System.out.print("\n[D] Result:\n"+new JSONObject(result).toStringPretty());
                }
                else System.out.print("[X] Usage: react <contentId> <(int)emoteId>");
            }
            else if(text.equals("reconnect")){System.out.print("[i] Reconnecting");if(client.isClosed()){client.connect();}else client.reconnect();}
            else if(text.equals("exit")){System.out.println("[i] Exiting");client.close();session.save();break;}
            else if(text.equals("help")) System.out.print(helpText);
            else{System.out.print("[!] Type 'help' to get available commands and usages");}
            textCache=text;
        }
    }
}
