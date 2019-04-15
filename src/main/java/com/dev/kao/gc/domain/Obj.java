package com.dev.kao.gc.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存基础对象
 *
 * @author wuliang
 * @create 2019-04-10
 */
public class Obj {

    private String name;

    private int tag;

    private int startIndex;

    private int size;

    private Obj forwarding;

    private List<Obj> children = new ArrayList<>();

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Obj getForwarding() {
        return forwarding;
    }

    public void setForwarding(Obj forwarding) {
        this.forwarding = forwarding;
    }

    public List<Obj> getChildren() {
        return children;
    }

    public void setChildren(List<Obj> children) {
        this.children = children;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
