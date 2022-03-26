# Guilded4J
#### Guilded API wrapper for Java development
<br>

Build status: [![GH Action status](https://github.com/MCUmbrella/Guilded4J/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/MCUmbrella/Guilded4J/actions/workflows/maven.yml)<br>
Official Guilded server: [guilded.gg/Guilded4J-Cafe](https://www.guilded.gg/Guilded4J-Cafe) (or use [this](https://www.guilded.gg/r/zzzE8VxJNR?i=8412wg5d))<br>
# Using:
- [Refer to the Wiki page](https://github.com/MCUmbrella/Guilded4J/wiki)
- [Refer to the Javadoc](http://docs.floatationdevice.vip/guilded4j/)
- [Some example bots](https://github.com/MCUmbrella/Guilded4J-Examples)
# CAUTION:
- The Guilded bot API is still in early development. Don't request Guilded4J to add some functions that are not implemented on the Guilded side.
- The API is only available to users who participate in the early access for now.<br>
# Progress:
`(Waiting for Guilded side to complete basic API functions)`
## Text
- [x] Create message - createChannelMessage()
- [x] Delete message - deleteChannelMessage()
- [x] Update message - updateChannelMessage()
- [x] Get message info - getMessage()
- [x] Get last 100 messages - getChannelMessages()
## Members
- [x] Update/delete nickname - setMemberNickname()
- [x] Get member info - getServerMember()
- [x] Kick server member - kickServerMember()
- [x] Get member list - getServerMembers()
- [ ] Get member ban info - getServerMemberBan()
- [ ] Ban server member - banServerMember()
- [ ] Unban server member - unbanServerMember()
- [ ] Get member ban list - getServerMemberBans()
## Forum
- [x] Create forum thread - createForumThread()
## List
- [x] Create list item - createListItem()
- [ ] Get list items - getListItems()
- [ ] Get a list item - getListItem()
- [ ] Update list item - updateListItem()
- [ ] Delete list item - deleteListItem()
## Document
- [x] Create document - createDoc()
- [x] Update document - updateDoc()
- [x] Delete document - deleteDoc()
- [x] Get document info - getDoc()
- [x] Get last 50 updated docs - getChannelDocs()
## Reaction
- [x] Add reaction - createContentReaction()
## XP
- [x] Add XP to user - awardUserXp()
- [x] Add XP to all users with specified role - awardRoleXp()
## Social links
- [x] Get member's social link - getSocialLink()
## Group membership
- [x] Add member to group - addGroupMember()
- [x] Remove member from group - removeGroupMember()
## Role membership
- [x] Get member's role(s) - getMemberRoles()
- [x] Assign role to member - addRoleMember()
- [x] Remove role from member - removeRoleMember()
## Event
- [x] GuildedWebsocketInitializedEvent
- [x] GuildedWebsocketClosedEvent
- [x] ChatMessageCreatedEvent
- [x] ChatMessageDeletedEvent
- [x] ChatMessageUpdatedEvent
- [x] TeamMemberJoinedEvent
- [x] TeamMemberRemovedEvent
- [x] TeamMemberUpdatedEvent
- [x] TeamRolesUpdatedEvent
- [x] TeamXpAddedEvent
