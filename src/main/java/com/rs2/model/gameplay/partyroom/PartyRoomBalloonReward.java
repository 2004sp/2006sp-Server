package com.rs2.model.gameplay.partyroom;

import com.rs2.model.Position;
import com.rs2.model.gameplay.partyroom.PartyRoomManager;
import com.rs2.model.item.ItemStack;

public final class PartyRoomBalloonReward {
    ItemStack rewardItem;
    Position balloonPosition;

    private PartyRoomBalloonReward(ItemStack itemStack, Position position) {
        this.rewardItem = itemStack;
        this.balloonPosition = position;
        PartyRoomManager.balloonRewards.add(this);
    }

    PartyRoomBalloonReward(ItemStack itemStack, Position position, byte value2) {
        this(itemStack, position);
    }

    PartyRoomBalloonReward(ItemStack itemStack, Position position, int value2) {
        this(itemStack, position);
    }
}

