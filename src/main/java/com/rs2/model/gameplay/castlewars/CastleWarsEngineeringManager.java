package com.rs2.model.gameplay.castlewars;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.util.GameUtil;
import com.rs2.util.path.ProjectileCollisionMap;
import com.rs2.util.path.WalkingCollisionMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

public final class CastleWarsEngineeringManager {
    public static final int ROCK_ITEM_ID = 4043;
    public static final int EXPLOSIVE_POTION_ID = 4045;
    public static final int TOOLKIT_ID = 4051;
    public static final int BARRICADE_ITEM_ID = 4053;
    public static final int CLIMBING_ROPE_ITEM_ID = 4047;
    public static final int BRONZE_PICKAXE_ID = 1265;
    public static final int TINDERBOX_ITEM_ID = 590;
    public static final int EMPTY_BUCKET_ITEM_ID = 1925;
    public static final int BUCKET_OF_WATER_ITEM_ID = 1929;

    public static final int BARRICADE_OBJECT_SARADOMIN = 4421;
    public static final int BARRICADE_OBJECT_ZAMORAK = 4422;
    public static final int COLLAPSED_ROCK_OBJECT_ID = 4437;
    public static final int CLEARED_ROCK_OBJECT_ID = 4439;
    public static final int CLIMBING_ROPE_OBJECT_ID = 4444;
    public static final int BATTLEMENT_OBJECT_ID = 4446;
    public static final int BATTLEMENT_OBJECT_ID_ALT = 4447;

    public static final int ZAMORAK_CATAPULT_ID = 4381;
    public static final int SARADOMIN_CATAPULT_ID = 4382;
    public static final int ZAMORAK_DAMAGED_CATAPULT_ID = 4385;
    public static final int SARADOMIN_DAMAGED_CATAPULT_ID = 4386;

    public static final int SARADOMIN_MAIN_DOOR_LEFT_CLOSED_ID = 4423;
    public static final int SARADOMIN_MAIN_DOOR_RIGHT_CLOSED_ID = 4424;
    public static final int SARADOMIN_MAIN_DOOR_LEFT_OPEN_ID = 4425;
    public static final int SARADOMIN_MAIN_DOOR_RIGHT_OPEN_ID = 4426;
    public static final int ZAMORAK_MAIN_DOOR_LEFT_CLOSED_ID = 4427;
    public static final int ZAMORAK_MAIN_DOOR_RIGHT_CLOSED_ID = 4428;
    public static final int ZAMORAK_MAIN_DOOR_LEFT_OPEN_ID = 4429;
    public static final int ZAMORAK_MAIN_DOOR_RIGHT_OPEN_ID = 4430;
    public static final int SARADOMIN_MAIN_DOOR_RIGHT_BROKEN_ID = 4431;
    public static final int SARADOMIN_MAIN_DOOR_LEFT_BROKEN_ID = 4432;
    public static final int ZAMORAK_MAIN_DOOR_LEFT_BROKEN_ID = 4433;
    public static final int ZAMORAK_MAIN_DOOR_RIGHT_BROKEN_ID = 4434;

    public static final int SARADOMIN_SIDE_DOOR_CLOSED_ID = 4465;
    public static final int SARADOMIN_SIDE_DOOR_OPEN_ID = 4466;
    public static final int ZAMORAK_SIDE_DOOR_CLOSED_ID = 4467;
    public static final int ZAMORAK_SIDE_DOOR_OPEN_ID = 4468;

    public static final Position SARADOMIN_CATAPULT = new Position(2413, 3088, 0);
    public static final Position ZAMORAK_CATAPULT = new Position(2384, 3117, 0);

    private static final int MAIN_DOOR_MAX_HITPOINTS = 100;
    private static final int MAIN_DOOR_PLANE = 0;
    private static final int THIEVING_SKILL_INDEX = 17;
    private static final int SIDE_DOOR_MIN_THIEVING_LEVEL = 1;
    private static final int SIDE_DOOR_ROLL_SIZE = 256;
    private static final int SIDE_DOOR_LEVEL_ONE_THRESHOLD = 16;
    private static final int SIDE_DOOR_LEVEL_NINETY_NINE_THRESHOLD = 256;
    private static final long GAME_TICK_MILLIS = 600L;
    private static final int CLIMBING_ROPE_LIFETIME_TICKS = 100;
    private static final int CLIMBING_ROPE_PLANE = 0;
    private static final int CLIMBING_ROPE_DESTINATION_PLANE = 1;
    private static final long BARRICADE_BURN_DURATION_MILLIS = 20L * 1000L;

    private static final Position[] ROCKSLIDE_POSITIONS = new Position[]{
        new Position(2391, 9501, 0),
        new Position(2400, 9512, 0),
        new Position(2409, 9503, 0),
        new Position(2401, 9494, 0)
    };

    private static final Map<String, BarricadeState> barricades = new HashMap<String, BarricadeState>();
    private static final Map<String, ClimbingRopeState> climbingRopes =
            new HashMap<String, ClimbingRopeState>();
    private static final boolean[] rockslideCollapsed = new boolean[]{true, true, true, true};
    private static boolean saradominCatapultOperational = true;
    private static boolean zamorakCatapultOperational = true;
    private static long saradominCatapultReadyAt;
    private static long zamorakCatapultReadyAt;
    private static final Map<Player, Long> mainDoorAttackReadyAt =
            new IdentityHashMap<Player, Long>();
    private static final MainDoorState saradominMainDoor = new MainDoorState(
            CastleWarsManager.Team.SARADOMIN,
            new MainDoorLeaf(SARADOMIN_MAIN_DOOR_LEFT_CLOSED_ID,
                    SARADOMIN_MAIN_DOOR_LEFT_OPEN_ID, SARADOMIN_MAIN_DOOR_LEFT_BROKEN_ID,
                    2426, 3088, 3, 2426, 3087, 0),
            new MainDoorLeaf(SARADOMIN_MAIN_DOOR_RIGHT_CLOSED_ID,
                    SARADOMIN_MAIN_DOOR_RIGHT_OPEN_ID, SARADOMIN_MAIN_DOOR_RIGHT_BROKEN_ID,
                    2427, 3088, 3, 2427, 3087, 2));
    private static final MainDoorState zamorakMainDoor = new MainDoorState(
            CastleWarsManager.Team.ZAMORAK,
            new MainDoorLeaf(ZAMORAK_MAIN_DOOR_LEFT_CLOSED_ID,
                    ZAMORAK_MAIN_DOOR_LEFT_OPEN_ID, ZAMORAK_MAIN_DOOR_LEFT_BROKEN_ID,
                    2373, 3119, 1, 2373, 3120, 2),
            new MainDoorLeaf(ZAMORAK_MAIN_DOOR_RIGHT_CLOSED_ID,
                    ZAMORAK_MAIN_DOOR_RIGHT_OPEN_ID, ZAMORAK_MAIN_DOOR_RIGHT_BROKEN_ID,
                    2372, 3119, 1, 2372, 3120, 0));
    private static final SideDoorState saradominSideDoor = new SideDoorState(
            CastleWarsManager.Team.SARADOMIN,
            SARADOMIN_SIDE_DOOR_CLOSED_ID, SARADOMIN_SIDE_DOOR_OPEN_ID,
            2415, 3073, 0, 2414, 3073, 1);
    private static final SideDoorState zamorakSideDoor = new SideDoorState(
            CastleWarsManager.Team.ZAMORAK,
            ZAMORAK_SIDE_DOOR_CLOSED_ID, ZAMORAK_SIDE_DOOR_OPEN_ID,
            2384, 3134, 2, 2385, 3134, 3);

