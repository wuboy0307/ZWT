package com.koolyun.koolwait.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.koolyun.koolwait.R;

/**
 * 数据载入对话框
 * 
 * @author Edwin
 *
 */
public class LoadingDialog extends Dialog {

	private String mLoadingText = null;
	private Context mContext = null;
	private int mAnimId;
	private Animation mAnim = null;
	private ImageView mAnimImage = null;
	private TextView mTextView = null;

	public LoadingDialog(Context context, int theme, int animation, String msg) {
		super(context, theme);
		mContext = context;
		mLoadingText = msg;
		mAnimId = animation;
	}

	public void setMsg(String msg) {
		if (mTextView != null)
			mTextView.setText(msg);
	}

	public String getMsg() {
		return mTextView.getText().toString();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载布局
		mAnimImage = (ImageView) v.findViewById(R.id.img);// 得到显示动画的控件
		mTextView = (TextView) v.findViewById(R.id.tipTextView);// 得到提示文字的控件

		beginAnimatiom();
		showText();
		setCancelable(false);// 不可以用“返回键”取消
		setContentView(v);// 设置布局
	}

	private void beginAnimatiom() {

		// 加载动画
		if (mAnim == null) {
			mAnim = AnimationUtils.loadAnimation(mContext, mAnimId);
		}
		// 使用ImageView显示动画
		if (mAnimImage != null)
			mAnimImage.startAnimation(mAnim);
	}

	private void showText() {
		if (mLoadingText == null) {
			mTextView.setVisibility(View.GONE);
		} else {
			if (mTextView != null)
				mTextView.setText(mLoadingText);// 设置加载信息
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		beginAnimatiom();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mAnimImage.clearAnimation();
	}

}
