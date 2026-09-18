package com.rs2.model.skill.farming;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

public final class MithrilSeedFlowerHandler {
    private static int[] mithrilSeedFlowerObjectIds = new int[]{2980, 2981, 2982, 2983, 2984, 2985, 2986, 2987, 2988};

    static {
        int[] integerValues = new int[]{2460, 2462, 2464, 2466, 2468, 2470, 2472, 2474, 2476};
    }

    public static void plantMithrilSeedFlower(Player player) {
        int value = mithrilSeedFlowerObjectIds[GameUtil.randomExclusive(9)];
        int position = player.getPosition().getX();
        int position2 = player.getPosition().getY();
        ObjectManager.getInstance();
        DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(position, position2, player.getPosition().getPlane());
        if (dynamicObject != null) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("You can't plant a flower here.");
            return;
        }
        if (!player.getInventoryManager().getContainer().containsItem(299)) {
            return;
        }
        player.getInventoryManager().removeItem(new ItemStack(299));
        player.getUpdateState().setAnimation(827);
        new DynamicObject(value, position, position2, player.getPosition().getPlane(), 0, 10, ServerSettings.placeholderObjectId, 500);
        if (player.canStepToOffset(-1, 0)) {
            Player player3 = player;
            player3.packetSender.queueRelativeMovementStep(-1, 0, false);
        } else {
            Player player4 = player;
            player4.packetSender.queueRelativeMovementStep(1, 0, false);
        }
        player.getUpdateState().setFacePosition(new Position(position, position2));
    }

    public static final boolean isMithrilSeedFlowerObjectId(int objectId) {
        int[] integerValues = mithrilSeedFlowerObjectIds;
        int index = 0;
        while (index < 9) {
            int value = integerValues[index];
            if (value == objectId) {
                return true;
            }
            ++index;
        }
        return false;
    }
}