    private CastleWarsEngineeringManager() {
    }

    public static void resetForGame() {
        clearAllBarricades();
        clearAllClimbingRopes();
        for (int i = 0; i < ROCKSLIDE_POSITIONS.length; ++i) {
            setRockslideState(i, true);
        }
        setCatapultOperational(CastleWarsManager.Team.SARADOMIN, true);
        setCatapultOperational(CastleWarsManager.Team.ZAMORAK, true);
        resetMainDoors();
        resetSideDoors();
        saradominCatapultReadyAt = 0L;
        zamorakCatapultReadyAt = 0L;
        mainDoorAttackReadyAt.clear();
    }

    public static void cleanupAfterGame() {
        clearAllBarricades();
        clearAllClimbingRopes();
        for (int i = 0; i < ROCKSLIDE_POSITIONS.length; ++i) {
            setRockslideState(i, true);
        }
        setCatapultOperational(CastleWarsManager.Team.SARADOMIN, true);
        setCatapultOperational(CastleWarsManager.Team.ZAMORAK, true);
        resetMainDoors();
        resetSideDoors();
        mainDoorAttackReadyAt.clear();
    }

    public static boolean handleSupplyTable(Player player, int objectId) {
        if (!CastleWarsManager.isInGame(player)) {
            return false;
        }
        int itemId;
        String message;
        if (objectId == 4459) {
            itemId = TOOLKIT_ID;
            message = "You take a toolkit.";
        } else if (objectId == 4460) {
            itemId = ROCK_ITEM_ID;
            message = "You take a rock for the catapult.";
        } else if (objectId == 4461) {
            itemId = BARRICADE_ITEM_ID;
            message = "You take a barricade.";
        } else if (objectId == 4462) {
            itemId = CLIMBING_ROPE_ITEM_ID;
            message = "You take a climbing rope.";
        } else if (objectId == 4463) {
            itemId = EXPLOSIVE_POTION_ID;
            message = "You take an explosive potion.";
        } else if (objectId == 4464) {
            itemId = BRONZE_PICKAXE_ID;
            message = "You take a bronze pickaxe.";
        } else {
            return false;
        }
        if (player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            player.getPacketSender().sendGameMessage("Not enough space in your inventory.");
            return true;
        }
        player.getInventoryManager().addItem(new ItemStack(itemId, 1));
        player.getUpdateState().setAnimation(881);
        player.getPacketSender().sendGameMessage(message);
        return true;
    }

    public static boolean handleItemOnObject(Player player, int itemId, int objectId,
                                             int objectX, int objectY, int objectPlane) {
        if (!CastleWarsManager.isInGame(player)) {
            return false;
        }

        if (itemId == CLIMBING_ROPE_ITEM_ID
                && (objectId == BATTLEMENT_OBJECT_ID || objectId == BATTLEMENT_OBJECT_ID_ALT)) {
            return attachClimbingRope(player, objectId, objectX, objectY, objectPlane);
        }

        if (objectId == BARRICADE_OBJECT_SARADOMIN || objectId == BARRICADE_OBJECT_ZAMORAK) {
            Position barricadePosition = new Position(objectX, objectY, objectPlane);
            if (itemId == TINDERBOX_ITEM_ID) {
                return igniteBarricade(player, barricadePosition);
            }
            if (itemId == BUCKET_OF_WATER_ITEM_ID) {
                return extinguishBarricade(player, barricadePosition);
            }
        }

        if (itemId == EXPLOSIVE_POTION_ID) {
            if (objectId == BARRICADE_OBJECT_SARADOMIN || objectId == BARRICADE_OBJECT_ZAMORAK) {
                return destroyBarricadeWithExplosive(player,
                        new Position(objectX, objectY, objectPlane));
            }
            int rockIndex = findRockslideIndex(objectX, objectY);
            if ((objectId == COLLAPSED_ROCK_OBJECT_ID || objectId == CLEARED_ROCK_OBJECT_ID)
                    && rockIndex >= 0) {
                return useRockslideTool(player, rockIndex, true);
            }
            if (objectId == SARADOMIN_CATAPULT_ID || objectId == ZAMORAK_CATAPULT_ID) {
                return sabotageEnemyCatapult(player);
            }
        }

        if (itemId == BRONZE_PICKAXE_ID
                && (objectId == COLLAPSED_ROCK_OBJECT_ID || objectId == CLEARED_ROCK_OBJECT_ID)) {
            int rockIndex = findRockslideIndex(objectX, objectY);
            return rockIndex >= 0 && useRockslideTool(player, rockIndex, false);
        }

        if (itemId == TOOLKIT_ID && isBrokenMainDoorObject(objectId)) {
            return repairMainDoor(player, objectId, objectX, objectY);
        }

        if (itemId == TOOLKIT_ID
                && (objectId == SARADOMIN_DAMAGED_CATAPULT_ID
                || objectId == ZAMORAK_DAMAGED_CATAPULT_ID)) {
            CastleWarsManager.Team team = CastleWarsManager.getGameTeam(player);
            CastleWarsManager.Team targetTeam = objectId == SARADOMIN_DAMAGED_CATAPULT_ID
                    ? CastleWarsManager.Team.SARADOMIN : CastleWarsManager.Team.ZAMORAK;
            Position target = targetTeam == CastleWarsManager.Team.SARADOMIN
                    ? SARADOMIN_CATAPULT : ZAMORAK_CATAPULT;
            if (team == targetTeam && GameUtil.getDistance(player.getPosition(), target) <= 3) {
                setCatapultOperational(targetTeam, true);
                player.getUpdateState().setAnimation(898);
                player.getPacketSender().sendGameMessage("You repair your team's catapult.");
                return true;
            }
        }
        return false;
    }

    public static boolean operateCatapult(Player player, int objectId) {
        CastleWarsManager.Team team = CastleWarsManager.getGameTeam(player);
        if (team == null) {
            return false;
        }
        if (objectId == SARADOMIN_CATAPULT_ID && team != CastleWarsManager.Team.SARADOMIN
                || objectId == ZAMORAK_CATAPULT_ID && team != CastleWarsManager.Team.ZAMORAK) {
            player.getPacketSender().sendGameMessage("Your team can't use this catapult.");
            return true;
        }
        if (objectId != SARADOMIN_CATAPULT_ID && objectId != ZAMORAK_CATAPULT_ID) {
            return false;
        }
        if (!fireCatapult(player)) {
            if (player.getInventoryManager().getItemAmount(ROCK_ITEM_ID) <= 0) {
                player.getPacketSender().sendGameMessage("You need a rock to fire the catapult.");
            } else {
                player.getPacketSender().sendGameMessage("The catapult isn't ready to fire yet.");
            }
        } else {
            player.getPacketSender().sendGameMessage("You fire the catapult!");
        }
        return true;
    }

