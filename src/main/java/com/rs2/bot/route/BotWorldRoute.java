package com.rs2.bot.route;

import com.rs2.bot.BotRoute;
import com.rs2.model.Position;
import com.rs2.util.GameUtil;

public enum BotWorldRoute {
    LUMBW_TO_DRAYNOR_CROSSROAD(new BotRoute(new Position[]{new Position(3232, 3230, 0), new Position(3222, 3240, 0), new Position(3222, 3240, 0), new Position(3216, 3254, 0), new Position(3216, 3269, 0), new Position(3214, 3277, 0), new Position(3197, 3280, 0), new Position(3181, 3286, 0), new Position(3160, 3289, 0), new Position(3151, 3295, 0), new Position(3136, 3295, 0), new Position(3121, 3297, 0), new Position(3110, 3294, 0)})),
    DRAYNOR_CROSSROAD_TO_DRAYNOR(new BotRoute(new Position[]{new Position(3109, 3281, 0), new Position(3104, 3273, 0), new Position(3102, 3255, 0), new Position(3093, 3247, 0)})),
    DRAYNOR_CROSSROAD_TO_BARB_VILLAGE(new BotRoute(new Position[]{new Position(3110, 3300, 0), new Position(3112, 3307, 0), new Position(3122, 3314, 0), new Position(3131, 3324, 0), new Position(3130, 3330, 0), new Position(3129, 3339, 0), new Position(3135, 3342, 0), new Position(3135, 3355, 0), new Position(3137, 3365, 0), new Position(3136, 3373, 0), new Position(3127, 3375, 0), new Position(3118, 3385, 0), new Position(3113, 3390, 0), new Position(3103, 3395, 0), new Position(3099, 3405, 0), new Position(3098, 3419, 0)})),
    BARB_VILLAGE_TO_VARROCK_WBANK(new BotRoute(new Position[]{new Position(3114, 3420, 0), new Position(3127, 3414, 0), new Position(3140, 3417, 0), new Position(3157, 3419, 0), new Position(3171, 3428, 0), new Position(3182, 3432, 0)})),
    BARB_VILLAGE_TO_EDGE(new BotRoute(new Position[]{new Position(3100, 3429, 0), new Position(3100, 3434, 0), new Position(3093, 3440, 0), new Position(3090, 3449, 0), new Position(3087, 3463, 0), new Position(3080, 3467, 0), new Position(3081, 3483, 0), new Position(3090, 3490, 0)})),
    BARB_VILLAGE_TO_FALA_CENTER(new BotRoute(new Position[]{new Position(3092, 3420, 0), new Position(3081, 3423, 0), new Position(3072, 3418, 0), new Position(3052, 3417, 0), new Position(3037, 3424, 0), new Position(3022, 3424, 0), new Position(3011, 3429, 0), new Position(2990, 3429, 0), new Position(2978, 3414, 0), new Position(2967, 3401, 0), new Position(2967, 3384, 0)})),
    FALA_CENTER_TO_WBANK(new BotRoute(new Position[]{new Position(2954, 3381, 0), new Position(2946, 3374, 0)})),
    FALA_CENTER_TO_EBANK(new BotRoute(new Position[]{new Position(2971, 3379, 0), new Position(2982, 3376, 0), new Position(2997, 3366, 0), new Position(3006, 3359, 0)})),
    DRAYNOR_CROSSROAD_TO_FALA_EBANK(new BotRoute(new Position[]{new Position(3105, 3294, 0), new Position(3095, 3294, 0), new Position(3089, 3289, 0), new Position(3081, 3288, 0), new Position(3071, 3278, 0), new Position(3052, 3276, 0), new Position(3039, 3276, 0), new Position(3022, 3275, 0), new Position(3013, 3276, 0), new Position(3006, 3289, 0), new Position(3005, 3304, 0), new Position(3007, 3323, 0), new Position(3007, 3341, 0), new Position(3007, 3359, 0)})),
    ALKHARID_TO_VARROCK_EBANK(new BotRoute(new Position[]{new Position(3273, 3167, 0), new Position(3280, 3179, 0), new Position(3282, 3195, 0), new Position(3285, 3206, 0), new Position(3285, 3214, 0), new Position(3277, 3226, 0), new Position(3277, 3243, 0), new Position(3277, 3262, 0), new Position(3276, 3278, 0), new Position(3275, 3292, 0), new Position(3275, 3305, 0), new Position(3275, 3320, 0), new Position(3284, 3329, 0), new Position(3292, 3335, 0), new Position(3303, 3335, 0), new Position(3300, 3347, 0), new Position(3298, 3358, 0), new Position(3298, 3373, 0), new Position(3292, 3388, 0), new Position(3291, 3398, 0), new Position(3288, 3415, 0), new Position(3279, 3424, 0), new Position(3273, 3427, 0), new Position(3254, 3428, 0)})),
    VARROCK_CENTER_TO_EBANK(new BotRoute(new Position[]{new Position(3221, 3429, 0), new Position(3237, 3430, 0), new Position(3253, 3428, 0)})),
    VARROCK_CENTER_TO_WBANK(new BotRoute(new Position[]{new Position(3205, 3428, 0), new Position(3190, 3430, 0), new Position(3182, 3432, 0)})),
    LUMBE_TO_VARROCK_CENTER(new BotRoute(new Position[]{new Position(3259, 3230, 0), new Position(3259, 3241, 0), new Position(3251, 3254, 0), new Position(3247, 3270, 0), new Position(3239, 3284, 0), new Position(3239, 3299, 0), new Position(3243, 3309, 0), new Position(3253, 3322, 0), new Position(3268, 3329, 0), new Position(3263, 3332, 0), new Position(3247, 3336, 0), new Position(3228, 3337, 0), new Position(3225, 3351, 0), new Position(3216, 3362, 0), new Position(3214, 3369, 0), new Position(3211, 3376, 0), new Position(3211, 3394, 0), new Position(3211, 3412, 0), new Position(3211, 3424, 0)})),
    LUMBE_TO_ALKHARID(2883, new BotRoute[]{new BotRoute(new Position[]{new Position(3267, 3228, 0)}), new BotRoute(new Position[]{new Position(3268, 3228, 0), new Position(3269, 3214, 0), new Position(3269, 3203, 0), new Position(3269, 3189, 0), new Position(3270, 3178, 0), new Position(3275, 3176, 0), new Position(3273, 3167, 0)})}),
    LUMBW_TO_LUMBE(new BotRoute(new Position[]{new Position(3237, 3226, 0), new Position(3254, 3226, 0)})),
    FALA_CENTER_TO_TAVERLEY(1597, new BotRoute[]{new BotRoute(new Position[]{new Position(2965, 3386, 0), new Position(2964, 3402, 0), new Position(2959, 3413, 0), new Position(2949, 3432, 0), new Position(2945, 3447, 0), new Position(2936, 3450, 0)}), new BotRoute(new Position[]{new Position(2935, 3450, 0), new Position(2920, 3455, 0), new Position(2907, 3455, 0), new Position(2897, 3455, 0)})}),
    TAVERLEY_TO_CATHERBY(new BotRoute(new Position[]{new Position(2890, 3445, 0), new Position(2888, 3430, 0), new Position(2882, 3428, 0), new Position(2873, 3430, 0), new Position(2866, 3442, 0), new Position(2863, 3458, 0), new Position(2855, 3470, 0), new Position(2856, 3482, 0), new Position(2862, 3496, 0), new Position(2856, 3508, 0), new Position(2846, 3508, 0), new Position(2841, 3500, 0), new Position(2850, 3495, 0), new Position(2850, 3488, 0), new Position(2848, 3477, 0), new Position(2844, 3466, 0), new Position(2848, 3453, 0), new Position(2855, 3442, 0), new Position(2853, 3437, 0), new Position(2843, 3435, 0), new Position(2834, 3436, 0), new Position(2825, 3437, 0), new Position(2810, 3435, 0)})),
    CATHERBY_TO_CAMELOT(new BotRoute(new Position[]{new Position(2798, 3433, 0), new Position(2791, 3435, 0), new Position(2779, 3446, 0), new Position(2770, 3458, 0), new Position(2764, 3462, 0), new Position(2757, 3477, 0)})),
    CAMELOT_TO_SEERS_VILLAGE(new BotRoute(new Position[]{new Position(2745, 3479, 0), new Position(2732, 3485, 0)})),
    SEERS_VILLAGE_TO_ARDYN(new BotRoute(new Position[]{new Position(2717, 3485, 0), new Position(2701, 3484, 0), new Position(2684, 3483, 0), new Position(2679, 3478, 0), new Position(2679, 3470, 0), new Position(2673, 3465, 0), new Position(2671, 3457, 0), new Position(2660, 3446, 0), new Position(2648, 3435, 0), new Position(2645, 3418, 0), new Position(2645, 3403, 0), new Position(2645, 3388, 0), new Position(2644, 3375, 0), new Position(2636, 3372, 0), new Position(2636, 3356, 0), new Position(2636, 3338, 0)})),
    ARDYN_TO_NBANK(new BotRoute(new Position[]{new Position(2626, 3336, 0), new Position(2617, 3337, 0)})),
    ARDYN_TO_ARDY_CENTER(new BotRoute(new Position[]{new Position(2644, 3332, 0), new Position(2647, 3328, 0), new Position(2654, 3314, 0)})),
    ARDY_CENTER_TO_SBANK(new BotRoute(new Position[]{new Position(2652, 3306, 0), new Position(2642, 3305, 0), new Position(2638, 3300, 0), new Position(2643, 3294, 0), new Position(2644, 3284, 0), new Position(2650, 3284, 0)}));

