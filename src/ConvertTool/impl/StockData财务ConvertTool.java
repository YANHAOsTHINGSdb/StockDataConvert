package ConvertTool.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import InputData.InputDataUtil财务;
import OutputData.OutputDataUtil;
import OutputData.OutputDataUtil爸爸;
import OutputData.feihu.OutputDataUtil飞狐;
import OutputData.feihu.财务DataOutputBean飞狐;



public class StockData财务ConvertTool extends StockData爸爸 implements ConvertTool.ConvertTool {

	private String[] s最后交易日期;

	public StockData财务ConvertTool() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void 输出到文件(String inputFileFullPathName) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * @param s财务原始文件全路径
	 *
	 */
	public String 输出到财务文件() {

		this.s最后交易日期 = new String[1];
		// this.sDate = "20190507";
		//List<String[]> todayDatas = new ArrayList<String[]>();
		List<String> out财务数据list = new ArrayList<String>();
		// 取得最后交易日的成交数据

		byte[] outputTofile最终整合出力数据 = new byte[0];
		byte[] resultByte深沪股票财务输出数据 = new byte[0];

		byte[] outHeader = null;
		byte[] outType = null;
		byte[] 股票数 = null;

//		byte[] resultByte一个文件 = new byte[0];
		int[] outi股票数 = {0};


		if(InputDataUtil财务.取得财务数据和最后交易日(out财务数据list, s最后交易日期)) {
			// 1、事前判断
			// todayDatas.add("0	002191	4052	12.580000	13.410000	12.980000	13.800000	12.220000	14999043	-1258	591083	4542	762218816.000000	327635	263448	6	129813	12.580000	12.590000	1280	1113	12.570000	12.600000	22	112	12.530000	12.610000	93	30	12.520000	12.620000	850	178	12.510000	12.630000	4	280	3402	0	-57	187	-65	0.400000	4052");
			if(out财务数据list == null || out财务数据list.size() <= 0) {return null;}

			// 2、解析取到的深沪股票财务输出数据:把除权信息做成出力格式
			List<String> in财务数据list = out财务数据list;
			财务DataOutputBean飞狐 out财务dataOutputBean飞狐 = new 财务DataOutputBean飞狐();
			resultByte深沪股票财务输出数据 = 解析取到的深沪股票财务输出数据(in财务数据list, outi股票数, out财务dataOutputBean飞狐);

			// 3、如果没有有效的财务股票，就退出
			if(outi股票数[0] == 0) return null;
			//    否则、计算有效股票数的输出格式
			else if(股票数 == null) 股票数 = OutputDataUtil爸爸.convertInttoBytePublic(outi股票数[0]);

			// 4、做成最终整合出力数据
			outputTofile最终整合出力数据 = OutputDataUtil爸爸.数组合并2(out财务dataOutputBean飞狐.getHeader(), out财务dataOutputBean飞狐.getType(), 股票数, resultByte深沪股票财务输出数据);

			// 5、设置出力文件名
			String outFileName = PROPERTY.取得飞狐用导入数据文件名();
			outFileName = StringUtils.isEmpty(outFileName)?"historyDataForFeihuSoftWare": outFileName;

			// 6、写入文件
			String s出力文件全路径 = PROPERTY.取得sh出力目录().concat("\\").concat(s数据格式扩展名[Integer.parseInt(飞狐财务_文件名)]);
			System.out.print("请在此处 取得出力文件:");
			System.out.println(new File(s出力文件全路径).getAbsolutePath());
			//String s处理文件 = PROPERTY.取得sh出力目录().concat("\\").concat(outFileName).concat(".".concat("cwd"));
			//write(s处理文件, outputTofile最终整合出力数据);
			write(s出力文件全路径, outputTofile最终整合出力数据);

			return s出力文件全路径;
		}

		return null;

	}

