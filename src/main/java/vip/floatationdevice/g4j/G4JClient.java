package vip.floatationdevice.g4j;
import java.net.URI;
import java.util.ArrayList;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import com.google.common.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import vip.floatationdevice.g4j.event.*;

public class G4JClient extends WebSocketClient
{
    /*bruh moment*/static URI initURI(){try{return new URI("wss://api.guilded.gg/v1/websocket");}catch(Throwable e){System.out.println("\n[X] Failed to initialize Guilded bot URI!\n    How did that f**king happen? Anyway the program will exit now");System.exit(-1);return null;}}
    public static final URI WEBSOCKET_URI =initURI();
    public static final String MSG_CHANNEL_URL="https://www.guilded.gg/api/v1/channels/{channelId}/messages";
    public static String authToken="Bearer 0";
    public static EventBus bus = new EventBus();
    public G4JClient(String token)//initial function
    {
        super(WEBSOCKET_URI);
        this.setAuthToken(token);
    }
    @Override
    public void onOpen(ServerHandshake h)//when successfully logged in
    {
        System.out.println("\n[i] Connection opened");
    }
    @Override
    public void onMessage(String rawMessage)//when received a RAW String from Guilded server
    {
        JSONObject json=new JSONObject(rawMessage);
        //System.out.println(json.toStringPretty());
        String eventType=json.getStr("t");
        if(eventType!=null)
            if(eventType.equals("ChatMessageCreated"))
                bus.post(
                        new ChatMessageCreatedEvent(this,new ChatMessage().fromString(rawMessage)).setOpCode(json.getInt("op"))
                );
            else if(eventType.equals("ChatMessageUpdated"))
                bus.post(
                        new ChatMessageUpdatedEvent(this,new ChatMessage().fromString(rawMessage)).setOpCode(json.getInt("op"))
                );
            else if(eventType.equals("ChatMessageDeleted"))
                bus.post(
                        new ChatMessageDeletedEvent(this)
                        .setDeletionTime((String)json.getByPath("d.message.deletedAt"))
                        .setMsgId((String)json.getByPath("d.message.id"))
                        .setChannelId((String)json.getByPath("d.message.channelId"))
                        .setOpCode(json.getInt("op"))
                );
            else bus.post(new GuildedEvent(this).setOpCode(json.getInt("op")).setEventType(eventType).setRawString(rawMessage));
        else if(json.getInt("op")!=null)
            bus.post(new GuildedEvent(this).setOpCode(json.getInt("op")).setRawString(rawMessage));
        else bus.post(new GuildedEvent(this).setRawString(rawMessage));
    }
    public String sendMessage(String channelId, String msg)//send text message to specified channel
    {
        try
        {
            return HttpRequest.post(MSG_CHANNEL_URL.replace("{channelId}",channelId)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    header("Content-type","application/json").
                    body("{\"content\":\"$1\"}".replace("$1",msg)).
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return "{\"Exception\":\""+e.toString()+"\"}";
        }
    }
    public ChatMessage getMessage(String channelId, String msgId)//TODO: get a message from specified message UUID and channel UUID
    {
        return new ChatMessage();
    }
    public ArrayList<ChatMessage> getMessages(String channelId)//TODO: get the last 50 msgs from specified channel
    {
        return new ArrayList<ChatMessage>();
    }

    public void setAuthToken(String token)//to initialize or reset AuthToken
    {
        authToken=token;
        this.clearHeaders();
        this.addHeader("Authorization","Bearer "+authToken);
    }
    @Override
    public void onClose(int code, String reason, boolean remote)//when connection closed
    {
        System.out.println("\n[i] Connection closed " + (remote ? " by remote peer (" : "(") + code + ")\n    " + reason);
    }
    @Override
    public void onError(Exception e)
    {
        e.printStackTrace();
    }
}
