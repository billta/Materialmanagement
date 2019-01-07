package com.ld.materialmanagement.bean;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.fragment.HomeFragment;
import com.ld.materialmanagement.fragment.InFragment;
import com.ld.materialmanagement.fragment.MoreFragment;
import com.ld.materialmanagement.fragment.OutFragment;
import com.ld.materialmanagement.fragment.SparePartTotalFragment;


/**
 * Created by Airmour@163.com on 2017/2/26
 *
 * app的TabHost
 */

public enum MainTab {


    //首页
    HOME(0,R.string.main_tab_name_home, R.drawable.tab_home_selector, HomeFragment.class),
    //备件
    BackUp(0, R.string.main_tab_name_backup, R.drawable.tab_backup_selector, SparePartTotalFragment.class),

    //入库
    In(0, R.string.main_tab_name_outLib, R.drawable.tab_inlib_selector, InFragment.class),

    //出库
    Out(0, R.string.main_tab_name_inLib, R.drawable.tab_outlib_selector, OutFragment.class),

    //更多
    MORE(0,R.string.main_tab_name_more, R.drawable.tab_more_selector, MoreFragment.class);


    public int idx;
    public int resName;
    public int resIcon;
    public Class<?> clazz;

    MainTab(int idx, int resName, int resIcon, Class<?> clazz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clazz = clazz;
    }
}
