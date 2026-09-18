package com.rs2.util.db.player;

import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemStack;
import com.rs2.util.db.DatabaseQuery;
import com.rs2.util.db.player.PlayerSaveQueryFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class PlayerContainerSaveQuery
extends DatabaseQuery {
    private ItemContainer container;
    private int containerId;
    private PlayerSaveQueryFactory factory;

    public PlayerContainerSaveQuery(PlayerSaveQueryFactory playerSaveQueryFactory, ItemContainer itemContainer, int containerId) {
        super(PlayerSaveQueryFactory.getContainerSaveSql(containerId, itemContainer.getCapacity()));
        this.factory = playerSaveQueryFactory;
        this.container = itemContainer;
        this.containerId = containerId;
    }

    @Override
    public final ResultSet executeStatement(PreparedStatement preparedStatement) throws java.sql.SQLException {
        int initialValue = 1;
        int index = 0;
        while (index < this.container.getCapacity()) {
            ItemStack itemStack = this.container.getItemAt(index);
            preparedStatement.setInt(initialValue++, this.containerId << 28 | index << 18 | PlayerSaveQueryFactory.getPlayer(this.factory).getReferenceId());
            preparedStatement.setInt(initialValue++, this.containerId);
            preparedStatement.setInt(initialValue++, PlayerSaveQueryFactory.getPlayer(this.factory).getReferenceId());
            if (itemStack == null) {
                preparedStatement.setInt(initialValue++, -1);
                preparedStatement.setInt(initialValue++, 0);
                preparedStatement.setInt(initialValue++, index);
                preparedStatement.setInt(initialValue++, 0);
            } else {
                preparedStatement.setInt(initialValue++, itemStack.getId());
                preparedStatement.setInt(initialValue++, itemStack.getAmount());
                preparedStatement.setInt(initialValue++, index);
                preparedStatement.setInt(initialValue++, itemStack.getMetadata());
            }
            ++index;
        }
        preparedStatement.executeUpdate();
        return null;
    }
}