	/**
	 * 解析取到的深沪股票财务输出数据
	 * @param 财务数据list in  取到的财务数据
	 * @param header     out 输出用头文件
	 * @param type       out 输出用type
	 * @param i股票数    out 输出股票个数
	 * @param out财务dataOutputBean飞狐2
	 * @return
	 */
	private byte[] 解析取到的深沪股票财务输出数据(List<String> 财务数据list, int[] i股票数, 财务DataOutputBean飞狐 out财务dataOutputBean飞狐2) {
		/**
		 * 财务数据list是什么样子？
		 *
		 *	String =市场 证券代码 日期 权息数据类别 前流通盘或派息金额 前总股本或配股价 后流通盘或送股数 后总股本或配售数
			String =市场 证券代码 日期 权息数据类别 前流通盘或派息金额 前总股本或配股价 后流通盘或送股数 后总股本或配售数
					0 300486 20150630 5 0.000000 0.000000 3472.000000 13886.087891
					0 300486 20160421 5 3472.000000 13886.087891 3472.000000 14138.087891
					0 300486 20160616 1 1.100000 0.000000 0.000000 0.000000
					0 300486 20160701 5 3472.000000 14138.087891 8425.858398 14138.087891
					0 300486 20171012 5 8425.858398 14138.087891 8425.858398 14028.288086
					0 300486 20171231 5 8425.858398 14028.288086 8958.393555 14028.288086
					0 300486 20180411 5 8958.393555 14028.288086 8425.858398 16304.118164
					0 300486 20180529 1 0.250000 0.000000 0.000000 0.000000
					0 300486 20180630 5 8425.858398 16304.118164 13338.958008 16304.118164
					0 300486 20180702 5 13338.958008 16304.118164 13353.552734 16304.118164
					0 300486 20180911 5 13353.552734 16304.118164 13338.958008 16161.918945
					0 300486 20190129 5 13338.958008 16161.918945 13371.403320 18067.083984
					0 300486 20190630 5 13371.403320 18067.083984 13209.409180 18067.083984
					0 300486 20190703 1 0.350000 0.000000 0.000000 0.000000
		 */
		byte[] resultByte深沪股票财务输出数据 = new byte[0];
		byte[] 股票代码 = null;
		byte[] 财务数据个数 = null;

		// 对每一只股票的财务信息解析
       for(String in每股财务Data : 财务数据list) {
           System.out.println(in每股财务Data);
			// 解析文件返回出力内容
           财务DataOutputBean飞狐 out财务dataOutputBean飞狐 = new 财务DataOutputBean飞狐();
			// byte[] resultByteO一只股票 = 将入力文件的内容转成Byte (file, dayDataOutputBean飞狐);

			byte[] resultByteO一条 = StockData财务ConvertTool.解析每一行的财务数据(
					this.s最后交易日期,
					in每股财务Data,
					out财务dataOutputBean飞狐);

			// 【dayDataOutputBean飞狐】是从子函数里返回的值
			if(out财务dataOutputBean飞狐.getHeader() != null)out财务dataOutputBean飞狐2.setHeader(out财务dataOutputBean飞狐.getHeader());
			if(out财务dataOutputBean飞狐.getType() != null )out财务dataOutputBean飞狐2.setType(out财务dataOutputBean飞狐.getType());


			// 股票个数加1
           // 将解析完的数组相加
			if(resultByteO一条 != null && resultByteO一条.length > 1 ) {
				// 整合出力数据

				// 财务个数 = OutputDataUtil爸爸.convertInttoBytePublic(i财务数据个数);
				财务数据个数 = out财务dataOutputBean飞狐.get股票数();
				resultByte深沪股票财务输出数据 = OutputDataUtil爸爸.数组合并2(
						resultByte深沪股票财务输出数据,
						out财务dataOutputBean飞狐.get股票代码(),
						//out财务dataOutputBean飞狐.get空白(),
						resultByteO一条
						);

				i股票数[0] ++;
			}
       }

		return resultByte深沪股票财务输出数据;
	}

