package com.changxiang.vod.common.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.changxiang.vod.R;
import com.changxiang.vod.common.utils.DensityUtil;


/**
 * 自定义的alertdialog:有两个选择按钮（确定，取消）
 * @author admin
 *
 */
public class CustomAlertDialog extends Dialog {
	public interface customAlertDialogHelper {
		void clickOk();

		void clickCancle();
	}

	private customAlertDialogHelper listener;
	private TextView tv_title;
	private TextView tv_msg;
	private Button bt_ok;
	private Button bt_cancle;
	private Context context;

	public void setListener(customAlertDialogHelper listener) {
		this.listener = listener;
	}

	public CustomAlertDialog(Context context) {
		super(context, R.style.CustomDialog);
		this.context = context;
		View view = View.inflate(context, R.layout.custom_alertdialog, null);
		LinearLayout ll_show = (LinearLayout) view
				.findViewById(R.id.ll_customdialog_show);
		Point point = DensityUtil.getWindowWidth(context);

		LayoutParams params = new LayoutParams((int) (point.x * 0.8), LayoutParams.WRAP_CONTENT);
		ll_show.setLayoutParams(params);
		init(view);
		setContentView(view);
	}

	private CustomAlertDialog(Context context, int theme) {
		super(context, theme);
	}

	private void init(View view) {
		tv_title = (TextView) view.findViewById(R.id.tv_alertdialog_title);
		tv_msg = (TextView) view.findViewById(R.id.tv_alertdialog_msg);
		bt_cancle = (Button) view.findViewById(R.id.bt_alertdialog_cancle);
		bt_ok = (Button) view.findViewById(R.id.bt_alertdialog_ok);

		bt_cancle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.clickCancle();
				}
			}
		});
		bt_ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.clickOk();
				}
			}
		});
	}

	@Override
	public void setTitle(CharSequence title) {
		tv_title.setVisibility(View.VISIBLE);
		tv_title.setText(title);
	}

	public void setMessage(String msg) {
		tv_msg.setText(msg);
	}

}
