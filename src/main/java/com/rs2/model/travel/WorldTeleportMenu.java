package com.rs2.model.travel;

import com.rs2.model.Position;
import com.rs2.model.player.Player;

/**
 * Developer convenience teleport selector opened with ::tp.
 *
 * The matching client defines a custom bank-style interface at 19600 using
 * the bank frame and Grand Exchange confirm-button artwork.
 */
public final class WorldTeleportMenu {
    private static final String INTERFACE_ACTION = "world_teleport_menu";

    public static final int ROOT_INTERFACE_ID = 19600;
    private static final int FIRST_DESTINATION_BUTTON_ID = 19610;
    private static final int DESTINATIONS_PER_PAGE = 9;
    private static final int PAGE_BUTTON_ID = 19619;
    private static final int CLOSE_BUTTON_ID = 19620;
    private static final int BACK_BUTTON_ID = 19621;
    private static final int FIRST_DESTINATION_TEXT_ID = 19630;
    private static final int PAGE_TEXT_ID = 19639;
    private static final int BACK_TEXT_ID = 19604;
    private static final int SUBTITLE_TEXT_ID = 19603;

    private static final Destination[] DESTINATIONS = new Destination[]{
        new Destination("Lumbridge", 3222, 3218, 0),
        new Destination("Varrock", 3210, 3424, 0),
        new Destination("Falador", 2964, 3378, 0),
        new Destination("Draynor Village", 3093, 3244, 0),
        new Destination("Al Kharid", 3293, 3174, 0),
        new Destination("Edgeville", 3087, 3496, 0),
        new Destination("Barbarian Village", 3082, 3420, 0),
        new Destination("Port Sarim", 3029, 3217, 0),
        new Destination("Rimmington", 2957, 3214, 0),

        new Destination("Taverley", 2894, 3457, 0),
        new Destination("Burthorpe", 2899, 3544, 0),
        new Destination("Catherby", 2804, 3434, 0),
        new Destination("Seers' Village", 2725, 3485, 0),
        new Destination("Ardougne", 2662, 3305, 0),
        new Destination("Yanille", 2606, 3093, 0),
        new Destination("Gnome Stronghold", 2461, 3444, 0),
        new Destination("Brimhaven", 2758, 3178, 0),
        new Destination("Shilo Village", 2852, 2954, 0),

        new Destination("Canifis", 3496, 3487, 0),
        new Destination("Rellekka", 2668, 3631, 0),
        new Destination("Karamja", 2918, 3176, 0),
        new Destination("Pollnivneach", 3359, 2968, 0),
        new Destination("Nardah", 3421, 2892, 0),
        new Destination("Ape Atoll", 2755, 2784, 0)
    };

    private WorldTeleportMenu() {
    }

    public static void open(Player player) {
        if (player == null) {
            return;
        }

        player.resetInteractionState();
        player.interfaceAction = INTERFACE_ACTION;
        player.temporaryActionValue = 0;
        showPage(player, true);
    }

    public static boolean handleButton(Player player, int buttonId) {
        if (player == null
                || !INTERFACE_ACTION.equals(player.interfaceAction)
                || player.getOpenInterfaceId() != ROOT_INTERFACE_ID) {
            return false;
        }

        if (buttonId == CLOSE_BUTTON_ID) {
            player.interfaceAction = "";
            player.packetSender.closeInterfaces();
            return true;
        }

        int pageCount = getPageCount();
        int page = normalizePage(player.temporaryActionValue, pageCount);

        if (buttonId == BACK_BUTTON_ID) {
            if (page > 0) {
                player.temporaryActionValue = page - 1;
                showPage(player, false);
            }
            return true;
        }

        if (buttonId == PAGE_BUTTON_ID) {
            if (page + 1 < pageCount) {
                player.temporaryActionValue = page + 1;
                showPage(player, false);
            }
            return true;
        }

        if (buttonId < FIRST_DESTINATION_BUTTON_ID
                || buttonId >= FIRST_DESTINATION_BUTTON_ID + DESTINATIONS_PER_PAGE) {
            return false;
        }

        int optionIndex = buttonId - FIRST_DESTINATION_BUTTON_ID;
        int destinationIndex = page * DESTINATIONS_PER_PAGE + optionIndex;
        if (destinationIndex < 0 || destinationIndex >= DESTINATIONS.length) {
            return true;
        }

        Destination destination = DESTINATIONS[destinationIndex];
        player.interfaceAction = "";
        player.moveTo(new Position(destination.x, destination.y, destination.plane));
        player.packetSender.sendGameMessage("Teleported to " + destination.name + ".");
        return true;
    }

    private static void showPage(Player player, boolean openInterface) {
        int pageCount = getPageCount();
        int page = normalizePage(player.temporaryActionValue, pageCount);
        player.temporaryActionValue = page;

        player.packetSender.sendInterfaceText(
                "Page " + (page + 1) + " of " + pageCount + " - Select a destination",
                SUBTITLE_TEXT_ID);

        int firstDestination = page * DESTINATIONS_PER_PAGE;
        for (int optionIndex = 0; optionIndex < DESTINATIONS_PER_PAGE; ++optionIndex) {
            int destinationIndex = firstDestination + optionIndex;
            boolean available = destinationIndex < DESTINATIONS.length;
            String text = available ? DESTINATIONS[destinationIndex].name : "";
            int buttonId = FIRST_DESTINATION_BUTTON_ID + optionIndex;
            int textId = FIRST_DESTINATION_TEXT_ID + optionIndex;
            player.packetSender.sendInterfaceText(text, textId);
            player.packetSender.setInterfaceHiddenFlag(available ? 0 : 1, buttonId);
            player.packetSender.setInterfaceHiddenFlag(available ? 0 : 1, textId);
        }

        boolean hasPreviousPage = page > 0;
        boolean hasNextPage = page + 1 < pageCount;

        player.packetSender.sendInterfaceText(hasPreviousPage ? "<lt>- Back" : "", BACK_TEXT_ID);
        player.packetSender.setInterfaceHiddenFlag(hasPreviousPage ? 0 : 1, BACK_BUTTON_ID);
        player.packetSender.setInterfaceHiddenFlag(hasPreviousPage ? 0 : 1, BACK_TEXT_ID);

        player.packetSender.sendInterfaceText(hasNextPage ? "Next ->" : "", PAGE_TEXT_ID);
        player.packetSender.setInterfaceHiddenFlag(hasNextPage ? 0 : 1, PAGE_BUTTON_ID);
        player.packetSender.setInterfaceHiddenFlag(hasNextPage ? 0 : 1, PAGE_TEXT_ID);

        if (openInterface) {
            player.packetSender.showInterface(ROOT_INTERFACE_ID);
        }
    }

    private static int getPageCount() {
        return (DESTINATIONS.length + DESTINATIONS_PER_PAGE - 1) / DESTINATIONS_PER_PAGE;
    }

    private static int normalizePage(int page, int pageCount) {
        if (page < 0 || page >= pageCount) {
            return 0;
        }
        return page;
    }

    private static final class Destination {
        private final String name;
        private final int x;
        private final int y;
        private final int plane;

        private Destination(String name, int x, int y, int plane) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.plane = plane;
        }
    }
}
