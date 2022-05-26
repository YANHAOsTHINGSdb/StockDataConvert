package InputData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.sun.jna.Native;
import com.sun.jna.ptr.ShortByReference;

import ConvertTool.impl.PROPERTY;
import ConvertTool.impl.StockData爸爸;
import OutputData.OutputDataUtil爸爸;

public class InputDataUtil爸爸 {

	final static byte 深圳 = (byte) 0;
	final static byte 上海 = (byte) 1;

	public String 取得最后交易日(){
		byte[] Result = new byte[65535];
		byte[] ErrInfo = new byte[256];
		if (TdxHqApi209912301.getConnect(PROPERTY.取得IP(), PROPERTY.取得Port(), Result, ErrInfo)) {
			String s = 取得最后交易日期(Result);
			return s;
		}
		return null;
	}

	public static List<String> 取得股票代码List(String s股票代码){

		ShortByReference Count = new ShortByReference();
		byte[] ErrInfo = new byte[256];
		byte[] 股票代码Result = new byte[1024 * 1024];
		List<String[]> 股票代码ArrayList = new ArrayList();
		List<String> 当日行情数据list = new ArrayList();
		List<String> 股票代码list = new ArrayList();
		byte byte股票代码 = 0;
		if(s股票代码.equals("sz")) {
			byte股票代码 =  深圳;
		}else if(s股票代码.equals("sh")){
			byte股票代码 =  上海;
		}
		if (TdxHqApi209912301.getGetSecurityList(byte股票代码, (short) 0, Count, 股票代码Result, ErrInfo, 股票代码ArrayList)) {

			//List<String[]> 深圳股票代码ArrayList = getArrayListByGBK(深圳股票代码Result);
			// 根据股票的结果数，取得行情数据
			股票代码ArrayList = removeDuplicate(股票代码ArrayList);
		}
		for(String[] 股票代码Array : 股票代码ArrayList) {
			//for(String s : 股票代码Array) {
				股票代码list.add(股票代码Array[0]);
			//}
		}
		return 股票代码list;
	}


	// 取得最后交易日的数据
	public static boolean 取得行情数据和最后交易日(List<String> 当日行情数据list, String[] s最后交易日期, Map<String, List<String>> 股票代码ArrayListMap) {

		byte[] Result = new byte[65535];
		byte[] ErrInfo = new byte[256];
		byte[] Market = { 0, 1 };
		byte[] Zqdm = null;
		byte[] 取得行情数据 = null;
		int 每次取得行情数据 = 20;
		//List<String> 取得行情 = new ArrayList();
		// 连接服务器
		if (TdxHqApi209912301.getConnect(PROPERTY.取得IP(), PROPERTY.取得Port(), Result, ErrInfo)) {

			//【交易日】
			// 招商证券北京行情	20190430	20960430
			// 9, 50, 48, 49, 57, 48, 52, 51, 48,
			// 9, 50, 48, 57, 54, 48, 52, 51, 48,
			String s = 取得最后交易日期(Result);
			s最后交易日期[0] = s;
			ShortByReference Count = new ShortByReference();
			//------------------------------------------------------------
			//【实时数据】=最后交易日期
			// 要取得实时数据（因为取不到K线数据，所以只能拿到实时数据）
			//------------------------------------------------------------
			// 0=深圳
			byte[] 深圳股票代码Result = new byte[1024 * 1024];
			List<String[]> 深圳股票代码ArrayList = new ArrayList();
			List<String> 深圳股票代码List = new ArrayList();
			if (TdxHqApi209912301.getGetSecurityList(深圳, (short) 0, Count, 深圳股票代码Result, ErrInfo, 深圳股票代码ArrayList)) {

				//List<String[]> 深圳股票代码ArrayList = getArrayListByGBK(深圳股票代码Result);
				// 根据股票的结果数，取得行情数据
				深圳股票代码ArrayList = removeDuplicate(深圳股票代码ArrayList);
				List<String> 行情数据 = 取得行情数据(深圳, 深圳股票代码ArrayList, 每次取得行情数据);
				当日行情数据list.addAll(行情数据);
				深圳股票代码List = 取得股票代码ArrayList(行情数据);
			}

			byte[] 上海股票代码Result = new byte[1024 * 1024];
			List<String[]> 上海股票代码ArrayList = new ArrayList();
			List<String> 上海股票代码List = new ArrayList();
			// 1=上海
			if (TdxHqApi209912301.getGetSecurityList(上海, (short) 0, Count, 上海股票代码Result, ErrInfo, 上海股票代码ArrayList)) {
				//List<String[]> 上海股票代码ArrayList = getArrayListByGBK(上海股票代码Result);
				// 根据股票的结果数，取得行情数据
				上海股票代码ArrayList = removeDuplicate(上海股票代码ArrayList);
				List<String> 行情数据 = 取得行情数据(上海, 上海股票代码ArrayList, 每次取得行情数据);
				当日行情数据list.addAll(行情数据);
				上海股票代码List = 取得股票代码ArrayList(行情数据);
			}
			股票代码ArrayListMap.put("sz",深圳股票代码List);
			股票代码ArrayListMap.put("sh",上海股票代码List);
		}
		// todayData = 取得Byte数组中的数据(取得行情数据,项目分割,一套分割, 44);
		return true;
	}

