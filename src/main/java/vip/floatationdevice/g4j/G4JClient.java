package vip.floatationdevice.g4j;
import java.net.URI;
//import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.date.DateUtil;
import java.util.UUID;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import com.google.common.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import vip.floatationdevice.g4j.event.ChatMessageCreatedEvent;
import vip.floatationdevice.g4j.event.GuildedEvent;

public class G4JClient extends WebSocketClient
{
    /*bruh moment*/static URI initURI(){try{return new URI("wss://api.guilded.gg/v1/websocket");}catch(Throwable e){System.out.println("\n[X] Failed to initialize Guilded bot URI!\n    How did that f**king happen? Anyway the program will exit now");System.exit(-1);return null;}}
    public static final URI WSS_URI=initURI();
    public static final String MSG_CHANNEL_URL="https://www.guilded.gg/api/v1/channels/{channelId}/messages";
    public static String authToken="Bearer 0";
    public static EventBus bus = new EventBus();
    @Deprecated
    public G4JClient(Map<String, String> httpHeaders)//a more simple initial function has replaced this
    {
        super(WSS_URI, httpHeaders);
    }
    public G4JClient(String token)//initial function
    {
        super(WSS_URI);
        this.setAuthToken(token);
    }
    @Override
    public void onOpen(ServerHandshake h)//when successfully logged in
    {
        System.out.println("\n[i] Connection opened");
    }
    @Override
    public void onMessage(String message)//when received a RAW String from Guilded server
    {
        JSONObject json=new JSONObject(message);
        //System.out.println(json.toStringPretty());
        String eventType=json.getStr("t");
        if(eventType!=null&&eventType.equals("ChatMessageCreated"))
        {
            bus.post(
                    new ChatMessageCreatedEvent(this,new ChatMessage()
                            .setId((String)json.getByPath("d.message.id"))
                            .setType((String)json.getByPath("d.message.type"))
                            .setChannelId((String)json.getByPath("d.message.channelId"))
                            .setContent((String)json.getByPath("d.message.content"))
                            .setCreatedAt((String)json.getByPath("d.message.createdAt"))
                            .setCreatedBy((String)json.getByPath("d.message.createdBy"))
                    ).setOpCode(json.getInt("op"))
            );
        }
        else
        {
            if (eventType!=null) bus.post(new GuildedEvent(this,json.getInt("op"),eventType));
            else bus.post(new GuildedEvent(this,json.getInt("op")));
        }
    }
    public void sendMessage(String channelId, String msg)//send text message to specified channel
    {
        String content="{\"content\":\"$1\"}".replace("$1",msg);
        try
        {
            String result=HttpRequest.post(MSG_CHANNEL_URL.replace("{channelId}",channelId)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    header("Content-type","application/json").
                    body(content).
                    timeout(20000).execute().body();
            //System.out.println(new JSONObject(result).toStringPretty());
        }catch (Throwable e){System.out.print("[X] Message failed to send: "+e.toString());}
    }
    public void getMessage(UUID channelId, UUID msgId)//get a message from specified message UUID and channel UUID
    {
        ;
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
