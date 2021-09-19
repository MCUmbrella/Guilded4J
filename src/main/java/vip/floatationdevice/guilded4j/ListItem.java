package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ListItem// https://www.guilded.gg/docs/api/listItems/ListItem
{
    String id, message, note, createdAt, createdBy, createdByBotId, createdByWebhookId;

    public String getId(){return id;}
    @Nullable public String getMessage(){return message;}
    @Nullable public String getNote(){return note;}
    public String getCreationTime(){return createdAt;}
    public String getCreatorId(){return createdBy;}
    @Nullable public String getBotCreatorId(){return createdByBotId;}
    @Nullable public String getWebhookCreatorId(){return createdByWebhookId;}

    public ListItem setId(@Nonnull String id){this.id=id;return this;}
    public ListItem setMessage(String message){this.message=message;return this;}
    public ListItem setNote(String note){this.note=note;return this;}
    public ListItem setCreationTime(@Nonnull String createdAt){this.createdAt=createdAt;return this;}
    public ListItem setCreatorId(@Nonnull String createdBy){this.createdBy=createdBy;return this;}
    public ListItem setBotCreatorId(String createdByBotId){this.createdByBotId=createdByBotId;return this;}
    public ListItem setWebhookCreatorId(String createdByWebhookId){this.createdByWebhookId=createdByWebhookId;return this;}

    public ListItem(String id, String createdAt, String createdBy)
    {
        this.setId(id)
                .setCreationTime(createdAt)
                .setCreatorId(createdBy);
    }
    public ListItem(){}

    @Nullable public ListItem fromString(String rawString)
    {
        JSONObject json=new JSONObject(rawString);
        if(json.getStr("id")==null||json.getStr("createdAt")==null||json.getStr("createdBy")==null)
            throw new IllegalArgumentException("At least 1 basic key of ListItem is missing");
        return this.setId(json.getStr("id"))
                .setMessage(json.getStr("message"))
                .setNote(json.getStr("note"))
                .setCreationTime(json.getStr("createdAt"))
                .setCreatorId(json.getStr("createdBy"))
                .setBotCreatorId(json.getStr("createdByBotId"))
                .setWebhookCreatorId(json.getStr("createdByWebhookId"));
    }

    @Override
    public String toString()
    {
        StringBuilder s=new StringBuilder()
                .append("{\"id\":\"")
                .append(id)
                .append("\",\"createdAt\":\"")
                .append(createdAt)
                .append("\",");
        if(message!=null) s.append("\"message\":\"").append(message).append("\",");
        if(note!=null) s.append("\"note\":\"").append(note).append("\",");
        if(createdByBotId!=null) s.append("\"createdByBotId\":\"").append(createdByBotId).append("\",");
        if(createdByWebhookId!=null) s.append("\"createdByWebhookId\":\"").append(createdByWebhookId).append("\",");
        s.append("\"createdBy\":\"").append(createdBy).append("\"}");
        return s.toString();
    }
}
