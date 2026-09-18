package com.rs2.model.bankpin;

import com.rs2.model.World;
import com.rs2.model.bankpin.BankPinEntryMode;
import com.rs2.model.bankpin.BankPinProtectedAction;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.gameplay.SmokeDungeonDamageTask;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.BankManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.guide.SkillGuideCategory;
import com.rs2.model.skill.guide.SkillGuideEntry;
import com.rs2.model.skill.guide.SkillGuideManager;
import com.rs2.util.GameUtil;
import java.util.ArrayList;
import java.util.Random;

public class BankPinManager {
    private Player player;
    private int[] currentPin = new int[]{-1, -1, -1, -1};
    private int[] pendingPin = new int[]{-1, -1, -1, -1};
    private int currentDigitIndex = -1;
    private int pinAppendYear = -1;
    private int pinAppendDate = -1;
    private boolean verified = false;
    private boolean changingPin = false;
    private boolean deletingPin = false;
    private BankPinEntryMode entryMode = BankPinEntryMode.a;
    private BankPinProtectedAction protectedAction = BankPinProtectedAction.BANK;
    private int[] digitButtonIds = new int[]{14883, 14884, 14885, 14886, 14887, 14888, 14889, 14890, 14891, 14892};

    public BankPinManager(Player player) {
        this.player = player;
    }

    public final boolean handleButtonClick(int buttonId) {
        switch (buttonId) {
            case 14922: {
                this.player.packetSender.closeInterfaces();
                return true;
            }
            case 14921: {
                DialogueManager.continueDialogue(this.player, 494, 19, 0);
                return true;
            }
        }

        if (this.entryMode == BankPinEntryMode.a) {
            for (int digitButton = 0; digitButton < 10; ++digitButton) {
                if (buttonId != digitButton + 14873) {
                    continue;
                }
                this.player.packetSender.sendSoundEffect(1827, 1, 0);
                this.player.setBankPinEntryDigit(this.decodeDigitButton(buttonId), this.currentDigitIndex);
                if (this.currentDigitIndex + 1 < 4) {
                    this.setCurrentDigitIndex(this.currentDigitIndex + 1);
                    return true;
                }

                boolean correctPin = true;
                for (int digitIndex = 0; digitIndex < 4; ++digitIndex) {
                    if (this.currentPin[digitIndex] < 0) {
                        break;
                    }
                    if (this.currentPin[digitIndex] != this.player.getBankPinEntryDigits()[digitIndex]) {
                        correctPin = false;
                        break;
                    }
                }

                if (!correctPin) {
                    this.player.packetSender.sendGameMessage("You've entered an incorrect pin, please try again.");
                    this.resetPinEntryInterface();
                    this.setCurrentDigitIndex(0);
                    this.player.resetBankPinEntryDigits();
                    this.player.packetSender.sendSoundEffect(1828, 1, 0);
                    return true;
                }

                this.player.packetSender.sendGameMessage("You have successfully verified your bank pin.");
                this.verified = true;
                if (this.protectedAction == BankPinProtectedAction.BANK) {
                    BankManager.openBank(this.player);
                }
                this.player.packetSender.sendSoundEffect(1257, 1, 0);
                return true;
            }
        } else if (this.entryMode == BankPinEntryMode.b) {
            for (int digitButton = 0; digitButton < 10; ++digitButton) {
                if (buttonId != digitButton + 14873) {
                    continue;
                }
                this.pendingPin[this.currentDigitIndex] = this.decodeDigitButton(buttonId);
                this.player.packetSender.sendSoundEffect(1827, 1, 0);
                if (this.currentDigitIndex + 1 < 4) {
                    this.setCurrentDigitIndex(this.currentDigitIndex + 1);
                } else {
                    DialogueManager.continueDialogue(this.player, 494, 11, 0);
                }
                return true;
            }
        }
        return false;
    }

