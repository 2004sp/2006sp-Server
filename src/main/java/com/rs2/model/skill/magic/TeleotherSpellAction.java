package com.rs2.model.skill.magic;

import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.magic.TeleotherDestination;

public final class TeleotherSpellAction
extends MagicSpellAction {
    private final SpellDefinition teleotherSpell;
    private final Player target;
    private final Player caster;

    public TeleotherSpellAction(Player player, SpellDefinition spellDefinition, SpellDefinition spellDefinition2, Player player2, Player player3) {
        super(player, spellDefinition, (byte)0);
        this.teleotherSpell = spellDefinition2;
        this.target = player2;
        this.caster = player3;
    }

    @Override
    public final boolean prepareCast() {
        switch (this.teleotherSpell) {
            case TELEOTHER_CAMELOT: {
                if (!this.target.isMember()) {
                    Player player = this.caster;
                    player.packetSender.sendGameMessage(String.valueOf(this.target.getUsername()) + " does not have access to members content.");
                    return false;
                }
                return TeleotherSpellAction.sendTeleotherRequest(this.caster, this.target, TeleotherDestination.CAMELOT);
            }
            case TELEOTHER_LUMBRIDGE: {
                if (!this.target.isMember()) {
                    Player player = this.caster;
                    player.packetSender.sendGameMessage(String.valueOf(this.target.getUsername()) + " does not have access to members content.");
                    return false;
                }
                return TeleotherSpellAction.sendTeleotherRequest(this.caster, this.target, TeleotherDestination.LUMBRIDGE);
            }
            case TELEOTHER_FALADOR: {
                if (!this.target.isMember()) {
                    Player player = this.caster;
                    player.packetSender.sendGameMessage(String.valueOf(this.target.getUsername()) + " does not have access to members content.");
                    return false;
                }
                return TeleotherSpellAction.sendTeleotherRequest(this.caster, this.target, TeleotherDestination.FALADOR);
            }
        }
        return true;
    }

    @Override
    public final void applyImpact(HitDefinition hitDefinition) {
    }
}

