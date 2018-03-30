package com.changxiang.vod.module.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class AppManager {

	private Stack<Activity> activityStack;
	private static AppManager manager;

	public static AppManager getInstance() {
		if (manager == null) {
			manager = new AppManager();
		}
		return manager;
	}

	/**
	 * 添加activity到堆栈,只保证堆栈当中只有一个同样的activity
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<>();
		}
		if(activityStack.contains(activity)){
			activityStack.remove(activity);
		}
		activityStack.add(activity);
	}

	/**
	 * 获取当前的activity，最后一个被压入堆栈当中的activity
	 * 
	 * @return
	 */
	public Activity getCurrentActivity() {
		if (activityStack != null&&!activityStack.isEmpty()) {
			return activityStack.lastElement();
		}
		return null;
	}

	/**
	 * 结束当前的activity
	 */
	public void finishCurrentActivity() {
		if (activityStack != null) {
			finishActivity(activityStack.lastElement());
		}
	}

	/**
	 * 结束指定的activity
	 * 
	 * @param activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的activity
	 * 
	 * @param clazz
	 *            Class
	 */
	public void finishActivityByClz(Class<?> clazz) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(clazz)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有的activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (activityStack.get(i) != null) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}
	/**
	 * 获取所有的activity
	 * @return List< BaseActivity \>
	 */
	public List<Activity> getAllActivity(){
		List<Activity> list=new ArrayList<>();
		for(Activity activity:activityStack){
			list.add((Activity) activity);
		}
		return list;
	}
	/**
	 * 获取指定activity名称的activity
	 * @param name
	 * @return
	 */
	public Activity getActivityByName(String name){
		for(Activity activity:activityStack){
			if(activity.getClass().getName().indexOf(name)>=0){
				return (Activity) activity;
			}
		}
		return null;
	}
	/**
	 * 退出应用程序
	 * @param context
	 */
	public void exitApp(Context context){
		try {
			finishAllActivity();
			ActivityManager acm=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			//TODO
//			MobclickAgent.onKillProcess(context);
			acm.killBackgroundProcesses(context.getPackageName());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
