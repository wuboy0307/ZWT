package com.koolyun.koolwait.model;

public class ConsumMenuDetail {

	private String vegetableName;
	private double price;
	private int num;
	// 保留
	private double priceResult;

	public ConsumMenuDetail() {

	}

	public ConsumMenuDetail(String vegetableName, double price, int num, double priceResult) {
		this.vegetableName = vegetableName;
		this.price = price;
		this.num = num;
		this.priceResult = priceResult;
	}

	public String getVegetableName() {
		return vegetableName;
	}

	public void setVegetableName(String vegetableName) {
		this.vegetableName = vegetableName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getPriceResult() {
		return priceResult;
	}

	public void setPriceResult(double priceResult) {
		this.priceResult = priceResult;
	}

}
