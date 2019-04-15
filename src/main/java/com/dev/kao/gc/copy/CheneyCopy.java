package com.dev.kao.gc.copy;

import com.dev.kao.gc.domain.Obj;
import com.dev.kao.gc.memory.BasicFromToGeneration;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Based on  <<A nonrecursive list compacting algorithm>>
 *
 * @author wuliang
 * @create 2019-04-12
 */
public class CheneyCopy extends BasicCopy {


    public CheneyCopy(int memorySize) {
        super(memorySize);
    }

    @Override
    public Obj copy(Obj obj) {
        if(!isPointerToHeap(obj.getForwarding())){
            Obj copy = copyData(obj);
            obj.setForwarding(copy);
            memory.setFree(memory.free() + obj.getSize());
        }
        return obj.getForwarding();
    }



    private boolean isPointerToHeap(Obj obj) {
        if(obj == null){
            return false;
        }
        int toHeapEnd = memory.to() == 0 ? memory.from() - 1 : memory.size();
        return memory.to() <= obj.getStartIndex() && memory.to() <= toHeapEnd;
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

    @Override
    public void copying() {
        memory.setFree(memory.to());

        // 1.first copy all object directed link to roots
        Queue<Obj> scanObjQueue = new LinkedList<>();
        for (Obj root : getRoots()) {
            List<Obj> children = root.getChildren();
            for (int i = 0; i < children.size(); i++) {
                children.set(i, copy(children.get(i)));
            }
            scanObjQueue.addAll(root.getChildren());
        }

        // 2.copy rest object with bfs search
        while (scanObjQueue.size() != 0) {
            Obj scanObj = scanObjQueue.poll();
            List<Obj> children = scanObj.getChildren();
            for (int i = 0; i < children.size(); i++) {
                children.set(i, copy(children.get(i)));
            }
            scanObjQueue.addAll(scanObj.getChildren());
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
}
