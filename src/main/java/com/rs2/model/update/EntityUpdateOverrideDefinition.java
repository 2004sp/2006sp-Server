/*
 * Recovered from javap bytecode after CFR failed whole-class enum analysis.
 */
package com.rs2.model.update;

public enum EntityUpdateOverrideDefinition {
    WHIP(1080, 1658, EntityUpdateOverrideType.a);

    private int replacementId;
    private int originalId;
    private EntityUpdateOverrideType type;

    private EntityUpdateOverrideDefinition(int replacementId, int originalId, EntityUpdateOverrideType type) {
        this.replacementId = replacementId;
        this.originalId = originalId;
        this.type = type;
    }

    public static EntityUpdateOverrideDefinition forOriginalIdAndType(int originalId, EntityUpdateOverrideType type) {
        EntityUpdateOverrideDefinition[] definitions = EntityUpdateOverrideDefinition.values();
        int count = definitions.length;
        int index = 0;
        while (index < count) {
            EntityUpdateOverrideDefinition definition = definitions[index];
            if (definition.originalId == originalId && type == definition.type) {
                return definition;
            }
            ++index;
        }
        return null;
    }

    public final int getReplacementId() {
        return this.replacementId;
    }
}
