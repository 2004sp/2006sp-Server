package com.rs2.model.player;

import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.CharacterFileBankTab;
import com.rs2.model.quest.QuestDefinition;
import java.util.ArrayList;

public final class CharacterFileRecord {
    public String username;
    public String password;
    public String hostAddress = "0.0.0.0";
    public String reservedVersion11String = "";
    public int playerRights;
    public String legacyProfileString = "";
    public String profileString1 = "";
    public String profileString2 = "";
    public long lastSavedMillis;
    public long totalPlayTimeMillis;
    public long createdAtMillis;
    public boolean loginRestrictionExempt;
    public boolean memberFlag;
    public int donatorPoints;
    public int petUnlockFlags;
    public int x = 3093;
    public int y = 3104;
    public int plane = 0;
    public int skeletonSkinUnlocked = 0;
    public int gender;
    public int npcKillCount;
    public int playerKillCount;
    public int deathCount;
    public int easyCluesCompleted;
    public int mediumCluesCompleted;
    public int hardCluesCompleted;
    public int soldItemsValue;
    public int boughtItemsValue;
    public int duelWins;
    public int duelLosses;
    public int legacyQuestPoints;
    public boolean autoRetaliate;
    public int fightMode;
    public int brightness = 2;
    public int mouseButtons;
    public int publicChatEffects = 1;
    public int splitPrivateChat;
    public int acceptAid;
    public int musicVolume;
    public int effectVolume;
    public int specialEnergy = 100;
    public boolean changingBankPin;
    public boolean deletingBankPin;
    public int pinAppendYear = -1;
    public int pinAppendDate = -1;
    public int bindingNecklaceCharge = 15;
    public int ringOfForgingLife = 140;
    public int ringOfRecoilLife = 40;
    public int skullTimer;
    public int runEnergyRaw = 10000;
    public boolean running;
    public int abyssMageNpcId;
    public long muteExpires;
    public long banExpires;
    public int barrowsKillCount;
    public int barrowsTargetBrotherIndex;
    public int poisonImmunityTicks;
    public int antifireTicks;
    public int teleblockTicks;
    public double poisonDamage;
    public int slayerMasterId;
    public String slayerTaskName = "";
    public int slayerTaskAmount;
    public boolean usingAncients;
    public boolean brimhavenOpen;
    public boolean killedClueAttacker;
    public int gangAffiliation;
    public int piratesTreasureBananaCrateCount;
    public boolean treasureTrailNavigationTaught;
    public int coalTruckAmount;
    public int treasureTrailStepCount;
    public boolean cluePuzzleSolved;
    public int[] currentPin = new int[]{-1, -1, -1, -1};
    public int[] pendingPin = new int[]{-1, -1, -1, -1};
    public int[] essencePouchAmounts = new int[4];
    public int[] appearanceParts = new int[7];
    public int[] appearanceColors = new int[5];
    public ItemStack[] inventoryItems = new ItemStack[28];
    public ItemStack[] equipmentItems = new ItemStack[14];
    public long[] friendsList = new long[200];
    public long[] ignoreList = new long[100];
    public int[] queuedLoginItemIds = new int[28];
    public int[] queuedLoginItemAmounts = new int[28];
    public boolean[] barrowsKilledBrothers = new boolean[6];
    public int[] allotmentGrowthStages = new int[8];
    public int[] allotmentCropIds = new int[8];
    public int[] allotmentHarvestAmounts = new int[8];
    public int[] allotmentPatchStates = new int[8];
    public long[] allotmentLastUpdateTicks = new long[8];
    public double[] allotmentDiseaseChanceMultipliers = new double[8];
    public boolean[] allotmentProtectionFlags = new boolean[8];
    public int[] bushGrowthStages = new int[4];
    public int[] bushCropIds = new int[4];
    public int[] bushPatchStates = new int[4];
    public long[] bushLastUpdateTicks = new long[4];
    public double[] bushDiseaseChanceMultipliers = new double[4];
    public boolean[] bushSavedFlags = new boolean[4];
    public int[] flowerGrowthStages = new int[4];
    public int[] flowerCropIds = new int[4];
    public int[] flowerPatchStates = new int[4];
    public long[] flowerLastUpdateTicks = new long[4];
    public double[] flowerDiseaseChanceMultipliers = new double[4];
    public int[] fruitTreeGrowthStages = new int[4];
    public int[] fruitTreeIds = new int[4];
    public int[] fruitTreePatchStates = new int[4];
    public long[] fruitTreeLastUpdateTicks = new long[4];
    public double[] fruitTreeDiseaseChanceMultipliers = new double[4];
    public boolean[] fruitTreeSavedFlags = new boolean[4];
    public int[] herbGrowthStages = new int[4];
    public int[] herbCropIds = new int[4];
    public int[] herbHarvestAmounts = new int[4];
    public int[] herbPatchStates = new int[4];
    public long[] herbLastUpdateTicks = new long[4];
    public double[] herbDiseaseChanceMultipliers = new double[4];
    public int[] hopsGrowthStages = new int[4];
    public int[] hopsCropIds = new int[4];
    public int[] hopsHarvestAmounts = new int[4];
    public int[] hopsPatchStates = new int[4];
    public long[] hopsLastUpdateTicks = new long[4];
    public double[] hopsDiseaseChanceMultipliers = new double[4];
    public boolean[] hopsProtectionFlags = new boolean[4];
    public int[] specialTreeGrowthStages = new int[4];
    public int[] specialTreeIds = new int[4];
    public int[] specialTreePatchStates = new int[4];
    public long[] specialTreeLastUpdateTicks = new long[4];
    public double[] specialTreeDiseaseChanceMultipliers = new double[4];
    public int[] specialCropGrowthStages = new int[4];
    public int[] specialCropIds = new int[4];
    public int[] specialCropPatchStates = new int[4];
    public long[] specialCropLastUpdateTicks = new long[4];
    public double[] specialCropDiseaseChanceMultipliers = new double[4];
    public int[] treeGrowthStages = new int[4];
    public int[] treeIds = new int[4];
    public int[] treePatchData = new int[4];
    public int[] treePatchStates = new int[4];
    public long[] treeLastUpdateTicks = new long[4];
    public double[] treeDiseaseChanceMultipliers = new double[4];
    public boolean[] treeSavedFlags = new boolean[4];
    public int[] compostBinStates = new int[4];
    public long[] compostBinLastUpdateTicks = new long[4];
    public int[] compostBinItemIds = new int[4];
    public int[] farmingToolStoreAmounts = new int[18];
    public int[] configStates = new int[2000];
    public int[] questProgress = new int[QuestDefinition.questStateCapacity];
    public int[] questBitFlags = new int[QuestDefinition.questStateCapacity];
    public int[] questHookStates = new int[100];
    public int[] currentLevels = new int[22];
    public long[] skillExperience = new long[22];
    public boolean barrowsDoorPuzzleSolved;
    public boolean barrowsChestOpened;
    private int cachedItemValue = 0;
    public int barrowsRewardPotential = 0;
    public boolean[] grandExchangeSellOfferFlags = new boolean[6];
    public int[] grandExchangeItemIds = new int[6];
    public int[] grandExchangeQuantities = new int[6];
    public int[] grandExchangeUnitPrices = new int[6];
    public boolean[] grandExchangeCancelledFlags = new boolean[6];
    public int[] grandExchangeCompletedQuantities = new int[6];
    public int[] grandExchangeTotalPrices = new int[6];
    public int[] grandExchangePrimaryCollectAmounts = new int[6];
    public int[] grandExchangeSecondaryCollectAmounts = new int[6];
    public boolean[] grandExchangeFinishMessagePending = new boolean[6];
    public int flourMillHopperGrainCount = 0;
    public int questRandomSeed;
    public int publicChatMode = 0;
    public int privateChatMode = 0;
    public int tradeMode = 0;
    public int reservedSaveInt1 = 0;
    public long reservedSaveLong1 = 0L;
    public int reservedSaveInt2 = 0;
    public int reservedSaveInt3 = 0;
    public int familyCrestGauntletItemId = 778;
    public int mageArenaFlamesOfZamorakCastsRemaining = 100;
    public int mageArenaSaradominStrikeCastsRemaining = 100;
    public int mageArenaClawsOfGuthixCastsRemaining = 100;
    public int mageArenaProgressStage = 0;
    public int telekineticPizazzPoints = 0;
    public int enchantmentPizazzPoints = 0;
    public int alchemistPizazzPoints = 0;
    public int graveyardPizazzPoints = 0;
    public boolean bonesToPeachesUnlocked = false;
    public int telekineticMazeIndex = 0;
    public boolean telekineticMazeSolved = false;
    public int telekineticConsecutiveMazesSolved = 0;
    public ArrayList bankTabs = new ArrayList();
    private int maxBankTabs = 10;
    public int gameMode = 0;
    public int barrowsRunsCompleted = 0;
    public int botReservedGoalInt4 = -1;
    public int botReservedGoalInt3 = -1;
    public int botReservedGoalInt2 = -1;
    public int botSecondaryCompletionItemId = -1;
    public int botCompletionItemAmount = -1;
    public int botCompletionItemId = -1;
    public byte botReservedGoalByte4 = (byte)-1;
    public byte botReservedGoalByte3 = (byte)-1;
    public byte botReservedGoalByte2 = (byte)-1;
    public byte botReservedGoalByte1 = (byte)-1;
    public byte botSkillTargetLevel = (byte)-1;
    public byte botSkillTargetSkillId = (byte)-1;
    public byte botCombatStyle = (byte)-1;
    public ArrayList botCombatLoadoutItemIds = new ArrayList();
    public ArrayList botShopSellItemIds;
    public int botShopItemAmount;
    public int botTaskItemId;
    public byte botShopBuyMode;
    public int tradeAdvertLastOfferAmount;
    public boolean tradeAdvertVariableQuantity;
    public boolean tradeAdvertScam;
    public int tradeAdvertUnitPrice;
    public int tradeAdvertQuantityRemaining;
    public int botAdvertItemId;
    public byte tradeAdvertMode;
    public byte savedWorldRouteIndex;
    public byte botTaskDurationMinutes;
    public long botTaskSavedElapsedMillis;
    public boolean savedWorldRouteReversed;
    public byte botPathWaypointIndex;
    public byte botPathSegmentIndex;
    public int botFoodItemId;
    public ItemStack[] botTaskRequiredItems;
    public String botTaskState;
    public int deferredBotTaskIndex;
    public byte deferredBotTaskTypeId;
    public int currentBotTaskIndex;
    public byte currentBotTaskTypeId;
    public byte botMode;
    public boolean botEnabled;
    public boolean botTaskReturnToBankRequested;
    public int botElementalSpellIndex;
    public int[] godWarsKillCounts;
    public long godWarsLastAltarBlessingMillis;
    public byte craftingThreadUseCount;
    public int reservedSaveByte;
    public long membershipExpiresMillis;
    public int savedCacheVersion;
    public int godBookPageFlags;
    public boolean swampCaveRopeAttached;
    public boolean lampOilStillFilled;
    public int enterTheAbyssMiniquestState;
    private static final int[] experienceForLevel;

