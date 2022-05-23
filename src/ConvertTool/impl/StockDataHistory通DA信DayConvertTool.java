package ConvertTool.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import OutputData.OutputDataUtil;
import OutputData.OutputDataUtil爸爸;
import OutputData.feihu.DayDataOutputBean;
import OutputData.feihu.OutputDataUtil飞狐;
import OutputData.qianlong.OutputDataUtil钱龙;

public class StockDataHistory通DA信DayConvertTool extends StockData爸爸 implements ConvertTool.ConvertTool {
	public static void main(String[] args) throws IOException {


		/*  sz300001.day
		    ------------------------------------------------
			5f8a 3401 7d01 0000 8701 0000 7b01 0000
			7b01 0000 3fc2 0d4d 83ea 4b02 0000 0100
			608a 3401 7701 0000 8301 0000 7601 0000
			8001 0000 29c0 c44c f1c3 9b01 0000 0100
			618a 3401 8101 0000 8401 0000 7c01 0000
			7f01 0000 a4bf 8b4c 1aab 2301 0000 0100
			648a 3401 8101 0000 8301 0000 7b01 0000
			7d01 0000 9440 9f4c aa69 4d01 0000 0100
			658a 3401 7e01 0000 7f01 0000 7601 0000
			7d01 0000 a0e8 9b4c ed33 4901 0000 0100
			668a 3401 8201 0000 8b01 0000 8101 0000
			8201 0000 0e14 ff4c 6878 0b02 0000 0100
			678a 3401 7c01 0000 8401 0000 7a01 0000
			8301 0000 8518 9d4c 5f77 4801 0000 0100
			688a 3401 8501 0000 8a01 0000 8301 0000
			8701 0000 ec14 a14c be4a 4a01 0000 0000


			5f8a 3401 日期  十六转十
			7d01 0000  开盘         十六转十
			8701 0000 最高  十六转十
			7b01 0000 最低  十六转十
			7b01 0000  收盘         十六转十
			3fc2 0d4d 成交额  十进制转单精度浮点数
			83ea 4b02 成交量  十六转十
			0000 0100

		 */
		new StockDataHistory通DA信DayConvertTool()
				.输出到文件("C:\\Users\\yanhao\\Documents\\GitHub\\StockDataConvert\\testdata");
	}

	@Override
	public void 输出到文件(String inputFileFolderName) throws IOException {
/*
		Step1 逐条取得个股文件      (旧)
		Step2 逐日解析二进制历史信息     (新)
		Step3 返回该股历史数据          (旧)
		Step4 将入力文件的内容转成Byte  (旧)
		Step5 整合输出                 (旧)
		Step6 结果文件出力                                                (旧)
*/


		byte[] header = null;
		byte[] type = null;
		byte[] 股票数 = null;
		int i股票数 = 0;

		if (StringUtils.isEmpty(inputFileFolderName)) {
			System.out.println("err=没有指定入力文件夹。");
			return;
		}

		List<File> fileList = (List<File>) FileUtils.listFiles(new File(inputFileFolderName), null, false);//列出该目录下的所有文件，不递归
		byte[] outputTofile = new byte[0];
		byte[] resultByte深沪股票 = new byte[0];


		/**************************************************************************
		 * Step1 逐条取得个股文件      (旧)
		 **************************************************************************/
		for (File file : fileList) {

			// 如果文件不存在则创建文件
			if (!file.exists()) {
				file.createNewFile();
			}

			InputStream inputStream;
			inputStream = new FileInputStream(file);

			String filename = file.getName();
			filename = filename.substring(0, filename.lastIndexOf("."));
			String s市场 = filename.substring(0, 2);
			String s股票代码 = filename.substring(2, filename.length());

			/**************************************************************************
			 * Step2 逐日解析二进制历史信息     (新)
			 **************************************************************************/
			List<String> 个股历史数据list = 返回各股历史数据(inputStream, s市场, s股票代码, "股票名称");

			/**************************************************************************
			 * Step4 将入力文件的内容转成Byte  (旧)
			 **************************************************************************/
			DayDataOutputBean dayDataOutputBean飞狐 = new DayDataOutputBean();
			byte[] resultByteO一只股票 = 将入力文件的内容转成Byte (个股历史数据list, dayDataOutputBean飞狐);



			// 【dayDataOutputBean飞狐】是从子函数里返回的值
			if (header == null)
				header = dayDataOutputBean飞狐.getHeader();
			if (type == null)
				type = dayDataOutputBean飞狐.getType();

			/**************************************************************************
			 * Step5 整合输出                 (旧)
			 **************************************************************************/

			// 股票个数加1
			if (resultByteO一只股票 != null && resultByteO一只股票.length > 1) {
				// 整合出力数据
				resultByte深沪股票 = OutputDataUtil爸爸.数组合并(resultByte深沪股票, resultByteO一只股票);

				i股票数++;
			}

		}

		if (i股票数 == 0)
			return; // 如果股票数为零，就退出

		if (股票数 == null)
			股票数 = OutputDataUtil爸爸.convertInttoBytePublic(i股票数);

		/**************************************************************************
		 * Step6 结果文件出力                                                (旧)
		 **************************************************************************/
		// 最终整合
		outputTofile = OutputDataUtil爸爸.数组合并(header, type, 股票数, resultByte深沪股票);

		String outFileName = PROPERTY.取得飞狐用导入数据文件名();
		outFileName = StringUtils.isEmpty(outFileName) ? "historyDataForFeihuSoftWare" : outFileName;
		write(PROPERTY.取得sh出力目录().concat("\\").concat(outFileName)
				.concat(".".concat(s数据格式扩展名[Integer.parseInt(sOutPutDataType)])), outputTofile);

	}

