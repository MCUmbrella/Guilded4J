// Copyright (c) 2021 MCUmbrella & contributors

package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The basic item object in a 'list' type channel.
 * <a>https://www.guilded.gg/docs/api/listItems/ListItem</a>
 */
public class ListItem
{
    private String id, channelId, message, note, createdAt, createdBy, createdByBotId, createdByWebhookId;

    public String getId(){return id;}//TODO: get an accurate answer about the type
    @Nullable public String getChannelId(){return channelId;}//TODO: get an accurate answer on whether its optional or not
    public String getMessage(){return message;}
    @Nullable public String getNote(){return note;}
    public String getCreationTime(){return createdAt;}
    public String getCreatorId(){return createdBy;}
    @Nullable public String getBotCreatorId(){return createdByBotId;}
    @Nullable public String getWebhookCreatorId(){return createdByWebhookId;}

    public ListItem setId(@Nonnull String id){this.id=id;return this;}
    public ListItem setChannelId(String channelId){this.channelId=channelId;return this;}
    public ListItem setMessage(@Nonnull String message){this.message=message;return this;}
    public ListItem setNote(String note){this.note=note;return this;}
    public ListItem setCreationTime(@Nonnull String createdAt){this.createdAt=createdAt;return this;}
    public ListItem setCreatorId(@Nonnull String createdBy){this.createdBy=createdBy;return this;}
    public ListItem setBotCreatorId(String createdByBotId){this.createdByBotId=createdByBotId;return this;}
    public ListItem setWebhookCreatorId(String createdByWebhookId){this.createdByWebhookId=createdByWebhookId;return this;}

    public ListItem(String id, String message, String createdAt, String createdBy)
    {
        this.id=id;
        this.message=message;
        this.createdAt=createdAt;
        this.createdBy=createdBy;
    }
    public ListItem(){}

    @Nullable public ListItem fromString(String rawString)
    {
        JSONObject json=new JSONObject(rawString);
        if(JSONUtil.isJson(rawString))
        {
            if(json.getStr("id")==null||json.getStr("channelId")==null
                    ||json.getStr("createdAt")==null||json.getStr("createdBy")==null)
                throw new IllegalArgumentException("At least 1 basic key of ListItem is missing");
            return this.setId(json.getStr("id"))
                    .setChannelId(json.getStr("channelId"))
                    .setMessage(json.getStr("message"))
                    .setNote(json.getStr("note"))
                    .setCreationTime(json.getStr("createdAt"))
                    .setCreatorId(json.getStr("createdBy"))
                    .setBotCreatorId(json.getStr("createdByBotId"))
                    .setWebhookCreatorId(json.getStr("createdByWebhookId"));
        }else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }

    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id",id)
                .set("channelId",channelId)
                .set("message",message)
                .set("note",note)
                .set("createdAt",createdAt)
                .set("createdBy",createdBy)
                .set("createdByBotId",createdByBotId)
                .set("createdByWebhookId",createdByWebhookId)
                .toString();
    }
}
