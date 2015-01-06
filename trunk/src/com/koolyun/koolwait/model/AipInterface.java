package com.koolyun.koolwait.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.koolyun.koolwait.R;
import com.koolyun.koolwait.utils.JsonConverter;

/**
 * <P>
 * 订餐易接口
 * </p>
 * <p>
 * 参考《订餐易MPOS接入规范》
 * </p>
 * 
 * @author Edwin
 */
public class AipInterface {

	/**
	 * 接入订餐易MPOS系统的全局维一凭证
	 */
	private static String access_token = null;

	/**
	 * 登入用户名
	 */
	private static String username = null;

	/**
	 * 登入密码的MD5值
	 */
	private static String passwordMd5 = null;

	/**
	 * 百度云推送user id
	 */
	private static String userId = null;

	/**
	 * 登录时间，单位：秒
	 */
	private static long loginTime;

	/**
	 * 凭证有效时间，单位：秒。（超过有效时间后需要重新获取access_token）
	 */
	private static int expires_in;

	/**
	 * 接口路径：获取access_token
	 */
	private static final String PATH_LOGIN = "/auth/login";

	/**
	 * 接口路径：登出
	 */
	private static final String PATH_LOGOUT = "/auth/logout";
	/**
	 * 接口路径：获取订单数目
	 */
	private static final String PATH_ORDER_COUNT = "/order/count";

	/**
	 * 接口路径：获取订单列表
	 */
	private static final String PATH_ORDER_LIST = "/order/list";

	/**
	 * 接口路径：获取订单详情
	 */
	private static final String PATH_ORDER_DETAILS = "/order/info";

	/**
	 * 接口路径：更新订单状态
	 */
	private static final String PATH_ORDER_UPDATE = "/order/update";

	/**
	 * 接口路径：刷新订单状态
	 */
	private static final String PATH_ORDER_TOUCH = "/order/touch";

	/**
	 * 接口路径：获取餐位类型列表
	 */
	private static final String PATH_SEAT_TYPE_LIST = "/seat/list";

	/**
	 * 接口路径：获取队伍列表
	 */
	private static final String PATH_QUEUE_LIST = "/queue/list";

	/**
	 * 接口路径：更新队伍状态
	 */
	private static final String PATH_QUEUE_STATUS = "/queue/update";

	/**
	 * 接口路径：队伍领号
	 */
	private static final String PATH_QUEUE_TAKE_NUMBER = "/queue/number";

	/**
	 * 获取access_token
	 * 
	 * @param context
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param userId
	 *            百度云推送user id
	 * @return {@link AipResponse}
	 */
	public static AipResponse Login(Context context, String username, String password, String userId) {
		if (!NetworkUtil.isNetWorkAvilable(context)) {
			return new AipResponse(AipResponse.CODE_NETWORK_NOT_AVAILABLE, context.getResources().getString(
					R.string.network_not_available));
		}

		String json = new Gson().toJson(new AipLogin(username, password, userId));
		// String ss =
		// "{\"code\":0,\"result\":{\"access_token\":\"54bb6fcd253875f941ed360d9d097f6b\",\"expires_in\":7200,\"brand\":\"佳味集团\",\"restaurant\":\"味 ['（陆家嘴店）\",\"user\":\"abc\",\"support_queue\":1,\"support_take_out\":1}}";
		// AipResponse response = httpRequestProcess(context, ss);
		AipResponse response = httpRequestProcess(context, NetworkCommunication.postRequest(PATH_LOGIN, json));
		if (response.getCode() != AipResponse.CODE_SUCESS) {
			return response;
		}

		AipInterface.loginTime = System.currentTimeMillis() / 1000;
		AipInterface.username = username;
		AipInterface.passwordMd5 = password;
		AipInterface.userId = userId;

		AipLoginResponse login = (AipLoginResponse) JsonConverter.fromJson(response.getResult().toString(),
				AipLoginResponse.class);

		AipInterface.access_token = login.getAccess_token();
		AipInterface.expires_in = login.getExpires_in();

		return response;
	}

	/**
	 * 登出
	 * 
	 * @param context
	 * @return {@link AipResponse}
	 */
	public static AipResponse Logout(Context context) {
		AipResponse before = beforeHttpRequest(context);
		if (before.getCode() != AipResponse.CODE_SUCESS)
			return before;

		return remoteCallGet(context, PATH_LOGOUT + "?access_token=" + access_token);
	}

