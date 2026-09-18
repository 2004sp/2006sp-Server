package com.rs2.model.skill.prayer;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.skill.prayer.RapidRestoreTask;
import com.rs2.model.skill.prayer.RetributionPrayerTask;
import com.rs2.model.task.TickTask;

public final class PrayerManager {
    private Player player;
    private static final Object[][] PRAYER_DEFINITIONS = new Object[][]{{0, 83, "Thick Skin", 1, 3, 446, false}, {1, 84, "Burst of Strength", 4, 3, 449, false}, {2, 85, "Clarity of Thought", 7, 3, 436, false}, {3, 86, "Rock Skin", 10, 6, 441, false}, {4, 87, "Superhuman Strength", 13, 6, 434, false}, {5, 88, "Improved Reflexes", 16, 6, 448, false}, {6, 89, "Rapid Restore", 19, 1, 451, false}, {7, 90, "Rapid Heal", 22, 2, 443, false}, {8, 91, "Protect Item", 25, 2, 337, false}, {9, 92, "Steel Skin", 28, 12, 439, false}, {10, 93, "Ultimate Strength", 31, 12, 450, false}, {11, 94, "Incredible Reflexes", 34, 12, 440, false}, {12, 95, "Protect from Magic", 37, 12, 438, false}, {13, 96, "Protect from Range", 40, 12, 444, false}, {14, 97, "Protect from Melee", 43, 12, 433, false}, {15, 98, "Retribution", 46, 3, 1703, true}, {16, 99, "Redemption", 49, 6, 1705, true}, {17, 100, "Smite", 52, 18, 1704, true}};
    private static final int[][] NPC_PRAYER_EXPERIENCE_REWARDS = new int[][]{{3867, 130}, {3868, 182}, {3869, 286}, {3870, 454}, {3871, 480}, {3872, 494}, {3873, 520}, {3874, 584}, {3875, 650}, {3876, 716}, {3877, 754}, {3878, 780}, {3879, 884}, {3880, 936}, {3881, 1040}, {3882, 1104}, {3883, 1170}, {3884, 1300}, {3885, 1560}};
    private TickTask rapidRestoreTask;

