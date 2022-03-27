package org.example;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.TeamMemberBan;

public class G4JTestMain
{
    public static void main(String[] args)
    {
        TeamMemberBan ban = TeamMemberBan.fromString("{\n" +
                "  \"user\": {\n" +
                "    \"id\": \"Ann6LewA\",\n" +
                "    \"type\": \"user\",\n" +
                "    \"name\": \"Leopold Stotch\"\n" +
                "  },\n" +
                "  \"reason\": \"More toxic than a poison Pokémon\",\n" +
                "  \"createdAt\": \"2021-06-15T20:15:00.706Z\",\n" +
                "  \"createdBy\": \"Ann6LewA\"\n" +
                "}");
        System.out.println(
                ban.getUser().getId() + "\ntype: " +
                ban.getUser().getType() + "\nname: " +
                ban.getUser().getName() + "\nreason: " +
                ban.getReason() + "\nbanned by: " +
                ban.getCreatorId() + "\nat: " +
                ban.getCreationTime() + "\n" +
                        new JSONObject(ban.toString()).toStringPretty()
        );
    }
}
