/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object.misc;

import cn.hutool.json.JSONObject;

/**
 * Represents a summary of a member's nickname.
 * This class is not present in the official API.
 */

public class MemberNicknameSummary
{
    String nickname, id;

    public MemberNicknameSummary(String id, String nickname)
    {
        this.id = id;
        this.nickname = nickname;
    }

    public static MemberNicknameSummary fromJSON(JSONObject json)
    {
        return new MemberNicknameSummary(
                json.getStr("id"),
                json.getStr("nickname")
        );
    }

    public String getNickname(){return nickname;}

    public String getUserId(){return id;}
}
