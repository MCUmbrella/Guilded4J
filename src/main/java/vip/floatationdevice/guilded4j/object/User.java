/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

/**
 * Represents a user (or bot).
 * <a href="https://www.guilded.gg/docs/api/members/User" target=_blank>https://www.guilded.gg/docs/api/members/User</a>
 */
public class User //TODO: let this get closer to the actual User object (that just appeared in the API docs)
{
    private final String id;
    private final String type;
    private String name;
    private final String createdAt;

    /**
     * Generate User object with the given 3 essential parameters.
     * @param id The ID of the user.
     * @param type The type of user. If this property is absent, it can assumed to be of type 'user'.
     * @param createdAt The ISO 8601 timestamp that the user was created at.
     */
    public User(String id, String type, String createdAt)
    {
        this.id = id;
        this.type = type;
        this.createdAt = createdAt;
    }

    public String getId(){return id;}
    public String getType(){return type;}
    public String getName(){return name;}
    public String getCreatedAt(){return createdAt;}

    public User setName(String name){this.name = name; return this;}
}
