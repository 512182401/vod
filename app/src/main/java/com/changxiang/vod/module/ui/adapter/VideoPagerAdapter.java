package com.changxiang.vod.module.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;


import com.changxiang.vod.module.ui.base.FragmentVideo1;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 15976 on 2016/10/6.
 */

public class VideoPagerAdapter extends FragmentPagerAdapter {

    private LinkedList<FragmentVideo1> list=new LinkedList<>();

    public VideoPagerAdapter(FragmentManager fm, List<FragmentVideo1> list) {
        super(fm);
        if (list==null){
            throw new NullPointerException("List can't be null");
        }else if (list.size()>1){

            /*FragmentVideo1 fragment1=new FragmentVideo1();
            fragment1.setImgUrl(list.get(list.size()-1).getImgUrl());
            this.list.add(fragment1);*/
            for (FragmentVideo1 fragment:list){
                this.list.add(fragment);
            }
            /*FragmentVideo1 fragmentN=new FragmentVideo1();
            fragmentN.setImgUrl(list.get(0).getImgUrl());
            this.list.add(fragmentN);*/
        }
    }

    @Override
    public Fragment getItem(int position) {

        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
///       return super.instantiateItem(container, position);
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
//        if(fragment instanceof xxxFragment){
////            mTags = fragment.getTag();
////            Log.e("MyFragmentPagerAdapter instantiateItem", mTags);
//        }
        return fragment;
    }
}
