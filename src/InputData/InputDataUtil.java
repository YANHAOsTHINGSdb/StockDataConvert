package InputData;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.ptr.ShortByReference;

import ConvertTool.impl.PROPERTY;

public class InputDataUtil {
	
	final static byte 深圳 = (byte)0;
	final static byte 上海 = (byte)1;
	// 取得最后交易日的数据
	public static boolean 取得最后交易日的数据(List<String> 取得行情, String sDate) {
		byte[] Result = new byte[65535];
		byte[] ErrInfo = new byte[256];
		byte[] Market = { 0, 1 };
		byte[] Zqdm = null;
		byte[] 取得行情数据 = null;
		//List<String> 取得行情 = new ArrayList();
		// 连接服务器
		if (hqApiJava1.getConnect(PROPERTY.取得IP(), PROPERTY.取得Port(), Result, ErrInfo)) {

			//【交易日】
			// 招商证券北京行情	20190430	20960430
			// 9, 50, 48, 49, 57, 48, 52, 51, 48, 
			// 9, 50, 48, 57, 54, 48, 52, 51, 48,
			sDate = 取得最后交易日期(Result);
			ShortByReference Count = new ShortByReference();
			//------------------------------------------------------------
			//【实时数据】=最后交易日期
			// 要取得实时数据（因为取不到K线数据，所以只能拿到实时数据）
			//------------------------------------------------------------
			// 0=深圳
			byte[] 深圳股票代码Result = new byte[65535];
			if (hqApiJava1.getGetSecurityList(深圳, (short) 0, Count, 深圳股票代码Result, ErrInfo)) {
				// 根据股票的结果数，取得行情数据
				List<String> 行情数据 = 取得行情数据(深圳, 深圳股票代码Result, 2);
				取得行情.addAll(行情数据);
				// 取得行情数据 = OutputDataUtil爸爸.数组合并(取得行情数据, 行情数据);
			}
			
//			byte[] 上海股票代码Result = new byte[65535];
//			// 1=上海
//			if (hqApiJava1.getGetSecurityList(上海, (short) 0, Count, 上海股票代码Result, ErrInfo)) {
//				// 根据股票的结果数，取得行情数据
//				byte[] 行情数据 = 取得行情数据(上海, 上海股票代码Result, 2);
//				取得行情数据 = OutputDataUtil爸爸.数组合并(取得行情数据, 行情数据);
//			}

		}
		// todayData = 取得Byte数组中的数据(取得行情数据,项目分割,一套分割, 44);
		return true;
	}
	
	static Byte 项目分割 = (byte)9;
	static Byte 一套分割 = (byte)10;
	/**
	 * 
	 * @param i市场代码
	 * @param 股票代码Result
	 * @param 取得行情数
	 * @return
	 */
	private static List<String> 取得行情数据(int i市场代码, byte[] 股票代码Result, int 取得行情数) {
		byte[] 取得行情数据 = null;
		byte[] Result = new byte[65535];
		byte[] ErrInfo = new byte[256];
		List<String> 取得行情 = new ArrayList();

		int 一套个数 = 8;
		取得行情数 = 2;
		List<String[]> Byte数组中的数据 = 取得Byte数组中的数据(股票代码Result, 项目分割 , 一套分割 , 一套个数);
		
		// 每500股取一次行情
		for (int i开始位置 = 0; i开始位置 < 股票代码Result.length; i开始位置 = i开始位置 + 取得行情数) {
			// 做成这500的市场数据
			byte[] 市场数据 = 做成这500的市场数据(i市场代码, 取得行情数);

			// 做成这500的股票代码
			String[] 股票代码 = 做成这500的股票代码(i市场代码, 取得行情数, i开始位置, Byte数组中的数据);

//			if(股票代码.length != 取得行情数) {
//				市场数据  = getStrFromByte(市场数据, 0, 股票代码.length);
//				break;
//			}
			// 取得行情数据
			// 每次做大取得两个
			if( 股票代码[0] == null )break;
			byte[] 行情数据 = hqApiJava1.getGetSecurityQuotes(市场数据, 股票代码, Result, ErrInfo);
			String s = Native.toString(行情数据, "GBK");
			String s1[] = s.split("\n");
			取得行情.add(s1[1]);
			取得行情.add(s1[2]);
			//取得行情数据 = OutputDataUtil爸爸.数组合并(取得行情数据, removeBlankFromByte(行情数据, 0, 行情数据.length));
		}
		return 取得行情;
	}
	
	/**
	 * 
	 * @param i市场代码
	 * @param 取得行情数
	 * @param i开始位置
	 * @param 股票代码Result
	 * @return
	 */
	private static String[] 做成这500的股票代码(int i市场代码, int 取得行情数, int i开始位置, List<String[]> 股票代码Result) {
		// 这里记述的是怎么解析
		// 基本的思路就是
		// 先将【股票代码Result】分解成结构体。
		// 然后从结构体中取得【股票代码】

		String[] 股票代码 = new String[取得行情数];

		for(int i=i开始位置, j = 0 ; j < 取得行情数; i++) {
			if(i >= 股票代码Result.size()) {
				//股票代码[j] = getStrFromStrArray(股票代码, 0, j-1);
				股票代码[1] = 股票代码[0];
				break;
			}
			if(股票代码Result.get(i)[0].length() != 6) continue;
			股票代码[j] = 股票代码Result.get(i)[0];
			j++;
		}

		return 股票代码;

	}
	
