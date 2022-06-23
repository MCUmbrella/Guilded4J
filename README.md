![Guilded4J](https://user-images.githubusercontent.com/40854260/163506743-1fdac3d2-f585-46d4-b365-c60ca5208eae.png)
_Guilded API wrapper for Java development_

Build status: [![GH Action status](https://github.com/MCUmbrella/Guilded4J/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/MCUmbrella/Guilded4J/actions/workflows/maven.yml)<br>
Official Guilded server: [guilded.gg/Guilded4J-Cafe](https://www.guilded.gg/Guilded4J-Cafe) (or use [this](https://www.guilded.gg/r/zzzE8VxJNR?i=8412wg5d))<br>
# Using:
- [Wiki page](https://github.com/MCUmbrella/Guilded4J/wiki)
- [Javadoc](http://docs.floatationdevice.vip/guilded4j/)
- [Some example bots](https://github.com/MCUmbrella/Guilded4J-Examples)
# CAUTION:
- The Guilded bot API is still in early development. Don't request Guilded4J to add some functions that are not implemented on the Guilded side.
- The API is only available to users who participate in the early access for now.<br>
# Implemented features / Progress:
<details><summary>Click me</summary>
<p>

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
- [x] Get member ban info - getServerMemberBan()
- [x] Ban server member - banServerMember()
- [x] Unban server member - unbanServerMember()
- [x] Get member ban list - getServerMemberBans()
## Forum
- [x] Create forum thread - createForumThread()
## List
- [x] Create list item - createListItem()
- [x] Get list items - getListItems()
- [x] Get a list item - getListItem()
- [x] Update list item - updateListItem()
- [x] Delete list item - deleteListItem()
- [x] Completed list item - completeListItem()
- [x] Uncomplted list item - uncompleteListItem()
## Document
- [x] Create document - createDoc()
- [x] Update document - updateDoc()
- [x] Delete document - deleteDoc()
- [x] Get document info - getDoc()
- [x] Get last 50 updated docs - getChannelDocs()
## Reaction
- [x] Add reaction - createContentReaction()
- [x] Remove reaction - deleteContentReaction()
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
## Webhooks
- [x] Create webhook - createWebhook()
- [x] Get webhooks - getWebhooks()
- [x] Update webhook - updateWebhook()
- [x] Delete webhook - deleteWebhook()
- [x] Get webhook info - getWebhook()
## Channels
- [x] Create channel - createServerChannel()
- [x] Update channel - updateServerChannel()
- [x] Delete channel - deleteServerChannel()
- [x] Get channel info - getServerChannel()
- [ ] Get channel list - getServerChannels()
## Server
- [x] Get server info - getServer()
## Event
- [x] GuildedWebsocketWelcomeEvent
- [x] GuildedWebsocketClosedEvent
- [x] ChatMessageCreatedEvent
- [x] ChatMessageDeletedEvent
- [x] ChatMessageUpdatedEvent
- [x] TeamMemberJoinedEvent
- [x] TeamMemberRemovedEvent
- [x] TeamMemberBannedEvent
- [x] TeamMemberUnbannedEvent
- [x] TeamMemberUpdatedEvent
- [x] TeamRolesUpdatedEvent
- [x] TeamXpAddedEvent
- [x] TeamWebhookCreatedEvent
- [x] TeamWebhookUpdatedEvent
- [x] TeamChannelCreatedEvent
- [x] TeamChannelUpdatedEvent
- [x] TeamChannelDeletedEvent
- [x] DocCreatedEvent
- [x] DocUpdatedEvent
- [x] DocDeletedEvent
- [x] ListItemCreatedEvent
- [x] ListItemUpdatedEvent
- [x] ListItemDeletedEvent
- [x] ListItemCompletedEvent
- [x] ListItemUncompletedEvent

</p>
</details>