    private int decodeDigitButton(int buttonId) {
        int index = 0;
        while (index < this.digitButtonIds.length) {
            int value = this.digitButtonIds[index];
            if (buttonId + 10 == value) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    private static void shuffleDigitButtons(int[] buttonId) {
        Random random = new Random();
        int value = buttonId.length - 1;
        while (value > 0) {
            int value2 = random.nextInt(value + 1);
            int value3 = buttonId[value2];
            buttonId[value2] = buttonId[value];
            buttonId[value] = value3;
            --value;
        }
    }

    public final void setEntryMode(BankPinEntryMode bankPinEntryMode) {
        this.entryMode = bankPinEntryMode;
        this.resetPinEntryInterface();
        this.setCurrentDigitIndex(0);
    }

    private void setCurrentDigitIndex(int index) {
        Player player;
        this.currentDigitIndex = index;
        int index2 = 0;
        while (index2 < index) {
            player = this.player;
            player.packetSender.sendInterfaceText("*", index2 + 14913);
            player = this.player;
            player.packetSender.sendInterfaceText("Click the " + GameUtil.getOrdinalWord(index2 + 2) + " digit...", 15313);
            ++index2;
        }
        BankPinManager.shuffleDigitButtons(this.digitButtonIds);
        index2 = 0;
        while (index2 < this.digitButtonIds.length) {
            player = this.player;
            player.packetSender.sendInterfacePosition(this.digitButtonIds[index2], GameUtil.randomInclusive(47), -GameUtil.randomInclusive(42));
            player = this.player;
            player.packetSender.sendInterfaceText(String.valueOf(index2), this.digitButtonIds[index2]);
            ++index2;
        }
        player = this.player;
        player.packetSender.showInterface(7424);
    }

    public final void processPendingPinChanges() {
        int value;
        if (this.changingPin) {
            value = this.getPendingPinDaysElapsed();
            if (value >= 7) {
                this.applyPendingPinChange();
            } else if (!this.hasPin()) {
                this.applyPendingPinChange();
            }
        }
        if (this.deletingPin && (value = this.getPendingPinDaysElapsed()) >= 7) {
            Object value2 = this;
            int index = 0;
            while (index < ((BankPinManager)value2).currentPin.length) {
                ((BankPinManager)value2).currentPin[index] = -1;
                ((BankPinManager)value2).pendingPin[index] = -1;
                ++index;
            }
            ((BankPinManager)value2).deletingPin = false;
            ((BankPinManager)value2).pinAppendYear = -1;
            ((BankPinManager)value2).pinAppendDate = -1;
            value2 = ((BankPinManager)value2).player;
            ((Player)value2).packetSender.sendGameMessage("Your bank pin has been successfully deleted!");
        }
    }

    private void applyPendingPinChange() {
        Player player = this.player;
        player.packetSender.sendGameMessage("Your bank pin has been successfully " + (this.hasPin() ? "changed" : "set") + "!");
        int index = 0;
        while (index < this.currentPin.length) {
            this.currentPin[index] = this.pendingPin[index];
            this.pendingPin[index] = -1;
            ++index;
        }
        this.changingPin = false;
        this.pinAppendYear = -1;
        this.pinAppendDate = -1;
    }

    private int getPendingPinDaysElapsed() {
        if (GameUtil.getCurrentYear() == this.pinAppendYear) {
            return GameUtil.getDayOfYear() - this.pinAppendDate;
        }
        return 365 - this.pinAppendDate + GameUtil.getDayOfYear();
    }

    private void resetPinEntryInterface() {
        this.currentDigitIndex = -1;
        Player player = this.player;
        BankPinManager bankPinManager = this;
        int pendingPinDaysElapsed = bankPinManager.getPendingPinDaysElapsed();
        player.packetSender.sendInterfaceText(bankPinManager.changingPin ? "You bank pin will change in " + (7 - pendingPinDaysElapsed) + " days." : (bankPinManager.deletingPin ? "You bank pin will be deleted in " + (7 - pendingPinDaysElapsed) + " days." : (bankPinManager.hasPin() ? "Your bank pin is set and up to date." : "You do not have a bank pin.")), 14923);
        Player player2 = this.player;
        player2.packetSender.sendInterfaceText("Click the " + GameUtil.getOrdinalWord(1) + " digit...", 15313);
        int index = 0;
        while (index < 4) {
            player2 = this.player;
            player2.packetSender.sendInterfaceText("?", index + 14913);
            ++index;
        }
        index = 0;
        while (index < 10) {
            player2 = this.player;
            player2.packetSender.sendInterfaceText(String.valueOf(index), index + 14883);
            ++index;
        }
    }

    public final void requestPinChange() {
        this.changingPin = true;
        this.pinAppendYear = GameUtil.getCurrentYear();
        this.pinAppendDate = GameUtil.getDayOfYear();
    }

    public final void requestPinDeletion() {
        this.clearPendingPinChange();
        this.deletingPin = true;
        this.pinAppendYear = GameUtil.getCurrentYear();
        this.pinAppendDate = GameUtil.getDayOfYear();
    }

    public final void clearPendingPinChange() {
        int index = 0;
        while (index < this.pendingPin.length) {
            this.pendingPin[index] = -1;
            ++index;
        }
        this.changingPin = false;
        this.deletingPin = false;
        this.pinAppendYear = -1;
        this.pinAppendDate = -1;
    }

    public final boolean hasPin() {
        return this.currentPin[0] != -1;
    }

    public final boolean hasPendingPinChange() {
        return this.changingPin || this.deletingPin;
    }

    public final boolean isVerified() {
        return this.verified;
    }

    public final int[] getPendingPin() {
        return this.pendingPin;
    }

    public final int[] getCurrentPin() {
        return this.currentPin;
    }

    public final void setChangingPin(boolean changingPin) {
        this.changingPin = changingPin;
    }

    public final boolean isChangingPin() {
        return this.changingPin;
    }

    public final void setDeletingPin(boolean deletingPin) {
        this.deletingPin = deletingPin;
    }

    public final boolean isDeletingPin() {
        return this.deletingPin;
    }

    public final void setPinAppendYear(int pinAppendYear) {
        this.pinAppendYear = pinAppendYear;
    }

    public final int getPinAppendYear() {
        return this.pinAppendYear;
    }

    public final void setPinAppendDate(int pinAppendDate) {
        this.pinAppendDate = pinAppendDate;
    }

    public final int getPinAppendDate() {
        return this.pinAppendDate;
    }

    public static boolean showSkillUnlockMessage(Player player, int skillId) {
        if (skillId != 13 && skillId != 8 && skillId != 16 && skillId != 0 && skillId != 7 && skillId != 12 && skillId != 1 && skillId != 19 && skillId != 11 && skillId != 10 && skillId != 9 && skillId != 15 && skillId != 14 && skillId != 4 && skillId != 20 && skillId != 18 && skillId != 2 && skillId != 17) {
            return false;
        }
        Object categoriesForSkillId = SkillGuideManager.getCategoriesForSkillId(skillId);
        if (categoriesForSkillId == null) {
            return false;
        }
        int skillManager = player.getSkillManager().getBaseLevel(skillId);
        int initialValue = -1;
        boolean enabled = false;
        int index = 0;
        while (index < ((ArrayList)categoriesForSkillId).size()) {
            Object value;
            Object value2;
            SkillGuideCategory skillGuideCategory = (SkillGuideCategory)((ArrayList)categoriesForSkillId).get(index);
            Object value3 = null;
            int index2 = 0;
            while (index2 < skillGuideCategory.entries.size()) {
                value2 = (SkillGuideEntry)skillGuideCategory.entries.get(index2);
                value = skillGuideCategory.getLevelText();
                if (((String)value).equals("-1")) {
                    value = ((SkillGuideEntry)value2).getLevelText();
                    if (((String)value).equals("-1")) {
                        value = "";
                    }
                    if (!((String)value).equals("")) {
                        initialValue = Integer.parseInt((String)value);
                    }
                    if (initialValue == skillManager) {
                        value3 = value2;
                        break;
                    }
                }
                ++index2;
            }
            if (value3 != null) {
                value = value3;
                index2 = ((SkillGuideEntry)value).itemId;
                if (index2 >= 0) {
                    value = skillGuideCategory;
                    if (!((SkillGuideCategory)value).skipItemDefinitionLookup) {
                        value = skillGuideCategory;
                        if (ItemDefinition.isDefined(index2)) {
                            value2 = new ItemStack(index2);
                            value = ((ItemStack)value2).getDefinition();
                            if (((ItemDefinition)value).isMembersOnly()) {
                                enabled = true;
                            }
                            categoriesForSkillId = ((ItemStack)value2).getDefinition().getName();
                            String text = (String)categoriesForSkillId;
                            String text2 = ".";
                            String text3 = "@bla@You ";
                            if (enabled) {
                                text3 = "@bla@Members ";
                            }
                            String text4 = "can now ";
                            String text5 = "";
                            if (skillId == 13) {
                                text5 = ((String)categoriesForSkillId).endsWith("ore") ? String.valueOf(text5) + "smelt" : String.valueOf(text5) + "make";
                                text = String.valueOf(text) + "s";
                            } else if (skillId == 8) {
                                if (!((String)categoriesForSkillId).endsWith("axe")) {
                                    text5 = String.valueOf(text5) + "cut down";
                                    value = value3;
                                    text = ((SkillGuideEntry)value).label;
                                } else {
                                    text5 = String.valueOf(text5) + "chop with a";
                                }
                            } else if (skillId == 16) {
                                value = value3;
                                text = ((SkillGuideEntry)value).label;
                                text5 = text.toLowerCase().endsWith("course") ? String.valueOf(text5) + "enter the" : String.valueOf(text5) + "use";
                            } else if (skillId == 0) {
                                text5 = String.valueOf(text5) + "wield";
                                value = value3;
                                text = ((SkillGuideEntry)value).label;
                                text = String.valueOf(text) + " weaponry";
                                text2 = "!";
                            } else if (skillId == 7) {
                                text5 = String.valueOf(text5) + "cook";
                            } else if (skillId == 12) {
                                value = skillGuideCategory;
                                if (((SkillGuideCategory)value).name.toLowerCase().equals("milestones")) {
                                    value = value3;
                                    text = ((SkillGuideEntry)value).label;
                                    text5 = String.valueOf(text5) + "enter the";
                                } else {
                                    text5 = String.valueOf(text5) + "craft";
                                }
                            } else if (skillId == 1) {
                                text5 = String.valueOf(text5) + "wear";
                                value = value3;
                                text = ((SkillGuideEntry)value).label;
                                text = String.valueOf(text) + " armour";
                                text2 = "!";
                            } else if (skillId == 19) {
                                text5 = String.valueOf(text5) + "grow";
                                text = String.valueOf(text) + "s";
                            } else if (skillId == 11) {
                                text5 = String.valueOf(text5) + "light";
                                value = value3;
                                text = ((SkillGuideEntry)value).label;
                            } else if (skillId == 10) {
                                text5 = String.valueOf(text5) + "try to catch";
                                if (!(text = text.replaceAll("Raw ", "")).endsWith("s")) {
                                    text = String.valueOf(text) + "s";
                                }
                            } else if (skillId == 9) {
                                text5 = String.valueOf(text5) + "craft";
                                value = value3;
                                text = ((SkillGuideEntry)value).label;
                            } else if (skillId == 15) {
                                value = skillGuideCategory;
                                text5 = ((SkillGuideCategory)value).name.toLowerCase().equals("herbs") ? String.valueOf(text5) + "identify" : String.valueOf(text5) + "make";
                                value = value3;
                                text = ((SkillGuideEntry)value).label;
                            } else if (skillId == 14) {
                                value = skillGuideCategory;
                                if (((SkillGuideCategory)value).name.toLowerCase().equals("milestones")) {
                                    value = value3;
                                    text = ((SkillGuideEntry)value).label;
                                    text5 = String.valueOf(text5) + "enter the";
                                } else if (!((String)categoriesForSkillId).endsWith("axe")) {
                                    text5 = String.valueOf(text5) + "mine";
                                    value = value3;
                                    text = ((SkillGuideEntry)value).label;
                                    text = text.replaceAll(" ore", "");
                                } else {
                                    text5 = String.valueOf(text5) + "mine with";
                                    text = String.valueOf(text) + "s";
                                }
                            } else if (skillId == 4) {
                                value = skillGuideCategory;
                                text5 = ((SkillGuideCategory)value).name.toLowerCase().equals("armour") ? String.valueOf(text5) + "wear" : String.valueOf(text5) + "wield";
                                value = value3;
                                text = ((SkillGuideEntry)value).label;
                            } else if (skillId == 20) {
                                value = value3;
                                text = ((SkillGuideEntry)value).label;
                                value = skillGuideCategory;
                                if (((SkillGuideCategory)value).name.toLowerCase().equals("runes")) {
                                    text5 = String.valueOf(text5) + "craft";
                                } else {
                                    value = skillGuideCategory;
                                    if (((SkillGuideCategory)value).name.toLowerCase().equals("multiples")) {
                                        text5 = String.valueOf(text5) + "receive";
                                        text4 = "will now ";
                                    } else {
                                        value = skillGuideCategory;
                                        if (((SkillGuideCategory)value).name.toLowerCase().equals("pouches")) {
                                            text5 = String.valueOf(text5) + "use ";
                                        }
                                    }
                                }
                            } else if (skillId == 18) {
                                value = value3;
                                text = ((SkillGuideEntry)value).label;
                                value = skillGuideCategory;
                                text5 = ((SkillGuideCategory)value).name.toLowerCase().equals("monsters") ? String.valueOf(text5) + "kill" : String.valueOf(text5) + "use";
                            } else if (skillId == 2) {
                                text5 = String.valueOf(text5) + "wield";
                                text = String.valueOf(text) + "s";
                            } else if (skillId == 17) {
                                value = value3;
                                text = ((SkillGuideEntry)value).label;
                                value = skillGuideCategory;
                                if (((SkillGuideCategory)value).name.toLowerCase().equals("pickpocket")) {
                                    text5 = String.valueOf(text5) + "pickpocket";
                                    text = String.valueOf(text) + "s";
                                } else {
                                    text5 = String.valueOf(text5) + "steal from";
                                    text = String.valueOf(text) + "s";
                                }
                            }
                            text5 = String.valueOf(text5) + " @dbl@" + GameUtil.capitalizeWords(text) + "@bla@" + text2;
                            player.getDialogueManager().showItemMessage(String.valueOf(text3) + text4 + text5, (ItemStack)value2);
                            return true;
                        }
                    }
                }
            }
            ++index;
        }
        return false;
    }

    public static boolean updateSmokeDungeonDamage(Player player) {
        if (player.activeRecurringEffectId == 1235) {
            return true;
        }
        if (player.isInSmokeDungeon()) {
            SmokeDungeonDamageTask smokeDungeonDamageTask = new SmokeDungeonDamageTask(20, player);
            World.getTaskScheduler().schedule(smokeDungeonDamageTask);
            player.activeRecurringEffectId = 1235;
            return true;
        }
        return false;
    }
}
