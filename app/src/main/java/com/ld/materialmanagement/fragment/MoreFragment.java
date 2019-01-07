package com.ld.materialmanagement.fragment;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.activity.AboutActivity;
import com.ld.materialmanagement.activity.SettingsActivity;
import com.ld.materialmanagement.activity.StockingActivity;
import com.ld.materialmanagement.application.AppConst;
import com.ld.materialmanagement.widget.SolarSystemView;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import zxing.activity.CaptureActivity;

/**
 * 更多
 */
public class MoreFragment extends BaseFragment {

    @BindView(R.id.rl_stocking)
    LinearLayout mStocking;
    @BindView(R.id.user_view_solar_system)
    SolarSystemView mSolarSystem;

    @BindView(R.id.iv_portrait)
    CircleImageView mCirclePortrait;
    @BindView(R.id.user_info_icon_container)
    FrameLayout mFlUserInfoIconContainer;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.rl_show_my_info)
    LinearLayout mRlShowInfo;

    @BindView(R.id.rl_setting)
    LinearLayout rlSetting;
    @BindView(R.id.rl_about)
    LinearLayout rlAbout;


    private int mMaxRadius;
    private int mR;
    private float mPx;
    private float mPy;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_more;
    }

    @OnClick({
            R.id.rl_stocking,
            R.id.rl_setting,
            R.id.rl_about,
            R.id.rl_loan_in,
            R.id.rl_loan_out
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_stocking:
                mActivity.startActivity(new Intent(mActivity, StockingActivity.class));
                break;

            case R.id.rl_setting:
                mActivity.startActivity(new Intent(mActivity, SettingsActivity.class));
                break;

            case R.id.rl_about:
                mActivity.startActivity(new Intent(mActivity, AboutActivity.class));
                break;
            case R.id.rl_loan_out:
                //借工具
                Intent intent = new Intent(mActivity, CaptureActivity.class);
                intent.putExtra("scan", CaptureActivity.LOAN_OUT);
                mActivity.startActivity(intent);
                break;
            case R.id.rl_loan_in:
                // 还工具
                Intent in = new Intent(mActivity, CaptureActivity.class);
                in.putExtra("scan", CaptureActivity.LOAN_IN);
                mActivity.startActivity(in);
                break;
        }
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);

        if (mSolarSystem != null)
            mSolarSystem.accelerate();


        View root = rootView;
        if (root != null) {
            root.post(new Runnable() {
                @Override
                public void run() {

                    if (mRlShowInfo == null)
                        return;
                    int width = mRlShowInfo.getWidth();
                    float rlShowInfoX = mRlShowInfo.getX();

                    int height = mFlUserInfoIconContainer.getHeight();
                    float y1 = mFlUserInfoIconContainer.getY();

                    float x = mCirclePortrait.getX();
                    float y = mCirclePortrait.getY();
                    int portraitWidth = mCirclePortrait.getWidth();

                    mPx = x + +rlShowInfoX + (width >> 1);
                    mPy = y1 + y + (height - y) / 2;
                    mMaxRadius = (int) (mSolarSystem.getHeight() - mPy + 250);
                    mR = (portraitWidth >> 1);

                    updateSolar(mPx, mPy);

                }
            });
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        tvUser.setText(mActivity
                .getSharedPreferences(AppConst.User.FILE, Context.MODE_PRIVATE)
                .getString(AppConst.User.KEY_USER_NAME, ""));
    }

    private void updateSolar(float px, float py) {

        SolarSystemView solarSystemView = mSolarSystem;
        Random random = new Random(System.currentTimeMillis());
        int maxRadius = mMaxRadius;
        int r = mR;
        solarSystemView.clear();
        for (int i = 40, radius = r + i; radius <= maxRadius; i = (int) (i * 1.4), radius += i) {
            SolarSystemView.Planet planet = new SolarSystemView.Planet();
            planet.setClockwise(random.nextInt(10) % 2 == 0);
            planet.setAngleRate((random.nextInt(35) + 1) / 1000.f);
            planet.setRadius(radius);
            solarSystemView.addPlanets(planet);

        }
        solarSystemView.setPivotPoint(px, py);
    }
}
