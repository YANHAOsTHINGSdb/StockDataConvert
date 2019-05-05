package ConvertTool.impl;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.sun.jna.ptr.ShortByReference;

import InputData.hqApiJava1;
import OutputData.OutputDataUtil;
import OutputData.OutputDataUtil爸爸;
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

		this.sDate = null;
		String[] todayDatas = null;
		// 取得最后交易日的成交数据
		if(getTodayData(todayDatas, sDate)) {
			// 
			if(todayDatas == null || todayDatas.length <= 0) {return;}
			
	        for(String todayData:todayDatas) {
	            System.out.println(todayData);
	            解析每一行的数据(todayData);
	        }
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
	 	
	 // 取得最后交易日的数据
	private boolean getTodayData(String[] todayData, String sDate) {
		byte[] Result = new byte[65535];
		byte[] ErrInfo = new byte[256];
		byte[] Market = {0,1};
		byte[] Zqdm = null;
		// 连接服务器
		if (hqApiJava1.getConnect(PROPERTY.取得IP(), PROPERTY.取得Port(), Result ,ErrInfo)) {
			
			//【交易日】
			// 招商证券北京行情	20190430	20960430
			// 9, 50, 48, 49, 57, 48, 52, 51, 48, 
			// 9, 50, 48, 57, 54, 48, 52, 51, 48,
			sDate = 取得最后交易日期(Result);
			ShortByReference Count=new ShortByReference();
			//------------------------------------------------------------
			//【实时数据】=最后交易日期
			// 要取得实时数据（因为取不到K线数据，所以只能拿到实时数据）
			//------------------------------------------------------------
			// 0=深圳
			byte[] 股票代码Result = new byte[65535];
			if(hqApiJava1.getGetSecurityList((byte)0, (short)0, Count, 股票代码Result, ErrInfo)) {
				// 根据股票的结果数，取得行情数据
				
				byte[] 行情数据 = 取得行情数据(0, 股票代码Result, 500);
			}
			// 1=上海
			if(hqApiJava1.getGetSecurityList((byte)0, (short)0, Count, Result, ErrInfo)) {
				// 根据股票的结果数，取得行情数据
				byte[] 行情数据 = 取得行情数据(1, 股票代码Result, 500);
			}
			
		}
		return false;
	}
	
	private byte[] 取得行情数据(int i市场代码, byte[] 股票代码Result, int 取得行情数) {
		byte[] 取得行情数据 = null;
		byte[] Result = new byte[65535];
		byte[] ErrInfo = new byte[256];
		
		// 每500股取一次行情
		for(int i=0 ; i < 股票代码Result.length; i=i+取得行情数) {
			// 做成这500的市场数据
			byte[] 市场数据 = 做成这500的市场数据(i市场代码, 取得行情数);
			
			// 做成这500的股票代码
			String[] 股票代码 = 做成这500的股票代码(i市场代码, 取得行情数, i, 股票代码Result);
			
			// 取得行情数据
			byte[] 行情数据 = hqApiJava1.getGetSecurityQuotes(市场数据, 股票代码, Result, ErrInfo);
			取得行情数据 = OutputDataUtil爸爸.数组合并(取得行情数据, 行情数据);
		}
		return 取得行情数据;
	}
	
	private String[] 做成这500的股票代码(int i市场代码, int 取得行情数, int i, byte[] 股票代码Result) {
		// 这里记述的是怎么解析
		// 基本的思路就是
		// 先将【股票代码Result】分解成结构体。
		// 然后从结构体中取得【股票代码】
		
		String[] 股票代码 = new String[取得行情数];
		
//		for(int i=0 ; i < 取得行情数; i=i+取得行情数) {
//			股票代码[i] = new String(股票代码Result[i]).substring(0, 6);
//		}
		return 股票代码;

	}
	private byte[] 做成这500的市场数据(int i市场代码, int 取得行情数) {
		byte[] 市场数据 = new byte[取得行情数];
		for(int i=0 ; i < 取得行情数; i=i+取得行情数) {
			市场数据[i] = (byte) i市场代码;
		}
		return 市场数据;
	}
	
	private String 取得最后交易日期(byte[] result) {
		// 取得最后的18个byte！
		String s=new String(result);
		return s.substring(s.length()-17, s.length()-9);
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
