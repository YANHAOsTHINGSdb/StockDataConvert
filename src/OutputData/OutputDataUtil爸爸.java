package OutputData;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OutputDataUtil爸爸 {
	String[] sStockCode =
			new String[] {
					"SH",
					"SZ"
			};
	/**
	 *
	 * @param inputData
	 * @return
	 */
	protected static byte[] convertFloattoByte(float inputData) {
		//Float.floatToIntBits(utCtime))
		return float倒叙(inputData);
	}
	/**
	 *
	 * @param inputData
	 * @return
	 */
	protected static byte[] convertInttoByte(int inputData) {
		return int倒叙(inputData);
	}
	/**
	 *
	 * @param inputData
	 * @return
	 */
	protected static byte[] convertChartoByte(char[] inputData) {
		return char倒叙(inputData);
	}

	protected static byte[] convertGB2312ChartoByte(char[] stockName) {
		ByteBuffer buffer = ByteBuffer.allocate(12);
		byte[] result = null;
			try {
				result = new String(stockName).getBytes("GB2312");
			} catch (UnsupportedEncodingException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		buffer.put(result);
		return buffer.array();
	}

	// java的二进制存储是倒着的
	// 所以要想把东西原封不动的转移给C语言的程序，
	// 就需要倒叙输出。
	static private byte[] int倒叙(int inputData) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(inputData);
		return buffer.array();
	}

	// java的二进制存储是倒着的
	// 所以要想把东西原封不动的转移给C语言的程序，
	// 就需要倒叙输出。
	static private byte[] float倒叙(float inputData) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putFloat(inputData);
		return buffer.array();
	}

	// java的二进制存储是倒着的
	// 所以要想把东西原封不动的转移给C语言的程序，
	// 就需要倒叙输出。
	static private byte[] char倒叙(char[] inputData) {
		ByteBuffer buffer = ByteBuffer.allocate(12);
		byte[] result = new String(inputData).getBytes();
		if(result.length>12) {
			try {
				result = new String(inputData).getBytes("GB2312");
			} catch (UnsupportedEncodingException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		buffer.put(result);
		return buffer.array();
	}

	/**
	 *
	 * @param string 20190101
	 * @return
	 */
	@SuppressWarnings("deprecation")
	protected static int getUTCtime(String yyyyMMdd) {
		//--------------------
		// String to Date
		//--------------------
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyyMMdd").parse(yyyyMMdd);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		//--------------------
		// get Date's UTCtime
		//--------------------
//		SimpleDateFormat dateFormat = new SimpleDateFormat("");
//		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//		System.out.println("UTC Time is: " + dateFormat.format(date));
		// return (int)date.getTime();
		// return (int)date.getTime() % 1000;
		return (int)(date.getTime() / 1000);
	}

	/**
	 *
	 * @param str
	 */
	protected String 取得市场代号(String s股票代号) {
		if(s股票代号.charAt(0)=='6') {
			return sStockCode[0];
		}
		return sStockCode[1];
	}

}
