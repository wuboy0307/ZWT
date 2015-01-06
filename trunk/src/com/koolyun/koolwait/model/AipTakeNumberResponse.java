package com.koolyun.koolwait.model;

/**
 * 调用接口/easyfood-mposapi/queue/number时从服务器返回的数据
 * 
 * @author Edwin
 *
 */
public class AipTakeNumberResponse {

	QueueNumber queue_number;
	Queue queue;

	public QueueNumber getQueue_number() {
		return queue_number;
	}

	public Queue getQueue() {
		return queue;
	}

	/**
	 * 领取到的号码
	 * 
	 * @author Edwin
	 *
	 */
	public class QueueNumber {
		/**
		 * 号码
		 */
		int number;

		/**
		 * 领取号码的时间（格式为yyyy-MM-dd HH:mm）
		 */
		String created_time;
		/**
		 * 领取号码的二维码 URL
		 */
		private String qrcode;

		public int getNumber() {
			return number;
		}

		public String getCreated_time() {
			return created_time;
		}

		public String getQrcode() {
			return qrcode;
		}

		public void setQrcode(String qrcode) {
			this.qrcode = qrcode;
		}

	}

	/**
	 * 号码所属队伍
	 * 
	 * @author Edwin
	 *
	 */
	public class Queue {

		/**
		 * 该队伍已叫到的号码
		 */
		int called_number;

		/**
		 * 该队伍已发放到的号码
		 */
		int delivered_number;
		
		/**
		 * 该队伍的状态（1：排队中；2：已暂停（暂停的队伍只能叫号不能发号）
		 */
		int status;

		public int getCalled_number() {
			return called_number;
		}

		public int getDelivered_number() {
			return delivered_number;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public void setCalled_number(int called_number) {
			this.called_number = called_number;
		}

		public void setDelivered_number(int delivered_number) {
			this.delivered_number = delivered_number;
		}

	}
}
