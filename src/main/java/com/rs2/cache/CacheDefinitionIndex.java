package com.rs2.cache;

import com.rs2.ServerSettings;
import com.rs2.model.GameplayHelper;
import com.rs2.model.clue.NpcClue;
import com.rs2.model.clue.PuzzleBoxHandler;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.randomevent.RandomEventRollEvent;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.net.packet.PacketSender;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

public class CacheDefinitionIndex {
    private MapIndexEntry[] mapIndexEntries;
    private DefinitionIndexEntry[] objectDefinitionEntries;
    private DefinitionIndexEntry[] itemDefinitionEntries;
    private DefinitionIndexEntry[] npcDefinitionEntries;

    public static void dismissRandomEventNpc(Player player) {
        if (player.getActiveRandomEventNpc() != null && !player.getActiveRandomEventNpc().isDead()) {
            player.packetSender.sendStillGraphic(86, player.getActiveRandomEventNpc().getPosition(), 0x640000);
            player.packetSender.sendSoundEffect(300, 1, 0);
            GameplayHelper.unregisterTemporaryNpc(player.getActiveRandomEventNpc());
            player.setActiveRandomEventNpc(null);
        }
    }

    public static void scheduleRandomEventRoll(Player player) {
        if (player.botEnabled) {
            return;
        }
        if (player.isInTutorialIsland()) {
            return;
        }
        if (ServerSettings.randomEventsMode == 2) {
            return;
        }
        CycleEventHandler.getInstance().schedule(player, new RandomEventRollEvent(player), 1000);
    }

    public CacheDefinitionIndex(CacheStore cacheStore) throws IOException {
        CacheArchive cacheArchive = new CacheArchive(cacheStore.readFile(0, 2));
        this.loadObjectDefinitionIndex(cacheArchive);
        this.loadItemDefinitionIndex(cacheArchive);
        this.loadNpcDefinitionIndex(cacheArchive);
        CacheArchive cacheArchive2 = new CacheArchive(cacheStore.readFile(0, 5));
        this.loadMapIndex(cacheArchive2);
    }

    private void loadObjectDefinitionIndex(CacheArchive cacheArchive) {
        ByteBuffer byteBuffer = cacheArchive.getFileBuffer("loc.idx");
        int shortValue = byteBuffer.getShort() & 0xFFFF;
        this.objectDefinitionEntries = new DefinitionIndexEntry[shortValue];
        int value = 2;
        int index = 0;
        while (index < shortValue) {
            this.objectDefinitionEntries[index] = new DefinitionIndexEntry(index, value);
            value += byteBuffer.getShort() & 0xFFFF;
            ++index;
        }
    }

    private void loadItemDefinitionIndex(CacheArchive cacheArchive) {
        ByteBuffer byteBuffer = cacheArchive.getFileBuffer("obj.idx");
        int shortValue = byteBuffer.getShort() & 0xFFFF;
        this.itemDefinitionEntries = new DefinitionIndexEntry[shortValue];
        int value = 2;
        int index = 0;
        while (index < shortValue) {
            this.itemDefinitionEntries[index] = new DefinitionIndexEntry(index, value);
            value += byteBuffer.getShort() & 0xFFFF;
            ++index;
        }
    }

    private void loadNpcDefinitionIndex(CacheArchive cacheArchive) {
        ByteBuffer byteBuffer = cacheArchive.getFileBuffer("npc.idx");
        int shortValue = byteBuffer.getShort() & 0xFFFF;
        this.npcDefinitionEntries = new DefinitionIndexEntry[shortValue];
        int value = 2;
        int index = 0;
        while (index < shortValue) {
            this.npcDefinitionEntries[index] = new DefinitionIndexEntry(index, value);
            value += byteBuffer.getShort() & 0xFFFF;
            ++index;
        }
    }

    private void loadMapIndex(CacheArchive cacheArchive) {
        ByteBuffer byteBuffer = cacheArchive.getFileBuffer("map_index");
        int value = byteBuffer.remaining() / 7;
        this.mapIndexEntries = new MapIndexEntry[value];
        int index = 0;
        while (index < value) {
            int shortValue = byteBuffer.getShort() & 0xFFFF;
            int shortValue2 = byteBuffer.getShort() & 0xFFFF;
            int shortValue3 = byteBuffer.getShort() & 0xFFFF;
            boolean enabled = (byteBuffer.get() & 0xFF) == 1;
            this.mapIndexEntries[index] = new MapIndexEntry(shortValue, shortValue2, shortValue3, enabled);
            ++index;
        }
    }

    public DefinitionIndexEntry[] getObjectDefinitionEntries() {
        return this.objectDefinitionEntries;
    }

    public DefinitionIndexEntry[] getItemDefinitionEntries() {
        return this.itemDefinitionEntries;
    }

    public DefinitionIndexEntry[] getNpcDefinitionEntries() {
        return this.npcDefinitionEntries;
    }

    public DefinitionIndexEntry getObjectDefinitionEntry(int objectId) {
        return this.objectDefinitionEntries[objectId];
    }

    public DefinitionIndexEntry getItemDefinitionEntry(int itemId) {
        return this.itemDefinitionEntries[itemId];
    }