    static {
        int[][][] nArrayArray = new int[][][]{new int[][]{new int[5], {11, 15, 15, 5, 7}}, new int[][]{new int[5], {11, 15, 15, 5, 7}}};
        int[][][] nArrayArray2 = new int[2][][];
        int[][] nArrayArray3 = new int[2][];
        int[] integerValues = new int[7];
        integerValues[0] = 18;
        integerValues[1] = 26;
        integerValues[2] = 36;
        integerValues[4] = 33;
        integerValues[5] = 42;
        integerValues[6] = 10;
        nArrayArray3[0] = integerValues;
        nArrayArray3[1] = new int[]{25, 31, 40, 8, 34, 43, 17};
        nArrayArray2[0] = nArrayArray3;
        nArrayArray2[1] = new int[][]{{56, 61, 70, 45, 67, 79, -1}, {60, 65, 77, 54, 68, 80, -1}};
        experienceForLevel = new int[100];
        int index = 0;
        int initialValue = 1;
        while (initialValue <= 99) {
            int value;
            index = (int)((double)index + Math.floor((double)initialValue + 300.0 * Math.pow(2.0, (double)initialValue / 7.0)));
            CharacterFileRecord.experienceForLevel[initialValue] = value = (int)Math.floor(index / 4);
            ++initialValue;
        }
    }

