package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.CompostBin;
import com.rs2.model.skill.farming.CompostBinCloseTask;
import com.rs2.model.skill.farming.CompostBinEmptyTask;
import com.rs2.model.skill.farming.CompostBinFillTask;
import com.rs2.model.skill.farming.CompostBinOpenTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

public final class CompostBinManager {
    private Player player;
    public int[] states = new int[4];
    public int[] itemIds = new int[4];
    public long[] lastUpdateTicks = new long[4];
    private static int[] compostableItemIds = new int[]{239, 249, 251, 253, 255, 257, 259, 261, 263, 265, 267, 269, 753, 1942, 1951, 1957, 1965, 2126, 2481, 2998, 3000, 5504, 5986, 6018, 6055};
    private static int[] supercompostableItemIds = new int[]{247, 2114, 5978, 5980, 5982, 6004, 6469};

    public CompostBinManager(Player player) {
        this.player = player;
    }

    public final void refreshConfig() {
        int[] integerValues = new int[this.states.length];
        int index = 0;
        while (index < this.states.length) {
            int value;
            int value2 = this.itemIds[index];
            int value3 = value = this.states[index];
            if (value > 0 && value <= 30) {
                if (value2 == 6034) {
                    value3 += 32;
                }
                if (value2 == 2518) {
                    value3 += 128;
                }
            }
            integerValues[index] = value3;
            ++index;
        }
        int value4 = integerValues[0] | integerValues[1] << 8 | integerValues[2] << 16 | integerValues[3] << 24;
        Player player = this.player;
        player.packetSender.sendConfig(511, value4);
    }

    private void startEmptyBin(int value4) {
        if (!ServerSettings.farmingEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return;
        }
        int value2 = this.itemIds[value4];
        int value3 = this.player.nextActionSequence();
        this.player.getUpdateState().setAnimation(832, 0);
        this.player.setActiveCycleEvent(new CompostBinEmptyTask(this, value3, value4, value2));
        CycleEventHandler.getInstance().schedule(this.player, this.player.getActiveCycleEvent(), 2);
    }

    public final boolean fillBin(int value7, int value22, int value32) {
        CompostBin compostBin = CompostBin.forPosition(new Position(value22, value32));
        if (compostBin == null) {
            return false;
        }
        int index = compostBin.getIndex();
        if (this.states[index] < 15) {
            Position position = new Position(value22, value32);
            value32 = value7;
            Object value4 = position;
            Object value5 = this;
            value4 = CompostBin.forPosition((Position)value4);
            index = ((CompostBin)((Object)value4)).getIndex();
            if (value4 != null) {
                if (!ServerSettings.farmingEnabled) {
                    value5 = ((CompostBinManager)value5).player;
                    ((Player)value5).packetSender.sendGameMessage("This skill is currently disabled.");
                } else if (((CompostBinManager)value5).states[index] < 15) {
                    int value6;
                    int index2 = 0;
                    int[] integerValues = compostableItemIds;
                    int index3 = 0;
                    while (index3 < 25) {
                        value6 = integerValues[index3];
                        if (value32 == value6) {
                            ((CompostBinManager)value5).itemIds[index] = 6032;
                            index2 = 1;
                        }
                        ++index3;
                    }
                    integerValues = supercompostableItemIds;
                    index3 = 0;
                    while (index3 < 7) {
                        value6 = integerValues[index3];
                        if (value32 == value6) {
                            if (((CompostBinManager)value5).states[index] == 0) {
                                ((CompostBinManager)value5).itemIds[index] = 6034;
                            }
                            index2 = 1;
                        }
                        ++index3;
                    }
                    if (value32 == 1982) {
                        if (((CompostBinManager)value5).states[index] == 0) {
                            ((CompostBinManager)value5).itemIds[index] = 2518;
                        }
                        index2 = 1;
                    }
                    if (index2 == 0) {
                        value5 = ((CompostBinManager)value5).player;
                        ((Player)value5).packetSender.sendGameMessage("You need to put organic items into the compost bin in order to make compost.");
                    } else {
                        value6 = index2;
                        index3 = ((CompostBinManager)value5).player.nextActionSequence();
                        ((CompostBinManager)value5).player.setActiveCycleEvent(new CompostBinFillTask((CompostBinManager)value5, index3, value32, index, value6));
                        CycleEventHandler.getInstance().schedule(((CompostBinManager)value5).player, ((CompostBinManager)value5).player.getActiveCycleEvent(), 2);
                    }
                }
            }
            return true;
        }
        if (this.states[index] >= 16 && this.states[index] <= 30) {
            if (value7 == 1925) {
                this.startEmptyBin(index);
            } else {
                Player player = this.player;
                player.packetSender.sendGameMessage("You might need some buckets to gather the compost.");
            }
            return true;
        }
        return false;
    }

