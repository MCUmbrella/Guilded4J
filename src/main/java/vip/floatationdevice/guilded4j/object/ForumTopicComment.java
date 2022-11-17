/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * The comment of a forum topic.<br>
 * <a href="https://www.guilded.gg/docs/api/forumComments/ForumTopicComment" target=_blank>https://www.guilded.gg/docs/api/forumComments/ForumTopicComment</a>
 */
public class ForumTopicComment
{
    int id, forumTopicId;
    String content, createdAt, createdBy, updatedAt, channelId;
    Mention mentions;

    public static ForumTopicComment fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getInt("id"),
                json.getStr("content"),
                json.getStr("createdAt"),
                json.getStr("createdBy"),
                json.getInt("forumTopicId")
        );
        return new ForumTopicComment()
                .setId(json.getInt("id"))
                .setContent(json.getStr("content"))
                .setCreatedAt(json.getStr("createdAt"))
                .setCreatedBy(json.getStr("createdBy"))
                .setUpdatedAt(json.getStr("updatedAt"))
                .setChannelId(json.getStr("channelId"))
                .setForumTopicId(json.getInt("forumTopicId"));
    }

    /**
     * Get the ID of the forum topic comment.
     */
    public int getId(){return id;}

    public ForumTopicComment setId(int id)
    {
        this.id = id;
        return this;
    }

    /**
     * Get the ID of the forum topic that the comment belongs to.
     */
    public int getForumTopicId(){return forumTopicId;}

    public ForumTopicComment setForumTopicId(int forumTopicId)
    {
        this.forumTopicId = forumTopicId;
        return this;
    }

    /**
     * Get the content of the forum topic comment.
     */
    public String getContent(){return content;}

    public ForumTopicComment setContent(String content)
    {
        this.content = content;
        return this;
    }

    /**
     * Get the ISO 8601 timestamp that the forum topic comment was created at.
     */
    public String getCreatedAt(){return createdAt;}

    public ForumTopicComment setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Get the ID of the user who created this forum topic comment.
     */
    public String getCreatedBy(){return createdBy;}

    public ForumTopicComment setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * Get the ISO 8601 timestamp that the forum topic comment was created at.
     */
    public String getUpdatedAt(){return updatedAt;}

    public ForumTopicComment setUpdatedAt(String updatedAt)
    {
        this.updatedAt = updatedAt;
        return this;
    }

    /**
     * Get the UUID of the channel where the comment belongs to.
     */
    public String getChannelId(){return channelId;}

    public ForumTopicComment setChannelId(String channelId)
    {
        this.channelId = channelId;
        return this;
    }

    public Mention getMentions(){throw new UnsupportedOperationException("https://www.guilded.gg/Guilded4J-Cafe/blog/Announcements/About-the-APIs-new-Mentions-feature");}

    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("content", content)
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("channelId", channelId)
                .set("forumTopicId", forumTopicId)
                .set("updatedAt", updatedAt)
                .toString();
    }
}
