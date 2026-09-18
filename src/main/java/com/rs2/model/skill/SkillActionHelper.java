package com.rs2.model.skill;

import com.rs2.ServerSettings;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.objects.WorldObject;
import com.rs2.model.objects.WorldObjectLookup;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import java.util.Random;

public final class SkillActionHelper {
    private static String[] skillNames;

    static {
        new Random(System.currentTimeMillis());
        int[] integerValues = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        skillNames = new String[]{"Attack", "Defence", "Strength", "Hitpoints", "Range", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer", "Farming", "Runecrafting"};
    }

    public static boolean checkSkillRequirement(Player player, int skillId, int value2, String skillId2) {
        if (player.getSkillManager().getBaseLevel(skillId) < value2) {
            player.getDialogueManager().showOneLineStatement("You need a " + skillNames[skillId] + " level of " + value2 + " to " + skillId2 + ".");
            return false;
        }
        return true;
    }

    public static boolean isObjectPresent(int objectId, int value2, int value32, int value42) {
        return SkillActionHelper.findWorldObjectById(objectId, value2, value32, value42) != null;
    }

    public static WorldObject findWorldObjectById(int objectId, int value2, int value32, int value42) {
        ObjectManager.getInstance();
        DynamicObject dynamicObject = ObjectManager.findDynamicObjectByIdAt(objectId, value2, value32, value42);
        if (dynamicObject != null) {
            return dynamicObject.getWorldObject();
        }
        ObjectManager.getInstance();
        dynamicObject = ObjectManager.findDynamicObjectByIdAt(ServerSettings.placeholderObjectId, value2, value32, value42);
        if (dynamicObject != null) {
            return null;
        }
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectByIdAt(objectId, value2, value32, value42);
        if (loadedWorldObject != null) {
            return loadedWorldObject.getWorldObject();
        }
        return null;
    }

    public static WorldObject findWorldObjectAt(int objectId, int value2, int value32) {
        ObjectManager.getInstance();
        DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(objectId, value2, value32);
        if (dynamicObject != null) {
            return dynamicObject.getWorldObject();
        }
        ObjectManager.getInstance();
        dynamicObject = ObjectManager.findDynamicObjectByIdAt(ServerSettings.placeholderObjectId, objectId, value2, value32);
        if (dynamicObject != null) {
            return null;
        }
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectAt(objectId, value2, value32);
        if (loadedWorldObject != null) {
            return loadedWorldObject.getWorldObject();
        }
        return null;
    }

    public static int getObjectOrientation(int objectId, int value2, int value32, int value42) {
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectByIdAt(objectId, value2, value32, value42);
        if (loadedWorldObject != null) {
            return loadedWorldObject.getOrientation();
        }
        return 0;
    }

    public static int getObjectType(int objectId, int value2, int value32, int value42) {
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectByIdAt(objectId, value2, value32, value42);
        if (loadedWorldObject != null) {
            return loadedWorldObject.getType();
        }
        return 10;
    }

    public static boolean shouldTriggerRandomEvent(Player player) {
        if (player.ownedNpc != null) {
            return false;
        }
        if (ServerSettings.randomEventsMode != 0) {
            return false;
        }
        return GameUtil.randomInclusive(2000) == 0;
    }
}

