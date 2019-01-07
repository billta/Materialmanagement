package com.ld.materialmanagement.bean;

/**
 * Created by BillTian on 2017/9/21.
 */

public class LoanRed {
    public String count;
    public String oldcount;
    public String GoodsId;
    public String history;

    @Override
    public String toString() {
        return "LoanRed{" +
                "count='" + count + '\'' +
                ", oldcount='" + oldcount + '\'' +
                ", GoodsId='" + GoodsId + '\'' +
                ", history='" + history + '\'' +
                '}';
    }
}
