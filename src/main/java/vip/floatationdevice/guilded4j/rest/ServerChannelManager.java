/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.enums.ServerChannelType;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.object.ServerChannel;

import static vip.floatationdevice.guilded4j.G4JClient.CHANNELS_URL;

/**
 * Manages the server channels.
 */
public class ServerChannelManager extends RestManager
{
    public ServerChannelManager(String authToken)
    {
        super(authToken);
    }

    /**
     * Create a channel in the specified server.<br>
     * <a href="https://www.guilded.gg/docs/api/channels/ChannelCreate" target=_blank>https://www.guilded.gg/docs/api/channels/ChannelCreate</a>
     * @param name The name of the channel (min length 1; max length 100).
     * @param topic The topic of the channel (max length 512).
     * @param isPublic Whether the channel can be accessed from users who are not member of the server (default false).
     * @param type The type of channel to create.
     * @param serverId The server that the channel should be created in.
     * @param groupId The group that the channel should be created in (optional). If not provided, channel will be created in the "Server home" group.
     * @param categoryId The category the channel should go in (optional). If not provided, channel will be a top-level channel.
     * @return The created channel's ServerChannel object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ServerChannel createServerChannel(String name, String topic, Boolean isPublic, ServerChannelType type, String serverId, String groupId, Integer categoryId)
    {
        JSONObject result = new JSONObject(HttpRequest.post(CHANNELS_URL).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                        .set("name", name)
                        .set("topic", topic)
                        .set("isPublic", isPublic)
                        .set("type", type.toString().toLowerCase())
                        .set("serverId", serverId)
                        .set("groupId", groupId)
                        .set("categoryId", categoryId)
                        .toString()).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ServerChannel.fromString(result.get("channel").toString());

    }

    /**
     * Get a channel by its UUID.<br>
     * <a href="https://www.guilded.gg/docs/api/channels/ChannelRead" target=_blank>https://www.guilded.gg/docs/api/channels/ChannelRead</a>
     * @param channelId The ID of the channel.
     * @return The channel's ServerChannel object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public ServerChannel getServerChannel(String channelId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(CHANNELS_URL + "/" + channelId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ServerChannel.fromString(result.get("channel").toString());
    }

    /**
     * Delete a channel by its UUID.<br>
     * <a href="https://www.guilded.gg/docs/api/channels/ChannelDelete" target=_blank>https://www.guilded.gg/docs/api/channels/ChannelDelete</a>
     * @param channelId The ID of the channel.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void deleteServerChannel(String channelId)
    {
        String result = HttpRequest.delete(CHANNELS_URL + "/" + channelId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("ChannelDelete returned an unexpected JSON string");
        }
    }

    /**
     * Update a channel.<br>
     * <a href="https://www.guilded.gg/docs/api/channels/ChannelUpdate" target=_blank>https://www.guilded.gg/docs/api/channels/ChannelUpdate</a>
     * @param channelId The ID of the channel.
     * @param name The new name of the channel.
     * @param topic The new topic of the channel.
     * @param isPublic The new public status of the channel.
     * @return The updated channel's ServerChannel object.
     */
    public ServerChannel updateServerChannel(String channelId, String name, String topic, Boolean isPublic)
    {
        JSONObject result = new JSONObject(HttpRequest.patch(CHANNELS_URL + "/" + channelId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject(new JSONConfig().setIgnoreNullValue(true)).
                        set("name", name).
                        set("topic", topic).
                        set("isPublic", isPublic).
                        toString()).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return ServerChannel.fromString(result.get("channel").toString());
    }

    public ServerChannel[] getServerChannels()
    {
        return null; //TODO: wait
    }
}