package com.koolyun.koolwait.view;

import android.content.Context;

import com.koolyun.koolwait.R;

public class LoadingDialogStyle1 extends LoadingDialog {

	final static int mTheme = R.style.loading_dialog_style1;
	final static int mAnim = R.anim.loading_animation1;

	public LoadingDialogStyle1(Context context, String msg) {
		super(context, mTheme, mAnim, msg);
	}

}