    public static boolean handleSideDoor(Player player, int objectId, int objectX, int objectY) {
        SideDoorState door = findSideDoor(objectId, objectX, objectY);
        if (door == null) {
            return false;
        }

        CastleWarsManager.Team team = CastleWarsManager.getGameTeam(player);
        if (team == null) {
            player.getPacketSender().sendGameMessage("The side doors can only be used during a Castle Wars game.");
            return true;
        }

        if (team == door.team) {
            setSideDoorOpen(door, !door.open);
            player.getPacketSender().sendSoundEffect(318, 1, 0);
            player.getPacketSender().sendGameMessage(door.open
                    ? "You unlock the side door."
                    : "You lock the side door.");
            return true;
        }

        if (door.open) {
            player.getPacketSender().sendGameMessage("Only the defending team can lock this door.");
            return true;
        }

        if (!ServerSettings.thievingEnabled) {
            player.getPacketSender().sendGameMessage("This skill is currently disabled.");
            return true;
        }

        int thievingLevel = player.getSkillManager().getCurrentLevels()[THIEVING_SKILL_INDEX];
        if (thievingLevel < SIDE_DOOR_MIN_THIEVING_LEVEL) {
            player.getPacketSender().sendGameMessage("You need a Thieving level of 1 to pick this lock.");
            return true;
        }

        player.getUpdateState().setAnimation(2246);
        player.getPacketSender().sendGameMessage("You attempt to pick the lock...");
        if (!rollSideDoorUnlock(thievingLevel)) {
            player.getPacketSender().sendGameMessage("You fail to pick the lock.");
            return true;
        }

        setSideDoorOpen(door, true);
        player.getPacketSender().sendSoundEffect(1502, 1, 0);
        player.getPacketSender().sendGameMessage("You manage to pick the lock.");
        return true;
    }

    public static boolean handleMainDoor(Player player, int objectId, int objectX, int objectY) {
        MainDoorState door = findMainDoor(objectId, objectX, objectY);
        if (door == null) {
            return false;
        }
        CastleWarsManager.Team team = CastleWarsManager.getGameTeam(player);
        if (team == null) {
            player.getPacketSender().sendGameMessage("The main doors can only be used during a Castle Wars game.");
            return true;
        }
        if (door.mode == MainDoorMode.BROKEN) {
            if (team != door.team) {
                player.getPacketSender().sendGameMessage("You can only repair your own team's main doors.");
                return true;
            }
            if (player.getInventoryManager().getItemAmount(TOOLKIT_ID) <= 0) {
                player.getPacketSender().sendGameMessage("You need a toolkit to repair the main doors.");
                return true;
            }
            return repairMainDoor(player, objectId, objectX, objectY);
        }
        if (team != door.team) {
            player.getPacketSender().sendGameMessage("You can't open the enemy team's main doors.");
            return true;
        }

        setMainDoorMode(door, door.mode == MainDoorMode.CLOSED
                ? MainDoorMode.OPEN : MainDoorMode.CLOSED);
        player.getPacketSender().sendSoundEffect(318, 1, 0);
        return true;
    }

    public static boolean attackMainDoor(Player player, int objectId, int objectX, int objectY) {
        MainDoorState door = findMainDoor(objectId, objectX, objectY);
        if (door == null || door.mode != MainDoorMode.CLOSED
                || !isClosedMainDoorObject(objectId)) {
            return false;
        }

        CastleWarsManager.Team team = CastleWarsManager.getGameTeam(player);
        if (team == null) {
            return false;
        }
        if (team == door.team) {
            player.getPacketSender().sendGameMessage("You can't attack your own team's main doors.");
            return true;
        }

        long now = System.currentTimeMillis();
        Long readyAt = mainDoorAttackReadyAt.get(player);
        if (readyAt != null && now < readyAt.longValue()) {
            return true;
        }

        int fightMode = player.getFightMode();
        AttackStyleDefinition[] styles =
                player.getWeaponProfile().getInterfaceDefinition().getAttackStyles();
        if (fightMode < 0 || fightMode >= styles.length
                || fightMode >= player.getWeaponProfile().getAttackAnimations().length) {
            return true;
        }
        AttackStyleDefinition attackStyle = styles[fightMode];
        if (attackStyle.getCombatType() != CombatType.MELEE) {
            player.getPacketSender().sendGameMessage("You need to use a melee attack to damage the main doors.");
            return true;
        }

        player.getUpdateState().setAnimation(
                player.getWeaponProfile().getAttackAnimations()[fightMode]);
        mainDoorAttackReadyAt.put(player, Long.valueOf(now
                + Math.max(1, player.getWeaponProfile().getAttackDelay()) * GAME_TICK_MILLIS));

        WeaponCombatAttack attack =
                new WeaponCombatAttack(player, player, player.getWeaponProfile());
        int maxHit = Math.max(1,
                (int) CombatManager.calculateMeleeMaxHit(player, attack));
        int damage = GameUtil.randomInt(maxHit + 1);
        if (damage <= 0) {
            player.getPacketSender().sendGameMessage("Your attack glances off the main doors.");
            return true;
        }

        door.hitpoints = Math.max(0, door.hitpoints - damage);
        if (door.hitpoints == 0) {
            setMainDoorMode(door, MainDoorMode.BROKEN);
            player.getPacketSender().sendGameMessage("The enemy team's main doors collapse!");
        } else {
            player.getPacketSender().sendGameMessage("You damage the enemy team's main doors.");
        }
        return true;
    }

    public static int getMainDoorHitpoints(CastleWarsManager.Team team) {
        return getMainDoorState(team).hitpoints;
    }

    public static boolean isMainDoorBroken(CastleWarsManager.Team team) {
        return getMainDoorState(team).mode == MainDoorMode.BROKEN;
    }

    public static boolean isSideDoorOpen(CastleWarsManager.Team team) {
        return (team == CastleWarsManager.Team.SARADOMIN
                ? saradominSideDoor : zamorakSideDoor).open;
    }

    private static boolean repairMainDoor(Player player, int objectId, int objectX, int objectY) {
        MainDoorState door = findMainDoor(objectId, objectX, objectY);
        if (door == null || door.mode != MainDoorMode.BROKEN) {
            return false;
        }
        CastleWarsManager.Team team = CastleWarsManager.getGameTeam(player);
        if (team != door.team) {
            player.getPacketSender().sendGameMessage("You can only repair your own team's main doors.");
            return true;
        }
        if (player.getInventoryManager().getItemAmount(TOOLKIT_ID) <= 0) {
            return false;
        }

        player.getUpdateState().setAnimation(898);
        door.hitpoints = MAIN_DOOR_MAX_HITPOINTS;
        setMainDoorMode(door, MainDoorMode.CLOSED);
        player.getPacketSender().sendGameMessage("You repair your team's main doors.");
        return true;
    }

