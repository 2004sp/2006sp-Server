package com.rs2.bot.combat;

import com.rs2.bot.combat.BotCombatEscapeLogoutTask;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;
import com.rs2.util.path.PathFinder;
import com.rs2.util.path.WalkingCollisionMap;
import java.util.ArrayList;

public final class BotCombatEscapeHandler {
    private static RectangularArea[] chaosTempleEscapeAreas = new RectangularArea[]{new RectangularArea(3218, 3640, 3233, 3613), new RectangularArea(3220, 3613, 3256, 3607), new RectangularArea(3253, 3607, 3222, 3604), new RectangularArea(3251, 3603, 3225, 3600), new RectangularArea(3251, 3600, 3230, 3596), new RectangularArea(3249, 3597, 3232, 3594), new RectangularArea(3245, 3594, 3250, 3592), new RectangularArea(3233, 3611, 3257, 3647), new RectangularArea(3257, 3619, 3259, 3655), new RectangularArea(3259, 3623, 3261, 3655), new RectangularArea(3260, 3626, 3267, 3659), new RectangularArea(3267, 3627, 3269, 3665), new RectangularArea(3269, 3629, 3279, 3667), new RectangularArea(3279, 3670, 3288, 3664), new RectangularArea(3290, 3665, 3279, 3660), new RectangularArea(3278, 3660, 3288, 3657), new RectangularArea(3279, 3657, 3282, 3647), new RectangularArea(3286, 3657, 3281, 3656), new RectangularArea(3285, 3656, 3281, 3654), new RectangularArea(3282, 3654, 3283, 3650)};
    private static Position[] chaosTempleEscapeWaypoints = new Position[]{new Position(3236, 3635, 0), new Position(3219, 3628, 0), new Position(3213, 3617, 0)};
    private static RectangularArea[] p2pGateSouthEscapeAreas = new RectangularArea[]{new RectangularArea(3249, 3903, 3190, 3856), new RectangularArea(3214, 3857, 3236, 3853), new RectangularArea(3219, 3853, 3231, 3850)};
    private static Position[] p2pGateSouthEscapeWaypoints = new Position[]{new Position(3203, 3867, 0), new Position(3174, 3868, 0), new Position(3150, 3865, 0)};
    private static RectangularArea[] p2pGateSouthwestEscapeAreas = new RectangularArea[]{new RectangularArea(3159, 3867, 3189, 3864), new RectangularArea(3161, 3864, 3185, 3862), new RectangularArea(3165, 3862, 3182, 3860), new RectangularArea(3168, 3860, 3179, 3857), new RectangularArea(3176, 3855, 3172, 3857)};
    private static Position[] p2pGateSouthwestEscapeWaypoints = new Position[]{new Position(3173, 3867, 0), new Position(3152, 3867, 0), new Position(3152, 3861, 0)};
    private static RectangularArea[] ruinsNorthEscapeAreas = new RectangularArea[]{new RectangularArea(3186, 3749, 3139, 3761)};
    private static Position[] ruinsNorthEscapeWaypoints = new Position[]{new Position(3191, 3751, 0)};
    private static RectangularArea[] runeRockEscapeAreas = new RectangularArea[]{new RectangularArea(3006, 3877, 3074, 3902), new RectangularArea(3006, 3871, 3061, 3880), new RectangularArea(3006, 3867, 3051, 3873), new RectangularArea(3006, 3864, 3053, 3869), new RectangularArea(3006, 3859, 3043, 3866), new RectangularArea(3006, 3856, 3029, 3861)};
    private static Position[] runeRockEscapeWaypoints = new Position[]{new Position(3042, 3880, 0), new Position(3027, 3869, 0), new Position(3012, 3863, 0), new Position(3005, 3849, 0)};

