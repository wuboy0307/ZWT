package com.koolyun.koolwait;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.koolyun.koolwait.model.LoginTask;
import com.koolyun.koolwait.utils.MyToast;

/**
 * 登陆界面
 */
public class LoginActivity extends Activity implements OnClickListener {
	private Button mLoginBtn;
	private EditText mLoginEdtName;
	private EditText mLoginEdtPwd;
	public static String userId = null;
	  
	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.blue));
		setContentView(R.layout.login_layout);

		mLoginEdtName = (EditText) findViewById(R.id.logineditname);
		mLoginEdtPwd = (EditText) findViewById(R.id.logineditpwd);

		// 设置用户名和密码
//		mLoginEdtName.setText("abc");
//		mLoginEdtPwd.setText("123456");
		mLoginBtn = (Button) findViewById(R.id.loginBtn);
		mLoginBtn.setOnClickListener(this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		userId = intent.getStringExtra("userId");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginBtn:
			loginBtnCall();
			break;
		}
	}

	/**
	 * 登陆按键点击事件
	 */
	private void loginBtnCall() {
		if (mLoginEdtName.getText() == null || mLoginEdtName.getText().toString().isEmpty() ||
				mLoginEdtPwd.getText() == null || mLoginEdtPwd.getText().toString().isEmpty()) {
			MyToast.showShort(this, this.getResources().getString(R.string.input_username_password));
			return;
		} else {
			new LoginTask(this, mLoginEdtName.getText().toString(), mLoginEdtPwd.getText().toString(), userId,
					mLoginBtn).execute();
		}
	}
}
