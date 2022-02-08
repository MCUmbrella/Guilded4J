# Guilded4J
#### Guilded API wrapper for Java development
<br>

Build status: [![GH Action status](https://github.com/MCUmbrella/Guilded4J/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/MCUmbrella/Guilded4J/actions/workflows/maven.yml)<br>
Official Guilded server: [guilded.gg/Guilded4J-Cafe](https://www.guilded.gg/Guilded4J-Cafe) (or use [this](https://www.guilded.gg/r/zzzE8VxJNR?i=8412wg5d))<br>
# Using:
- [Refer to the Wiki page](https://github.com/MCUmbrella/Guilded4J/wiki)
- [Refer to the Javadoc](http://docs.floatationdevice.vip/guilded4j/)
# CAUTION:
- The Guilded bot API is still in early development. Don't request Guilded4J to add some functions that are not implemented on the Guilded side.
- The API is only available to users who participate in the early access for now.<br>
# Progress:
`(Waiting for Guilded side to complete basic API functions)`
- [x] Text
- - [x] Create message - createChannelMessage()
- - [x] Delete message - deleteChannelMessage()
- - [x] Update message - updateChannelMessage()
- - [x] Get message info - getMessage()
- - [x] Get last 100 messages - getChannelMessages()
- [ ] Members
- - [x] Get member's role(s) - getMemberRoles()
- - [x] Update/delete nickname - setMemberNickname()
- [ ] Forum
- - [x] Create forum thread - createForumThread()
- [ ] List
- - [x] Create list item - createListItem()
- [ ] Document
- - [x] Create document - createDocument()
- - [x] Update document - updateDocument()
- - [x] Delete document - deleteDocument()
- - [ ] Get document info - getDocument()
- - [ ] Get last 50 updated docs - getChannelDocuments()
- [ ] Reaction
- - [x] Add reaction - createContentReaction()
- [x] XP
- - [x] Add XP to user - awardUserXp()
- - [x] Add XP to user group - awardRoleXp()
- [x] Social links
- - [x] Get member's social link - getSocialLink()
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
- - [x] TeamMemberUpdatedEvent
- - [x] TeamRolesUpdatedEvent
