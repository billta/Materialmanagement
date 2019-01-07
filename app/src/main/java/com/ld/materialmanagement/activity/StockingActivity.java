package com.ld.materialmanagement.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ld.materialmanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 盘点的界面
 */
public class StockingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.im_no_data)
    ImageView mNoData;
    @BindView(R.id.activity_stocking)
    LinearLayout mActivity;
    @BindView(R.id.text_title)
    TextView mTextTitle;

    @Override
    protected int getContentView() {
        return R.layout.activity_stocking;
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void initWidget() {
        mTextTitle.setText(R.string.stocking);
    }


}
