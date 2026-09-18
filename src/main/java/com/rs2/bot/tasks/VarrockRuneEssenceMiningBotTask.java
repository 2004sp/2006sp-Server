package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.SkillManager;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class VarrockRuneEssenceMiningBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3254, 3420, 0);
    private static BotRoute[] taskRouteSegments = new BotRoute[]{new BotRoute(new Position[]{new Position(3254, 3428, 0), new Position(3260, 3425, 0), new Position(3260, 3414, 0), new Position(3257, 3400, 0), new Position(3253, 3398, 0)}), new BotRoute(new Position[]{new Position(3253, 3399, 0)}), new BotRoute(new Position[]{new Position(2911, 4832, 0)})};

    public VarrockRuneEssenceMiningBotTask(int value3) {
        super(routeStartPosition, taskRouteSegments, 0, false, 6);
        int value2 = 30;
        VarrockRuneEssenceMiningBotTask varrockRuneEssenceMiningBotTask = this;
        this.targetSearchRadius = value2;
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        arrayList.add(new ItemStack(ItemCombinationHandler.getOwnedOrFallbackGatheringTool(player, 14).getToolItemId(), 1));
        return arrayList;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(2491);
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        if (player.getSkillManager().getCurrentLevels()[14] >= 41 && player.getSkillManager().getCurrentLevels()[0] >= 40) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1275));
        } else if (player.getSkillManager().getCurrentLevels()[14] >= 31 && player.getSkillManager().getCurrentLevels()[0] >= 30) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1271));
        } else if (player.getSkillManager().getCurrentLevels()[14] >= 21 && player.getSkillManager().getCurrentLevels()[0] >= 20) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1273));
        } else if (player.getSkillManager().getCurrentLevels()[14] >= 6 && player.getSkillManager().getCurrentLevels()[0] >= 5) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1269));
        } else {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(GameUtil.randomInt(2) == 0 ? 1267 : 1265));
        }
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        GameplayHelper.resetBotSkillsToBase(player);
        int value = 1 + GameUtil.randomInt(99);
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
        BotCombatHelper.setBotSkillLevel(player, 6, value - value / 5 + GameUtil.randomInt(value2));
        value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 5, value - (value / 5 << 1) + GameUtil.randomInt(value2));
        value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 14, value - value / 5 + GameUtil.randomInt(value2));
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] skillManager = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        skillManager[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
    }

    @Override
    public final void startWalkToTask(Player player) {
        player.botInteractionOption = 1;
        player.setAutoRetaliate(true);
        player.botTaskState = "walk towards task";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = 0;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
        player.botTargetNpcId = 1530;
        player.continueBotRoute();
    }

    @Override
    public final void startWalkToBank(Player player) {
        player.botInteractionOption = 1;
        player.setAutoRetaliate(false);
        player.botTaskState = "walk towards bank";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = taskRouteSegments.length - 2;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(2492);
        player.interactWithBotObjectTargets(arrayList);
        player.botRouteActionPending = true;
        player.botTargetNpcId = 1530;
        player.continueBotRoute();
    }

    @Override
    public final void continueWalkToTask(Player player, int value2) {
        player.botInteractionOption = 1;
        player.setAutoRetaliate(true);
        player.botPathWaypointIndex = value2;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
        player.botTargetNpcId = 1530;
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void continueWalkToBank(Player player, int value2) {
        player.botInteractionOption = 1;
        player.setAutoRetaliate(false);
        player.botPathWaypointIndex = value2;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
        value2 = GameUtil.getRegionId(player.getPosition().getX(), player.getPosition().getY());
        if (player.botPathSegmentIndex == taskRouteSegments.length - 2 && value2 == 11595) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            arrayList.add(2492);
            player.interactWithBotObjectTargets(arrayList);
            player.botRouteActionPending = true;
        }
        player.botTargetNpcId = 1530;
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void advanceTaskRouteSegment(Player player, boolean continuing) {
        player.botInteractionOption = 1;
        if (player.botTaskState.equals("walk towards task") || player.botTaskState.equals("walk to task") && continuing) {
            if (!continuing) {
                ++player.botPathSegmentIndex;
            }
            player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
            if (!continuing) {
                player.botPathWaypointIndex = 0;
            }
            player.botTargetNpcId = 1530;
            int regionId = GameUtil.getRegionId(player.getPosition().getX(), player.getPosition().getY());
            if (player.botPathSegmentIndex == taskRouteSegments.length - 1 && player.botTaskState.equals("walk towards task") || player.botTaskState.equals("walk to task") && regionId != 11595) {
                player.botTaskState = "walk to task";
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(553);
                player.botInteractionOption = 3;
                player.interactWithBotNpcTargets(arrayList);
                player.botRouteActionPending = true;
                player.botInteractionOption = 1;
                return;
            }
        } else if (player.botTaskState.equals("walk towards bank") || player.botTaskState.equals("walk to bank") && continuing) {
            if (!continuing) {
                --player.botPathSegmentIndex;
            }
            player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
            if (!continuing) {
                player.botPathWaypointIndex = 0;
            }
            player.botTargetNpcId = 1530;
            if (player.botPathSegmentIndex == taskRouteSegments.length - 1 && player.botTaskState.equals("walk towards bank")) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(2492);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
            }
            if (player.botPathSegmentIndex == 0) {
                player.botTaskState = "walk to bank";
            }
        }
    }
}

