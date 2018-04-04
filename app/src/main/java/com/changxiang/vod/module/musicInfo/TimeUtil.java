package com.changxiang.vod.module.musicInfo;


public class TimeUtil {

	//把毫秒值转化为分:秒
	public static String mill2mmss(long duration){
		if(duration==0){
			return "00:00";
		}

		int m,s;
		String str = "";
		
		int x=(int)duration/1000;
		s=x%60;
		m=x/60;
		if(m<10){
			str+="0"+m;
		}else{
			str+=m;
		}
		
		if(s<10){
			str+=":0"+s;
		}else{
			str+=":"+s;
		}
		
		return str;
	}

	//把毫秒值转化为分:秒
	public static String mill2mmssTwo(long duration){
		if(duration==0){
			return "00:00";
		}

		int m,s;
		String str = "";
		float temp = (duration + 0.0f)/1000;
		duration = Math.round(temp) * 1000;
		int x=(int)duration/1000;
		s=x%60;
		m=x/60;
		if(m<10){
			str+="0"+m;
		}else{
			str+=m;
		}

		if(s<10){
			str+=":0"+s;
		}else{
			str+=":"+s;
		}

		return str;
	}
	
	public static int getLrcMillTime(String time){
		int millTime=0;
		time=time.replace(".", ":");

		String timedata[]=time.split(":");
		
		//Log.i("min,second,mill", timedata[0]+","+timedata[1]+","+timedata[2]);
		int min=0;
		int second=0;
		int mill=0;
		try {
			min = Integer.parseInt(timedata[0]);
			second = Integer.parseInt(timedata[1]);
			mill = Integer.parseInt(timedata[2]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return -1;
		}
		millTime=(min*60+second)*1000+mill*10;
		return millTime;
	}

	/**
	 * 将毫秒数转为时间
	 * @param time
	 * @return
     */
	/*public static int getLrcMillTime(int time){
		int millTime=0;
		time=time.replace(".", ":");

		String timedata[]=time.split(":");

		//Log.i("min,second,mill", timedata[0]+","+timedata[1]+","+timedata[2]);
		int min=0;
		int second=0;
		int mill=0;
		try {
			min = Integer.parseInt(timedata[0]);
			second = Integer.parseInt(timedata[1]);
			mill = Integer.parseInt(timedata[2]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return -1;
		}
		millTime=(min*60+second)*1000+mill*10;
		return millTime;
	}*/
}
