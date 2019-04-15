package com.dev.kao.gc.memory;

/**
 * @author wuliang
 * @create 2019-04-10
 */
public class BasicFromToGeneration implements FromToGeneration {

    private int from;

    private int to;

    private int free;

    private byte[] heap;

    public BasicFromToGeneration(int size) {
        this.heap = new byte[size];
        this.from = 0;
        this.free = 0;
        this.to = size / 2;
    }

    @Override
    public int from() {
        return this.from;
    }

    @Override
    public int to() {
        return this.to;
    }

    public byte[] heap() {
        return this.heap;
    }

    @Override
    public int free() {
        return this.free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    @Override
    public int size() {
        return heap.length;
    }

    public void swap() {
        int tmp = from;
        this.from = to;
        this.to = tmp;
    }
}
