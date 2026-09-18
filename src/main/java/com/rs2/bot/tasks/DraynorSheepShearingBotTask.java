package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class DraynorSheepShearingBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3092, 3245, 0);
    private static BotRoute[] taskRouteSegments = new BotRoute[]{new BotRoute(new Position[]{new Position(3093, 3247, 0), new Position(3098, 3247, 0), new Position(3098, 3239, 0), new Position(3104, 3234, 0), new Position(3110, 3232, 0), new Position(3115, 3228, 0), new Position(3135, 3228, 0), new Position(3152, 3234, 0), new Position(3161, 3239, 0), new Position(3173, 3240, 0), new Position(3180, 3247, 0), new Position(3190, 3245, 0), new Position(3198, 3254, 0), new Position(3213, 3255, 0), new Position(3213, 3261, 0)}), new BotRoute(new Position[]{new Position(3212, 3261, 0), new Position(3204, 3263, 0)})};

    public DraynorSheepShearingBotTask(int value3) {
        super(routeStartPosition, taskRouteSegments, 1, false, 4);
        int value2 = 3194;
        DraynorSheepShearingBotTask draynorSheepShearingBotTask = this;
        this.targetMinX = value2;
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> requiredItems = new ArrayList<ItemStack>();
        requiredItems.add(new ItemStack(1735, 1));
        return requiredItems;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(43);
        player.botInteractionTargetIds.add(1765);
        player.botTaskItemId = 1735;
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        ItemStack[] itemStackArray = new ItemStack[]{new ItemStack(1735)};
        player.botTaskRequiredItems = itemStackArray;
        player.getInventoryManager().addItem(itemStackArray[0]);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        GameplayHelper.resetBotSkillsToBase(player);
        int value = 1 + GameUtil.randomInt(10);
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
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] skillManager = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        skillManager[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
    }

    @Override
    public final void startWalkToTask(Player player) {
        int value = !GameplayHelper.isObjectDefinitionIdValid(12986) ? 1551 : 12986;
        player.botUseTaskItemOnTarget = false;
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
        int value = !GameplayHelper.isObjectDefinitionIdValid(12986) ? 1551 : 12986;
        player.botUseTaskItemOnTarget = false;
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
        int value2 = !GameplayHelper.isObjectDefinitionIdValid(12986) ? 1551 : 12986;
        player.botUseTaskItemOnTarget = false;
        player.setAutoRetaliate(true);
        player.botPathWaypointIndex = value3;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
        player.botTargetNpcId = value2;
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void continueWalkToBank(Player player, int value3) {
        int value2 = !GameplayHelper.isObjectDefinitionIdValid(12986) ? 1551 : 12986;
        player.botUseTaskItemOnTarget = false;
        player.setAutoRetaliate(false);
        player.botPathWaypointIndex = value3;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
        player.botTargetNpcId = value2;
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void advanceTaskRouteSegment(Player player, boolean enabled2) {
        int value = !GameplayHelper.isObjectDefinitionIdValid(12986) ? 1551 : 12986;
        player.botUseTaskItemOnTarget = false;
        if (player.botTaskState.equals("walk towards task") || player.botTaskState.equals("walk to task") && enabled2) {
            if (!enabled2) {
                ++player.botPathSegmentIndex;
            }
            player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
            if (!enabled2) {
                player.botPathWaypointIndex = 0;
            }
            player.botTargetNpcId = value;
            if (player.botPathSegmentIndex == taskRouteSegments.length - 1) {
                player.botTaskState = "walk to task";
                player.botUseTaskItemOnTarget = true;
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
            player.botTargetNpcId = value;
            if (player.botPathSegmentIndex == 0) {
                player.botTaskState = "walk to bank";
            }
        }
    }
}

