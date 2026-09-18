package com.rs2.net;

public final class IsaacCipher {
    private int count = 0;
    private int[] results = new int[256];
    private int[] memory = new int[256];
    private int accumulator;
    private int lastResult;
    private int counter;

    public IsaacCipher(int[] integerValues2) {
        int index = 0;
        while (index < 4) {
            this.results[index] = integerValues2[index];
            ++index;
        }
        this.initialize(true);
    }

    public final int nextInt() {
        if (this.count-- == 0) {
            this.generateResults();
            this.count = 255;
        }
        return this.results[this.count];
    }

    private void generateResults() {
        int value;
        int value2;
        this.lastResult += ++this.counter;
        int index = 0;
        int value3 = 128;
        while (index < 128) {
            value2 = this.memory[index];
            this.accumulator ^= this.accumulator << 13;
            this.accumulator += this.memory[value3++];
            this.memory[index] = value = this.memory[(value2 & 0x3FC) >> 2] + this.accumulator + this.lastResult;
            this.results[index++] = this.lastResult = this.memory[(value >> 8 & 0x3FC) >> 2] + value2;
            value2 = this.memory[index];
            this.accumulator ^= this.accumulator >>> 6;
            this.accumulator += this.memory[value3++];
            this.memory[index] = value = this.memory[(value2 & 0x3FC) >> 2] + this.accumulator + this.lastResult;
            this.results[index++] = this.lastResult = this.memory[(value >> 8 & 0x3FC) >> 2] + value2;
            value2 = this.memory[index];
            this.accumulator ^= this.accumulator << 2;
            this.accumulator += this.memory[value3++];
            this.memory[index] = value = this.memory[(value2 & 0x3FC) >> 2] + this.accumulator + this.lastResult;
            this.results[index++] = this.lastResult = this.memory[(value >> 8 & 0x3FC) >> 2] + value2;
            value2 = this.memory[index];
            this.accumulator ^= this.accumulator >>> 16;
            this.accumulator += this.memory[value3++];
            this.memory[index] = value = this.memory[(value2 & 0x3FC) >> 2] + this.accumulator + this.lastResult;
            this.results[index++] = this.lastResult = this.memory[(value >> 8 & 0x3FC) >> 2] + value2;
        }
        value3 = 0;
        while (value3 < 128) {
            value2 = this.memory[index];
            this.accumulator ^= this.accumulator << 13;
            this.accumulator += this.memory[value3++];
            this.memory[index] = value = this.memory[(value2 & 0x3FC) >> 2] + this.accumulator + this.lastResult;
            this.results[index++] = this.lastResult = this.memory[(value >> 8 & 0x3FC) >> 2] + value2;
            value2 = this.memory[index];
            this.accumulator ^= this.accumulator >>> 6;
            this.accumulator += this.memory[value3++];
            this.memory[index] = value = this.memory[(value2 & 0x3FC) >> 2] + this.accumulator + this.lastResult;
            this.results[index++] = this.lastResult = this.memory[(value >> 8 & 0x3FC) >> 2] + value2;
            value2 = this.memory[index];
            this.accumulator ^= this.accumulator << 2;
            this.accumulator += this.memory[value3++];
            this.memory[index] = value = this.memory[(value2 & 0x3FC) >> 2] + this.accumulator + this.lastResult;
            this.results[index++] = this.lastResult = this.memory[(value >> 8 & 0x3FC) >> 2] + value2;
            value2 = this.memory[index];
            this.accumulator ^= this.accumulator >>> 16;
            this.accumulator += this.memory[value3++];
            this.memory[index] = value = this.memory[(value2 & 0x3FC) >> 2] + this.accumulator + this.lastResult;
            this.results[index++] = this.lastResult = this.memory[(value >> 8 & 0x3FC) >> 2] + value2;
        }
    }

    private void initialize(boolean seeded) {
        int value = -1640531527;
        int value2 = -1640531527;
        int value3 = -1640531527;
        int value4 = -1640531527;
        int value5 = -1640531527;
        int value6 = -1640531527;
        int value7 = -1640531527;
        int value8 = -1640531527;
        int index = 0;
        while (index < 4) {
            value5 += (value8 ^= value7 << 11);
            value7 += value6;
            value4 += (value7 ^= value6 >>> 2);
            value6 += value5;
            value3 += (value6 ^= value5 << 8);
            value5 += value4;
            value2 += (value5 ^= value4 >>> 16);
            value4 += value3;
            value += (value4 ^= value3 << 10);
            value3 += value2;
            value8 += (value3 ^= value2 >>> 4);
            value2 += value;
            value7 += (value2 ^= value << 8);
            value += value8;
            value6 += (value ^= value8 >>> 9);
            value8 += value7;
            ++index;
        }
        index = 0;
        while (index < 256) {
            value8 += this.results[index];
            value7 += this.results[index + 1];
            value6 += this.results[index + 2];
            value5 += this.results[index + 3];
            value4 += this.results[index + 4];
            value3 += this.results[index + 5];
            value2 += this.results[index + 6];
            value += this.results[index + 7];
            value5 += (value8 ^= value7 << 11);
            value7 += value6;
            value4 += (value7 ^= value6 >>> 2);
            value6 += value5;
            value3 += (value6 ^= value5 << 8);
            value5 += value4;
            value2 += (value5 ^= value4 >>> 16);
            value4 += value3;
            value += (value4 ^= value3 << 10);
            value3 += value2;
            value8 += (value3 ^= value2 >>> 4);
            value2 += value;
            value7 += (value2 ^= value << 8);
            value += value8;
            value6 += (value ^= value8 >>> 9);
            this.memory[index] = value8 += value7;
            this.memory[index + 1] = value7;
            this.memory[index + 2] = value6;
            this.memory[index + 3] = value5;
            this.memory[index + 4] = value4;
            this.memory[index + 5] = value3;
            this.memory[index + 6] = value2;
            this.memory[index + 7] = value;
            index += 8;
        }
        index = 0;
        while (index < 256) {
            value8 += this.memory[index];
            value7 += this.memory[index + 1];
            value6 += this.memory[index + 2];
            value5 += this.memory[index + 3];
            value4 += this.memory[index + 4];
            value3 += this.memory[index + 5];
            value2 += this.memory[index + 6];
            value += this.memory[index + 7];
            value5 += (value8 ^= value7 << 11);
            value7 += value6;
            value4 += (value7 ^= value6 >>> 2);
            value6 += value5;
            value3 += (value6 ^= value5 << 8);
            value5 += value4;
            value2 += (value5 ^= value4 >>> 16);
            value4 += value3;
            value += (value4 ^= value3 << 10);
            value3 += value2;
            value8 += (value3 ^= value2 >>> 4);
            value2 += value;
            value7 += (value2 ^= value << 8);
            value += value8;
            value6 += (value ^= value8 >>> 9);
            this.memory[index] = value8 += value7;
            this.memory[index + 1] = value7;
            this.memory[index + 2] = value6;
            this.memory[index + 3] = value5;
            this.memory[index + 4] = value4;
            this.memory[index + 5] = value3;
            this.memory[index + 6] = value2;
            this.memory[index + 7] = value;
            index += 8;
        }
        this.generateResults();
        this.count = 256;
    }
}