	/**
	 *
	 * @param file
	 * @return
	 */
	private static byte[] 将入力文件的内容转成Byte(List<String> list, DayDataOutputBean dayDataOutputBean飞狐) {
		/*=======================================
		|-----header			️
		|-----type
		|-----股票数
				|------股票代码		⬅
				|------股票中文名
				|------日线个数
						|------开
						|------高
						|------低
						|------收
		=======================================*/
		byte[] resultByte一个文件 = new byte[0];
		byte[] 股票代码 = null;
		byte[] 股票中文名 = null;
		byte[] 日线个数 = null;
		int i日线个数 = 0;

		for (String str : list) {
			System.out.println(str);
			// 解析每一行返回出力内容
			byte[] resultByteO一行 = 解析每一行的数据2(str, dayDataOutputBean飞狐);

			// 【dayDataOutputBean飞狐】是从子函数里返回的值
			if (dayDataOutputBean飞狐.getStockName() != null)
				股票代码 = dayDataOutputBean飞狐.getStockName();
			if (dayDataOutputBean飞狐.getStockChName() != null)
				股票中文名 = dayDataOutputBean飞狐.getStockChName();

			if (resultByteO一行 != null) {
				// 日线数据合并
				resultByte一个文件 = OutputDataUtil爸爸.数组合并(resultByteO一行, resultByte一个文件);
				i日线个数++;
			}
		}
		// 取得【日线个数】的byte值
		if (日线个数 == null)
			日线个数 = OutputDataUtil爸爸.convertInttoBytePublic(i日线个数);

		// 最终整合
		if (resultByte一个文件 != null && resultByte一个文件.length > 1) {
			resultByte一个文件 = OutputDataUtil爸爸.数组合并(股票代码, 股票中文名, 日线个数, resultByte一个文件);
		} else {
			resultByte一个文件 = null;
		}

		return resultByte一个文件;

	}

	/**
	 * 返回各股历史数据
	 * @param input
	 * @return
	 * @throws IOException
	 */
	private static List<String> 返回各股历史数据(InputStream input, String s市场, String s股票代码, String s股票名称)
			throws IOException {
		/**************************************************************************
		 * Step3 返回该股历史数据          (旧)
		 **************************************************************************/
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int numRead;
		byte[] buffer = new byte[32];
		List<String> list = new ArrayList();

		while ((numRead = input.read(buffer, 0, buffer.length)) != -1) {

			list.add(getStringListByByteUtil(buffer, s市场, s股票代码, s股票名称));
		}
		return list;
	}

