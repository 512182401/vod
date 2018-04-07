//package com.changxiang.vod.module.ui.typemusic;
//
//import android.os.Bundle;
//import android.os.Message;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.changxiang.vod.R;
//import com.changxiang.vod.common.utils.ImageLoader;
//import com.changxiang.vod.common.utils.LogUtils;
//import com.changxiang.vod.common.utils.SharedPrefManager;
//import com.changxiang.vod.module.engine.JsonParserFirst;
//import com.changxiang.vod.module.entry.TypeMusic;
//import com.changxiang.vod.module.ui.base.BaseMusicActivity;
//import com.changxiang.vod.module.ui.typemusic.adapter.TypeNameAdapter;
//import com.changxiang.vod.module.ui.typemusic.net.TypeMusicNet;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Response;
//
//public class TypeIndexActivity extends BaseMusicActivity implements View.OnClickListener {
//    private LinearLayout linearLayout;//总布局
//    private TextView top_text;//顶部居中显示
//    private ImageView bt_right;//右边显示
//    private ImageView bt_back;//返回
//    private TextView right_text;//y右边显示
//    private List<TypeMusic> typeList;//分类点歌对象集合
//    private List<String> nameList=new ArrayList<>();//分类板块名字集合
//    private List<String> imgList=new ArrayList<>();//分类板块图标集合
//    //List<View> viewList=new ArrayList<>();//分类板块视图集合
//    private List<TypeMusic.ListBean> typeNameList;//分类版块下的标签集合
//
//    private GridLayoutManager manager;
//    private RecyclerView recyclerView;
//
//    @Override
//    public void handMsg(Message msg) {
//        if (msg.what==0){
//            if (typeList!=null){
//                for (int i=0;i<typeList.size();i++){
//                    nameList.add(typeList.get(i).getName());
//                    imgList.add(typeList.get(i).getImg());
//                    typeNameList = typeList.get(i).getList();
//                    View view= LayoutInflater.from(this).inflate(R.layout.item_type_music,null);//RecyclerView布局
//                    LinearLayout ll= (LinearLayout) view.findViewById(R.id.item_ll);
//                    /*//获取屏幕宽度
//                    int screenWidth = SuperPlayerUtils.getScreenWidth(this);
//                    LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) ll.getLayoutParams();
//                    params.width= (screenWidth-DisplayUtil.dip2px(getApplicationContext(),23))/4;
//                    ll.setLayoutParams(params);*/
//                    ImageView iv= (ImageView) view.findViewById(R.id.item_type_icon);
//                    TextView tv= (TextView) view.findViewById(R.id.item_type_name);
//                    RecyclerView rv= (RecyclerView) view.findViewById(R.id.item_type_rv);
//                    manager = new GridLayoutManager(this, 3);//RecyclerView布局管理器
//                    /*manager.setSmoothScrollbarEnabled(true);
//                    manager.setAutoMeasureEnabled(true);
//                    rv.setLayoutManager(manager);
//                    rv.setHasFixedSize(true);*/
//                    rv.setLayoutManager(manager);
//                    rv.setNestedScrollingEnabled(false);
//                    TypeNameAdapter typeNameAdapter=new TypeNameAdapter(this,typeNameList,typeList.get(i).getType());
//                    rv.setAdapter(typeNameAdapter);
//                    tv.setText(typeList.get(i).getName());
//                    ImageLoader.getImageViewLoad(iv,typeList.get(i).getImg(),R.drawable.dianji_11);
//                    linearLayout.addView(view);
//                    /*if (typeNameList.size()>6){//多余6个，就会大于2行，后面的布局会一样
//                        manager = new GridLayoutManager(this, 4);//RecyclerView布局管理器
//                        recyclerView=new RecyclerView(getApplicationContext());
//                        recyclerView.setLayoutManager(manager);
//                        List<TypeMusic.ListBean> typeNames=new ArrayList<>();
//                        for (int j=6;j<typeNameList.size();j++){
//                            typeNames.add(typeNameList.get(j));
//                        }
//                        TypeNameAdapter2 adapter=new TypeNameAdapter2(getApplicationContext(),typeNames,typeList.get(i).getType());
//                        recyclerView.setAdapter(adapter);
//                    }*/
//                    if (recyclerView!=null){
//                        linearLayout.addView(recyclerView);
//                        recyclerView=null;
//                    }
//
//
//                }
//            }
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_typemusic);
//        initView();
//        initData();
//        intiEvent();
//
//    }
//    private void initView() {
//        manager = new GridLayoutManager(this, 3);
//
//        linearLayout = (LinearLayout) findViewById(R.id.activity_ll);
//        bt_back = (ImageView) findViewById(R.id.back_last);
//        top_text = (TextView) findViewById(R.id.center_text);
//        right_text = (TextView) findViewById(R.id.back_next_left);
//        bt_right = (ImageView) findViewById(R.id.back_next);
//        top_text.setText(R.string.choose_type);
//
//        /*madapter = new TypeZYAdapter(this);
//        zongyi.setAdapter(madapter);*/
//    }
//
//    private void initData() {
//        TypeMusicNet.api_TypeMusic(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        String data = SharedPrefManager.getInstance().getCacheApitypemusic();
//                        if(data!=null){
//                            try {
//                                JSONObject root = new JSONObject(data);
//                                JSONArray data1 = root.getJSONArray("data");
//                                typeList = TypeMusic.arrayTypeMusicFromData(data1.toString());
//                                handler.sendEmptyMessage(0);
//                            } catch (JSONException en) {
//                                en.printStackTrace();
//                            }
//                        }
//                        LogUtils.w("TAG", "数据获取失败");
//                    }
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        String data = response.body().string();
//                        LogUtils.w("TAG", "分类点歌数据：" + data);
//                        int code = JsonParserFirst.getRetCode(data);
//                        if (code == 0) {
//                            SharedPrefManager.getInstance().cacheApitypemusic( data );//保存数据
//                            try {
//                                JSONObject root = new JSONObject(data);
//                                JSONArray data1 = root.getJSONArray("data");
//                                typeList = TypeMusic.arrayTypeMusicFromData(data1.toString());
//                                handler.sendEmptyMessage(0);
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//        );
//    }
//
//    private void intiEvent() {
//
//        bt_back.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        //Intent intent=null;
//        switch (id) {
//            case R.id.back_last:
//                finish();
//                break;
//            /*case R.id.back_next:
//                //返回主页
//                Intent home = new Intent(TypeIndexActivity.this, HomeActivity.class);
//                startActivity(home);
//                break;*/
//
//        }
//    }
//
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//}
