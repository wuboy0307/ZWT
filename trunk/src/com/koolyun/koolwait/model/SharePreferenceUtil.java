package com.koolyun.koolwait.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 本地配置工具
 * 
 * @author Edwin
 * 
 */
public class SharePreferenceUtil {
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	/**
	 * @param context
	 *            {@link Context}
	 * @param file
	 *            本地配置文件名, 可使用{@link #FILE_SETTINGS}
	 */
	@SuppressLint("CommitPrefEdits")
	public SharePreferenceUtil(Context context, String file) {
		sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	/**
	 * 设置界面的配置文件名
	 */
	public static final String FILE_SETTINGS = "easyfood_settings";

	/**
	 * 订单打印份数
	 */
	public static final String SETTINGS_PRINT_PAGE_COUNT = "print_page_count";

	/**
	 * 音量调节
	 */
	public static final String SETTINGS_VOLUMN = "volumn";

	/**
	 * 是否自动更新版本
	 */
	public static final String SETTINGS_IS_UPDATE_AUTO = "is_update_auto";

	/**
	 * 教学界面版本
	 */
	public static final String SETTINGS_BEGINNER_PAGE_VERSION = "beginner_page_version";

	/**
	 * 百度云推送user_id
	 */
	public static final String SETTINGS_BAIDU_PUSH_USER_ID = "baidu_push_user_id";

	/**
	 * 百度云推送绑定
	 */
	public static final String SETTINGS_BAIDU_PUSH_BIND = "baidu_push_bind";

	/**
	 * 订单打印份数默认值
	 */
	public static final int SETTINGS_PRINT_PAGE_COUNT_DEFAULT = 2;

	/**
	 * 音量默认值
	 */
	public static final int SETTINGS_VOLUMN_DEFUALT = 50;

	/**
	 * 是否自动更新版本默认值
	 */
	public static final boolean SETTINGS_IS_UPDATE_AUTO_DEFAULT = true;

	/**
	 * 教学界面版本默认值
	 */
	public static final int SETTINGS_BEGINNER_PAGE_VERSION_DEFAULT = 0;

	/**
	 * 当前教学界面版本代码
	 */
	public static final int SETTINGS_BEGINNER_PAGE_VERSION_CURRENT = 1;

	/**
	 * 百度云推送user_id默认值
	 */
	public static final String SETTINGS_BAIDU_PUSH_USER_ID_DEFAULT = "";

	/**
	 * 百度云推送绑定默认值
	 */
	public static boolean SETTINGS_BAIDU_PUSH_BIND_DEFAULT = false;

	/**
	 * 获取订单打印份数
	 * 
	 * @return 订单打印份数
	 */
	public int getSettingsPrintPageCount() {
		return sp.getInt(SETTINGS_PRINT_PAGE_COUNT, SETTINGS_PRINT_PAGE_COUNT_DEFAULT);
	}

	/**
	 * 设置订单打印份数
	 * 
	 * @param count
	 *            订单打印份数
	 */
	public void setSettingsPrintPageCount(int count) {
		editor.putInt(SETTINGS_PRINT_PAGE_COUNT, count);
		editor.commit();
	}

	/**
	 * 获取音量值
	 * 
	 * @return 音量值
	 */
	public int getSettingsVolumn() {
		return sp.getInt(SETTINGS_VOLUMN, SETTINGS_VOLUMN_DEFUALT);
	}

	/**
	 * 设置音量值
	 * 
	 * @param volumn
	 *            音量值
	 */
	public void SetSettingsVolumn(int volumn) {
		editor.putInt(SETTINGS_VOLUMN, volumn);
		editor.commit();
	}

	/**
	 * 获取是否自动更新版本
	 * 
	 * @return 是否自动更新版本
	 */
	public boolean getSettingsIsUpdateAuto() {
		return sp.getBoolean(SETTINGS_IS_UPDATE_AUTO, SETTINGS_IS_UPDATE_AUTO_DEFAULT);
	}

	/**
	 * 设置是否自动更新版本
	 * 
	 * @param b
	 *            是否自动更新版本
	 */
	public void setSettingsIsUpdateAuto(boolean b) {
		editor.putBoolean(SETTINGS_IS_UPDATE_AUTO, b);
		editor.commit();
	}

	/**
	 * 获取教学界面版本
	 * 
	 * @return 教学界面版本代码
	 */
	public int getSettingsBeginnerPageVersion() {
		return sp.getInt(SETTINGS_BEGINNER_PAGE_VERSION, SETTINGS_BEGINNER_PAGE_VERSION_DEFAULT);
	}

	/**
	 * 设置教学界面版本
	 * 
	 * @param version
	 *            教学界面版本代码
	 */
	public void setSettingsBeginnerPageVersion(int version) {
		editor.putInt(SETTINGS_BEGINNER_PAGE_VERSION, version);
		editor.commit();
	}

	/**
	 * 获取百度云推送user_id
	 * 
	 * @return 百度云推送user_id
	 */
	public String getSettingsBaiduPushUserId() {
		return sp.getString(SETTINGS_BAIDU_PUSH_USER_ID, SETTINGS_BAIDU_PUSH_USER_ID_DEFAULT);
	}

	/**
	 * 设置百度云推送user_id
	 * 
	 * @param userId
	 *            百度云推送user_id
	 */
	public void setSettingsBaiduPushUserId(String userId) {
		editor.putString(SETTINGS_BAIDU_PUSH_USER_ID, userId);
		editor.commit();
	}

	/**
	 * 判断百度推送是否绑定
	 * 
	 * @return
	 */
	public boolean getSettingsBaiduPushBind() {
		boolean isBind = sp.getBoolean(SETTINGS_BAIDU_PUSH_BIND, SETTINGS_BAIDU_PUSH_BIND_DEFAULT);
		String userId = sp.getString(SETTINGS_BAIDU_PUSH_USER_ID, SETTINGS_BAIDU_PUSH_USER_ID_DEFAULT);

		if (isBind && !userId.isEmpty()) {
			return true;
		}
		return false;
	}

	public void setSettingsBaiduPushBind(boolean isBind, String userId) {
		editor.putBoolean(SETTINGS_BAIDU_PUSH_BIND, isBind);
		editor.putString(SETTINGS_BAIDU_PUSH_USER_ID, userId);
		editor.commit();
	}

	// public static final String MESSAGE_NOTIFY_KEY = "message_notify";
	// public static final String MESSAGE_SOUND_KEY = "message_sound";
	// public static final String SHOW_HEAD_KEY = "show_head";
	// public static final String PULLREFRESH_SOUND_KEY = "pullrefresh_sound";
	//
	// // appid
	// public void setAppId(String appid) {
	// editor.putString("appid", appid);
	// editor.commit();
	// }
	//
	// public String getAppId() {
	// return sp.getString("appid", "");
	// }
	//
	// // user_id
	// public void setUserId(String userId) {
	// editor.putString("userId", userId);
	// editor.commit();
	// }
	//
	// public String getUserId() {
	// return sp.getString("userId", "");
	// }
	//
	// // channel_id
	// public void setChannelId(String ChannelId) {
	// editor.putString("ChannelId", ChannelId);
	// editor.commit();
	// }
	//
	// public String getChannelId() {
	// return sp.getString("ChannelId", "");
	// }
	//
	// // 设置Tag
	// public void setTag(String tag) {
	// editor.putString("tag", tag);
	// editor.commit();
	// }
	//
	// public String getTag() {
	// return sp.getString("tag", "");
	// }
	//
	// // 是否通知
	// public boolean getMsgNotify() {
	// return sp.getBoolean(MESSAGE_NOTIFY_KEY, true);
	// }
	//
	// public void setMsgNotify(boolean isChecked) {
	// editor.putBoolean(MESSAGE_NOTIFY_KEY, isChecked);
	// editor.commit();
	// }
	//
	// // 新消息是否有声音
	// public boolean getMsgSound() {
	// return sp.getBoolean(MESSAGE_SOUND_KEY, true);
	// }
	//
	// public void setMsgSound(boolean isChecked) {
	// editor.putBoolean(MESSAGE_SOUND_KEY, isChecked);
	// editor.commit();
	// }
	//
	// // 刷新是否有声音
	// public boolean getPullRefreshSound() {
	// return sp.getBoolean(PULLREFRESH_SOUND_KEY, true);
	// }
	//
	// public void setPullRefreshSound(boolean isChecked) {
	// editor.putBoolean(PULLREFRESH_SOUND_KEY, isChecked);
	// editor.commit();
	// }
}
