package com.ld.materialmanagement.activity;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.utils.StringUtil;

import butterknife.BindView;

public class AboutActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_version_name)
    TextView mTvVersionName;

    @Override
    protected int getContentView() {
        return R.layout.activity_about;
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("关于");
    }


    @Override
    protected void initData() {
        mTvVersionName.setText(StringUtil.getVersionName());
    }
}
