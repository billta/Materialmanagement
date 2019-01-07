package com.ld.materialmanagement.bean;

/**
 * Created by BillTian on 2017/10/24.
 */

public class StoreKeeperBean {
    public int Value;
    public String Text;

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    @Override
    public String toString() {
        return "StoreKeeperBean{" +
                "Value=" + Value +
                ", Text='" + Text + '\'' +
                '}';
    }

}
