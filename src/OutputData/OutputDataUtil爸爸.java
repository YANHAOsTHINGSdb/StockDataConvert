package OutputData;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
	public static byte[] convertInttoBytePublic(int inputData) {
		return convertInttoByte(inputData);
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
			date = new SimpleDateFormat("yyyyMMdd hh:mm:ss").parse(yyyyMMdd);
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

	public static byte[] 数组合并(byte[]... 合并对象数组) {
		//------------------------------
		// 本函数的目的，把参数按顺序的拼接
		//------------------------------

		// 取得所有参数的实际大小的总和
		int i所有参数的实际大小的总和=0;
		for(byte[]对象数组 : 合并对象数组 )  {
			if(对象数组 == null || 对象数组.length == 0) continue;
			i所有参数的实际大小的总和 += 对象数组.length;
		}
		byte[] outputToByte = new byte[i所有参数的实际大小的总和];

		// 然后顺次加入其中
		int i已经加入的数据大小=0;
		for(byte[]对象数组 : 合并对象数组 )  {
			// 如果【对象数组】没有数据就忽略。
			if(对象数组 == null || 对象数组.length == 0) continue;

			// 处理内容：将数组1加到数组2
			//-----------------------------------------------------------
			// 以下是System.arraycopy的入力参数说明
			// 对象数组, 0,     outputToByte, i已经加入的数据大小, 对象数组.length
			// 数组1
			//          数组1的起始位置
			//                 数组2
			//                               数组2的起始位置
			//                                                 数组1的数据长度
			//-----------------------------------------------------------
			System.arraycopy(对象数组, 0, outputToByte, i已经加入的数据大小, 对象数组.length);
			i已经加入的数据大小 += 对象数组.length;
		}

		return outputToByte;
	}
	/**
	  * 数组组合的第2种写法
	  * @param 合并对象数组
	  * @return
	  */
	public static byte[] 数组合并2(byte[]... 合并对象数组) {
		byte[] outputToByte = {};
		int i所有参数的实际大小的总和 = 0;
		for (byte[] 对象数组 : 合并对象数组) {

			i所有参数的实际大小的总和 += 对象数组.length;
			outputToByte = Arrays.copyOf(outputToByte, i所有参数的实际大小的总和);
			System.arraycopy(对象数组, 0, outputToByte, i所有参数的实际大小的总和-对象数组.length, 对象数组.length);
		}
		return outputToByte;
	}
}