	/**
	 *
	 * @param 行情数据
	 * @param 股票名称
	 * @return
	 */
	private static String getStringListByByteUtil(byte[] 行情数据, String s市场, String s股票代码, String s股票名称) {

		if (行情数据 == null || 行情数据.length == 0) {
			return null;
		}
		//------------------------
		// 取出通达信行情数据
		//		int date;   // e.g. 20100304
		//		int open;           // *0.01 开盘价
		//		int high;           // *0.01 最高价
		//		int low;            // *0.01 最低价
		//		int close;          // *0.01 收盘价
		//		float amount;        // 成交额
		//		int vol;             // 成交量(手)
		//		int reserved;
		//------------------------
		//DayDataInputBean通达信 dayDataInputBean通达信 = new DayDataInputBean通达信();

		//读取顺序参照 DayDataInputBean通达信
		String result = "";
		result += s市场;
		result += "\t";
		result += s股票代码;
		result += "\t";
		result += s股票名称;
		result += "\t";
		result += ByteUtil.toInt(行情数据, 0, 4);
		result += "\t";
		result += ByteUtil.toInt(行情数据, 4, 4);
		result += "\t";
		result += ByteUtil.toInt(行情数据, 8, 4);
		result += "\t";
		result += ByteUtil.toInt(行情数据, 12, 4);
		result += "\t";
		result += ByteUtil.toInt(行情数据, 16, 4);
		result += "\t";
		result += ByteUtil.toFloat(行情数据, 20, 4);
		result += "\t";
		result += ByteUtil.toInt(行情数据, 24, 4);
		result += "\t";
		result += ByteUtil.toInt(行情数据, 28, 4);

		return result;
	}




	/**
	 * 要把一行拆成一个数组
	 * @param str
	 * @return
	 */
	public static byte[] 解析每一行的数据2(String str, DayDataOutputBean dayDataOutputBean飞狐) {
		/*=======================================
		|-----header			️
		|-----type
		|-----股票数
				|------股票代码
				|------股票中文名
				|------日线个数
						|------开		⬅
						|------高
						|------低
						|------收
		=======================================*/
		// 把数据分解成数组
		String[] s = str.split("\\t");
		if (s.length < 8)
			return null;
		// 判断是不是实际的数据
		//if (判断是不是实际的数据ForDLLData(s)) {
			// 只有实际的数据才进行后续处理
			// 先取得股票代号，做成文件名
			// 再取得入力的值
			// 输出实体文件

			// 先取得股票代号，做成文件名
			// 日期	股票代码	名称	开盘价	最高价	收盘价	最低价	收盘价	成交量	成交金额

			// 先取得股票代号，做成文件名
			String s市场 = s[0];
			String s股票代码 = s[1];
			String s股票名称 = s[2];
			String s日期 = s[3];
			String s开 = s[4];
			String s高 = s[5];
			String s低 = s[6];
			String s收 = s[7];
			String s成交量 = s[9];
			String s成交额 = s[8];

			String[] sData = new String[] { s日期, s开, s高, s低, s收, s成交量, s成交额, s股票代码, s股票名称, s市场 };

			//
			return getByte(sData, dayDataOutputBean飞狐);

		//}
		//return null;
	}

	/**
	 * 再把数组转成byte
	 * @param sData
	 * @return
	 */
	private static byte[] getByte(String[] sData, DayDataOutputBean dayDataOutputBean飞狐) {
		OutputDataUtil outputDataUtil = null;
		switch (sOutPutDataType) {
		case "1": // 钱龙格式数据分析
			outputDataUtil = new OutputDataUtil钱龙();
			break;
		case "2": // 飞狐格式数据分析
			outputDataUtil = new OutputDataUtil飞狐();
			break;
		}
		return outputDataUtil.getOutputData(sData, dayDataOutputBean飞狐);
	}

}
