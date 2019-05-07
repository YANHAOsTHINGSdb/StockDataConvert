package ConvertTool.impl;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import InputData.InputDataUtil;
import OutputData.OutputDataUtil;
import OutputData.OutputDataUtil爸爸;
import OutputData.feihu.DayDataOutputBean;
import OutputData.feihu.OutputDataUtil飞狐;
import OutputData.qianlong.OutputDataUtil钱龙;

public class StockDataOneDayConvertTool extends StockData爸爸 implements ConvertTool.ConvertTool{


//	String[] sStockPath =
//			new String[] {
//					"/Applications/Eclipse_2018-12.app/Contents/workspace/StockDataConvert/data/output/sh",
//					"/Applications/Eclipse_2018-12.app/Contents/workspace/StockDataConvert/data/output/sz"
//			};
	// 日期！，每次只取得一个日期的文件。
	public static void main(String[] args) {
//		// TODO 自動生成されたメソッド・スタブ
//		new StockDataConvertTool().write2(
//				"test.DAY",
//				new String[]{"20190417","6098","7260","6098","7059","319796","487649"}
//				);

		// new StockDataConvertTool().输出到文件("/Applications/Eclipse_2018-12.app/Contents/workspace/StockDataConvert/data/沪深Ａ股20190417.txt");
		// PROPERTY.取得送信URL_head();
		new StockDataOneDayConvertTool().输出到文件(PROPERTY.取得入力文件路径());
	}
	// 每次只读入一个文件
	// 还要说明出力的文件夹在哪里
	// 例
	//    inputFileFullPathName=‘’
	//    shpath= ‘d://’
	//    szpath= ‘d://’
	// 另外，股票代码的首位如果是【6】，则是上海，就往【shpath】的路径下放
	// 其余往【szpath】的路径下放
	 public void 输出到文件(String inputFileFullPathName) {
		// 从文件名取得日期
		// 解析读入的文件
		//   要逐行解析
		//     取得每行的【股票代号】、以及信息明细
		//     调用工具输出文件。

		// 从文件名取得日期
		// 文件名：沪深Ａ股20190415
		//    后8位为日期
		// this.sDate = inputFileFullPathName.substring(inputFileFullPathName.length()-8, inputFileFullPathName.length())
		if(StringUtils.isEmpty(inputFileFullPathName)) {
			System.out.println("err=没有指定入力文件。");
			return;
		}
		this.sDate = FilenameUtils.removeExtension(inputFileFullPathName);
		this.sDate = sDate.substring(sDate.length() -8, sDate.length());   //截取;

		// 接下来要逐行读入文本文件的数据
		// 进行分析，如果OK，就直接输出文件。
	    try{
	        File file = new File(inputFileFullPathName);
	        if (checkBeforeReadfile(file)){
	          //BufferedReader br = new BufferedReader(new FileReader(file));
	          BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
	          String str;
	          while((str = br.readLine()) != null){
	            System.out.println(str);
	            解析每一行的数据(str);
	          }
	          br.close();
	        }else{
	          System.out.println("ファイルが見つからないか開けません");
	        }
	      }catch(FileNotFoundException e){
	        System.out.println(e);
	      }catch(IOException e){
	        System.out.println(e);
	      }
	}

