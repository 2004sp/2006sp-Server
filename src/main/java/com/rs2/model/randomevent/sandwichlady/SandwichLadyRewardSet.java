package com.rs2.model.randomevent.sandwichlady;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import java.util.ArrayList;

public interface SandwichLadyRewardSet {
    public ArrayList getButtonIds();

    public ItemStack[] getRewards();

    public String[] getMessages();

    public void punishWrongChoice(Player player);
}
