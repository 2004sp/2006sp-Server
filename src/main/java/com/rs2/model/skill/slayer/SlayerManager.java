package com.rs2.model.skill.slayer;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.WeaponInterfaceDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.slayer.MogreSpawnTask;
import com.rs2.model.skill.slayer.SlayerAssignmentDefinition;
import com.rs2.model.skill.slayer.SlayerAssignmentRequirement;
import com.rs2.model.skill.slayer.SlayerMasterDefinition;
import com.rs2.model.skill.slayer.SlayerMonsterDefinition;
import com.rs2.model.skill.slayer.ZygomiteSpawnTask;
import com.rs2.model.skill.woodcutting.WoodcuttingHandler;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class SlayerManager {
    public static final Npc ZYGOMITE_SPAWN_A = new Npc(3346);
    public static final Npc ZYGOMITE_SPAWN_B = new Npc(3347);
    public static final Npc MOGRE_SPAWN = new Npc(2801);
    private Player player;
    public int slayerMasterId = 0;
    public String slayerTaskName = "";
    public int taskAmount;
    public static final int[] FUNGICIDE_SPRAY_ITEM_IDS = new int[]{7421, 7422, 7423, 7424, 7425, 7426, 7427, 7428, 7429, 7430};
    public static final SlayerAssignmentDefinition[] BURTHORPE_ASSIGNMENTS = new SlayerAssignmentDefinition[]{new SlayerAssignmentDefinition("banshee", 15, 50, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 15), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 20), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("lizard", 15, 50, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 22)}), new SlayerAssignmentDefinition("bat", 15, 50, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 5)}), new SlayerAssignmentDefinition("bird", 15, 50, 6), new SlayerAssignmentDefinition("bear", 15, 50, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 13)}), new SlayerAssignmentDefinition("cave bug", 10, 20, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 7)}), new SlayerAssignmentDefinition("cave slime", 10, 20, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 17), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 15)}), new SlayerAssignmentDefinition("cow", 15, 50, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 5)}), new SlayerAssignmentDefinition("crawling hand", 15, 50, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 5), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("dog", 15, 50, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 15)}), new SlayerAssignmentDefinition("dwarf", 15, 50, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 6)}), new SlayerAssignmentDefinition("ghost", 15, 50, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 13)}), new SlayerAssignmentDefinition("goblin", 15, 50, 7), new SlayerAssignmentDefinition("icefiend", 15, 20, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 20)}), new SlayerAssignmentDefinition("kalphite", 15, 50, 6, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 15)}), new SlayerAssignmentDefinition("monkey", 15, 50, 6), new SlayerAssignmentDefinition("scorpion", 15, 50, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 7)}), new SlayerAssignmentDefinition("skeleton", 15, 50, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 15)}), new SlayerAssignmentDefinition("spider", 15, 50, 6), new SlayerAssignmentDefinition("wolf", 15, 50, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 20)}), new SlayerAssignmentDefinition("zombie", 15, 50, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 10)})};
    public static final SlayerAssignmentDefinition[] CANIFIS_ASSIGNMENTS = new SlayerAssignmentDefinition[]{new SlayerAssignmentDefinition("banshee", 40, 70, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 15), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 20), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("lizard", 40, 70, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 22)}), new SlayerAssignmentDefinition("bat", 40, 70, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 5)}), new SlayerAssignmentDefinition("bear", 40, 70, 6, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 13)}), new SlayerAssignmentDefinition("cave bug", 10, 20, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 7)}), new SlayerAssignmentDefinition("cave crawler", 40, 70, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 10), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 10)}), new SlayerAssignmentDefinition("cave slime", 10, 20, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 17), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 15)}), new SlayerAssignmentDefinition("cockatrice", 40, 70, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 25), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 25), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.DEFENCE_LEVEL, 20)}), new SlayerAssignmentDefinition("crawling hand", 40, 70, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 5), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("dog", 40, 70, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 15)}), new SlayerAssignmentDefinition("ghost", 40, 70, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 13)}), new SlayerAssignmentDefinition("ghoul", 10, 20, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 25), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("hill giant", 40, 70, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 25)}), new SlayerAssignmentDefinition("hobgoblin", 40, 70, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 20)}), new SlayerAssignmentDefinition("ice warrior", 40, 70, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 45)}), new SlayerAssignmentDefinition("kalphite", 40, 70, 6, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 15)}), new SlayerAssignmentDefinition("pyrefiend", 40, 70, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 30), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 25)}), new SlayerAssignmentDefinition("rockslug", 40, 70, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 20), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 20)}), new SlayerAssignmentDefinition("scorpion", 40, 70, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 7)}), new SlayerAssignmentDefinition("skeleton", 40, 70, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 15)}), new SlayerAssignmentDefinition("wolf", 40, 70, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 20)}), new SlayerAssignmentDefinition("zombie", 40, 70, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 10)})};
    public static final SlayerAssignmentDefinition[] EDGEVILLE_DUNGEON_ASSIGNMENTS = new SlayerAssignmentDefinition[]{new SlayerAssignmentDefinition("banshee", 60, 120, 6, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 15), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 20), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("abyssal demon", 60, 120, 5, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 85), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 85), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("lizard", 60, 120, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 22)}), new SlayerAssignmentDefinition("basilisk", 60, 120, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 40), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 40), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.DEFENCE_LEVEL, 20)}), new SlayerAssignmentDefinition("blue dragon", 60, 120, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 65)}), new SlayerAssignmentDefinition("bloodveld", 60, 120, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 50), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 50), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("bronze dragon", 10, 20, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 75)}), new SlayerAssignmentDefinition("cave crawler", 60, 120, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 10), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 10)}), new SlayerAssignmentDefinition("cockatrice", 60, 120, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 25), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 25), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.DEFENCE_LEVEL, 20)}), new SlayerAssignmentDefinition("earth warrior", 40, 80, 6, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 35), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.AGILITY_LEVEL, 15)}), new SlayerAssignmentDefinition("ghoul", 10, 40, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 25), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("green dragon", 40, 80, 6, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 52)}), new SlayerAssignmentDefinition("harpie bug swarm", 60, 120, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 33), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 45), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.FIREMAKING_LEVEL, 33)}), new SlayerAssignmentDefinition("hellhound", 40, 80, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 75)}), new SlayerAssignmentDefinition("hill giant", 60, 120, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 25)}), new SlayerAssignmentDefinition("hobgoblin", 60, 120, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 20)}), new SlayerAssignmentDefinition("ice giant", 40, 80, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 50)}), new SlayerAssignmentDefinition("ice warrior", 60, 120, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 45)}), new SlayerAssignmentDefinition("infernal mage", 60, 120, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 45), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 40), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("jelly", 60, 120, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 52), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 57)}), new SlayerAssignmentDefinition("kalphite", 60, 120, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 15)}), new SlayerAssignmentDefinition("kurask", 60, 120, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 70), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 65)}), new SlayerAssignmentDefinition("nechryael", 60, 120, 5, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 80), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 85), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("lesser demon", 60, 120, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 60)}), new SlayerAssignmentDefinition("moss giant", 60, 120, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 40)}), new SlayerAssignmentDefinition("pyrefiend", 60, 120, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 30), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 25)}), new SlayerAssignmentDefinition("rockslug", 60, 120, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 20), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 20)}), new SlayerAssignmentDefinition("werewolf", 40, 80, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 60), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)})};
    public static final SlayerAssignmentDefinition[] ZANARIS_ASSIGNMENTS = new SlayerAssignmentDefinition[]{new SlayerAssignmentDefinition("aberrant specter", 110, 170, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 60), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 65), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("lizard", 110, 170, 5, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 22)}), new SlayerAssignmentDefinition("dust devil", 110, 170, 9, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 65), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 70)}), new SlayerAssignmentDefinition("abyssal demon", 110, 170, 12, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 85), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 85), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("banshee", 110, 170, 5, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 15), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 20), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("basilisk", 110, 170, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 40), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 40), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.DEFENCE_LEVEL, 20)}), new SlayerAssignmentDefinition("black demon", 110, 170, 10, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 80)}), new SlayerAssignmentDefinition("bloodveld", 110, 170, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 50), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 50), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("blue dragon", 110, 170, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 65)}), new SlayerAssignmentDefinition("cave crawler", 110, 170, 5, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 10), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 10)}), new SlayerAssignmentDefinition("cave slime", 10, 20, 6, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 17), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 15)}), new SlayerAssignmentDefinition("cockatrice", 110, 170, 6, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 25), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 25), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.DEFENCE_LEVEL, 20)}), new SlayerAssignmentDefinition("dagannoth", 110, 170, 11, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 75)}), new SlayerAssignmentDefinition("fire giant", 110, 170, 12, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 65)}), new SlayerAssignmentDefinition("gargoyle", 110, 170, 11, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 75), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 80), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("harpie bug swarm", 110, 170, 6, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 33), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 45), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.FIREMAKING_LEVEL, 33)}), new SlayerAssignmentDefinition("infernal mage", 110, 170, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 45), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 40), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("jelly", 110, 170, 10, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 52), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 57)}), new SlayerAssignmentDefinition("kalphite", 110, 170, 11, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 15)}), new SlayerAssignmentDefinition("kurask", 110, 170, 12, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 70), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 65)}), new SlayerAssignmentDefinition("lesser demon", 110, 170, 9, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 60)}), new SlayerAssignmentDefinition("skeletal wyvern", 10, 20, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 72), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 70), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 32)}), new SlayerAssignmentDefinition("rockslug", 110, 170, 5, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 20), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 20)}), new SlayerAssignmentDefinition("turoth", 110, 170, 10, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 55), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 60)}), new SlayerAssignmentDefinition("blue dragon", 110, 170, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 65)}), new SlayerAssignmentDefinition("hellhound", 110, 170, 9, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 75)}), new SlayerAssignmentDefinition("bronze dragon", 10, 20, 11, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 75)}), new SlayerAssignmentDefinition("nechryael", 110, 170, 12, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 80), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 85), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("iron dragon", 25, 45, 12, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 80)}), new SlayerAssignmentDefinition("pyrefiend", 110, 170, 6, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 30), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 25)})};
    public static final SlayerAssignmentDefinition[] SHILO_VILLAGE_ASSIGNMENTS = new SlayerAssignmentDefinition[]{new SlayerAssignmentDefinition("aberrant specter", 130, 200, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 60), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 65), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("dust devil", 130, 200, 5, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 65), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 70)}), new SlayerAssignmentDefinition("abyssal demon", 130, 200, 12, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 85), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 85), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("basilisk", 130, 200, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 40), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 40), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.DEFENCE_LEVEL, 20)}), new SlayerAssignmentDefinition("black demon", 130, 200, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 80)}), new SlayerAssignmentDefinition("blue dragon", 110, 170, 4, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 65)}), new SlayerAssignmentDefinition("black dragon", 10, 20, 9, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 80)}), new SlayerAssignmentDefinition("bloodveld", 130, 200, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 50), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 50), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("dagannoth", 130, 200, 9, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 75)}), new SlayerAssignmentDefinition("dark beast", 10, 20, 11, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 90), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 90), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("fire giant", 130, 200, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 65)}), new SlayerAssignmentDefinition("gargoyle", 130, 200, 8, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 75), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 80), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("greater demon", 130, 200, 9, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 75)}), new SlayerAssignmentDefinition("hellhound", 130, 200, 10, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 75)}), new SlayerAssignmentDefinition("iron dragon", 40, 60, 5, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 80)}), new SlayerAssignmentDefinition("kalphite", 130, 200, 9, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 15)}), new SlayerAssignmentDefinition("kurask", 130, 200, 4, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 70), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 65)}), new SlayerAssignmentDefinition("skeletal wyvern", 20, 40, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 72), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 70), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 32)}), new SlayerAssignmentDefinition("nechryael", 130, 200, 9, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(0, 80), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 85), new SlayerAssignmentRequirement(SlayerAssignmentRequirement.QUEST_STATE, 72)}), new SlayerAssignmentDefinition("steel dragon", 10, 20, 7, new SlayerAssignmentRequirement[]{new SlayerAssignmentRequirement(SlayerAssignmentRequirement.COMBAT_LEVEL, 85)})};
    public static final SlayerAssignmentDefinition[] EDGEVILLE_ASSIGNMENTS = new SlayerAssignmentDefinition[]{new SlayerAssignmentDefinition("greater demon", 100, 150, 8), new SlayerAssignmentDefinition("hellhound", 75, 125, 7), new SlayerAssignmentDefinition("red dragon", 35, 60, 3), new SlayerAssignmentDefinition("ice giant", 100, 150, 6), new SlayerAssignmentDefinition("lesser demon", 80, 120, 6), new SlayerAssignmentDefinition("moss giant", 100, 150, 4), new SlayerAssignmentDefinition("chaos elemental", 3, 35, 8)};

    public SlayerManager(Player player) {
        this.player = player;
    }

    public final void assignTaskFromMaster(int slayerMasterId) {
        SlayerMasterDefinition slayerMasterDefinition = SlayerMasterDefinition.forNpcId(slayerMasterId);
        if (slayerMasterDefinition == null) {
            return;
        }
        if (!ServerSettings.slayerEnabled) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            player = this.player;
            player.packetSender.closeInterfaces();
            return;
        }
        if (this.player.getCombatLevel() < slayerMasterDefinition.getRequiredCombatLevel()) {
            this.player.getDialogueManager().showNpcOneLineDialogue("You need a combat level of " + slayerMasterDefinition.getRequiredCombatLevel() + " to recieve a task from me.", 588);
            return;
        }
        if (slayerMasterDefinition.getNpcId() == 1599 && this.player.getSkillManager().getCurrentLevels()[18] < 50) {
            this.player.getDialogueManager().showNpcOneLineDialogue("You need a slayer level of 50 to recieve a task from me.", 588);
            return;
        }
        SlayerAssignmentDefinition slayerAssignmentDefinition = SlayerManager.chooseWeightedAssignment(slayerMasterDefinition.getAssignments(), this.player);
        if (slayerAssignmentDefinition == null) {
            Player player = this.player;
            player.packetSender.sendGameMessage("No tasks available currently.");
            player = this.player;
            player.packetSender.closeInterfaces();
            return;
        }
        this.slayerMasterId = slayerMasterId;
        this.slayerTaskName = slayerAssignmentDefinition.taskName;
        this.taskAmount = GameUtil.randomBetweenInclusive(slayerAssignmentDefinition.minAmount, slayerAssignmentDefinition.maxAmount);
        this.player.getDialogueManager().showNpcOneLineDialogue("Your new task is to kill " + this.taskAmount + " " + this.slayerTaskName + "s.", 588);
    }

    private static SlayerAssignmentDefinition chooseWeightedAssignment(SlayerAssignmentDefinition[] slayerAssignmentDefinitionArray, Player player) {
        ArrayList<SlayerAssignmentDefinition> arrayList = new ArrayList<SlayerAssignmentDefinition>();
        SlayerAssignmentDefinition[] slayerAssignmentDefinitionArray2 = slayerAssignmentDefinitionArray;
        int length = slayerAssignmentDefinitionArray.length;
        int index = 0;
        while (index < length) {
            SlayerAssignmentDefinition slayerAssignmentDefinition = slayerAssignmentDefinitionArray2[index];
            if (slayerAssignmentDefinition.isAvailableFor(player)) {
                arrayList.add(slayerAssignmentDefinition);
            }
            ++index;
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        double value = 0.0;
        for (SlayerAssignmentDefinition slayerAssignmentDefinition : arrayList) {
            value += (double)slayerAssignmentDefinition.weight;
        }
        double[] doubleValues = new double[arrayList.size()];
        int index2 = 0;
        while (index2 < arrayList.size()) {
            SlayerAssignmentDefinition slayerAssignmentDefinition = (SlayerAssignmentDefinition)arrayList.get(index2);
            double value2 = slayerAssignmentDefinition.weight;
            doubleValues[index2] = value2 / value;
            ++index2;
        }
        index2 = GameUtil.rollProbabilityIndex(doubleValues);
        return (SlayerAssignmentDefinition)arrayList.get(index2);
    }

    public final void completeTask() {
        this.slayerMasterId = 0;
        this.slayerTaskName = "none";
        this.taskAmount = 0;
        Player player = this.player;
        player.packetSender.sendGameMessage("You have finished your slayer task! Speak to a slayer master to get a new one.");
    }

    public final void recordKill(Npc npc) {
        if (this.slayerMasterId <= 0 || this.slayerTaskName.equalsIgnoreCase("")) {
            return;
        }
        if (npc.getDefinition().getHitpoints() == 0) {
            return;
        }
        if (npc.getNpcId() == 1483 || npc.getNpcId() == 645) {
            return;
        }
        if (npc.getNpcId() == 912 || npc.getNpcId() == 913 || npc.getNpcId() == 914 || npc.getNpcId() == 944) {
            return;
        }
        if (npc.getNpcId() == 1886) {
            return;
        }
        String definition = npc.getDefinition().getName().toLowerCase();
        if (!(this.slayerTaskName.equalsIgnoreCase("werewolf") && (definition.contains("wolfman") || definition.contains("wolfwoman")) || this.slayerTaskName.equalsIgnoreCase("bird") && (definition.contains("chicken") || definition.contains("bird")) || this.slayerTaskName.equalsIgnoreCase("spiritual creature") && (definition.contains("spiritual ranger") || definition.contains("spiritual warrior") || definition.contains("spiritual mage")) || definition.contains(this.slayerTaskName))) {
            return;
        }
        if (this.slayerMasterId == 3887) {
            if (this.player.isInWilderness()) {
                --this.taskAmount;
                int definition2 = npc.getDefinition().getHitpoints() << 1;
                if (this.player.skulled) {
                    definition2 <<= 1;
                }
                this.player.getSkillManager().addExperience(18, definition2);
            }
        } else {
            --this.taskAmount;
            this.player.getSkillManager().addExperience(18, npc.getDefinition().getHitpoints());
        }
        if (this.taskAmount == 0) {
            this.completeTask();
        }
    }

    public final boolean canAttackSlayerMonster(Npc npc) {
        SlayerMonsterDefinition slayerMonsterDefinition = SlayerMonsterDefinition.forName(npc.getDefinition().getName().toLowerCase());
        if (slayerMonsterDefinition == null) {
            return true;
        }
        if (this.player.getSkillManager().getCurrentLevels()[18] < slayerMonsterDefinition.getRequiredSlayerLevel()) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You need a slayer level of " + slayerMonsterDefinition.getRequiredSlayerLevel() + " to attack this monster.");
            return false;
        }
        if (slayerMonsterDefinition.getMonsterName().equalsIgnoreCase("kurask") || slayerMonsterDefinition.getMonsterName().equalsIgnoreCase("turoth")) {
            if (this.player.getWeaponProfile() != null && (this.player.getWeaponProfile().getInterfaceDefinition() != null && this.player.getWeaponProfile().getInterfaceDefinition() == WeaponInterfaceDefinition.LONG_BOW || this.player.getWeaponProfile().getInterfaceDefinition() == WeaponInterfaceDefinition.BOW) && this.player.getEquipmentManager().getContainer().getItemAt(13) != null && this.player.getEquipmentManager().getContainer().getItemAt(13).getId() == 4160) {
                return true;
            }
            if (this.player.getQueuedCombatSpell() == SpellDefinition.MAGIC_DART || this.player.getAutocastSpell() == SpellDefinition.MAGIC_DART) {
                return true;
            }
        }
        if (slayerMonsterDefinition.getRequiredItemIds() == null) {
            return true;
        }
        if (slayerMonsterDefinition.getRequirementMode().equals("use") || slayerMonsterDefinition.getRequirementMode().equals("none")) {
            return true;
        }
        ItemStack[] itemStackArray = this.player.getEquipmentManager().getContainer().getRawItems();
        int length = itemStackArray.length;
        int index = 0;
        while (index < length) {
            ItemStack itemStack = itemStackArray[index];
            if (itemStack != null) {
                int[] requiredItemIds = slayerMonsterDefinition.getRequiredItemIds();
                int length2 = requiredItemIds.length;
                int index2 = 0;
                while (index2 < length2) {
                    int value = requiredItemIds[index2];
                    if (itemStack.getId() == value) {
                        return true;
                    }
                    ++index2;
                }
            }
            ++index;
        }
        Player player = this.player;
        player.packetSender.sendGameMessage("You don't have the required protection against this kind of monster!");
        return false;
    }

    public final boolean requiresFinishingItem(Npc entity, boolean itemId) {
        SlayerMonsterDefinition slayerMonsterDefinition = SlayerMonsterDefinition.forName(entity.getDefinition().getName().toLowerCase());
        if (slayerMonsterDefinition == null) {
            return false;
        }
        if (slayerMonsterDefinition.getRequirementMode().equals("use") && entity.getCurrentHitpoints() > 1) {
            return true;
        }
        int maxHitpoints = entity.getMaxHitpoints() / 10;
        if (slayerMonsterDefinition.getRequirementMode().equals("use") && entity.getCurrentHitpoints() <= maxHitpoints) {
            this.player.packetSender.sendGameMessage("The " + GameUtil.capitalizeLowercaseFirst(slayerMonsterDefinition.getMonsterName()) + " is on its last legs! Finish it quickly!");
            return true;
        }
        return false;
    }

    public final boolean handleZygomiteSpawn(Npc npc) {
        int position = npc.getPosition().getX();
        int position2 = npc.getPosition().getY();
        switch (npc.getDefinition().getId()) {
            case 3344: 
            case 3345: {
                this.player.getUpdateState().setAnimation(827);
                CycleEventHandler.getInstance().schedule(this.player, new ZygomiteSpawnTask(this, npc, position, position2), 2);
                return true;
            }
        }
        return false;
    }

    public final boolean handleMogreLure(int value4, int value22, int value32) {
        if (!GameUtil.isWithinDistance(this.player.getPosition().getX(), this.player.getPosition().getY(), value22, value32, 7)) {
            return false;
        }
        switch (value4) {
            case 10087: 
            case 10088: 
            case 10089: {
                if (!this.player.getInventoryManager().containsItem(6664)) {
                    Player player = this.player;
                    player.packetSender.sendGameMessage("You don't have anything to lure with.");
                    return false;
                }
                this.player.getMovementQueue().clear();
                this.player.getUpdateState().setFacePosition(new Position(value22, value32));
                this.player.getUpdateState().setAnimation(806);
                Player player = this.player;
                player.packetSender.sendGameMessage("You throw the shuddering vial into the water...");
                this.player.getInventoryManager().removeItem(new ItemStack(6664));
                new WoodcuttingHandler(this.player.getPosition(), 3, new Position(value22, value32), 0, new ProjectileDefinition(29, ProjectileTiming.MAGIC)).sendProjectileToNearbyPlayers();
                this.player.setActionLocked(true);
                CycleEventHandler.getInstance().schedule(this.player, new MogreSpawnTask(this), 5);
                return true;
            }
        }
        return false;
    }

    public final void useFinishingItemOnMonster(Npc npc, int itemId) {
        SlayerMonsterDefinition slayerMonsterDefinition = SlayerMonsterDefinition.forName(npc.getDefinition().getName().toLowerCase());
        if (slayerMonsterDefinition == null) {
            return;
        }
        int maxHitpoints = npc.getMaxHitpoints() / 10;
        if (!slayerMonsterDefinition.getRequirementMode().equals("use") || npc.getCurrentHitpoints() > maxHitpoints) {
            return;
        }
        int[] requiredItemIds = slayerMonsterDefinition.getRequiredItemIds();
        if (requiredItemIds == null) {
            return;
        }
        maxHitpoints = requiredItemIds.length;
        int index = 0;
        while (index < maxHitpoints) {
            int requiredItemId = requiredItemIds[index];
            if (requiredItemId == itemId) {
                this.player.getUpdateState().setAnimation(832);
                npc.setDead(true);
                CombatManager.handleDeath(npc);
                if (itemId != 4162) {
                    this.player.getInventoryManager().removeItem(new ItemStack(itemId));
                    this.player.getInventoryManager().addItem(new ItemStack(itemId != 7432 ? (itemId < 7421 || itemId > 7430 ? -1 : itemId + 1) : 7421));
                }
                return;
            }
            ++index;
        }
    }

    public final boolean combineFungicideSpray(int value3, int value22) {
        if (value3 == 7432 && value22 == 7431 || value3 == 7431 && value22 == 7432) {
            this.player.getInventoryManager().removeItem(new ItemStack(7432));
            this.player.getInventoryManager().removeItem(new ItemStack(7431));
            this.player.getInventoryManager().addItem(new ItemStack(7421));
            return true;
        }
        return false;
    }

    static Player getPlayer(SlayerManager slayerManager) {
        return slayerManager.player;
    }
}
