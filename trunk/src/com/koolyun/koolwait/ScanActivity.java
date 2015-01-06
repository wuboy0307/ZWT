package com.koolyun.koolwait;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.Result;
import com.google.zxing.client.android.IScanEvent;
import com.google.zxing.client.android.ScannerRelativeLayout;
import com.google.zxing.client.android.camera.CameraSettings;
import com.koolyun.koolwait.utils.MyLog;

public class ScanActivity extends Activity {
	private ImageView scanqrAnimImage;
	private ScannerRelativeLayout scanner;
	private IScanEvent iScanSuccessListener;
	private ScreenBroadcastReceiver mScreenReceiver;
	private boolean isScreenOff;
	private TextView scanResultText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mScreenReceiver = new ScreenBroadcastReceiver();
        registerListener();
        setContentView(R.layout.scan_layout);
        isScreenOff = false;
        CameraSettings.setCAMERA_FACING(CameraSettings.FACING_BACK);
		CameraSettings.setAUTO_FOCUS(true);
		CameraSettings.setBEEP(true);
		//true：可以连续识别 ，false：识别成功后要重新 startScan
		CameraSettings.setBULKMODE(true);
        
		scanner = (ScannerRelativeLayout) findViewById(R.id.scanner);
        scanner.setVisibility(View.INVISIBLE);
		iScanSuccessListener = new ScanSuccesListener();
		scanner.setScanSuccessListener(iScanSuccessListener);
		scanResultText = (TextView) findViewById(R.id.scanResultText);
		
		scanqrAnimImage = (ImageView) findViewById(R.id.scanqrAnimImage);
        scanqrAnimImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_down));
		
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.blue));
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.scan);
	}
	
	private class ScanSuccesListener extends IScanEvent {
		@Override
		public void scanCompleted(Result paramScannerResult) {
			MyLog.e("paramScannerResult.getText():"+paramScannerResult.getText());
			
			scanResultText.setText(getResources().getString(R.string.scan_result, paramScannerResult.getText()));
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(!isScreenOff) {
			scanner.pauseScan();
			scanner.stopScan();
			scanner.startScan();
			scanner.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onDestroy() {
		scanner.pauseScan();
		scanner.stopScan();
		scanqrAnimImage.clearAnimation();
		unregisterReceiver(mScreenReceiver);
		
		super.onDestroy();
	}
	
	/**
     * screen状态广播接收者
     */
    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
            	isScreenOff = true;
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
            	isScreenOff = false;
            }
        }
    }
    
    /**
     * 启动screen状态广播接收器
     */
    private void registerListener() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(mScreenReceiver, filter);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
