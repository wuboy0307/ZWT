package com.koolyun.koolwait.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订餐易数据
 * 
 * @author Edwin
 *
 */
public class OrdersBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 预定
	 */
	public static final int RESERVATION_INDEX = 1;

	/**
	 * 点单
	 */
	public static final int LOCAL_INDEX = 2;

	/**
	 * 外送
	 */
	public static final int TAKEOUT_INDEX = 3;

	/**
	 * 支持排队功能
	 */
	public static final int SUPPORT_QUEUE = 1;

	/**
	 * 不支持排队功能
	 */
	public static final int NOT_SUPPORT_QUEUE = 2;

	/**
	 * 支持外卖功能
	 */
	public static final int SUPPORT_TAKEOUT = 1;

	/**
	 * 不支持外卖功能
	 */
	public static final int NOT_SUPPORT_TAKEOUT = 2;

	private AipLoginResponse aipLoginResponse;
	private AipOrdersCountResponse aipOrdersCountResponse;
	private AipOrdersListResponse[] aipOrdersListResponse;

	private Reservation reservation = new Reservation();
	private Local local = new Local();
	private Takeout takeout = new Takeout();

	private AipSeatCategoriesResponse aipSeatCategoriesResponse;
	private AipQueueResponse aipQueueResponse;

	public OrdersBean() {

	}

	public OrdersBean(AipLoginResponse aipLoginResponse, AipOrdersCountResponse aipOrdersCountResponse,
			AipOrdersListResponse[] aipOrdersListResponse, AipSeatCategoriesResponse aipSeatCategoriesResponse,
			AipQueueResponse aipQueueResponse) {
		this.aipLoginResponse = aipLoginResponse;
		this.aipOrdersCountResponse = aipOrdersCountResponse;
		this.aipOrdersListResponse = aipOrdersListResponse;
		this.aipSeatCategoriesResponse = aipSeatCategoriesResponse;
		this.aipQueueResponse = aipQueueResponse;

		updateOrders(this.aipOrdersListResponse[OrdersBean.RESERVATION_INDEX - 1], OrdersBean.RESERVATION_INDEX);
		updateOrders(this.aipOrdersListResponse[OrdersBean.LOCAL_INDEX - 1], OrdersBean.LOCAL_INDEX);
		updateOrders(this.aipOrdersListResponse[OrdersBean.TAKEOUT_INDEX - 1], OrdersBean.TAKEOUT_INDEX);
	}

	private void updateOrders(AipOrdersListResponse aipOrdersListResponse, int index) {
		if (index == RESERVATION_INDEX) {
			updateReservation(aipOrdersListResponse.getOrders());
		} else if (index == LOCAL_INDEX) {
			updateLocal(aipOrdersListResponse.getOrders());
		} else if (index == TAKEOUT_INDEX) {
			updateTakeout(aipOrdersListResponse.getOrders());
		}
	}

	private void updateReservation(Orders[] ordersArray) {
		reservation.clearAll();
		for (Orders orders : ordersArray) {
			if (orders.getCompleted() == Orders.UNCOMPLETED_STATUS)
				reservation.unComplete.add(orders);
			else {
				reservation.complete.add(orders);
			}

//			if (orders.getPay_method() == Orders.BARGAIN_PAY_STATUS) {
//				reservation.bargin.add(orders);
//			} else {
//				reservation.allPaid.add(orders);
//			}
			reservation.total.add(orders);
		}
	}

	public void updateOrders(OrderStatus order, long orderId) {
		int size;

		size = reservation.total.size();
		for (int i = 0; i < size; i++) {
			Orders orders = reservation.total.get(i);
			if (orders.getId() == orderId) {
				orders.updateOrder(order);
				updateReservation((Orders[]) reservation.total.toArray(new Orders[size]));
				return;
			}
		}

		size = local.total.size();
		for (int i = 0; i < size; i++) {
			Orders orders = local.total.get(i);
			if (orders.getId() == orderId) {
				orders.updateOrder(order);
				updateLocal((Orders[]) local.total.toArray(new Orders[size]));
				return;
			}
		}

		size = takeout.total.size();
		for (int i = 0; i < size; i++) {
			Orders orders = takeout.total.get(i);
			if (orders.getId() == orderId) {
				orders.updateOrder(order);
				updateTakeout((Orders[]) takeout.total.toArray(new Orders[size]));
				return;
			}
		}
	}

	private void updateLocal(Orders[] ordersArray) {
		local.clearAll();
		for (Orders orders : ordersArray) {
			if (orders.getCompleted() == Orders.COMPLETED_STATUS)
				local.complete.add(orders);
			else {
				local.unComplete.add(orders);
			}

			if (orders.getClear() == Orders.CLEARED_STATUS) {
				local.paid.add(orders);
			} else {
				local.unpaid.add(orders);
			}
			local.total.add(orders);
		}
	}

	private void updateTakeout(Orders[] ordersArray) {
		takeout.clearAll();
		for (Orders orders : ordersArray) {
			if (orders.getCompleted() == Orders.COMPLETED_STATUS)
				takeout.complete.add(orders);
			else {
				takeout.unComplete.add(orders);
			}

			takeout.total.add(orders);
		}
	}

	public AipLoginResponse getAipLoginResponse() {
		return aipLoginResponse;
	}

	public AipOrdersListResponse[] getAipOrdersListResponse() {
		return aipOrdersListResponse;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public Local getLocal() {
		return local;
	}

	public Takeout getTakeout() {
		return takeout;
	}

	public AipSeatCategoriesResponse getAipSeatCategoriesResponse() {
		return aipSeatCategoriesResponse;
	}

	public AipQueueResponse getAipQueueResponse() {
		return aipQueueResponse;
	}

	public AipOrdersCountResponse getAipOrdersCountResponse() {
		return aipOrdersCountResponse;
	}

	public void setAipOrdersCountResponse(AipOrdersCountResponse aipOrdersCountResponse) {
		this.aipOrdersCountResponse = aipOrdersCountResponse;
	}

	public void setAipQueueResponse(AipQueueResponse aipQueueResponse) {
		this.aipQueueResponse = aipQueueResponse;
	}

	public void setAipOrdersListResponse(AipOrdersListResponse aipOrdersListResponse, int index) {
		this.aipOrdersListResponse[index - 1] = aipOrdersListResponse;
		updateOrders(this.aipOrdersListResponse[index - 1], index);
	}

	public void updateAipQueueResponse(AipQueueResponse aipQueueResponse) {
		this.aipQueueResponse.updateQueues(aipQueueResponse.getQueues());
	}

	public class Reservation {
		List<Orders> total = new ArrayList<Orders>();
		List<Orders> unComplete = new ArrayList<Orders>();
		List<Orders> complete = new ArrayList<Orders>();
		List<Orders> allPaid = new ArrayList<Orders>();
		List<Orders> bargin = new ArrayList<Orders>();

		public void clearAll() {
			total.clear();
			unComplete.clear();
			complete.clear();
			allPaid.clear();
			bargin.clear();
		}

		public List<Orders> getTotal() {
			return total;
		}

		public List<Orders> getUnComplete() {
			return unComplete;
		}

		public List<Orders> getComplete() {
			return complete;
		}

		public List<Orders> getAllPaid() {
			return allPaid;
		}

		public List<Orders> getBargin() {
			return bargin;
		}

	}

	public class Local {
		List<Orders> total = new ArrayList<Orders>();
		List<Orders> complete = new ArrayList<Orders>();
		List<Orders> unComplete = new ArrayList<Orders>();
		List<Orders> paid = new ArrayList<Orders>();
		List<Orders> unpaid = new ArrayList<Orders>();

		public void clearAll() {
			total.clear();
			complete.clear();
			unComplete.clear();
			paid.clear();
			unpaid.clear();
		}

		public List<Orders> getTotal() {
			return total;
		}

		public List<Orders> getSaled() {
			return complete;
		}

		public List<Orders> getUnsaled() {
			return unComplete;
		}

		public List<Orders> getPaid() {
			return paid;
		}

		public List<Orders> getUnpaid() {
			return unpaid;
		}

	}

	public class Takeout {
		List<Orders> total = new ArrayList<Orders>();
		List<Orders> complete = new ArrayList<Orders>();
		List<Orders> unComplete = new ArrayList<Orders>();

		public void clearAll() {
			total.clear();
			complete.clear();
			unComplete.clear();
		}

		public List<Orders> getTotal() {
			return total;
		}

		public List<Orders> getDelivered() {
			return complete;
		}

		public List<Orders> getUndelivered() {
			return unComplete;
		}

	}
}
