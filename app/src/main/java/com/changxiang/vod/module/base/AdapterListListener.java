package com.changxiang.vod.module.base;


public interface AdapterListListener<T> {
	void click(int opt, int position, T item);
}