    public PrayerManager(Player player) {
        this.player = player;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void awardNpcPrayerExperience(Npc npc) {
        int initialValue = -1;
        int index = 0;
        while (index < 19) {
            if (npc.getNpcId() == NPC_PRAYER_EXPERIENCE_REWARDS[index][0]) {
                initialValue = index;
                break;
            }
            ++index;
        }
        if (initialValue != -1) {
            this.player.getSkillManager().addExperience(5, NPC_PRAYER_EXPERIENCE_REWARDS[initialValue][1]);
        }
    }

    public final void drainPrayerPoints() {
        if (this.player.prayerDrainRate > 0) {
            this.player.prayerDrainAccumulator += this.player.prayerDrainRate;
            int index = 0;
            while (this.player.prayerDrainAccumulator > this.player.getPrayerDrainThreshold()) {
                this.player.prayerDrainAccumulator -= this.player.getPrayerDrainThreshold();
                ++index;
            }
            int value = index;
            PrayerManager prayerManager = this;
            int[] skillManager = prayerManager.player.getSkillManager().getCurrentLevels();
            skillManager[5] = skillManager[5] - value;
            if (prayerManager.player.getSkillManager().getCurrentLevels()[5] <= 0) {
                prayerManager.player.getSkillManager().getCurrentLevels()[5] = 0;
                prayerManager.player.getSkillManager().refreshSkill(5);
                prayerManager.deactivateAll();
                Player player = prayerManager.player;
                player.packetSender.sendGameMessage("You have ran out of prayer points; you must recharge at an altar.");
                player = prayerManager.player;
                player.packetSender.sendSoundEffect(437, 1, 0);
                return;
            }
            prayerManager.player.getSkillManager().refreshSkill(5);
        }
    }

    private static int getPrayerConfigId(Integer prayerId, int prayerId2) {
        prayerId2 = 0;
        Object[][] objectValues = PRAYER_DEFINITIONS;
        int index = 0;
        while (index < 18) {
            Object[] objectValues2 = objectValues[index];
            if (objectValues2[0] == prayerId) {
                prayerId2 = (Integer)objectValues2[1];
            }
            ++index;
        }
        return prayerId2;
    }

    public final void togglePrayer(Integer prayerId) {
        if (this.player.isDead()) {
            return;
        }
        int index = 0;
        String text = null;
        int index2 = 0;
        int initialValue = -1;
        boolean enabled = false;
        Object[][] objectValues = PRAYER_DEFINITIONS;
        int index3 = 0;
        while (index3 < 18) {
            Object[] objectValues2 = objectValues[index3];
            if (objectValues2[0] == prayerId) {
                index = (Integer)objectValues2[1];
                text = (String)objectValues2[2];
                index2 = (Integer)objectValues2[3];
                initialValue = (Integer)objectValues2[5];
                enabled = (Boolean)objectValues2[6];
            }
            ++index3;
        }
        if (enabled) {
            if (this.player.isMember()) {
                if (ServerSettings.freeToPlayWorld) {
                    this.player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                    Player player = this.player;
                    player.packetSender.sendConfig(index, 0);
                    this.player.getDialogueManager().finishDialogue();
                    return;
                }
            } else {
                this.player.packetSender.sendGameMessage("You need a members account to access members content.");
                Player player = this.player;
                player.packetSender.sendConfig(index, 0);
                this.player.getDialogueManager().finishDialogue();
                return;
            }
        }
        if (this.player.getSkillManager().getBaseLevel(5) < index2) {
            Player player = this.player;
            player.packetSender.sendConfig(index, 0);
            this.player.getDialogueManager().finishDialogue();
            this.player.getDialogueManager().showOneLineStatement("You need a prayer level of at least " + index2 + " to use " + text + ".");
            player = this.player;
            player.packetSender.sendGameMessage("You need a prayer level of at least " + index2 + " to use " + text + ".");
            player = this.player;
            player.packetSender.sendSoundEffect(447, 1, 0);
            return;
        }
        if (DuelRule.NO_PRAYER.isEnabledFor(this.player)) {
            Player player = this.player;
            player.packetSender.sendGameMessage("Usage of prayers have been disabled during this fight!");
            player = this.player;
            player.packetSender.sendConfig(index, 0);
            return;
        }
        if (this.player.getSkillManager().getCurrentLevels()[5] <= 0) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You have run out of prayer points; recharge your prayer points at an altar");
            player = this.player;
            player.packetSender.sendSoundEffect(437, 1, 0);
            this.deactivateAll();
            return;
        }
        int initialValue2 = -1;
        index3 = 0;
        if ((prayerId == 12 || prayerId == 13 || prayerId == 14) && this.player.getProtectionPrayerDisabledUntil() > System.currentTimeMillis()) {
            Player player = this.player;
            player.packetSender.sendGameMessage("Your protection prayers are temporarily disabled.");
            player = this.player;
            player.packetSender.sendConfig(PrayerManager.getPrayerConfigId(prayerId, 1), 0);
            return;
        }
        switch (prayerId) {
            case 12: {
                initialValue2 = 2;
                index3 = 1;
                break;
            }
            case 13: {
                initialValue2 = 1;
                index3 = 1;
                break;
            }
            case 14: {
                initialValue2 = 0;
                index3 = 1;
                break;
            }
            case 15: {
                initialValue2 = 3;
                index3 = 1;
                break;
            }
            case 16: {
                initialValue2 = 5;
                index3 = 1;
                break;
            }
            case 17: {
                initialValue2 = 4;
                index3 = 1;
            }
        }
        if (index3 != 0) {
            this.player.setPrayerHeadIcon(!this.player.getActivePrayers()[prayerId] ? initialValue2 : -1);
        }
        if (!this.player.getActivePrayers()[prayerId] && initialValue != -1) {
            Player player = this.player;
            player.packetSender.sendSoundEffect(initialValue, 1, 0);
        }
        if (this.player.getActivePrayers()[prayerId]) {
            Player player = this.player;
            player.packetSender.sendSoundEffect(435, 1, 0);
        }
        this.player.getActivePrayers()[prayerId.intValue()] = !this.player.getActivePrayers()[prayerId];
        Player player = this.player;
        player.packetSender.sendConfig(index, this.player.getActivePrayers()[prayerId] ? 1 : 0);
        this.deactivateConflictingPrayers(prayerId);
        this.updatePrayerDrain();
        index = this.player.getActivePrayers()[prayerId] ? 0 : 1;
        Player player2 = this.player;
        this.player.setAppearanceUpdateRequired(true);
        if (prayerId == 6) {
            this.updateRapidRestoreTask();
        }
        if (prayerId == 7) {
            this.player.getSkillManager().setRapidHealRestoreDelay(this.player.getActivePrayers()[prayerId]);
        }
    }

    private void updatePrayerDrain() {
        this.player.prayerDrainRate = 0;
        int index = 0;
        while (index < this.player.getActivePrayers().length) {
            if (this.player.getActivePrayers()[index]) {
                Object[] objectValues = PRAYER_DEFINITIONS[index];
                int integer = (Integer)objectValues[4];
                this.player.prayerDrainRate += integer;
            }
            ++index;
        }
    }

