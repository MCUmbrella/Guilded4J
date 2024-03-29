/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.ServerMember;

/**
 * Event that is fired when a user joins a server.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/ServerMemberJoined" target=_blank>https://www.guilded.gg/docs/api/websockets/ServerMemberJoined</a>
 */
public class ServerMemberJoinedEvent extends GuildedEvent
{
    private final ServerMember member;

    public ServerMemberJoinedEvent(Object source, String json)
    {
        super(source, json);
        this.member = ServerMember.fromJSON((JSONObject) new JSONObject(json).getByPath("d.member"));
    }

    /**
     * Get the member that joined the server.
     * @return The member's ServerMember object.
     */
    public ServerMember getMember(){return member;}
}
