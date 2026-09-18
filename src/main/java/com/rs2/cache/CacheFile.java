package com.rs2.cache;

import com.rs2.model.Position;
import com.rs2.model.clue.ClueKeyHandler;
import com.rs2.model.clue.SearchClue;
import com.rs2.model.clue.TreasureTrailManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.WorldObject;
import com.rs2.model.player.Player;
import com.rs2.net.packet.PacketSender;
import java.nio.ByteBuffer;
import java.util.Random;

public class CacheFile {
    private ByteBuffer buffer;

    public CacheFile(int value3, int value22, ByteBuffer byteBuffer) {
        this.buffer = byteBuffer;
    }

    public ByteBuffer getBuffer() {
        return this.buffer;
    }

    public static boolean showSearchClue(Player player, int value2) {
        SearchClue searchClue = SearchClue.forClueItemId(value2);
        if (searchClue == null) {
            return false;
        }
        String[] stringValues;
        player.packetSender.showInterface(6965);
        int index = 0;
        while (index < searchClue.getClueTextLines().length) {
            int[] integerValues;
            PacketSender packetSender = player.packetSender;
            String clueTextLines = searchClue.getClueTextLines()[index];
            stringValues = searchClue.getClueTextLines();
            switch (stringValues.length) {
                case 1: {
                    int[] integerValues2 = new int[1];
                    integerValues = integerValues2;
                    integerValues2[0] = 6971;
                    break;
                }
                case 2: {
                    int[] integerValues3 = new int[2];
                    integerValues3[0] = 6971;
                    integerValues = integerValues3;
                    integerValues3[1] = 6972;
                    break;
                }
                case 3: {
                    int[] integerValues4 = new int[3];
                    integerValues4[0] = 6970;
                    integerValues4[1] = 6971;
                    integerValues = integerValues4;
                    integerValues4[2] = 6972;
                    break;
                }
                case 4: {
                    int[] integerValues5 = new int[4];
                    integerValues5[0] = 6970;
                    integerValues5[1] = 6971;
                    integerValues5[2] = 6972;
                    integerValues = integerValues5;
                    integerValues5[3] = 6973;
                    break;
                }
                case 5: {
                    int[] integerValues6 = new int[5];
                    integerValues6[0] = 6969;
                    integerValues6[1] = 6970;
                    integerValues6[2] = 6971;
                    integerValues6[3] = 6972;
                    integerValues = integerValues6;
                    integerValues6[4] = 6973;
                    break;
                }
                case 6: {
                    int[] integerValues7 = new int[6];
                    integerValues7[0] = 6969;
                    integerValues7[1] = 6970;
                    integerValues7[2] = 6971;
                    integerValues7[3] = 6972;
                    integerValues7[4] = 6973;
                    integerValues = integerValues7;
                    integerValues7[5] = 6974;
                    break;
                }
                case 7: {
                    int[] integerValues8 = new int[7];
                    integerValues8[0] = 6968;
                    integerValues8[1] = 6969;
                    integerValues8[2] = 6970;
                    integerValues8[3] = 6971;
                    integerValues8[4] = 6972;
                    integerValues8[5] = 6973;
                    integerValues = integerValues8;
                    integerValues8[6] = 6974;
                    break;
                }
                case 8: {
                    int[] integerValues9 = new int[8];
                    integerValues9[0] = 6968;
                    integerValues9[1] = 6969;
                    integerValues9[2] = 6970;
                    integerValues9[3] = 6971;
                    integerValues9[4] = 6972;
                    integerValues9[5] = 6973;
                    integerValues9[6] = 6974;
                    integerValues = integerValues9;
                    integerValues9[7] = 6975;
                    break;
                }
                default: {
                    integerValues = null;
                }
            }
            packetSender.sendInterfaceText(clueTextLines, integerValues[index]);
            ++index;
        }
        return true;
    }

    public static int randomSearchClueItemForLevel(int itemId) {
        int value = new Random().nextInt(SearchClue.values().length);
        while (SearchClue.values()[value].getLevel() != itemId) {
            value = new Random().nextInt(SearchClue.values().length);
        }
        return SearchClue.values()[value].getClueItemId();
    }

    public static boolean searchClueObject(Player player, WorldObject worldObject) {
        SearchClue searchClue = SearchClue.forPosition(new Position(worldObject.getPosition().getX(), worldObject.getPosition().getY(), player.getPosition().getPlane()));
        if (searchClue == null) {
            return false;
        }
        if (!player.getInventoryManager().containsItem(searchClue.getClueItemId()) || player.getPosition().getPlane() != searchClue.getPosition().getPlane()) {
            return false;
        }
        if (!ClueKeyHandler.consumeRequiredKey(player, searchClue.getClueItemId())) {
            return true;
        }
        if (searchClue.getReplacementObjectId() > 0) {
            new DynamicObject(searchClue.getReplacementObjectId(), worldObject.getPosition().getX(), worldObject.getPosition().getY(), player.getPosition().getPlane(), worldObject.getOrientation(), worldObject.getType(), worldObject.getObjectId(), 30);
        }
        player.getInventoryManager().removeItem(new ItemStack(searchClue.getClueItemId(), 1));
        player.getUpdateState().setAnimation(searchClue.getAnimationId());
        TreasureTrailManager.advanceOrCompleteTrail(player, searchClue.getLevel(), "You've found another clue!", false, "You've found a casket!");
        return true;
    }
}

