package com.rs2.model.skill.firemaking;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.firemaking.FiremakingLog;
import com.rs2.model.skill.firemaking.FiremakingTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameplayTrace;

public final class FiremakingHandler {
    private Player player;
    private static int[] fireObjectIds = new int[]{2732, 11404, 11405, 11406};

    public FiremakingHandler(Player player) {
        this.player = player;
    }
    public final void startFiremaking(int value13, int value22, boolean enabled2, int value32, int value42, int value52) {
        startFiremakingControlExit1: {
            GroundItem groundItem;
            FiremakingLog firemakingLog;
            int fireY = value42;
            int firePlane = value52;
            int value6 = value42;
            Object value7;
            Object value8;
            int value9;
            startFiremakingControlExit2: {
                startFiremakingControlExit3: {
                    int player = value52;
                    int groundItem2 = value32;
                    Object value10;
                    startFiremakingControlExit4: {
                        if (!ServerSettings.firemakingEnabled) {
                            Player player2 = this.player;
                            player2.packetSender.sendGameMessage("This skill is currently disabled.");
                            return;
                        }
                        value13 = value13 == 590 ? value22 : value13;
                        value22 = value13;
                        FiremakingLog[] firemakingLogValues = FiremakingLog.values();
                        value9 = firemakingLogValues.length;
                        int firemakingLog2 = 0;
                        while (firemakingLog2 < value9) {
                            FiremakingLog firemakingLog22 = firemakingLogValues[firemakingLog2];
                            if (firemakingLog22.getLogItemId() == value22) {
                                value10 = firemakingLog22;
                                break startFiremakingControlExit4;
                            }
                            ++firemakingLog2;
                        }
                        value10 = value8 = null;
                    }
                    if (value10 == null) {
                        if (GameplayTrace.enabled()) {
                            GameplayTrace.log("firemaking no-log-match player=" + GameplayTrace.describe(this.player) + " firstItemId=" + value13 + " secondItemId=" + value22 + " x=" + groundItem2 + " y=" + value6 + " plane=" + player);
                        }
                        return;
                    }
                    if (!this.player.getInventoryManager().containsItem(590)) {
                        if (GameplayTrace.enabled()) {
                            GameplayTrace.log("firemaking missing-tinderbox player=" + GameplayTrace.describe(this.player) + " logItemId=" + ((FiremakingLog)value10).getLogItemId());
                        }
                        Player player3 = this.player;
                        player3.packetSender.sendGameMessage("You need a tinderbox to light this fire.");
                        return;
                    }
                    value7 = this.player;
                    if (System.currentTimeMillis() < 200L) {
                        return;
                    }
                    if (this.player.isInFiremakingRestrictedArea() || this.player.isInCastleWars() || this.player.isInDuelArena()) {
                        Player player4 = this.player;
                        player4.packetSender.sendGameMessage("You can't light a fire here.");
                        return;
                    }
                    ObjectManager.getInstance();
                    value7 = ObjectManager.findDynamicObjectAt(groundItem2, value6, player);
                    if (value7 != null) {
                        if (GameplayTrace.enabled()) {
                            GameplayTrace.log("firemaking blocked-existing-object player=" + GameplayTrace.describe(this.player) + " logItemId=" + ((FiremakingLog)value10).getLogItemId() + " x=" + groundItem2 + " y=" + value6 + " plane=" + player);
                        }
                        Player player5 = this.player;
                        player5.packetSender.sendGameMessage("You can't light a fire here.");
                        return;
                    }
                    if (!SkillActionHelper.checkSkillRequirement(this.player, 11, ((FiremakingLog)value10).getRequiredLevel(), "light these logs")) {
                        return;
                    }
                    boolean useGroundItem = enabled2;
                    firePlane = player;
                    fireY = value6;
                    value9 = groundItem2;
                    firemakingLog = (FiremakingLog)value10;
                    value8 = this.player;
                    value7 = this;
                    value6 = firemakingLog.getLogItemId();
                    if (useGroundItem) break startFiremakingControlExit3;
                    if (!((Player)value8).getInventoryManager().removeItem(new ItemStack(value6))) {
                        if (GameplayTrace.enabled()) {
                            GameplayTrace.log("firemaking remove-log-failed player=" + GameplayTrace.describe((Player)value8) + " logItemId=" + value6 + " x=" + value9 + " y=" + fireY + " plane=" + firePlane);
                        }
                        break startFiremakingControlExit1;
                    }
                    groundItem = new GroundItem(new ItemStack(value6), (Entity)value8);
                    GroundItemManager.getInstance().spawn(groundItem);
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("firemaking ground-log spawned player=" + GameplayTrace.describe((Player)value8) + " logItemId=" + value6 + " x=" + value9 + " y=" + fireY + " plane=" + firePlane);
                    }
                    break startFiremakingControlExit2;
                }
                GroundItemManager.getInstance();
                groundItem = GroundItemManager.findVisibleItem((Player)value8, value6, new Position(value9, fireY, ((Entity)value8).getPosition().getPlane()));
                if (groundItem == null) {
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("firemaking visible-ground-log-missing player=" + GameplayTrace.describe((Player)value8) + " logItemId=" + value6 + " x=" + value9 + " y=" + fireY + " plane=" + ((Entity)value8).getPosition().getPlane());
                    }
                    break startFiremakingControlExit1;
                }
            }
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("firemaking start player=" + GameplayTrace.describe((Player)value8) + " logItemId=" + value6 + " fireObjectId=" + firemakingLog.getFireObjectId() + " source=" + (enabled2 ? "ground" : "inventory") + " x=" + value9 + " y=" + fireY + " plane=" + firePlane);
            }
            if (((Player)value8).getQuestState(0) != 1) {
                ((Player)value8).getDialogueManager().showTutorialInstructionOverlay("Please wait.", "", "Your character is now attempting to light the fire.", "This should only take a few seconds.", "", true);
            }
            Object value11 = value8;
            ((Player)value11).packetSender.sendGameMessage("You attempt to light the logs.");
            ((Entity)value8).getUpdateState().setAnimation(733);
            value11 = value8;
            ((Player)value11).packetSender.sendSoundEffect(375, 1, 0);
            int value12 = ((Entity)value8).nextActionSequence();
            ((Entity)value8).setActiveCycleEvent(new FiremakingTask((FiremakingHandler)value7, (Player)value8, value12, groundItem, value6, firemakingLog, value9, fireY, firePlane));
            CycleEventHandler.getInstance().schedule((Entity)value8, ((Entity)value8).getActiveCycleEvent(), 4);
        }
    }

    public static final boolean isFireObjectId(int objectId) {
        int[] integerValues = fireObjectIds;
        int index = 0;
        while (index < 4) {
            int value = integerValues[index];
            if (value == objectId) {
                return true;
            }
            ++index;
        }
        return false;
    }
}
