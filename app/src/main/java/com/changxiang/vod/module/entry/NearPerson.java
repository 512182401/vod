package com.changxiang.vod.module.entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * 附近的人实体类
 * @author admin
 *方式一：Code-->Generate

方式二：通过快捷键Alt+Insert
 */
public class NearPerson implements Serializable {


	/**
	 * total : 63
	 * pageCount : 7
	 * results : [{"id":"402883ec57bced620157bcf15d270000","sex":"1","distance":"100米以内","level":"497","name":"大大兀立","levelImg":"http://app.srv.quchangkeji.com:8083/tsAPI/files/dj/home/chaoji.png","imgHead":"http://app.srv.quchangkeji.com:8083/tsAPI/files/vip/402883ec57bced620157bcf15d270000/2017-04-10/ff8081815b55a356015b56c6461a000d_20170410153126_iamge.png","levelName":"超级明星"},{"id":"ff8081815b1e2845015b1e29869b0000","sex":"1","distance":"100米以内","level":"1","name":"THE ONLY ONE","levelImg":"http://app.srv.quchangkeji.com:8083/tsAPI/files/dj/home/caogen.png","imgHead":"http://q.qlogo.cn/qqapp/1105746417/8607862849E772B61CAE28CB1C6FA436/100","levelName":"草根歌手"},{"id":"ff8081815af4152f015af49c55df0007","sex":"1","distance":"100米以内","level":"1","name":"火狐","levelImg":"http://app.srv.quchangkeji.com:8083/tsAPI/files/dj/home/caogen.png","imgHead":"http://q.qlogo.cn/qqapp/1105746417/17A6E201D9B6FE884FAB92A16E91F2BD/100","levelName":"草根歌手"},{"id":"ff8081815b2cc09d015b3cc36e8e00ab","sex":"1","distance":"100米以内","level":"1","name":"三生三世十里桃花","levelImg":"http://app.srv.quchangkeji.com:8083/tsAPI/files/dj/home/caogen.png","imgHead":"http://app.srv.quchangkeji.com:8083/tsAPI/files/vip/ff8081815b2cc09d015b3cc36e8e00ab/2017-04-07/ff8081815b472cf9015b48a0be500021_20170407213545_iamge.png","levelName":"草根歌手"},{"id":"ff80818159a4fdc60159a618a89e0033","sex":"1","distance":"100米以内","level":"11","name":"么么哒","levelImg":"http://app.srv.quchangkeji.com:8083/tsAPI/files/dj/home/yanyi.png","imgHead":"http://app.srv.quchangkeji.com:8083/tsAPI/files/vip/ff80818159a4fdc60159a618a89e0033/2017-01-17/ff80818159aa28100159ab7c0de70016_20170117161231_image.png","levelName":"演艺歌手"},{"id":"ff8081815969199901596ca41bd40023","sex":"1","distance":"100米以内","level":"3","name":"热爱音乐","levelImg":"http://app.srv.quchangkeji.com:8083/tsAPI/files/dj/home/caogen.png","imgHead":"http://app.srv.quchangkeji.com:8083/tsAPI/files/vip/ff8081815969199901596ca41bd40023/2017-01-17/ff80818159aa28100159ab1081f00007_20170117141502_iamge.png","levelName":"草根歌手"},{"id":"ff8081815b4728c1015b473ea67f0004","sex":"1","distance":"100米以内","level":"1","name":"9399021218","levelImg":"http://app.srv.quchangkeji.com:8083/tsAPI/files/dj/home/caogen.png","imgHead":"http://app.srv.quchangkeji.com:8083/tsAPI/files/pro_gs.png","levelName":"草根歌手"},{"id":"ff8081815b21f6a0015b23a8e4ad00a3","sex":"1","distance":"100米以内","level":"1","name":"半杯阳光","levelImg":"http://app.srv.quchangkeji.com:8083/tsAPI/files/dj/home/caogen.png","imgHead":"http://q.qlogo.cn/qqapp/1105746417/05A95D7F04FE4D1025D0FA46486448B8/100","levelName":"草根歌手"},{"id":"ff8081815b4728c1015b554ed2100159","sex":"1","distance":"100米以内","level":"1","name":"恩古鲁","levelImg":"http://app.srv.quchangkeji.com:8083/tsAPI/files/dj/home/caogen.png","imgHead":"http://app.srv.quchangkeji.com:8083/tsAPI/files/vip/ff8081815b4728c1015b554ed2100159/2017-04-10/ff8081815b472cf9015b55506fd60093_20170410084306_iamge.png","levelName":"草根歌手"},{"id":"ff8081815b468423015b471376510035","sex":"1","distance":"100米以内","level":"1","name":"1095201209","levelImg":"http://app.srv.quchangkeji.com:8083/tsAPI/files/dj/home/caogen.png","imgHead":"http://app.srv.quchangkeji.com:8083/tsAPI/files/pro_gs.png","levelName":"草根歌手"}]
	 * last : false
	 * curPage : 1
	 * pageSize : 10
	 * first : true
	 */

