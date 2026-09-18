package com.rs2.model.skill.fishing;

import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.npc.Npc;
import com.rs2.model.npc.NpcMovementMode;
import com.rs2.model.skill.fishing.FishingSpotDefinition;
import java.util.HashMap;
import java.util.Map;

public final class FishingSpotManager {
    public static final Map activeSpotsByPosition = new HashMap();
    private static Position[][] spotPositions = new Position[][]{{new Position(3101, 3092, 0), new Position(3103, 3092)}, {new Position(3104, 3424, 0), new Position(3104, 3425, 0), new Position(3110, 3432, 0), new Position(3110, 3433, 0)}, {new Position(3085, 3230, 0), new Position(3085, 3231, 0), new Position(3086, 3227, 0)}, {new Position(3239, 3244, 0), new Position(3238, 3252, 0)}, {new Position(2921, 3178, 0), new Position(2923, 3179, 0), new Position(2923, 3180, 0), new Position(2924, 3181, 0), new Position(2926, 3180, 0), new Position(2926, 3179, 0), new Position(2926, 3176, 0)}, {new Position(2838, 3431, 0), new Position(2837, 3431, 0), new Position(2836, 3431, 0), new Position(2846, 3429, 0), new Position(2844, 3429, 0), new Position(2845, 3429, 0)}, {new Position(2853, 3423, 0), new Position(2855, 3423, 0), new Position(2859, 3426, 0)}, {new Position(2726, 3524, 0), new Position(2727, 3524, 0), new Position(2714, 3532, 0)}, {new Position(2990, 3169, 0), new Position(2986, 3176, 0)}, {new Position(2461, 3151, 0), new Position(2461, 3150, 0), new Position(2462, 3145, 0), new Position(2472, 3156, 0), new Position(2474, 3153, 0)}, {new Position(2393, 3419, 0), new Position(2391, 3421, 0), new Position(2389, 3423, 0), new Position(2388, 3423, 0), new Position(2385, 3422, 0), new Position(2384, 3419, 0), new Position(2383, 3417, 0), new Position(2382, 3415, 0), new Position(2382, 3414, 0), new Position(2384, 3411, 0)}, {new Position(2527, 3412, 0), new Position(2530, 3412, 0), new Position(2533, 3410, 0), new Position(2537, 3406, 0), new Position(2507, 3421, 0)}, {new Position(2561, 3374, 0), new Position(2562, 3374, 0), new Position(2568, 3365, 0), new Position(2566, 3370, 0)}, {new Position(2855, 2974, 0), new Position(2865, 2972, 0), new Position(2860, 2972, 0), new Position(2835, 2974, 0), new Position(2859, 2976, 0), new Position(2866, 2976, 0), new Position(2836, 2971, 0), new Position(2841, 2971, 0)}, {new Position(2791, 3279, 0), new Position(2795, 3279, 0), new Position(2790, 3273, 0), new Position(2793, 3283, 0)}, {new Position(2843, 3359, 0), new Position(2842, 3359, 0), new Position(2847, 3361, 0), new Position(2848, 3361, 0), new Position(2840, 3356, 0), new Position(2845, 3356, 0), new Position(2875, 3342, 0), new Position(2876, 3342, 0), new Position(2877, 3342, 0), new Position(2879, 3338, 0), new Position(2879, 3335, 0), new Position(2877, 3331, 0), new Position(2876, 3331, 0), new Position(2875, 3331, 0)}, {new Position(3497, 3175, 0), new Position(3496, 3178, 0), new Position(3499, 3178, 0), new Position(3489, 3184, 0), new Position(3496, 3176, 0), new Position(3486, 3184, 0), new Position(3479, 3189, 0), new Position(3476, 3191, 0), new Position(3472, 3196, 0), new Position(3496, 3180, 0), new Position(3512, 3178, 0), new Position(3515, 3180, 0), new Position(3518, 3177, 0), new Position(3528, 3172, 0), new Position(3531, 3169, 0), new Position(3531, 3172, 0), new Position(3531, 3167, 0), new Position(3529, 3165, 0), new Position(3528, 3167, 0), new Position(3527, 3169, 0), new Position(3529, 3165, 0), new Position(3527, 3171, 0)}, {new Position(3047, 3703, 0), new Position(3045, 3702, 0)}, {new Position(2612, 3411, 0), new Position(2607, 3410, 0), new Position(2612, 3414, 0), new Position(2612, 3415, 0), new Position(2609, 3416, 0), new Position(2604, 3417, 0), new Position(2605, 3416, 0), new Position(2602, 3411, 0), new Position(2602, 3412, 0), new Position(2602, 3414, 0), new Position(2603, 3417, 0), new Position(2599, 3419, 0), new Position(2601, 3422, 0), new Position(2605, 3421, 0), new Position(2602, 3426, 0), new Position(2604, 3426, 0), new Position(2605, 3425, 0), new Position(2605, 3420, 0), new Position(2603, 3419, 0), new Position(2598, 3422, 0)}, {new Position(2210, 3243, 0), new Position(2216, 3236, 0), new Position(2222, 3241, 0), new Position(2223, 3238, 0)}, {new Position(2266, 3253, 0), new Position(2265, 3258, 0), new Position(2264, 3258, 0)}, {new Position(2633, 3691, 0), new Position(2633, 3689, 0), new Position(2639, 3698, 0), new Position(2639, 3697, 0), new Position(2639, 3695, 0), new Position(2642, 3694, 0), new Position(2642, 3697, 0), new Position(2644, 3709, 0), new Position(2645, 3708, 0), new Position(2648, 3708, 0), new Position(2648, 3711, 0)}, {new Position(2580, 3851, 0), new Position(2581, 3851, 0), new Position(2582, 3851, 0), new Position(2583, 3852, 0), new Position(2583, 3853, 0), new Position(2572, 3860, 0), new Position(2573, 3860, 0)}, {new Position(2694, 2706, 0), new Position(2707, 2698, 0)}, {new Position(3267, 3148, 0), new Position(3268, 3147, 0), new Position(3275, 3140, 0), new Position(3275, 3140, 0)}, {new Position(3350, 3817, 0), new Position(3347, 3814, 0), new Position(3363, 3816, 0), new Position(3368, 3811, 0)}, {new Position(2342, 3702, 0), new Position(2345, 3702, 0), new Position(2348, 3702, 0), new Position(2326, 3700, 0), new Position(2321, 3702, 0), new Position(2311, 3704, 0), new Position(2307, 3700, 0)}, {new Position(2889, 9766, 0), new Position(2890, 9766), new Position(2891, 9766)}};
    private static int[][] spotNpcIdsByLocation = new int[][]{{316, 316}, {309, 309, 309}, {316, 316, 316}, {309, 309}, {316, 312, 312, 316, 316, 312}, {316, 312, 312, 316, 316, 312}, {313, 313, 313}, {309, 309}, {316, 316}, {309, 309, 309, 309}, {309, 309, 309, 309, 309, 309, 309}, {309, 309, 309}, {309, 309, 309}, {309, 309, 309, 309, 309}, {316, 316, 316}, {316, 316, 316, 316, 316, 316, 316, 316, 316}, {313, 313, 313, 313, 313, 313, 313, 313, 313, 313, 313, 313, 313, 313, 313, 313, 313}, {316, 316}, {312, 312, 313, 313, 313, 312, 313, 313, 313, 312, 313, 313, 313, 313, 312, 313, 312}, {309, 309, 309}, {309, 309, 309}, {316, 316, 312, 312, 312, 313, 313, 313}, {312, 312, 312, 312, 312}, {313, 313}, {316, 316, 316}, {312, 312, 312, 312}, {1174, 1174, 1174, 1174, 1174, 1174, 1174}, {800, 800, 800}};

    /*
     * Enabled aggressive block sorting
     */
    public static void spawnFishingSpots() {
        int index = 0;
        while (index < 28) {
            int index2 = 0;
            while (index2 < spotNpcIdsByLocation[index].length) {
                Npc npc = new Npc(spotNpcIdsByLocation[index][index2]);
                npc.moveTo(spotPositions[index][index2]);
                npc.setMovementMode(NpcMovementMode.STATIONARY);
                npc.setSpawnPosition(spotPositions[index][index2]);
                npc.setRespawnEnabled(true);
                World.registerNpc(npc);
                activeSpotsByPosition.put(spotPositions[index][index2], npc);
                ++index2;
            }
            ++index;
        }
    }

    static boolean isSpotAtPosition(Position position, FishingSpotDefinition fishingSpotDefinition) {
        Npc npc = (Npc)activeSpotsByPosition.get(position);
        if (npc != null) {
            int[] spotNpcIds = fishingSpotDefinition.getSpotNpcIds();
            int length = spotNpcIds.length;
            int index = 0;
            while (index < length) {
                int value = spotNpcIds[index];
                if (npc.getDefinition().getId() == value) {
                    return true;
                }
                ++index;
            }
        }
        return false;
    }
}

