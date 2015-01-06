package com.koolyun.coupon.print.control;


import com.koolyun.coupon.print.entity.CharacterSetting_Command;
import com.koolyun.coupon.print.entity.FormatSetting_Command;
import com.koolyun.coupon.print.util.PrintBitmapSprt;

import android.graphics.Bitmap;

public class SprtPrinter extends DefaultPrinter {

	@Override
	String getType() {
		return "SP-POS58IVU";
	}

	@Override
	byte[] setPrinterMode(int mode) {
		/**
		 * CharacterSetting_Command.getESCEn(byte n)
		 * { 27, 69, n }
		 */
		byte cmdflag;
		final byte NORMAL = 0x00;
		final byte BOLD = 0x01;
		
		if((mode & 0x01) == 0)
		{
			cmdflag = NORMAL;
		}
		else
		{
			cmdflag = BOLD;
		}
		return CharacterSetting_Command.getESCEn(cmdflag);
	}
	
	@Override
	byte[] setDoubleSize(int flag) {

		/**
		 * CharacterSetting_Command.getGSExclamationN(byte n)
		 * { 29, 33, n }
		 */
		byte cmdflag;
		final byte NORMAL = 0x00;
		final byte DOUBLE_W = 0x10;
		final byte DOUBLE_H = 0x01;
		final byte DOUBLE_WH = 0x11;
		switch (flag) {
		case DOUBLE_NONE:
			cmdflag = NORMAL;
			break;
		case DOUBLE_WIDTH:
			cmdflag = DOUBLE_W;
			break;
		case DOUBLE_HEIGHT:
			cmdflag = DOUBLE_H;
			break;
		case DOUBLE_WIDTH_HEIGHT:
			cmdflag = DOUBLE_WH;
			break;
		default:
			cmdflag = NORMAL;
			break;
		}
		
		return CharacterSetting_Command.getGSExclamationN(cmdflag);
	}

	@Override
	byte[] setAlign(int align) {

		/**
		 * CharacterSetting_Command.getESCEn(byte n)
		 * { 27, 69, n }
		 */
		byte cmdflag;
		final byte LEFT = 0x30;
		final byte CENTER = 0x31;
		final byte RIGHT = 0x32;
		
		switch (align) {
		case ALIGN_LEFT:
			cmdflag = LEFT;
			break;
		case ALIGN_CENTER:
			cmdflag = CENTER;
			break;
		case ALIGN_RIGHT:
			cmdflag = RIGHT;
			break;
		default:
			cmdflag = LEFT;
			break;
		}
		return FormatSetting_Command.getESCan(cmdflag);
	}

	@Override
	byte[] setLineSpacing(int linespace) {
		return FormatSetting_Command.getESC3n((byte)linespace);
	}

	@Override
	void printBitmap(Bitmap mBitmap, int width, int height) {
		PrintBitmapSprt.printBitMap(mBitmap);
	}


	@Override
	byte[] printOneDBarCode(String content) {

		return null;
	}

}
