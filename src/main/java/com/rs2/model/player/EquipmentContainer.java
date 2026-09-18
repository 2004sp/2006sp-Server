package com.rs2.model.player;

import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemContainerType;
import com.rs2.model.player.EquipmentManager;

public final class EquipmentContainer
extends ItemContainer {
    private final EquipmentManager equipmentManager;

    public EquipmentContainer(EquipmentManager equipmentManager, ItemContainerType itemContainerType, int value2) {
        super(itemContainerType, 14);
        this.equipmentManager = equipmentManager;
    }

    public final void clearAndRefreshCarriedValue() {
        super.clear();
        EquipmentManager.getPlayer(this.equipmentManager).getEquipmentManager().refreshCarriedValue();
    }
}
