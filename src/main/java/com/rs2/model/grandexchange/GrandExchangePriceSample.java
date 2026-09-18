package com.rs2.model.grandexchange;

import com.rs2.model.grandexchange.GrandExchangeOffer;
import com.rs2.model.grandexchange.GrandExchangePriceSampleTimestampComparator;
import com.rs2.model.item.ItemDefinition;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.CountingDataOutputStream;
import com.rs2.util.FileUtil;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class GrandExchangePriceSample {
    long timestampMillis;
    private int itemId;
    private int quantity;
    private int unitPrice;
    private static ArrayList allSamples = new ArrayList();
    private static ArrayList sampledItemIds = new ArrayList();

    GrandExchangePriceSample(int itemId, int quantity, int unitPrice) {
        this.timestampMillis = System.currentTimeMillis();
        this.itemId = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        ItemDefinition itemDefinition = ItemDefinition.forId(itemId);
        if (!sampledItemIds.contains(itemId)) {
            sampledItemIds.add(itemId);
        }
        itemDefinition.grandExchangePriceSamples.add(this);
        allSamples.add(this);
        try {
            GrandExchangePriceSample.savePriceSamples();
            return;
        }
        catch (IOException iOException) {
            IOException iOException2 = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    GrandExchangePriceSample(GrandExchangeOffer grandExchangeOffer) {
        this.timestampMillis = System.currentTimeMillis();
        this.itemId = grandExchangeOffer.itemId;
        this.quantity = grandExchangeOffer.quantity;
        this.unitPrice = grandExchangeOffer.unitPrice;
        ItemDefinition itemDefinition = ItemDefinition.forId(this.itemId);
        if (!sampledItemIds.contains(this.itemId)) {
            sampledItemIds.add(this.itemId);
        }
        itemDefinition.grandExchangePriceSamples.add(this);
        allSamples.add(this);
        try {
            GrandExchangePriceSample.savePriceSamples();
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
    }

    private GrandExchangePriceSample(long timestampMillis, int itemId, int quantity, int unitPrice) {
        this.timestampMillis = timestampMillis;
        this.itemId = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        ItemDefinition itemDefinition = ItemDefinition.forId(itemId);
        if (!sampledItemIds.contains(itemId)) {
            sampledItemIds.add(itemId);
        }
        itemDefinition.grandExchangePriceSamples.add(this);
        allSamples.add(this);
    }

    public static int getAveragePrice(int value8) {
        long value2 = 0L;
        long value3 = 0L;
        Object value4 = ItemDefinition.forId(value8);
        Iterator iterator = ((ItemDefinition)value4).grandExchangePriceSamples.iterator();
        while (iterator.hasNext()) {
            Object value5 = value4 = (GrandExchangePriceSample)iterator.next();
            long value6 = ((GrandExchangePriceSample)value4).quantity;
            value5 = value4;
            long value7 = ((GrandExchangePriceSample)value5).unitPrice;
            value2 += value6 * value7;
            value3 += value6;
        }
        if (value2 == 0L || value3 == 0L) {
            return -1;
        }
        return (int)(value2 / value3);
    }

    public static void loadPriceSamples() {
        allSamples.clear();
        if (!FileUtil.exists("./data/geOfferData.dat")) {
            return;
        }
        Object value = FileUtil.readBytes("./data/geOfferData.dat");
        ByteArrayReader byteArrayReader = new ByteArrayReader((byte[])value);
        value = byteArrayReader;
        int value2 = byteArrayReader.readInt();
        int index = 0;
        while (index < value2) {
            long value3 = ((ByteArrayReader)value).readLong();
            int value4 = ((ByteArrayReader)value).readUnsignedShort();
            int value5 = ((ByteArrayReader)value).readInt();
            int value6 = ((ByteArrayReader)value).readInt();
            new GrandExchangePriceSample(value3, value4, value5, value6);
            ++index;
        }
        Iterator iterator = sampledItemIds.iterator();
        while (iterator.hasNext()) {
            int integer = (Integer)iterator.next();
            value = ItemDefinition.forId(integer);
            Collections.sort(((ItemDefinition)value).grandExchangePriceSamples, new GrandExchangePriceSampleTimestampComparator());
            double value7 = 0.0;
            Iterator iterator2 = ((ItemDefinition)value).grandExchangePriceSamples.iterator();
            while (iterator2.hasNext()) {
                GrandExchangePriceSample grandExchangePriceSample;
                GrandExchangePriceSample grandExchangePriceSample2 = grandExchangePriceSample = (GrandExchangePriceSample)iterator2.next();
                value7 += (double)grandExchangePriceSample2.quantity;
            }
            double value8 = value7 * 0.75;
            index = (int)value8;
            ArrayList<GrandExchangePriceSample> arrayList = new ArrayList<GrandExchangePriceSample>();
            int index2 = 0;
            for (Object grandExchangePriceSampleObject : ((ItemDefinition)value).grandExchangePriceSamples) {
                GrandExchangePriceSample grandExchangePriceSample = (GrandExchangePriceSample)grandExchangePriceSampleObject;
                if (index2 >= index) {
                    arrayList.add(grandExchangePriceSample);
                    continue;
                }
                GrandExchangePriceSample grandExchangePriceSample3 = grandExchangePriceSample;
                index2 += grandExchangePriceSample3.quantity;
            }
            for (GrandExchangePriceSample grandExchangePriceSample : arrayList) {
                ((ItemDefinition)value).grandExchangePriceSamples.remove(grandExchangePriceSample);
                allSamples.remove(grandExchangePriceSample);
            }
        }
    }

    private static void savePriceSamples() throws IOException {
        CountingDataOutputStream countingDataOutputStream = new CountingDataOutputStream(new FileOutputStream("./data/geOfferData.dat"));
        countingDataOutputStream.writeInt(allSamples.size());
        Iterator iterator = allSamples.iterator();
        while (iterator.hasNext()) {
            GrandExchangePriceSample grandExchangePriceSample;
            GrandExchangePriceSample grandExchangePriceSample2 = grandExchangePriceSample = (GrandExchangePriceSample)iterator.next();
            countingDataOutputStream.writeLong(grandExchangePriceSample2.timestampMillis);
            grandExchangePriceSample2 = grandExchangePriceSample;
            countingDataOutputStream.writeShort(grandExchangePriceSample2.itemId);
            grandExchangePriceSample2 = grandExchangePriceSample;
            countingDataOutputStream.writeInt(grandExchangePriceSample2.quantity);
            grandExchangePriceSample2 = grandExchangePriceSample;
            countingDataOutputStream.writeInt(grandExchangePriceSample2.unitPrice);
        }
        countingDataOutputStream.close();
    }
}

