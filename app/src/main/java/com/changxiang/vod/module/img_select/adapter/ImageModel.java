package com.changxiang.vod.module.img_select.adapter;

import android.graphics.Bitmap;

import com.changxiang.vod.common.view.editimage.StickerItem;

import java.io.Serializable;
import java.util.LinkedHashMap;

//import com.quchangkeji.tosing.module.editimage.view.StickerItem;

public class ImageModel implements Serializable {
	public String id;
	public String imageUrl;
	public boolean isSelected;//是否选择标志
	private String edpath;//编辑之后的路径
	private boolean isedit;//是否已经编辑
	private boolean isdelect;//是否删除标志
	private boolean isupload;//是否已经上传
	private Bitmap cacheBitmap;//临时bitmap；
	LinkedHashMap<Integer, StickerItem> bank;//贴纸对象列表

	public ImageModel() {
	}

	public ImageModel(String id, String imageUrl, boolean isSelected, String edpath, boolean isedit, boolean isdelect, boolean isupload, Bitmap cacheBitmap, LinkedHashMap<Integer, StickerItem> bank) {
		this.id = id;
		this.imageUrl = imageUrl;
		this.isSelected = isSelected;
		this.edpath = edpath;
		this.isedit = isedit;
		this.isdelect = isdelect;
		this.isupload = isupload;
		this.cacheBitmap = cacheBitmap;
		this.bank = bank;
	}

	public Bitmap getCacheBitmap() {
		return cacheBitmap;
	}

	public void setCacheBitmap(Bitmap cacheBitmap) {
		this.cacheBitmap = cacheBitmap;
	}

	public LinkedHashMap<Integer, StickerItem> getBank() {
		return bank;
	}

	public void setBank(LinkedHashMap<Integer, StickerItem> bank) {
		this.bank = bank;
	}

	public String getEdpath() {
		return edpath;
	}

	public void setEdpath(String edpath) {
		this.edpath = edpath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isdelect() {
		return isdelect;
	}

	public void setIsdelect(boolean isdelect) {
		this.isdelect = isdelect;
	}

	public boolean isedit() {
		return isedit;
	}

	public void setIsedit(boolean isedit) {
		this.isedit = isedit;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	public boolean isupload() {
		return isupload;
	}

	public void setIsupload(boolean isupload) {
		this.isupload = isupload;
	}
}