	/**
	 * 获取订单数目
	 * 
	 * @param context
	 * @return {@link AipResponse}
	 */
	public static AipResponse getOrdersCount(Context context) {
		AipResponse before = beforeHttpRequest(context);
		if (before.getCode() != AipResponse.CODE_SUCESS)
			return before;

		return remoteCallGet(context, PATH_ORDER_COUNT + "?access_token=" + access_token);
	}

	/**
	 * 获取订单数目
	 * 
	 * @param context
	 * @param aipOrdersList
	 *            {@link AipOrdersList}
	 * @return {@link AipResponse}
	 */
	public static AipResponse getOrdersList(Context context, AipOrdersList aipOrdersList) {
		AipResponse before = beforeHttpRequest(context);
		if (before.getCode() != AipResponse.CODE_SUCESS)
			return before;

		String json = new Gson().toJson(aipOrdersList);
		return remoteCallPost(context, PATH_ORDER_LIST + "?access_token=" + access_token, json);
	}

	/**
	 * 获取订单详情
	 * 
	 * @param context
	 * @param aipOrderDetails
	 *            {@link AipOrderDetails}
	 * @return {@link AipResponse}
	 */
	public static AipResponse getOrderDetais(Context context, AipOrderDetails aipOrderDetails) {
		AipResponse before = beforeHttpRequest(context);
		if (before.getCode() != AipResponse.CODE_SUCESS)
			return before;

		String json = new Gson().toJson(aipOrderDetails);
		return remoteCallPost(context, PATH_ORDER_DETAILS + "?access_token=" + access_token, json);
	}

	/**
	 * 更新订单状态
	 * 
	 * @param context
	 * @param aipUpdateOrderStatus
	 *            {@link AipUpdateOrderStatus}
	 * @return {@link AipResponse}
	 */
	public static AipResponse updateOrderStatus(Context context, AipUpdateOrderStatus aipUpdateOrderStatus) {
		AipResponse before = beforeHttpRequest(context);
		if (before.getCode() != AipResponse.CODE_SUCESS)
			return before;

		String json = new Gson().toJson(aipUpdateOrderStatus);
		return remoteCallPost(context, PATH_ORDER_UPDATE + "?access_token=" + access_token, json);
	}

	/**
	 * 刷新订单状态
	 * 
	 * @param context
	 * @param aipTouchOrderStatus
	 *            {@link AipTouchOrderStatus}
	 * @return {@link AipResponse}
	 */
	public static AipResponse touchOrderStatus(Context context, AipTouchOrderStatus aipTouchOrderStatus) {
		AipResponse before = beforeHttpRequest(context);
		if (before.getCode() != AipResponse.CODE_SUCESS)
			return before;

		String json = new Gson().toJson(aipTouchOrderStatus);
		return remoteCallPost(context, PATH_ORDER_TOUCH + "?access_token=" + access_token, json);
	}

	/**
	 * 获取餐位类型列表
	 * 
	 * @param context
	 * @return {@link AipResponse}
	 */
	public static AipResponse getSeatList(Context context) {
		AipResponse before = beforeHttpRequest(context);
		if (before.getCode() != AipResponse.CODE_SUCESS)
			return before;

		return remoteCallGet(context, PATH_SEAT_TYPE_LIST + "?access_token=" + access_token);
	}

	/**
	 * 更新队伍状态
	 * 
	 * @param context
	 * @param aipTakeNumber
	 *            {@link AipTakeNumber}
	 * @return {@link AipResponse}
	 */
	public static AipResponse updateQueueStatus(Context context, AipUpdateQueueStatus aipUpdateQueueStatus) {
		AipResponse before = beforeHttpRequest(context);
		if (before.getCode() != AipResponse.CODE_SUCESS)
			return before;

		String json = new Gson().toJson(aipUpdateQueueStatus);
		return remoteCallPost(context, PATH_QUEUE_STATUS + "?access_token=" + access_token, json);
	}

	/**
	 * 队伍领号
	 * 
	 * @param context
	 * @param aipTakeNumber
	 *            {@link AipTakeNumber}
	 * @return {@link AipResponse}
	 */
	public static AipResponse takeQueueNumber(Context context, AipTakeNumber aipTakeNumber) {
		AipResponse before = beforeHttpRequest(context);
		if (before.getCode() != AipResponse.CODE_SUCESS)
			return before;

		String json = new Gson().toJson(aipTakeNumber);
		return remoteCallPost(context, PATH_QUEUE_TAKE_NUMBER + "?access_token=" + access_token, json);
	}

