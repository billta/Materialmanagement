package com.ld.materialmanagement.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 *
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments;
    private final String[] mFragmentTitles;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mFragments, String[] mFragmentTitles) {
        super(fm);
        this.mFragments = mFragments;
        this.mFragmentTitles = mFragmentTitles;
    }

    public void addFragment(Fragment fragment, String title) {
        //mFragments.add(fragment);
        //mFragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles[position];

    }
}