    public static boolean tryStartBotCombatEscape(Player player) {
        if (player.currentBotTask != null && !player.isInWilderness()) {
            player.currentBotTask.startWalkToBank(player);
            return true;
        }
        if (player.botCombatEscapeActive) {
            return false;
        }
        player.botCombatEscapeActive = true;
        if (player.currentGroup != null) {
            player.currentGroup.deferredRemovedMembers.add(player);
            player.pendingGroupCleanup = player.currentGroup;
            player.currentGroup.removeMember(player);
        }
        CombatManager.stopCombat(player);
        if (player.botCombatTickTask != null && player.botCombatTickTask.isActive()) {
            player.botCombatTickTask.stop();
        }
        if (player.getEquipmentManager().getItemIdAtSlot(3) != player.botWeaponItemId) {
            player.getEquipmentManager().equipFromInventorySlot(player.getInventoryManager().getContainer().indexOfItem(player.botWeaponItemId));
            if (player.botShieldItemId != 0 && player.getEquipmentManager().getItemIdAtSlot(5) != player.botShieldItemId) {
                player.getEquipmentManager().equipFromInventorySlot(player.getInventoryManager().getContainer().indexOfItem(player.botShieldItemId));
            }
            player.botActiveCombatStyle = player.botPrimaryCombatStyle;
        }
        player.botCombatState = "escape";
        if (player.getEquipmentManager().getItemIdAtSlot(2) == 1712 && !player.isTeleblocked()) {
            BotCombatHelper.operateGloryTeleport(player);
            return true;
        }
        if (player.getSpellbook() == Spellbook.MODERN) {
            if (SpellDefinition.VARROCK_TELEPORT.getRequiredLevel() <= player.getSkillManager().getCurrentLevels()[6] && !player.isTeleblocked() && BotCombatHelper.hasRunesForSpell(player, SpellDefinition.VARROCK_TELEPORT)) {
                MagicSpellAction.castSelfSpell(player, SpellDefinition.VARROCK_TELEPORT);
                return true;
            }
            BotCombatEscapeHandler.startBotCombatWalkingEscape(player);
            return false;
        }
        if (player.getSpellbook() == Spellbook.ANCIENT) {
            if (SpellDefinition.PADDEWWA_TELEPORT.getRequiredLevel() <= player.getSkillManager().getCurrentLevels()[6] && !player.isTeleblocked() && BotCombatHelper.hasRunesForSpell(player, SpellDefinition.PADDEWWA_TELEPORT)) {
                MagicSpellAction.castSelfSpell(player, SpellDefinition.PADDEWWA_TELEPORT);
                return true;
            }
            BotCombatEscapeHandler.startBotCombatWalkingEscape(player);
            return false;
        }
        return false;
    }