    private int routeNpcId = -1;
    private BotRoute route;
    private BotRoute[] segments;

    private BotWorldRoute(BotRoute botRoute) {
        this.route = botRoute;
    }
    private BotWorldRoute(int routeNpcId, BotRoute[] botRouteArray) {
        this.routeNpcId = routeNpcId;
        this.segments = botRouteArray;
    }

    public final BotRoute getRoute() {
        return this.route;
    }

    public final BotRoute[] getSegments() {
        return this.segments;
    }

    public final int getRouteNpcId() {
        return this.routeNpcId;
    }

    public final int getDistance() {
        if (this.route != null) {
            return this.route.getDistance();
        }
        BotRoute[] botRouteArray = this.segments;
        int index = 0;
        int index2 = 0;
        while (index2 < botRouteArray.length) {
            index += botRouteArray[index2].getDistance();
            if (index2 < botRouteArray.length - 1) {
                index += GameUtil.getDistance(botRouteArray[index2].getEndPosition(), botRouteArray[index2 + 1].getStartPosition());
            }
            ++index2;
        }
        return index;
    }

    public final Position getStartPosition() {
        if (this.route != null) {
            return this.route.getStartPosition();
        }
        BotRoute[] botRouteArray = this.segments;
        return this.segments[0].waypoints[0];
    }

    public final Position getEndPosition() {
        if (this.route != null) {
            return this.route.getEndPosition();
        }
        BotRoute[] botRouteArray = this.segments;
        BotRoute botRoute = this.segments[botRouteArray.length - 1];
        return botRouteArray[botRouteArray.length - 1].waypoints[botRoute.waypoints.length - 1];
    }
}

