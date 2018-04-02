//package com.changxiang.vod.module.ui;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//
//import com.changxiang.vod.module.pinyinindexing.Person;
//import com.changxiang.vod.module.pinyinindexing.QuickindexBar;
//import com.changxiang.vod.module.ui.base.BaseActivity;
//import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Response;
//
//
//public class SingerPinYinActivity extends BaseActivity implements View.OnClickListener{
//
//	PersonAdapter adapter = null;
//	private QuickindexBar slideBar;
//	private ListView lv;
//	private ArrayList<Person> persons;
//	private TextView tv_zimu;
//	private Handler handler1;
//
//	private String[] addstr = {"全部", "内地", "港台", "日韩", "欧美"};// 地域  1 内地 2 港台 3 日韩 4 欧美
//	private String[] sexstr = {"全部", "男", "女", "组合"};
//
//
//	private TextView top_text;//顶部居中显示
//	private ImageView bt_right;//右边显示
//	private ImageView bt_back;//返回
//	private LinearLayout ll_no_data;//no_data_show
//	private TwinklingRefreshLayout refreshLayout;
//	private HotTJ hotTJ = null;
//	private HotTJ hotTJall = null;
//	private int curPage = 1;
//	private String langValue = "0";
//	private String sexValue = "0";
//	private String pinyin = "a";
//	private int rowWidth = 0;
//	private LinearLayout add;
//	private LinearLayout sex;
//	List<View> addviews = new ArrayList<View>();
//	List<View> sexviews = new ArrayList<View>();
//	boolean islast = false;
//
//
//	private int ImageOnFail = R.drawable.default_icon;// 下载失败时的图片名称
//	String responsemsg = "请求数据为空";//网络请求返回信息
//	@Override
//	public void handMsg(Message msg) {
//		Intent intent;
//		switch (msg.what) {
//			case -1:
//				isGetNewData = true;
//				toast( "联网登录出现网络异常错误" );
////                    handler.sendEmptyMessageDelayed( -1, 1000 );
//				break;
//			case 0:
////                toast( "成功" );
////                    handler.sendEmptyMessageDelayed( 0, 1000 );
//
//				break;
//			case 1:
//
//				isGetNewData = true;
////				toast( responsemsg );//
//				refreshLayout.setVisibility( View.GONE );
//				ll_no_data.setVisibility( View.VISIBLE );
////                handler.sendEmptyMessageDelayed( 1, 100 );
//				break;
//			case 2://刷新成功，刷新界面
//				refreshLayout.setVisibility( View.VISIBLE );
//				ll_no_data.setVisibility( View.GONE );
////				madapter.setDataList( hotTJ.getResults() );
////                handler.sendEmptyMessageDelayed( 2, 100 );
//				if(hotTJ!=null&&hotTJ.getResults()!=null&&hotTJ.getResults().size()>0){
//					persons = new ArrayList<Person>();
//					for (int i = 0; i < hotTJ.getResults().size(); i++) {
//						persons.add(new Person(hotTJ.getResults().get(i).getName(),hotTJ.getResults().get(i)));
//					}
////					// 进行排序
////					Collections.sort(persons);
//					adapter.notifyDataSetChanged();
//					isGetNewData = true;
////				PersonAdapter adapter = new PersonAdapter();
//				}else{
//					return;
//				}
//				break;
//			case 3://加载更多成功，刷新界面
//
//				isGetNewData = true;
//				for (int i = 0; i < hotTJ.getResults().size(); i++) {
//					persons.add(new Person(hotTJ.getResults().get(i).getName(),hotTJ.getResults().get(i)));
//				}
//				// 进行排序
////				Collections.sort(persons);
//				adapter.notifyDataSetChanged();
////				madapter.addDataList( hotTJ.getResults() );
////                handler.sendEmptyMessageDelayed( 3, 100 );
//				break;
//
//			case 123://权限
////                updataimage();//修改图片，以及上传
////                handler.sendEmptyMessageDelayed( 123, 100 );
//				break;
//
//			case 10://提交成功（）
////                handler.sendEmptyMessageDelayed( 10, 100 );
////                club_image.setImageBitmap( newbitmap );
//				break;
//
//
//		}
//	}
//
//	boolean isGetNewData  = true;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_pinyin_singer);
//		rowWidth = this.getResources().getDisplayMetrics().widthPixels / 7;
//		Intent intent = getIntent();
//		pinyin = intent.getStringExtra("pinyin");
////		intent.putExtra("pinyin",str);
//		add = (LinearLayout) findViewById( R.id.ll_add );
//		sex = (LinearLayout) findViewById( R.id.ll_sex );
//
//		bt_back = (ImageView) findViewById( R.id.back_last );
//		top_text = (TextView) findViewById( R.id.center_text );
//		bt_right = (ImageView) findViewById( R.id.back_next );
//
//		refreshLayout = (TwinklingRefreshLayout) findViewById( R.id.refreshLayout );
//		ll_no_data= (LinearLayout) findViewById( R.id.ll_no_data_show  );//无数据
//		tv_zimu = (TextView) findViewById(R.id.tv_zimu);
//		handler1 = new Handler();
//		slideBar = (QuickindexBar) findViewById(R.id.slideBar);
//		slideBar.setOnSlideTouchListener(new QuickindexBar.OnSlideTouchListener() {
//
//			@Override
//			public void onBack(String str) {
//
//				showZimu(str);
//				pinyin = str;
//				if(isGetNewData) {
//					isGetNewData = false;
//					curPage = 1;
//					getNewData(curPage);//首次请求数据
//				}
////				for (int i = 0; i < persons.size(); i++) {
////					if (persons.get(i).getPinyin().substring(0, 1).equals(str)) {
////						lv.setSelection(i);
////						break;
////					}
////				}
//			}
//		});
//
//		top_text.setText( "歌星点歌" );
//		bt_right.setVisibility( View.VISIBLE );
//		bt_right.setImageResource( R.mipmap.search_white);
//
//
//		hotTJ = new HotTJ();
//		bt_back.setOnClickListener( this );
//		bt_right.setOnClickListener( this );
//
//		lv = (ListView) findViewById(R.id.lv);
////		persons = new ArrayList<Person>();
//////		for (int i = 0; i < PersonSS.names.length; i++) {
//////			persons.add(new Person(PersonSS.names[i]));
//////		}
////
////		for (int i = 0; i < hotTJ.getResults().size(); i++) {
////			persons.add(new Person(hotTJ.getResults().get(i).getName(),hotTJ.getResults().get(i)));
////		}
////		// 进行排序
////		Collections.sort(persons);
//		 adapter = new PersonAdapter();
//		lv.setAdapter(adapter);
//		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
////				Intent intent = new Intent(SingerPinYinActivity.this,
////						FullScreenADActivity.class);
////				startActivity(intent);
//
//				//取得歌手信息
//				HotTJ.ResultsBean singer =  adapter.getItemData(position);
//				//根据不同信息跳转到不同的界面
//				Intent intent = new Intent( SingerPinYinActivity.this, SingerListActivity.class );
//				intent.putExtra("singerid", singer.getId());
//				intent.putExtra("imgCover", singer.getImgCover());
//				intent.putExtra("singername", singer.getName());
////                holder.songerinfo.setText("MP3("+persons.get(arg0).getmSingerPY().getMp3num()+"首)/MV("+persons.get(arg0).getmSingerPY().getMvnum()+"首)");
//				intent.putExtra("songcount", "MP3("+singer.getMp3num()+"首)/MV("+singer.getMvnum()+"首)");
//				startActivity( intent );
//
//			}
//
//		});
//
//		refreshLayout.setOnRefreshListener( new TwinklingRefreshLayout.OnRefreshListener() {
//			//x下拉刷新
//			@Override
//			public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
//				new Handler().postDelayed(new Runnable() {
//					@Override
//					public void run() {
//						curPage = 1;
//						getNewData( curPage );//首次请求数据
//						refreshLayout.finishRefreshing();
//					}
//				}, 2000 );
//			}
//
//			//上拉加载
//			@Override
//			public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
//				new Handler().postDelayed(new Runnable() {
//					@Override
//					public void run() {
//						curPage++;
//						if(islast){
//							toast( "没有更多的数据了" );
//							refreshLayout.finishLoadmore();
//							return;
//						}
//						getNewData( curPage );//首次请求数据
//						refreshLayout.finishLoadmore();
//					}
//				}, 2000 );
//			}
//		} );
//		setAddHoriListView();
//		setSexHoriListView();
//		curPage = 1;
//		getNewData( curPage );//首次请求数据
//	}
//
//	// 显示在屏幕中间的字母
//	private void showZimu(String string) {
//
//		tv_zimu.setVisibility(View.VISIBLE);
//		tv_zimu.setText(string);
//		handler1.removeCallbacksAndMessages(null);
//		handler1.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				tv_zimu.setVisibility(View.GONE);
//			}
//		}, 1500);
//	}
//
//	@Override
//	public void onClick(View v) {
//		Intent intent;
//		switch (v.getId()) {
//			case R.id.back_next://主页
////                toast( "跳转到搜索界面" );
////                intent = new Intent( SingerIndexActivity.this, SingerListActivity.class );
//				intent = new Intent( SingerPinYinActivity.this, SearchActivity.class );
//				intent.putExtra("search", "1");
//				startActivity( intent );
//				break;
//			case R.id.back_last://
//				finishActivity();
//				break;
//		}
//	}
//
//	private class PersonAdapter extends BaseAdapter {
//
//		@Override
//		public int getCount() {
//			return persons==null?0:persons.size();
//		}
//
//		@Override
//		public HotTJ.ResultsBean getItem(int selectPos) {
//
//			if(persons != null){
//				return persons.get(selectPos).getmSingerPY();
//			}return null;
//		}
//		public HotTJ.ResultsBean getItemData(int selectPos) {
//
//			if(persons != null){
//				return persons.get(selectPos).getmSingerPY();
//			}return null;
//		}
//
//		@Override
//		public long getItemId(int arg0) {
//			return 0;
//		}
//
//		@Override
//		public View getView(int arg0, View arg1, ViewGroup arg2) {
//			ViewHolder holder;
//			if (arg1 == null) {
//				holder = new ViewHolder();
//				arg1 = View.inflate(SingerPinYinActivity.this, R.layout.item_lv_singer, null);
//				holder.tv_py = (TextView) arg1.findViewById(R.id.tv_py);
//				holder.tv_name = (TextView) arg1.findViewById(R.id.tv_name);
//				holder.songerinfo = (TextView) arg1.findViewById(R.id.tv_songer_count);
//				holder.showimager = (CircleImageView) arg1.findViewById(R.id.showimager);
//				arg1.setTag(holder);
//			} else {
//				holder = (ViewHolder) arg1.getTag();
//			}
//
//			String string = null;
//
//			if (arg0 == 0) {
//				string = persons.get(arg0).getPinyin().substring(0, 1);
//			} else {
//				String py = persons.get(arg0).getPinyin().substring(0, 1);
//				String spy = persons.get(arg0 - 1).getPinyin().substring(0, 1);
//				if (!py.equals(spy)) {
//					string = persons.get(arg0).getPinyin().substring(0, 1);
//				}
//			}
//			if (string == null) {
//				holder.tv_py.setVisibility(View.GONE);
//			} else {
////				holder.tv_py.setVisibility(View.VISIBLE);
//				holder.tv_py.setVisibility(View.GONE);
//				holder.tv_py.setText(string);
//			}
//			holder.tv_name.setText(persons.get(arg0).getName());
//			try {
//				holder.songerinfo.setText("MP3("+persons.get(arg0).getmSingerPY().getMp3num()+"首)/MV("+persons.get(arg0).getmSingerPY().getMvnum()+"首)");
//			}catch (Exception e){
//				e.printStackTrace();
//			}
//			String Userpic = persons.get(arg0).getmSingerPY().getImgHead();
//			if (Userpic == null || Userpic.equals("")) {// 没有图片，图片为空
////			ImageOnFail = ResultAdapterShow.showCircleImageView(holder.showimager, getItem(position).getUserid());
//
//			} else {
////			String iconUrl = NetInterface.SERVER_IMG_PRE + item.getUserpic();
//				ImageLoader.getImageViewLoad(holder.showimager, Userpic, ImageOnFail);
//
//			}
//			return arg1;
//		}
//	}
//
//	private class ViewHolder {
//		TextView tv_py, tv_name;
//		TextView songerinfo;//歌曲数量;
//		CircleImageView showimager;//头像
//	}
//
//	private void setAddHoriListView() {
//		int size = addstr.length;
//		addviews.clear();
//		add.removeAllViews();
//		for (int i = 0; i < size; i++) {
//			View item = LayoutInflater.from( this ).inflate( R.layout.item_singer_hrzlistview_layout, null );
//			RadioGroup.LayoutParams params = new RadioGroup.LayoutParams( rowWidth, RadioGroup.LayoutParams.MATCH_PARENT );
//			item.setLayoutParams( params );
//			item.setTag( i );
//			Bundle b = new Bundle();
//			b.putString( "aid", "" + i );
//			final int finalI = i;
//			item.setOnClickListener( new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					setAddFocus( v );
//					langValue = finalI + "";
//					curPage = 1;
//					getNewData( curPage );
//				}
//			} );
//			RadioButton txt = AppUtil.findViewById( item, R.id.txt );
//			txt.setText( addstr[i] );
//			if (i == 0) {
//				txt.setTextColor( Color.parseColor( "#ff5f5f" ) );
//			}
//			addviews.add( item );
//			add.addView( item );
//		}
//	}
//
//
//	private void setSexHoriListView() {
//		int size = sexstr.length;
//		sexviews.clear();
//		sex.removeAllViews();
//		for (int i = 0; i < size; i++) {
//			View item = LayoutInflater.from( this ).inflate( R.layout.item_singer_hrzlistview_layout, null );
//			RadioGroup.LayoutParams params = new RadioGroup.LayoutParams( rowWidth, RadioGroup.LayoutParams.MATCH_PARENT );
//			item.setLayoutParams( params );
//			item.setTag( i );
//			Bundle b = new Bundle();
//			b.putString( "sid", "" + i );
//			final int finalI = i;
//			item.setOnClickListener( new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					setSexFocus( v );
//					sexValue = finalI + "";
//					curPage = 1;
//					getNewData( curPage );
//				}
//			} );
//			RadioButton txt = AppUtil.findViewById( item, R.id.txt );
//			txt.setText( sexstr[i] );
//			if (i == 0) {
//				txt.setTextColor( Color.parseColor( "#ff5f5f" ) );
//			}
//			sexviews.add( item );
//			sex.addView( item );
//		}
//	}
//
//	private void setAddFocus(View item) {
//		for (View view : addviews) {
//			TextView txt = (TextView) view.findViewById( R.id.txt );
//			if (view == item) {
//				txt.setTextColor( Color.parseColor( "#ff5f5f" ) );
//			} else {
//				txt.setTextColor( Color.parseColor( "#2e2e2e" ) );
//			}
//		}
//	}
//
//	private void setSexFocus(View item) {
//		for (View view : sexviews) {
//			TextView txt = (TextView) view.findViewById( R.id.txt );
//			if (view == item) {
//				txt.setTextColor( Color.parseColor( "#ff5f5f" ) );
//			} else {
//				txt.setTextColor( Color.parseColor( "#2e2e2e" ) );
//			}
//		}
//	}
//
//
//
//	//TODO 取得数据
//	private void getNewData(final int curPage1) {
//
//		SingUtil.api_hotTJ2( this, "" + langValue, "" + sexValue, "" + pinyin, "" + curPage, new Callback() {
////		SingUtil.api_hotTJ( this, "" + langValue, "" + sexValue, "0", "" + curPage, new Callback() {
//
//			@Override
//			public void onFailure(Call call, IOException e) {
//				closeProgressDialog();
//				LogUtils.sysout( "联网登录出现网络异常错误！" );
//				handler.sendEmptyMessageDelayed( -1, 1000 );
////                toast( "请求失败" );
//				isGetNewData = true;
//
//				if (curPage < 2&&pinyin.equals("#")) {
////				if (curPage < 2) {
//					hotTJ = new HotTJ();
//					String data = SharedPrefManager.getInstance().getCacheApisingermusic();
//					if (data != null && !data.equals( "" )) {
//						hotTJ = hotTJ.objectFromData( data, "data" );
//						if (hotTJ != null) {
////                            LogUtils.sysout( "nearperson=" + nearperson );
//							handler.sendEmptyMessageDelayed( 2, 100 );//刷新
//						} else {
//							LogUtils.sysout( "没有保存数据" );
//						}
//					}
//				}
//			}
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				closeProgressDialog();
//
//				String data = response.body().string();
////                toast( show );
////                int code = 0;
//				LogUtils.sysout("歌星点歌返回结果:"+data);
////                showProgressDialog("歌星点歌:",true);
////                int code = SingerJsonParser.getRetCode(data);
//				int code = JsonParserFirst.getRetCode( data );
//				if (code == 0) {
//					hotTJ = new HotTJ();
////                    hotTJ = SingerJsonParser.parser_HotTJ(data);
//					hotTJ = HotTJ.objectFromData( data, "data" );
//					if (hotTJ != null && !hotTJ.equals( "" )) {
////                        List<Singer> list = hotTJ.getResults();
////                        if (list == null || list.isEmpty()) {
////                            return;
////                        }
//						if(hotTJ.isLast()){
//							islast = true;
//						}else{
//							islast = false;
//						}
//						if (curPage < 2) {
//							if (hotTJ.getTotal() == 0) {//数据为空
//								handler.sendEmptyMessageDelayed( 1, 100 );
//							} else {
//								handler.sendEmptyMessageDelayed( 2, 100 );//刷新
//								SharedPrefManager.getInstance().cacheApisingermusic( data );//保存数据
//								hotTJall = hotTJ;
////								persons = new ArrayList<Person>();
////								for (int i = 0; i < hotTJ.getResults().size(); i++) {
////									persons.add(new Person(hotTJ.getResults().get(i).getName(),hotTJ.getResults().get(i)));
////								}
////								// 进行排序
////								Collections.sort(persons);
////								adapter.notifyDataSetChanged();
////								PersonAdapter adapter = new PersonAdapter();
////								lv.setAdapter(adapter);
//							}
//
//							//TODO  保存数据到本地
//						} else {
//							try {
//								curPage = hotTJ.getCurPage();
//								//TODO
////                               hotTJall.getResults().add( (HotTJ.ResultsBean) hotTJ.getResults() );
////                                singerscluball.getResults().add( (SingerClub.ResultsBean) singersclub.getResults() );
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
////
//							handler.sendEmptyMessageDelayed( 3, 100 );//加载更多
//						}
////                        if (hotTJ.getFirst().equals( "true" )) {//判断是否为首页
////                        cacheFirstPageAll(data);//缓存第一页数据
////                            mdapter.setDataList( list );
////                            runOnUiThread(new Runnable() {
////                                @Override
////                                public void run() {
////                                    mdapter.notifyDataSetChanged();
////                                }
////                            });
////                        } else {
////                            mdapter.addDataList( list );
////                            runOnUiThread(new Runnable() {
////                                @Override
////                                public void run() {
////                                    mdapter.notifyDataSetChanged();
////                                }
////                            });
////                        }
//					}
//				} else {
//					closeProgressDialog();
////                    String meg = SingerJsonParser.getRetMsg( response.body().toString() );
//					responsemsg = JsonParserFirst.getRetMsg( response.body().toString() );
////                    toast(meg);
//				}
//
//			}
//
//		} );
//
//	}
//
//}
