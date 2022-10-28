/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.ForumTopicComment;

/**
 * Event fired when a forum comment is updated.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ForumTopicCommentUpdated" target=_blank>https://www.guilded.gg/docs/api/websockets/ForumTopicCommentUpdated</a>
 */
public class ForumTopicCommentUpdatedEvent extends GuildedEvent
{
    private final ForumTopicComment forumTopicComment;

    public ForumTopicCommentUpdatedEvent(Object source, String json)
    {
        super(source, json);
        forumTopicComment = ForumTopicComment.fromJSON((JSONObject) new JSONObject(json).getByPath("d.forumTopicComment"));
    }

    /**
     * Get the ForumTopicComment object of the event.
     */
    public ForumTopicComment getForumTopicComment()
    {
        return forumTopicComment;
    }
}
