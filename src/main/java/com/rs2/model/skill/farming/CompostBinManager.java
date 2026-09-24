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
    // 2006-era regular compost ingredients: weeds, low-level crops/fruit/berries,
    // hops, flowers, snape grass, and herbs below the supercompost threshold.
    private static final int[] compostableItemIds = new int[]{
        6055, // Weeds
        1947, // Grain
        1779, // Flax
        231,  // Snape grass (regular compost in 2006)
        1951, 753, 2126, // Redberries, cadava berries, dwellberries
        1942, 1957, 1965, 5986, 5504, // Potato, onion, cabbage, sweetcorn, strawberry
        1955, 1963, 2102, 2108, 2120, 5970, // Apple, banana, lemon, orange, lime, curry leaf
        6006, 5994, 5996, 5931, 5998, 6000, 6002, // Barley/jute/hops
        6010, 6014, 6012, 1793, 225, // Marigold, rosemary, nasturtium, woad, limpwurt
        199, 249, 201, 251, 203, 253, 205, 255, 207, 257, 209, 259 // Grimy/clean guam through irit
    };

    // 2006-era supercompost ingredients. All 15 items must remain supercompostable;
    // adding any regular-compost item downgrades the whole bin to normal compost.
    private static final int[] supercompostableItemIds = new int[]{
        247, 239, 6018, // Jangerberries, white berries, poison ivy berries
        2114, 5972, 5974, 5978, // Pineapple, papaya, coconut, coconut shell
        5980, 5982, 6004, 6016, 6469, // Calquat, watermelon, mushroom, cactus spine, white tree fruit
        6043, 6045, 6047, 6049, 6051, // Oak, willow, maple, yew, magic roots
        3049, 2998, // Grimy/clean toadflax
        211, 261,   // Grimy/clean avantoe
        213, 263,   // Grimy/clean kwuarm
        3051, 3000, // Grimy/clean snapdragon
        215, 265,   // Grimy/clean cadantine
        2485, 2481, // Grimy/clean lantadyme
        217, 267,   // Grimy/clean dwarf weed
        219, 269    // Grimy/clean torstol
    };

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

    public final boolean fillBin(int itemId, int objectId, int objectX, int objectY) {
        Position position = new Position(objectX, objectY);
        CompostBin compostBin = CompostBin.forPosition(position);

        // Object 7809 is the compost-bin object used by the Falador interaction.
        // Its clickable tile can differ from the farming anchor, so only this
        // known compost object gets the small nearby-position fallback.
        if (compostBin == null && objectId == 7809) {
            compostBin = CompostBin.forInteractionPosition(position);
        }
        if (compostBin == null) {
            return false;
        }

        int index = compostBin.getIndex();
        if (this.states[index] < 15) {
            if (!ServerSettings.farmingEnabled) {
                this.player.packetSender.sendGameMessage("This skill is currently disabled.");
                return true;
            }

            boolean compostable = false;
            for (int compostableItemId : compostableItemIds) {
                if (itemId == compostableItemId) {
                    this.itemIds[index] = 6032;
                    compostable = true;
                    break;
                }
            }

            if (!compostable) {
                for (int supercompostableItemId : supercompostableItemIds) {
                    if (itemId == supercompostableItemId) {
                        if (this.states[index] == 0) {
                            this.itemIds[index] = 6034;
                        }
                        compostable = true;
                        break;
                    }
                }
            }

            if (itemId == 1982) {
                if (this.states[index] == 0) {
                    this.itemIds[index] = 2518;
                }
                compostable = true;
            }

            if (!compostable) {
                this.player.packetSender.sendGameMessage("You need to put organic items into the compost bin in order to make compost.");
                return true;
            }

            int actionSequence = this.player.nextActionSequence();
            this.player.setActiveCycleEvent(new CompostBinFillTask(this, actionSequence, itemId, index, 1));
            CycleEventHandler.getInstance().schedule(this.player, this.player.getActiveCycleEvent(), 2);
            return true;
        }

        if (this.states[index] >= 16 && this.states[index] <= 30) {
            if (itemId == 1925) {
                this.startEmptyBin(index);
            } else {
                this.player.packetSender.sendGameMessage("You might need some buckets to gather the compost.");
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