	private static String[] getStrFromStrArray(String[] 股票代码, int i开, int i闭) {
		// 第一次 i闭=0， i开=10
		if((i闭 - i开) == 0) {
			return new String[] {股票代码[0]};
		}
		String b[] = new String[i闭 - i开];

		for(int i=0,j=i开; i< i闭 - i开 ; i++,j++) {
			b[i] = 股票代码[j];
		}
		return b;
	}

	/**
	 * 
	 */
	private static List<String[]> 取得Byte数组中的数据(byte[] 股票代码Result, Byte 项目分割, Byte 一套分割, int 一套个数) {
		long i开 = 0;
		long i闭 = 0;
		int i一套数据中的计数器 = 0;
		long iSize = 股票代码Result.length;
		List<String[]> sList = new ArrayList<String[]>();
		String[] 一套数据 = new String[一套个数];
		
		// 循环取得【股票代码Result】中每一个byte
		for(byte b: 股票代码Result) {
			
			
			if(b != 项目分割  && b != 一套分割) {
				//---------------------------------
				// 如果还没有碰到（项目分割/一套分割）
				// 就继续计数
				//---------------------------------
				
			}else {
				//---------------------------------
				// 如果碰到了其中一个（项目分割/一套分割）
				// 那么这个数据就形成了。
				//---------------------------------
				if(i一套数据中的计数器 == 一套个数) {
					sList.add(一套数据);
					一套数据 = new String[一套个数];
					i一套数据中的计数器 = 0;
				}
				一套数据[i一套数据中的计数器] = getGB2312StrFromByte(股票代码Result,i开,i闭);
				if(i一套数据中的计数器 ==42 && sList.size()==1) {
					i一套数据中的计数器 =42;
				}
				if(i一套数据中的计数器 ==41 && sList.size()==2) {
					i一套数据中的计数器 =41;
				}
			}
			
			if(b == 项目分割){
				// 需要移动i开的位置
				i开 = i闭 + 1;
				i一套数据中的计数器++;
			}
			
			// 而且当你碰到的是【一套分割】时
			if(b == 一套分割) {
				//---------------------------------
				// 需要移动i开的位置
				// 并且将取到的数据计入到sList中
				// 将【i一套数据中的计数器】归零
				//---------------------------------
				i开 = i闭 + 1;
				sList.add(一套数据);
				一套数据 = new String[一套个数];
				i一套数据中的计数器 = 0;
			}
			i闭++;
		}
		// 说明最后一位不是【一套分割】的标志位就结束了。
		if(i一套数据中的计数器 != 0) {
			一套数据[i一套数据中的计数器] = getGB2312StrFromByte(股票代码Result,i开,i闭);
			sList.add(一套数据);
			i一套数据中的计数器 = 0;
		}
		
		return sList;
	}

	private static String getGB2312StrFromByte(byte[] 股票代码Result, long i开, long i闭) {
		// 第一次 i闭=0， i开=10
		
		byte b[] = new byte[(int) (i闭 - i开)];

		for(long i=0,j=i开; i< i闭 - i开 ; i++,j++) {
			
			// 如果这个数值是零，且以后的，都为零时。
			// 舍掉后面的数据。
			if(股票代码Result[(int) j] == 0) {
				if(isBlankFromByte(股票代码Result, (int) j, 股票代码Result.length)) {
					
					b[(int) i] = 股票代码Result[(int) j];
					b = cutByteFromByte(b, 0, (int) (j-i开));
					break;
				}
			}
			b[(int) i] = 股票代码Result[(int) j];			
		}
		try {
			return new String(b, "gb2312");
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}
		return null;
	}
	private static byte[] removeBlankFromByte(byte[] 股票代码Result, long i开, long i闭) {
		byte b[] = new byte[(int) (i闭 - i开)];
		for(long i=0,j=i开; i< i闭 - i开 ; i++,j++) {
			
			// 如果这个数值是零，且以后的，都为零时。
			// 舍掉后面的数据。
			if(股票代码Result[(int) j] == 0) {
				if(isBlankFromByte(股票代码Result, (int) j, 股票代码Result.length)) {
					
					b[(int) i] = 股票代码Result[(int) j]; // 
					b = cutByteFromByte(b, 0, (int) (j-i开) + 1);
					b[(int) j] = (byte)10;
					break;
				}
			}
			b[(int) i] = 股票代码Result[(int) j];			
		}
		return b;
	}
	private static boolean isBlankFromByte(byte[] 股票代码Result, int i开, int i闭) {

		int iResult=0;
		for(int i=0,j=i开; i< i闭 - i开 ; i++,j++) {
			iResult += 股票代码Result[j];
		}
		return iResult == 0;

	}

	private static byte[] cutByteFromByte(byte[] 股票代码Result, int i开, int i闭) {
		// 第一次 i闭=0， i开=10
		if((i闭 - i开) == 0) {
			return new byte[] {股票代码Result[0]};
		}
		byte b[] = new byte[i闭 - i开];

		for(int i=0,j=i开; i< i闭 - i开 ; i++,j++) {
			b[i] = 股票代码Result[j];
		}
		return b;
	}

	private static byte[] 做成这500的市场数据(int i市场代码, int 取得行情数) {
		byte[] 市场数据 = new byte[取得行情数];
		for (int i = 0; i < 取得行情数; i++) {
			市场数据[i] = (byte) i市场代码;
		}
		return 市场数据;
	}

	private static String 取得最后交易日期(byte[] result) {
		
		// 取得最后的18个byte！
		List<String[]> sList =取得Byte数组中的数据(result,项目分割,一套分割,3 );
		return sList.get(1)[1];
	}
	
	
}
