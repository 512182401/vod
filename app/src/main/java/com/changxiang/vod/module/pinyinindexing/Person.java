package com.changxiang.vod.module.pinyinindexing;


import com.changxiang.vod.module.db.Singers;

public class Person implements Comparable<Person> {

	private String name;//显示
	private String pinyin;//拼音首字母：
	private Singers mSingerPY;


	public Person(String name, Singers mSingerPY) {
		super();
		this.name = name;
		this.mSingerPY =mSingerPY;
		this.pinyin = PinYinUtils.getPinyin(name);
	}

	public Person() {
		super();
	}

	public Singers getmSingerPY() {
		return mSingerPY;
	}

	public void setmSingerPY(Singers mSingerPY) {
		this.mSingerPY = mSingerPY;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Override
	public int compareTo(Person arg0) {
		return pinyin.compareTo(arg0.getPinyin());
	}

}
