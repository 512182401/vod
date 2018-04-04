package com.changxiang.vod.module.ui.recordmusic.screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.view.guideview.Component;

//import com.quchangkeji.tosingpk.R;
//import com.quchangkeji.tosingpk.common.view.guideview.Component;


//import com.blog.www.guideview.Component;
//import com.demo.guide.R;

/**
 * Created by binIoter on 16/6/17.
 */
public class SimpleComponent implements Component {
  TextView content;
  boolean isleft;
  String str;
  private Context context;
  @Override
  public View getView(LayoutInflater inflater) {

//    View ll = (LinearLayout) inflater.from(context).inflate(R.layout.layer_frends, null);
    LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.layer_frends, null);
    ImageView arrow_image = (ImageView)ll.findViewById(R.id.im_arrow_image);
     content = (TextView)ll.findViewById(R.id.tv_content_show);
//    content.setText("11111111111");
    if(str!=null&&str.length()>0){
      content.setText(str);
    }
    if(isleft){
      arrow_image.setImageResource(R.mipmap.arrow_left);
    }else{
      arrow_image.setImageResource(R.mipmap.arrow);
    }
    ll.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
//        Toast.makeText(view.getContext(), "引导层被点击了", Toast.LENGTH_SHORT).show();
      }
    });
    return ll;
  }

  public SimpleComponent(){

  }
  public SimpleComponent(Context context, String str, boolean isleft){
    this.context = context;
    this.str = str;
    this.isleft = isleft;

  }

  public  SimpleComponent setContent(String str){
    if(content!=null) {
      content.setText(str);
    }
    return this;
  }
  @Override
  public int getAnchor() {
    return Component.ANCHOR_BOTTOM;
  }

  @Override
  public int getFitPosition() {
    return Component.FIT_END;
  }

  @Override
  public int getXOffset() {
    if(isleft){
      return 0;
    }else{
//      return context.getResources().getDisplayMetrics().widthPixels/-2;
      return 100;
    }

  }

  @Override
  public int getYOffset() {
    return 10;
  }
}
