package com.changxiang.vod.module.musicInfo;

public class LrcContent {
	private String lrcStr;
	private int lrcTime;//开始时间
	private int endLrcTime;//每句结束时间
	public String getLrcStr() {
		return lrcStr;
	}
	public void setLrcStr(String lrcStr) {
		this.lrcStr = lrcStr;
	}
	public int getLrcTime() {
		return lrcTime;
	}
	public void setLrcTime(int lrcTime) {
		this.lrcTime = lrcTime;
	}

	public int getEndLrcTime() {
		return endLrcTime;
	}

	public void setEndLrcTime(int endLrcTime) {
		this.endLrcTime = endLrcTime;
	}
}
