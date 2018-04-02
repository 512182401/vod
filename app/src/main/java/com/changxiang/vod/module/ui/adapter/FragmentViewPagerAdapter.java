package com.changxiang.vod.module.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    List<String> list;//TabLayout显示的标题
    public FragmentViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> list) {
        super(fm);  
        this.list = list;
        this.fragmentList=fragmentList;
          
    }  
      
      
    @Override
    public int getCount() {  
        return fragmentList.size();
    }  
      
    @Override
    public Fragment getItem(int arg0) {
        return fragmentList.get(arg0);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
      
      
      
}  