	private static List<String> 取得股票代码ArrayList(List<String> 行情数据) {
		List<String> 股票代码List = new ArrayList();
		for(String s : 行情数据) {
			String[] s股票代码 = s.split("\t");
			股票代码List.add(s股票代码[1]);
		}
		return 股票代码List;
	}

	/**
	 *
	 * @param 取得结果Result
	 * @return
	 */
	private static List<String[]> getArrayListByGBK(byte[] 取得结果Result) {
		List<String[]> ResultListByGBK = new ArrayList();
		//------------------------
		// 取出行情数据
		//------------------------
		String s = Native.toString(取得结果Result, "GBK");
		String s1[] = s.split("\n");
		for (int o = 1; o < s1.length; o++) {
			ResultListByGBK.add(s1[o].split("\t"));
		}
		return ResultListByGBK;
	}

	/**
	 *
	 * @param 取得结果Result
	 * @return
	 */
	static List<String> getStringListByGBK(byte[] 取得结果Result) {
		List<String> ResultListByGBK = new ArrayList();

		if (取得结果Result == null || 取得结果Result.length == 0) {
			return null;
		}
		//------------------------
		// 取出行情数据
		//------------------------
		String s = Native.toString(取得结果Result, "GBK");
		String s1[] = s.split("\n");
		for (int o = 1; o < s1.length; o++) {
			ResultListByGBK.add(s1[o]);
		}
		return ResultListByGBK;
	}

	public static List<String[]> getStringListByGBK3(byte[] 取得结果Result) {
		List<String[]> ResultListByGBK = new ArrayList();

		if (取得结果Result == null || 取得结果Result.length == 0) {
			return null;
		}
		//------------------------
		// 取出行情数据
		//------------------------
		String s = Native.toString(取得结果Result, "GBK");
		String s1[] = s.split("\n");
		for (int o = 1; o < s1.length; o++) {
			ResultListByGBK.add(s1[o].split("\t"));
		}
		return ResultListByGBK;
	}

	static Byte 项目分割 = (byte) 9;
	static Byte 一套分割 = (byte) 10;

	/**
	 *
	 * @param i市场代码
	 * @param 股票代码ArrayList
	 * @param 取得行情数
	 * @return
	 */
	private static List<String> 取得行情数据(int i市场代码, List<String[]> 股票代码ArrayList, int 每次取得行情数据) {

		byte[] Result = new byte[65535];
		byte[] ErrInfo = new byte[256];
		List<String> 取得行情 = new ArrayList<String>();
		ShortByReference Count = new ShortByReference();
		int 股票代码位置 = 0;
		int 股票名称位置 = 2;
		int 一套个数 = 8;
		// List<String[]> Byte数组中的数据 = 取得Byte数组中的数据(股票代码ArrayList, 项目分割 , 一套分割 , 一套个数);

		// 每500股取一次行情
		for (int i开始位置 = 0; i开始位置 < 股票代码ArrayList.size(); i开始位置 = i开始位置 + 每次取得行情数据) {

			int[] 每次取得行情数据array = new int[] { 每次取得行情数据 };

			// 做成这500的股票代码
			String[] 股票代码array = 做成这500的股票信息(股票代码位置, i市场代码, 每次取得行情数据array, i开始位置, 股票代码ArrayList);
			String[] 股票名称array = 做成这500的股票信息(股票名称位置, i市场代码, 每次取得行情数据array, i开始位置, 股票代码ArrayList);
			// 做成这500的市场数据
			byte[] 市场代码array = 做成这500的市场数据(i市场代码, 每次取得行情数据array);
			//------------------------
			// 取得行情数据
			//------------------------
			// 每次做大取得两个
			if (股票代码array[0] == null)
				break;
			Count.setValue((short) 每次取得行情数据array[0]);
			// 获取盘口五档报价
			byte[] 行情数据array = TdxHqApi209912301.getGetSecurityQuotes(市场代码array, 股票代码array, Count, Result, ErrInfo);

			//------------------------
			// 取出行情数据
			//------------------------
			//			String s = Native.toString(行情数据, "GBK");
			//			String s1[] = s.split("\n");
			//			for(int o=1; o<=每次取得行情数据array[0]; o++ ) {
			//				取得行情.add(s1[o]);
			//			}
			取得行情.addAll(getStringListByGBK2(行情数据array, 股票名称array));

		}
		return 取得行情;
	}

