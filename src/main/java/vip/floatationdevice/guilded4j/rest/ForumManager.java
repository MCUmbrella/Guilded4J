package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.ForumTopic;
import vip.floatationdevice.guilded4j.object.ForumTopicSummary;

import static vip.floatationdevice.guilded4j.G4JClient.FORUM_CHANNEL_URL;

/**
 * Manages the forum threads.
 */
public class ForumManager extends RestManager
{
    public ForumManager(String authToken)
    {
        super(authToken);
    }

    /**
     * Create a topic in a forum.<br>
     * <a href="https://www.guilded.gg/docs/api/forums/ForumTopicCreate" target=_blank>https://www.guilded.gg/docs/api/forums/ForumTopicCreate</a>
     * @param title The title of the topic.
     * @param content The content of the topic.
     * @return The newly created topic's ForumTopic object.
     */
    public ForumTopic createForumTopic(String channelId, String title, String content)
    {
        return ForumTopic.fromJSON(
                execute(Method.POST,
                        FORUM_CHANNEL_URL.replace("{channelId}", channelId),
                        new JSONObject().set("title", title).set("content", content)
                ).getJSONObject("forumTopic")
        );
    }

    public ForumTopic updateForumTopic(String channelId, int forumTopicId, String title, String content) //TODO
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void deleteForumTopic(String channelId, int forumTopicId) //TODO
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public ForumTopic getForumTopic(String channelId, int forumTopicId) //TODO
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    public ForumTopicSummary getForumTopics(String channelId) //TODO
    {
        throw new UnsupportedOperationException("Not implemented");
    }
}