    private void deactivateConflictingPrayers(int prayerId) {
        int[] integerValues = new int[]{};
        switch (prayerId) {
            case 0: {
                integerValues = new int[]{3, 9};
                break;
            }
            case 3: {
                int[] integerValues2 = new int[2];
                integerValues2[1] = 9;
                integerValues = integerValues2;
                break;
            }
            case 9: {
                int[] integerValues3 = new int[2];
                integerValues3[1] = 3;
                integerValues = integerValues3;
                break;
            }
            case 2: {
                integerValues = new int[]{5, 11};
                break;
            }
            case 5: {
                integerValues = new int[]{2, 11};
                break;
            }
            case 11: {
                integerValues = new int[]{5, 2};
                break;
            }
            case 1: {
                integerValues = new int[]{4, 10};
                break;
            }
            case 4: {
                integerValues = new int[]{1, 10};
                break;
            }
            case 10: {
                integerValues = new int[]{4, 1};
                break;
            }
            case 12: {
                integerValues = new int[]{16, 17, 15, 13, 14};
                break;
            }
            case 13: {
                integerValues = new int[]{16, 17, 15, 12, 14};
                break;
            }
            case 14: {
                integerValues = new int[]{16, 17, 15, 13, 12};
                break;
            }
            case 15: {
                integerValues = new int[]{16, 17, 14, 13, 12};
                break;
            }
            case 16: {
                integerValues = new int[]{15, 17, 14, 13, 12};
                break;
            }
            case 17: {
                integerValues = new int[]{16, 15, 14, 13, 12};
            }
        }
        int[] integerValues4 = integerValues;
        int length = integerValues.length;
        int index = 0;
        while (index < length) {
            int value = integerValues4[index];
            if (value != prayerId) {
                this.player.getActivePrayers()[value] = false;
                Player player = this.player;
                player.packetSender.sendConfig(PrayerManager.getPrayerConfigId(value, 1), 0);
            }
            ++index;
        }
    }