    public DefinitionIndexEntry getNpcDefinitionEntry(int npcId) {
        return this.npcDefinitionEntries[npcId];
    }

    public static boolean showNpcClue(Player player, int npcId) {
        NpcClue npcClue = NpcClue.forClueItemId(npcId);
        if (npcClue == null) {
            return false;
        }
        player.packetSender.showInterface(6965);
        String[] lines = npcClue.getClueTextLines();
        int[] textIds = CacheDefinitionIndex.interfaceTextIdsForLineCount(lines.length);
        int index = 0;
        while (index < lines.length) {
            PacketSender packetSender = player.packetSender;
            packetSender.sendInterfaceText(lines[index], textIds[index]);
            ++index;
        }
        return true;
    }

    private static int[] interfaceTextIdsForLineCount(int interfaceId) {
        switch (interfaceId) {
            case 1: {
                return new int[]{6971};
            }
            case 2: {
                return new int[]{6971, 6972};
            }
            case 3: {
                return new int[]{6970, 6971, 6972};
            }
            case 4: {
                return new int[]{6970, 6971, 6972, 6973};
            }
            case 5: {
                return new int[]{6969, 6970, 6971, 6972, 6973};
            }
            case 6: {
                return new int[]{6969, 6970, 6971, 6972, 6973, 6974};
            }
            case 7: {
                return new int[]{6968, 6969, 6970, 6971, 6972, 6973, 6974};
            }
            case 8: {
                return new int[]{6968, 6969, 6970, 6971, 6972, 6973, 6974, 6975};
            }
        }
        return null;
    }

    public static int randomNpcClueItemForLevel(int npcId) {
        int value = new Random().nextInt(NpcClue.values().length);
        while (NpcClue.values()[value].getLevel() != npcId) {
            value = new Random().nextInt(NpcClue.values().length);
        }
        return NpcClue.values()[value].getClueItemId();
    }

    private static void setTreasureTrailDialogueStep(Player player, int step) {
        player.getDialogueManager().setDialogueId(10009);
        player.getDialogueManager().setDialogueStep(step);
    }

    public static boolean handleNpcClueNpc(Player player, int npcId) {
        NpcClue npcClue = NpcClue.forNpcId(npcId);
        if (npcClue == null) {
            return false;
        }
        if (!player.getInventoryManager().containsItem(npcClue.getClueItemId())) {
            return false;
        }
        player.getDialogueManager().setDialogueNpcId(npcId);
        if (npcClue.getFollowupType() == "Challenge") {
            if (CacheArchive.hasChallengeQuestionAnswerItem(player, npcClue.getClueItemId())) {
                player.activeClueLevel = npcClue.getLevel();
                player.activeClueItemId = npcClue.getClueItemId();
                CacheDefinitionIndex.setTreasureTrailDialogueStep(player, 3);
                if (CacheArchive.getChallengeQuestionLines(npcClue.getClueItemId()).length == 1) {
                    player.getDialogueManager().showNpcOneLineDialogue(CacheArchive.getChallengeQuestionLines(npcClue.getClueItemId())[0], 588);
                } else {
                    player.getDialogueManager().showNpcTwoLineDialogue(CacheArchive.getChallengeQuestionLines(npcClue.getClueItemId())[0], CacheArchive.getChallengeQuestionLines(npcClue.getClueItemId())[1], 588);
                }
            } else {
                player.clueRequiredItems = new ItemStack[1];
                player.clueRequiredItems[0] = new ItemStack(npcClue.getClueItemId(), 1);
                player.getDialogueManager().showNpcOneLineDialogue("Here's a challenge for you.", 588);
                CacheArchive.giveChallengeQuestionAnswerItem(player, npcClue.getClueItemId());
            }
        } else if (npcClue.getFollowupType() == "Puzzle") {
            if (PuzzleBoxHandler.isCluePuzzleSolved(player) && player.ownsCluePuzzleBox()) {
                CacheDefinitionIndex.setTreasureTrailDialogueStep(player, 2);
                player.getDialogueManager().showNpcOneLineDialogue("Thank you very much.", 588);
                player.clueRequiredItems = new ItemStack[4];
                player.clueRequiredItems[0] = new ItemStack(npcClue.getClueItemId(), 1);
                player.clueRequiredItems[1] = new ItemStack(2800, 1);
                player.clueRequiredItems[2] = new ItemStack(3571, 1);
                player.clueRequiredItems[3] = new ItemStack(3565, 1);
                player.activeClueLevel = npcClue.getLevel();
            } else if (player.ownsCluePuzzleBox()) {
                player.getDialogueManager().showNpcOneLineDialogue("The puzzle doesn't seem to be complete yet.", 588);
            } else {
                player.getDialogueManager().showNpcOneLineDialogue("Hello, Solve this puzzle for me please.", 588);
                PuzzleBoxHandler.giveRandomPuzzleBox(player);
            }
        } else {
            CacheDefinitionIndex.setTreasureTrailDialogueStep(player, 2);
            player.getDialogueManager().showNpcOneLineDialogue("Thank you very much.", 588);
            player.clueRequiredItems = new ItemStack[1];
            player.clueRequiredItems[0] = new ItemStack(npcClue.getClueItemId(), 1);
            player.activeClueLevel = npcClue.getLevel();
        }
        return true;
    }
}
