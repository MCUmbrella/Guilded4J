package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.object.ForumThread;

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
     * Create a thread in a forum.<br>
     * <a href="https://www.guilded.gg/docs/api/forums/ForumThreadCreate" target=_blank>https://www.guilded.gg/docs/api/forums/ForumThreadCreate</a>
     * @param title The title of the thread.
     * @param content The thread's content.
     * @return The newly created thread's ForumThread object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ForumThread createForumThread(String channelId, String title, String content)
    {
        JSONObject result = new JSONObject(HttpRequest.post(FORUM_CHANNEL_URL.replace("{channelId}", channelId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject().set("title", title).set("content", content).toString()).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ForumThread.fromString(result.get("forumThread").toString());
    }
}
