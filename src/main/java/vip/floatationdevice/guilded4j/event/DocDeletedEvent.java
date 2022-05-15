/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.Doc;

/**
 * Event that is fired when a document is deleted.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/DocDeleted" target=_blank>https://www.guilded.gg/docs/api/websockets/DocDeleted</a>
 */
public class DocDeletedEvent extends GuildedEvent
{
    private final Doc doc;

    public DocDeletedEvent(Object source, String json)
    {
        super(source, json);
        JSONObject j = new JSONObject(json);
        this.doc = Doc.fromString(j.getByPath("d.doc").toString());
    }

    /**
     * Get the document that was deleted.
     * @return The deleted document's Doc object.
     */
    public Doc getDoc(){return doc;}
}
