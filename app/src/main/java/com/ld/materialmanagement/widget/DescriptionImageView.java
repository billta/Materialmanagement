package com.ld.materialmanagement.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
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
 * 带标题的描述图片控件
 */
public class DescriptionImageView extends RelativeLayout {


    private TextView mTitle;
    private ImageView mDesc;


    private String titleText;
    private boolean status;

    public DescriptionImageView(Context context) {
        super(context);
        //加载视图的布局
        LayoutInflater.from(context).inflate(R.layout.view_image_description, this);
    }

    public DescriptionImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_image_description, this);


        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DescriptionImageView);
        titleText = a.getString(R.styleable.DescriptionImageView_titText);
        status  = a.getBoolean(R.styleable.DescriptionImageView_descStatus,false);
        //回收资源，这一句必须调用
        a.recycle();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTitle = (TextView) findViewById(R.id.tv_title);
        mDesc = (ImageView) findViewById(R.id.iv_state);

        setTitleText(titleText);
        setDescState(status);

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
     * 设置使用哪张图片
     *
     * @param flag
     */
    public void setDescState(boolean flag) {
        mDesc.setImageResource(flag ? R.drawable.ic_yes : R.drawable.ic_no);
    }


}
