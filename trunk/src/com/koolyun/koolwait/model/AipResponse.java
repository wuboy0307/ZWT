package com.koolyun.koolwait.model;

/**
 * <P>
 * 订餐易接口应答
 * </p>
 * <p>
 * 参考《订餐易MPOS接入规范》
 * </p>
 * 
 * @author Edwin
 */
public class AipResponse {

	/**
	 * 调用返回码，0表示调用成功
	 */
	private int code;

	/**
	 * 调用结果，定义详见每个接口说明
	 */
	private Object result;

	/**
	 * 错误信息
	 */
	private String msg;

	/**
	 * 错误代码：网络无应答
	 */
	public static final int CODE_NETWORK_NO_RESPONSE = -1;

	/**
	 * 错误代码：JSON数据解析错误
	 */
	public static final int CODE_DATA_PARSE_ERROR = -2;

	/**
	 * 错误代码：网络不可用
	 */
	public static final int CODE_NETWORK_NOT_AVAILABLE = -3;

	/**
	 * 错误代码：重新登入
	 */
	public static final int CODE_RELOGIN = -100;

	/**
	 * 错误代码：凭证已过时
	 */
	public static final int CODE_ACCESS_TOKEN_EXPIRED = -101;

	/**
	 * 错误代码：成功
	 */
	public static final int CODE_SUCESS = 0;

	public AipResponse() {

	}

	public AipResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getResult() {
		return result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 判断应答是否成功
	 * 
	 * @return true 成功; false 失败
	 */
	public boolean isSuccessful() {
		if (code == CODE_SUCESS)
			return true;

		return false;
	}

	@Override
	public String toString() {
		return "AipResponse [code=" + code + ", result=" + result + ", msg=" + msg + "]";
	}

}
