# Guilded4J
#### Guilded API wrapper for Java development
<br>

Build status: [![GH Action status](https://github.com/MCUmbrella/Guilded4J/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/MCUmbrella/Guilded4J/actions/workflows/maven.yml)<br>
Official Guilded server: https://guilded.gg/G4JCafe
# WARNING:
- ## The Guilded bot API is still in early development. Don't request Guilded4J to add some functions that are not implemented on the Guilded side.
- ## The API is only available to users who participate in the early access for now.
# Using:
In pom.xml:
- Add repository from URL "http://maven.floatationdevice.vip/"<br>
After that the file will look like this:
```XML
...
    <repositories>
        <repository>
            ...
        </repository>
        <repository>
            ...
        </repository>
        <repository>
            <id>floatationdevice</id>
            <url>http://maven.floatationdevice.vip/</url>
        </repository>
    </repositories>
...
```
- Add vip.floatationdevice.guilded4j as dependency<br>
After that the file will look like this:
```XML
...
    <dependencies>
        <dependency>
            ...
        </dependency>
        <dependency>
            ...
        </dependency>
        <dependency>
            <groupId>vip.floatationdevice</groupId>
            <artifactId>guilded4j</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
...
```
# Progress: `(Waiting for Guilded side to complete basic API functions)`
- [x] Text
- - [x] Create message - createChannelMessage()
- - [x] Delete message - deleteChannelMessage()
- - [x] Update message - updateChannelMessage()
- - [x] Get message info - getMessage()
- - [x] Get last 50 messages - getChannelMessages()
- [ ] Forum
- - [x] Create forum thread - createForumThread()
- [ ] List
- - [x] Create list item - createListItem()
- [ ] Reaction
- - [x] Add reaction - createContentReaction()
- [x] XP
- - [x] Add XP to user - awardUserXp()
- - [x] Add XP to user group - awardRoleXp()
- [ ] Group membership
- - [x] Add group member - addGroupMember()
- - [x] Remove group member - removeGroupMember()
- [ ] Role membership
- - [x] Add role member - addRoleMember()
- - [x] Remove role member - removeRoleMember()
- [ ] Event
- - [x] GuildedWebsocketInitializedEvent
- - [x] GuildedWebsocketClosedEvent
- - [x] ChatMessageCreatedEvent
- - [x] ChatMessageDeletedEvent
- - [x] ChatMessageUpdatedEvent
- - [x] TeamXpAddedEvent