	/**
	 * 将【行情数据】转成【GBK】、并以【行情数据】+【"\t"】+【股票名称】的形式加入到【ResultListByGBK】中、并返回
	 * @param 行情数据
	 * @param 股票名称
	 * @return
	 */
	private static Collection<? extends String> getStringListByGBK2(byte[] 行情数据, String[] 股票名称) {
		List<String> ResultListByGBK = new ArrayList();

		if (行情数据 == null || 行情数据.length == 0) {
			return null;
		}
		//------------------------
		// 取出行情数据
		//------------------------
		String s = Native.toString(行情数据, "GBK");
		String s1[] = s.split("\n");
		for (int o = 1; o < s1.length; o++) {
			ResultListByGBK.add(s1[o].concat("\t").concat(股票名称[o - 1]));
		}
		return ResultListByGBK;
	}

	/**
	 *
	 * @param i市场代码
	 * @param 取得行情数
	 * @param i开始位置
	 * @param 股票代码Result
	 * @return
	 */
	private static String[] 做成这500的股票信息(int 取得位置, int i市场代码, int[] 取得行情数, int i开始位置, List<String[]> 股票代码Result) {
		// 这里记述的是怎么解析
		// 基本的思路就是
		// 先将【股票代码Result】分解成结构体。
		// 然后从结构体中取得【股票代码】
		int n = 取得行情数[0];
		String[] 股票代码 = new String[n];

		for (int i = i开始位置, j = 0; j < n; i++) {
			// 如果超界了，就只好有多少取多少了。
			if (i >= 股票代码Result.size()) {
				if (j == 0) {
					break;
				}
				股票代码 = getStrFromStrArray(股票代码, 0, j - 1);
				取得行情数[0] = j;
				break;
			}
			if (股票代码Result.get(i)[0].length() != 6)
				continue;
			if (i市场代码 == 1) {
				if ((股票代码Result.get(i)[0].charAt(0) == '6' || 股票代码Result.get(i)[0].charAt(0) == '0'
						|| 股票代码Result.get(i)[0] == "1A0001")) {

				} else {
					continue;
				}
			}
			if (i市场代码 == 0) {
				if ((股票代码Result.get(i)[0].charAt(0) == '1')) {
					continue;
				} else {

				}
			}
			股票代码[j] = 股票代码Result.get(i)[取得位置];
			j++;
		}

		return 股票代码;

	}

