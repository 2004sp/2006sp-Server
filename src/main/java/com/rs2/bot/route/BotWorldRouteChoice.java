package com.rs2.bot.route;

import com.rs2.bot.route.BotWorldRoute;
import com.rs2.model.clue.CrypticDigClue;
import com.rs2.model.player.Player;
import com.rs2.net.packet.PacketSender;
import java.util.Random;

public class BotWorldRouteChoice {
    BotWorldRoute route;
    boolean reversed;

    public BotWorldRouteChoice(BotWorldRoute botWorldRoute, boolean reversed) {
        this.route = botWorldRoute;
        this.reversed = reversed;
    }

    public final boolean isReversed() {
        return this.reversed;
    }

    public static boolean showCrypticDigClue(Player player, int value2) {
        CrypticDigClue crypticDigClue = CrypticDigClue.forClueItemId(value2);
        if (crypticDigClue == null) {
            return false;
        }
        String[] stringValues;
        player.packetSender.showInterface(6965);
        int index = 0;
        while (index < crypticDigClue.getClueTextLines().length) {
            int[] integerValues;
            PacketSender packetSender = player.packetSender;
            String clueTextLines = crypticDigClue.getClueTextLines()[index];
            stringValues = crypticDigClue.getClueTextLines();
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

    public static int randomCrypticDigClueItemForLevel(int itemId) {
        int value = new Random().nextInt(CrypticDigClue.values().length);
        while (CrypticDigClue.values()[value].getLevel() != itemId) {
            value = new Random().nextInt(CrypticDigClue.values().length);
        }
        return CrypticDigClue.values()[value].getClueItemId();
    }
}

