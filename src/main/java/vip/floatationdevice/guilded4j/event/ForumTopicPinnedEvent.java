/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.ForumTopic;

/**
 * Event fired when a forum topic gets pinned.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ForumTopicPinned" target=_blank>https://www.guilded.gg/docs/api/websockets/ForumTopicPinned</a>
 */
public class ForumTopicPinnedEvent extends GuildedEvent
{
    private final ForumTopic forumTopic;

    public ForumTopicPinnedEvent(Object source, String json)
    {
        super(source, json);
        this.forumTopic = ForumTopic.fromJSON((JSONObject) new JSONObject(json).getByPath("d.forumTopic"));
    }

    /**
     * Get the ForumTopic object of the event.
     */
    public ForumTopic getForumTopic(){return forumTopic;}
}