    private static void resetMainDoors() {
        saradominMainDoor.hitpoints = MAIN_DOOR_MAX_HITPOINTS;
        zamorakMainDoor.hitpoints = MAIN_DOOR_MAX_HITPOINTS;
        setMainDoorMode(saradominMainDoor, MainDoorMode.CLOSED);
        setMainDoorMode(zamorakMainDoor, MainDoorMode.CLOSED);
    }

    private static void resetSideDoors() {
        setSideDoorOpen(saradominSideDoor, false);
        setSideDoorOpen(zamorakSideDoor, false);
    }

    private static SideDoorState findSideDoor(int objectId, int objectX, int objectY) {
        if (saradominSideDoor.matches(objectId, objectX, objectY)) {
            return saradominSideDoor;
        }
        if (zamorakSideDoor.matches(objectId, objectX, objectY)) {
            return zamorakSideDoor;
        }
        return null;
    }

    private static boolean rollSideDoorUnlock(int thievingLevel) {
        int level = Math.max(1, Math.min(99, thievingLevel));
        int range = SIDE_DOOR_LEVEL_NINETY_NINE_THRESHOLD - SIDE_DOOR_LEVEL_ONE_THRESHOLD;
        int threshold = SIDE_DOOR_LEVEL_ONE_THRESHOLD
                + (int) Math.round((level - 1) * (double) range / 98.0);
        return GameUtil.randomInt(SIDE_DOOR_ROLL_SIZE) < threshold;
    }

    private static void setSideDoorOpen(SideDoorState door, boolean open) {
        removeSideDoorDynamicObject(door.closedX, door.closedY);
        removeSideDoorDynamicObject(door.openX, door.openY);

        if (open) {
            new DynamicObject(ServerSettings.placeholderObjectId,
                    door.closedX, door.closedY, MAIN_DOOR_PLANE,
                    door.closedOrientation, 0, ServerSettings.placeholderObjectId, 999999999);
            new DynamicObject(door.openId, door.openX, door.openY, MAIN_DOOR_PLANE,
                    door.openOrientation, 0, door.openId, 999999999);
        } else {
            new DynamicObject(door.closedId, door.closedX, door.closedY, MAIN_DOOR_PLANE,
                    door.closedOrientation, 0, door.closedId, 999999999);
            new DynamicObject(ServerSettings.placeholderObjectId,
                    door.openX, door.openY, MAIN_DOOR_PLANE,
                    door.openOrientation, 0, ServerSettings.placeholderObjectId, 999999999);
        }
        door.open = open;
    }

    private static void removeSideDoorDynamicObject(int x, int y) {
        if (ObjectManager.findDynamicObjectAt(x, y, MAIN_DOOR_PLANE) != null) {
            ObjectManager.getInstance().removeDynamicObjectAt(x, y, MAIN_DOOR_PLANE, 0);
        }
    }

    private static MainDoorState getMainDoorState(CastleWarsManager.Team team) {
        return team == CastleWarsManager.Team.SARADOMIN
                ? saradominMainDoor : zamorakMainDoor;
    }

    private static MainDoorState findMainDoor(int objectId, int objectX, int objectY) {
        if (saradominMainDoor.matches(objectId, objectX, objectY)) {
            return saradominMainDoor;
        }
        if (zamorakMainDoor.matches(objectId, objectX, objectY)) {
            return zamorakMainDoor;
        }
        return null;
    }

    private static boolean isClosedMainDoorObject(int objectId) {
        return objectId == SARADOMIN_MAIN_DOOR_LEFT_CLOSED_ID
                || objectId == SARADOMIN_MAIN_DOOR_RIGHT_CLOSED_ID
                || objectId == ZAMORAK_MAIN_DOOR_LEFT_CLOSED_ID
                || objectId == ZAMORAK_MAIN_DOOR_RIGHT_CLOSED_ID;
    }

    private static boolean isBrokenMainDoorObject(int objectId) {
        return objectId == SARADOMIN_MAIN_DOOR_LEFT_BROKEN_ID
                || objectId == SARADOMIN_MAIN_DOOR_RIGHT_BROKEN_ID
                || objectId == ZAMORAK_MAIN_DOOR_LEFT_BROKEN_ID
                || objectId == ZAMORAK_MAIN_DOOR_RIGHT_BROKEN_ID;
    }

    private static void setMainDoorMode(MainDoorState door, MainDoorMode mode) {
        for (MainDoorLeaf leaf : door.leaves) {
            removeMainDoorDynamicObject(leaf.closedX, leaf.closedY);
            removeMainDoorDynamicObject(leaf.openX, leaf.openY);
        }

        for (MainDoorLeaf leaf : door.leaves) {
            if (mode == MainDoorMode.CLOSED) {
                new DynamicObject(leaf.closedId, leaf.closedX, leaf.closedY, MAIN_DOOR_PLANE,
                        leaf.closedOrientation, 0, leaf.closedId, 999999999);
                new DynamicObject(ServerSettings.placeholderObjectId,
                        leaf.openX, leaf.openY, MAIN_DOOR_PLANE,
                        leaf.openOrientation, 0, ServerSettings.placeholderObjectId, 999999999);
            } else {
                new DynamicObject(ServerSettings.placeholderObjectId,
                        leaf.closedX, leaf.closedY, MAIN_DOOR_PLANE,
                        leaf.closedOrientation, 0, leaf.closedId, 999999999);
                int displayId = mode == MainDoorMode.OPEN ? leaf.openId : leaf.brokenId;
                new DynamicObject(displayId, leaf.openX, leaf.openY, MAIN_DOOR_PLANE,
                        leaf.openOrientation, 0, ServerSettings.placeholderObjectId, 999999999);
            }
        }
        door.mode = mode;
    }

    private static void removeMainDoorDynamicObject(int x, int y) {
        if (ObjectManager.findDynamicObjectAt(x, y, MAIN_DOOR_PLANE) != null) {
            ObjectManager.getInstance().removeDynamicObjectAt(x, y, MAIN_DOOR_PLANE, 0);
        }
    }

    public static int giveSupply(Player player, int itemId, int amount) {
        if (!CastleWarsManager.isInGame(player) || amount <= 0) {
            return 0;
        }
        if (itemId != ROCK_ITEM_ID && itemId != EXPLOSIVE_POTION_ID
                && itemId != BARRICADE_ITEM_ID && itemId != CLIMBING_ROPE_ITEM_ID
                && itemId != BRONZE_PICKAXE_ID && itemId != TOOLKIT_ID) {
            return 0;
        }
        return player.getInventoryManager().addItemPartial(new ItemStack(itemId, amount));
    }

    public static void cleanupPlayerSupplies(Player player) {
        int[] ids = new int[]{ROCK_ITEM_ID, EXPLOSIVE_POTION_ID, BARRICADE_ITEM_ID,
                CLIMBING_ROPE_ITEM_ID, TOOLKIT_ID};
        for (int id : ids) {
            int amount = player.getInventoryManager().getItemAmount(id);
            if (amount > 0) {
                player.getInventoryManager().removeItem(new ItemStack(id, amount));
            }
        }
    }

