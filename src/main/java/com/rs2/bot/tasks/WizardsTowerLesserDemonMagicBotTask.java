package com.rs2.bot.tasks;

import com.rs2.ServerSettings;
import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class WizardsTowerLesserDemonMagicBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3092, 3245, 0);
    private static int[] ignoredLootItemIds = new int[]{592, 1993};
    private static BotRoute[] taskRouteSegments = new BotRoute[]{new BotRoute(new Position[]{new Position(3093, 3247, 0), new Position(3098, 3247, 0), new Position(3098, 3239, 0), new Position(3101, 3229, 0), new Position(3113, 3222, 0), new Position(3117, 3215, 0), ServerSettings.cacheVersion < 366 ? new Position(3122, 3206, 0) : new Position(3114, 3206, 0), new Position(3113, 3190, 0), new Position(3113, 3174, 0), new Position(3109, 3167, 0)}), new BotRoute(new Position[]{new Position(3109, 3166, 0), ServerSettings.cacheVersion < 366 ? new Position(3108, 3162, 0) : new Position(3107, 3163, 0)}), new BotRoute(new Position[]{ServerSettings.cacheVersion < 366 ? new Position(3107, 3161, 0) : new Position(3106, 3162, 0), new Position(3105, 3160, 0)}), new BotRoute(new Position[]{new Position(3104, 3161, 1), new Position(3105, 3160, 1)}), new BotRoute(new Position[]{new Position(3104, 3161, 2), new Position(3107, 3162, 2)}), new BotRoute(new Position[]{new Position(3108, 3162, 2), new Position(3110, 3162, 2), new Position(3110, 3159, 2)})};

    public WizardsTowerLesserDemonMagicBotTask(int value3) {
        super(routeStartPosition, taskRouteSegments, 1, false, 1);
        boolean enabled = true;
        WizardsTowerLesserDemonMagicBotTask wizardsTowerLesserDemonMagicBotTask = this;
        this.combatTask = true;
        super.setForcedCombatStyle(2);
        int[] integerValues = ignoredLootItemIds;
        wizardsTowerLesserDemonMagicBotTask = this;
        ((BotTaskDefinition)this).ignoredLootItemIds = integerValues;
        int value2 = 10;
        wizardsTowerLesserDemonMagicBotTask = this;
        this.targetSearchRadius = value2;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(82);
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getSkillManager().getCurrentLevels()[6] >= 33;
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> requiredItems = new ArrayList<ItemStack>();
        requiredItems.add(new ItemStack(563, 5));
        return requiredItems;
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        GameplayHelper.prepareBotCombatStyle(player, 2);
        BotCombatHelper.grantBotSpellRunes(player, SpellDefinition.TELEKINETIC_GRAB, 1000);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        GameplayHelper.resetBotSkillsToBase(player);
        int value = 30 + GameUtil.randomInt(20);
        int value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 0, value - value / 5 + GameUtil.randomInt(value2));
        value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 2, value - value / 5 + GameUtil.randomInt(value2));
        value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 1, value - value / 5 + GameUtil.randomInt(value2));
        value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 4, value - value / 5 + GameUtil.randomInt(value2));
        value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 5, value - (value / 5 << 1) + GameUtil.randomInt(value2));
        value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        if ((value = value - value / 5 + GameUtil.randomInt(value2)) < 33) {
            value = 33;
        }
        BotCombatHelper.setBotSkillLevel(player, 6, value);
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] skillManager = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        skillManager[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
    }

    @Override
    public final void startWalkToTask(Player player) {
        int value = ServerSettings.cacheVersion < 366 ? 1536 : 11993;
        player.setAutoRetaliate(true);
        player.botTaskState = "walk towards task";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = 0;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
        player.botTargetNpcId = value;
        player.continueBotRoute();
    }

    @Override
    public final void startWalkToBank(Player player) {
        int value = ServerSettings.cacheVersion < 366 ? 1536 : 11993;
        player.setAutoRetaliate(false);
        player.botTaskState = "walk towards bank";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = taskRouteSegments.length - 1;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
        player.botTargetNpcId = value;
        player.continueBotRoute();
    }

    @Override
    public final void continueWalkToTask(Player player, int value3) {
        int value2 = ServerSettings.cacheVersion < 366 ? 1536 : 11993;
        player.setAutoRetaliate(true);
        player.botPathWaypointIndex = value3;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
        player.botTargetNpcId = value2;
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void continueWalkToBank(Player player, int value3) {
        int value2 = ServerSettings.cacheVersion < 366 ? 1536 : 11993;
        player.setAutoRetaliate(false);
        player.botPathWaypointIndex = value3;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
        player.botTargetNpcId = value2;
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void advanceTaskRouteSegment(Player player, boolean enabled2) {
        if (player.botTaskState.equals("walk towards task") || player.botTaskState.equals("walk to task") && enabled2) {
            if (!enabled2) {
                ++player.botPathSegmentIndex;
            }
            player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
            if (!enabled2) {
                player.botPathWaypointIndex = 0;
            }
            if (player.botPathSegmentIndex == 3) {
                int value = ServerSettings.cacheVersion < 366 ? 1738 : 12536;
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(value);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
                return;
            }
            if (player.botPathSegmentIndex == 4) {
                int value2 = ServerSettings.cacheVersion < 366 ? 1739 : 12537;
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(value2);
                player.botInteractionOption = 2;
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
                return;
            }
            if (player.botPathSegmentIndex == 5) {
                player.botInteractionOption = 1;
                player.botTaskState = "walk to task";
                return;
            }
        } else if (player.botTaskState.equals("walk towards bank") || player.botTaskState.equals("walk to bank") && enabled2) {
            if (!enabled2) {
                --player.botPathSegmentIndex;
            }
            player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
            if (!enabled2) {
                player.botPathWaypointIndex = 0;
            }
            if (player.botPathSegmentIndex == 3) {
                int value3 = ServerSettings.cacheVersion < 366 ? 1740 : 12538;
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(value3);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
                return;
            }
            if (player.botPathSegmentIndex == 2) {
                int value4 = ServerSettings.cacheVersion < 366 ? 1739 : 12537;
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(value4);
                player.botInteractionOption = 3;
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
                return;
            }
            if (player.botPathSegmentIndex == 1) {
                player.botInteractionOption = 1;
                return;
            }
            if (player.botPathSegmentIndex == 0) {
                player.botTaskState = "walk to bank";
            }
        }
    }
}

