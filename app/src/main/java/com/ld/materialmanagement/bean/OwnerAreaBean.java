package com.ld.materialmanagement.bean;

/**
 * Created by BillTian on 2017/11/1.
 */

public class OwnerAreaBean {
 private String Text;

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    @Override
    public String toString() {
        return "OwnerAreaBean{" +
                "Text='" + Text + '\'' +
                '}';
    }
}
