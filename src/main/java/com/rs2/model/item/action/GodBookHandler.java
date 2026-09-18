package com.rs2.model.item.action;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.action.GodBookRecitationEvent;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class GodBookHandler {
    public static int damagedSaradominBookId = 3839;
    public static int holyBookId = 3840;
    public static int damagedZamorakBookId = 3841;
    public static int unholyBookId = 3842;
    public static int damagedGuthixBookId = 3843;
    public static int bookOfBalanceId = 3844;
    private static int saradominPage1Id = 3827;
    private static int saradominPage2Id = 3828;
    private static int saradominPage3Id = 3829;
    private static int saradominPage4Id = 3830;
    private static int zamorakPage1Id = 3831;
    private static int zamorakPage2Id = 3832;
    private static int zamorakPage3Id = 3833;
    private static int zamorakPage4Id = 3834;
    private static int guthixPage1Id = 3835;
    private static int guthixPage2Id = 3836;
    private static int guthixPage3Id = 3837;
    private static int guthixPage4Id = 3838;
    private static int saradominPage1Bit = 1;
    private static int saradominPage2Bit = 2;
    private static int saradominPage3Bit = 3;
    private static int saradominPage4Bit = 4;
    private static int zamorakPage1Bit = 5;
    private static int zamorakPage2Bit = 6;
    private static int zamorakPage3Bit = 7;
    private static int zamorakPage4Bit = 8;
    private static int guthixPage1Bit = 9;
    private static int guthixPage2Bit = 10;
    private static int guthixPage3Bit = 11;
    private static int guthixPage4Bit = 12;
    private static int[] saradominPageBits = new int[]{saradominPage1Bit, saradominPage2Bit, saradominPage3Bit, saradominPage4Bit};
    private static int[] zamorakPageBits = new int[]{zamorakPage1Bit, zamorakPage2Bit, zamorakPage3Bit, zamorakPage4Bit};
    private static int[] guthixPageBits = new int[]{guthixPage1Bit, guthixPage2Bit, guthixPage3Bit, guthixPage4Bit};
    private static int saradominRecitationAnimationId = 1335;
    private static int zamorakRecitationAnimationId = 1336;
    private static int guthixRecitationAnimationId = 1337;
    private static int weddingCeremonyOptionId = 1;
    private static int lastRitesOptionId = 2;
    private static int blessingsOptionId = 3;
    private static int preachOptionId = 4;
    private static String[] saradominWeddingCeremonyLines = new String[]{"In the name of Saradomin,", "Protector of us all,", "I now join you in the eyes of Saradomin."};
    private static String[] saradominLastRitesLines = new String[]{"Thy cause was false, thy skills did lack;", "See you in Lumbridge when you get back."};
    private static String[] saradominBlessingLines = new String[]{"Go in peace in the name of Saradomin;", "May his glory shine upon you like the sun."};
    private static String[] saradominPreachLines = new String[]{"Walk proud, and show mercy.", "For you carry my name in your heart.", "This is Saradomin's wisdom."};
    private static String[] zamorakWeddingCeremonyLines = new String[]{"Two great warriors, joined by hand,", "to spread destruct, on across the land.", "In Zamorak's name, now two are one."};
    private static String[] zamorakLastRitesLines = new String[]{"The weak deserve to die,", "So that the strong may flourish.", "This is the creed of Zamorak."};
    private static String[] zamorakBlessingLines = new String[]{"May your bloodthirst be never sated.", "and may all your battles be glorious.", "Zamorak bring you strength."};
    private static String[] zamorakPreachLines = new String[]{"There is no opinion that cannot be proven true,", "by crushing those who choose to disagree with it.", "Zamorak give me strength!"};
    private static String[] guthixWeddingCeremonyLines = new String[]{"Light and dark, day and night,", "Balance arises from contrast.", "I unify thee in the name of Guthix."};
    private static String[] guthixLastRitesLines = new String[]{"Thy death was not in vain,", "for it brought some balance to the world.", "May Guthix bring you rest."};
    private static String[] guthixBlessingLines = new String[]{"May you walk the path, and never fall,", "For Guthix walks beside thee on thy journey.", "May Guthix bring you peace."};
    private static String[] guthixPreachLines = new String[]{"A journey of a single step,", "May take thee over a thousand miles.", "May Guthix bring you balance."};

    public static boolean showMissingPages(Player player, int value2) {
        if (value2 == damagedSaradominBookId || value2 == damagedZamorakBookId || value2 == damagedGuthixBookId) {
            ArrayList arrayList = GodBookHandler.getMissingPageNumbers(player, value2);
            String text = "";
            int index = 0;
            while (index < arrayList.size()) {
                if (index > 0) {
                    text = String.valueOf(text) + ", ";
                }
                text = String.valueOf(text) + arrayList.get(index);
                ++index;
            }
            player.packetSender.sendGameMessage("The book is missing the following pages: " + text);
            return true;
        }
        return false;
    }

    private static ArrayList getMissingPageNumbers(Player player, int value2) {
        ArrayList<Integer> arrayList;
        getMissingPageNumbersControlExit1: {
            getMissingPageNumbersControlExit2: {
                getMissingPageNumbersControlExit3: {
                    arrayList = new ArrayList<Integer>();
                    if (value2 != damagedSaradominBookId) break getMissingPageNumbersControlExit3;
                    value2 = 0;
                    while (value2 < saradominPageBits.length) {
                        if ((player.godBookPageFlags & GameUtil.bitFlag(saradominPageBits[value2])) == 0) {
                            arrayList.add(value2 + 1);
                        }
                        ++value2;
                    }
                    break getMissingPageNumbersControlExit1;
                }
                if (value2 != damagedZamorakBookId) break getMissingPageNumbersControlExit2;
                value2 = 0;
                while (value2 < zamorakPageBits.length) {
                    if ((player.godBookPageFlags & GameUtil.bitFlag(zamorakPageBits[value2])) == 0) {
                        arrayList.add(value2 + 1);
                    }
                    ++value2;
                }
                break getMissingPageNumbersControlExit1;
            }
            if (value2 != damagedGuthixBookId) break getMissingPageNumbersControlExit1;
            value2 = 0;
            while (value2 < guthixPageBits.length) {
                if ((player.godBookPageFlags & GameUtil.bitFlag(guthixPageBits[value2])) == 0) {
                    arrayList.add(value2 + 1);
                }
                ++value2;
            }
        }
        return arrayList;
    }

    public static boolean handlePageOnBook(Player player, int value15, int value22) {
        if (value15 == damagedSaradominBookId || value22 == damagedSaradominBookId) {
            if (value15 == saradominPage1Id || value22 == saradominPage1Id) {
                int value3 = saradominPageBits[0];
                if ((player.godBookPageFlags & GameUtil.bitFlag(value3)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(saradominPage1Id));
                    Player player2 = player;
                    player2.packetSender.sendGameMessage("You add the page to the book...");
                    player.godBookPageFlags += GameUtil.bitFlag(value3);
                    GodBookHandler.completeBookIfFilled(player, value15, value22);
                } else {
                    Player player3 = player;
                    player3.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (value15 == saradominPage2Id || value22 == saradominPage2Id) {
                int value4 = saradominPageBits[1];
                if ((player.godBookPageFlags & GameUtil.bitFlag(value4)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(saradominPage2Id));
                    Player player4 = player;
                    player4.packetSender.sendGameMessage("You add the page to the book...");
                    player.godBookPageFlags += GameUtil.bitFlag(value4);
                    GodBookHandler.completeBookIfFilled(player, value15, value22);
                } else {
                    Player player5 = player;
                    player5.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (value15 == saradominPage3Id || value22 == saradominPage3Id) {
                int value5 = saradominPageBits[2];
                if ((player.godBookPageFlags & GameUtil.bitFlag(value5)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(saradominPage3Id));
                    Player player6 = player;
                    player6.packetSender.sendGameMessage("You add the page to the book...");
                    player.godBookPageFlags += GameUtil.bitFlag(value5);
                    GodBookHandler.completeBookIfFilled(player, value15, value22);
                } else {
                    Player player7 = player;
                    player7.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (value15 == saradominPage4Id || value22 == saradominPage4Id) {
                int value6 = saradominPageBits[3];
                if ((player.godBookPageFlags & GameUtil.bitFlag(value6)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(saradominPage4Id));
                    Player player8 = player;
                    player8.packetSender.sendGameMessage("You add the page to the book...");
                    player.godBookPageFlags += GameUtil.bitFlag(value6);
                    GodBookHandler.completeBookIfFilled(player, value15, value22);
                } else {
                    Player player9 = player;
                    player9.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
        }
        if (value15 == damagedZamorakBookId || value22 == damagedZamorakBookId) {
            if (value15 == zamorakPage1Id || value22 == zamorakPage1Id) {
                int value7 = zamorakPageBits[0];
                if ((player.godBookPageFlags & GameUtil.bitFlag(value7)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(zamorakPage1Id));
                    Player player10 = player;
                    player10.packetSender.sendGameMessage("You add the page to the book...");
                    player.godBookPageFlags += GameUtil.bitFlag(value7);
                    GodBookHandler.completeBookIfFilled(player, value15, value22);
                } else {
                    Player player11 = player;
                    player11.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (value15 == zamorakPage2Id || value22 == zamorakPage2Id) {
                int value8 = zamorakPageBits[1];
                if ((player.godBookPageFlags & GameUtil.bitFlag(value8)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(zamorakPage2Id));
                    Player player12 = player;
                    player12.packetSender.sendGameMessage("You add the page to the book...");
                    player.godBookPageFlags += GameUtil.bitFlag(value8);
                    GodBookHandler.completeBookIfFilled(player, value15, value22);
                } else {
                    Player player13 = player;
                    player13.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (value15 == zamorakPage3Id || value22 == zamorakPage3Id) {
                int value9 = zamorakPageBits[2];
                if ((player.godBookPageFlags & GameUtil.bitFlag(value9)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(zamorakPage3Id));
                    Player player14 = player;
                    player14.packetSender.sendGameMessage("You add the page to the book...");
                    player.godBookPageFlags += GameUtil.bitFlag(value9);
                    GodBookHandler.completeBookIfFilled(player, value15, value22);
                } else {
                    Player player15 = player;
                    player15.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (value15 == zamorakPage4Id || value22 == zamorakPage4Id) {
                int value10 = zamorakPageBits[3];
                if ((player.godBookPageFlags & GameUtil.bitFlag(value10)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(zamorakPage4Id));
                    Player player16 = player;
                    player16.packetSender.sendGameMessage("You add the page to the book...");
                    player.godBookPageFlags += GameUtil.bitFlag(value10);
                    GodBookHandler.completeBookIfFilled(player, value15, value22);
                } else {
                    Player player17 = player;
                    player17.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
        }
        if (value15 == damagedGuthixBookId || value22 == damagedGuthixBookId) {
            if (value15 == guthixPage1Id || value22 == guthixPage1Id) {
                int value11 = guthixPageBits[0];
                if ((player.godBookPageFlags & GameUtil.bitFlag(value11)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(guthixPage1Id));
                    Player player18 = player;
                    player18.packetSender.sendGameMessage("You add the page to the book...");
                    player.godBookPageFlags += GameUtil.bitFlag(value11);
                    GodBookHandler.completeBookIfFilled(player, value15, value22);
                } else {
                    Player player19 = player;
                    player19.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (value15 == guthixPage2Id || value22 == guthixPage2Id) {
                int value12 = guthixPageBits[1];
                if ((player.godBookPageFlags & GameUtil.bitFlag(value12)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(guthixPage2Id));
                    Player player20 = player;
                    player20.packetSender.sendGameMessage("You add the page to the book...");
                    player.godBookPageFlags += GameUtil.bitFlag(value12);
                    GodBookHandler.completeBookIfFilled(player, value15, value22);
                } else {
                    Player player21 = player;
                    player21.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (value15 == guthixPage3Id || value22 == guthixPage3Id) {
                int value13 = guthixPageBits[2];
                if ((player.godBookPageFlags & GameUtil.bitFlag(value13)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(guthixPage3Id));
                    Player player22 = player;
                    player22.packetSender.sendGameMessage("You add the page to the book...");
                    player.godBookPageFlags += GameUtil.bitFlag(value13);
                    GodBookHandler.completeBookIfFilled(player, value15, value22);
                } else {
                    Player player23 = player;
                    player23.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
            if (value15 == guthixPage4Id || value22 == guthixPage4Id) {
                int value14 = guthixPageBits[3];
                if ((player.godBookPageFlags & GameUtil.bitFlag(value14)) == 0) {
                    player.getInventoryManager().removeItem(new ItemStack(guthixPage4Id));
                    Player player24 = player;
                    player24.packetSender.sendGameMessage("You add the page to the book...");
                    player.godBookPageFlags += GameUtil.bitFlag(value14);
                    GodBookHandler.completeBookIfFilled(player, value15, value22);
                } else {
                    Player player25 = player;
                    player25.packetSender.sendGameMessage("The book already has that page.");
                }
                return true;
            }
        }
        return false;
    }

    private static void completeBookIfFilled(Player player, int value3, int value22) {
        ArrayList arrayList;
        if (value3 == damagedSaradominBookId || value22 == damagedSaradominBookId) {
            ArrayList arrayList2 = GodBookHandler.getMissingPageNumbers(player, damagedSaradominBookId);
            if (arrayList2.size() == 0) {
                player.getInventoryManager().removeItem(new ItemStack(damagedSaradominBookId));
                player.getInventoryManager().addItem(new ItemStack(holyBookId));
                player.packetSender.sendGameMessage("The book is now complete!");
                return;
            }
        } else if (value3 == damagedZamorakBookId || value22 == damagedZamorakBookId) {
            ArrayList arrayList3 = GodBookHandler.getMissingPageNumbers(player, damagedZamorakBookId);
            if (arrayList3.size() == 0) {
                player.getInventoryManager().removeItem(new ItemStack(damagedZamorakBookId));
                player.getInventoryManager().addItem(new ItemStack(unholyBookId));
                player.packetSender.sendGameMessage("The book is now complete!");
                return;
            }
        } else if ((value3 == damagedGuthixBookId || value22 == damagedGuthixBookId) && (arrayList = GodBookHandler.getMissingPageNumbers(player, damagedGuthixBookId)).size() == 0) {
            player.getInventoryManager().removeItem(new ItemStack(damagedGuthixBookId));
            player.getInventoryManager().addItem(new ItemStack(bookOfBalanceId));
            player.packetSender.sendGameMessage("The book is now complete!");
        }
    }

    public static void giveReplacementBook(Player player, int value2) {
        ArrayList arrayList = GodBookHandler.getMissingPageNumbers(player, value2);
        if (arrayList.size() == 0) {
            if (value2 == damagedSaradominBookId) {
                value2 = holyBookId;
            } else if (value2 == damagedZamorakBookId) {
                value2 = unholyBookId;
            } else if (value2 == damagedGuthixBookId) {
                value2 = bookOfBalanceId;
            }
        }
        player.getInventoryManager().addOrDropItem(new ItemStack(value2, 1));
    }

    public static boolean openRecitationDialogue(Player player, int value2) {
        if (value2 == holyBookId || value2 == unholyBookId || value2 == bookOfBalanceId) {
            player.temporaryActionValue = value2;
            DialogueManager.startDialogue(player, 13001);
            return true;
        }
        return false;
    }

    public static void startRecitation(Player player, int value3) {
        int value2 = player.temporaryActionValue;
        int initialValue = -1;
        player.sharedActionValue = 0;
        if (value2 == holyBookId) {
            initialValue = saradominRecitationAnimationId;
        } else if (value2 == unholyBookId) {
            initialValue = zamorakRecitationAnimationId;
        } else if (value2 == bookOfBalanceId) {
            initialValue = guthixRecitationAnimationId;
        }
        String[] stringValues = null;
        if (value3 == weddingCeremonyOptionId) {
            if (value2 == holyBookId) {
                stringValues = saradominWeddingCeremonyLines;
            } else if (value2 == unholyBookId) {
                stringValues = zamorakWeddingCeremonyLines;
            } else if (value2 == bookOfBalanceId) {
                stringValues = guthixWeddingCeremonyLines;
            }
        } else if (value3 == lastRitesOptionId) {
            if (value2 == holyBookId) {
                stringValues = saradominLastRitesLines;
            } else if (value2 == unholyBookId) {
                stringValues = zamorakLastRitesLines;
            } else if (value2 == bookOfBalanceId) {
                stringValues = guthixLastRitesLines;
            }
        } else if (value3 == blessingsOptionId) {
            if (value2 == holyBookId) {
                stringValues = saradominBlessingLines;
            } else if (value2 == unholyBookId) {
                stringValues = zamorakBlessingLines;
            } else if (value2 == bookOfBalanceId) {
                stringValues = guthixBlessingLines;
            }
        } else if (value3 == preachOptionId) {
            if (value2 == holyBookId) {
                stringValues = saradominPreachLines;
            } else if (value2 == unholyBookId) {
                stringValues = zamorakPreachLines;
            } else if (value2 == bookOfBalanceId) {
                stringValues = guthixPreachLines;
            }
        }
        String[] stringValues2 = stringValues;
        player.setActionLocked(true);
        player.getUpdateState().setAnimation(initialValue, 0);
        CycleEventHandler.getInstance().schedule(player, new GodBookRecitationEvent(player, stringValues2), 2);
    }
}