    public final void deactivatePrayer(int prayerId) {
        if (this.player.getActivePrayers()[prayerId]) {
            this.player.getActivePrayers()[prayerId] = false;
            Player player = this.player;
            player.packetSender.sendConfig(PrayerManager.getPrayerConfigId(prayerId, 1), 0);
            if (prayerId == 12 || prayerId == 13 || prayerId == 14 || prayerId == 15 || prayerId == 16 || prayerId == 17) {
                this.player.setPrayerHeadIcon(-1);
                this.player.setAppearanceUpdateRequired(true);
            }
        }
        this.updatePrayerDrain();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void deactivateAll() {
        if (this.player.getActivePrayers()[7]) {
            this.player.getSkillManager().setRapidHealRestoreDelay(false);
        }
        int index = 0;
        while (index < 18) {
            this.player.getActivePrayers()[index] = false;
            Player player = this.player;
            player.packetSender.sendConfig(PrayerManager.getPrayerConfigId(index, 1), 0);
            ++index;
        }
        this.updateRapidRestoreTask();
        this.player.setPrayerHeadIcon(-1);
        this.player.setAppearanceUpdateRequired(true);
        this.updatePrayerDrain();
    }

    private void updateRapidRestoreTask() {
        Object value = this.rapidRestoreTask;
        if (this.player.getActivePrayers()[6]) {
            value = this;
            if (((PrayerManager)value).rapidRestoreTask == null || !((PrayerManager)value).rapidRestoreTask.isActive()) {
                ((PrayerManager)value).rapidRestoreTask = new RapidRestoreTask((PrayerManager)value, 100);
                World.getTaskScheduler().schedule(((PrayerManager)value).rapidRestoreTask);
            }
            return;
        }
        if (value != null) {
            ((TickTask)value).stop();
            ((TickTask)value).setIntervalTicks(100);
            ((TickTask)value).setRemainingTicks(100);
        }
    }

    public final boolean isRapidRestoreActive() {
        return this.player.getSkillManager().getCurrentLevels()[5] > 0 && this.player.getActivePrayers()[6];
    }

    public final boolean handleButtonClick(int buttonId) {
        switch (buttonId) {
            case 5609: {
                this.togglePrayer(0);
                return true;
            }
            case 5610: {
                this.togglePrayer(1);
                return true;
            }
            case 5611: {
                this.togglePrayer(2);
                return true;
            }
            case 5612: {
                this.togglePrayer(3);
                return true;
            }
            case 5613: {
                this.togglePrayer(4);
                return true;
            }
            case 5614: {
                this.togglePrayer(5);
                return true;
            }
            case 5615: {
                this.togglePrayer(6);
                return true;
            }
            case 5616: {
                this.togglePrayer(7);
                return true;
            }
            case 5617: {
                if (this.player.gameMode == 2) {
                    Player player = this.player;
                    player.packetSender.sendGameMessage("In ultimate ironman mode you cannot use protect item.");
                } else {
                    this.togglePrayer(8);
                }
                return true;
            }
            case 5618: {
                this.togglePrayer(9);
                return true;
            }
            case 5619: {
                this.togglePrayer(10);
                return true;
            }
            case 5620: {
                this.togglePrayer(11);
                return true;
            }
            case 5621: {
                this.togglePrayer(12);
                return true;
            }
            case 5622: {
                this.togglePrayer(13);
                return true;
            }
            case 5623: {
                this.togglePrayer(14);
                return true;
            }
            case 683: {
                this.togglePrayer(15);
                return true;
            }
            case 684: {
                this.togglePrayer(16);
                Player player = this.player;
                if (player.getSkillManager().getCurrentLevels()[3] <= (int)((double)player.getSkillManager().getBaseLevel(3) * 0.1)) {
                    int[] skillManager = player.getSkillManager().getCurrentLevels();
                    skillManager[3] = skillManager[3] + (int)((double)player.getSkillManager().getBaseLevel(5) * 0.25);
                    player.getUpdateState().setGraphic(436, 0);
                    player.getSkillManager().refreshSkill(5);
                    player.getSkillManager().setCurrentLevel(5, 0);
                    player.getSkillManager().refreshSkill(3);
                }
                return true;
            }
            case 685: {
                this.togglePrayer(17);
                return true;
            }
        }
        return false;
    }

    public static void rechargePrayerAtAltar(Player player) {
        if (player.getSkillManager().getCurrentLevels()[5] < player.getSkillManager().getBaseLevel(5)) {
            player.getUpdateState().setAnimation(645);
            player.getSkillManager().setCurrentLevel(5, player.getSkillManager().getBaseLevel(5));
            player.getSkillManager().refreshSkill(5);
            Player player2 = player;
            player2.packetSender.sendGameMessage("You recharge your prayer at the altar.");
            player2 = player;
            player2.packetSender.sendSoundEffect(442, 1, 0);
        } else {
            Player player3 = player;
            player3.packetSender.sendGameMessage("You already have full prayer!");
        }
        if (player.botEnabled) {
            player.botTaskReturnToBankRequested = true;
            player.currentBotTask.startWalkToBank(player);
        }
    }

    public static void rechargePrayerWithBoost(Player player) {
        if (player.getSkillManager().getCurrentLevels()[5] < player.getSkillManager().getBaseLevel(5) + 2) {
            player.getUpdateState().setAnimation(645);
            player.getSkillManager().setCurrentLevel(5, player.getSkillManager().getBaseLevel(5) + 2);
            player.getSkillManager().refreshSkill(5);
            Player player2 = player;
            player2.packetSender.sendGameMessage("You recharge your prayer at the altar.");
            player2 = player;
            player2.packetSender.sendGameMessage("You recieve a temporary prayer boost.");
            player2 = player;
            player2.packetSender.sendSoundEffect(442, 1, 0);
        } else {
            Player player3 = player;
            player3.packetSender.sendGameMessage("You already have full prayer!");
        }
        if (player.botEnabled) {
            player.botTaskReturnToBankRequested = true;
            player.currentBotTask.startWalkToBank(player);
        }
    }

    public static void triggerRetribution(Entity entity2, Entity entity) {
        Player player = (Player)entity2;
        boolean enabled = ((Entity)entity2).isInMultiCombatArea();
        int skillManager = player.getSkillManager().getBaseLevel(5) / 4;
        HitDefinition hitDefinition = new HitDefinition(null, HitType.NORMAL, skillManager).enableRandomDamage().setAlwaysHits(true);
        World.getTaskScheduler().schedule(new RetributionPrayerTask(3, player, enabled, (Entity)entity2, entity, hitDefinition));
    }

    public static void triggerRedemption(Player player, Entity entity, int value2) {
        int skillManager = (int)Math.floor((double)player.getSkillManager().getBaseLevel(5) / 4.0);
        if ((value2 += skillManager) > entity.getMaxHitpoints()) {
            entity.getMaxHitpoints();
        }
        player.getUpdateState().setGraphic(436, 0);
        player.getSkillManager().setCurrentLevel(5, 0);
        player.getPrayerManager().deactivateAll();
    }

    public static void drainPrayerForSmite(Player player, int prayerId) {
        prayerId = player.getSkillManager().getCurrentLevels()[5] - (int)Math.floor(prayerId / 4);
        if (prayerId < 0) {
            prayerId = 0;
        }
        player.getSkillManager().setCurrentLevel(5, prayerId);
        player.getSkillManager().refreshSkill(5);
    }

    static Player getPlayer(PrayerManager prayerManager) {
        return prayerManager.player;
    }
}

