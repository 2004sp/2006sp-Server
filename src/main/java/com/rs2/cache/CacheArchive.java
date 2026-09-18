package com.rs2.cache;

import com.rs2.model.clue.ChallengeQuestion;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.fletching.logs.AcheyLogFletchingAction;
import com.rs2.model.skill.fletching.logs.MagicLogFletchingAction;
import com.rs2.model.skill.fletching.logs.MapleLogFletchingAction;
import com.rs2.model.skill.fletching.logs.NormalLogFletchingAction;
import com.rs2.model.skill.fletching.logs.OakLogFletchingAction;
import com.rs2.model.skill.fletching.logs.WillowLogFletchingAction;
import com.rs2.model.skill.fletching.logs.YewLogFletchingAction;
import com.rs2.net.packet.PacketSender;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class CacheArchive {
    private boolean wholeArchiveDecompressed = false;
    private ByteBuffer archiveBuffer;
    private Map entriesByNameHash = new HashMap();

    public CacheArchive(CacheFile cacheFile) {
        ByteBuffer byteBuffer = cacheFile.getBuffer();
        byteBuffer.position(0);
        int value = (byteBuffer.get() & 0xFF) << 16 | (byteBuffer.get() & 0xFF) << 8 | byteBuffer.get() & 0xFF;
        int value2 = (byteBuffer.get() & 0xFF) << 16 | (byteBuffer.get() & 0xFF) << 8 | byteBuffer.get() & 0xFF;
        if (value != value2) {
            byte[] byteValues = new byte[value2];
            byteBuffer.get(byteValues);
            byte[] byteValues2 = CacheArchive.decompressBzip2Payload(byteValues);
            byteBuffer = ByteBuffer.allocate(byteValues2.length);
            byteBuffer.put(byteValues2);
            byteBuffer.flip();
            this.wholeArchiveDecompressed = true;
        }
        value = byteBuffer.getShort() & 0xFFFF;
        value2 = byteBuffer.position() + value * 10;
        int index = 0;
        while (index < value) {
            int intValue = byteBuffer.getInt();
            int value3 = (byteBuffer.get() & 0xFF) << 16 | (byteBuffer.get() & 0xFF) << 8 | byteBuffer.get() & 0xFF;
            int value4 = (byteBuffer.get() & 0xFF) << 16 | (byteBuffer.get() & 0xFF) << 8 | byteBuffer.get() & 0xFF;
            CacheArchiveEntry cacheArchiveEntry = new CacheArchiveEntry(intValue, value3, value4, value2);
            this.entriesByNameHash.put(cacheArchiveEntry.getNameHash(), cacheArchiveEntry);
            value2 += cacheArchiveEntry.getCompressedSize();
            ++index;
        }
        this.archiveBuffer = byteBuffer;
    }

    public byte[] getFileBytes(String text2) {
        int index = 0;
        text2 = text2.toUpperCase();
        int index2 = 0;
        while (index2 < text2.length()) {
            index = index * 61 + text2.charAt(index2) - 32;
            ++index2;
        }
        CacheArchiveEntry cacheArchiveEntry = (CacheArchiveEntry)this.entriesByNameHash.get(index);
        if (cacheArchiveEntry == null) {
            return null;
        }
        byte[] compressedSize = new byte[cacheArchiveEntry.getCompressedSize()];
        this.archiveBuffer.position(cacheArchiveEntry.getDataOffset());
        this.archiveBuffer.get(compressedSize);
        if (this.wholeArchiveDecompressed) {
            return compressedSize;
        }
        return CacheArchive.decompressBzip2Payload(compressedSize);
    }

    public ByteBuffer getFileBuffer(String text2) {
        byte[] fileBytes = this.getFileBytes(text2);
        if (fileBytes == null) {
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(fileBytes.length);
        byteBuffer.put(fileBytes);
        byteBuffer.flip();
        return byteBuffer;
    }

    private static byte[] decompressBzip2Payload(byte[] byteValues3) {
        byte[] byteValues2 = new byte[byteValues3.length + 4];
        System.arraycopy(byteValues3, 0, byteValues2, 4, byteValues3.length);
        byteValues2[0] = 66;
        byteValues2[1] = 90;
        byteValues2[2] = 104;
        byteValues2[3] = 49;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
            BZip2CompressorInputStream bZip2CompressorInputStream = new BZip2CompressorInputStream(new ByteArrayInputStream(byteValues2));
            try {
                int value;
                byte[] buffer = new byte[512];
                while ((value = bZip2CompressorInputStream.read(buffer, 0, buffer.length)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, value);
                }
            }
            finally {
                bZip2CompressorInputStream.close();
            }
            byteArrayOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    public static boolean handleLogCuttingButton(Player player, int buttonId, int value2) {
        if (player.interfaceAction == "normalCutting" && NormalLogFletchingAction.create(player, buttonId, value2) != null) {
            NormalLogFletchingAction.create(player, buttonId, value2).start();
            return true;
        }
        if (player.interfaceAction == "oakCutting" && OakLogFletchingAction.create(player, buttonId, value2) != null) {
            OakLogFletchingAction.create(player, buttonId, value2).start();
            return true;
        }
        if (player.interfaceAction == "acheyCutting" && AcheyLogFletchingAction.create(player, buttonId, value2) != null) {
            AcheyLogFletchingAction.create(player, buttonId, value2).start();
            return true;
        }
        if (player.interfaceAction == "willowCutting" && WillowLogFletchingAction.create(player, buttonId, value2) != null) {
            WillowLogFletchingAction.create(player, buttonId, value2).start();
            return true;
        }
        if (player.interfaceAction == "mapleCutting" && MapleLogFletchingAction.create(player, buttonId, value2) != null) {
            MapleLogFletchingAction.create(player, buttonId, value2).start();
            return true;
        }
        if (player.interfaceAction == "yewCutting" && YewLogFletchingAction.create(player, buttonId, value2) != null) {
            YewLogFletchingAction.create(player, buttonId, value2).start();
            return true;
        }
        if (player.interfaceAction == "magicCutting" && MagicLogFletchingAction.create(player, buttonId, value2) != null) {
            MagicLogFletchingAction.create(player, buttonId, value2).start();
            return true;
        }
        return false;
    }

    public static void giveChallengeQuestionAnswerItem(Player player, int itemId) {
        ChallengeQuestion challengeQuestion = ChallengeQuestion.forClueItemId(itemId);
        if (challengeQuestion == null) {
            return;
        }
        player.getInventoryManager().addOrDropItem(new ItemStack(challengeQuestion.getAnswerItemId(), 1));
    }

    public static boolean hasChallengeQuestionAnswerItem(Player player, int itemId) {
        ChallengeQuestion challengeQuestion = ChallengeQuestion.forClueItemId(itemId);
        if (challengeQuestion == null) {
            return false;
        }
        return player.getInventoryManager().containsItem(challengeQuestion.getAnswerItemId());
    }

    public static String[] getChallengeQuestionLines(int value2) {
        ChallengeQuestion challengeQuestion = ChallengeQuestion.forClueItemId(value2);
        if (challengeQuestion == null) {
            return null;
        }
        return challengeQuestion.getQuestionLines();
    }

    public static boolean showChallengeQuestionForAnswerItem(Player player, int itemId) {
        ChallengeQuestion challengeQuestion = ChallengeQuestion.forAnswerItemId(itemId);
        if (challengeQuestion == null) {
            return false;
        }
        player.packetSender.showInterface(6965);
        String[] lines = challengeQuestion.getQuestionLines();
        int[] textIds = CacheArchive.interfaceTextIdsForLineCount(lines.length);
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
                return new int[]{6971, 6972, 6973};
            }
        }
        return null;
    }
}
