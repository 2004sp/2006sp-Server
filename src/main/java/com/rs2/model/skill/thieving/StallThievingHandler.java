package com.rs2.model.skill.thieving;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.thieving.StallDefinition;
import com.rs2.model.skill.thieving.StallThievingTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

public final class StallThievingHandler {
    private static final Random random = new Random();
    private static final int OWNER_MEMORY_TICKS = (int)GameUtil.secondsToTicks(20L * 60L);
    private static final Map<Player, EnumMap<StallDefinition, Integer>> recentThefts =
            new WeakHashMap<Player, EnumMap<StallDefinition, Integer>>();

    public static void recordSuccessfulTheft(Player player, StallDefinition stall) {
        EnumMap<StallDefinition, Integer> thefts = recentThefts.get(player);
        if (thefts == null) {
            thefts = new EnumMap<StallDefinition, Integer>(StallDefinition.class);
            recentThefts.put(player, thefts);
        }
        thefts.put(stall, World.tickCount);
    }

    public static boolean hasRecentlyStolenFromOwner(Player player, int npcId) {
        StallDefinition stall;
        switch (npcId) {
            case 571: stall = StallDefinition.CAKE_STALL; break;
            case 574: stall = StallDefinition.SILK_STALL; break;
            case 573: stall = StallDefinition.FUR_STALL; break;
            case 569: stall = StallDefinition.SILVER_STALL; break;
            case 572: stall = StallDefinition.SPICE_STALL; break;
            case 570: stall = StallDefinition.GEM_STALL; break;
            default: return false;
        }
        EnumMap<StallDefinition, Integer> thefts = recentThefts.get(player);
        Integer theftTick = thefts == null ? null : thefts.get(stall);
        return theftTick != null && World.tickCount - theftTick < OWNER_MEMORY_TICKS;
    }

    public static int getEmptyStallObjectId(int objectId) {
        if (objectId >= 4874 && objectId <= 4878) {
            return 4276;
        }
        if (objectId == 6163) {
            return 6573;
        }
        return 634;
    }

    public static boolean handleStallThieving(Player player, int value9, int value22, int value32) {
        StallDefinition stallDefinition;
        StallDefinition stallDefinition2;
        Object value4;
        int value5;
        handleStallThievingControlExit1: {
            int value6 = value9;
            StallDefinition[] stallDefinitionArray = StallDefinition.values();
            value5 = stallDefinitionArray.length;
            int index = 0;
            while (index < value5) {
                stallDefinition = stallDefinitionArray[index];
                int[] objectIds = stallDefinition.getObjectIds();
                int length = objectIds.length;
                int index2 = 0;
                while (index2 < length) {
                    int value7 = objectIds[index2];
                    if (value6 == value7) {
                        stallDefinition2 = stallDefinition;
                        break handleStallThievingControlExit1;
                    }
                    ++index2;
                }
                ++index;
            }
            stallDefinition2 = stallDefinition = null;
        }
        if (stallDefinition2 == null) {
            return false;
        }
        if (!ServerSettings.thievingEnabled) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (!player.isMember()) {
            player.packetSender.sendGameMessage("You need a members account to access members content.");
            return true;
        }
        if (ServerSettings.freeToPlayWorld) {
            player.packetSender.sendGameMessage("You need to be in members world to access members content.");
            return true;
        }
        ObjectManager.getInstance();
        if (ObjectManager.findDynamicObjectAt(value22, value32, player.getPosition().getPlane()) != null) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("Too late, the items are gone.");
            return true;
        }
        if (!SkillActionHelper.checkSkillRequirement(player, 17, stallDefinition.getRequiredLevel(), "steal from this stall")) {
            return true;
        }
        value4 = stallDefinition.getRewards()[random.nextInt(stallDefinition.getRewards().length)];
        if (!player.getInventoryManager().canAddItem((ItemStack)value4)) {
            return true;
        }
        Entity entity = player;
        ((Player)entity).packetSender.sendGameMessage("You attempt to steal from the stall..");
        Npc[] npcArray = World.getNpcs();
        int length2 = npcArray.length;
        value5 = 0;
        while (value5 < length2) {
            entity = npcArray[value5];
            if (entity != null && !entity.isDead() && !entity.hasCombatTarget()
                    && entity.getPosition().getPlane() == player.getPosition().getPlane()
                    && stallDefinition.isProtectingNpc(((Npc)entity).getDefinition().getName())
                    && GameUtil.isWithinDistance(entity.getPosition().getX(), entity.getPosition().getY(), value22, value32, 4)) {
                entity.getUpdateState().setForcedTextAndMarkUpdated("Hey! Get away from there!");
                if (((Npc)entity).getDefinition().isAttackable()) {
                    CombatManager.startCombat(entity, player);
                }
                return true;
            }
            ++value5;
        }
        int value8 = player.nextActionSequence();
        player.setActionLocked(false);
        player.setHideHeldItemsInAppearance(true);
        player.getUpdateState().setAnimation(832);
        player.setActiveCycleEvent(new StallThievingTask(player, value8, value9, value22, value32, (ItemStack)value4, stallDefinition));
        CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 2);
        return true;
    }
}

