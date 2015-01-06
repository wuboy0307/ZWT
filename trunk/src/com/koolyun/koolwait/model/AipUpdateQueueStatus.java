package com.koolyun.koolwait.model;

/**
 * 调用接口/easyfood-mposapi/queue/update时POST给服务器的数据
 * 
 * @author Edwin
 */
public class AipUpdateQueueStatus {

	/**
	 * 开始排队
	 */
	public static final int CREATE_QUEUE = 1;

	/**
	 * 叫号
	 */
	public static final int NEXT = 2;

	/**
	 * 停止排队
	 */
	public static final int STOP_QUEUE = 3;
	/**
	 * 暂停指定餐位类型下正在进行的队伍（暂停后只叫号，不发号，已发的号还没叫到的任然有效）
	 */
	public static final int PAUSE_QUEUE = 4;
	/**
	 * 恢复指定餐位类型下被暂停的队伍（恢复后可以继续发号叫号）
	 */
	public static final int RESUME_QUEUE = 5;
	/**
	 * 停止所有的队伍（停止后无法再叫号和发号，已发的号全部作废）
	 */
	public static final int STOP_ALL_QUEUE = 6;

	/**
	 * 餐位类型ID
	 */
	long seat_category_id;

	/**
	 * 队伍更新动作，目前支持如下操作 1：在指定餐位类型下建新一个队伍 2：下一位 3：停止指定餐位类型下正在进行的队伍
	 */
	int operation;

	public AipUpdateQueueStatus(long seat_category_id, int operation) {
		this.seat_category_id = seat_category_id;
		this.operation = operation;
	}

	public int getOperation() {
		return operation;
	}

}
