/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;

/**
 * This class represents a note in a ListItem object.
 * This class is not present in the official API.
 */
public class ListItemNote
{
    private String createdAt, createdBy, updatedAt, updatedBy;
    private String content;

    public String getCreationTime(){return createdAt;}

    public String getCreatorId(){return createdBy;}

    public String getUpdateTime(){return updatedAt;}

    public String getUpdaterId(){return updatedBy;}

    /**
     * Get the displayable content of the note.
     * @return The note's text.
     */
    public String getContent(){return content;}

    public ListItemNote setContent(String content)
    {
        this.content = content;
        return this;
    }

    public ListItemNote setCreationTime(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    public ListItemNote setCreatorId(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    public ListItemNote setUpdateTime(String updatedAt)
    {
        this.updatedAt = updatedAt;
        return this;
    }

    public ListItemNote setUpdaterId(String updatedBy)
    {
        this.updatedBy = updatedBy;
        return this;
    }

    public static ListItemNote fromJSON(JSONObject json)
    {
        return new ListItemNote()
                .setContent(json.getStr("content"))
                .setCreationTime(json.getStr("createdAt"))
                .setCreatorId(json.getStr("createdBy"))
                .setUpdateTime(json.getStr("updatedAt"))
                .setUpdaterId(json.getStr("updatedBy"));
    }

    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("updatedAt", updatedAt)
                .set("updatedBy", updatedBy)
                .set("content", content)
                .toString();
    }
}