	/**
	 * 获取队伍列表
	 * 
	 * @param context
	 * @param aipUpdateQueueStatus
	 *            {@link AipUpdateQueueStatus}
	 * @return {@link AipResponse}
	 */
	public static AipResponse getQueueList(Context context, AipQueue aipQueue) {
		AipResponse before = beforeHttpRequest(context);
		if (before.getCode() != AipResponse.CODE_SUCESS)
			return before;

		String json = new Gson().toJson(aipQueue);
		return remoteCallPost(context, PATH_QUEUE_LIST + "?access_token=" + access_token, json);
	}

	/**
	 * HTTP POST
	 * 
	 * @param context
	 * @param path
	 *            接口路径
	 * @param json
	 *            JSON格式的数据
	 * @return {@link AipResponse}
	 */
	private static AipResponse remoteCallPost(Context context, String path, String json) {
		AipResponse after = afterHttpRequest(context,
				httpRequestProcess(context, NetworkCommunication.postRequest(path, json)));
		if (after.getCode() == AipResponse.CODE_RELOGIN) {
			return httpRequestProcess(context, NetworkCommunication.postRequest(path, json));
		}

		return after;
	}

	/**
	 * HTTP GET
	 * 
	 * @param context
	 * @param path
	 *            接口路径
	 * @return {@link AipResponse}
	 */
	private static AipResponse remoteCallGet(Context context, String path) {
		AipResponse after = afterHttpRequest(context,
				httpRequestProcess(context, NetworkCommunication.getRequest(path)));
		if (after.getCode() == AipResponse.CODE_RELOGIN) {
			return httpRequestProcess(context, NetworkCommunication.getRequest(path));
		}

		return after;
	}

	/**
	 * HTTP 请求处理
	 * 
	 * @param context
	 * @param response
	 *            HTTP 应答
	 * @return {@link AipResponse}
	 */
	private static AipResponse httpRequestProcess(Context context, String response) {
		AipResponse ar = new AipResponse();

		if (response == null) {
			ar.setCode(AipResponse.CODE_NETWORK_NO_RESPONSE);
			ar.setMsg(context.getResources().getString(R.string.network_no_response));
			return ar;
		}

		try {
			Gson gson = new Gson();
			// 如果出现空格，需转义，防止程序崩溃
			response = response.replace(" ", "\b");
			ar = gson.fromJson(response, AipResponse.class);
		} catch (JsonSyntaxException e) {
			ar.setCode(AipResponse.CODE_DATA_PARSE_ERROR);
			ar.setMsg(context.getResources().getString(R.string.data_parse_error));
			return ar;
		}

		return ar;
	}

	/**
	 * 在进行HTTP请求前需要做的处理
	 * 
	 * @param context
	 * @return {@link AipResponse}
	 */
	private static AipResponse beforeHttpRequest(Context context) {
		if (!NetworkUtil.isNetWorkAvilable(context)) {
			return new AipResponse(AipResponse.CODE_NETWORK_NOT_AVAILABLE, context.getResources().getString(
					R.string.network_not_available));
		}

		if (isExpired()) {
			AipResponse login = Login(context, username, passwordMd5, userId);
			if (login.getCode() != AipResponse.CODE_SUCESS) {
				return login;
			}
		}

		return new AipResponse(AipResponse.CODE_SUCESS, null);
	}

	/**
	 * 在进行HTTP请求后需要做的处理
	 * 
	 * @param context
	 * @param response
	 * @return {@link AipResponse}
	 */
	private static AipResponse afterHttpRequest(Context context, AipResponse response) {
		if (response.getCode() == AipResponse.CODE_ACCESS_TOKEN_EXPIRED) {
			AipResponse login = Login(context, username, passwordMd5, userId);
			if (login.getCode() == AipResponse.CODE_SUCESS) {
				return new AipResponse(AipResponse.CODE_RELOGIN, null);
			} else {
				return login;
			}
		}

		return response;
	}

	/**
	 * 检查access_token是否失效
	 * 
	 * @return true 已失效; false 未失效
	 */
	private static boolean isExpired() {
		if (System.currentTimeMillis() / 1000 + 10 >= loginTime + expires_in)
			return true;

		return false;
	}

	/**
	 * 获取用户密码的MD5值
	 * 
	 * @return 用户密码的MD5值
	 */
	public static String getPasswordMd5() {
		return passwordMd5;
	}
}
