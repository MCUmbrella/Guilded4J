# Guilded4J
#### Guilded API wrapper for Java development
<br>

Build status: [![GH Action status](https://github.com/MCUmbrella/Guilded4J/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/MCUmbrella/Guilded4J/actions/workflows/maven.yml)<br>
Official Guilded server: https://guilded.gg/G4JCafe
# WARNING:
- ## Guilded4J is currently under development - there are still many imperfect or uncompleted features.
- ## The bot API is only available to users who participate in the early access for now.

## Progress:

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
- - [ ] Add reaction
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
