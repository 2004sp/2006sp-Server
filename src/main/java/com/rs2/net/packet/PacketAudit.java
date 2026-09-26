package com.rs2.net.packet;

import com.rs2.ServerSettings;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Strict diagnostics for legacy server behaviour that still crosses the
 * revision-443 compatibility boundary. This class never changes packet data.
 */
public final class PacketAudit {
    private static final Logger LOGGER = Logger.getLogger(PacketAudit.class.getName());
    private static final Set<String> REPORTED = Collections.newSetFromMap(
            new ConcurrentHashMap<String, Boolean>());
    private static final int MAX_PAYLOAD_LENGTH = 220;

    private PacketAudit() {
    }

    public static boolean enabled() {
        return ServerSettings.clientBuild == 443 && ServerSettings.revision443AuditMode;
    }

    public static void interfaceComponent(int legacyId, int mappedId, String payload) {
        if (!enabled()) return;
        report("component", legacyId, mappedId, payload);
    }

    public static void interfaceGroup(int legacyId, int mappedId, String payload) {
        if (!enabled() || legacyId == -1) return;
        report("group", legacyId, mappedId, payload);
    }

    public static void legacyVarp(int legacyId, int value, boolean verified) {
        if (!enabled()) return;
        String caller = caller();
        String sender = packetSenderMethod();
        String status = verified ? "verified" : "SUPPRESSED";
        String key = "varp:" + legacyId + ":" + sender + ":" + caller + ":" + status;
        if (!REPORTED.add(key)) return;
        LOGGER.warning("[443-AUDIT] Legacy config/varp update: caller=" + caller
                + " sender=PacketSender." + sender + " legacyVarp=" + legacyId
                + " status=" + status + " value=" + value);
    }

    public static void legacySkill(int legacyId, int mappedId, String payload) {
        if (!enabled()) return;
        reportGeneric("Legacy skill translation", "skill", legacyId,
                Integer.toString(mappedId), payload);
    }

    private static void report(String kind, int legacyId, int mappedId, String payload) {
        String mapped;
        if (mappedId == InterfaceBridge.UNMAPPED) {
            mapped = "UNMAPPED";
        } else if (kind.equals("component") && (mappedId >>> 16) != 0) {
            mapped = (mappedId >>> 16) + ":" + (mappedId & 65535);
        } else if (mappedId == legacyId) {
            mapped = "flat:" + mappedId;
        } else {
            mapped = Integer.toString(mappedId);
        }
        reportGeneric(operationName(packetSenderMethod()), kind, legacyId, mapped, payload);
    }

    private static void reportGeneric(String operation, String kind, int legacyId,
                                      String mapped, String payload) {
        String caller = caller();
        String sender = packetSenderMethod();
        String key = operation + ":" + kind + ":" + legacyId + ":" + mapped
                + ":" + sender + ":" + caller;
        if (!REPORTED.add(key)) return;
        String idLabel = kind.equals("group") ? "legacyGroup" :
                kind.equals("skill") ? "legacySkill" : "legacyComponent";
        StringBuilder message = new StringBuilder("[443-AUDIT] ")
                .append(operation).append(": caller=").append(caller)
                .append(" sender=PacketSender.").append(sender)
                .append(' ').append(idLabel).append('=').append(legacyId)
                .append(" mapped443=").append(mapped);
        if (payload != null && payload.length() != 0) {
            message.append(' ').append(sanitize(payload));
        }
        LOGGER.warning(message.toString());
    }

    private static String sanitize(String payload) {
        String value = payload.replace("\\", "\\\\")
                .replace("\r", "\\r").replace("\n", "\\n");
        if (value.length() > MAX_PAYLOAD_LENGTH) {
            value = value.substring(0, MAX_PAYLOAD_LENGTH) + "...";
        }
        return value;
    }

    private static String packetSenderMethod() {
        for (StackTraceElement frame : Thread.currentThread().getStackTrace()) {
            if (frame.getClassName().equals(PacketSender.class.getName())) {
                return frame.getMethodName();
            }
        }
        return "unknown";
    }

    private static String caller() {
        for (StackTraceElement frame : Thread.currentThread().getStackTrace()) {
            String className = frame.getClassName();
            if (!className.startsWith("com.rs2.")) continue;
            if (className.equals(PacketAudit.class.getName())
                    || className.equals(InterfaceBridge.class.getName())
                    || className.equals(PacketSender.class.getName())) continue;
            return shortClassName(className) + "." + frame.getMethodName()
                    + ":" + frame.getLineNumber();
        }
        return "unknown";
    }

    private static String shortClassName(String className) {
        int dot = className.lastIndexOf('.');
        return dot < 0 ? className : className.substring(dot + 1);
    }

    private static String operationName(String sender) {
        if (sender.equals("sendInterfaceText")) return "Legacy interface text update";
        if (sender.equals("sendInterfaceTextColor")) return "Legacy interface text-color update";
        if (sender.equals("sendInterfaceModel") || sender.equals("sendInterfaceModelId")) return "Legacy interface model update";
        if (sender.equals("sendInterfaceModelRotation")) return "Legacy interface model-rotation update";
        if (sender.equals("sendInterfaceAnimation")) return "Legacy interface animation update";
        if (sender.equals("sendPlayerHeadOnInterface")) return "Legacy player-head interface update";
        if (sender.equals("sendNpcHeadOnInterface")) return "Legacy NPC-head interface update";
        if (sender.equals("setInterfaceHiddenFlag") || sender.equals("setInterfaceVisible")) return "Legacy interface visibility update";
        if (sender.equals("sendInterfaceOffset") || sender.equals("sendInterfacePosition")) return "Legacy interface position update";
        if (sender.equals("sendInterfaceScrollPosition")) return "Legacy interface scroll update";
        if (sender.equals("sendRevision443InterfaceSlotItem")) return "Legacy interface slot-item update";
        if (sender.equals("sendItemContainer")) return "Legacy item-container update";
        if (sender.equals("setSidebarInterface")) return "Legacy sidebar interface update";
        if (sender.equals("showInterface")) return "Legacy interface open";
        if (sender.equals("showWalkableInterface")) return "Legacy walkable-interface open";
        if (sender.equals("showInterfaceWithInventory")) return "Legacy interface+inventory open";
        if (sender.equals("showChatboxInterface")) return "Legacy chatbox-interface open";
        if (sender.equals("closeInterface")) return "Legacy interface close";
        return "Legacy interface translation (" + sender + ")";
    }
}