    public static boolean handleClimbingRope(Player player, int objectId, int objectX, int objectY) {
        if (objectId != CLIMBING_ROPE_OBJECT_ID) {
            return false;
        }
        if (!CastleWarsManager.isInGame(player)) {
            return false;
        }
        if (player.getPosition().getPlane() != CLIMBING_ROPE_PLANE) {
            player.getPacketSender().sendGameMessage("The rope can only be climbed from below.");
            return true;
        }

        ClimbingRopeState rope = getActiveClimbingRope(objectX, objectY);
        if (rope == null) {
            return false;
        }
        if (GameUtil.getDistance(player.getPosition(), rope.position) > 2) {
            return false;
        }

        player.getMovementQueue().clear();
        player.getUpdateState().setAnimation(828);
        player.moveTo(rope.destination.copy());
        player.getMovementQueue().clearMovementActions();
        if (!player.isBot) {
            player.getPacketSender().resetCamera();
        }
        player.getPacketSender().sendGameMessage("You climb the rope onto the battlements.");
        return true;
    }

    private static boolean attachClimbingRope(Player player, int battlementId,
                                              int objectX, int objectY, int objectPlane) {
        if (objectPlane != CLIMBING_ROPE_PLANE) {
            return false;
        }
        CastleWarsManager.Team targetTeam = getBattlementTeam(objectX, objectY);
        if (targetTeam == null) {
            return false;
        }

        CastleWarsManager.Team playerTeam = CastleWarsManager.getGameTeam(player);
        if (playerTeam == null) {
            return false;
        }
        if (playerTeam == targetTeam) {
            player.getPacketSender().sendGameMessage("You can only attach climbing ropes to the enemy castle.");
            return true;
        }
        if (player.getInventoryManager().getItemAmount(CLIMBING_ROPE_ITEM_ID) <= 0) {
            return true;
        }
        if (GameUtil.getDistance(player.getPosition(),
                new Position(objectX, objectY, objectPlane)) > 2) {
            return false;
        }

        DynamicObject existing = ObjectManager.findDynamicObjectAt(objectX, objectY, objectPlane);
        if (existing != null) {
            if (existing.getWorldObject().getObjectId() == CLIMBING_ROPE_OBJECT_ID) {
                player.getPacketSender().sendGameMessage("A climbing rope is already attached here.");
                return true;
            }
            return false;
        }

        Position destination = findClimbingRopeDestination(objectX, objectY);
        if (destination == null) {
            player.getPacketSender().sendGameMessage("You cannot attach a rope to this part of the wall.");
            return true;
        }

        int orientation = SkillActionHelper.getObjectOrientation(
                battlementId, objectX, objectY, objectPlane);
        int type = SkillActionHelper.getObjectType(
                battlementId, objectX, objectY, objectPlane);

        player.getInventoryManager().removeItem(new ItemStack(CLIMBING_ROPE_ITEM_ID, 1));
        new DynamicObject(CLIMBING_ROPE_OBJECT_ID, objectX, objectY, objectPlane,
                orientation, type, battlementId, CLIMBING_ROPE_LIFETIME_TICKS, false);
        climbingRopes.put(key(new Position(objectX, objectY, objectPlane)),
                new ClimbingRopeState(new Position(objectX, objectY, objectPlane), destination));
        player.getPacketSender().sendGameMessage("You attach the climbing rope to the battlements.");
        return true;
    }

    private static ClimbingRopeState getActiveClimbingRope(int objectX, int objectY) {
        String key = key(new Position(objectX, objectY, CLIMBING_ROPE_PLANE));
        ClimbingRopeState rope = climbingRopes.get(key);
        if (rope == null) {
            return null;
        }

        DynamicObject dynamicObject = ObjectManager.findDynamicObjectByIdAt(
                CLIMBING_ROPE_OBJECT_ID, objectX, objectY, CLIMBING_ROPE_PLANE);
        if (dynamicObject == null) {
            climbingRopes.remove(key);
            return null;
        }
        return rope;
    }

