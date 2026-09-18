package com.rs2.model.item;

import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;

public final class DegradableEquipmentHandler {
    private static String[] barrowsSetNameTokens = new String[]{"ahrims", "dharoks", "torags", "guthans", "karils", "veracs"};

    static {
        (new String[1])[0] = "crystal";
    }

    public static void degradeEquipmentAfterCombat(Player player) {
        int index = 0;
        while (index < 14) {
            Object equipmentManager = player.getEquipmentManager().getContainer().getItemAt(index);
            int value = index;
            Player player2 = player;
            if (equipmentManager != null) {
                Object value2;
                ItemStack itemStack = (ItemStack)equipmentManager;
                int value3 = value;
                equipmentManager = player2;
                String[] stringValues = barrowsSetNameTokens;
                int index2 = 0;
                while (index2 < 6) {
                    value2 = stringValues[index2];
                    if (itemStack.getDefinition().getName().toLowerCase().contains((CharSequence)value2) && (itemStack.getId() < 4856 || itemStack.getId() > 4999)) {
                        int value4;
                        ItemContainer itemContainer = ((Player)equipmentManager).getEquipmentManager().getContainer();
                        int id = itemStack.getId();
                        switch (id) {
                            case 4708: {
                                value4 = 4856;
                                break;
                            }
                            case 4710: {
                                value4 = 4862;
                                break;
                            }
                            case 4712: {
                                value4 = 4868;
                                break;
                            }
                            case 4714: {
                                value4 = 4874;
                                break;
                            }
                            case 4716: {
                                value4 = 4880;
                                break;
                            }
                            case 4718: {
                                value4 = 4886;
                                break;
                            }
                            case 4720: {
                                value4 = 4892;
                                break;
                            }
                            case 4722: {
                                value4 = 4898;
                                break;
                            }
                            case 4724: {
                                value4 = 4904;
                                break;
                            }
                            case 4726: {
                                value4 = 4910;
                                break;
                            }
                            case 4728: {
                                value4 = 4916;
                                break;
                            }
                            case 4730: {
                                value4 = 4922;
                                break;
                            }
                            case 4732: {
                                value4 = 4928;
                                break;
                            }
                            case 4734: {
                                value4 = 4934;
                                break;
                            }
                            case 4736: {
                                value4 = 4940;
                                break;
                            }
                            case 4738: {
                                value4 = 4946;
                                break;
                            }
                            case 4745: {
                                value4 = 4952;
                                break;
                            }
                            case 4747: {
                                value4 = 4958;
                                break;
                            }
                            case 4749: {
                                value4 = 4964;
                                break;
                            }
                            case 4751: {
                                value4 = 4970;
                                break;
                            }
                            case 4753: {
                                value4 = 4976;
                                break;
                            }
                            case 4755: {
                                value4 = 4982;
                                break;
                            }
                            case 4757: {
                                value4 = 4988;
                                break;
                            }
                            case 4759: {
                                value4 = 4994;
                                break;
                            }
                            default: {
                                value4 = id;
                            }
                        }
                        itemContainer.setItem(value3, new ItemStack(value4));
                        ((Player)equipmentManager).getEquipmentManager().getContainer().getItemAt(value3).setMetadata(4500);
                    }
                    ++index2;
                }
                if (itemStack.getId() >= 4856 && itemStack.getId() <= 4999 && !itemStack.getDefinition().getName().toLowerCase().contains(" 0") && ((Player)equipmentManager).getEquipmentManager().getContainer().getItemAt(value3).getMetadata() == 0) {
                    ((Player)equipmentManager).getEquipmentManager().getContainer().setItem(value3, new ItemStack(itemStack.getId() + 1));
                    if (!itemStack.getDefinition().getName().toLowerCase().contains(" 25")) {
                        ((Player)equipmentManager).getEquipmentManager().getContainer().getItemAt(value3).setMetadata(4500);
                    } else {
                        value2 = equipmentManager;
                        ((Player)value2).packetSender.sendGameMessage("Your barrow equipment has broke.");
                    }
                }
                if (itemStack.getDefinition().getName().toLowerCase().contains("crystal")) {
                    if (itemStack.getDefinition().getId() == 4212 || itemStack.getDefinition().getId() == 4224) {
                        ((Player)equipmentManager).getEquipmentManager().getContainer().setItem(value3, new ItemStack(itemStack.getDefinition().getId() == 4212 ? 4214 : 4225));
                        ((Player)equipmentManager).getEquipmentManager().getContainer().getItemAt(value3).setMetadata(250);
                    } else if (((Player)equipmentManager).getEquipmentManager().getContainer().getItemAt(value3).getMetadata() == 0) {
                        if (itemStack.getDefinition().getId() == 4223 || itemStack.getDefinition().getId() == 4234) {
                            ((Player)equipmentManager).getEquipmentManager().getContainer().remove(itemStack);
                        } else {
                            ((Player)equipmentManager).getEquipmentManager().getContainer().setItem(value3, new ItemStack(itemStack.getId() + 1));
                            ((Player)equipmentManager).getEquipmentManager().getContainer().getItemAt(value3).setMetadata(250);
                        }
                    }
                }
                if (player2.getEquipmentManager().getContainer().getItemAt(value).getMetadata() >= 0 && player2.getEquipmentManager().getItemIdAtSlot(value) != 11283 && player2.getEquipmentManager().getItemIdAtSlot(value) != 11284) {
                    player2.getEquipmentManager().getContainer().getItemAt(value).setMetadata(player2.getEquipmentManager().getContainer().getItemAt(value).getMetadata() - 1);
                }
            }
            ++index;
        }
        player.getEquipmentManager().refresh();
    }
}

