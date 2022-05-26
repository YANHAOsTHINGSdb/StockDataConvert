package InputData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sun.jna.Native;
import com.sun.jna.ptr.ShortByReference;

import ConvertTool.impl.PROPERTY;

public class InputDataUtil财务 extends InputDataUtil爸爸{

	/**
	  * 取得财务数据
	  * @param i市场代码
	  * @param 股票代码ArrayList
	  * @return List<String> 取得的财务信息List
	  */
	public static List<String> 取得财务数据(int i市场代码, List<String[]> 股票代码ArrayList) {

		byte[] Result = new byte[65535];
		byte[] ErrInfo = new byte[256];
		List<String> 取得的财务信息List = new ArrayList<String>();

		//for (int i开始位置 = 0; i开始位置 < 50; i开始位置++) {
		for (int i开始位置 = 0; i开始位置 < 股票代码ArrayList.size(); i开始位置++) {
		//for (int i开始位置 = 0; i开始位置 < 1000; i开始位置++) {
			boolean bResult = false;

			String[] 股票代码 = 股票代码ArrayList.get(i开始位置);

			bResult = TdxHqApi209912301.TdxHqApi20991230.TdxHq_GetFinanceInfo((byte) i市场代码, 股票代码[0], Result, ErrInfo);

			String s取得的财务信息 = Native.toString(Result, "GBK");
			if (!StringUtils.isEmpty(s取得的财务信息)) {
				System.out.println(s取得的财务信息);
			}

			取得的财务信息List.add(s取得的财务信息);

			//return 取得的财务信息List;
		}
		return 取得的财务信息List;
	}


	/**
	 * 取得最后交易日的数据
	 * @param 当日行情数据list
	 * @param s最后交易日期
	 * @return
	 */
	public static boolean 取得财务数据和最后交易日(List<String> 财务数据list, String[] s最后交易日期) {

		byte[] Result = new byte[65535];
		byte[] ErrInfo = new byte[256];
//		byte[] Market = { 0, 1 };
//		byte[] Zqdm = null;
//		byte[] 取得行情数据 = null;
//		int 每次取得行情数据 = 20;
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
			List<String[]> 股票代码ArrayList = new ArrayList<String[]>();
			if (TdxHqApi209912301.getGetSecurityList(深圳, (short) 0, Count, 深圳股票代码Result, ErrInfo, 股票代码ArrayList)) {

				//List<String[]> 深圳股票代码ArrayList = getArrayListByGBK(深圳股票代码Result);
				// 根据股票的结果数，取得行情数据
				股票代码ArrayList = removeDuplicate(股票代码ArrayList);
				List<String> 行情数据 = 取得财务数据(深圳, 股票代码ArrayList);
				财务数据list.addAll(行情数据);

			}

			byte[] 上海股票代码Result = new byte[1024 * 1024];
			股票代码ArrayList = new ArrayList<String[]>();
			// 1=上海
			if (TdxHqApi209912301.getGetSecurityList(上海, (short) 0, Count, 上海股票代码Result, ErrInfo, 股票代码ArrayList)) {
				//List<String[]> 上海股票代码ArrayList = getArrayListByGBK(上海股票代码Result);
				// 根据股票的结果数，取得行情数据
				股票代码ArrayList = removeDuplicate(股票代码ArrayList);
				List<String> 行情数据 = 取得财务数据(上海, 股票代码ArrayList);
				财务数据list.addAll(行情数据);

			}

		}
		try {
			writeFileContext3(财务数据list, ".\\财务数据.data");
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		// todayData = 取得Byte数组中的数据(取得行情数据,项目分割,一套分割, 44);
		return true;
	}





	/**
	* 获取txt文件内容, 并按行放入list中
	*/
	public static List<String> getFileContext(String path) {
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		List<String> list = new ArrayList<String>();
		String str = "";
		String newLine = "";
		boolean flg = false;
		try {
			fileReader = new FileReader(path);
			bufferedReader = new BufferedReader(fileReader);
			while ((str = bufferedReader.readLine()) != null) {
				// 市场	证券代码	日期	权息数据类别	前流通盘或派息金额	前总股本或配股价	后流通盘或送股数	后总股本或配售数
				if(str.equals("市场	证券代码	日期	财务数据类别	前流通盘或派息金额	前总股本或配股价	后流通盘或送股数	后总股本或配售数")) {
					if(flg == false) {

					}else {
						list.add(newLine);

					}
					flg = true;
					newLine = str;
				}else {
					newLine += "\n";
					newLine += str;
				}
//				if (str.trim().length() > 2) {
//					list.add(str);
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			try {
				if (fileReader != null) {
					fileReader.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}




}
