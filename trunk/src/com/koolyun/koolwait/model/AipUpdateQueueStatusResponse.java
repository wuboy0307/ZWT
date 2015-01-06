package com.koolyun.koolwait.model;

/**
 * 调用接口/easyfood-mposapi/queue/update时从服务器返回的数据
 * 
 * @author Edwin
 *
 */
public class AipUpdateQueueStatusResponse {
	/**
	 * 更新后的队伍（如果调用操作为“停止队伍”，queue为null）
	 */
	private Queue queue;

	public Queue getQueue() {
		return queue;
	}

	@Override
	public String toString() {
		return "AipUpdateQueueStatusResponse [queue=" + queue + "]";
	}

	/**
	 * 更新后的队伍（如果调用操作为“停止队伍”，queue为null）
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
		 * 该队伍的状态（1：排队中；2：已暂停，暂停的队伍只能叫号不能发号）（由于暂停的队伍不能领号，如果调用该接口成功，则队伍的状态应该始终为1）
		 */
		int status;

		public int getCalled_number() {
			return called_number;
		}

		public int getDelivered_number() {
			return delivered_number;
		}

		@Override
		public String toString() {
			return "Queue [called_number=" + called_number + ", delivered_number=" +
					delivered_number + ", status=" + status + "]";
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
