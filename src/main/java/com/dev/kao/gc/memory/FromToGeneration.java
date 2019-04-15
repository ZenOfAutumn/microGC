package com.dev.kao.gc.memory;


/**
 * From == To
 *
 * @author wuliang
 * @create 2019-04-10
 */
public interface FromToGeneration extends Generation {

    int from();

    int to();

    int free();

}