	public static byte[]解析每一行的财务数据( String[] s最后交易日期, String s财务Data, 财务DataOutputBean飞狐 财务dataOutputBean飞狐) {
		/*=======================================
		|-----header			️
		|-----type
		|-----股票数
				|------股票代码
				|------财务数据
						|------送股-配股-配送股-分红
						|------送股-配股-配送股-分红
						|------送股-配股-配送股-分红
						|------送股-配股-配送股-分红

		=======================================*/

		/**
		 * s除权Data是什么样子？
		 *  <没有除权信息>
		 *	String =市场 证券代码 日期 权息数据类别 前流通盘或派息金额 前总股本或配股价 后流通盘或送股数 后总股本或配售数
		    <有除权信息>
			String =市场 证券代码 日期 权息数据类别 前流通盘或派息金额 前总股本或配股价 后流通盘或送股数 后总股本或配售数
					0 300486 20150630 5 0.000000 0.000000 3472.000000 13886.087891
					0 300486 20160421 5 3472.000000 13886.087891 3472.000000 14138.087891
					0 300486 20160616 1 1.100000 0.000000 0.000000 0.000000
					0 300486 20160701 5 3472.000000 14138.087891 8425.858398 14138.087891
					0 300486 20171012 5 8425.858398 14138.087891 8425.858398 14028.288086
					0 300486 20171231 5 8425.858398 14028.288086 8958.393555 14028.288086
					0 300486 20180411 5 8958.393555 14028.288086 8425.858398 16304.118164
					0 300486 20180529 1 0.250000 0.000000 0.000000 0.000000
					0 300486 20180630 5 8425.858398 16304.118164 13338.958008 16304.118164
					0 300486 20180702 5 13338.958008 16304.118164 13353.552734 16304.118164
					0 300486 20180911 5 13353.552734 16304.118164 13338.958008 16161.918945
					0 300486 20190129 5 13338.958008 16161.918945 13371.403320 18067.083984
					0 300486 20190630 5 13371.403320 18067.083984 13209.409180 18067.083984
					0 300486 20190703 1 0.350000 0.000000 0.000000 0.000000
		 */
		// 把数据分解成数组
		byte[] resultByte深沪个股财务输出数据 = new byte[0];
		String[] s每条财务数据 = s财务Data.split("\\n");
		int iIndex = 0;
		int i财务数据个数 = 0;
		for(String s每项财务数据  : s每条财务数据) {
			if(iIndex == 0) {
				iIndex++;
				continue;
			}

			// 从第二条开始分析
			String[] s = s每项财务数据.split("\\t");
			if (s.length !=37) return null;
			// 判断是不是实际的数据
			if(判断是不是有效的财务数据ForDLLData(s)) {
				// 只有实际的数据才进行后续处理
				// 先取得股票代号，做成文件名
				// 再取得入力的值
				// 输出实体文件

				// 先取得股票代号，做成文件名
				// 市场 证券代码 日期 权息数据类别 派息金额 配股价 送股数 配股数

				// 先取得股票代号，做成文件名
				String s市场       = s[0];
				String s证券代码 = s[1];
				String s流通股本      = s[2];
				String s所属省份= s[3];
				String s所属行业 = s[4];
				String s账务更新日期  = s[5];
				String s上市日期   = s[6];
				String s总股本   = s[7];
				String s国家股   = s[8];
				String s发起人法人股   = s[9];
				String s法人股   = s[10];
				String sB股   = s[11];
				String sH股  = s[12];
				String s职工股   = s[13];
				String s总资产   = s[14];
				String s流动资产  = s[15];
				String s固定资产   = s[16];
				String s无形资产   = s[17];
				String s股东人数   = s[18];
				String s流动负债   = s[19];
				String s长期负债   = s[20];
				String s资本公积金  = s[21];
				String s净资产   = s[22];
				String s主营收入   = s[23];
				String s主营利润   = s[24];
				String s应收帐款   = s[25];
				String s营业利润   = s[26];
				String s投资收益 = s[27];
				String s经营现金流   = s[28];
				String s总现金流   = s[29];
				String s存贷   = s[30];
				String s利润总额  = s[31];
				String s税后利润   = s[32];
				String s净利润   = s[33];
				String s未分利润   = s[34];
				String s保留  = s[35];
				String s保留1   = s[36];


				String[] sData = new String[] {s市场, s证券代码,s流通股本 ,s所属省份,s所属行业,s账务更新日期,s上市日期 ,s总股本 ,s国家股,s发起人法人股,s法人股,sB股,sH股,s职工股,s总资产,s流动资产,s固定资产,s无形资产,s股东人数,s流动负债,s长期负债,s资本公积金,s净资产, s主营收入,s主营利润,s应收帐款,s营业利润 ,s投资收益,s经营现金流,s总现金流,s存贷,s利润总额 ,s税后利润,s净利润 ,s未分利润,s保留,s保留1};
				/**
				 * 把每条财务数据进行拼接+组合
				 */
				byte[] 财务单条dataOutputBean飞狐 = get财务Byte(sData, 财务dataOutputBean飞狐);
				resultByte深沪个股财务输出数据  = OutputDataUtil爸爸.数组合并2(resultByte深沪个股财务输出数据, 财务单条dataOutputBean飞狐);
				i财务数据个数++;
				财务dataOutputBean飞狐.set股票数(OutputDataUtil爸爸.convertInttoBytePublic(i财务数据个数));
			}
		}
		return resultByte深沪个股财务输出数据;
	}