	private static String[] getStrFromStrArray(String[] 股票代码, int i开, int i闭) {
		// 第一次 i闭=0， i开=10
		if ((i闭 - i开) == 0) {
			return new String[] { 股票代码[0] };
		}
		String b[] = new String[i闭 - i开];

		for (int i = 0, j = i开; i < i闭 - i开; i++, j++) {
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
		for (byte b : 股票代码Result) {

			if (b != 项目分割 && b != 一套分割) {
				//---------------------------------
				// 如果还没有碰到（项目分割/一套分割）
				// 就继续计数
				//---------------------------------

			} else {
				//---------------------------------
				// 如果碰到了其中一个（项目分割/一套分割）
				// 那么这个数据就形成了。
				//---------------------------------
				if (i一套数据中的计数器 == 一套个数) {
					sList.add(一套数据);
					一套数据 = new String[一套个数];
					i一套数据中的计数器 = 0;
				}
				一套数据[i一套数据中的计数器] = getGB2312StrFromByte(股票代码Result, i开, i闭);
				if (i一套数据中的计数器 == 42 && sList.size() == 1) {
					i一套数据中的计数器 = 42;
				}
				if (i一套数据中的计数器 == 41 && sList.size() == 2) {
					i一套数据中的计数器 = 41;
				}
			}

			if (b == 项目分割) {
				// 需要移动i开的位置
				i开 = i闭 + 1;
				i一套数据中的计数器++;
			}

			// 而且当你碰到的是【一套分割】时
			if (b == 一套分割) {
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
		if (i一套数据中的计数器 != 0) {
			一套数据[i一套数据中的计数器] = getGB2312StrFromByte(股票代码Result, i开, i闭);
			sList.add(一套数据);
			i一套数据中的计数器 = 0;
		}

		return sList;
	}

	private static String getGB2312StrFromByte(byte[] 股票代码Result, long i开, long i闭) {
		// 第一次 i闭=0， i开=10

		byte b[] = new byte[(int) (i闭 - i开)];

		for (long i = 0, j = i开; i < i闭 - i开; i++, j++) {

			// 如果这个数值是零，且以后的，都为零时。
			// 舍掉后面的数据。
			if (股票代码Result[(int) j] == 0) {
				if (isBlankFromByte(股票代码Result, (int) j, 股票代码Result.length)) {

					b[(int) i] = 股票代码Result[(int) j];
					b = cutByteFromByte(b, 0, (int) (j - i开));
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

	/**
	 *
	 * @param 股票代码Result
	 * @param i开
	 * @param i闭
	 * @return
	 */
	private static byte[] removeBlankFromByte(byte[] 股票代码Result, long i开, long i闭) {
		byte b[] = new byte[(int) (i闭 - i开)];
		for (long i = 0, j = i开; i < i闭 - i开; i++, j++) {

			// 如果这个数值是零，且以后的，都为零时。
			// 舍掉后面的数据。
			if (股票代码Result[(int) j] == 0) {
				if (isBlankFromByte(股票代码Result, (int) j, 股票代码Result.length)) {

					b[(int) i] = 股票代码Result[(int) j]; //
					b = cutByteFromByte(b, 0, (int) (j - i开) + 1);
					b[(int) j] = (byte) 10;
					break;
				}
			}
			b[(int) i] = 股票代码Result[(int) j];
		}
		return b;
	}

	private static boolean isBlankFromByte(byte[] 股票代码Result, int i开, int i闭) {

		int iResult = 0;
		for (int i = 0, j = i开; i < i闭 - i开; i++, j++) {
			iResult += 股票代码Result[j];
		}
		return iResult == 0;

	}

	public static byte[] cutByteFromByte(byte[] 股票代码Result, int i开, int i闭) {
		if ((i闭 - i开) >= 股票代码Result.length) {
			return 股票代码Result;
		}
		// 第一次 i闭=0， i开=10
		if ((i闭 - i开) == 0) {
			return new byte[] { 股票代码Result[0] };
		}
		byte b[] = new byte[i闭 - i开];

		for (int i = 0, j = i开; i < i闭 - i开; i++, j++) {
			if (i >= i闭 - i开) {
				break;
			}

			b[i] = 股票代码Result[j];
		}
		return b;
	}

	/**
	 * 根据每次取得行情数据array
	 * 自动生成【市场数据】的数据
	 * @param i市场代码
	 * @param 每次取得行情数据array
	 * @return
	 */
	private static byte[] 做成这500的市场数据(int i市场代码, int[] 每次取得行情数据array) {

		int n = 每次取得行情数据array[0];
		byte[] 市场数据 = new byte[n];
		for (int i = 0; i < n; i++) {
			市场数据[i] = (byte) i市场代码;
		}
		return 市场数据;
	}

	/**
	 *
	 * @param result
	 * @return
	 */
	protected static String 取得最后交易日期(byte[] result) {

		// 每一行用换行符(\r)分割
		// 每个数据用的是tab(\t)分割

		// 先将，二进制result转成字符串        // 例、String s = Native.toString(Result, "GBK");
		// 再用换行符将上述字符串按行分割                	// 例、String[] 所有行 = s.split("\r");
		// 最后把第二行的第二个字符串提取、并返回        	// 例、String 第二行 = 所有行[1];
		// 例、String[] 第二行所有字符串 = 第二行.split("\t");
		//     String 第二行第二个字符= 第二行所有字符串[1];
		//     return 第二行第二个字符
		/*
		 服务器名称 最后交易日期 接口过期日期
		 招商证券北京行情 20190909 20991230
		 */
		String s = Native.toString(result, "GBK");
		/*
		 * 服务器名称\t最后交易日期\t接口过期日期\r招商证券北京行情\t20190909\t20991230
		 */
		String[] 所有行 = s.split("\n"); // 换行符=\r
		String 第二行 = 所有行[1]; // 0,1
		String[] 第二行所有字符串 = 第二行.split("\t");
		String 第二行第二个字符 = 第二行所有字符串[1];
		return 第二行第二个字符;

		//  // 取得最后的18个byte！
		//  List<String[]> sList =取得Byte数组中的数据(result,项目分割,一套分割,3 );
		//  return sList.get(1)[1];
	}

	public static List removeDuplicate(List list) {
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}

	/**
	 * 将字符串中的"\n"换成"\b"
	 * @param l
	 * @return
	 * 问题：Java /n 无法替换
	 * 方法一（无效
	 *   在去除字符串中的换行符(\n)的时候，写成str.replace("\\n", "")才能正确执行。
	 *   str.replace("\n","") ，str.replaceAll("\\n","")，str.replaceAll("\n","")均替换失败。
	 * 方法二（无效
	 *   java str replace /n
	 * 方法三（有效
	 *   自己写函数解决
	 *   \n是换行，ASCLL码是10
	 */
	public static String 替换指定Str换行符(String l, char fromChar, char toChar) {
		char[] s = l.toCharArray();
		int iIndex = 0;
		for(char a : s) {
			if(a == fromChar) {
				System.out.println(a);
				s[iIndex] = toChar;
			}
			iIndex++;
		}
		return new String(s);
	}

	/**
	 * 将list按行写入到txt文件中
	 * @param strings
	 * @param path
	 * @throws Exception
	 */
	public static void writeFileContext(List<String> strings, String path) throws Exception {
		File file = new File(path);
		//如果没有文件就创建
		if (!file.isFile()) {
			file.createNewFile();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
		for (String l : strings) {
			writer.write(l + "\r\n");
		}
		writer.close();
	}

	public static void writeFileContext2(List<String> strings, String path) throws Exception {
		File file = new File(path);
		//如果没有文件就创建
		if (!file.isFile()) {
			file.createNewFile();
		}
		//BufferedWriter writer = new BufferedWriter(new FileWriter(path));

		byte[] all = {};
		for (String l : strings) {
			all = OutputDataUtil爸爸.数组合并2(all, l.getBytes("GKB"));
		}

		new StockData爸爸().write(path,  all);

		//writer.close();
	}

	public static void writeFileContext3(List<String> 财务数据list, String path) throws IOException {
		File file = new File(path);
		// 如果没有文件就创建
		if (!file.exists()) {
			//file.mkdirs();
			try {
				file.createNewFile();
			} catch (Exception e) {
				System.out.println("第一次没有成功建成文件：" + path);
				e.printStackTrace();
				String parentPath = file.getParent();
				System.out.println("先建文件夹，后建文件：" + parentPath);
				File file1 = new File(parentPath);
				if (!file1.exists()) {
					file1.mkdirs();
					file.createNewFile();
				}
			}
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
		for (String l : 财务数据list) {
			//.replace('\n', '\b');
			替换指定Str换行符(l, '\n', '\b');
			writer.write(l + "\r\n");
		}
		writer.close();
	}
	/**
	 * 获取txt文件内容并按行放入list中
	 */
	public static List<String> getFileContext2(String path) {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(path);
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        BufferedReader bufferedReader =new BufferedReader(fileReader);
        List<String> list =new ArrayList<String>();
        String str=null;
        try {
			while((str=bufferedReader.readLine())!=null) {
				if(str.trim().length()>2) {
					//str.replace('\b', '\n');
					str = 替换指定Str换行符(str, '\b', '\n');
					list.add(str);
				}
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return list;
    }
}
