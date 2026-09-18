/*
 * Source recovered from CFR output plus javap bytecode for refreshFriendStatuses.
 */
package com.rs2.model.player;

import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.util.ChatTextCodec;
import com.rs2.util.TextUtil;

public final class SocialManager {
    private final Player player;
    private int privateMessageCounter = 1;

    public SocialManager(Player player) {
        this.player = player;
    }

    public final void initializePrivateMessaging() {
        Player player = this.player;
        player.packetSender.sendPrivateMessagingStatus(2);
        this.refreshFriendStatuses(false);
    }

    public final void refreshFriendStatuses(boolean forceOffline) {
        for (long friendHash : this.player.getFriendsList()) {
            if (friendHash == 0L) {
                continue;
            }
            this.player.packetSender.sendFriendStatus(friendHash, this.getFriendOnlineStatus(friendHash));
        }

        long playerNameHash = this.player.getNameHash();
        for (Player otherPlayer : World.getPlayers()) {
            if (otherPlayer == null || !containsNameHash(otherPlayer.getFriendsList(), playerNameHash)) {
                continue;
            }
            int status = this.isVisibleTo(otherPlayer, forceOffline) ? 1 : 0;
            otherPlayer.packetSender.sendFriendStatus(playerNameHash, status);
        }
    }

    private int getFriendOnlineStatus(long friendHash) {
        long playerNameHash = this.player.getNameHash();
        for (Player friend : World.getPlayers()) {
            if (friend == null || friend.getNameHash() == playerNameHash || friend.getNameHash() != friendHash) {
                continue;
            }
            return isVisibleToPlayer(friend, playerNameHash) ? 1 : 0;
        }
        return 0;
    }

    private boolean isVisibleTo(Player viewer, boolean forceOffline) {
        if (forceOffline || this.player.getPrivateChatMode() == 2) {
            return false;
        }
        if (this.player.getPrivateChatMode() == 1) {
            return containsNameHash(this.player.getFriendsList(), viewer.getNameHash());
        }
        return true;
    }

    private static boolean isVisibleToPlayer(Player subject, long viewerNameHash) {
        if (subject.getPrivateChatMode() == 0) {
            return true;
        }
        if (subject.getPrivateChatMode() == 1) {
            subject.getSocialManager();
            return containsNameHash(subject.getFriendsList(), viewerNameHash);
        }
        return false;
    }

    public final void addFriend(long value2) {
        if (SocialManager.countEntries(this.player.getFriendsList()) >= 200) {
            Player player = this.player;
            player.packetSender.sendGameMessage("Your friends list is full.");
            return;
        }
        if (SocialManager.containsNameHash(this.player.getFriendsList(), value2)) {
            Player player = this.player;
            player.packetSender.sendGameMessage(TextUtil.decodeNameHash(value2) + " is already on your friends list.");
            return;
        }
        int freeSlot = SocialManager.findFreeSlot(this.player.getFriendsList());
        this.player.getFriendsList()[freeSlot] = value2;
        this.refreshFriendStatuses(false);
    }

    public final void addIgnore(long value2) {
        if (SocialManager.countEntries(this.player.getIgnoreList()) >= 100) {
            Player player = this.player;
            player.packetSender.sendGameMessage("Your ignores list is full.");
            return;
        }
        if (SocialManager.containsNameHash(this.player.getIgnoreList(), value2)) {
            Player player = this.player;
            player.packetSender.sendGameMessage(TextUtil.decodeNameHash(value2) + " is already on your ignores list.");
            return;
        }
        int freeSlot = SocialManager.findFreeSlot(this.player.getIgnoreList());
        this.player.getIgnoreList()[freeSlot] = value2;
    }

    public final void sendPrivateMessage(Player player, long value5, byte[] byteValues2, int value6) {
        Player[] playerArray = World.getPlayers();
        int length = playerArray.length;
        int index = 0;
        while (index < length) {
            Player player2 = playerArray[index];
            if (player2 != null && player2.getNameHash() == value5) {
                boolean enabled;
                sendPrivateMessageControlExit1: {
                    long nameHash = this.player.getNameHash();
                    SocialManager socialManager = player2.getSocialManager();
                    long[] ignoreList = socialManager.player.getIgnoreList();
                    int length2 = ignoreList.length;
                    int index2 = 0;
                    while (index2 < length2) {
                        long value3 = ignoreList[index2];
                        if (value3 == nameHash) {
                            enabled = true;
                            break sendPrivateMessageControlExit1;
                        }
                        ++index2;
                    }
                    enabled = false;
                }
                if (!enabled) {
                    int playerRights = player.getPlayerRights();
                    int value4 = player.gameMode;
                    player2.packetSender.sendPrivateMessage(player.getNameHash(), playerRights, 0, value4, byteValues2, value6);
                    ChatTextCodec.decode(byteValues2, value6);
                    return;
                }
            }
            ++index;
        }
    }

    public final void removeFromList(long[] longValues, long value2) {
        int index = 0;
        while (index < longValues.length) {
            if (longValues[index] == value2) {
                longValues[index] = 0L;
                break;
            }
            ++index;
        }
        this.refreshFriendStatuses(false);
    }

    private static boolean containsNameHash(long[] longValues, long value2) {
        int index = 0;
        while (index < longValues.length) {
            if (longValues[index] == value2) {
                return true;
            }
            ++index;
        }
        return false;
    }

    private static int countEntries(long[] longValues) {
        int index = 0;
        long[] longValues2 = longValues;
        int length = longValues.length;
        int index2 = 0;
        while (index2 < length) {
            long value = longValues2[index2];
            if (value > 0L) {
                ++index;
            }
            ++index2;
        }
        return index;
    }

    private static int findFreeSlot(long[] slot) {
        int index = 0;
        while (index < slot.length) {
            if (slot[index] == 0L) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    public final int nextPrivateMessageId() {
        return this.privateMessageCounter++;
    }
}