	/**
	 * 判断是不是有效的财务数据ForDLLData
	 * @param s={市场 证券代码 日期 权息数据类别 派息金额 配股价 送股数 配股数}
	 * @return 如果 权息数据类别 == “1” return true;
	 *         如果 权息数据类别 != “1” return false;
	 */
	public static boolean 判断是不是有效的财务数据ForDLLData(String[] s) {
		// 市场 证券代码 日期 权息数据类别 派息金额 配股价 送股数 配股数
		if(s[2].equals("0.000000")) {return false;}
		if(s[5].equals("0"))  {return false;}
		return true;
	  }

	private static byte[] get财务Byte(String[] sData, 财务DataOutputBean飞狐 财务dataOutputBean飞狐) {
		OutputDataUtil outputDataUtil = new OutputDataUtil飞狐();

		return outputDataUtil.getOutputData财务(sData, 财务dataOutputBean飞狐);
	}

	// 日期！，每次只取得一个日期的文件。
	public static void main(String[] args) {

		List<String> 财务数据list = InputDataUtil财务.getFileContext2("./财务数据.data");

		int[] outi股票数 = {0};
		财务DataOutputBean飞狐 out财务dataOutputBean飞狐 = new 财务DataOutputBean飞狐();
		byte[] resultByte深沪股票财务输出数据 = new StockData财务ConvertTool().解析取到的深沪股票财务输出数据( 财务数据list, outi股票数,out财务dataOutputBean飞狐);

		if(outi股票数[0] == 0) return; // 如果股票数为零，就退出
		byte[] 股票数 = null;
		if(股票数 == null)股票数 = OutputDataUtil爸爸.convertInttoBytePublic(outi股票数[0]);

		// 最终整合
		byte[] outputTofile最终整合出力数据 = new byte[0];
		outputTofile最终整合出力数据 = OutputDataUtil爸爸.数组合并(
				out财务dataOutputBean飞狐.getHeader(),
				out财务dataOutputBean飞狐.getType(),
				股票数,
				resultByte深沪股票财务输出数据
		);

		String outFileName = PROPERTY.取得飞狐用导入数据文件名();
		outFileName = StringUtils.isEmpty(outFileName)?"historyDataForFeihuSoftWare": outFileName;

		//new StockDataOneDayConvertTool().write(PROPERTY.取得sh出力目录().concat("\\").concat(outFileName).concat(".".concat(new StockDataOneDayConvertTool().s数据格式扩展名[Integer.parseInt(sOutPutDataType)])),outputTofile最终整合出力数据);
		new StockDataOneDayConvertTool().write("/Users/haoyan/Desktop/FinanceDataConvert/ftsplit.CQD",outputTofile最终整合出力数据);

	}
}
