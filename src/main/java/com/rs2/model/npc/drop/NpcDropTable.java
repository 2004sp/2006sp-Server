package com.rs2.model.npc.drop;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.npc.NpcDefinition;
import com.rs2.model.player.Player;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.FileUtil;
import java.util.ArrayList;

public final class NpcDropTable {
    private static NpcDropTable[] tablesByNpcId = new NpcDropTable[0];
    private NpcDropEntry[] guaranteedDrops;
    private NpcDropEntry[] weightedDrops;
    private NpcDropEntry[] independentDrops;
    private static int[] membersOnlyVirtualDropIds = new int[]{65001, 65002, 65003, 65004, 65005, 65007, 65009, 65011, 65012, 65013, 65014, 65015, 65018, 65020, 65021, 65022, 65023, 65024, 65025, 65026, 65027, 65028, 65029, 65030};

    public static void loadDropTables() {
        if (!FileUtil.exists("./data/npcs/Npc drops.dat")) {
            return;
        }
        ByteArrayReader byteArrayReader = new ByteArrayReader(FileUtil.readBytes("./data/npcs/Npc drops.dat"));
        int value = byteArrayReader.readUnsignedByte();
        int value2 = byteArrayReader.readUnsignedShort();
        tablesByNpcId = new NpcDropTable[value2];
        int index = 0;
        while (index < value2) {
            int value3 = byteArrayReader.readUnsignedByte();
            if (value3 == 2) {
                int value4 = byteArrayReader.readUnsignedShort();
                NpcDefinition.forId(index).setDropTableNpcIdOverride(value4);
            }
            if (value3 == 1) {
                NpcDropEntry[] npcDropEntryArray2 = null;
                NpcDropEntry[] npcDropEntryArray = null;
                NpcDropEntry[] npcDropEntryArray3 = null;
                NpcDropEntry[] npcDropEntryArray4 = null;
                NpcDropEntry[] npcDropEntryArray5 = null;
                NpcDropEntry[] npcDropEntryArray6 = null;
                NpcDropEntry[] npcDropEntryArray7 = null;
                int index2 = 0;
                while (index2 < 7) {
                    int value5 = byteArrayReader.readUnsignedByte();
                    if (index2 == 0) {
                        npcDropEntryArray2 = new NpcDropEntry[value5];
                    }
                    if (index2 == 1) {
                        npcDropEntryArray = new NpcDropEntry[value5];
                    }
                    if (index2 == 2) {
                        npcDropEntryArray3 = new NpcDropEntry[value5];
                    }
                    if (index2 == 3) {
                        npcDropEntryArray4 = new NpcDropEntry[value5];
                    }
                    if (index2 == 4) {
                        npcDropEntryArray5 = new NpcDropEntry[value5];
                    }
                    if (index2 == 5) {
                        npcDropEntryArray6 = new NpcDropEntry[value5];
                    }
                    if (index2 == 6) {
                        npcDropEntryArray7 = new NpcDropEntry[value5];
                    }
                    int index3 = 0;
                    while (index3 < value5) {
                        int initialValue = -1;
                        int initialValue2 = -1;
                        int initialValue3 = -1;
                        if (index2 == 5 || index2 == 6) {
                            initialValue = byteArrayReader.readUnsignedByte();
                            if (value == 1) {
                                initialValue2 = byteArrayReader.readUnsignedByte();
                            }
                            if (value == 2) {
                                initialValue2 = byteArrayReader.readUnsignedShort();
                            }
                            initialValue3 = byteArrayReader.readInt();
                        }
                        int value6 = byteArrayReader.readUnsignedByte();
                        int initialValue4 = -1;
                        int initialValue5 = -1;
                        int initialValue6 = -1;
                        int initialValue7 = -1;
                        int[] integerValues = null;
                        int[] integerValues2 = null;
                        int[] integerValues3 = null;
                        int[] integerValues4 = null;
                        if (value6 != 3 && value6 != 4) {
                            initialValue4 = byteArrayReader.readUnsignedShort();
                        }
                        if (value6 == 0) {
                            initialValue5 = byteArrayReader.readUnsignedShort();
                        }
                        if (value6 == 1) {
                            initialValue6 = byteArrayReader.readUnsignedShort();
                            initialValue7 = byteArrayReader.readUnsignedShort();
                        }
                        if (value6 == 2) {
                            int value7 = byteArrayReader.readUnsignedByte();
                            integerValues = new int[value7];
                            int index4 = 0;
                            while (index4 < value7) {
                                integerValues[index4] = byteArrayReader.readUnsignedShort();
                                ++index4;
                            }
                        }
                        if (value6 == 3) {
                            int value8 = byteArrayReader.readUnsignedByte();
                            integerValues = new int[value8];
                            integerValues2 = new int[value8];
                            int index5 = 0;
                            while (index5 < value8) {
                                integerValues2[index5] = byteArrayReader.readUnsignedShort();
                                integerValues[index5] = byteArrayReader.readUnsignedShort();
                                ++index5;
                            }
                        }
                        if (value6 == 4) {
                            int value9 = byteArrayReader.readUnsignedByte();
                            integerValues2 = new int[value9];
                            integerValues3 = new int[value9];
                            integerValues4 = new int[value9];
                            int index6 = 0;
                            while (index6 < value9) {
                                integerValues2[index6] = byteArrayReader.readUnsignedShort();
                                integerValues3[index6] = byteArrayReader.readUnsignedShort();
                                integerValues4[index6] = byteArrayReader.readUnsignedShort();
                                ++index6;
                            }
                        }
                        NpcDropEntry npcDropEntry = new NpcDropEntry(initialValue4, initialValue5, initialValue6, initialValue7, integerValues2, integerValues, initialValue, initialValue2, initialValue3, integerValues3, integerValues4);
                        if (index2 == 0) {
                            npcDropEntryArray2[index3] = npcDropEntry;
                        }
                        if (index2 == 1) {
                            npcDropEntryArray[index3] = npcDropEntry;
                        }
                        if (index2 == 2) {
                            npcDropEntryArray3[index3] = npcDropEntry;
                        }
                        if (index2 == 3) {
                            npcDropEntryArray4[index3] = npcDropEntry;
                        }
                        if (index2 == 4) {
                            npcDropEntryArray5[index3] = npcDropEntry;
                        }
                        if (index2 == 5) {
                            npcDropEntryArray6[index3] = npcDropEntry;
                        }
                        if (index2 == 6) {
                            npcDropEntryArray7[index3] = npcDropEntry;
                        }
                        ++index3;
                    }
                    ++index2;
                }
                NpcDropTable.tablesByNpcId[index] = new NpcDropTable(npcDropEntryArray2, npcDropEntryArray, npcDropEntryArray3, npcDropEntryArray4, npcDropEntryArray5, npcDropEntryArray6, npcDropEntryArray7);
            }
            ++index;
        }
    }

