package com.koolyun.koolwait.model;

import java.util.Arrays;

/**
 * 调用接口/easyfood-mposapi/seat/list时从服务器返回的数据
 * 
 * @author Edwin
 *
 */
public class AipSeatCategoriesResponse {

	/**
	 * 餐位分类列表
	 */
	private SeatCategory[] seat_categories;

	public SeatCategory[] getSeat_categories() {
		return seat_categories;
	}

	@Override
	public String toString() {
		return "AipSeatCategoriesResponse [seat_categories=" + Arrays.toString(seat_categories) + "]";
	}

	/**
	 * 餐位分类
	 * 
	 * @author Edwin
	 *
	 */
	public class SeatCategory {
		/**
		 * 餐位分类ID
		 */
		private long id;

		/**
		 * 餐位分类名称
		 */
		private String name;

		/**
		 * 该餐位至少应容纳人数
		 */
		private int min_num;

		/**
		 * 该餐位至多可容纳人数
		 */
		private int max_num;

		@Override
		public String toString() {
			return "SeatCategory [id=" + id + ", name=" + name + ", min_num=" + min_num + ", max_num=" + max_num + "]";
		}

		public long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public int getMin_num() {
			return min_num;
		}

		public int getMax_num() {
			return max_num;
		}

	}

	public AipQueue toAipQueue() {
		long[] categories = new long[seat_categories.length];
		for (int i = 0; i < categories.length; i++) {
			categories[i] = seat_categories[i].getId();
		}

		return new AipQueue(categories);
	}

	// @Override
	// public int describeContents() {
	// return 0;
	// }
	//
	// @Override
	// public void writeToParcel(Parcel out, int flags) {
	// out.writeLong(id);
	// out.writeString(name);
	// }
	//
	// public static final Parcelable.Creator<AipSeatCategoriesResponse> CREATOR
	// = new Parcelable.Creator<AipSeatCategoriesResponse>() {
	//
	// @Override
	// public AipSeatCategoriesResponse createFromParcel(Parcel in) {
	// AipSeatCategoriesResponse r = new AipSeatCategoriesResponse();
	// r.id = in.readLong();
	// r.name = in.readString();
	// return r;
	// }
	//
	// @Override
	// public AipSeatCategoriesResponse[] newArray(int size) {
	// return new AipSeatCategoriesResponse[size];
	// }
	// };
}
