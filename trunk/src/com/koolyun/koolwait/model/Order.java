package com.koolyun.koolwait.model;

import java.util.Arrays;

import android.content.Context;
import android.content.res.Resources;

import com.koolyun.koolwait.R;

/**
 * 订单详情
 * 
 * @author Edwin
 *
 */
public class Order {

	/**
	 * 订单类型：订座
	 */
	public static final int TYPE_RESERVATION = 1;

	/**
	 * 订单类型：订单
	 */
	public static final int TYPE_ORDER = 2;

	/**
	 * 订单类型：外卖
	 */
	public static final int TYPE_TAKEOUT = 3;

	/**
	 * 订单类型：团购
	 */
	public static final int TYPE_GROUP = 4;

	/**
	 * 订单ID
	 */
	long id;

	/**
	 * 订单编号
	 */
	String order_no;

	/**
	 * 订单类型（1：订座，2：点单，3：外卖，4：团购）
	 */
	int type;

	/**
	 * 订单创建时间（格式为yyyy-MM-dd HH:mm）
	 */
	String created_time;

	/**
	 * 对于订座订单，表示预订就餐时间；对于点单订单，表示现场下单时间；对于外卖订单，表示希望送达时间。（格式为yyyy-MM-dd HH:mm）
	 */
	String dinner_time;

	/**
	 * 对于订座订单，表示预订餐位类型；对于点单订单，表示正在使用的餐位号；外卖订单无餐位，该字段固定为空字符串（””）
	 */
	String seat;

	/**
	 * 对于外卖订单，表示外卖餐具需求。订座订单与点单订单无此需求，该字段固定为空字符串（””）。
	 */
	String tableware;

	/**
	 * 订单支付方式（1：定金支付，2：全额支付）。点单订单与外卖订单必须是全额支付，所以该字段固定为2。
	 */
	int pay_method;

	/**
	 * 订单结算状态（1：未结清，2：已结清）。对于订座订单，“未结清”表示订单为定金支付且尚未补足余款，“已结清”表示订单为全额支付，
	 * 或者为定金支付且已补足余款；对于点单订单，“未结清”表示未付款，“已结清”表示已付款。外卖订单必须结清，所以该字段固定为2。
	 */
	int clear;

	/**
	 * 订单完成状态（1：未完成，2：已完成）。对于订座订单与点单订单，“未完成”表示未消费，“已完成”表示已消费；对于外卖的订单，“未完成”表示未派送，
	 * “已完成”表示已派送。
	 */
	public static final int COMPLETED_STATUS_NO = 1;
	public static final int COMPLETED_STATUS_YES = 2;
	
	int completed;

	/**
	 * 订单是否超时（1：未超时，2：已超时）。该字段只对订座订单有意义，表示已经过了订单预订时间。点单订单与外卖订单该字段固定为1。
	 */
	public static final int EXPIRED_STATUS_NO = 1;
	public static final int EXPIRED_STATUS_YES = 2;
	int expired;

	/**
	 * 联系人姓名
	 */
	String contact_person;

	/**
	 * 联系人电话
	 */
	String contact_tel;

	/**
	 * 联系人地址。仅外卖订单，订座订单与点单订单无此信息，该字段固定为空字符串（””）。
	 */
	String contact_address;

	/**
	 * 订单备注
	 */
	String comments;

	/**
	 * 订单应付总金额
	 */
	double amount;

	/**
	 * 订单已支付金额
	 */
	double paid_amount;

	/**
	 * 团购名称。仅团购订单，其他订单无此信息，该字段固定为空字符串。
	 */
	String groupon_name;

	/**
	 * 团购有效期起始时间。格式为 yyyy-MM-dd。仅团购订单，其他订单无此信息，该字段固定为空字符串。
	 */
	String groupon_start;

	/**
	 * 团购有效期结束时间。格式为 yyyy-MM-dd。仅团购订单，其他订单无此信息，该字段固定为空字符串。
	 */
	String groupon_end;

	/**
	 * 菜单列表
	 */
	Menu[] menu;

	public long getId() {
		return id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public int getType() {
		return type;
	}

	public String getCreated_time() {
		return created_time;
	}

	public String getDinner_time() {
		return dinner_time;
	}

	public String getSeat() {
		return seat;
	}

	public String getTableware() {
		return tableware;
	}

	public int getPay_method() {
		return pay_method;
	}

	public int getClear() {
		return clear;
	}

	public int getCompleted() {
		return completed;
	}

	public int getExpired() {
		return expired;
	}

	public String getContact_person() {
		return contact_person;
	}

	public String getContact_tel() {
		return contact_tel;
	}

	public String getContact_address() {
		return contact_address;
	}

	public String getComments() {
		return comments;
	}

	public double getAmount() {
		return amount;
	}

	public double getPaid_amount() {
		return paid_amount;
	}

	public String getGroupon_name() {
		return groupon_name;
	}

	public String getGroupon_start() {
		return groupon_start;
	}

	public String getGroupon_end() {
		return groupon_end;
	}

	public Menu[] getMenu() {
		return menu;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", order_no=" + order_no + ", type=" + type + ", created_time=" + created_time
				+ ", dinner_time=" + dinner_time + ", seat=" + seat + ", tableware=" + tableware + ", pay_method="
				+ pay_method + ", clear=" + clear + ", completed=" + completed + ", expired=" + expired
				+ ", contact_person=" + contact_person + ", contact_tel=" + contact_tel + ", contact_address="
				+ contact_address + ", comments=" + comments + ", amount=" + amount + ", paid_amount=" + paid_amount
				+ ", groupon_name=" + groupon_name + ", groupon_start=" + groupon_start + ", groupon_end="
				+ groupon_end + ", menu=" + Arrays.toString(menu) + "]";
	}

	/**
	 * 菜单
	 * 
	 * @author Edwin
	 *
	 */
	public class Menu {

		/**
		 * 菜品名称
		 */
		String name;

		/**
		 * 菜品数量
		 */
		int count;

		/**
		 * 菜品单价
		 */
		double price;

		/**
		 * 菜品金额小计
		 */
		double total;

		public String getName() {
			return name;
		}

		public int getCount() {
			return count;
		}

		public double getPrice() {
			return price;
		}

		public double getTotal() {
			return total;
		}

		@Override
		public String toString() {
			return "Menu [name=" + name + ", count=" + count + ", price=" + price + ", total=" + total + "]";
		}
	}

	/**
	 * 更新订单状态
	 * 
	 * @param os
	 *            {@link OrderStatus}
	 */
	public void updateOrderStatus(OrderStatus os) {
		this.clear = os.clear;
		this.completed = os.completed;
		this.expired = os.expired;
		this.paid_amount = os.paid_amount;
	}

	/**
	 * 获取订单类型名称
	 * 
	 * @param context
	 * @return 订单类型名称
	 */
	public String getTypeString(Context context) {
		String typeName = "";
		Resources r = context.getResources();

		switch (type) {
		case Order.TYPE_RESERVATION:
			typeName = r.getString(R.string.reservations2);
			break;
		case Order.TYPE_ORDER:
			typeName = r.getString(R.string.orders2);
			break;
		case Order.TYPE_TAKEOUT:
			typeName = r.getString(R.string.takeaway2);
			break;
		case Order.TYPE_GROUP:
			typeName = r.getString(R.string.group);
			break;
		default:
			break;
		}

		return typeName;
	}
}