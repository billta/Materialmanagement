package com.ld.materialmanagement.bean;

/**
 * Created by Airmour@163.com on 2017/3/7
 */
public class SecondChart {

  //  {"lend":4,"back":3,"OutData":1,"InData":2}
    public int lend;
    public int back;
    public int total;
    public int InData;
    public int OutData;

    public int getLend() {
        return lend;
    }

    public void setLend(int lend) {
        this.lend = lend;
    }

    public int getBack() {
        return back;
    }

    public void setBack(int back) {
        this.back = back;
    }

    public int getTotal() {
        return lend + back;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getInData() {
        return InData;
    }

    public int getOutData() {
        return OutData;
    }

    public void setInData(int inData) {
        InData = inData;
    }

    public void setOutData(int outData) {
        OutData = outData;
    }

    @Override
    public String toString() {
        return "SecondChart{" +
                "lend=" + lend +
                ", back=" + back +
                ", total=" + total +
                ", InData=" + InData +
                ", OutData=" + OutData +
                '}';
    }

}
