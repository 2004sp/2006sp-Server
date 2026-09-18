package com.rs2.util.db.player;

import com.rs2.model.player.Player;
import com.rs2.util.db.DatabaseCallback;
import com.rs2.util.db.DatabaseQuery;
import com.rs2.util.db.player.PlayerContactsLoadCallback;
import com.rs2.util.db.player.PlayerContainerLoadCallback;
import com.rs2.util.db.player.PlayerContainerLoadQuery;
import com.rs2.util.db.player.PlayerProfileLoadCallback;
import com.rs2.util.db.player.PlayerSkillsLoadCallback;

public final class PlayerLoadQueryFactory {
    private final Player player;

    public PlayerLoadQueryFactory(Player player) {
        this.player = player;
    }

    public final DatabaseCallback createProfileLoadCallback() {
        return new PlayerProfileLoadCallback(this, (byte)0);
    }

    public final DatabaseCallback createBankLoadCallback() {
        return new PlayerContainerLoadCallback(this, this.player.getBankContainer());
    }

    public final DatabaseCallback createInventoryLoadCallback() {
        return new PlayerContainerLoadCallback(this, this.player.getInventoryManager().getContainer());
    }

    public final DatabaseCallback createEquipmentLoadCallback() {
        return new PlayerContainerLoadCallback(this, this.player.getEquipmentManager().getContainer());
    }

    public final DatabaseCallback createContactsLoadCallback() {
        return new PlayerContactsLoadCallback(this);
    }

    public final DatabaseCallback createSkillsLoadCallback() {
        return new PlayerSkillsLoadCallback(this, (byte)0);
    }

    public final DatabaseQuery createContainerLoadQuery(int value2) {
        return new PlayerContainerLoadQuery(this, "SELECT item_id, amount, timer, slot FROM prs06_containers WHERE user_id = ? AND container_id = ?", value2);
    }

    static Player getPlayer(PlayerLoadQueryFactory playerLoadQueryFactory) {
        return playerLoadQueryFactory.player;
    }
}

