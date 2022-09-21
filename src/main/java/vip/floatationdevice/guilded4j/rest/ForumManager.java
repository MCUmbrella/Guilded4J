package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.misc.GObjectQuery;
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
     * @param channelId The ID of the channel to create the topic in.
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

    /**
     * Update a topic in a forum.<br>
     * <a href="https://www.guilded.gg/docs/api/forums/ForumTopicUpdate" target=_blank>https://www.guilded.gg/docs/api/forums/ForumTopicUpdate</a>
     * @param channelId The ID of the channel to update the topic in.
     * @param forumTopicId The ID of the topic.
     * @param title The title of the topic.
     * @param content The content of the topic.
     * @return The updated topic's ForumTopic object.
     */
    public ForumTopic updateForumTopic(String channelId, int forumTopicId, String title, String content)
    {
        return ForumTopic.fromJSON(
                execute(Method.PATCH,
                        FORUM_CHANNEL_URL.replace("{channelId}", channelId) + "/" + forumTopicId,
                        new JSONObject().set("title", title).set("content", content)
                ).getJSONObject("forumTopic")
        );
    }

    /**
     * Delete a forum topic.<br>
     * <a href="https://www.guilded.gg/docs/api/forums/ForumTopicDelete" target=_blank>https://www.guilded.gg/docs/api/forums/ForumTopicDelete</a>
     * @param channelId The ID of the channel to delete the topic in.
     * @param forumTopicId The ID of the topic that will be deleted.
     */
    public void deleteForumTopic(String channelId, int forumTopicId)
    {
        execute(Method.DELETE,
                FORUM_CHANNEL_URL.replace("{channelId}", channelId) + "/" + forumTopicId,
                null
        );
    }

    /**
     * Get the ForumTopic object of a forum topic.<br>
     * <a href="https://www.guilded.gg/docs/api/forums/ForumTopicRead" target=_blank>https://www.guilded.gg/docs/api/forums/ForumTopicRead</a>
     * @param channelId The ID of the channel to get the topic info from.
     * @param forumTopicId The ID of the topic.
     * @return The ForumTopic object.
     */
    public ForumTopic getForumTopic(String channelId, int forumTopicId)
    {
        return ForumTopic.fromJSON(
                execute(Method.GET,
                        FORUM_CHANNEL_URL.replace("{channelId}", channelId) + "/" + forumTopicId,
                        null
                ).getJSONObject("forumTopic")
        );
    }

    /**
     * Get a list of forum topics in a channel.<br>
     * <a href="https://www.guilded.gg/docs/api/forums/ForumTopicReadMany" target=_blank>https://www.guilded.gg/docs/api/forums/ForumTopicReadMany</a>
     * @param channelId The ID of the channel to get topics from.
     * @param query The query settings. Set it to null to use default settings.
     * @return Array of ForumTopicSummary objects.
     */
    public ForumTopicSummary[] getForumTopics(String channelId, GObjectQuery query)
    {
        JSONArray jsonArray = execute(Method.GET,
                FORUM_CHANNEL_URL.replace("{channelId}", channelId) + (query == null ? "" : query.toString()),
                null
        ).getJSONArray("forumTopics");
        ForumTopicSummary[] forumTopicSummaries = new ForumTopicSummary[jsonArray.size()];
        for(int i = 0; i != jsonArray.size(); i++)
            forumTopicSummaries[i] = ForumTopicSummary.fromJSON(jsonArray.getJSONObject(i));
        return forumTopicSummaries;
    }

    /**
     * Pin a forum topic.<br>
     * <a href="https://www.guilded.gg/docs/api/forums/ForumTopicPin" target=_blank>https://www.guilded.gg/docs/api/forums/ForumTopicPin</a>
     * @param channelId The ID of the channel where the topic belongs to.
     * @param forumTopicId The ID of the topic.
     */
    public void pinForumTopic(String channelId, int forumTopicId)
    {
        execute(Method.PUT,
                FORUM_CHANNEL_URL.replace("{channelId}", channelId) + '/' + forumTopicId + "/pin",
                null
        );
    }

    /**
     * Unpin a forum topic.<br>
     * <a href="https://www.guilded.gg/docs/api/forums/ForumTopicUnpin" target=_blank>https://www.guilded.gg/docs/api/forums/ForumTopicUnpin</a>
     * @param channelId The ID of the channel where the topic belongs to.
     * @param forumTopicId The ID of the topic.
     */
    public void unpinForumTopic(String channelId, int forumTopicId)
    {
        execute(Method.DELETE,
                FORUM_CHANNEL_URL.replace("{channelId}", channelId) + '/' + forumTopicId + "/pin",
                null
        );
    }

    public void lockForumTopic()
    {
        //TODO: wait for the announcement
    }

    public void unlockForumTopic()
    {
        //TODO: wait for the announcement
    }
}