    public CharacterFileRecord() {
        new ArrayList();
        this.botShopSellItemIds = new ArrayList();
        this.botShopItemAmount = -1;
        this.botTaskItemId = -1;
        this.botShopBuyMode = (byte)-1;
        this.tradeAdvertLastOfferAmount = -1;
        this.tradeAdvertVariableQuantity = false;
        this.tradeAdvertScam = false;
        this.tradeAdvertUnitPrice = -1;
        this.tradeAdvertQuantityRemaining = -1;
        this.botAdvertItemId = -1;
        this.tradeAdvertMode = (byte)-1;
        this.savedWorldRouteIndex = (byte)-1;
        this.botTaskDurationMinutes = 0;
        this.botTaskSavedElapsedMillis = 0L;
        this.savedWorldRouteReversed = false;
        this.botPathWaypointIndex = (byte)-1;
        this.botPathSegmentIndex = (byte)-1;
        this.botFoodItemId = -1;
        this.deferredBotTaskIndex = -1;
        this.deferredBotTaskTypeId = (byte)-1;
        this.currentBotTaskIndex = -1;
        this.currentBotTaskTypeId = (byte)-1;
        this.botMode = (byte)-1;
        this.botEnabled = false;
        this.botTaskReturnToBankRequested = false;
        this.botElementalSpellIndex = -1;
        this.godWarsKillCounts = new int[4];
        this.craftingThreadUseCount = 0;
        this.reservedSaveByte = 0;
        this.swampCaveRopeAttached = false;
        this.lampOilStillFilled = false;
        this.enterTheAbyssMiniquestState = 0;
        this.bankTabs.add(new CharacterFileBankTab(0));
    }