    static void processBotCombatEscape(Player player) {
        if (player.botFoodItemId != -1) {
            FoodDefinition food = FoodDefinition.forItemId(player.botFoodItemId);
            if (food != null) {
                int healAmount = food.getHealAmount();
                int healedLevel = player.getSkillManager().getCurrentLevels()[3] + healAmount;
                player.getSkillManager();
                if (healedLevel <= SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]) && !player.botFoodDepleted) {
                    if (player.botEatDelayTicks < 3) {
                        ++player.botEatDelayTicks;
                    } else {
                        BotCombatHelper.eatBotFood(player);
                        player.botEatDelayTicks = 0;
                    }
                }
            }
        }
        if (player.getPoisonDamage() > 0.0) {
            BotCombatHelper.drinkAntipoisonPotion(player);
        }
        if (!player.getRecentCombatTimer().hasElapsed()) {
            if (player.getSkillManager().getCurrentLevels()[5] > 1 && !player.getActivePrayers()[8]) {
                player.getSkillManager();
                if (SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[5]) >= 25) {
                    player.getPrayerManager().togglePrayer(8);
                }
            }
            if (BotCombatHelper.hasPrayerLevelForCombatStyle(player, player.botOpponentCombatStyle)) {
                BotCombatHelper.toggleProtectionPrayerForOpponentStyle(player);
            }
        } else {
            player.getPrayerManager().deactivateAll();
        }
        if (player.getPosition().getY() < 3760 && player.botCombatState != null && player.botCombatState.equals("tele") && player.getEquipmentManager().getItemIdAtSlot(2) == 1712 && !player.isTeleblocked()) {
            BotCombatHelper.operateGloryTeleport(player);
        }
        if (player.getPosition().getY() < 3680 && player.botCombatState != null && player.botCombatState.equals("tele") && !player.isTeleblocked()) {
            if (player.getSpellbook() == Spellbook.MODERN) {
                MagicSpellAction.castSelfSpell(player, SpellDefinition.VARROCK_TELEPORT);
            } else if (player.getSpellbook() == Spellbook.ANCIENT) {
                MagicSpellAction.castSelfSpell(player, SpellDefinition.PADDEWWA_TELEPORT);
            }
        }
        if (BotCombatHelper.isPlayerInAnyArea(player, chaosTempleEscapeAreas) || player.botEscapeRouteName.equals("chaos temple")) {
            if (!player.botEscapeRouteName.equals("chaos temple")) {
                player.botEscapeRouteName = "chaos temple";
                player.botPathWaypointIndex = 0;
            }
            BotCombatHelper.advanceBotEscapeWaypoints(player, chaosTempleEscapeWaypoints);
            return;
        }
        if (BotCombatHelper.isPlayerInAnyArea(player, runeRockEscapeAreas) || player.botEscapeRouteName.equals("rune rocks")) {
            if (!player.botEscapeRouteName.equals("rune rocks")) {
                player.botEscapeRouteName = "rune rocks";
                player.botPathWaypointIndex = 0;
            }
            BotCombatHelper.advanceBotEscapeWaypoints(player, runeRockEscapeWaypoints);
            return;
        }
        if (BotCombatHelper.isPlayerInAnyArea(player, p2pGateSouthEscapeAreas) || player.botEscapeRouteName.equals("escape south of p2p gates")) {
            if (!player.botEscapeRouteName.equals("escape south of p2p gates")) {
                player.botEscapeRouteName = "escape south of p2p gates";
                player.botPathWaypointIndex = 0;
            }
            BotCombatHelper.advanceBotEscapeWaypoints(player, p2pGateSouthEscapeWaypoints);
            return;
        }
        if (BotCombatHelper.isPlayerInAnyArea(player, p2pGateSouthwestEscapeAreas) || player.botEscapeRouteName.equals("escape south west of p2p gates")) {
            if (!player.botEscapeRouteName.equals("escape south west of p2p gates")) {
                player.botEscapeRouteName = "escape south west of p2p gates";
                player.botPathWaypointIndex = 0;
            }
            BotCombatHelper.advanceBotEscapeWaypoints(player, p2pGateSouthwestEscapeWaypoints);
            return;
        }
        if (BotCombatHelper.isPlayerInAnyArea(player, ruinsNorthEscapeAreas) || player.botEscapeRouteName.equals("escape north of ruins")) {
            if (!player.botEscapeRouteName.equals("escape north of ruins")) {
                player.botEscapeRouteName = "escape north of ruins";
                player.botPathWaypointIndex = 0;
            }
            BotCombatHelper.advanceBotEscapeWaypoints(player, ruinsNorthEscapeWaypoints);
            return;
        }
        if (player.getPosition().getY() < 3904 && !player.botEscapeRouteName.equals("escape p2p gates")) {
            if (player.botEscapeRouteName.equals("")) {
                BotCombatEscapeHandler.walkFallbackEscapePath(player);
                player.botPathWaypointIndex = -1;
            }
            return;
        }
        if (!player.botEscapeRouteName.equals("escape p2p gates")) {
            player.botEscapeRouteName = "escape p2p gates";
            player.botPathWaypointIndex = 0;
        }
        Position gatePosition = new Position(3224, 3904);
        if (GameUtil.isWithinDistance(player.getPosition(), gatePosition, 1)) {
            ArrayList<Integer> objectTargets = new ArrayList<Integer>();
            objectTargets.add(1597);
            objectTargets.add(1596);
            player.interactWithBotObjectTargets(objectTargets);
            return;
        }
        BotCombatHelper.walkBotTowardPosition(player, gatePosition);
    }

    private static void walkFallbackEscapePath(Player player) {
        int position = player.getPosition().getX();
        int position2 = player.getPosition().getY() - 20;
        if (position2 <= 3519) {
            position2 = 3519;
        }
        boolean enabled = false;
        int index = 0;
        if (WalkingCollisionMap.getTileFlags(position, position2, 0) != 0) {
            enabled = true;
        }
        while (enabled && index < 20) {
            position = player.getPosition().getX() + GameUtil.randomInt(20) - 10;
            enabled = WalkingCollisionMap.getTileFlags(position, position2 += index, 0) != 0;
            ++index;
        }
        if (!player.isMovementLocked()) {
            PathFinder.getInstance();
            PathFinder.findPath(player, position, position2, true, 0, 0);
        }
    }

    public static boolean startBotCombatWalkingEscape(Player player) {
        player.getMovementQueue().setRunning(true);
        player.setAutoRetaliate(false);
        if (!player.getRecentCombatTimer().hasElapsed()) {
            if (player.getSkillManager().getCurrentLevels()[5] > 1 && !player.getActivePrayers()[8]) {
                player.getSkillManager();
                if (SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[5]) >= 25) {
                    player.getPrayerManager().togglePrayer(8);
                }
            }
            if (BotCombatHelper.hasPrayerLevelForCombatStyle(player, player.botOpponentCombatStyle)) {
                BotCombatHelper.toggleProtectionPrayerForOpponentStyle(player);
            }
        } else {
            player.getPrayerManager().deactivateAll();
        }
        BotCombatEscapeHandler.processBotCombatEscape(player);
        player.botEscapeLogoutTask = new BotCombatEscapeLogoutTask(2, player);
        World.getTaskScheduler().schedule(player.botEscapeLogoutTask);
        return true;
    }
}