    private NpcDropTable(NpcDropEntry[] npcDropEntryArray, NpcDropEntry[] npcDropEntryArray2, NpcDropEntry[] npcDropEntryArray3, NpcDropEntry[] npcDropEntryArray4, NpcDropEntry[] npcDropEntryArray5, NpcDropEntry[] npcDropEntryArray6, NpcDropEntry[] npcDropEntryArray7) {
        this.guaranteedDrops = npcDropEntryArray;
        this.weightedDrops = npcDropEntryArray6;
        this.independentDrops = npcDropEntryArray7;
    }

    public static NpcDropTable forNpcId(int npcId) {
        NpcDropTable npcDropTable;
        if (npcId < 0) {
            npcId = 1;
        }
        if ((npcDropTable = tablesByNpcId[npcId]) == null) {
            npcDropTable = new NpcDropTable(null, null, null, null, null, null, null);
        }
        return npcDropTable;
    }

    public final NpcDropEntry[] getGuaranteedDrops(Entity entity) {
        if (this.guaranteedDrops == null) {
            return null;
        }
        return NpcDropTable.filterDrops(this.guaranteedDrops, entity, false);
    }

    public final NpcDropEntry[] getWeightedDrops(Entity entity) {
        if (this.weightedDrops == null) {
            return null;
        }
        return NpcDropTable.filterDrops(this.weightedDrops, entity, true);
    }

    public final NpcDropEntry[] getIndependentDrops(Entity entity) {
        if (this.independentDrops == null) {
            return null;
        }
        return NpcDropTable.filterDrops(this.independentDrops, entity, true);
    }

    private static NpcDropEntry[] filterDrops(NpcDropEntry[] entries, Entity entity, boolean rejectUnavailableSeedTable) {
        Player player = null;
        if (entity.isPlayer()) {
            player = (Player)entity;
        }
        ArrayList<NpcDropEntry> arrayList = new ArrayList<NpcDropEntry>();
        int length = entries.length;
        int index = 0;
        while (index < length) {
            NpcDropEntry npcDropEntry = entries[index];
            if (NpcDropTable.isAllowedDrop(npcDropEntry, player, rejectUnavailableSeedTable)) {
                arrayList.add(npcDropEntry);
            }
            ++index;
        }
        return arrayList.toArray(new NpcDropEntry[arrayList.size()]);
    }

    private static boolean isAllowedDrop(NpcDropEntry npcDropEntry, Player player, boolean rejectUnavailableSeedTable) {
        if (npcDropEntry == null) {
            return false;
        }
        int itemId = npcDropEntry.getItemId();
        if (itemId < 65000) {
            return (itemId < 7956 || itemId > 8118) && ItemDefinition.isDefined(itemId) && (!ServerSettings.freeToPlayWorld && (player == null || player.isMember()) || !ItemDefinition.forId(itemId).isMembersOnly());
        }
        boolean membersOnly = false;
        int index = 0;
        while (index < membersOnlyVirtualDropIds.length) {
            int value = membersOnlyVirtualDropIds[index];
            if (value == itemId) {
                membersOnly = true;
            }
            ++index;
        }
        if ((ServerSettings.freeToPlayWorld || player != null && !player.isMember()) && membersOnly) {
            return false;
        }
        return !rejectUnavailableSeedTable || itemId - 65000 != 7 || ItemDefinition.isDefined(5300);
    }
}
