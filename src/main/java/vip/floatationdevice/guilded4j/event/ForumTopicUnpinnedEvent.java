/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.ForumTopic;

/**
 * Event fired when a forum topic gets unpinned.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ForumTopicUnpinned" target=_blank>https://www.guilded.gg/docs/api/websockets/ForumTopicUnpinned</a>
 */
public class ForumTopicUnpinnedEvent extends GuildedEvent
{
    private final ForumTopic forumTopic;

    public ForumTopicUnpinnedEvent(Object source, String json)
    {
        super(source, json);
        this.forumTopic = ForumTopic.fromJSON((JSONObject) new JSONObject(json).getByPath("d.forumTopic"));
    }

    /**
     * Get the ForumTopic object of the event.
     */
    public ForumTopic getForumTopic(){return forumTopic;}
}
