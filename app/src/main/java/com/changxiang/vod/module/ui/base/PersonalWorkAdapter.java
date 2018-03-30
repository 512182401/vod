package com.changxiang.vod.module.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 15976 on 2016/11/10.
 */

public class PersonalWorkAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList;//ViewPager中显示的Fragment
    List<String> list;//TabLayout显示的标题

    public PersonalWorkAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> list) {
        super(fm);
        this.fragmentList=fragmentList;
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList==null?0:fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