    public final void processRotting() {
        int index = 0;
        while (index < this.states.length) {
            long value;
            int value2 = index;
            CompostBinManager compostBinManager = this;
            if ((compostBinManager.itemIds[value2] == 6032 && compostBinManager.states[value2] >= 65 && compostBinManager.states[value2] <= 126 ? true : (compostBinManager.itemIds[value2] == 6034 && compostBinManager.states[value2] == 32 ? true : compostBinManager.itemIds[value2] == 2518 && compostBinManager.states[value2] >= 65 && compostBinManager.states[value2] <= 126)) && (value = Server.getElapsedMinutes() - this.lastUpdateTicks[index]) > 0L) {
                if (this.itemIds[index] == 6034) {
                    if (value >= 90L) {
                        this.states[index] = 31;
                    }
                } else {
                    int value3 = index;
                    this.states[value3] = (int)((long)this.states[value3] - value);
                    if (this.states[index] <= 64) {
                        this.states[index] = 64;
                    }
                    this.lastUpdateTicks[index] = Server.getElapsedMinutes();
                }
            }
            ++index;
        }
        this.refreshConfig();
    }

    public final boolean handleBinObject(int objectId, int value4) {
        CompostBin compostBin = CompostBin.forPosition(new Position(objectId, value4));
        if (compostBin == null) {
            return false;
        }
        int index = compostBin.getIndex();
        if (this.states[index] == 15) {
            boolean enabled;
            int value2 = index;
            CompostBinManager compostBinManager = this;
            if (!ServerSettings.farmingEnabled) {
                Player player = compostBinManager.player;
                player.packetSender.sendGameMessage("This skill is currently disabled.");
                enabled = true;
            } else if (compostBinManager.states[value2] != 15) {
                enabled = true;
            } else {
                if (compostBinManager.itemIds[value2] == 6032) {
                    compostBinManager.states[value2] = 99 + GameUtil.randomInt(16);
                }
                if (compostBinManager.itemIds[value2] == 6034) {
                    compostBinManager.states[value2] = 32;
                }
                if (compostBinManager.itemIds[value2] == 2518) {
                    compostBinManager.states[value2] = 99 + GameUtil.randomInt(16);
                }
                compostBinManager.lastUpdateTicks[value2] = Server.getElapsedMinutes();
                compostBinManager.player.getUpdateState().setAnimation(835, 0);
                compostBinManager.player.setActionLocked(true);
                CycleEventHandler.getInstance().schedule(compostBinManager.player, new CompostBinCloseTask(compostBinManager), 2);
                enabled = true;
            }
            return true;
        }
        if (this.states[index] >= 16 && this.states[index] <= 30) {
            this.startEmptyBin(index);
            return true;
        }
        int value3 = index;
        CompostBinManager compostBinManager = this;
        if (compostBinManager.itemIds[value3] == 6032 && compostBinManager.states[value3] >= 64 && compostBinManager.states[value3] <= 126 ? true : (compostBinManager.itemIds[value3] == 6034 && (compostBinManager.states[value3] == 31 || compostBinManager.states[value3] == 32) ? true : compostBinManager.itemIds[value3] == 2518 && compostBinManager.states[value3] >= 64 && compostBinManager.states[value3] <= 126)) {
            value3 = index;
            compostBinManager = this;
            index = 0;
            if (compostBinManager.itemIds[value3] == 6032 && compostBinManager.states[value3] == 64) {
                index = 1;
            } else if (compostBinManager.itemIds[value3] == 6034 && compostBinManager.states[value3] == 31) {
                index = 1;
            } else if (compostBinManager.itemIds[value3] == 2518 && compostBinManager.states[value3] == 64) {
                index = 1;
            }
            if (index != 0) {
                compostBinManager.states[value3] = 30;
                compostBinManager.player.getUpdateState().setAnimation(834, 0);
                CycleEventHandler.getInstance().schedule(compostBinManager.player, new CompostBinOpenTask(compostBinManager), 2);
            } else {
                Player player = compostBinManager.player;
                player.packetSender.sendGameMessage("The compost bin is still rotting. I should wait until it is complete.");
            }
            return true;
        }
        return false;
    }

    static Player getPlayer(CompostBinManager compostBinManager) {
        return compostBinManager.player;
    }
}

