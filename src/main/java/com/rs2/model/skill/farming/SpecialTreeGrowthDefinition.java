package com.rs2.model.skill.farming;

import java.util.HashMap;
import java.util.Map;

public enum SpecialTreeGrowthDefinition {
    SPIRIT_TREE(5375, (String[][])new String[][]{{"The spirit tree sapling has only just been planted.", "It has not grown yet."}, {"The spirit tree has grown slightly, and sprouted a few more leaves."}, {"Some dark spots have appeared on the trees trunk,", "and the leaves have grown longer."}, {"The tree has grown larger in all respects, and has grown more leaves."}, {"The spirit tree base has widened showing some roots,", "and the leaves have morphed into a small canopy.", "Two small branches have appeared on either side of the trunk."}, {"The spirit tree has grown wider in girth, but is still the same height", "as before. The base is larger as well."}, {"The spirit tree has grown larger in all respects. The trunk", "is more warped towards the west, and the roots are more visible."}, {"The spirit tree is larger in all respects.", "The trunk has grown in a 'S' shape."}, {"The spirit tree has grown another knob on the trunk which will", "eventually become its nose. The tree is larger in all respects,", " and its branches are growing out to the sides more."}, {"The spirit tree canopy shifts the angle its inclining towards,", " and its branches are almost parallel to the ground.", "The nose is more defined, and the tree is slightly larger."}, {"The spirit tree branches are slightly angling towards the ground,", "and it is slightly larger than before."}, {"The spirit tree branches are slightly angling towards the ground,", "and it is slightly larger than before."}, {"The spirit tree canopy is smaller, the face is fully defined", "and the texture of the tree has changed dramatically", "The Spirit tree is ready to be checked."}}),
    CALQUAT(5503, (String[][])new String[][]{{"The calquat sapling has only just been planted."}, {"The calquat tree grows another segment taller."}, {"The calquat tree grows another segment taller."}, {"The calquat tree grows another segment taller."}, {"The calquat tree grows another segment longer", "and starts a branch midway up its trunk."}, {"The calquat tree grows some leaves."}, {"The calquat tree grows another segment upside down", "and grows leaves on its mid-branch."}, {"The calquat tree grows towards the ground."}, {"The calquat tree grows towards the ground."}, {"The calquat tree is ready to be harvested."}});

    private int treeId;
    private String[][] growthMessages;
    private static Map definitionsByTreeId;

    static {
        definitionsByTreeId = new HashMap();
        SpecialTreeGrowthDefinition[] specialTreeGrowthDefinitionArray = SpecialTreeGrowthDefinition.values();
        int length = specialTreeGrowthDefinitionArray.length;
        int index = 0;
        while (index < length) {
            SpecialTreeGrowthDefinition specialTreeGrowthDefinition = specialTreeGrowthDefinitionArray[index];
            definitionsByTreeId.put(specialTreeGrowthDefinition.treeId, specialTreeGrowthDefinition);
            ++index;
        }
    }
    private SpecialTreeGrowthDefinition(int treeId, String[][] growthMessages) {
        this.treeId = treeId;
        this.growthMessages = growthMessages;
    }

    public static SpecialTreeGrowthDefinition forTreeId(int value2) {
        return (SpecialTreeGrowthDefinition)((Object)definitionsByTreeId.get(value2));
    }

    public final String[][] getGrowthMessages() {
        return this.growthMessages;
    }
}

