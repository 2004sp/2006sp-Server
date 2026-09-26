package com.rs2.cache.js5;

import com.rs2.model.objects.ObjectDefinition;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.npc.NpcDefinition;
import com.rs2.net.packet.InterfaceBridge;
import java.io.File;
import java.util.Map;

/** Command-line verification tool for the revision 443 cache during migration. */
public final class CacheProbe {
    private static final int CONFIG_INDEX = 2;
    private static final int OBJECT_GROUP = 6;
    private static final int NPC_GROUP = 9;
    private static final int ITEM_GROUP = 10;

    private CacheProbe() {
    }

    public static void main(String[] args) throws Exception {
        File cache = new File(args.length == 0 ? "cache" : args[0]);
        Js5CacheStore store = new Js5CacheStore(cache);
        try {
            for (int index = 0; index <= 13; index++) {
                Js5ReferenceTable table = store.readReferenceTable(index);
                System.out.println("index " + index + ": " + table.getGroupCount()
                        + " groups (reference protocol " + table.getProtocol() + ")");
            }

            Js5ReferenceTable configs = store.readReferenceTable(CONFIG_INDEX);
            System.out.println("443 config counts:");
            System.out.println("  objects: " + configs.getFileCount(OBJECT_GROUP));
            System.out.println("  npcs:    " + configs.getFileCount(NPC_GROUP));
            System.out.println("  items:   " + configs.getFileCount(ITEM_GROUP));

            Map<Integer, byte[]> itemFiles = store.readFiles(CONFIG_INDEX, ITEM_GROUP);
            Map<Integer, byte[]> npcFiles = store.readFiles(CONFIG_INDEX, NPC_GROUP);
            Map<Integer, byte[]> objectFiles = store.readFiles(CONFIG_INDEX, OBJECT_GROUP);
            System.out.println("unpacked config files:");
            System.out.println("  objects: " + objectFiles.size());
            System.out.println("  npcs:    " + npcFiles.size());
            System.out.println("  items:   " + itemFiles.size());

            if (itemFiles.size() != 10501 || npcFiles.size() != 5168
                    || objectFiles.size() != 20089) {
                throw new IllegalStateException("Unexpected revision 443 config counts");
            }
            if (ObjectDefinition.loadRevision443() != 20099) {
                throw new IllegalStateException("Unexpected decoded 443 object count");
            }
            ItemDefinition.loadRevision443();
            if (!"Coins".equals(ItemDefinition.forId(995).getName())
                    || !ItemDefinition.forId(995).isStackable()) {
                throw new IllegalStateException("Incorrect decoded 443 coin definition");
            }
            NpcDefinition.loadRevision443();
            if (!"Man".equals(NpcDefinition.forId(1).getName())) {
                throw new IllegalStateException("Incorrect decoded 443 NPC definition");
            }
            verifyInterfaces(store);
            Js5ReferenceTable maps = store.readReferenceTable(5);
            byte[] terrain = MapGroups.readTerrain(store, maps, 26, 69);
            byte[] locations = MapGroups.readLocations(store, maps, 26, 69);
            // This group used to fail when the disk version trailer was decrypted.
            byte[] trailerSensitiveLocations = MapGroups.readLocations(
                    store, maps, 29, 68);
            if (terrain == null || locations == null || terrain.length == 0
                    || locations.length == 0 || trailerSensitiveLocations == null
                    || trailerSensitiveLocations.length != 6365) {
                throw new IllegalStateException("Missing decoded 443 keyed map square");
            }
            WorldMaps.Square[] worldMaps = WorldMaps.load();
            if (worldMaps.length < 500) {
                throw new IllegalStateException("Too few 443 world map squares: "
                        + worldMaps.length);
            }
            int terrain443 = 0;
            int locations443 = 0;
            int legacyLocations = 0;
            for (WorldMaps.Square square : worldMaps) {
                if (square.terrainFrom443) terrain443++;
                if (square.locationsFrom443) locations443++;
                else {
                    legacyLocations++;
                    System.out.println("  377 locations " + (square.regionId >> 8)
                            + "_" + (square.regionId & 255) + " "
                            + square.locationIssue);
                }
            }
            System.out.println("world map squares: " + worldMaps.length
                    + " (443 terrain " + terrain443 + ", 443 locations "
                    + locations443 + ", 377 locations " + legacyLocations + ")");
            System.out.println("Revision 443 cache probe passed.");
        } finally {
            store.close();
        }
    }

    private static void verifyInterfaces(Js5CacheStore store) throws Exception {
        Js5ReferenceTable table = store.readReferenceTable(3);
        int componentCount = Interfaces.load(store);
        int expectedCount = 0;
        int traversedCount = 0;
        for (int groupId : table.getGroupIds()) {
            int[] fileIds = table.getFileIds(groupId);
            int expectedGroupCount = fileIds == null ? 0 : fileIds.length;
            Map<Integer, Interfaces.Component> group =
                    Interfaces.group(groupId);
            if (group.size() != expectedGroupCount) {
                throw new IllegalStateException("Interface group " + groupId
                        + " expected " + expectedGroupCount + " components but loaded "
                        + group.size());
            }
            expectedCount += expectedGroupCount;
            if (fileIds == null) continue;
            for (int childId : fileIds) {
                Interfaces.Component component =
                        Interfaces.forId(groupId, childId);
                if (component == null || component.getData().length == 0) {
                    throw new IllegalStateException("Missing interface component "
                            + groupId + ":" + childId);
                }
                traversedCount++;
            }
        }
        if (Interfaces.groupCount() != table.getGroupCount()
                || componentCount != expectedCount || traversedCount != expectedCount) {
            throw new IllegalStateException("Incomplete revision 443 interface traversal: "
                    + componentCount + "/" + expectedCount + " components across "
                    + Interfaces.groupCount() + "/" + table.getGroupCount()
                    + " groups");
        }
        System.out.println("interfaces: " + table.getGroupCount() + " groups, "
                + componentCount + " components (all reference-table files traversed)");
        verifyInterfaceMappings(table);
    }

    private static void verifyInterfaceMappings(Js5ReferenceTable table) {
        Map<Integer, Integer> groups = InterfaceBridge.groupMappings();
        Map<Integer, Integer> components = InterfaceBridge.componentMappings();
        System.out.println("interface bridge mappings:");
        if (groups.isEmpty() && components.isEmpty()) {
            System.out.println("  (none configured yet)");
            return;
        }
        for (Map.Entry<Integer, Integer> mapping : groups.entrySet()) {
            int legacyId = mapping.getKey();
            int groupId = mapping.getValue();
            if (table.getFileIds(groupId) == null) {
                throw new IllegalStateException("Legacy interface group " + legacyId
                        + " maps to missing 443 group " + groupId);
            }
            System.out.println("  legacy group " + legacyId + " -> 443 group " + groupId
                    + " (" + Interfaces.group(groupId).size() + " components)");
        }
        for (Map.Entry<Integer, Integer> mapping : components.entrySet()) {
            int legacyId = mapping.getKey();
            int packedId = mapping.getValue();
            Interfaces.Component component =
                    Interfaces.forPackedId(packedId);
            if (component == null) {
                throw new IllegalStateException("Legacy component " + legacyId
                        + " maps to missing 443 component " + (packedId >>> 16) + ":"
                        + (packedId & 0xffff));
            }
            System.out.println("  legacy " + legacyId + " -> 443 group "
                    + component.groupId + " component " + component.childId
                    + " (packed " + component.packedId + ", type " + component.type
                    + (component.modern ? ", IF3" : ", legacy-format") + ")");
        }
    }
}
