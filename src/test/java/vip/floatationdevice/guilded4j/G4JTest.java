package vip.floatationdevice.guilded4j;

import cn.hutool.json.*;
import vip.floatationdevice.guilded4j.object.*;

public class G4JTest
{
    /**
     * Some temporary test code will go here.
     */
    public static void main(String[] args)
    {
        G4JDebugger.G4JSession session = new G4JDebugger.G4JSession();
        session.restore();
        //G4JWebSocketClient ws = new G4JWebSocketClient(session.savedToken);
        //ws.toggleDump();
        //ws.connect();
        ChatMessage e = ChatMessage.fromString("{\n" +
                "    \"id\": \"00000000-0000-0000-0000-000000000000\",\n" +
                "    \"type\": \"default\",\n" +
                "    \"serverId\": \"wlVr3Ggl\",\n" +
                "    \"channelId\": \"00000000-0000-0000-0000-000000000000\",\n" +
                "    \"content\": \"Hello **world**!\",\n" +
                "    \"embeds\": [\n" +
                "      {\n" +
                "        \"title\": \"embed title\",\n" +
                "        \"description\": \"embeds support a **different** __subset__ *of* markdown than other markdown fields. <@Ann6LewA>\\n\\n [links](https://www.guilded.gg) ```\\ncheck this code out```\\n\\n:pizza: time!! ttyl\",\n" +
                "        \"url\": \"https://www.guilded.gg\",\n" +
                "        \"color\": 6118369,\n" +
                "        \"timestamp\": \"2022-04-12T22:14:36.737Z\",\n" +
                "        \"footer\": {\n" +
                "          \"icon_url\": \"https://www.guilded.gg/asset/Logos/logomark/Color/Guilded_Logomark_Color.png\",\n" +
                "          \"text\": \"footer text\"\n" +
                "        },\n" +
                "        \"thumbnail\": {\n" +
                "          \"url\": \"https://www.guilded.gg/asset/Logos/logomark/Color/Guilded_Logomark_Color.png\"\n" +
                "        },\n" +
                "        \"image\": {\n" +
                "          \"url\": \"https://www.guilded.gg/asset/Logos/logomark_wordmark/Color/Guilded_Logomark_Wordmark_Color.png\"\n" +
                "        },\n" +
                "        \"author\": {\n" +
                "          \"name\": \"Gil\",\n" +
                "          \"url\": \"https://www.guilded.gg\",\n" +
                "          \"icon_url\": \"https://www.guilded.gg/asset/Default/Gil-md.png\"\n" +
                "        },\n" +
                "        \"fields\": [\n" +
                "          {\n" +
                "            \"name\": \"hello\",\n" +
                "            \"value\": \"these are fields\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"~~help i have been crossed out~~\",\n" +
                "            \"value\": \"~~oh noes~~\",\n" +
                "            \"inline\": true\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"another inline\",\n" +
                "            \"value\": \"field\",\n" +
                "            \"inline\": true\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ],\n" +
                "    \"createdAt\": \"2021-06-15T20:15:00.706Z\",\n" +
                "    \"createdBy\": \"Ann6LewA\"\n" +
                "  }");
        System.out.println(new JSONObject(e.toString()).toStringPretty());
    }
}