	 public void 输出到文件2() {

		this.sDate = new String();
		// this.sDate = "20190507";
		//List<String[]> todayDatas = new ArrayList<String[]>();
		List<String> todayDatas = new ArrayList<String>();
		// 取得最后交易日的成交数据

		byte[] outputTofile = new byte[0];
		byte[] resultByte深沪股票 = new byte[0];

		byte[] header = null;
		byte[] type = null;
		byte[] 股票数 = null;
		int i股票数 =0;

		if(InputDataUtil.取得最后交易日的数据(todayDatas, sDate)) {
			//
			// todayDatas.add("0	002191	4052	12.580000	13.410000	12.980000	13.800000	12.220000	14999043	-1258	591083	4542	762218816.000000	327635	263448	6	129813	12.580000	12.590000	1280	1113	12.570000	12.600000	22	112	12.530000	12.610000	93	30	12.520000	12.620000	850	178	12.510000	12.630000	4	280	3402	0	-57	187	-65	0.400000	4052");
			if(todayDatas == null || todayDatas.size() <= 0) {return;}

	        for(String todayData:todayDatas) {
	            System.out.println(todayData);
				// 解析文件返回出力内容
				DayDataOutputBean dayDataOutputBean飞狐 = new DayDataOutputBean();
				// byte[] resultByteO一只股票 = 将入力文件的内容转成Byte (file, dayDataOutputBean飞狐);
				byte[] resultByteO一行 = StockDataHistoryDayConvertTool.解析每一行的数据2(this.sDate, todayData, dayDataOutputBean飞狐);
				// 【dayDataOutputBean飞狐】是从子函数里返回的值
				if(header == null)header = dayDataOutputBean飞狐.getHeader();
				if(type == null)type = dayDataOutputBean飞狐.getType();

				// 股票个数加1
				if(resultByteO一行 != null && resultByteO一行.length > 1 ) {
					// 整合出力数据
					resultByte深沪股票 = OutputDataUtil爸爸.数组合并(resultByte深沪股票, resultByteO一行);

					i股票数 ++;
				}
	        }

			if(i股票数 == 0) return; // 如果股票数为零，就退出
			if(股票数 == null)股票数 = OutputDataUtil爸爸.convertInttoBytePublic(i股票数);

			// 最终整合
			outputTofile = OutputDataUtil爸爸.数组合并(header,type,股票数,resultByte深沪股票);

			String outFileName = PROPERTY.取得飞狐用导入数据文件名();
			outFileName = StringUtils.isEmpty(outFileName)?"historyDataForFeihuSoftWare": outFileName;
			write(PROPERTY.取得sh出力目录().concat("\\").concat(outFileName).concat(".".concat(s数据格式扩展名[Integer.parseInt(sOutPutDataType)])),outputTofile);

		}
	}


//	/*
//	 * Java文件操作 获取不带扩展名的文件名
//	 *
//	 *  Created on: 2011-8-2
//	 *      Author: blueeagle
//	 */
//    public static String getFileNameNoEx(String filename) {
//        if ((filename != null) && (filename.length() > 0)) {
//            int dot = filename.lastIndexOf('.');
//            if ((dot >-1) && (dot < (filename.length()))) {
//                return filename.substring(0, dot);
//            }
//        }
//        return filename;
//    }


//	private void write() {
//
//		File file = new File("./test.dat");
//		try{
//			if(!file.exists()) {
//				file.createNewFile();
//			}
//			FileOutputStream fos = new FileOutputStream(file);
//			ObjectOutputStream oos = new ObjectOutputStream(fos);
//			oos.write(OutputDataUtil钱龙.getOutputData("20060915","6098","7260","6098","7059","319796","487649"));
//			System.out.println("over");
//			oos.close();
//			fos.close();
//
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//	}

	 public void 解析每一行的数据2(String str) {
		// 把数据分解成数组，
		String[] s = str.split("\\t");
		if (s.length < 8) return;
		// 判断是不是实际的数据
		if(判断是不是实际的数据ForDLLData(s)) {
			// 只有实际的数据才进行后续处理
			// 先取得股票代号，做成文件名
			// 再取得入力的值
			// 输出实体文件

			// 先取得股票代号，做成文件名
			String s股票名称=s[0].equals("0")?"深证指数":"上证指数";
			String s股票代码=s[1];
			String s开=s[5];
			String s高=s[6];
			String s低=s[7];
			String s收=s[3];
			String s成交量=s[10];
			String s成交额=s[12];
			String[] sData = new String[] {this.sDate, s开,s高,s低,s收,s成交量,s成交额,s股票代码,s股票名称};


			write2(取得市场路径2(s[0]).concat("/").concat(s[0]).concat(".".concat(s数据格式扩展名[Integer.parseInt(sOutPutDataType)])), sData);
		}

	}


		/**
	 *
	 * @param str
	 */
	public void 解析每一行的数据(String str) {
		// 把数据分解成数组，
		String[] s = str.split("\\t");
		if (s.length < 8) return;
		// 判断是不是实际的数据
		if(判断是不是实际的数据(s)) {
			// 只有实际的数据才进行后续处理
			// 先取得股票代号，做成文件名
			// 再取得入力的值
			// 输出实体文件

			// 先取得股票代号，做成文件名
			String[] sData = new String[] {this.sDate, s[2],s[3],s[4],s[5],s[6],s[7],s[0],s[1]};


			write2(取得市场路径(s[0]).concat("/").concat(s[0]).concat(".".concat(s数据格式扩展名[Integer.parseInt(sOutPutDataType)])), sData);
		}
	}

	// 指定文件名
	// 指定要导入的数据
	// 就可以直接生成文件了。
	protected void write2(String sFileName, String[] sData) {

		//ByteArrayOutputStream byteArrayOutputStream = null;
		DataOutputStream dataOutputStream = null;

		OutputDataUtil outputDataUtil=null;
		switch(sOutPutDataType) {
		case "1" : // 钱龙格式数据分析
			outputDataUtil = new OutputDataUtil钱龙();
			break;
		case "2" : // 飞狐格式数据分析
			outputDataUtil = new OutputDataUtil飞狐();
			break;
		}

	      try{

	    	  FileOutputStream fos = new FileOutputStream(sFileName);

	          // create byte array output stream
	    	  // byteArrayOutputStream = new ByteArrayOutputStream();

	          // create data output stream
	    	  dataOutputStream = new DataOutputStream(fos);

	          // write to the stream from integer array
	    	  dataOutputStream.write(outputDataUtil.getOutputData(sData,null));

	          // flushes bytes to underlying output stream
	    	  dataOutputStream.flush();

	          System.out.println("over");

	       }catch(Exception e){
	          // if any error occurs
	          e.printStackTrace();
	       }finally{

	          if(dataOutputStream!=null)
				try {
					dataOutputStream.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
	       }
	    }
}
