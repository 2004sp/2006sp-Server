package com.rs2.model.skill.farming;

import com.rs2.model.Position;
import java.util.HashMap;
import java.util.Map;

public enum CompostBin {
    FALADOR(0, new Position(3056, 3312, 0)),
    CATHERBY(1, new Position(2804, 3464, 0)),
    MORYTANIA(2, new Position(3610, 3522, 0)),
    ARDOUGNE(3, new Position(2661, 3375, 0));

    private int index;
    private Position position;
    private static Map binByIndex;

    static {
        binByIndex = new HashMap();
        CompostBin[] compostBinArray = CompostBin.values();
        int length = compostBinArray.length;
        int index = 0;
        while (index < length) {
            CompostBin compostBin = compostBinArray[index];
            binByIndex.put(compostBin.index, compostBin);
            ++index;
        }
    }
    private CompostBin(int index, Position position) {
        this.index = index;
        this.position = position;
    }

    public static CompostBin forPosition(Position position) {
        CompostBin[] compostBinArray = CompostBin.values();
        int length = compostBinArray.length;
        int index = 0;
        while (index < length) {
            CompostBin compostBin = compostBinArray[index];
            if (compostBin.position.equals(position)) {
                return compostBin;
            }
            ++index;
        }
        return null;
    }

    public static CompostBin forInteractionPosition(Position position) {
        CompostBin exact = forPosition(position);
        if (exact != null) {
            return exact;
        }
        for (CompostBin compostBin : CompostBin.values()) {
            if (Math.abs(compostBin.position.getX() - position.getX()) <= 2
                    && Math.abs(compostBin.position.getY() - position.getY()) <= 2) {
                return compostBin;
            }
        }
        return null;
    }

    public final int getIndex() {
        return this.index;
    }
}

