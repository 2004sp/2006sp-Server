package com.rs2.model.gameplay.castlewars;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import com.rs2.util.path.ProjectileCollisionMap;
import com.rs2.util.path.WalkingCollisionMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class CastleWarsEngineeringManager {
    public static final int ROCK_ITEM_ID = 4043;
    public static final int EXPLOSIVE_POTION_ID = 4045;
    public static final int TOOLKIT_ID = 4051;
    public static final int BARRICADE_ITEM_ID = 4053;
    public static final int BRONZE_PICKAXE_ID = 1265;

    public static final int BARRICADE_OBJECT_SARADOMIN = 4421;
    public static final int BARRICADE_OBJECT_ZAMORAK = 4422;
    public static final int COLLAPSED_ROCK_OBJECT_ID = 4437;
    public static final int CLEARED_ROCK_OBJECT_ID = 4439;

    public static final int ZAMORAK_CATAPULT_ID = 4381;
    public static final int SARADOMIN_CATAPULT_ID = 4382;
    public static final int ZAMORAK_DAMAGED_CATAPULT_ID = 4385;
    public static final int SARADOMIN_DAMAGED_CATAPULT_ID = 4386;

    public static final Position SARADOMIN_CATAPULT = new Position(2413, 3088, 0);
    public static final Position ZAMORAK_CATAPULT = new Position(2384, 3117, 0);

    private static final Position[] ROCKSLIDE_POSITIONS = new Position[]{
        new Position(2391, 9501, 0),
        new Position(2400, 9512, 0),
        new Position(2409, 9503, 0),
        new Position(2401, 9494, 0)
    };

    private static final Map<String, BarricadeState> barricades = new HashMap<String, BarricadeState>();
    private static final boolean[] rockslideCollapsed = new boolean[]{true, true, true, true};
    private static boolean saradominCatapultOperational = true;
    private static boolean zamorakCatapultOperational = true;
    private static long saradominCatapultReadyAt;
    private static long zamorakCatapultReadyAt;

    private CastleWarsEngineeringManager() {
    }

    public static void resetForGame() {
        clearAllBarricades();
        for (int i = 0; i < ROCKSLIDE_POSITIONS.length; ++i) {
            setRockslideState(i, true);
        }
        setCatapultOperational(CastleWarsManager.Team.SARADOMIN, true);
        setCatapultOperational(CastleWarsManager.Team.ZAMORAK, true);
        saradominCatapultReadyAt = 0L;
        zamorakCatapultReadyAt = 0L;
    }

    public static void cleanupAfterGame() {
        clearAllBarricades();
        for (int i = 0; i < ROCKSLIDE_POSITIONS.length; ++i) {
            setRockslideState(i, true);
        }
        setCatapultOperational(CastleWarsManager.Team.SARADOMIN, true);
        setCatapultOperational(CastleWarsManager.Team.ZAMORAK, true);
    }

    public static boolean handleSupplyTable(Player player, int objectId) {
        if (!CastleWarsManager.isInGame(player)) {
            return false;
        }
        int itemId;
        String message;
        if (objectId == 4460) {
            itemId = ROCK_ITEM_ID;
            message = "You take a rock for the catapult.";
        } else if (objectId == 4461) {
            itemId = BARRICADE_ITEM_ID;
            message = "You take a barricade.";
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

    public static int giveSupply(Player player, int itemId, int amount) {
        if (!CastleWarsManager.isInGame(player) || amount <= 0) {
            return 0;
        }
        if (itemId != ROCK_ITEM_ID && itemId != EXPLOSIVE_POTION_ID
                && itemId != BARRICADE_ITEM_ID && itemId != BRONZE_PICKAXE_ID
                && itemId != TOOLKIT_ID) {
            return 0;
        }
        return player.getInventoryManager().addItemPartial(new ItemStack(itemId, amount));
    }

    public static void cleanupPlayerSupplies(Player player) {
        int[] ids = new int[]{ROCK_ITEM_ID, EXPLOSIVE_POTION_ID, BARRICADE_ITEM_ID, TOOLKIT_ID};
        for (int id : ids) {
            int amount = player.getInventoryManager().getItemAmount(id);
            if (amount > 0) {
                player.getInventoryManager().removeItem(new ItemStack(id, amount));
            }
        }
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

    public static boolean destroyBarricadeWithExplosive(Player player, Position position) {
        if (player.getInventoryManager().getItemAmount(EXPLOSIVE_POTION_ID) <= 0 || position == null) {
            return false;
        }
        BarricadeState state = barricades.get(key(position));
        if (state == null || GameUtil.getDistance(player.getPosition(), state.position) > 2) {
            return false;
        }
        player.getInventoryManager().removeItem(new ItemStack(EXPLOSIVE_POTION_ID, 1));
        removeBarricade(state);
        player.getPacketSender().sendStillGraphicToNearbyPlayers(176,
                position.getX(), position.getY(), position.getPlane(), 0);
        return true;
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

    private static final class BarricadeState {
        private final CastleWarsManager.Team team;
        private final Position position;
        private final int objectId;

        private BarricadeState(CastleWarsManager.Team team, Position position, int objectId) {
            this.team = team;
            this.position = position.copy();
            this.objectId = objectId;
        }
    }
}
