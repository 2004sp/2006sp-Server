package com.rs2.model.skill.mining;

import com.rs2.ServerSettings;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.item.ItemService;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.objects.WorldObjectLookup;
import com.rs2.model.player.Player;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.mining.MineableRockDefinition;
import com.rs2.model.skill.mining.MiningTask;
import com.rs2.model.skill.mining.ProspectingTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;
import com.rs2.util.GameplayTrace;
import com.rs2.util.RectangularArea;

public final class MiningManager {
    private Player player;
    private static int[] graniteItemIds = new int[]{6981, 6979, 6983};
    private static int[] sandstoneItemIds = new int[]{6977, 6971, 6975, 6973};
    private static int[] commonGemItemIds = new int[]{1623, 1623, 1623, 1623, 1621, 1621, 1621, 1619, 1619, 1617};
    private static int[] semipreciousGemItemIds = new int[]{1625, 1625, 1627, 1627, 1629};
    RectangularArea perfectGoldOreArea = new RectangularArea(2727, 9681, 2742, 9696, 0);

    public MiningManager(Player player) {
        this.player = player;
    }

    public final boolean canMineRock(int value2) {
        if (!MiningManager.isMineableRockObjectId(value2)) {
            return false;
        }
        if (!ServerSettings.miningEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return false;
        }
        if (this.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            Player player = this.player;
            player.packetSender.sendSoundEffect(1878, 1, 0);
            player = this.player;
            player.packetSender.sendGameMessage("Not enough space in your inventory.");
            if (this.player.getQuestState(0) != 1) {
                this.player.getDialogueManager().showOneLineStatement("Not enough space in your inventory.");
                this.player.setInteractionTargetId(0);
            }
            if (this.player.botEnabled) {
                this.player.currentBotTask.startWalkToBank(this.player);
            }
            return false;
        }
        if (ItemCombinationHandler.findUsableGatheringTool(this.player, 14) == null) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You do not have a pickaxe that you can use.");
            if (this.player.getQuestState(0) != 1) {
                this.player.getDialogueManager().showOneLineStatement("You do not have a pickaxe that you can use.");
                this.player.setInteractionTargetId(0);
            }
            if (this.player.botEnabled) {
                this.player.currentBotTask.startWalkToBank(this.player);
            }
            return false;
        }
        return SkillActionHelper.checkSkillRequirement(this.player, 14, MineableRockDefinition.forObjectId(value2) != null ? MineableRockDefinition.getRequiredLevel(MineableRockDefinition.forObjectId(value2)) : 0, "mine here");
    }

    public final void startMining(int value5, int value22, int value32) {
        if (!SkillActionHelper.isObjectPresent(value5, value22, value32, this.player.getPosition().getPlane())) {
            if (this.player.botEnabled) {
                this.player.interactWithBotObjectTargets(this.player.botInteractionTargetIds);
            }
            return;
        }
        ObjectManager.getInstance();
        Object dynamicObjectAt = ObjectManager.findDynamicObjectAt(value22, value32, this.player.getPosition().getPlane());
        if (MiningManager.getRestoredRockObjectId(value5) == -1 && dynamicObjectAt != null) {
            dynamicObjectAt = this.player;
            ((Player)dynamicObjectAt).packetSender.sendGameMessage("There is currently no ores remaining in this rock.");
            if (this.player.getQuestState(0) != 1) {
                this.player.getDialogueManager().showOneLineStatement("There is currently no ores remaining in this rock.");
                this.player.setInteractionTargetId(0);
            }
            dynamicObjectAt = this.player;
            ((Player)dynamicObjectAt).packetSender.sendSoundEffect(429, 1, 0);
            if (this.player.botEnabled) {
                this.player.interactWithBotObjectTargets(this.player.botInteractionTargetIds);
            }
            return;
        }
        if (this.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            dynamicObjectAt = this.player;
            ((Player)dynamicObjectAt).packetSender.sendGameMessage("Not enough space in your inventory.");
            if (this.player.botEnabled) {
                this.player.currentBotTask.startWalkToBank(this.player);
            }
            return;
        }
        dynamicObjectAt = this.player;
        ((Player)dynamicObjectAt).packetSender.sendGameMessage("You swing your pick at the rock.");
        if (this.player.getQuestState(0) != 1) {
            this.player.getDialogueManager().showTutorialInstructionOverlay("Please wait.", "", "Your character is now attempting to mine the rock.", "This should only take a few seconds.", "", true);
        }
        int value4 = this.player.nextActionSequence();
        MineableRockDefinition mineableRockDefinition = MineableRockDefinition.forObjectId(value5);
        if (mineableRockDefinition == null) {
            return;
        }
        GatheringToolDefinition gatheringToolDefinition = ItemCombinationHandler.findUsableGatheringTool(this.player, 14);
        if (gatheringToolDefinition == null) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You do not have a pickaxe that you can use.");
            if (this.player.botEnabled) {
                this.player.currentBotTask.startWalkToBank(this.player);
            }
            return;
        }
        int oreItemId = MineableRockDefinition.getOreItemId(mineableRockDefinition);
        int mineChanceLow = MineableRockDefinition.getMineChanceLow(mineableRockDefinition);
        int mineChanceHigh = MineableRockDefinition.getMineChanceHigh(mineableRockDefinition);
        int toolSpeed = (int)gatheringToolDefinition.getToolSpeed();
        double depletionChance = MineableRockDefinition.getDepletionChance(mineableRockDefinition);
        if (oreItemId == 0) {
            Player player = this.player;
            player.packetSender.sendGameMessage("There is currently no ores remaining in this rock.");
            player = this.player;
            player.packetSender.sendSoundEffect(429, 1, 0);
            if (this.player.botEnabled) {
                this.player.interactWithBotObjectTargets(this.player.botInteractionTargetIds);
            }
            return;
        }
        int baseExperience = MineableRockDefinition.getBaseExperience(mineableRockDefinition);
        int respawnTicks = MineableRockDefinition.getRespawnTicks(mineableRockDefinition);
        MineableRockDefinition.getRequiredLevel(mineableRockDefinition);
        this.player.resetAnimation();
        this.player.getUpdateState().setAnimation(gatheringToolDefinition.getGatherAnimationId());
        this.player.gatheringHazardCounter = 0;
        if (ServerSettings.randomEventsMode == 0 && GameUtil.randomInt(800) == 0 && this.player.getQuestState(0) == 1 && !this.player.botEnabled && !this.player.isInTutorialIsland()) {
            int randomEventRockObjectId = MiningManager.getRandomEventRockObjectId(value5, new Position(value22, value32, this.player.getPosition().getPlane()));
            ObjectManager.getInstance();
            DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(value22, value32, this.player.getPosition().getPlane());
            if (dynamicObject == null && randomEventRockObjectId != -1) {
                randomEventRockObjectId = SkillActionHelper.getObjectOrientation(value5, value22, value32, this.player.getPosition().getPlane());
                int objectType = SkillActionHelper.getObjectType(value5, value22, value32, this.player.getPosition().getPlane());
                ObjectManager.getInstance().addDynamicObject(new DynamicObject(MiningManager.getRandomEventRockObjectId(value5, new Position(value22, value32, this.player.getPosition().getPlane())), value22, value32, this.player.getPosition().getPlane(), randomEventRockObjectId, objectType, value5, 15), true);
            }
        }
        this.player.setActiveCycleEvent(new MiningTask(this, value4, value5, value22, value32, gatheringToolDefinition, mineChanceLow, mineChanceHigh, oreItemId, baseExperience, depletionChance, respawnTicks));
        CycleEventHandler.getInstance().schedule(this.player, this.player.getActiveCycleEvent(), toolSpeed);
    }

    public static int rotateDepletedRockOrientation(int value2) {
        switch (value2) {
            case 1: {
                return 2;
            }
            case 2: {
                return 4;
            }
            case 3: {
                return 1;
            }
            case 4: {
                return 0;
            }
        }
        return value2;
    }

    public static int rollMinedItemId(int itemId, int value2) {
        switch (itemId) {
            case 0: {
                return commonGemItemIds[GameUtil.randomExclusive(10)];
            }
            case 2111: {
                if (GameUtil.randomInclusive(2) == 0) {
                    return commonGemItemIds[GameUtil.randomExclusive(10)];
                }
                return semipreciousGemItemIds[GameUtil.randomExclusive(5)];
            }
            case 10947: {
                return graniteItemIds[GameUtil.randomInclusive(2)];
            }
            case 10946: {
                return sandstoneItemIds[GameUtil.randomInclusive(3)];
            }
        }
        return value2;
    }

    public static boolean isMineableRockObjectId(int objectId) {
        return MineableRockDefinition.forObjectId(objectId) != null;
    }

    public final boolean prospectRock(int value3) {
        int[] integerValues = new int[]{10587, 10585, 10586, 14832, 14833, 14834, 10944, 10945, 9723, 9724, 9725, 11555, 11552, 11553, 11554, 11557, 11556, 450, 451, 452};
        int index = 0;
        while (index < 20) {
            int value2 = integerValues[index];
            if (value3 == value2) {
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("mining prospect depleted player=" + GameplayTrace.describe(this.player) + " rockObjectId=" + value3);
                }
                Player player = this.player;
                player.packetSender.sendGameMessage("There is currently no ores remaining in this rock.");
                player = this.player;
                player.packetSender.sendSoundEffect(429, 1, 0);
                return true;
            }
            ++index;
        }
        MineableRockDefinition mineableRockDefinition = MineableRockDefinition.forObjectId(value3);
        if (mineableRockDefinition == null) {
            return false;
        }
        if (this.player.getQuestState(0) != 1) {
            this.player.getDialogueManager().showTutorialInstructionOverlay("Please wait.", "", "Your character is now attempting to prospect the rock. This", "should only take a few seconds.", "", true);
        }
        Player player = this.player;
        player.packetSender.sendGameMessage("You examine the rock for ores...");
        this.player.setActionLocked(true);
        int oreItemId = MiningManager.rollMinedItemId(value3, MineableRockDefinition.getOreItemId(mineableRockDefinition));
        ItemService.getInstance();
        String oreName = ItemService.getItemName(oreItemId).toLowerCase().replaceAll("ore", "").trim();
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("mining prospect scheduled player=" + GameplayTrace.describe(this.player) + " rockObjectId=" + value3 + " oreItemId=" + oreItemId + " oreName=" + oreName);
        }
        CycleEventHandler.getInstance().schedule(this.player, new ProspectingTask(this, value3, oreName), 5);
        return true;
    }

    public static int getRandomEventRockObjectId(int objectId, Position position) {
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectByIdAt(objectId, position.getX(), position.getY(), position.getPlane());
        if (loadedWorldObject.getWorldObject().getObjectId() >= 2090 && loadedWorldObject.getWorldObject().getObjectId() <= 2111) {
            return loadedWorldObject.getWorldObject().getObjectId() + 29;
        }
        if (loadedWorldObject.getWorldObject().getObjectId() >= 10946 && loadedWorldObject.getWorldObject().getObjectId() <= 10949) {
            return loadedWorldObject.getWorldObject().getObjectId() + 246;
        }
        if (loadedWorldObject.getWorldObject().getObjectId() >= 11183 && loadedWorldObject.getWorldObject().getObjectId() <= 11191) {
            return loadedWorldObject.getWorldObject().getObjectId() - 1456;
        }
        if (loadedWorldObject.getWorldObject().getObjectId() >= 10583 && loadedWorldObject.getWorldObject().getObjectId() <= 10584) {
            return loadedWorldObject.getWorldObject().getObjectId() + 583;
        }
        if (loadedWorldObject.getWorldObject().getObjectId() >= 14850 && loadedWorldObject.getWorldObject().getObjectId() <= 14864) {
            return loadedWorldObject.getWorldObject().getObjectId() - 15;
        }
        return -1;
    }

    public static int getRestoredRockObjectId(int objectId) {
        if (objectId >= 2119 && objectId <= 2140) {
            return objectId - 29;
        }
        if (objectId >= 9727 && objectId <= 9735) {
            return objectId + 1456;
        }
        if (objectId >= 11166 && objectId <= 11167) {
            return objectId - 583;
        }
        if (objectId >= 11168 && objectId <= 11182) {
            return objectId - 1460;
        }
        if (objectId >= 11192 && objectId <= 11195) {
            return objectId - 246;
        }
        if (objectId >= 11424 && objectId <= 11432) {
            return objectId + 506;
        }
        if (objectId >= 11433 && objectId <= 11444) {
            return objectId + 521;
        }
        if (objectId >= 11915 && objectId <= 11920) {
            return objectId + 33;
        }
        if (objectId >= 11921 && objectId <= 11923) {
            return objectId + 24;
        }
        if (objectId >= 11925 && objectId <= 11929) {
            return objectId + 15;
        }
        if (objectId >= 14835 && objectId <= 14849) {
            return objectId + 15;
        }
        return -1;
    }

    static Player getPlayer(MiningManager miningManager) {
        return miningManager.player;
    }

    static double getExperienceForMinedItem(int itemId, int value2) {
        switch (itemId) {
            case 6979: {
                return 50.0;
            }
            case 6981: {
                return 60.0;
            }
            case 6983: {
                return 75.0;
            }
            case 6971: {
                return 30.0;
            }
            case 6973: {
                return 40.0;
            }
            case 6975: {
                return 50.0;
            }
            case 6977: {
                return 60.0;
            }
        }
        return value2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static int getDepletedRockObjectId(int objectId) {
        int[] integerValues = new int[]{9708, 9711, 9714, 9717, 9720};
        int[] integerValues2 = new int[]{9709, 9712, 9715, 9718, 9721};
        int[] integerValues3 = new int[]{9710, 9713, 9716, 9719, 9722};
        int[] integerValues4 = new int[]{11183, 11186, 11189, 11930, 11933, 11936, 11939, 11942, 11945, 11948, 11951, 11954, 11957, 11960, 11963};
        int[] integerValues5 = new int[]{11184, 11187, 11190, 11931, 11934, 11937, 11940, 11943, 11946, 11949, 11952, 11955, 11958, 11961, 11964};
        int[] integerValues6 = new int[]{11185, 11188, 11191, 11932, 11935, 11938, 11941, 11944, 11947, 11950, 11953, 11956, 11959, 11962, 11965};
        int[] integerValues7 = new int[]{14850, 14853, 14856, 14859, 14862};
        int[] integerValues8 = new int[]{14851, 14854, 14857, 14860, 14863};
        int[] integerValues9 = new int[]{14852, 14855, 14858, 14861, 14864};
        if (objectId == 10946) return 10944;
        if (objectId == 10948) {
            return 10944;
        }
        if (objectId == 10947) return 10945;
        if (objectId == 10949) {
            return 10945;
        }
        if (objectId == 2110) {
            if (GameplayHelper.isObjectDefinitionIdValid(10587)) {
                return 10587;
            }
            if (ServerSettings.cacheVersion >= 270) return 451;
            return 452;
        }
        if (objectId == 10583) {
            return 10585;
        }
        if (objectId == 10584) {
            return 10586;
        }
        int[] integerValues10 = integerValues;
        int index = 0;
        while (index < 5) {
            int value = integerValues10[index];
            if (objectId == value) {
                return 9723;
            }
            ++index;
        }
        integerValues10 = integerValues2;
        index = 0;
        while (index < 5) {
            int value2 = integerValues10[index];
            if (objectId == value2) {
                return 9724;
            }
            ++index;
        }
        integerValues10 = integerValues3;
        index = 0;
        while (index < 5) {
            int value3 = integerValues10[index];
            if (objectId == value3) {
                return 9725;
            }
            ++index;
        }
        integerValues10 = integerValues4;
        index = 0;
        while (index < 15) {
            int value4 = integerValues10[index];
            if (objectId == value4) {
                if (objectId < 11945) return 11552;
                return 11555;
            }
            ++index;
        }
        integerValues10 = integerValues5;
        index = 0;
        while (index < 15) {
            int value5 = integerValues10[index];
            if (objectId == value5) {
                if (objectId < 11945) return 11553;
                return 11556;
            }
            ++index;
        }
        integerValues10 = integerValues6;
        index = 0;
        while (index < 15) {
            int value6 = integerValues10[index];
            if (objectId == value6) {
                if (objectId < 11945) return 11554;
                return 11557;
            }
            ++index;
        }
        integerValues10 = integerValues7;
        index = 0;
        while (index < 5) {
            int value7 = integerValues10[index];
            if (objectId == value7) {
                return 14832;
            }
            ++index;
        }
        integerValues10 = integerValues8;
        index = 0;
        while (index < 5) {
            int value8 = integerValues10[index];
            if (objectId == value8) {
                return 14833;
            }
            ++index;
        }
        integerValues10 = integerValues9;
        index = 0;
        while (index < 5) {
            int value9 = integerValues10[index];
            if (objectId == value9) {
                return 14834;
            }
            ++index;
        }
        if (objectId % 2 == 0) return 450;
        if (objectId == 3043) {
            return 450;
        }
        if (ServerSettings.cacheVersion >= 270) return 451;
        return 452;
    }
}

