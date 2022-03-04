/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import java.util.HashMap;

/**
 * Guilded user object (also can be bot or webhook).
 */
public class User {
    private final String id;
    private String name;
    private String nickname;
    private String email;
    private String avatarURL;
    private int[] roleIds;
    private HashMap<String, HashMap<String, String>> socialLinks;
    private String botId;
    private String webhookId;

    /**
     * Generate User object with the given userId.
     *
     * @param userId The ID of the user.
     */
    public User(String userId) {
        this.id = userId;
    }

    public String getRealName() {
        return name;
    }

    public User setRealName(String name) {
        this.name = name;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getUserId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public User setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
        return this;
    }

    public int[] getRoleIds() {
        return roleIds;
    }

    public User setRoleIds(int[] roleIds) {
        this.roleIds = roleIds;
        return this;
    }

    public HashMap<String, HashMap<String, String>> getSocialLinks() {
        return socialLinks;
    }

    public User setSocialLinks(HashMap<String, HashMap<String, String>> socialLinks) {
        this.socialLinks = socialLinks;
        return this;
    }

    public String getBotId() {
        return botId;
    }

    public User setBotId(String botId) {
        this.botId = botId;
        return this;
    }

    public String getWebhookId() {
        return webhookId;
    }

    public User setWebhookId(String webhookId) {
        this.webhookId = webhookId;
        return this;
    }

    /**
     * Detect whether the user is a bot or not.
     *
     * @return {@code true} if the user is a bot, {@code false} otherwise.
     */
    public Boolean isBot() {
        return botId != null;
    }

    /**
     * Detect whether the user is a webhook or not.
     *
     * @return {@code true} if the user is a webhook, {@code false} otherwise.
     */
    public Boolean isWebhook() {
        return webhookId != null;
    }

    /**
     * Detect whether the user is a normal Guilded user or not.
     *
     * @return {@code true} if the user is a normal user, {@code false} otherwise.
     */
    public Boolean isUser() {
        return !this.isBot() && !this.isWebhook();
    }
}
