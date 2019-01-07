package com.ld.materialmanagement.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.adapter.ViewPagerAdapter;
import com.ld.materialmanagement.application.AppConst;
import com.ld.materialmanagement.utils.L;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;

import static com.ld.materialmanagement.R.id.viewpager;

/**
 * ___/|
 * \o.O|   a cat
 * (___)0
 * U
 * Created by Airmour@163.com on 2017/4/26.
 */

public class InFragment extends BaseFragment {
    @BindView(R.id.toolbar_tab)
    TabLayout mTab;
    @BindView(viewpager)
    ViewPager mViewPager;
    private ViewPagerAdapter vpAdapter;


    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_in;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new InLibraryFragment());
        fragments.add(new OutLibraryFragment());

        final String[] titles = {"入库","出库"};

        vpAdapter = new ViewPagerAdapter(this.getChildFragmentManager(), fragments, titles);
//        L.e("入库......"+titles[0]);
//        L.e("出库......"+titles[1]);

        mViewPager.setAdapter(vpAdapter);
        mViewPager.setOffscreenPageLimit(0); //设置缓存

        mTab.setupWithViewPager(mViewPager);


        //获取sharedpreference
        final SharedPreferences sp=getActivity().getSharedPreferences(AppConst.FLAG_CHECKED, Context.MODE_PRIVATE);

        mTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabs= (String) tab.getText();

                Toast.makeText(getActivity(),"选中"+tab.getText(),Toast.LENGTH_SHORT).show();


                sp.edit().putString(AppConst.FLAG_CHECKED,tabs).apply();

                L.e("tabs.........."+tabs);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Toast.makeText(getActivity(),"未选中"+tab.getText(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                String tabs= (String) tab.getText();
//                Toast.makeText(getActivity(),"复选中"+tab.getText(),Toast.LENGTH_SHORT).show();
//                sp.edit().putString(AppConst.FLAG_CHECKED,tabs).apply();
//
//                L.e("tabs.........."+tabs);
            }
        });
        //设置默认选中
        mViewPager.setCurrentItem(1);
        mTab.getTabAt(1).select();
    }



    @Override
    public void onResume() {
        super.onResume();
        Logger.i("onResume");
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        Logger.i("onLazyLoad");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i("onDestroy");
    }
}
