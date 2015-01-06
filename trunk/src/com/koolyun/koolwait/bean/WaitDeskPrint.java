package com.koolyun.koolwait.bean;

import java.io.Serializable;

public class WaitDeskPrint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int number;
	private int waitingCount;
	private String deskType;
	private String qrcode;
	private String merchantName;
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getWaitingCount() {
		return waitingCount;
	}
	public void setWaitingCount(int waitingCount) {
		this.waitingCount = waitingCount;
	}
	public String getDeskType() {
		return deskType;
	}
	public void setDeskType(String deskType) {
		this.deskType = deskType;
	}
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
}
