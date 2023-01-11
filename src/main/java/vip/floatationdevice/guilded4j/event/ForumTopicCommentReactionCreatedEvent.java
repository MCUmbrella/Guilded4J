/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.Emote;

/**
 * Event fired when a reaction emote is added to a forum comment.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ForumTopicCommentReactionCreated" target=_blank>https://www.guilded.gg/docs/api/websockets/ForumTopicCommentReactionCreated</a>
 */
public class ForumTopicCommentReactionCreatedEvent extends GuildedEvent
{
    private final String channelId, createdBy;
    private final int forumTopicId, forumTopicCommentId;
    private final Emote emote;

    public ForumTopicCommentReactionCreatedEvent(Object source, String json)
    {
        super(source, json);
        JSONObject j = (JSONObject) new JSONObject(json).getByPath("d.reaction");
        this.channelId = j.getStr("channelId");
        this.createdBy = j.getStr("createdBy");
        this.forumTopicId = j.getInt("forumTopicId");
        this.forumTopicCommentId = j.getInt("forumTopicCommentId");
        this.emote = Emote.fromJSON(j.getJSONObject("emote"));
    }

    /**
     * Get the UUID of the channel where the forum topic belongs to.
     */
    public String getChannelId()
    {
        return channelId;
    }

    /**
     * Get the ID of the user who added the reaction.
     */
    public String getCreatedBy()
    {
        return createdBy;
    }

    /**
     * Get the ID of the comment's parent.
     */
    public int getForumTopicId()
    {
        return forumTopicId;
    }

    /**
     * Get the ID of the comment to which the reaction was added.
     */
    public int getForumTopicCommentId()
    {
        return forumTopicCommentId;
    }

    /**
     * Get the Emote object of the reaction.
     */
    public Emote getEmote()
    {
        return emote;
    }
}
