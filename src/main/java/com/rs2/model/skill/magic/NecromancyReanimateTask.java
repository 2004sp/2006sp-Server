package com.rs2.model.skill.magic;

import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.model.task.TickTask;

public final class NecromancyReanimateTask
extends TickTask {
    private MagicSpellAction spellAction;
    private final int reanimationIndex;

    public NecromancyReanimateTask(MagicSpellAction magicSpellAction, int value3, int reanimationIndex) {
        super(2);
        this.spellAction = magicSpellAction;
        this.reanimationIndex = reanimationIndex;
    }

    @Override
    public final void execute() {
        MagicSpellAction.getPlayer(this.spellAction).getInventoryManager().removeItem(new ItemStack(MagicSpellAction.getNecromancyReanimationTable()[this.reanimationIndex][0], 1));
        Entity entity = new Npc(MagicSpellAction.getNecromancyReanimationTable()[this.reanimationIndex][1]);
        GameplayHelper.spawnOwnedNpcAdjacentToPlayer(MagicSpellAction.getPlayer(this.spellAction), (Npc)entity, true, false);
        entity.getUpdateState().setGraphicHeight100(110);
        if (MagicSpellAction.getPlayer(this.spellAction).getEquipmentManager().getItemIdAtSlot(5) != 8118) {
            MagicSpellAction.getPlayer(this.spellAction).setSpellbook(MagicSpellAction.getPlayer((MagicSpellAction)this.spellAction).previousSpellbookBeforeNecromancy);
            if (MagicSpellAction.getPlayer(this.spellAction).getSpellbook() == Spellbook.MODERN) {
                entity = MagicSpellAction.getPlayer(this.spellAction);
                ((Player)entity).packetSender.setSidebarInterface(6, 1151);
            }
            if (MagicSpellAction.getPlayer(this.spellAction).getSpellbook() == Spellbook.ANCIENT) {
                entity = MagicSpellAction.getPlayer(this.spellAction);
                ((Player)entity).packetSender.setSidebarInterface(6, 12855);
            }
        }
        MagicSpellAction.getPlayer(this.spellAction).setActionLocked(false);
        this.stop();
    }
}