    public final void setBankTabItem(int itemId, ItemStack itemStack, int value2) {
        try {
            if (value2 > this.maxBankTabs - 1) {
                value2 = this.maxBankTabs - 1;
            }
            if (value2 > this.bankTabs.size() - 1) {
                this.bankTabs.add(new CharacterFileBankTab(0));
            }
            CharacterFileBankTab characterFileBankTab = (CharacterFileBankTab)this.bankTabs.get(value2);
            if (itemId > characterFileBankTab.items.size() - 1) {
                characterFileBankTab = (CharacterFileBankTab)this.bankTabs.get(value2);
                characterFileBankTab.items.add(itemStack);
                return;
            }
            characterFileBankTab = (CharacterFileBankTab)this.bankTabs.get(value2);
            characterFileBankTab.items.set(itemId, itemStack);
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final int getStoredItemValue() {
        if (this.cachedItemValue == 0) {
            ItemDefinition itemDefinition;
            Object value;
            int value2;
            int index = 0;
            while (index < this.bankTabs.size()) {
                Object characterFileBankTab = (CharacterFileBankTab)this.bankTabs.get(index);
                characterFileBankTab = ((CharacterFileBankTab)characterFileBankTab).items;
                value2 = 0;
                while (value2 < ((ArrayList)characterFileBankTab).size()) {
                    value = (ItemStack)((ArrayList)characterFileBankTab).get(value2);
                    if (value != null && ((ItemStack)value).getAmount() > 0 && !(itemDefinition = ((ItemStack)value).getDefinition()).isUntradeable()) {
                        this.cachedItemValue += itemDefinition.getValue() * ((ItemStack)value).getAmount();
                    }
                    ++value2;
                }
                ++index;
            }
            ItemStack[] inventoryItems = this.inventoryItems;
            value2 = this.inventoryItems.length;
            int index2 = 0;
            while (index2 < value2) {
                ItemStack itemStack = inventoryItems[index2];
                if (itemStack != null && itemStack.getAmount() > 0 && !(itemDefinition = itemStack.getDefinition()).isUntradeable()) {
                    this.cachedItemValue += itemDefinition.getValue() * itemStack.getAmount();
                }
                ++index2;
            }
            ItemStack[] equipmentItems = this.equipmentItems;
            value2 = this.equipmentItems.length;
            index2 = 0;
            while (index2 < value2) {
                ItemStack itemStack2 = equipmentItems[index2];
                if (itemStack2 != null && ((ItemStack)itemStack2).getAmount() > 0 && !(itemDefinition = ((ItemStack)itemStack2).getDefinition()).isUntradeable()) {
                    this.cachedItemValue += itemDefinition.getValue() * ((ItemStack)itemStack2).getAmount();
                }
                ++index2;
            }
        }
        return this.cachedItemValue;
    }

    public final long getSkillExperience(int experience) {
        if (experience < 21) {
            return this.skillExperience[experience];
        }
        long value = 0L;
        experience = 0;
        while (experience < 21) {
            value += this.skillExperience[experience];
            ++experience;
        }
        return value;
    }

    public final int getTotalLevel() {
        int index = 0;
        int index2 = 0;
        while (index2 < 21) {
            index += CharacterFileRecord.getLevelForExperience(this.getSkillExperience(index2));
            ++index2;
        }
        return index;
    }

    public static int getLevelForExperience(double level) {
        int initialValue = 1;
        while (initialValue <= 99) {
            if ((double)experienceForLevel[initialValue] > level) {
                return initialValue;
            }
            ++initialValue;
        }
        return 99;
    }
}
