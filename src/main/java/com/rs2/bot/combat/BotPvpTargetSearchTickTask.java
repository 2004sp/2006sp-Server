package com.rs2.bot.combat;

import com.rs2.bot.combat.BotCombatEscapeHandler;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.combat.BotCombatLoadoutManager;
import com.rs2.model.Entity;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.path.PathReachability;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerGroup;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import com.rs2.util.path.PathFinder;
import java.util.ArrayList;
import java.util.Collections;

public final class BotPvpTargetSearchTickTask
extends TickTask {
    private final Player bot;
    private final boolean ignoreLootRiskCheck;

    public BotPvpTargetSearchTickTask(int value2, Player player, boolean ignoreLootRiskCheck) {
        super(10);
        this.bot = player;
        this.ignoreLootRiskCheck = ignoreLootRiskCheck;
    }
    @Override
    public final void execute() {
        executeControlExit1: {
            int candidateIndex = 0;
            int value;
            int value2;
            Object value3;
            if (this.bot.isDead() || !this.bot.isRegistered()) {
                this.stop();
                return;
            }
            if (this.bot.botCombatEscapeActive) {
                if (this.bot.getPosition().equals(this.bot.botEscapeLastPosition) && this.bot.getRecentCombatTimer().hasElapsed()) {
                    ++this.bot.botEscapeStuckTicks;
                } else {
                    this.bot.botEscapeLastPosition = this.bot.getPosition().copy();
                    this.bot.botEscapeStuckTicks = 0;
                }
                if (this.bot.botEscapeStuckTicks >= 10) {
                    BotCombatLoadoutManager.startCombatLoadoutBot(this.bot);
                }
            }
            if (BotCombatHelper.hasExternalCombatTarget(this.bot) || this.bot.botCombatState != null || this.bot.botCombatEscapeActive) break executeControlExit1;
            if (!this.bot.isInWilderness()) {
                BotCombatHelper.prepareBotPvpSearchPosition(this.bot);
            }
            if (this.bot.getEquipmentManager().getItemIdAtSlot(3) != this.bot.botWeaponItemId) {
                this.bot.getEquipmentManager().equipFromInventorySlot(this.bot.getInventoryManager().getContainer().indexOfItem(this.bot.botWeaponItemId));
                if (this.bot.botShieldItemId != 0 && this.bot.getEquipmentManager().getItemIdAtSlot(5) != this.bot.botShieldItemId) {
                    this.bot.getEquipmentManager().equipFromInventorySlot(this.bot.getInventoryManager().getContainer().indexOfItem(this.bot.botShieldItemId));
                }
                this.bot.botActiveCombatStyle = this.bot.botPrimaryCombatStyle;
            }
            if (this.bot.botMagicPenaltyGearUnequipped) {
                BotCombatHelper.reequipMagicPenaltyGear(this.bot);
            }
            if (this.bot.getMovementQueue().isRunning()) {
                if (this.bot.currentGroup == null) {
                    this.bot.getMovementQueue().setRunning(false);
                } else if (this.bot.currentGroup.leader == this.bot) {
                    this.bot.getMovementQueue().setRunning(false);
                }
            }
            BotCombatHelper.disableBotCombatPrayers(this.bot);
            Object value4 = this.bot;
            if (((Player)value4).botPvpChatSource != null && ((Player)value4).botPvpChatMessage != null) {
                if (((Player)value4).currentGroup == null && ((Player)value4).botPvpPendingTeamTarget != null && ((Player)value4).botPvpPendingTeamTarget == ((Player)value4).botPvpChatSource) {
                    if (((Player)value4).botPvpChatMessage.toLowerCase().equals("sure") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("okay") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("ok") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("k")) {
                        ((Player)value4).botPvpPendingTeamTarget = null;
                        ((Player)value4).botPvpTeamInviteTicks = 0;
                        if (((Player)value4).botPvpChatSource.currentGroup == null) {
                            value3 = new PlayerGroup(((Player)value4).botPvpChatSource, (Player)value4);
                            ((PlayerGroup)value3).refreshGroupFollowChain();
                        } else {
                            ((Player)value4).botPvpChatSource.currentGroup.addMember((Player)value4);
                            ((Player)value4).botPvpChatSource.currentGroup.refreshGroupFollowChain();
                        }
                    } else if (((Player)value4).botPvpChatMessage.toLowerCase().equals("nty") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("nah") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("no") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("nope") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("no thanks")) {
                        ((Player)value4).botPvpPendingTeamTarget.botPvpTeamRequesters.remove(value4);
                        EntityTargetMovement.clearMovementTarget((Entity)value4);
                        ((Player)value4).botPvpRejectedTeamTargets.add(((Player)value4).botPvpPendingTeamTarget);
                        ((Player)value4).botPvpPendingTeamTarget = null;
                        ((Player)value4).botPvpTeamInviteTicks = 0;
                    }
                }
                if (((Player)value4).botPvpChatMessage.toLowerCase().equals("team") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("team?")) {
                    if (((Player)value4).currentGroup == null && BotCombatHelper.canTeamWithBotPvpPlayer((Player)value4, ((Player)value4).botPvpChatSource, false)) {
                        String[] responses = new String[]{"sure", "okay", "ok", "k"};
                        ((Player)value4).queuePublicChatMessage(responses[GameUtil.randomInt(4)]);
                        ((Player)value4).botPvpChatSource.botPvpTeamRequesters.add(value4);
                        if (((Player)value4).botPvpChatSource.currentGroup == null) {
                            PlayerGroup playerGroup = new PlayerGroup(((Player)value4).botPvpChatSource, (Player)value4);
                            playerGroup.refreshGroupFollowChain();
                        } else {
                            ((Player)value4).botPvpChatSource.currentGroup.addMember((Player)value4);
                            ((Player)value4).botPvpChatSource.currentGroup.refreshGroupFollowChain();
                        }
                    } else {
                        ((Player)value4).queuePublicChatMessage("nty");
                    }
                } else if (((Player)value4).currentGroup != null && ((Player)value4).botPvpChatSource.currentGroup != null && ((Player)value4).currentGroup == ((Player)value4).botPvpChatSource.currentGroup) {
                    if (((Player)value4).botPvpChatMessage.toLowerCase().equals("food") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("food?") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("food left") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("food left?")) {
                        value3 = ItemDefinition.forId(((Player)value4).botFoodItemId);
                        String displayName = ((ItemDefinition)value3).getDisplayName();
                        value2 = ((Player)value4).getInventoryManager().getItemAmount(((Player)value4).botFoodItemId);
                        String foodName = displayName;
                        if (value2 > 1) {
                            foodName = String.valueOf(displayName) + "s";
                        }
                        ((Player)value4).queuePublicChatMessage("I have " + value2 + " " + foodName + " left");
                    } else if (((Player)value4).botPvpChatMessage.toLowerCase().equals("skills") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("skills?") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("lvls") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("lvls?") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("stats") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("stats?")) {
                        int value5;
                        int value6;
                        int value7;
                        int value8;
                        value3 = "";
                        int player = ((Player)value4).getSkillManager().getBaseLevel(0);
                        if (player > 1) {
                            value3 = String.valueOf(value3) + "atk: " + player + " ";
                        }
                        if ((value2 = ((Player)value4).getSkillManager().getBaseLevel(1)) > 1) {
                            value3 = String.valueOf(value3) + "def: " + value2 + " ";
                        }
                        if ((value8 = ((Player)value4).getSkillManager().getBaseLevel(2)) > 1) {
                            value3 = String.valueOf(value3) + "str: " + value8 + " ";
                        }
                        if ((value = ((Player)value4).getSkillManager().getBaseLevel(6)) > 1) {
                            value3 = String.valueOf(value3) + "mage: " + value + " ";
                        }
                        if ((value7 = ((Player)value4).getSkillManager().getBaseLevel(4)) > 1) {
                            value3 = String.valueOf(value3) + "range: " + value7 + " ";
                        }
                        if ((value6 = ((Player)value4).getSkillManager().getBaseLevel(3)) > 10) {
                            value3 = String.valueOf(value3) + "hp: " + value6 + " ";
                        }
                        if ((value5 = ((Player)value4).getSkillManager().getBaseLevel(5)) > 1) {
                            value3 = String.valueOf(value3) + "pray: " + value5 + " ";
                        }
                        if (player == 1 && value2 == 1 && value8 == 1 && value == 1 && value7 == 1 && value6 == 10 && value5 == 1) {
                            value3 = "lol, i havent trained my skills at all";
                        }
                        ((Player)value4).queuePublicChatMessage((String)value3);
                    } else if (((Player)value4).botPvpChatMessage.toLowerCase().equals("leave") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("g2g") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("bye") || ((Player)value4).botPvpChatMessage.toLowerCase().equals("cya")) {
                        ((Player)value4).queuePublicChatMessage("cya");
                        EntityTargetMovement.clearMovementTarget((Entity)value4);
                        ((Player)value4).botPvpRejectedTeamTargets.add(((Player)value4).botPvpChatSource);
                        ((Player)value4).currentGroup.removeMember(((Player)value4).botPvpChatSource);
                    }
                }
                ((Player)value4).botPvpChatMessage = null;
                ((Player)value4).botPvpChatSource = null;
            }
            this.bot.botMagicGearSwapDelayTicks = 0;
            this.bot.botThreatEscapeDelayTicks = 0;
            this.bot.botPrayerSwitchDelayTicks = 0;
            this.bot.botQueuedPrayerId = -1;
            this.bot.botEatDelayTicks = 0;
            this.bot.botWeaponSwapDelayTicks = 0;
            if (!(this.bot.getCombatTarget() == null || this.bot.getCombatTarget().isDead() || this.bot.botCombatTickTask != null && this.bot.botCombatTickTask.isActive())) {
                this.bot.getMovementQueue().setRunning(true);
                CombatManager.startCombat(this.bot, this.bot.getCombatTarget());
            }
            value4 = FoodDefinition.forItemId(this.bot.botFoodItemId);
            int healAmount = ((FoodDefinition)((Object)value4)).getHealAmount();
            int skillManager = this.bot.getSkillManager().getCurrentLevels()[3] + healAmount;
            this.bot.getSkillManager();
            int levelForExperience = healAmount = skillManager <= SkillManager.getLevelForExperience(this.bot.getSkillManager().getExperience()[3]) ? 1 : 0;
            if (!this.bot.botFoodDepleted && healAmount != 0) {
                int value9 = healAmount = this.bot.isTeleblocked() ? 6 : 4;
                if (!BotCombatHelper.eatBotFood(this.bot) || this.bot.getInventoryManager().getItemAmount(this.bot.botFoodItemId) <= healAmount) {
                    BotCombatEscapeHandler.tryStartBotCombatEscape(this.bot);
                }
            }
            if (this.bot.getPoisonDamage() > 0.0) {
                BotCombatHelper.drinkAntipoisonPotion(this.bot);
            }
            if (this.bot.botPvpTeamInviteTicks >= 3) {
                this.bot.botPvpPendingTeamTarget.botPvpTeamRequesters.remove(this.bot);
                EntityTargetMovement.clearMovementTarget(this.bot);
                this.bot.botPvpRejectedTeamTargets.add(this.bot.botPvpPendingTeamTarget);
                this.bot.botPvpPendingTeamTarget = null;
                this.bot.botPvpTeamInviteTicks = 0;
            }
            if (this.bot.botPvpTeamInviteTicks > 0) {
                ++this.bot.botPvpTeamInviteTicks;
                return;
            }
            if (this.bot.currentGroup != null && this.bot.currentGroup.leader != this.bot) {
                if (!this.bot.currentGroup.leader.isInWilderness()) {
                    this.bot.currentGroup.removeMember(this.bot.currentGroup.leader);
                } else {
                    if (this.bot.getMovementTarget() == null) {
                        this.bot.currentGroup.refreshGroupFollowChain();
                    } else if (this.bot.getMovementQueue().isRunning() != this.bot.currentGroup.leader.getMovementQueue().isRunning()) {
                        this.bot.getMovementQueue().setRunning(this.bot.currentGroup.leader.getMovementQueue().isRunning());
                    }
                    if (this.bot.currentGroup.leader.getCombatTarget() != null && !this.bot.currentGroup.leader.getCombatTarget().isDead()) {
                        this.bot.getMovementQueue().setRunning(true);
                        CombatManager.startCombat(this.bot, this.bot.currentGroup.leader.getCombatTarget());
                    }
                }
            }
            if (this.bot.currentGroup != null && this.bot.currentGroup.leader != this.bot) {
                return;
            }
            ArrayList<Player> arrayList = new ArrayList<Player>();
            Player[] playerArray = World.players;
            value2 = World.players.length;
            boolean enabled = false;
            while (candidateIndex < value2) {
                executeControlExit2: {
                    executeControlExit3: {
                        executeControlExit4: {
                            value3 = playerArray[candidateIndex];
                            if (value3 == null || value3 == this.bot || ((Player)value3).clanWarsBot) break executeControlExit2;
                            if (this.bot.currentGroup == null) break executeControlExit3;
                            if (!this.bot.currentGroup.containsMember((Player)value3)) break executeControlExit4;
                            if (!((Entity)value3).getPosition().isWithinViewport(this.bot.getPosition())) {
                                this.bot.currentGroup.removeMember((Player)value3);
                            }
                            break executeControlExit2;
                        }
                        if (this.bot.currentGroup.deferredRemovedMembers.contains(value3)) break executeControlExit2;
                    }
                    if (((Entity)value3).getPosition().isWithinViewport(this.bot.getPosition())) {
                        value = BotCombatHelper.getEscapeCombatLevelMargin(this.bot);
                        if (((Player)value3).getCombatLevel() <= this.bot.getCombatLevel() + value) {
                            arrayList.add((Player)value3);
                        }
                    }
                }
                ++candidateIndex;
            }
            Collections.shuffle(arrayList);
            boolean enabled2 = false;
            for (Player player : arrayList) {
                if (player.getWildernessLevel() < Math.abs(this.bot.getCombatLevel() - player.getCombatLevel()) || !player.isInMultiCombatArea() && !player.getSingleCombatTimer().hasElapsed()) continue;
                if (!PathReachability.isReachable(this.bot, player.getPosition().getX(), player.getPosition().getY(), true, 0, 0) || BotCombatHelper.tryHandleBotPvpTeamGrouping(this.bot, player)) continue;
                Player teamTarget = player;
                if (player.currentGroup != null) {
                    teamTarget = player.currentGroup.leader;
                }
                if (!teamTarget.isBot && BotCombatHelper.canTeamWithBotPvpPlayer(this.bot, teamTarget, true) && !this.bot.botPvpRejectedTeamTargets.contains(teamTarget)) {
                    this.bot.getMovementQueue().setRunning(true);
                    this.bot.getUpdateState().setFaceEntity(player.getEncodedIndex());
                    this.bot.setAttackRange(1);
                    this.bot.setMovementTarget(player);
                    this.bot.botPvpPendingTeamTarget = teamTarget;
                    if (GameUtil.getDistance(this.bot.getPosition(), player.getPosition()) > 5) continue;
                    this.bot.queuePublicChatMessage("team?");
                    teamTarget.botPvpTeamRequesters.add(this.bot);
                    this.bot.botPvpTeamInviteTicks = 1;
                    continue;
                }
                if (!this.ignoreLootRiskCheck && !BotCombatHelper.isTargetLootWorthRisk(this.bot, player)) continue;
                if (!this.bot.getMovementQueue().isRunning()) {
                    this.bot.getMovementQueue().setRunning(true);
                }
                if (this.bot.currentGroup == null && player.currentGroup != null && (this.bot.isInMultiCombatArea() || player.isInMultiCombatArea()) && ((double)player.currentGroup.getHighestCombatLevel() >= (double)this.bot.getCombatLevel() * 0.7 || (double)player.currentGroup.getTotalCombatLevel() >= (double)this.bot.getCombatLevel() * 1.2)) continue;
                if (player == null || player.isDead()) break;
                if (this.bot.currentGroup == null) {
                    CombatManager.startCombat(this.bot, player);
                } else {
                    this.bot.currentGroup.attackTarget(player);
                }
                enabled2 = true;
                break;
            }
            if (!enabled2) {
                int position = this.bot.getPosition().getX() + GameUtil.randomInt(20) - 10;
                int position2 = this.bot.getPosition().getY() + GameUtil.randomInt(20) - 10;
                if (position < 2951) {
                    position = 2961;
                }
                if (position > 3376) {
                    position = 3366;
                }
                if (position2 < 3520) {
                    position2 = 3530;
                }
                if (position2 > this.bot.botWildernessMaxY) {
                    position2 = this.bot.botWildernessMaxY - 10;
                }
                PathFinder.getInstance();
                PathFinder.findPath(this.bot, position, position2, true, 0, 0);
            }
        }
    }
}
