package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.Util;

/**
 * The basic document object in a 'doc' type channel.<br>
 * <a href="https://www.guilded.gg/docs/api/docs/Doc" target=_blank>https://www.guilded.gg/docs/api/docs/Doc</a>
 */
public class Document
{
    int id;
    String serverId, channelId, title, content, createdAt, createdBy, updatedAt, updatedBy;

    /**
     * Get the ID of the document.
     */
    public int getId(){return id;}

    /**
     * Get the server ID of the document.
     */
    public String getServerId(){return serverId;}

    /**
     * Get the channel ID of the document.
     */
    public String getChannelId(){return channelId;}

    /**
     * Get the title of the document.
     */
    public String getTitle(){return title;}

    /**
     * Get the content of the document.
     */
    public String getContent(){return content;}

    /**
     * Get the ISO 8601 timestamp string that the doc was created at.
     */
    public String getCreationTime(){return createdAt;}

    /**
     * Get the user ID of the user who created the document.
     */
    public String getCreatorId(){return createdBy;}

    /**
     * Get the ISO 8601 timestamp string that the doc was updated at.
     * @return The timestamp string. If the document has not been updated, return {@code null}.
     */
    public String getUpdateTime(){return updatedAt;}

    /**
     * Get the user ID of the user who last updated the document.
     */
    public String getUpdatedBy(){return updatedBy;}

    public Document setId(int id){this.id=id; return this;}
    public Document setServerId(String serverId){this.serverId=serverId; return this;}
    public Document setChannelId(String channelId){this.channelId=channelId; return this;}
    public Document setTitle(String title){this.title=title; return this;}
    public Document setContent(String content){this.content=content; return this;}
    public Document setCreationTime(String createdAt){this.createdAt=createdAt; return this;}
    public Document setCreatorId(String createdBy){this.createdBy=createdBy; return this;}
    public Document setUpdateTime(String updatedAt){this.updatedAt=updatedAt; return this;}
    public Document setUpdatedBy(String updatedBy){this.updatedBy=updatedBy; return this;}

    /**
     * Generate empty Document object - make sure to set all the essential fields before using it.
     */
    public Document(){}

    /**
     * Generate Document object from JSON string.
     * @param jsonString The JSON string.
     */
    public Document(String jsonString) {fromString(jsonString);}

    /**
     * Use the given JSON string to generate Document object.
     * @param rawString The JSON string.
     * @return Document object.
     * @throws IllegalArgumentException when the essential fields are not set.
     * @throws ClassCastException when the provided String's content isn't JSON format.
     */
    public Document fromString(String rawString)
    {
        if(JSONUtil.isJson(rawString))
        {
            JSONObject json=new JSONObject(rawString);
            Util.checkNullArgument(
                    json.getStr("id"),
                    json.getStr("serverId"),
                    json.getStr("channelId"),
                    json.getStr("title"),
                    json.getStr("content"),
                    json.getStr("createdAt"),
                    json.getStr("createdBy")
            );
            return setId(json.getInt("id"))
                    .setServerId(json.getStr("serverId"))
                    .setChannelId(json.getStr("channelId"))
                    .setTitle(json.getStr("title"))
                    .setContent(json.getStr("content"))
                    .setCreationTime(json.getStr("createdAt"))
                    .setCreatorId(json.getStr("createdBy"))
                    .setUpdateTime(json.getStr("updatedAt"))
                    .setUpdatedBy(json.getStr("updatedBy"));
        }
        else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }

    /**
     * Convert the ForumThread object to JSON string.
     * @return A JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id",id)
                .set("serverId",serverId)
                .set("channelId",channelId)
                .set("title",title)
                .set("content",content)
                .set("createdAt",createdAt)
                .set("createdBy",createdBy)
                .set("updatedAt",updatedAt)
                .set("updatedBy",updatedBy)
                .toString();
    }
}
