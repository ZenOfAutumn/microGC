package com.dev.kao.gc.copy;

import com.dev.kao.gc.domain.Obj;
import com.dev.kao.gc.memory.BasicFromToGeneration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Based On <<A LISP Garbage-Collector for Virtual-Memory Computer Systems>>
 *
 * @author wuliang
 * @create 2019-04-10
 */
public class BasicCopy extends Copy{

    private static final int COPIED = 1;

    protected BasicFromToGeneration memory;

    public BasicCopy(int memorySize) {
        this.memory = new BasicFromToGeneration(memorySize);
    }

    public Obj newObj(int size) {

        if (memory.free() + size > memory.from() + memory.size() / 2) {
            // trigger gc
            copying();
        }

        if (memory.free() + size > memory.from() + memory.size() / 2) {
            allocationFail();
        }

        int startIndex = memory.free();
        memory.setFree(startIndex + size);
        Obj obj = new Obj();
        obj.setStartIndex(startIndex);
        obj.setSize(size);
        return obj;
    }

    public Obj newObj(byte[] data) {
        Obj obj = newObj(data.length);
        for (int i = 0; i < data.length; i++) {
            memory.heap()[obj.getStartIndex() + i] = data[i];
        }
        return obj;
    }

    private void allocationFail() {
        throw new IllegalStateException("fail to allocate memory");
    }


    @Override
    public void copying() {
        memory.setFree(memory.to());
        for (Obj root : getRoots()) {
            List<Obj> children = root.getChildren();
            for (int i = 0; i < children.size(); i++) {
                children.set(i, copy(children.get(i)));
            }
        }
        memory.swap();
        // clear heap for print
        clearForPrint();
    }


    private void clearForPrint() {
        int end = memory.to() < memory.from() ? memory.from() : memory.size();
        for (int i = memory.to(); i < end; i++) {
            memory.heap()[i] = 0;
        }
    }


    @Override
    public Obj copy(Obj obj) {
        if (obj.getTag() != COPIED) {
            Obj copy = copyData(obj);
            obj.setTag(COPIED);
            obj.setForwarding(copy);
            memory.setFree(memory.free() + obj.getSize());
            List<Obj> children = copy.getChildren();
            if (children != null && children.size() != 0) {
                for (int i = 0; i < children.size(); i++) {
                    children.set(i, copy(children.get(i)));
                }
            }
            return copy;
        } else {
            return obj.getForwarding();
        }

    }

    @Override
    public Obj copyData(Obj obj) {
        Obj copyObj = new Obj();
        copyObj.setChildren(obj.getChildren());
        copyObj.setStartIndex(memory.free());
        copyObj.setSize(obj.getSize());
        copyObj.setName(obj.getName());
        for (int i = 0; i < obj.getSize(); i++) {
            memory.heap()[memory.free() + i] = memory.heap()[obj.getStartIndex() + i];
        }
        return copyObj;

    }


    public BasicFromToGeneration getMemory() {
        return memory;
    }
}
