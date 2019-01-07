package com.ld.materialmanagement.bean;

/**
 * Created by BillTian on 2017/10/25.
 */

public class LocationBean {
    private int Value;
    private String Text;

    @Override
    public String toString() {
        return "LocationBean{" +
                "Value=" + Value +
                ", Text='" + Text + '\'' +
                '}';
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }

    public String getText() {

        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
