package com.rs2.model.skill.magic;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.AbyssTeleportTask;
import com.rs2.model.skill.magic.DelayedTeleportTask;
import com.rs2.model.skill.magic.MagicTeleportTask;
import com.rs2.model.skill.magic.ScriptedTeleportTask;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.model.skill.magic.StandardTeleportTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameplayTrace;

public final class TeleportManager {
    private Player player;
    public static final Position RESPAWN_TELEPORT_POSITION = new Position(ServerSettings.respawnX, ServerSettings.respawnY, ServerSettings.respawnPlane);
    public static final Position EDGEVILLE_TELEPORT_POSITION = new Position(3087, 3495);
    public static final Position KARAMJA_TELEPORT_POSITION = new Position(2912, 3170);
    public static final Position DRAYNOR_VILLAGE_TELEPORT_POSITION = new Position(3104, 3249);
    public static final Position AL_KHARID_TELEPORT_POSITION = new Position(3293, 3162);
    public static final Position CASTLE_WARS_TELEPORT_POSITION = new Position(2441, 3090);
    public static final Position DUEL_ARENA_TELEPORT_POSITION = new Position(3317, 3233);
    public static final Position BURTHORPE_TELEPORT_POSITION = new Position(2207, 4941);

    public TeleportManager(Player player) {
        this.player = player;
    }

    public final boolean castSpellbookTeleport(Position position) {
        if (this.player.getEnchantmentChamberController().isInsideChamber() || this.player.getAlchemistPlaygroundController().isInsidePlayground() || this.player.getCreatureGraveyardController().isInsideGraveyard() || this.player.getTelekineticTheatreController().isInsideTheatre()) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't teleport out of here.");
            return false;
        }
        if (this.player.isInWilderness() && this.player.getWildernessLevel() > 20) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't teleport above level 20 in the wilderness.");
            return false;
        }
        if (this.player.isTeleblocked()) {
            Player player = this.player;
            player.packetSender.sendGameMessage("A magical force prevents you from teleporting.");
            return false;
        }
        if (this.player.isInTeleportRestrictedArea()) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't teleport from here.");
            return false;
        }
        this.startMagicTeleportTask(position.getX(), position.getY(), position.getPlane(), this.player.getSpellbook() == Spellbook.ANCIENT);
        return true;
    }

    public final boolean castItemTeleport(Position position) {
        if (this.player.getEnchantmentChamberController().isInsideChamber() || this.player.getAlchemistPlaygroundController().isInsidePlayground() || this.player.getCreatureGraveyardController().isInsideGraveyard() || this.player.getTelekineticTheatreController().isInsideTheatre()) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't teleport out of here.");
            return false;
        }
        if (this.player.isInWilderness() && this.player.getWildernessLevel() > 30) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't teleport above level 30 in the wilderness.");
            return false;
        }
        if (this.player.isTeleblocked()) {
            Player player = this.player;
            player.packetSender.sendGameMessage("A magical force prevents you from teleporting.");
            return false;
        }
        if (this.player.isInTeleportRestrictedArea()) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't teleport from here.");
            return false;
        }
        this.player.getUpdateState().setAnimation(714);
        this.player.getUpdateState().setGraphicHeight100(301);
        Player player = this.player;
        player.packetSender.sendSoundEffect(202, 1, 0);
        this.startMagicTeleportTask(position.getX(), position.getY(), position.getPlane(), false);
        return true;
    }

    public final void startScriptedTeleport(int value8, int value22, int value32, String text2, int value42, int value52, int value62, int value72) {
        this.player.setActionLocked(true);
        this.player.getAttributes().put("canTakeDamage", Boolean.FALSE);
        CycleEventHandler.getInstance().schedule(this.player, new ScriptedTeleportTask(this, 5, 804, 68, -1, value8, value22, 0, null), 1);
    }

    public final void startStandardTeleport(int value4, int value22, int value32, String text2) {
        this.player.setActionLocked(true);
        this.player.getAttributes().put("canTakeDamage", Boolean.FALSE);
        CycleEventHandler.getInstance().schedule(this.player, new StandardTeleportTask(this, value4, value22, value32, text2), 1);
    }

    private void startMagicTeleportTask(int value4, int value22, int value32, boolean enabled2) {
        if (this.player.isTeleblocked() || this.player.isInTeleportRestrictedArea()) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't teleport out of here!");
            return;
        }
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("magic teleport scheduled player=" + GameplayTrace.describe(this.player) + " destination=" + value4 + "," + value22 + "," + value32 + " ancient=" + enabled2);
        }
        this.player.setActionLocked(true);
        this.player.getAttributes().put("canTakeDamage", Boolean.FALSE);
        CycleEventHandler.getInstance().schedule(this.player, new MagicTeleportTask(this, enabled2, value4, value22, value32), 1);
    }

    public final void startDelayedTeleport(int delayTicks, int value22, int value32) {
        this.startDelayedTeleport(delayTicks, value22, value32, true, null);
    }

    public final void startDelayedTeleport(int delayTicks, int value22, int value32, boolean delayTicks2, String delayTicks3) {
        if (this.player.isTeleblocked()) {
            Player player = this.player;
            player.packetSender.sendGameMessage("A magical force prevents you from teleporting.");
            return;
        }
        if (this.player.isInTeleportRestrictedArea()) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't teleport from here.");
            return;
        }
        if (delayTicks2) {
            this.player.getUpdateState().setGraphicHeight100(342);
        }
        this.player.getUpdateState().setAnimation(1816);
        this.player.setActionLocked(true);
        this.player.getAttributes().put("canTakeDamage", Boolean.FALSE);
        CycleEventHandler.getInstance().schedule(this.player, new DelayedTeleportTask(this, delayTicks, value22, value32, delayTicks3), 1);
    }

    public final void startAbyssTeleport(int value4, int value22, int value32) {
        if (this.player.isTeleblocked() || this.player.isInTeleportRestrictedArea()) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't teleport out of here!");
            return;
        }
        this.player.getUpdateState().setGraphicHeight100(110);
        CycleEventHandler.getInstance().schedule(this.player, new AbyssTeleportTask(this, value4, value22, value32), 1);
    }

    static Player getPlayer(TeleportManager teleportManager) {
        return teleportManager.player;
    }
}

