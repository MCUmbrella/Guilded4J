/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import vip.floatationdevice.guilded4j.object.User;

/**
 * Event fired when a member's nickname is updated.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/TeamMemberUpdated" target=_blank>https://www.guilded.gg/docs/api/websockets/TeamMemberUpdated</a>
 */
public class TeamMemberUpdatedEvent extends GuildedEvent {
    private final User userInfo;

    /**
     * Generate TeamMemberUpdatedEvent using the 2 given essential keys.
     *
     * @param id       The ID of the member.
     * @param nickname The nickname that was just updated for the user.
     */
    public TeamMemberUpdatedEvent(Object source, String id, String nickname) {
        super(source);
        this.userInfo = new User(id).setNickname(nickname);
    }

    /**
     * Get the member's information as User object.
     *
     * @return the User object representing the member whose nickname was updated.
     */
    public User getUserInfo() {
        return userInfo;
    }
}
