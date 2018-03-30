package com.changxiang.vod.module.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.LinkedList;


public class FragmentStack {
	private FragmentActivity mContext;
	public LinkedList<Fragment> frgList = new LinkedList<Fragment>();

	public FragmentStack(FragmentActivity ctx) {
		mContext = ctx;
	}

	public void pushFragment(Fragment frg) {
		if (frg == null)
			return;
		frgList.add(frg);
		replaceFrament(frg);
	}

	public void pushFragmentWithAnim(Fragment frg) {
		if (frg == null)
			return;
		frgList.add(frg);
		replaceWithAmin(frg);
	}

	public boolean popFragment() {
		if (frgList.isEmpty())
			return false;
		frgList.removeFirst();
		if (!frgList.isEmpty()) {
			replaceWithAmin(frgList.getFirst());
			return true;
		}
		return false;
	}

	public void pushRootFragment(Fragment frg) {
		if (frg == null)
			return;
		frgList.clear();
		frgList.addFirst(frg);
		replaceFrament(frg);
	}

	private void replaceFrament(Fragment frg) {
//		mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container, frg, "fragment")
//				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
	}

	private void replaceWithAmin(Fragment frg) {
//		if (frg == null)
//			return;
//		mContext.getSupportFragmentManager()
//				.beginTransaction()
//				.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit, R.anim.fragment_slide_right_enter,
//						R.anim.fragment_slide_right_exit).replace(R.id.container, frg, "fragment")
//				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
	}
}
