/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;

/**
 * Represents a comment of a doc.<br>
 * <a href="https://www.guilded.gg/docs/api/docComments/DocComment" target=_blank>https://www.guilded.gg/docs/api/docComments/DocComment</a>
 */
public class DocComment
{
    private int id;
    private int docId;
    private String channelId;
    private String content;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private Mentions mentions;

    public static DocComment fromJSON(JSONObject json)
    {
        DocComment dc = new DocComment();
        dc.id = json.getInt("id");
        dc.docId = json.getInt("docId");
        dc.channelId = json.getStr("channelId");
        dc.content = json.getStr("content");
        dc.createdAt = json.getStr("createdAt");
        dc.createdBy = json.getStr("createdBy");
        dc.updatedAt = json.getStr("updatedAt");
        return dc;
    }

    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("docId", docId)
                .set("channelId", channelId)
                .set("content", content)
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("updatedAt", updatedAt)
                .toString();
    }

    /**
     * Get the ID of the doc comment.
     */
    public int getId(){return id;}

    /**
     * Get the ID of the doc that the comment belongs to.
     */
    public int getDocId(){return docId;}

    /**
     * Get the UUID of the channel where the comment belongs to.
     */
    public String getChannelId(){return channelId;}

    /**
     * Get the content of the comment.
     */
    public String getContent(){return content;}

    /**
     * Get the ISO 8601 timestamp that the doc comment was created at.
     */
    public String getCreatedAt(){return createdAt;}

    /**
     * Get the ID of the user who created the doc comment.
     */
    public String getCreatedBy(){return createdBy;}

    /**
     * Get the ISO 8601 timestamp that the doc comment was last updated at.
     */
    public String getUpdatedAt(){return updatedAt;}

    /**
     * (NOT IMPLEMENTED) Get the mentions of the comment.
     */
    public Mentions getMentions(){throw new UnsupportedOperationException("https://www.guilded.gg/Guilded4J-Cafe/blog/Announcements/About-the-APIs-new-Mentions-feature");}

    public DocComment setId(int id)
    {
        this.id = id;
        return this;
    }

    public DocComment setDocId(int docId)
    {
        this.docId = docId;
        return this;
    }

    public DocComment setChannelId(String channelId)
    {
        this.channelId = channelId;
        return this;
    }

    public DocComment setContent(String content)
    {
        this.content = content;
        return this;
    }

    public DocComment setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    public DocComment setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    public DocComment setUpdatedAt(String updatedAt)
    {
        this.updatedAt = updatedAt;
        return this;
    }

    public DocComment setMentions(Mentions mentions)
    {
        this.mentions = mentions;
        return this;
    }
}