	private int total;
	private int pageCount;
	private boolean last;
	private int curPage;
	private int pageSize;
	private boolean first;
	private List<ResultsBean> results;

	public static NearPerson objectFromData(String str) {

		return new Gson().fromJson(str, NearPerson.class);
	}

	public static NearPerson objectFromData(String str, String key) {

		try {
			JSONObject jsonObject = new JSONObject(str);

			return new Gson().fromJson(jsonObject.getString(key), NearPerson.class);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<NearPerson> arrayNearPersonFromData(String str) {

		Type listType = new TypeToken<ArrayList<NearPerson>>() {
		}.getType();

		return new Gson().fromJson(str, listType);
	}

	public static List<NearPerson> arrayNearPersonFromData(String str, String key) {

		try {
			JSONObject jsonObject = new JSONObject(str);
			Type listType = new TypeToken<ArrayList<NearPerson>>() {
			}.getType();

			return new Gson().fromJson(jsonObject.getString(key), listType);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return new ArrayList();


	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public List<ResultsBean> getResults() {
		return results;
	}

	public void setResults(List<ResultsBean> results) {
		this.results = results;
	}

	public static class ResultsBean {
		/**
		 * id : 402883ec57bced620157bcf15d270000
		 * sex : 1
		 * distance : 100米以内
		 * level : 497
		 * name : 大大兀立
		 * levelImg : http://app.srv.quchangkeji.com:8083/tsAPI/files/dj/home/chaoji.png
		 * imgHead : http://app.srv.quchangkeji.com:8083/tsAPI/files/vip/402883ec57bced620157bcf15d270000/2017-04-10/ff8081815b55a356015b56c6461a000d_20170410153126_iamge.png
		 * levelName : 超级明星
		 */

		private String id;
		private String sex;
		private String distance;
		private String level;
		private String name;
		private String levelImg;
		private String imgHead;
		private String levelName;

		public static ResultsBean objectFromData(String str) {

			return new Gson().fromJson(str, ResultsBean.class);
		}

		public static ResultsBean objectFromData(String str, String key) {

			try {
				JSONObject jsonObject = new JSONObject(str);

				return new Gson().fromJson(jsonObject.getString(key), ResultsBean.class);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		public static List<ResultsBean> arrayResultsBeanFromData(String str) {

			Type listType = new TypeToken<ArrayList<ResultsBean>>() {
			}.getType();

			return new Gson().fromJson(str, listType);
		}

		public static List<ResultsBean> arrayResultsBeanFromData(String str, String key) {

			try {
				JSONObject jsonObject = new JSONObject(str);
				Type listType = new TypeToken<ArrayList<ResultsBean>>() {
				}.getType();

				return new Gson().fromJson(jsonObject.getString(key), listType);

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return new ArrayList();


		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getDistance() {
			return distance;
		}

		public void setDistance(String distance) {
			this.distance = distance;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLevelImg() {
			return levelImg;
		}

		public void setLevelImg(String levelImg) {
			this.levelImg = levelImg;
		}

		public String getImgHead() {
			return imgHead;
		}

		public void setImgHead(String imgHead) {
			this.imgHead = imgHead;
		}

		public String getLevelName() {
			return levelName;
		}

		public void setLevelName(String levelName) {
			this.levelName = levelName;
		}
	}
}
