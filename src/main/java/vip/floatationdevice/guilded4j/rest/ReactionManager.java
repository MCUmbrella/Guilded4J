/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;
import vip.floatationdevice.guilded4j.exception.GuildedException;

import static vip.floatationdevice.guilded4j.G4JClient.REACTION_URL;

/**
 * Manages the content reactions.
 */
public class ReactionManager extends RestManager
{
    public ReactionManager(String authToken)
    {
        super(authToken);
    }

    /**
     * Add a reaction emote.<br>
     * <a href="https://www.guilded.gg/docs/api/reactions/ContentReactionCreate" target=_blank>https://www.guilded.gg/docs/api/reactions/ContentReactionCreate</a>
     * @param channelId The ID of the channel.
     * @param contentId Content ID of the content.
     * @param emoteId Emote ID to apply.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void createContentReaction(String channelId, String contentId, int emoteId)
    {
        execute(Method.PUT,
                REACTION_URL
                        .replace("{channelId}", channelId)
                        .replace("{contentId}", contentId)
                        .replace("{emoteId}", Integer.toString(emoteId)),
                null
        );
    }

    /**
     * Remove a reaction emote.<br>
     * <a href="https://www.guilded.gg/docs/api/reactions/ContentReactionDelete" target=_blank>https://www.guilded.gg/docs/api/reactions/ContentReactionDelete</a>
     * @param channelId Channel UUID where the content exists.
     * @param contentId The ID of the content.
     * @param emoteId The ID of the emote to remove.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void deleteContentReaction(String channelId, String contentId, int emoteId)
    {
        execute(Method.DELETE,
                REACTION_URL
                        .replace("{channelId}", channelId)
                        .replace("{contentId}", contentId)
                        .replace("{emoteId}", Integer.toString(emoteId)),
                null
        );
    }
}
