package com.koolyun.coupon.print.ticket;

import com.koolyun.coupon.print.control.BasePrinterInterface;
import com.koolyun.coupon.print.control.PrinterImpl;
import com.koolyun.coupon.print.exception.AccessException;
import com.koolyun.koolwait.bean.WaitDeskPrint;
import com.koolyun.koolwait.model.NetworkCommunication;

public class TicketPrint {
	
	PrinterImpl mPrinterImpl = new PrinterImpl();
	private final int upWidth = 300;
	private final int downWidth = 300;

	public void printWaitingNumber(WaitDeskPrint coupon) throws AccessException
	{
		mPrinterImpl.open();
		mPrinterImpl.setAlign(BasePrinterInterface.ALIGN_CENTER);
		mPrinterImpl.setDoubleFormat(BasePrinterInterface.DOUBLE_NONE);
		mPrinterImpl.setPrinterMode(BasePrinterInterface.MODE_BOLD);
		mPrinterImpl.printerText("让排队再轻松一点\n");
		mPrinterImpl.printerText("----------------------------\n");

		mPrinterImpl.setPrinterMode(BasePrinterInterface.MODE_NORMAL);
		mPrinterImpl.printerText(coupon.getMerchantName()+"\n\n");
		mPrinterImpl.setDoubleFormat(3);
		mPrinterImpl.printerText(coupon.getDeskType() + coupon.getNumber() + "号\n");
		mPrinterImpl.setDoubleFormat(0);
		mPrinterImpl.printerText("目前已排到："+coupon.getWaitingCount() + "号。\n\n");
		mPrinterImpl.setAlign(BasePrinterInterface.ALIGN_LEFT);

		mPrinterImpl.setAlign(BasePrinterInterface.ALIGN_CENTER);
		mPrinterImpl.printerText("关注酷餐饮，轻松远程排队！");
		
		if(NetworkCommunication.URL.equals(NetworkCommunication.FORMAL_URL)) {
			mPrinterImpl.printTwoDBarCode("http://weixin.qq.com/r/DEhyam-E-TKCrSsJ9x0e", downWidth, downWidth); // product
		} else {
			mPrinterImpl.printTwoDBarCode("http://weixin.qq.com/r/bnVudiDEon3drWQV9yB8", downWidth, downWidth); // test
		}
		
		mPrinterImpl.printerText("排到哪儿了？扫一扫就知道！");
		//打印二维码
//		mPrinterImpl.setAlign(BasePrinterInterface.ALIGN_CENTER);
		mPrinterImpl.printTwoDBarCode(coupon.getQrcode()+"", upWidth, upWidth);

		mPrinterImpl.printerText(" \n \n \n \n");
		mPrinterImpl.close();
	}
	
}
