package com.dev.kao.gc.mutator;

import com.dev.kao.gc.copy.BasicCopy;
import com.dev.kao.gc.domain.Obj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wuliang
 * @create 2019-04-11
 */
public class Mutator {

    static BasicCopy basicCopy;

    static {
        List<Obj> roots = new ArrayList<>();
        Obj root = new Obj();
        roots.add(root);
        basicCopy = new BasicCopy(10);
        basicCopy.setRoots(roots);
    }


    public static void main(String[] args) throws Exception{

        Obj a = basicCopy.newObj(new byte[]{1,1});
        a.setName("A");
        if(basicCopy.linkToRoot(a)!=0){
            throw new Exception();
        }

        Obj b = basicCopy.newObj(new byte[]{2,2});
        b.setName("B");

        System.out.println(Arrays.toString(basicCopy.getMemory().heap()));

        Obj c = basicCopy.newObj(new byte[]{3,3});
        c.setName("C");

        System.out.println(Arrays.toString(basicCopy.getMemory().heap()));
        a.getChildren().add(c);

        Obj d = basicCopy.newObj(new byte[]{4});
        System.out.println(Arrays.toString(basicCopy.getMemory().heap()));

        Obj e = basicCopy.newObj(new byte[]{5});
        System.out.println(Arrays.toString(basicCopy.getMemory().heap()));
        basicCopy.linkToRoot(e);

        basicCopy.unlinkToRoot("A");
        Obj f = basicCopy.newObj(new byte[]{6});
        System.out.println(Arrays.toString(basicCopy.getMemory().heap()));

        Obj g = basicCopy.newObj(new byte[]{7,7,7});
        System.out.println(Arrays.toString(basicCopy.getMemory().heap()));

        e.getChildren().add(g);
        Obj h = basicCopy.newObj(new byte[]{8});
        System.out.println(Arrays.toString(basicCopy.getMemory().heap()));


    }

}
