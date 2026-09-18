package com.rs2.model.randomevent.sandwichlady;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.randomevent.RandomEventTeleportLocations;
import com.rs2.model.randomevent.sandwichlady.SandwichLadyRewardSet;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class SandwichLadyFoodOffer
implements SandwichLadyRewardSet {
    private int offerIndex;

    public SandwichLadyFoodOffer(boolean enabled2) {
        if (enabled2) {
            this.offerIndex = GameUtil.randomInclusive(6);
        }
    }

    public final String[] getOfferedFoodNames() {
        switch (this.offerIndex) {
            case 0: {
                return new String[]{"redberry pie"};
            }
            case 1: {
                return new String[]{"kebab"};
            }
            case 2: {
                return new String[]{"chocolate bar"};
            }
            case 3: {
                return new String[]{"baguette"};
            }
            case 4: {
                return new String[]{"triangle sandwich"};
            }
            case 5: {
                return new String[]{"square sandwich"};
            }
            case 6: {
                return new String[]{"bread roll"};
            }
        }
        return null;
    }

    @Override
    public final ArrayList getButtonIds() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(16141);
        arrayList.add(16142);
        arrayList.add(16143);
        arrayList.add(16137);
        arrayList.add(16138);
        arrayList.add(16139);
        arrayList.add(16140);
        return arrayList;
    }

    @Override
    public final ItemStack[] getRewards() {
        return new ItemStack[]{new ItemStack(2325), new ItemStack(1971), new ItemStack(1973), new ItemStack(6961), new ItemStack(6962), new ItemStack(6965), new ItemStack(2309)};
    }

    @Override
    public final String[] getMessages() {
        return new String[]{"Hope that fills you up!!", "Let's see how you like this!", "I'm sorry, this wasn't the treat I offered you!"};
    }

    public final int getOfferIndex() {
        return this.offerIndex;
    }

    @Override
    public final void punishWrongChoice(Player player) {
        int value = GameUtil.randomInclusive(6);
        Player player2 = player;
        player2.ownedNpc.startNpcRelocation(player, 402, 2304, RandomEventTeleportLocations.TELEPORT_DESTINATIONS[value].getX(), RandomEventTeleportLocations.TELEPORT_DESTINATIONS[value].getY(), RandomEventTeleportLocations.TELEPORT_DESTINATIONS[value].getPlane(), this.getMessages()[2], true);
    }
}

