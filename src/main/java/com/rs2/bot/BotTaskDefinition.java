package com.rs2.bot;

import com.rs2.ServerSettings;
import com.rs2.bot.BotCombatTickTask;
import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskPlanner;
import com.rs2.bot.BotTradeAdvertManager;
import com.rs2.bot.BrassKeyBotTaskList;
import com.rs2.bot.CombatBotTaskList;
import com.rs2.bot.CookingBotTaskList;
import com.rs2.bot.DropPartyBotManager;
import com.rs2.bot.FishingBotTaskList;
import com.rs2.bot.LeatherCraftingBotTaskList;
import com.rs2.bot.MiningBotTaskList;
import com.rs2.bot.MoneyMakingBotTaskList;
import com.rs2.bot.RunecraftingBotTaskList;
import com.rs2.bot.SheepShearingBotTaskList;
import com.rs2.bot.ShopBotTaskList;
import com.rs2.bot.SmeltingBotTaskList;
import com.rs2.bot.SmithingBotTaskList;
import com.rs2.bot.SpinningBotTaskList;
import com.rs2.bot.TanningBotTaskList;
import com.rs2.bot.WoodcuttingBotTaskList;
import com.rs2.bot.route.BotWorldRouteWalker;
import com.rs2.bot.tasks.AirRuneRunecraftingBotTask;
import com.rs2.bot.tasks.AlKharidFlyFishingBotTask;
import com.rs2.bot.tasks.AlKharidLobsterCookingBotTask;
import com.rs2.bot.tasks.AlKharidMineBotTask;
import com.rs2.bot.tasks.AlKharidNetBaitFishingBotTask;
import com.rs2.bot.tasks.AlKharidSteelSmeltingBotTask;
import com.rs2.bot.tasks.AlKharidWarriorCombatBotTask;
import com.rs2.bot.tasks.BarbarianVillageBarbarianCombatBotTask;
import com.rs2.bot.tasks.BarbarianVillageFlyFishingBotTask;
import com.rs2.bot.tasks.BodyRuneRunecraftingBotTask;
import com.rs2.bot.tasks.BrimhavenDungeonBlueDragonNorthCombatBotTask;
import com.rs2.bot.tasks.BrimhavenDungeonBlueDragonSouthCombatBotTask;
import com.rs2.bot.tasks.BrimhavenDungeonRedDragonCombatBotTask;
import com.rs2.bot.tasks.CatherbyFishingBotTask;
import com.rs2.bot.tasks.CatherbyLobsterCookingBotTask;
import com.rs2.bot.tasks.CraftingGuildMineBotTask;
import com.rs2.bot.tasks.DraynorChickenCombatBotTask;
import com.rs2.bot.tasks.DraynorGoblinCombatBotTask;
import com.rs2.bot.tasks.DraynorNetFishingBotTask;
import com.rs2.bot.tasks.DraynorOakWoodcuttingBotTask;
import com.rs2.bot.tasks.DraynorSheepShearingBotTask;
import com.rs2.bot.tasks.DraynorTreeWoodcuttingBotTask;
import com.rs2.bot.tasks.DraynorWillowWoodcuttingBotTask;
import com.rs2.bot.tasks.DraynorYewWoodcuttingBotTask;
import com.rs2.bot.tasks.DwarvenMineBotTask;
import com.rs2.bot.tasks.DwarvenMineDwarfCombatBotTask;
import com.rs2.bot.tasks.EarthRuneRunecraftingBotTask;
import com.rs2.bot.tasks.EdgevilleDungeonHillGiantCombatBotTask;
import com.rs2.bot.tasks.EdgevilleDungeonNorthMossGiantCombatBotTask;
import com.rs2.bot.tasks.EdgevilleDungeonSkeletonCombatBotTask;
import com.rs2.bot.tasks.EdgevilleDungeonSouthMossGiantCombatBotTask;
import com.rs2.bot.tasks.EdgevilleDungeonSpiderRatCombatBotTask;
import com.rs2.bot.tasks.EdgevilleTradeAdvertBotTask;
import com.rs2.bot.tasks.EdgevilleTreeWoodcuttingBotTask;
import com.rs2.bot.tasks.EdgevilleYewWoodcuttingBotTask;
import com.rs2.bot.tasks.FaladorCowCombatBotTask;
import com.rs2.bot.tasks.FaladorDropPartyBotTask;
import com.rs2.bot.tasks.FaladorGuardCombatBotTask;
import com.rs2.bot.tasks.FaladorImpCombatBotTask;
import com.rs2.bot.tasks.FaladorSteelSmeltingBotTask;
import com.rs2.bot.tasks.FaladorTradeAdvertBotTask;
import com.rs2.bot.tasks.FaladorWineOfZamorakTelegrabBotTask;
import com.rs2.bot.tasks.FaladorYewWoodcuttingBotTask;
import com.rs2.bot.tasks.FireRuneRunecraftingBotTask;
import com.rs2.bot.tasks.KaramjaFishingBotTask;
import com.rs2.bot.tasks.KaramjaVolcanoNorthLesserDemonCombatBotTask;
import com.rs2.bot.tasks.KaramjaVolcanoSouthLesserDemonCombatBotTask;
import com.rs2.bot.tasks.LumbridgeCowCombatBotTask;
import com.rs2.bot.tasks.LumbridgeEastChickenCombatBotTask;
import com.rs2.bot.tasks.LumbridgeGoblinCombatBotTask;
import com.rs2.bot.tasks.LumbridgeWestChickenCombatBotTask;
import com.rs2.bot.tasks.LumbridgeWoolSpinningBotTask;
import com.rs2.bot.tasks.MindRuneRunecraftingBotTask;
import com.rs2.bot.tasks.MiningGuildMineBotTask;
import com.rs2.bot.tasks.RimmingtonMineBotTask;
import com.rs2.bot.tasks.SeersFlaxPickingBotTask;
import com.rs2.bot.tasks.SeersFlaxSpinningBotTask;
import com.rs2.bot.tasks.SeersMagicTreeWoodcuttingBotTask;
import com.rs2.bot.tasks.SeersMapleWoodcuttingBotTask;
import com.rs2.bot.tasks.SeersTradeAdvertBotTask;
import com.rs2.bot.tasks.SeersYewWoodcuttingBotTask;
import com.rs2.bot.tasks.SorcerersTowerMagicTreeWoodcuttingBotTask;
import com.rs2.bot.tasks.TaverleyDungeonHellhoundCombatBotTask;
import com.rs2.bot.tasks.VarrockDropPartyBotTask;
import com.rs2.bot.tasks.VarrockEastMineBotTask;
import com.rs2.bot.tasks.VarrockEastOakWoodcuttingBotTask;
import com.rs2.bot.tasks.VarrockEastTreeWoodcuttingBotTask;
import com.rs2.bot.tasks.VarrockGuardCombatBotTask;
import com.rs2.bot.tasks.VarrockLobsterCookingBotTask;
import com.rs2.bot.tasks.VarrockPalaceYewWoodcuttingBotTask;
import com.rs2.bot.tasks.VarrockRuneEssenceMiningBotTask;
import com.rs2.bot.tasks.VarrockSewerGiantRatCombatBotTask;
import com.rs2.bot.tasks.VarrockSouthChickenCombatBotTask;
import com.rs2.bot.tasks.VarrockSteelDaggerSmithingBotTask;
import com.rs2.bot.tasks.VarrockTradeAdvertBotTask;
import com.rs2.bot.tasks.VarrockWestMineBotTask;
import com.rs2.bot.tasks.VarrockWestOakWoodcuttingBotTask;
import com.rs2.bot.tasks.VarrockWestTreeWoodcuttingBotTask;
import com.rs2.bot.tasks.WaterRuneRunecraftingBotTask;
import com.rs2.bot.tasks.WildernessRuniteMineBotTask;
import com.rs2.bot.tasks.WizardsTowerLesserDemonMagicBotTask;
import com.rs2.cache.CacheArchiveEntry;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.shop.ShopDefinition;
import com.rs2.model.shop.ShopManager;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class BotTaskDefinition {
    private static AlKharidMineBotTask alKharidMineTask = new AlKharidMineBotTask(6);
    static BrimhavenDungeonBlueDragonNorthCombatBotTask brimhavenDungeonBlueDragonNorthCombatTask = new BrimhavenDungeonBlueDragonNorthCombatBotTask(4);
    private static BrimhavenDungeonBlueDragonSouthCombatBotTask brimhavenDungeonBlueDragonSouthCombatTask = new BrimhavenDungeonBlueDragonSouthCombatBotTask(6);
    private static BrimhavenDungeonRedDragonCombatBotTask brimhavenDungeonRedDragonCombatTask = new BrimhavenDungeonRedDragonCombatBotTask(2);
    private static CatherbyFishingBotTask catherbyFishingTask = new CatherbyFishingBotTask(2);
    private static CraftingGuildMineBotTask craftingGuildMineTask = new CraftingGuildMineBotTask(1);
    private static DraynorNetFishingBotTask draynorNetFishingTask = new DraynorNetFishingBotTask(3);
    static DraynorWillowWoodcuttingBotTask draynorWillowWoodcuttingTask = new DraynorWillowWoodcuttingBotTask(4);
    private static BarbarianVillageFlyFishingBotTask barbarianVillageFlyFishingTask = new BarbarianVillageFlyFishingBotTask(3);
    private static EdgevilleYewWoodcuttingBotTask edgevilleYewWoodcuttingTask = new EdgevilleYewWoodcuttingBotTask(4);
    private static DraynorChickenCombatBotTask draynorChickenCombatTask = new DraynorChickenCombatBotTask(1);
    private static FaladorCowCombatBotTask faladorCowCombatTask = new FaladorCowCombatBotTask(4);
    static DwarvenMineBotTask dwarvenMineTask = new DwarvenMineBotTask(4);
    private static FaladorYewWoodcuttingBotTask faladorYewWoodcuttingTask = new FaladorYewWoodcuttingBotTask(4);
    private static KaramjaFishingBotTask karamjaFishingTask = new KaramjaFishingBotTask(4);
    private static KaramjaVolcanoNorthLesserDemonCombatBotTask karamjaVolcanoNorthLesserDemonCombatTask = new KaramjaVolcanoNorthLesserDemonCombatBotTask(4);
    private static KaramjaVolcanoSouthLesserDemonCombatBotTask karamjaVolcanoSouthLesserDemonCombatTask = new KaramjaVolcanoSouthLesserDemonCombatBotTask(2);
    private static LumbridgeEastChickenCombatBotTask lumbridgeEastChickenCombatTask = new LumbridgeEastChickenCombatBotTask(2);
    private static LumbridgeWestChickenCombatBotTask lumbridgeWestChickenCombatTask = new LumbridgeWestChickenCombatBotTask(1);
    private static LumbridgeCowCombatBotTask lumbridgeCowCombatTask = new LumbridgeCowCombatBotTask(10);
    private static MiningGuildMineBotTask miningGuildMineTask = new MiningGuildMineBotTask(6);
    private static TaverleyDungeonHellhoundCombatBotTask taverleyDungeonHellhoundCombatTask = new TaverleyDungeonHellhoundCombatBotTask(4);
    private static VarrockEastMineBotTask varrockEastMineTask = new VarrockEastMineBotTask(8);
    static RimmingtonMineBotTask rimmingtonMineTask = new RimmingtonMineBotTask(8);
    private static EdgevilleDungeonHillGiantCombatBotTask edgevilleDungeonHillGiantCombatTask = new EdgevilleDungeonHillGiantCombatBotTask(10);
    private static EdgevilleDungeonNorthMossGiantCombatBotTask edgevilleDungeonNorthMossGiantCombatTask = new EdgevilleDungeonNorthMossGiantCombatBotTask(2);
    private static EdgevilleDungeonSouthMossGiantCombatBotTask edgevilleDungeonSouthMossGiantCombatTask = new EdgevilleDungeonSouthMossGiantCombatBotTask(2);
    static VarrockWestMineBotTask varrockWestMineTask = new VarrockWestMineBotTask(3);
    private static VarrockPalaceYewWoodcuttingBotTask varrockPalaceYewWoodcuttingTask = new VarrockPalaceYewWoodcuttingBotTask(4);
    private static SeersMapleWoodcuttingBotTask seersMapleWoodcuttingTask = new SeersMapleWoodcuttingBotTask(2);
    private static SeersYewWoodcuttingBotTask seersYewWoodcuttingTask = new SeersYewWoodcuttingBotTask(2);
    private static SeersMagicTreeWoodcuttingBotTask seersMagicTreeWoodcuttingTask = new SeersMagicTreeWoodcuttingBotTask(2);
    private static SorcerersTowerMagicTreeWoodcuttingBotTask sorcerersTowerMagicTreeWoodcuttingTask = new SorcerersTowerMagicTreeWoodcuttingBotTask(2);
    static SeersFlaxPickingBotTask seersFlaxPickingTask = new SeersFlaxPickingBotTask(4);
    private static FaladorWineOfZamorakTelegrabBotTask faladorWineOfZamorakTelegrabTask = new FaladorWineOfZamorakTelegrabBotTask(1);
    private static DraynorOakWoodcuttingBotTask draynorOakWoodcuttingTask = new DraynorOakWoodcuttingBotTask(3);
    private static DraynorTreeWoodcuttingBotTask draynorTreeWoodcuttingTask = new DraynorTreeWoodcuttingBotTask(3);
    private static VarrockWestOakWoodcuttingBotTask varrockWestOakWoodcuttingTask = new VarrockWestOakWoodcuttingBotTask(3);
    private static VarrockWestTreeWoodcuttingBotTask varrockWestTreeWoodcuttingTask = new VarrockWestTreeWoodcuttingBotTask(3);
    private static VarrockEastOakWoodcuttingBotTask varrockEastOakWoodcuttingTask = new VarrockEastOakWoodcuttingBotTask(6);
    private static VarrockEastTreeWoodcuttingBotTask varrockEastTreeWoodcuttingTask = new VarrockEastTreeWoodcuttingBotTask(3);
    private static EdgevilleTreeWoodcuttingBotTask edgevilleTreeWoodcuttingTask = new EdgevilleTreeWoodcuttingBotTask(1);
    private static DraynorYewWoodcuttingBotTask draynorYewWoodcuttingTask = new DraynorYewWoodcuttingBotTask(4);
    private static DraynorGoblinCombatBotTask draynorGoblinCombatTask = new DraynorGoblinCombatBotTask(6);
    static LumbridgeGoblinCombatBotTask lumbridgeGoblinCombatTask = new LumbridgeGoblinCombatBotTask(4);
    private static AlKharidFlyFishingBotTask alKharidFlyFishingTask = new AlKharidFlyFishingBotTask(2);
    private static AlKharidNetBaitFishingBotTask alKharidNetBaitFishingTask = new AlKharidNetBaitFishingBotTask(4);
    private static AlKharidWarriorCombatBotTask alKharidWarriorCombatTask = new AlKharidWarriorCombatBotTask(3);
    private static VarrockGuardCombatBotTask varrockGuardCombatTask = new VarrockGuardCombatBotTask(3);
    private static VarrockSewerGiantRatCombatBotTask varrockSewerGiantRatCombatTask = new VarrockSewerGiantRatCombatBotTask(1);
    private static BarbarianVillageBarbarianCombatBotTask barbarianVillageBarbarianCombatTask = new BarbarianVillageBarbarianCombatBotTask(2);
    private static DwarvenMineDwarfCombatBotTask dwarvenMineDwarfCombatTask = new DwarvenMineDwarfCombatBotTask(1);
    private static FaladorGuardCombatBotTask faladorGuardCombatTask = new FaladorGuardCombatBotTask(3);
    private static EdgevilleDungeonSpiderRatCombatBotTask edgevilleDungeonSpiderRatCombatTask = new EdgevilleDungeonSpiderRatCombatBotTask(1);
    private static EdgevilleDungeonSkeletonCombatBotTask edgevilleDungeonSkeletonCombatTask = new EdgevilleDungeonSkeletonCombatBotTask(2);
    private static VarrockRuneEssenceMiningBotTask varrockRuneEssenceMiningTask = new VarrockRuneEssenceMiningBotTask(6);
    private static AirRuneRunecraftingBotTask airRuneRunecraftingTask = new AirRuneRunecraftingBotTask(1);
    private static MindRuneRunecraftingBotTask mindRuneRunecraftingTask = new MindRuneRunecraftingBotTask(1);
    private static WaterRuneRunecraftingBotTask waterRuneRunecraftingTask = new WaterRuneRunecraftingBotTask(1);
    private static EarthRuneRunecraftingBotTask earthRuneRunecraftingTask = new EarthRuneRunecraftingBotTask(1);
    private static FireRuneRunecraftingBotTask fireRuneRunecraftingTask = new FireRuneRunecraftingBotTask(1);
    private static BodyRuneRunecraftingBotTask bodyRuneRunecraftingTask = new BodyRuneRunecraftingBotTask(1);
    private static AlKharidLobsterCookingBotTask alKharidLobsterCookingTask = new AlKharidLobsterCookingBotTask(1);
    private static AlKharidSteelSmeltingBotTask alKharidSteelSmeltingTask = new AlKharidSteelSmeltingBotTask(1);
    private static FaladorSteelSmeltingBotTask faladorSteelSmeltingTask = new FaladorSteelSmeltingBotTask(1);
    private static VarrockLobsterCookingBotTask varrockLobsterCookingTask = new VarrockLobsterCookingBotTask(1);
    static VarrockSteelDaggerSmithingBotTask varrockSteelDaggerSmithingTask = new VarrockSteelDaggerSmithingBotTask(1);
    private static WildernessRuniteMineBotTask wildernessRuniteMineTask = new WildernessRuniteMineBotTask(1);
    private static VarrockSouthChickenCombatBotTask varrockSouthChickenCombatTask = new VarrockSouthChickenCombatBotTask(1);
    static WizardsTowerLesserDemonMagicBotTask wizardsTowerLesserDemonMagicTask = new WizardsTowerLesserDemonMagicBotTask(1);
    static FaladorImpCombatBotTask faladorImpCombatTask = new FaladorImpCombatBotTask(1);
    private static DraynorSheepShearingBotTask draynorSheepShearingTask = new DraynorSheepShearingBotTask(4);
    private static LumbridgeWoolSpinningBotTask lumbridgeWoolSpinningTask = new LumbridgeWoolSpinningBotTask(4);
    private static CatherbyLobsterCookingBotTask catherbyLobsterCookingTask = new CatherbyLobsterCookingBotTask(1);
    private static SeersFlaxSpinningBotTask seersFlaxSpinningTask = new SeersFlaxSpinningBotTask(4);
    private static BotTaskDefinition[] progressiveTaskDefinitions = new BotTaskDefinition[]{alKharidMineTask, brimhavenDungeonBlueDragonNorthCombatTask, brimhavenDungeonBlueDragonSouthCombatTask, brimhavenDungeonRedDragonCombatTask, catherbyFishingTask, craftingGuildMineTask, draynorNetFishingTask, draynorWillowWoodcuttingTask, barbarianVillageFlyFishingTask, edgevilleYewWoodcuttingTask, draynorChickenCombatTask, faladorCowCombatTask, dwarvenMineTask, faladorYewWoodcuttingTask, karamjaFishingTask, karamjaVolcanoNorthLesserDemonCombatTask, karamjaVolcanoSouthLesserDemonCombatTask, lumbridgeEastChickenCombatTask, lumbridgeWestChickenCombatTask, lumbridgeCowCombatTask, miningGuildMineTask, taverleyDungeonHellhoundCombatTask, varrockEastMineTask, edgevilleDungeonHillGiantCombatTask, edgevilleDungeonNorthMossGiantCombatTask, edgevilleDungeonSouthMossGiantCombatTask, varrockWestMineTask, varrockPalaceYewWoodcuttingTask, seersMapleWoodcuttingTask, seersYewWoodcuttingTask, seersMagicTreeWoodcuttingTask, sorcerersTowerMagicTreeWoodcuttingTask, seersFlaxPickingTask, faladorWineOfZamorakTelegrabTask, draynorOakWoodcuttingTask, draynorTreeWoodcuttingTask, varrockWestOakWoodcuttingTask, varrockWestTreeWoodcuttingTask, varrockEastOakWoodcuttingTask, varrockEastTreeWoodcuttingTask, edgevilleTreeWoodcuttingTask, draynorYewWoodcuttingTask, draynorGoblinCombatTask, lumbridgeGoblinCombatTask, alKharidFlyFishingTask, alKharidNetBaitFishingTask, alKharidWarriorCombatTask, varrockGuardCombatTask, varrockSewerGiantRatCombatTask, barbarianVillageBarbarianCombatTask, dwarvenMineDwarfCombatTask, faladorGuardCombatTask, edgevilleDungeonSpiderRatCombatTask, edgevilleDungeonSkeletonCombatTask, varrockRuneEssenceMiningTask, airRuneRunecraftingTask, mindRuneRunecraftingTask, waterRuneRunecraftingTask, earthRuneRunecraftingTask, fireRuneRunecraftingTask, bodyRuneRunecraftingTask, alKharidLobsterCookingTask, alKharidSteelSmeltingTask, faladorSteelSmeltingTask, varrockLobsterCookingTask, varrockSteelDaggerSmithingTask, wildernessRuniteMineTask, varrockSouthChickenCombatTask, wizardsTowerLesserDemonMagicTask, faladorImpCombatTask, draynorSheepShearingTask, lumbridgeWoolSpinningTask, catherbyLobsterCookingTask, seersFlaxSpinningTask, rimmingtonMineTask};
    private static BotTaskDefinition[] tradeAdvertTaskDefinitions = new BotTaskDefinition[]{new VarrockTradeAdvertBotTask(30), new FaladorTradeAdvertBotTask(30), new SeersTradeAdvertBotTask(10), new EdgevilleTradeAdvertBotTask(5)};
    private static BotTaskDefinition[] dropPartyTaskDefinitions = new BotTaskDefinition[]{new VarrockDropPartyBotTask(1), new FaladorDropPartyBotTask(1)};
    public static ArrayList brassKeyTasks = new BrassKeyBotTaskList();
    public static ArrayList shopTasks = new ShopBotTaskList();
    public static ArrayList fishingTasks = new FishingBotTaskList();
    public static ArrayList cookingTasks = new CookingBotTaskList();
    public static ArrayList miningTasks = new MiningBotTaskList();
    public static ArrayList smeltingTasks = new SmeltingBotTaskList();
    public static ArrayList smithingTasks = new SmithingBotTaskList();
    public static ArrayList woodcuttingTasks = new WoodcuttingBotTaskList();
    public static ArrayList runecraftingTasks = new RunecraftingBotTaskList();
    public static ArrayList moneyMakingTasks = new MoneyMakingBotTaskList();
    public static ArrayList sheepShearingTasks = new SheepShearingBotTaskList();
    public static ArrayList spinningTasks = new SpinningBotTaskList();
    public static ArrayList tanningTasks = new TanningBotTaskList();
    public static ArrayList leatherCraftingTasks = new LeatherCraftingBotTaskList();
    public static ArrayList combatTasks = new CombatBotTaskList();
    private static ArrayList lootSellShopTasks = new ArrayList();
    public static ArrayList tradeAdvertTaskPool = new ArrayList();
    public static ArrayList dropPartyTaskPool = new ArrayList();
    private static int totalTradeAdvertTaskWeight;
    public static ArrayList progressiveTaskPool;
    private static int totalProgressiveTaskWeight;
    public int minimumServerRevision = -1;
    public ArrayList lootSellShopIds = new ArrayList();
    public boolean usesCustomTaskAction = false;
    public boolean usesEscapeMonitor = false;
    private RectangularArea[] taskAreas;
    public Position startPosition;
    private BotRoute pretaskRoute;
    public BotRoute taskRoute;
    public BotRoute[] taskRouteSegments;
    public int[] ignoredLootItemIds;
    public int interactionTargetType;
    public boolean membersOnly;
    public int interactionOption = -1;
    public boolean combatTask = false;
    public boolean usesDepositBox = false;
    public int selectionWeight;
    public int targetSearchRadius = -1;
    public ArrayList assignedBotPlayers = new ArrayList();
    public int targetMaxX = -1;
    public int targetMaxY = -1;
    public int targetMinX = -1;
    public int targetMinY = -1;
    int forcedCombatStyle = -1;
    public boolean usesCombatTradeAdvertItems = false;
    public static int dropPartyBotJoinIndex;

    static {
        progressiveTaskPool = new ArrayList();
        dropPartyBotJoinIndex = 0;
    }

    public static ArrayList getLootSellShopTasks() {
        if (lootSellShopTasks.size() == 0) {
            for (Object taskObject : shopTasks) {
                BotTaskDefinition botTaskDefinition = (BotTaskDefinition)taskObject;
                int shopId = botTaskDefinition.getShopId();
                ShopDefinition shopDefinition = (ShopDefinition)ShopManager.getShopDefinitions().get(shopId);
                if (!shopDefinition.isGeneralStore()) continue;
                lootSellShopTasks.add(botTaskDefinition);
            }
        }
        return lootSellShopTasks;
    }

    public static BotTaskDefinition getTaskByTypeAndIndex(int index, int value2) {
        if (index == 0) {
            return (BotTaskDefinition)brassKeyTasks.get(value2);
        }
        if (index == 1) {
            return (BotTaskDefinition)shopTasks.get(value2);
        }
        if (index == 2) {
            return (BotTaskDefinition)fishingTasks.get(value2);
        }
        if (index == 3) {
            return (BotTaskDefinition)cookingTasks.get(value2);
        }
        if (index == 4) {
            return (BotTaskDefinition)miningTasks.get(value2);
        }
        if (index == 5) {
            return (BotTaskDefinition)smeltingTasks.get(value2);
        }
        if (index == 6) {
            return (BotTaskDefinition)smithingTasks.get(value2);
        }
        if (index == 7) {
            return (BotTaskDefinition)woodcuttingTasks.get(value2);
        }
        if (index == 8) {
            return (BotTaskDefinition)runecraftingTasks.get(value2);
        }
        if (index == 9) {
            return (BotTaskDefinition)moneyMakingTasks.get(value2);
        }
        if (index == 10) {
            return (BotTaskDefinition)combatTasks.get(value2);
        }
        if (index == 11) {
            return (BotTaskDefinition)sheepShearingTasks.get(value2);
        }
        if (index == 12) {
            return (BotTaskDefinition)spinningTasks.get(value2);
        }
        if (index == 13) {
            return (BotTaskDefinition)tanningTasks.get(value2);
        }
        if (index == 14) {
            return (BotTaskDefinition)leatherCraftingTasks.get(value2);
        }
        System.out.println("Error botTask (" + index + "-" + value2 + ") not found while loading!");
        return null;
    }

    public final int getTaskTypeId() {
        if (brassKeyTasks.contains(this)) {
            return 0;
        }
        if (shopTasks.contains(this)) {
            return 1;
        }
        if (fishingTasks.contains(this)) {
            return 2;
        }
        if (cookingTasks.contains(this)) {
            return 3;
        }
        if (miningTasks.contains(this)) {
            return 4;
        }
        if (smeltingTasks.contains(this)) {
            return 5;
        }
        if (smithingTasks.contains(this)) {
            return 6;
        }
        if (woodcuttingTasks.contains(this)) {
            return 7;
        }
        if (runecraftingTasks.contains(this)) {
            return 8;
        }
        if (moneyMakingTasks.contains(this)) {
            return 9;
        }
        if (combatTasks.contains(this)) {
            return 10;
        }
        if (sheepShearingTasks.contains(this)) {
            return 11;
        }
        if (spinningTasks.contains(this)) {
            return 12;
        }
        if (tanningTasks.contains(this)) {
            return 13;
        }
        if (leatherCraftingTasks.contains(this)) {
            return 14;
        }
        System.out.println("Error botTask (" + this + ") type not found while saving!");
        return -1;
    }

    public final int getTaskIndexForType(int index) {
        if (index == 0) {
            return brassKeyTasks.indexOf(this);
        }
        if (index == 1) {
            return shopTasks.indexOf(this);
        }
        if (index == 2) {
            return fishingTasks.indexOf(this);
        }
        if (index == 3) {
            return cookingTasks.indexOf(this);
        }
        if (index == 4) {
            return miningTasks.indexOf(this);
        }
        if (index == 5) {
            return smeltingTasks.indexOf(this);
        }
        if (index == 6) {
            return smithingTasks.indexOf(this);
        }
        if (index == 7) {
            return woodcuttingTasks.indexOf(this);
        }
        if (index == 8) {
            return runecraftingTasks.indexOf(this);
        }
        if (index == 9) {
            return moneyMakingTasks.indexOf(this);
        }
        if (index == 10) {
            return combatTasks.indexOf(this);
        }
        if (index == 11) {
            return sheepShearingTasks.indexOf(this);
        }
        if (index == 12) {
            return spinningTasks.indexOf(this);
        }
        if (index == 13) {
            return tanningTasks.indexOf(this);
        }
        if (index == 14) {
            return leatherCraftingTasks.indexOf(this);
        }
        System.out.println("Error botTask (" + this + ") index not found while saving!");
        return -1;
    }

    public static void initializeTradeAdvertTaskPool() {
        BotTradeAdvertManager.initializeTradeAdvertOfferPools();
        BotTaskDefinition[] taskDefinitions = tradeAdvertTaskDefinitions;
        int length = tradeAdvertTaskDefinitions.length;
        int index = 0;
        while (index < length) {
            BotTaskDefinition botTaskDefinition = taskDefinitions[index];
            if (!ServerSettings.freeToPlayWorld || !botTaskDefinition.membersOnly) {
                tradeAdvertTaskPool.add(botTaskDefinition);
                totalTradeAdvertTaskWeight += botTaskDefinition.selectionWeight;
            }
            ++index;
        }
        int value = ServerSettings.tradeBotCount;
        if (value < 0) {
            value = 0;
        } else if (value > 100) {
            value = 100;
        }
        if (value == 100) {
            for (Object taskObject : tradeAdvertTaskPool) {
                ++((BotTaskDefinition)taskObject).selectionWeight;
            }
        }
    }

    public static void initializeDropPartyTaskPool() {
        BotTaskDefinition[] botTaskDefinitionArray = dropPartyTaskDefinitions;
        int length = dropPartyTaskDefinitions.length;
        int index = 0;
        while (index < length) {
            BotTaskDefinition botTaskDefinition = botTaskDefinitionArray[index];
            if (!ServerSettings.freeToPlayWorld || !botTaskDefinition.membersOnly) {
                dropPartyTaskPool.add(botTaskDefinition);
            }
            ++index;
        }
        DropPartyBotManager.initializeDropPartyRewardPools();
    }

    public static void initializeProgressiveTaskPool() {
        BotTaskDefinition[] taskDefinitions = progressiveTaskDefinitions;
        int length = progressiveTaskDefinitions.length;
        int index = 0;
        while (index < length) {
            BotTaskDefinition botTaskDefinition = taskDefinitions[index];
            if (!(ServerSettings.freeToPlayWorld && botTaskDefinition.membersOnly || botTaskDefinition.minimumServerRevision != -1 && ServerSettings.cacheVersion < botTaskDefinition.minimumServerRevision)) {
                progressiveTaskPool.add(botTaskDefinition);
                totalProgressiveTaskWeight += botTaskDefinition.selectionWeight;
            }
            ++index;
        }
        int value = ServerSettings.skillingBotCount;
        if (value < 0) {
            value = 0;
        } else if (value > 100) {
            value = 100;
        }
        if (value == 100) {
            for (Object taskObject : progressiveTaskPool) {
                ++((BotTaskDefinition)taskObject).selectionWeight;
            }
        }
    }

    public final void setForcedCombatStyle(int forcedCombatStyle) {
        this.forcedCombatStyle = forcedCombatStyle;
    }

    public final int getForcedCombatStyle() {
        return this.forcedCombatStyle;
    }

    public int getShopId() {
        return -1;
    }

    public final void setPretaskRoute(BotRoute botRoute) {
        this.pretaskRoute = botRoute;
    }

    public void startCustomTaskAction(Player player) {
    }

    public void startEscapeMonitor(Player player) {
    }

    public boolean meetsUnlockRequirements(Player player) {
        return true;
    }

    public boolean isWithinProgressionRange(Player player) {
        return true;
    }

    public ArrayList getRequiredItems(Player player) {
        return new ArrayList();
    }

    public final ArrayList getMissingRequiredItems(Player player) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        ArrayList requiredItems = this.getRequiredItems(player);
        player.botTaskRequiredItems = new ItemStack[requiredItems.size()];
        int index = 0;
        Iterator iterator = requiredItems.iterator();
        while (iterator.hasNext()) {
            ItemStack itemStack = (ItemStack)iterator.next();
            player.botTaskRequiredItems[index] = itemStack;
            if (!player.ownsItemAmount(itemStack.getId(), itemStack.getAmount())) {
                arrayList.add(itemStack);
            }
            ++index;
        }
        return arrayList;
    }

    public final boolean isAvailableFor(Player player, boolean enabled2) {
        if (this.membersOnly && ServerSettings.freeToPlayWorld) {
            return false;
        }
        if (this.membersOnly && !player.isMember()) {
            return false;
        }
        if (this.minimumServerRevision != -1 && ServerSettings.cacheVersion < this.minimumServerRevision) {
            return false;
        }
        if (!this.meetsUnlockRequirements(player)) {
            return false;
        }
        if (!this.isWithinProgressionRange(player)) {
            return false;
        }
        if (enabled2) {
            return true;
        }
        ArrayList arrayList = this.getMissingRequiredItems(player);
        if (arrayList.size() > 0) {
            if (((ItemStack)arrayList.get(0)).getId() == 983) {
                return true;
            }
            return BotTaskPlanner.selectShopPurchaseTask(player, ((ItemStack)arrayList.get(0)).getId(), ((ItemStack)arrayList.get(0)).getAmount()) != null;
        }
        return true;
    }

    public BotTaskDefinition(int value3, boolean enabled2, int selectionWeight) {
        this.interactionTargetType = 0;
        this.membersOnly = false;
        this.selectionWeight = selectionWeight;
    }

    public BotTaskDefinition(Position position, BotRoute botRoute, int interactionTargetType, boolean membersOnly, int selectionWeight) {
        this.startPosition = position;
        this.taskRoute = botRoute;
        this.interactionTargetType = interactionTargetType;
        this.membersOnly = membersOnly;
        this.selectionWeight = selectionWeight;
    }

    public BotTaskDefinition(Position position, BotRoute[] botRouteArray, int interactionTargetType, boolean membersOnly, int selectionWeight) {
        this.startPosition = position;
        this.taskRouteSegments = botRouteArray;
        this.interactionTargetType = interactionTargetType;
        this.membersOnly = membersOnly;
        this.selectionWeight = selectionWeight;
    }

    public final Position getStartPosition() {
        return this.startPosition;
    }

    public final void setTaskAreas(RectangularArea[] rectangularAreaArray) {
        this.taskAreas = rectangularAreaArray;
    }

    public int getInteractionOption(Player player) {
        if (this.interactionOption != -1) {
            return this.interactionOption;
        }
        return 1;
    }

    public final void addLootSellShopIds(int[] integerValues3) {
        int[] integerValues2 = integerValues3;
        int length = integerValues3.length;
        int index = 0;
        while (index < length) {
            int value = integerValues2[index];
            this.lootSellShopIds.add(value);
            ++index;
        }
    }

    public final void startTask(Player player) {
        GameplayHelper.resetBotTaskState(player);
        if (!(shopTasks.contains(this) || spinningTasks.contains(this) || leatherCraftingTasks.contains(this) || tanningTasks.contains(this) || cookingTasks.contains(this) || smeltingTasks.contains(this) || smithingTasks.contains(this))) {
            player.botTaskItemId = -1;
        }
        player.botSmithingProductItemId = -1;
        player.botUseTaskItemOnTarget = false;
        if (player.botMode != 4) {
            this.prepareTaskCombatLoadout(player);
            this.prepareTradeAdvertState(player);
            this.prepareDropPartyState(player);
            this.prepareTaskInventory(player);
        }
        this.configureTaskInteractionTargets(player);
        player.botEnabled = true;
        player.botInteractionOption = 1;
        if (player.tradeAdvertMode == -1) {
            boolean enabled = true;
            if (player.botMode == 0 && ServerSettings.walkingBotsEnabled || player.botMode == 4) {
                enabled = false;
            }
            if (enabled && this.startPosition != null) {
                player.moveTo(this.startPosition);
            }
            if (this.pretaskRoute != null) {
                this.startPretaskPath(player);
            } else if (enabled) {
                this.startWalkToTask(player);
            }
            if (!enabled && this.startPosition != null) {
                BotWorldRouteWalker.findWorldRoute(player);
                return;
            }
        } else {
            player.moveTo(this.getRandomTaskAreaPosition());
            player.botTaskState = "do task";
        }
    }

    public final Position getRandomTaskAreaPosition() {
        int value = GameUtil.randomInt(this.taskAreas.length);
        int minX = this.taskAreas[value].getMinX();
        int minY = this.taskAreas[value].getMinY();
        int maxX = this.taskAreas[value].getMaxX();
        value = this.taskAreas[value].getMaxY();
        maxX -= minX;
        value -= minY;
        value = minY + GameUtil.randomInt(value);
        return new Position(minX += GameUtil.randomInt(maxX), value);
    }

    public final void startNpcCombatTick(Player player, Npc npc) {
        boolean enabled = player.botCombatTickTask != null && player.botCombatTickTask.isActive();
        if (!enabled) {
            player.botCombatTickTask = new BotCombatTickTask(this, 1, npc, player);
            World.getTaskScheduler().schedule(player.botCombatTickTask);
        }
    }

    public static void completeTradeAdvertOffer(Player player, boolean enabled2) {
        CacheArchiveEntry.completeTradeAdvertOffer(player, enabled2);
    }

    public void prepareTradeAdvertState(Player player) {
        player.tradeAdvertMode = -1;
        player.botAdvertItemId = -1;
        player.tradeAdvertQuantityRemaining = -1;
        player.tradeAdvertUnitPrice = -1;
        player.botPublicChatMessage = "";
        player.botPublicChatColor = -1;
        player.botPublicChatEffect = -1;
        player.pendingTradeTarget = null;
        player.tradeAdvertVariableQuantity = false;
        player.tradeAdvertLastOfferAmount = -1;
        player.tradeAdvertOfferPoolIndex = -1;
        player.tradeAdvertQuantityOptionIndex = -1;
    }

    public void prepareDropPartyState(Player player) {
        player.dropPartyLeader = false;
        player.dropPartyPretaskComplete = false;
        player.dropPartyPretaskLoopCount = 0;
    }

    public void configureTaskInteractionTargets(Player player) {
    }

    public void prepareTaskInventory(Player player) {
    }

    public void prepareTaskCombatLoadout(Player player) {
    }

    public void startWalkToTask(Player player) {
        player.botTaskState = "walk to task";
        player.currentBotRoute = this.taskRoute;
        player.botPathWaypointIndex = 0;
        player.continueBotRoute();
    }

    public void continueWalkToTask(Player player, int value2) {
        player.botTaskState = "walk to task";
        player.currentBotRoute = this.taskRoute;
        player.botPathWaypointIndex = value2;
        player.continueBotRoute();
    }

    public final void startPretaskPath(Player player) {
        player.botTaskState = "walk pretask path1";
        player.currentBotRoute = this.pretaskRoute;
        player.botPathWaypointIndex = 0;
        player.continueBotRoute();
    }

    public final void returnPretaskPath(Player player) {
        player.botTaskState = "walk pretask path2";
        player.currentBotRoute = this.pretaskRoute.reversed();
        player.botPathWaypointIndex = 0;
        player.continueBotRoute();
    }

    public void startWalkToBank(Player player) {
        player.botTaskState = "walk to bank";
        player.currentBotRoute = this.taskRoute.reversed();
        player.botPathWaypointIndex = 0;
        player.continueBotRoute();
    }

    public void continueWalkToBank(Player player, int value2) {
        player.botTaskState = "walk to bank";
        player.currentBotRoute = this.taskRoute.reversed();
        player.botPathWaypointIndex = value2;
        player.continueBotRoute();
    }

    public final Position getTaskPosition() {
        if (this.taskRouteSegments != null) {
            return this.taskRouteSegments[this.taskRouteSegments.length - 1].reversed().getStartPosition();
        }
        return this.taskRoute.reversed().getStartPosition();
    }

    public void advanceTaskRouteSegment(Player player, boolean enabled2) {
    }

    static DraynorNetFishingBotTask getDraynorNetFishingTask() {
        return draynorNetFishingTask;
    }

    static BarbarianVillageFlyFishingBotTask getBarbarianVillageFlyFishingTask() {
        return barbarianVillageFlyFishingTask;
    }

    static KaramjaFishingBotTask getKaramjaFishingTask() {
        return karamjaFishingTask;
    }

    static AlKharidFlyFishingBotTask getAlKharidFlyFishingTask() {
        return alKharidFlyFishingTask;
    }

    static AlKharidNetBaitFishingBotTask getAlKharidNetBaitFishingTask() {
        return alKharidNetBaitFishingTask;
    }

    static CatherbyFishingBotTask getCatherbyFishingTask() {
        return catherbyFishingTask;
    }

    static VarrockLobsterCookingBotTask getVarrockLobsterCookingTask() {
        return varrockLobsterCookingTask;
    }

    static AlKharidLobsterCookingBotTask getAlKharidLobsterCookingTask() {
        return alKharidLobsterCookingTask;
    }

    static CatherbyLobsterCookingBotTask getCatherbyLobsterCookingTask() {
        return catherbyLobsterCookingTask;
    }

    static AlKharidMineBotTask getAlKharidMineTask() {
        return alKharidMineTask;
    }

    static CraftingGuildMineBotTask getCraftingGuildMineTask() {
        return craftingGuildMineTask;
    }

    static MiningGuildMineBotTask getMiningGuildMineTask() {
        return miningGuildMineTask;
    }

    static VarrockEastMineBotTask getVarrockEastMineTask() {
        return varrockEastMineTask;
    }

    static WildernessRuniteMineBotTask getWildernessRuniteMineTask() {
        return wildernessRuniteMineTask;
    }

    static AlKharidSteelSmeltingBotTask getAlKharidSteelSmeltingTask() {
        return alKharidSteelSmeltingTask;
    }

    static FaladorSteelSmeltingBotTask getFaladorSteelSmeltingTask() {
        return faladorSteelSmeltingTask;
    }

    static EdgevilleYewWoodcuttingBotTask getEdgevilleYewWoodcuttingTask() {
        return edgevilleYewWoodcuttingTask;
    }

    static FaladorYewWoodcuttingBotTask getFaladorYewWoodcuttingTask() {
        return faladorYewWoodcuttingTask;
    }

    static VarrockPalaceYewWoodcuttingBotTask getVarrockPalaceYewWoodcuttingTask() {
        return varrockPalaceYewWoodcuttingTask;
    }

    static DraynorOakWoodcuttingBotTask getDraynorOakWoodcuttingTask() {
        return draynorOakWoodcuttingTask;
    }

    static DraynorTreeWoodcuttingBotTask getDraynorTreeWoodcuttingTask() {
        return draynorTreeWoodcuttingTask;
    }

    static VarrockWestOakWoodcuttingBotTask getVarrockWestOakWoodcuttingTask() {
        return varrockWestOakWoodcuttingTask;
    }

    static VarrockWestTreeWoodcuttingBotTask getVarrockWestTreeWoodcuttingTask() {
        return varrockWestTreeWoodcuttingTask;
    }

    static VarrockEastOakWoodcuttingBotTask getVarrockEastOakWoodcuttingTask() {
        return varrockEastOakWoodcuttingTask;
    }

    static VarrockEastTreeWoodcuttingBotTask getVarrockEastTreeWoodcuttingTask() {
        return varrockEastTreeWoodcuttingTask;
    }

    static EdgevilleTreeWoodcuttingBotTask getEdgevilleTreeWoodcuttingTask() {
        return edgevilleTreeWoodcuttingTask;
    }

    static DraynorYewWoodcuttingBotTask getDraynorYewWoodcuttingTask() {
        return draynorYewWoodcuttingTask;
    }

    static SeersMapleWoodcuttingBotTask getSeersMapleWoodcuttingTask() {
        return seersMapleWoodcuttingTask;
    }

    static SeersYewWoodcuttingBotTask getSeersYewWoodcuttingTask() {
        return seersYewWoodcuttingTask;
    }

    static SeersMagicTreeWoodcuttingBotTask getSeersMagicTreeWoodcuttingTask() {
        return seersMagicTreeWoodcuttingTask;
    }

    static SorcerersTowerMagicTreeWoodcuttingBotTask getSorcerersTowerMagicTreeWoodcuttingTask() {
        return sorcerersTowerMagicTreeWoodcuttingTask;
    }

    static VarrockRuneEssenceMiningBotTask getVarrockRuneEssenceMiningTask() {
        return varrockRuneEssenceMiningTask;
    }

    static AirRuneRunecraftingBotTask getAirRuneRunecraftingTask() {
        return airRuneRunecraftingTask;
    }

    static MindRuneRunecraftingBotTask getMindRuneRunecraftingTask() {
        return mindRuneRunecraftingTask;
    }

    static WaterRuneRunecraftingBotTask getWaterRuneRunecraftingTask() {
        return waterRuneRunecraftingTask;
    }

    static EarthRuneRunecraftingBotTask getEarthRuneRunecraftingTask() {
        return earthRuneRunecraftingTask;
    }

    static FireRuneRunecraftingBotTask getFireRuneRunecraftingTask() {
        return fireRuneRunecraftingTask;
    }

    static BodyRuneRunecraftingBotTask getBodyRuneRunecraftingTask() {
        return bodyRuneRunecraftingTask;
    }

    static FaladorWineOfZamorakTelegrabBotTask getFaladorWineOfZamorakTelegrabTask() {
        return faladorWineOfZamorakTelegrabTask;
    }

    static DraynorSheepShearingBotTask getDraynorSheepShearingTask() {
        return draynorSheepShearingTask;
    }

    static LumbridgeWoolSpinningBotTask getLumbridgeWoolSpinningTask() {
        return lumbridgeWoolSpinningTask;
    }

    static SeersFlaxSpinningBotTask getSeersFlaxSpinningTask() {
        return seersFlaxSpinningTask;
    }

    static DraynorChickenCombatBotTask getDraynorChickenCombatTask() {
        return draynorChickenCombatTask;
    }

    static FaladorCowCombatBotTask getFaladorCowCombatTask() {
        return faladorCowCombatTask;
    }

    static KaramjaVolcanoNorthLesserDemonCombatBotTask getKaramjaVolcanoNorthLesserDemonCombatTask() {
        return karamjaVolcanoNorthLesserDemonCombatTask;
    }

    static KaramjaVolcanoSouthLesserDemonCombatBotTask getKaramjaVolcanoSouthLesserDemonCombatTask() {
        return karamjaVolcanoSouthLesserDemonCombatTask;
    }

    static LumbridgeEastChickenCombatBotTask getLumbridgeEastChickenCombatTask() {
        return lumbridgeEastChickenCombatTask;
    }

    static LumbridgeWestChickenCombatBotTask getLumbridgeWestChickenCombatTask() {
        return lumbridgeWestChickenCombatTask;
    }

    static LumbridgeCowCombatBotTask getLumbridgeCowCombatTask() {
        return lumbridgeCowCombatTask;
    }

    static EdgevilleDungeonHillGiantCombatBotTask getEdgevilleDungeonHillGiantCombatTask() {
        return edgevilleDungeonHillGiantCombatTask;
    }

    static EdgevilleDungeonNorthMossGiantCombatBotTask getEdgevilleDungeonNorthMossGiantCombatTask() {
        return edgevilleDungeonNorthMossGiantCombatTask;
    }

    static EdgevilleDungeonSouthMossGiantCombatBotTask getEdgevilleDungeonSouthMossGiantCombatTask() {
        return edgevilleDungeonSouthMossGiantCombatTask;
    }

    static DraynorGoblinCombatBotTask getDraynorGoblinCombatTask() {
        return draynorGoblinCombatTask;
    }

    static AlKharidWarriorCombatBotTask getAlKharidWarriorCombatTask() {
        return alKharidWarriorCombatTask;
    }

    static VarrockGuardCombatBotTask getVarrockGuardCombatTask() {
        return varrockGuardCombatTask;
    }

    static VarrockSewerGiantRatCombatBotTask getVarrockSewerGiantRatCombatTask() {
        return varrockSewerGiantRatCombatTask;
    }

    static BarbarianVillageBarbarianCombatBotTask getBarbarianVillageBarbarianCombatTask() {
        return barbarianVillageBarbarianCombatTask;
    }

    static DwarvenMineDwarfCombatBotTask getDwarvenMineDwarfCombatTask() {
        return dwarvenMineDwarfCombatTask;
    }

    static FaladorGuardCombatBotTask getFaladorGuardCombatTask() {
        return faladorGuardCombatTask;
    }

    static EdgevilleDungeonSpiderRatCombatBotTask getEdgevilleDungeonSpiderRatCombatTask() {
        return edgevilleDungeonSpiderRatCombatTask;
    }

    static EdgevilleDungeonSkeletonCombatBotTask getEdgevilleDungeonSkeletonCombatTask() {
        return edgevilleDungeonSkeletonCombatTask;
    }

    static VarrockSouthChickenCombatBotTask getVarrockSouthChickenCombatTask() {
        return varrockSouthChickenCombatTask;
    }

    static BrimhavenDungeonBlueDragonSouthCombatBotTask getBrimhavenDungeonBlueDragonSouthCombatTask() {
        return brimhavenDungeonBlueDragonSouthCombatTask;
    }

    static BrimhavenDungeonRedDragonCombatBotTask getBrimhavenDungeonRedDragonCombatTask() {
        return brimhavenDungeonRedDragonCombatTask;
    }

    static TaverleyDungeonHellhoundCombatBotTask getTaverleyDungeonHellhoundCombatTask() {
        return taverleyDungeonHellhoundCombatTask;
    }
}
