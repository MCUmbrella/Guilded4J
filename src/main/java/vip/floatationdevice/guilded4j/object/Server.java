/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * The server object.<br>
 * <a href="https://www.guilded.gg/docs/api/servers/Server" target=_blank>https://www.guilded.gg/docs/api/servers/Server</a>
 */
public class Server
{
    String id, ownerId, type, name, url, about, avatar, banner, timezone, defaultChannelId, createdAt;
    boolean isVerified = false;

    /**
     * Get the ID of the server.
     */
    public String getId(){return id;}

    /**
     * Get the ID of the user who created this server.
     */
    public String getOwnerId(){return ownerId;}

    /**
     * Get the type of server designated from the server's settings page.
     */
    public String getType(){return type;}

    /**
     * Get the name of the server.
     */
    public String getName(){return name;}

    /**
     * Get the URL that the server can be accessible from.
     */
    public String getUrl(){return url;}

    /**
     * Get the description associated with the server.
     */
    public String getDescription(){return about;}

    /**
     * Get the URL of the avatar image associated with the server.
     */
    public String getAvatarUrl(){return avatar;}

    /**
     * Get the URL of the banner image associated with the server.
     */
    public String getBannerUrl(){return banner;}

    /**
     * Get the timezone associated with the server.
     */
    public String getTimeZone(){return timezone;}

    /**
     * Get the verified status of the server.
     */
    public boolean isVerified(){return isVerified;}

    /**
     * Get the channel ID of the default channel of the server.
     */
    public String getDefaultChannel(){return defaultChannelId;}

    /**
     * Get the ISO 8601 timestamp that the server was created at.
     */
    public String getCreationTime(){return createdAt;}

    public Server setId(String id)
    {
        this.id = id;
        return this;
    }

    public Server setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
        return this;
    }

    public Server setType(String type)
    {
        this.type = type;
        return this;
    }

    public Server setName(String name)
    {
        this.name = name;
        return this;
    }

    public Server setUrl(String url)
    {
        this.url = url;
        return this;
    }

    public Server setAbout(String about)
    {
        this.about = about;
        return this;
    }

    public Server setAvatar(String avatar)
    {
        this.avatar = avatar;
        return this;
    }

    public Server setBanner(String banner)
    {
        this.banner = banner;
        return this;
    }

    public Server setTimezone(String timezone)
    {
        this.timezone = timezone;
        return this;
    }

    public Server setIsVerified(boolean verified)
    {
        this.isVerified = verified;
        return this;
    }

    public Server setDefaultChannelId(String defaultChannelId)
    {
        this.defaultChannelId = defaultChannelId;
        return this;
    }

    public Server setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    public static Server fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getStr("id"),
                json.getStr("ownerId"),
                json.getStr("name"),
                json.getStr("createdAt")
        );
        return new Server()
                .setId(json.getStr("id"))
                .setOwnerId(json.getStr("ownerId"))
                .setType(json.getStr("type"))
                .setName(json.getStr("name"))
                .setUrl(json.getStr("url"))
                .setAbout(json.getStr("about"))
                .setAvatar(json.getStr("avatar"))
                .setBanner(json.getStr("banner"))
                .setTimezone(json.getStr("timezone"))
                .setIsVerified(json.getBool("isVerified") != null && json.getBool("isVerified"))
                .setCreatedAt(json.getStr("createdAt"));
    }

    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("ownerId", ownerId)
                .set("name", name)
                .set("type", type)
                .set("url", url)
                .set("about", about)
                .set("avatar", avatar)
                .set("banner", banner)
                .set("timezone", timezone)
                .set("isVerified", isVerified)
                .set("createdAt", createdAt)
                .toString();
    }
}