    private static Position findClimbingRopeDestination(int objectX, int objectY) {
        Position best = null;
        int bestDistance = Integer.MAX_VALUE;
        for (int x = objectX - 2; x <= objectX + 2; ++x) {
            for (int y = objectY - 2; y <= objectY + 2; ++y) {
                Position candidate = new Position(x, y, CLIMBING_ROPE_DESTINATION_PLANE);
                int clipping = WalkingCollisionMap.getTileFlags(
                        x, y, CLIMBING_ROPE_DESTINATION_PLANE);
                if (!CastleWarsManager.isCastleBattlementPosition(candidate)
                        || (clipping & 0x1280100) != 0) {
                    continue;
                }
                int distance = Math.abs(x - objectX) + Math.abs(y - objectY);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    best = candidate;
                }
            }
        }
        return best;
    }

    private static CastleWarsManager.Team getBattlementTeam(int x, int y) {
        if (x >= 2415 && x <= 2431 && y >= 3072 && y <= 3083) {
            return CastleWarsManager.Team.SARADOMIN;
        }
        if (x >= 2368 && x <= 2384 && y >= 3124 && y <= 3135) {
            return CastleWarsManager.Team.ZAMORAK;
        }
        return null;
    }

    private static void clearAllClimbingRopes() {
        for (ClimbingRopeState rope : climbingRopes.values()) {
            DynamicObject dynamicObject = ObjectManager.findDynamicObjectByIdAt(
                    CLIMBING_ROPE_OBJECT_ID,
                    rope.position.getX(), rope.position.getY(), rope.position.getPlane());
            if (dynamicObject != null) {
                dynamicObject.remainingTicks = 0;
            }
        }
        climbingRopes.clear();
    }

    public static boolean placeBarricade(Player player) {
        CastleWarsManager.Team team = CastleWarsManager.getGameTeam(player);
        if (team == null || player.getInventoryManager().getItemAmount(BARRICADE_ITEM_ID) <= 0) {
            return false;
        }
        if (getBarricadeCount(team) >= 10) {
            player.getPacketSender().sendGameMessage("Your team already has ten barricades set up.");
            return false;
        }

        Position position = player.getPosition().copy();
        String key = key(position);
        if (barricades.containsKey(key) || ObjectManager.findDynamicObjectAt(
                position.getX(), position.getY(), position.getPlane()) != null) {
            return false;
        }

        int objectId = team == CastleWarsManager.Team.SARADOMIN
                ? BARRICADE_OBJECT_SARADOMIN : BARRICADE_OBJECT_ZAMORAK;
        player.getInventoryManager().removeItem(new ItemStack(BARRICADE_ITEM_ID, 1));
        new DynamicObject(objectId, position.getX(), position.getY(), position.getPlane(),
                0, 10, ServerSettings.placeholderObjectId, 99999, false);
        WalkingCollisionMap.addObjectCollision(objectId, position.getX(), position.getY(),
                position.getPlane(), 0, 10, true);
        ProjectileCollisionMap.addObjectCollision(objectId, position.getX(), position.getY(),
                position.getPlane(), 0, 10, true);
        barricades.put(key, new BarricadeState(team, position, objectId));
        player.getUpdateState().setAnimation(827);
        return true;
    }

    public static Position findNearestEnemyBarricade(Player player, int radius) {
        CastleWarsManager.Team team = CastleWarsManager.getGameTeam(player);
        if (team == null) {
            return null;
        }
        Position best = null;
        int bestDistance = Integer.MAX_VALUE;
        for (BarricadeState barricade : barricades.values()) {
            if (barricade.team == team || barricade.position.getPlane() != player.getPosition().getPlane()) {
                continue;
            }
            int distance = GameUtil.getDistance(player.getPosition(), barricade.position);
            if (distance <= radius && distance < bestDistance) {
                bestDistance = distance;
                best = barricade.position;
            }
        }
        return best == null ? null : best.copy();
    }

    public static boolean igniteBarricade(Player player, Position position) {
        if (player.getInventoryManager().getItemAmount(TINDERBOX_ITEM_ID) <= 0 || position == null) {
            return false;
        }
        BarricadeState state = barricades.get(key(position));
        if (state == null || GameUtil.getDistance(player.getPosition(), state.position) > 2) {
            return false;
        }
        if (state.burning) {
            player.getPacketSender().sendGameMessage("The barricade is already on fire.");
            return true;
        }

        state.burning = true;
        state.burnExpiresAt = System.currentTimeMillis() + BARRICADE_BURN_DURATION_MILLIS;
        player.getUpdateState().setAnimation(733);
        player.getPacketSender().sendGameMessage("You set fire to the barricade.");
        return true;
    }

    public static boolean extinguishBarricade(Player player, Position position) {
        if (player.getInventoryManager().getItemAmount(BUCKET_OF_WATER_ITEM_ID) <= 0 || position == null) {
            return false;
        }
        BarricadeState state = barricades.get(key(position));
        if (state == null || GameUtil.getDistance(player.getPosition(), state.position) > 2) {
            return false;
        }
        if (!state.burning) {
            player.getPacketSender().sendGameMessage("The barricade isn't on fire.");
            return true;
        }

        if (!player.getInventoryManager().removeItem(new ItemStack(BUCKET_OF_WATER_ITEM_ID, 1))) {
            return false;
        }
        player.getInventoryManager().addItem(new ItemStack(EMPTY_BUCKET_ITEM_ID, 1));
        state.burning = false;
        state.burnExpiresAt = 0L;
        player.getPacketSender().sendGameMessage("You extinguish the barricade.");
        return true;
    }

    public static boolean destroyBarricadeWithExplosive(Player player, Position position) {
        if (player.getInventoryManager().getItemAmount(EXPLOSIVE_POTION_ID) <= 0 || position == null) {
            return false;
        }
        BarricadeState state = barricades.get(key(position));
        if (state == null || GameUtil.getDistance(player.getPosition(), state.position) > 2) {
            return false;
        }
        if (state.burning) {
            player.getPacketSender().sendGameMessage("The burning barricade resists the explosive potion.");
            return true;
        }
        player.getInventoryManager().removeItem(new ItemStack(EXPLOSIVE_POTION_ID, 1));
        removeBarricade(state);
        player.getPacketSender().sendStillGraphicToNearbyPlayers(176,
                position.getX(), position.getY(), position.getPlane(), 0);
        return true;
    }

    public static void processBarricadeFires(long now) {
        if (barricades.isEmpty()) {
            return;
        }
        ArrayList<BarricadeState> burnedOut = new ArrayList<BarricadeState>();
        for (BarricadeState state : barricades.values()) {
            if (state.burning && state.burnExpiresAt > 0L && now >= state.burnExpiresAt) {
                burnedOut.add(state);
            }
        }
        for (BarricadeState state : burnedOut) {
            removeBarricade(state);
        }
    }

    public static int getBarricadeCount(CastleWarsManager.Team team) {
        int count = 0;
        for (BarricadeState state : barricades.values()) {
            if (state.team == team) {
                ++count;
            }
        }
        return count;
    }

    public static boolean isRockslideCollapsed(int index) {
        return index >= 0 && index < rockslideCollapsed.length && rockslideCollapsed[index];
    }

    public static Position getRockslidePosition(int index) {
        if (index < 0 || index >= ROCKSLIDE_POSITIONS.length) {
            return null;
        }
        return ROCKSLIDE_POSITIONS[index].copy();
    }

    public static boolean clearRockslide(Player player, int index) {
        if (!isRockslideCollapsed(index) || index < 0 || index >= ROCKSLIDE_POSITIONS.length) {
            return false;
        }
        Position position = ROCKSLIDE_POSITIONS[index];
        if (GameUtil.getDistance(player.getPosition(), position) > 3) {
            return false;
        }

        if (player.getInventoryManager().getItemAmount(EXPLOSIVE_POTION_ID) > 0) {
            player.getInventoryManager().removeItem(new ItemStack(EXPLOSIVE_POTION_ID, 1));
            player.getPacketSender().sendStillGraphicToNearbyPlayers(176,
                    position.getX(), position.getY(), 0, 0);
        } else if (player.getInventoryManager().getItemAmount(BRONZE_PICKAXE_ID) > 0) {
            player.getUpdateState().setAnimation(625);
        } else {
            return false;
        }

        setRockslideState(index, false);
        return true;
    }

    public static boolean collapseRockslide(Player player, int index) {
        if (index < 0 || index >= ROCKSLIDE_POSITIONS.length || isRockslideCollapsed(index)) {
            return false;
        }
        Position position = ROCKSLIDE_POSITIONS[index];
        if (GameUtil.getDistance(player.getPosition(), position) > 4) {
            return false;
        }

        if (player.getInventoryManager().getItemAmount(EXPLOSIVE_POTION_ID) > 0) {
            player.getInventoryManager().removeItem(new ItemStack(EXPLOSIVE_POTION_ID, 1));
            player.getPacketSender().sendStillGraphicToNearbyPlayers(176,
                    position.getX(), position.getY(), 0, 0);
        } else if (player.getInventoryManager().getItemAmount(BRONZE_PICKAXE_ID) > 0) {
            player.getUpdateState().setAnimation(625);
        } else {
            return false;
        }

        setRockslideState(index, true);
        crushPlayersAtRockslide(position);
        return true;
    }

    private static boolean useRockslideTool(Player player, int index, boolean explosive) {
        if (index < 0 || index >= ROCKSLIDE_POSITIONS.length) {
            return false;
        }
        Position position = ROCKSLIDE_POSITIONS[index];
        if (GameUtil.getDistance(player.getPosition(), position) > 4) {
            return false;
        }

        if (explosive) {
            if (player.getInventoryManager().getItemAmount(EXPLOSIVE_POTION_ID) <= 0) {
                return false;
            }
            player.getInventoryManager().removeItem(new ItemStack(EXPLOSIVE_POTION_ID, 1));
            player.getPacketSender().sendStillGraphicToNearbyPlayers(
                    176, position.getX(), position.getY(), 0, 0);
        } else {
            if (player.getInventoryManager().getItemAmount(BRONZE_PICKAXE_ID) <= 0) {
                return false;
            }
            player.getUpdateState().setAnimation(625);
        }

        boolean collapsing = !rockslideCollapsed[index];
        setRockslideState(index, collapsing);
        if (collapsing) {
            crushPlayersAtRockslide(position);
            player.getPacketSender().sendGameMessage("You collapse the tunnel.");
        } else {
            player.getPacketSender().sendGameMessage("You clear the fallen rocks.");
        }
        return true;
    }

    private static int findRockslideIndex(int x, int y) {
        for (int i = 0; i < ROCKSLIDE_POSITIONS.length; ++i) {
            Position position = ROCKSLIDE_POSITIONS[i];
            if (position.getX() == x && position.getY() == y) {
                return i;
            }
        }
        return -1;
    }

    public static boolean fireCatapult(Player player) {
        CastleWarsManager.Team team = CastleWarsManager.getGameTeam(player);
        if (team == null || player.getInventoryManager().getItemAmount(ROCK_ITEM_ID) <= 0) {
            return false;
        }
        if (!isCatapultOperational(team)) {
            return false;
        }

        Position catapult = team == CastleWarsManager.Team.SARADOMIN
                ? SARADOMIN_CATAPULT : ZAMORAK_CATAPULT;
        if (GameUtil.getDistance(player.getPosition(), catapult) > 3 || player.getPosition().getPlane() != 0) {
            return false;
        }

        long now = System.currentTimeMillis();
        long readyAt = team == CastleWarsManager.Team.SARADOMIN
                ? saradominCatapultReadyAt : zamorakCatapultReadyAt;
        if (now < readyAt) {
            return false;
        }

        Position target = chooseCatapultTarget(team);
        if (target == null) {
            return false;
        }

        player.getInventoryManager().removeItem(new ItemStack(ROCK_ITEM_ID, 1));
        if (team == CastleWarsManager.Team.SARADOMIN) {
            saradominCatapultReadyAt = now + 12000L;
        } else {
            zamorakCatapultReadyAt = now + 12000L;
        }
        player.getPacketSender().sendStillGraphicToNearbyPlayers(287,
                target.getX(), target.getY(), 0, 0);
        damageCatapultArea(target);
        return true;
    }

    public static boolean sabotageEnemyCatapult(Player player) {
        CastleWarsManager.Team team = CastleWarsManager.getGameTeam(player);
        if (team == null || player.getInventoryManager().getItemAmount(EXPLOSIVE_POTION_ID) <= 0) {
            return false;
        }
        CastleWarsManager.Team enemy = team == CastleWarsManager.Team.SARADOMIN
                ? CastleWarsManager.Team.ZAMORAK : CastleWarsManager.Team.SARADOMIN;
        Position target = enemy == CastleWarsManager.Team.SARADOMIN
                ? SARADOMIN_CATAPULT : ZAMORAK_CATAPULT;
        if (player.getPosition().getPlane() != 0 || GameUtil.getDistance(player.getPosition(), target) > 3) {
            return false;
        }
        if (!isCatapultOperational(enemy)) {
            return false;
        }

        player.getInventoryManager().removeItem(new ItemStack(EXPLOSIVE_POTION_ID, 1));
        player.getPacketSender().sendStillGraphicToNearbyPlayers(176,
                target.getX(), target.getY(), 0, 0);
        setCatapultOperational(enemy, false);
        return true;
    }

    public static boolean isCatapultOperational(CastleWarsManager.Team team) {
        return team == CastleWarsManager.Team.SARADOMIN
                ? saradominCatapultOperational : zamorakCatapultOperational;
    }

    public static boolean repairOwnCatapult(Player player) {
        CastleWarsManager.Team team = CastleWarsManager.getGameTeam(player);
        if (team == null || isCatapultOperational(team)
                || player.getInventoryManager().getItemAmount(TOOLKIT_ID) <= 0) {
            return false;
        }
        Position target = team == CastleWarsManager.Team.SARADOMIN
                ? SARADOMIN_CATAPULT : ZAMORAK_CATAPULT;
        if (player.getPosition().getPlane() != 0
                || GameUtil.getDistance(player.getPosition(), target) > 3) {
            return false;
        }
        player.getUpdateState().setAnimation(898);
        setCatapultOperational(team, true);
        player.getPacketSender().sendGameMessage("You repair your team's catapult.");
        return true;
    }

    public static boolean isHomeTunnelCollapsed(CastleWarsManager.Team team, int route) {
        if (team == CastleWarsManager.Team.SARADOMIN) {
            return rockslideCollapsed[route == 0 ? 3 : 2];
        }
        return rockslideCollapsed[route == 0 ? 0 : 1];
    }

    private static void setCatapultOperational(CastleWarsManager.Team team, boolean operational) {
        Position position = team == CastleWarsManager.Team.SARADOMIN
                ? SARADOMIN_CATAPULT : ZAMORAK_CATAPULT;
        int normalId = team == CastleWarsManager.Team.SARADOMIN
                ? SARADOMIN_CATAPULT_ID : ZAMORAK_CATAPULT_ID;
        int damagedId = team == CastleWarsManager.Team.SARADOMIN
                ? SARADOMIN_DAMAGED_CATAPULT_ID : ZAMORAK_DAMAGED_CATAPULT_ID;

        DynamicObject existing = ObjectManager.findDynamicObjectAt(
                position.getX(), position.getY(), position.getPlane());
        if (existing != null) {
            ObjectManager.getInstance().removeDynamicObjectAt(
                    position.getX(), position.getY(), position.getPlane(), 10);
        }

        if (!operational) {
            new DynamicObject(damagedId, position.getX(), position.getY(), 0,
                    0, 10, normalId, 99999, false);
        }

        if (team == CastleWarsManager.Team.SARADOMIN) {
            saradominCatapultOperational = operational;
        } else {
            zamorakCatapultOperational = operational;
        }
    }

    private static void setRockslideState(int index, boolean collapsed) {
        Position position = ROCKSLIDE_POSITIONS[index];
        DynamicObject existing = ObjectManager.findDynamicObjectAt(
                position.getX(), position.getY(), position.getPlane());
        if (rockslideCollapsed[index] == collapsed && existing == null) {
            return;
        }
        if (existing != null) {
            ObjectManager.getInstance().removeDynamicObjectAt(
                    position.getX(), position.getY(), position.getPlane(), 10);
        }

        if (collapsed) {
            WalkingCollisionMap.addObjectCollision(COLLAPSED_ROCK_OBJECT_ID,
                    position.getX(), position.getY(), 0, 0, 10, false);
            ProjectileCollisionMap.addObjectCollision(COLLAPSED_ROCK_OBJECT_ID,
                    position.getX(), position.getY(), 0, 0, 10, false);
        } else {
            new DynamicObject(CLEARED_ROCK_OBJECT_ID, position.getX(), position.getY(), 0,
                    0, 10, COLLAPSED_ROCK_OBJECT_ID, 99999, false);
            WalkingCollisionMap.addObjectCollision(CLEARED_ROCK_OBJECT_ID,
                    position.getX(), position.getY(), 0, 0, 10, false);
            ProjectileCollisionMap.addObjectCollision(CLEARED_ROCK_OBJECT_ID,
                    position.getX(), position.getY(), 0, 0, 10, false);
        }
        rockslideCollapsed[index] = collapsed;
    }

    private static void crushPlayersAtRockslide(Position position) {
        for (Player target : World.getPlayers()) {
            if (target == null || target.isDead() || !CastleWarsManager.isInGame(target)) {
                continue;
            }
            if (target.getPosition().getPlane() != 0
                    || GameUtil.getDistance(target.getPosition(), position) > 1) {
                continue;
            }
            target.applyDirectHit(target.getCurrentHitpoints(), HitType.NORMAL);
        }
    }

    private static Position chooseCatapultTarget(CastleWarsManager.Team firingTeam) {
        Player best = null;
        int bestNearbyEnemies = -1;
        for (Player candidate : World.getPlayers()) {
            if (candidate == null || candidate.isDead()
                    || CastleWarsManager.getGameTeam(candidate) == null
                    || CastleWarsManager.getGameTeam(candidate) == firingTeam
                    || candidate.getPosition().getPlane() != 0
                    || candidate.getPosition().getY() >= 9000) {
                continue;
            }

            int nearby = 0;
            for (Player other : World.getPlayers()) {
                if (other == null || other.isDead()
                        || CastleWarsManager.getGameTeam(other) == null
                        || CastleWarsManager.getGameTeam(other) == firingTeam
                        || other.getPosition().getPlane() != 0) {
                    continue;
                }
                if (GameUtil.getDistance(candidate.getPosition(), other.getPosition()) <= 3) {
                    ++nearby;
                }
            }
            if (nearby > bestNearbyEnemies) {
                bestNearbyEnemies = nearby;
                best = candidate;
            }
        }

        if (best != null) {
            return new Position(best.getPosition().getX() - 1 + GameUtil.randomInt(3),
                    best.getPosition().getY() - 1 + GameUtil.randomInt(3), 0);
        }

        int[][] fallback = firingTeam == CastleWarsManager.Team.SARADOMIN
                ? new int[][]{{2381,3104},{2385,3110},{2394,3119},{2380,3111},{2400,3125}}
                : new int[][]{{2417,3103},{2409,3093},{2402,3086},{2397,3079},{2420,3096}};
        int[] point = fallback[GameUtil.randomInt(fallback.length)];
        return new Position(point[0], point[1], 0);
    }

    private static void damageCatapultArea(Position target) {
        for (Player player : World.getPlayers()) {
            if (player == null || player.isDead() || !CastleWarsManager.isInGame(player)
                    || player.getPosition().getPlane() != 0) {
                continue;
            }
            if (Math.abs(player.getPosition().getX() - target.getX()) <= 2
                    && Math.abs(player.getPosition().getY() - target.getY()) <= 2) {
                player.applyDirectHit(5 + GameUtil.randomInt(11), HitType.NORMAL);
            }
        }
    }

    private static void clearAllBarricades() {
        ArrayList<BarricadeState> copy = new ArrayList<BarricadeState>(barricades.values());
        for (BarricadeState state : copy) {
            removeBarricade(state);
        }
        barricades.clear();
    }

    private static void removeBarricade(BarricadeState state) {
        ObjectManager.getInstance().removeDynamicObjectAt(
                state.position.getX(), state.position.getY(), state.position.getPlane(), 10);
        WalkingCollisionMap.removeObjectCollision(state.objectId,
                state.position.getX(), state.position.getY(), state.position.getPlane(), 0, 10);
        ProjectileCollisionMap.removeObjectCollision(state.objectId,
                state.position.getX(), state.position.getY(), state.position.getPlane(), 0, 10);
        barricades.remove(key(state.position));
    }

    private static String key(Position position) {
        return position.getX() + ":" + position.getY() + ":" + position.getPlane();
    }

    private static final class SideDoorState {
        private final CastleWarsManager.Team team;
        private final int closedId;
        private final int openId;
        private final int closedX;
        private final int closedY;
        private final int closedOrientation;
        private final int openX;
        private final int openY;
        private final int openOrientation;
        private boolean open;

        private SideDoorState(CastleWarsManager.Team team,
                              int closedId, int openId,
                              int closedX, int closedY, int closedOrientation,
                              int openX, int openY, int openOrientation) {
            this.team = team;
            this.closedId = closedId;
            this.openId = openId;
            this.closedX = closedX;
            this.closedY = closedY;
            this.closedOrientation = closedOrientation;
            this.openX = openX;
            this.openY = openY;
            this.openOrientation = openOrientation;
        }

        private boolean matches(int objectId, int x, int y) {
            return objectId == closedId && x == closedX && y == closedY
                    || objectId == openId && x == openX && y == openY;
        }
    }

    private enum MainDoorMode {
        CLOSED,
        OPEN,
        BROKEN
    }

    private static final class MainDoorLeaf {
        private final int closedId;
        private final int openId;
        private final int brokenId;
        private final int closedX;
        private final int closedY;
        private final int closedOrientation;
        private final int openX;
        private final int openY;
        private final int openOrientation;

        private MainDoorLeaf(int closedId, int openId, int brokenId,
                             int closedX, int closedY, int closedOrientation,
                             int openX, int openY, int openOrientation) {
            this.closedId = closedId;
            this.openId = openId;
            this.brokenId = brokenId;
            this.closedX = closedX;
            this.closedY = closedY;
            this.closedOrientation = closedOrientation;
            this.openX = openX;
            this.openY = openY;
            this.openOrientation = openOrientation;
        }

        private boolean matches(int objectId, int x, int y) {
            if (objectId == closedId) {
                return x == closedX && y == closedY;
            }
            return (objectId == openId || objectId == brokenId)
                    && x == openX && y == openY;
        }
    }

    private static final class MainDoorState {
        private final CastleWarsManager.Team team;
        private final MainDoorLeaf[] leaves;
        private int hitpoints = MAIN_DOOR_MAX_HITPOINTS;
        private MainDoorMode mode = MainDoorMode.CLOSED;

        private MainDoorState(CastleWarsManager.Team team,
                              MainDoorLeaf first, MainDoorLeaf second) {
            this.team = team;
            this.leaves = new MainDoorLeaf[]{first, second};
        }

        private boolean matches(int objectId, int x, int y) {
            for (MainDoorLeaf leaf : leaves) {
                if (leaf.matches(objectId, x, y)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static final class ClimbingRopeState {
        private final Position position;
        private final Position destination;

        private ClimbingRopeState(Position position, Position destination) {
            this.position = position;
            this.destination = destination;
        }
    }

    private static final class BarricadeState {
        private final CastleWarsManager.Team team;
        private final Position position;
        private final int objectId;
        private boolean burning;
        private long burnExpiresAt;

        private BarricadeState(CastleWarsManager.Team team, Position position, int objectId) {
            this.team = team;
            this.position = position.copy();
            this.objectId = objectId;
        }
    }
}
