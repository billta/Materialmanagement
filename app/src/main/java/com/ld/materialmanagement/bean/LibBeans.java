package com.ld.materialmanagement.bean;

import java.util.ArrayList;

/**
 * ___/|
 * \o.O|   a cat
 * (___)
 * U
 * Created by Airmour@163.com on 2017/3/9.
 * <p>
 * 入库单的bean 对应JSon
 */

public class LibBeans<T> {


    public int recordsTotal; //几条数

    public int recordsFiltered;

    public ArrayList<T> data;

    @Override
    public String toString() {
        return "InLibBean{" +
                "recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", data=" + data +
                '}';
    }

}
