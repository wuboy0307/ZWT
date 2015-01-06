package com.koolyun.koolwait.model;

/**
 * 队列号码
 * 
 * @author Edwin
 *
 */
public class QueueNum {

	private int num;

	public QueueNum() {

	}

	public QueueNum(int num) {
		this.num = num;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public static void main(String[] args) {
		System.out.println(String.format("%03d", "1"));
	}

	// public static void main(String[] args){
	// int a = 1;
	// System.out.println( String.format("%03d",a));
	// }
}
