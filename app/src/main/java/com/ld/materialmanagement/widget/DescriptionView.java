package com.ld.materialmanagement.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ld.materialmanagement.R;

/**
 * ___/|
 * \o.O|   a cat
 * (___)
 * U
 * Created by Airmour@163.com on 2017/3/9.
 * <p>
 * 带标题的描述控件
 */
public class DescriptionView extends RelativeLayout {

    private TextView mTitle;
    private TextView mDesc;

    private String titleText;
    private String descText;
    private int titleTextColor;
    private float titleTextSize;
    private float descTextSize;
    private int descTextColor;
    private int maxEms;

    public DescriptionView(Context context) {
        super(context);
        //加载视图的布局
        LayoutInflater.from(context).inflate(R.layout.view_description, this);
    }

    public DescriptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载视图的布局
        LayoutInflater.from(context).inflate(R.layout.view_description, this);

        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DescriptionView);
        titleText = a.getString(R.styleable.DescriptionView_titleText);
        descText = a.getString(R.styleable.DescriptionView_descText);
        titleTextColor = a.getColor(R.styleable.DescriptionView_titleTextColor, Color.rgb(173, 173, 173));
        titleTextSize = a.getDimension(R.styleable.DescriptionView_titleTextSize, 10f);

        descTextColor = a.getColor(R.styleable.DescriptionView_descTextColor, Color.argb(240,0,0,0));
        descTextSize = a.getDimension(R.styleable.DescriptionView_descTextSize, 15f);
        maxEms = a.getInteger(R.styleable.DescriptionView_maxEms, 8);
        //回收资源，这一句必须调用
        a.recycle();

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTitle = (TextView) findViewById(R.id.tv_title);
        mDesc = (TextView) findViewById(R.id.tv_description);

        mDesc.setTextColor(descTextColor);

        setDescTextSize(descTextSize);
        setTitleText(titleText);
        setDescText(descText);
        setMaxEms(maxEms);

    }

    public void setDescTextSize(float descTextSize) {

        mDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP,descTextSize);
    }


    /**
     * 设置左上角的文字
     *
     * @param title
     */
    public void setTitleText(String title) {
        mTitle.setText(title);
    }

    /**
     * 设置右下角的文字
     *
     * @param desc
     */
    public void setDescText(String desc) {
        mDesc.setText(desc);
    }

    /**
     * 设置最多显示字数
     *
     * @param maxEms
     */
    public void setMaxEms(int maxEms) {
        mDesc.setMaxEms(maxEms);
    }
}
