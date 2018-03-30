package com.changxiang.vod.common.view.guideview.component;//package tosingpk.quchangkeji.com.quchangpk.common.view.guideview.component;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.quchangkeji.tosing.R;
//import com.quchangkeji.tosing.common.utils.DensityUtil;
//import com.quchangkeji.tosing.common.view.guideview.Component;
//
////import com.blog.www.guideview.Component;
////import com.demo.guide.R;
//
///**
// * Created by binIoter on 16/6/17.
// */
//public class MutiComponent implements Component {
//  TextView content;
//  boolean isleft;
//  String str = "查看附近的人";
//  private Context context;
//
//  @Override
//  public View getView(LayoutInflater inflater) {
//    LinearLayout ll = new LinearLayout(inflater.getContext());
//    LinearLayout.LayoutParams param =
//        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT);
//
//    int pad = DensityUtil.dip2px(context,100);
//    param.setMargins(pad,0,pad,0);
//    ll.setPadding(pad,pad,pad,pad);
//    ll.setOrientation(LinearLayout.VERTICAL);
//    ll.setLayoutParams(param);
//    TextView textView = new TextView(inflater.getContext());
////    textView.setText(R.string.nearby);
//
////    textView.setText("查看附近的人");
//    textView.setTextColor(inflater.getContext().getResources().getColor(R.color.white));
//    textView.setTextSize(20);
//    ImageView imageView = new ImageView(inflater.getContext());
////    imageView.setImageResource(R.mipmap.arrow);
//    if(str!=null&&str.length()>0){
//      textView.setText(str);
//    }
//    if(isleft){
//      imageView.setImageResource(R.mipmap.arrow_left);
//    }else{
//      imageView.setImageResource(R.mipmap.arrow);
//    }
//    textView.setPadding(pad,0,pad,0);
//    ll.removeAllViews();
//    ll.addView(imageView);
//    ll.addView(textView);
//    ll.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        Toast.makeText(view.getContext(), "引导层被点击了", Toast.LENGTH_SHORT).show();
//      }
//    });
//    return ll;
//  }
//
//  public MutiComponent(Context context, String str, boolean isleft){
//    this.context = context;
//    this.str = str;
//    this.isleft = isleft;
//
//  }
//  @Override
//  public int getAnchor() {
//    return Component.ANCHOR_BOTTOM;
//  }
//
//  @Override
//  public int getFitPosition() {
//    return Component.FIT_CENTER;
//  }
//
//  @Override
//  public int getXOffset() {
//    return 0;
//  }
//
//  @Override
//  public int getYOffset() {
//    return 20;
//  }
//}
