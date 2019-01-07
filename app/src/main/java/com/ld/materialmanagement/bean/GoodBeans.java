package com.ld.materialmanagement.bean;

import java.util.ArrayList;

/**
 * Created by BillTian on 2017/10/24.
 */

public class GoodBeans<T> {

    public int recordsTotal; //几条数

    public ArrayList<T> data;
    @Override
    public String toString() {
        return "GoodBeans{" +
                "recordsTotal=" + recordsTotal +
                ", data=" + data +
                '}';
    }

}
