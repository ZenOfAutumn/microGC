package com.dev.kao.gc.mutator;

import com.dev.kao.gc.copy.BasicCopy;
import com.dev.kao.gc.copy.CheneyCopy;
import com.dev.kao.gc.domain.Obj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wuliang
 * @create 2019-04-11
 */
public class CheneyMutator {

    static CheneyCopy copy;

    static {
        List<Obj> roots = new ArrayList<>();
        Obj root = new Obj();
        roots.add(root);
        copy = new CheneyCopy(12);
        copy.setRoots(roots);
    }


    public static void main(String[] args) throws Exception{

        Obj a = copy.newObj(new byte[]{1,1});
        a.setName("A");
        copy.linkToRoot(a);

        Obj b = copy.newObj(new byte[]{2,2});
        b.setName("B");
        a.getChildren().add(b);


        Obj c = copy.newObj(new byte[]{3});
        c.setName("C");
        copy.linkToRoot(c);

        System.out.println(Arrays.toString(copy.getMemory().heap()));

        Obj d = copy.newObj(new byte[]{4});
        System.out.println(Arrays.toString(copy.getMemory().heap()));

        Obj e = copy.newObj(new byte[]{5});
        System.out.println(Arrays.toString(copy.getMemory().heap()));
        copy.linkToRoot(e);

        copy.unlinkToRoot("A");
        Obj f = copy.newObj(new byte[]{6});
        System.out.println(Arrays.toString(copy.getMemory().heap()));

        Obj g = copy.newObj(new byte[]{7,7,7});
        System.out.println(Arrays.toString(copy.getMemory().heap()));

        e.getChildren().add(g);
        Obj h = copy.newObj(new byte[]{8});
        System.out.println(Arrays.toString(copy.getMemory().heap()));


    }

}
