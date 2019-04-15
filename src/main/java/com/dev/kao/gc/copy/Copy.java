package com.dev.kao.gc.copy;

import com.dev.kao.gc.domain.Obj;
import com.dev.kao.gc.memory.BasicFromToGeneration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wuliang
 * @create 2019-04-12
 */
public abstract class Copy {


    private List<Obj> roots;

    public List<Obj> getRoots() {
        return roots;
    }

    public void setRoots(List<Obj> roots) {
        this.roots = roots;
    }

    public int linkToRoot(Obj obj) {
        return linkToRoot(0, obj);
    }

    public int linkToRoot(int index, Obj obj) {
        if (roots == null || index > roots.size()) {
            return -1;
        }
        Obj root = roots.get(index);
        if (root.getChildren() == null) {
            root.setChildren(new ArrayList<>());
        }

        root.getChildren().add(obj);
        return 0;
    }

    public int unlinkToRoot(String name) {
        return unlinkToRoot(0, name);
    }

    public int unlinkToRoot(int index, String name) {
        if (roots == null || index > roots.size()) {
            return -1;
        }
        Obj root = roots.get(index);

        List<Obj> children = root.getChildren();
        if (children == null) {
            return -1;
        }

        Iterator<Obj> iterator = children.iterator();
        while (iterator.hasNext()) {
            Obj obj = iterator.next();
            if (obj.getName().equals(name)) {
                iterator.remove();
                return 0;
            }
        }

        return -1;
    }

    abstract void copying();

    abstract Obj copy(Obj obj);

    abstract Obj copyData(Obj obj);
}
