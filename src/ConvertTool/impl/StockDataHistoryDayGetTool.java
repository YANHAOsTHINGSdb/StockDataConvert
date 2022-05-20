package ConvertTool.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.ptr.ShortByReference;

import InputData.TdxHqApi209912301;

public class StockDataHistoryDayGetTool  extends StockData爸爸 implements ConvertTool.ConvertTool{
	byte[] Result = new byte[65535];
	byte[] ErrInfo = new byte[256];
	byte[] Market = { 0, 1 };
	byte[] Zqdm = null;
	byte[] 取得行情数据 = null;
	int 每次取得行情数据= 20;
	ShortByReference Count=new ShortByReference();
	final static byte 深圳 = (byte)0;
	final static byte 上海 = (byte)1;


	public static void main(String[] args) {
//		// TODO 自動生成されたメソッド・スタブ
//		new StockDataConvertTool().write2(
//				"test.DAY",
//				new String[]{"20190417","6098","7260","6098","7059","319796","487649"}
//				);

		// new StockDataConvertTool().输出到文件("/Applications/Eclipse_2018-12.app/Contents/workspace/StockDataConvert/data/沪深Ａ股20190417.txt");
		// PROPERTY.取得送信URL_head();
		new StockDataHistoryDayGetTool().取得股票代码List();
	}

	@Override
	public void 输出到文件(String inputFileFullPathName) {
		// TODO 自動生成されたメソッド・スタブ

	}

	// 取得股票代码List
	void 取得股票代码List() {
		byte[] 股票代码Result = new byte[1024*1024];
		List<String[]> 股票信息ArrayList = new ArrayList();
		ShortByReference Count = new ShortByReference();
		//Count.setValue((short)1);
		List<String> 取得行情 = new ArrayList<String>();
		byte[] ErrInfo = new byte[256];

		if (TdxHqApi209912301.getConnect(PROPERTY.取得IP(), PROPERTY.取得Port(), Result, ErrInfo)) {
			if (TdxHqApi209912301.getGetSecurityList(深圳, (short) 0, Count, 股票代码Result, ErrInfo, 股票信息ArrayList)) {

				股票信息ArrayList =去除List中重复的数据(股票信息ArrayList);
				for(String[] s股票信息 : 股票信息ArrayList) {
					String s股票代码 = s股票信息[0];
					String s股票名称 = s股票信息[2];
					if( s股票代码 == null )continue;

//					if (TdxHqApi209912301.getHistoryTransactionData( 深圳, s股票代码, (short) 0,  Count, Integer.parseInt(PROPERTY.取得指定历史数据日期()), Result, ErrInfo)) {
//						取得行情.addAll(getStringListByGBK2(Result, s股票名称));
//					}

					if (TdxHqApi209912301.getSecurityBars((byte)9, 深圳,  s股票代码, (short) 0,  Count, Result, ErrInfo)) {
						取得行情.addAll(getStringListByGBK2(Result, s股票名称));
					}
				}
			}
			if (TdxHqApi209912301.getGetSecurityList(上海, (short) 0, Count, 股票代码Result, ErrInfo, 股票信息ArrayList)) {

				股票信息ArrayList.addAll(去除List中重复的数据(股票信息ArrayList));
				for(String[] s股票代码 : 股票信息ArrayList) {
					if (TdxHqApi209912301.getHistoryTransactionData( 上海, s股票代码[0], (short) 0,  Count, Integer.parseInt(PROPERTY.取得指定历史数据日期()), Result, ErrInfo)) {

					}
				}
			}
		}


	}

	// 取得指定日期


	// 取得指定日期的指定股票数据
	void 取得指定日期的指定股票数据(byte 市场CD) {
		// byte[] 行情数据 = TdxHqApi209912301.getGetSecurityQuotes(市场数据, 股票代码, Count, Result, ErrInfo);
		if (TdxHqApi209912301.getHistoryTransactionData( Market[0],  new String(Zqdm, StandardCharsets.UTF_8), (short) 0,  Count, 20200519, Result, ErrInfo)) {

		}
	}

	/**
	 * 去除List中重复的数据
	 * @param list
	 * @return
	 */
	public static List 去除List中重复的数据(List list) {
		/**
		 * HashSet简单的理解就是HashSet对象中不能存储相同的数据，存储数据时是无须的。
		 * 但是HashSet存储元素的顺序并不是按照存入时的顺序（和List显然不同）
		 * 是按照哈希值来存的所以取数据也是按照哈希值取得。
		 * 存储是无序的这就和C++里的Set就不一样了C++里面的Set是有序的我认为这是在使用时候的主要区别
		 */
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}

	// 处力结果
	// [15:30	100.000000	0	B	0	湖北2192]
	private static List<String> getStringListByGBK2(byte[] 行情数据, String s股票名称) {
		List<String> ResultListByGBK = new ArrayList();

		if(行情数据 == null || 行情数据.length == 0) {
			return null;
		}
		//------------------------
		// 取出行情数据
		//------------------------
		String s = Native.toString(行情数据, "GBK");
		String s1[] = s.split("\n");
		for(int o=1; o<s1.length; o++ ) {
			ResultListByGBK.add(s1[o].concat("\t").concat(s股票名称));
		}
		return ResultListByGBK;
	}

}
