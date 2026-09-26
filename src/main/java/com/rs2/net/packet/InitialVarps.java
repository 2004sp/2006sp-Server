package com.rs2.net.packet;

import com.rs2.model.GameplayHelper;
import com.rs2.model.objects.functions.FlourMillHandler;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ErnestTheChickenQuest;

/** Sends the semantically verified revision 443 object-morph varps on login. */
public final class InitialVarps {
    private static final int[] VERIFIED_VARPS = {
        33, 452, 453, 491, 502, 503, 504, 505, 506, 507, 508, 509,
        511, 512, 515, 668, 674, 695,
        // Native combat interfaces read 43 for style/spell attack and 301 for
        // special-attack selection. Autocast state is bit 8 of varp 439
        // (varbit 2668). The lamp-skill picker reads 261 with the same 1..22
        // skill-selection values used by the server.
        43, 153, 261, 301, 439,
        // Player options and bank controls retain these varp identities in 443.
        115, 166, 168, 169, 170, 171, 172, 173, 287, 304, 427,
        // Revision 443 area-sound slider.
        872
    };
    private static final int ERNEST_LEVER_VARP = 33;
    private static final int BARROWS_TOMB_VARP = 452;
    private static final int BARROWS_TUNNEL_VARP = 453;
    private static final int CANOE_VARP = 674;

    private InitialVarps() {
    }

    static boolean isVerified(int id) {
        for (int verified : VERIFIED_VARPS) {
            if (verified == id) return true;
        }
        return false;
    }

    public static void send(Player player) {
        if (player == null || player.isBot) {
            return;
        }

        player.packetSender.refreshAutocastConfig();
        player.packetSender.refreshSpecialAttackConfig();

        // Ernest the Chicken: lever state (33) and its derived basement-door state (668).
        player.packetSender.sendConfig(ERNEST_LEVER_VARP,
                player.configStates[ERNEST_LEVER_VARP]);
        ErnestTheChickenQuest.refreshBasementLeverDoorConfig(player);

        // Barrows morph state. 452 is persisted; 453 is the current tunnel/chest state.
        player.packetSender.sendConfig(BARROWS_TOMB_VARP,
                player.configStates[BARROWS_TOMB_VARP]);
        player.packetSender.sendConfig(BARROWS_TUNNEL_VARP,
                player.configStates[BARROWS_TUNNEL_VARP]);

        // Varp 491 morphs the runecrafting ruins for the currently equipped tiara.
        GameplayHelper.refreshRunecraftingTiaraConfig(player,
                player.getEquipmentManager().getItemIdAtSlot(0));

        // Farming configs are packed from the loaded patch-manager state rather than
        // copied from configStates. These cover 502-509, 511, 512, and 515.
        player.getTreePatchManager().refreshConfig();
        player.getFruitTreePatchManager().refreshConfig();
        player.getAllotmentPatchManager().refreshConfig();
        player.getHopsPatchManager().refreshConfig();
        player.getSpecialTreePatchManager().refreshConfig();
        player.getFlowerPatchManager().refreshConfig();
        player.getBushPatchManager().refreshConfig();
        player.getCompostBinManager().refreshConfig();
        player.getSpecialCropPatchManager().refreshConfig();
        player.getHerbPatchManager().refreshConfig();

        // Canoe-tree and flour-bin morph state.
        player.packetSender.sendConfig(CANOE_VARP, player.configStates[CANOE_VARP]);
        player.packetSender.sendConfig(FlourMillHandler.flourBinConfigId,
                player.configStates[FlourMillHandler.flourBinConfigId]);
    }
}
