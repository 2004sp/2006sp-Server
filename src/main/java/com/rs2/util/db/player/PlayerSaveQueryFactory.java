package com.rs2.util.db.player;

import com.rs2.model.player.Player;
import com.rs2.util.db.DatabaseQuery;
import com.rs2.util.db.player.PlayerContactsSaveQuery;
import com.rs2.util.db.player.PlayerContainerSaveQuery;
import com.rs2.util.db.player.PlayerProfileSaveQuery;
import com.rs2.util.db.player.PlayerSkillsSaveQuery;
import java.util.HashMap;
import java.util.Map;

public final class PlayerSaveQueryFactory {
    private static final String CONTACTS_SAVE_SQL;
    private static final Map containerSaveSqlByContainerId;
    private final Player player;

    static {
        String contactsInsertSql = "INSERT INTO prs06_contacts (`id`, `player_id`, `slot`, `contact`, `ignore`) VALUES ";
        String contactsUpsertClause = " ON CONFLICT(id) DO UPDATE SET contact = excluded.contact, `ignore` = excluded.`ignore`, slot = excluded.slot";
        int initialValue = 1;
        while (initialValue <= 300) {
            contactsInsertSql = String.valueOf(contactsInsertSql) + "(?, ?, ?, ?, ?)";
            if (initialValue != 300) {
                contactsInsertSql = String.valueOf(contactsInsertSql) + ", ";
            }
            ++initialValue;
        }
        CONTACTS_SAVE_SQL = String.valueOf(contactsInsertSql) + contactsUpsertClause;
        containerSaveSqlByContainerId = new HashMap();
    }

    public PlayerSaveQueryFactory(Player player) {
        this.player = player;
    }

    public final DatabaseQuery createProfileSaveQuery() {
        return new PlayerProfileSaveQuery(this);
    }

    public final DatabaseQuery createBankSaveQuery() {
        return new PlayerContainerSaveQuery(this, this.player.getBankContainer(), 0);
    }

    public final DatabaseQuery createInventorySaveQuery() {
        return new PlayerContainerSaveQuery(this, this.player.getInventoryManager().getContainer(), 1);
    }

    public final DatabaseQuery createEquipmentSaveQuery() {
        return new PlayerContainerSaveQuery(this, this.player.getEquipmentManager().getContainer(), 2);
    }

    public final DatabaseQuery createContactsSaveQuery() {
        return new PlayerContactsSaveQuery(this);
    }

    public final DatabaseQuery createSkillsSaveQuery() {
        return new PlayerSkillsSaveQuery(this);
    }

    static Player getPlayer(PlayerSaveQueryFactory playerSaveQueryFactory) {
        return playerSaveQueryFactory.player;
    }

    static String getContainerSaveSql(int value3, int value22) {
        if (!containerSaveSqlByContainerId.containsKey(value3)) {
            String text = "INSERT INTO prs06_containers (id, container_id, user_id, item_id, amount, slot, timer) VALUES ";
            String text2 = " ON CONFLICT(id) DO UPDATE SET item_id = excluded.item_id, amount = excluded.amount, slot = excluded.slot, timer = excluded.timer";
            int initialValue = 1;
            while (initialValue <= value22) {
                text = String.valueOf(text) + "(?, ?, ?, ?, ?, ?, ?)";
                if (initialValue != value22) {
                    text = String.valueOf(text) + ", ";
                }
                ++initialValue;
            }
            String text3 = String.valueOf(text) + text2;
            containerSaveSqlByContainerId.put(value3, text3);
            return text3;
        }
        return (String)containerSaveSqlByContainerId.get(value3);
    }

    static String getContactsSaveSql() {
        return CONTACTS_SAVE_SQL;
    }
}
