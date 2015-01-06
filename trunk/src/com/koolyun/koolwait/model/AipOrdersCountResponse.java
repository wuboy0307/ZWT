package com.koolyun.koolwait.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 调用接口/easyfood-mposapi/order/count时从服务器返回的数据
 * 
 * @author Edwin
 *
 */
public class AipOrdersCountResponse implements Parcelable {

	private Reservation reservation = new Reservation();
	private Local local = new Local();
	private Takeout take_out = new Takeout();

	protected AipOrdersCountResponse(Parcel in) {
		reservation = new Reservation(in);
		local = new Local(in);
		take_out = new Takeout(in);
	}

	@Override
	public String toString() {
		return "AipOrdersCountResponse [reservation=" + reservation + ", local=" + local + ", take_out=" + take_out
				+ "]";
	}

	public Reservation getReservation() {
		return reservation;
	}

	public Local getLocal() {
		return local;
	}

	public Takeout getTake_out() {
		return take_out;
	}

	/**
	 * 订座
	 * 
	 * @author Edwin
	 *
	 */
	public class Reservation implements Parcelable {
		private int total;
		private int not_completed;
		private int completed;
		private int paid_in_full;
		private int partially_paid;

		public Reservation() {

		}

		protected Reservation(Parcel in) {
			total = in.readInt();
			not_completed = in.readInt();
			completed = in.readInt();
			paid_in_full = in.readInt();
			partially_paid = in.readInt();
		}

		@Override
		public String toString() {
			return "Reservation [total=" + total + ", not_completed=" + not_completed + ", completed=" + completed
					+ ", paid_in_full=" + paid_in_full + ", partially_paid=" + partially_paid + "]";
		}

		public int getTotal() {
			return total;
		}

		public int getNot_completed() {
			return not_completed;
		}

		public int getCompleted() {
			return completed;
		}

		public int getPaid_in_full() {
			return paid_in_full;
		}

		public int getPartially_paid() {
			return partially_paid;
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			out.writeInt(total);
			out.writeInt(not_completed);
			out.writeInt(completed);
			out.writeInt(paid_in_full);
			out.writeInt(partially_paid);
		}
	}

	/**
	 * 点单
	 * 
	 * @author Edwin
	 *
	 */
	public class Local implements Parcelable {
		private int total;
		private int not_completed;
		private int completed;
		private int unpaid;
		private int paid;

		public Local() {

		}

		protected Local(Parcel in) {
			total = in.readInt();
			not_completed = in.readInt();
			completed = in.readInt();
			unpaid = in.readInt();
			paid = in.readInt();
		}

		@Override
		public String toString() {
			return "Local [total=" + total + ", not_completed=" + not_completed + ", completed=" + completed
					+ ", unpaid=" + unpaid + ", paid=" + paid + "]";
		}

		public int getTotal() {
			return total;
		}

		public int getNot_completed() {
			return not_completed;
		}

		public int getCompleted() {
			return completed;
		}

		public int getUnpaid() {
			return unpaid;
		}

		public int getPaid() {
			return paid;
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			out.writeInt(total);
			out.writeInt(not_completed);
			out.writeInt(completed);
			out.writeInt(unpaid);
			out.writeInt(paid);
		}

	}

	/**
	 * 外卖
	 * 
	 * @author Edwin
	 *
	 */
	public class Takeout implements Parcelable {
		private int total;
		private int not_completed;
		private int completed;

		public Takeout() {

		}

		protected Takeout(Parcel in) {
			total = in.readInt();
			not_completed = in.readInt();
			completed = in.readInt();
		}

		@Override
		public String toString() {
			return "Takeout [total=" + total + ", not_completed=" + not_completed + ", completed=" + completed + "]";
		}

		public int getTotal() {
			return total;
		}

		public int getNot_completed() {
			return not_completed;
		}

		public int getCompleted() {
			return completed;
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			out.writeInt(total);
			out.writeInt(not_completed);
			out.writeInt(completed);
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		reservation.writeToParcel(out, flags);
		local.writeToParcel(out, flags);
		take_out.writeToParcel(out, flags);
	}

	public static final Parcelable.Creator<AipOrdersCountResponse> CREATOR = new Parcelable.Creator<AipOrdersCountResponse>() {

		@Override
		public AipOrdersCountResponse createFromParcel(Parcel in) {
			return new AipOrdersCountResponse(in);
		}

		@Override
		public AipOrdersCountResponse[] newArray(int size) {
			return new AipOrdersCountResponse[size];
		}
	};
}
