/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * Represents a summary of a member's nickname. This class is not present in the official API
 * (as TeamMemberUpdated's property of this is described as type "object" instead of something else).
 */

public class MemberNicknameSummary
{
    String nickname, id;

    public MemberNicknameSummary(String id, String nickname)
    {
        this.id = id;
        this.nickname = nickname;
    }

    public String getNickname(){return nickname;}

    public String getUserId(){return id;}

    public static MemberNicknameSummary fromString(String jsonString)
    {
        if(JSONUtil.isJson(jsonString))
        {
            JSONObject json = new JSONObject(jsonString);
            return new MemberNicknameSummary(
                    json.getStr("id"),
                    json.getStr("nickname")
            );
        }
        else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }
}
