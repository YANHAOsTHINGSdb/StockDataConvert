import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import OutputData.OutputDataUtil;
import OutputData.feihu.OutputDataUtil飞狐;
import OutputData.qianlong.OutputDataUtil钱龙;

public class StockDataConvertTool {
	// 日期！，每次只取得一个日期的文件。
	String sDate = null;
	// 出力的路径
	// 因为sz与sh是在同一个文件里的。所以出力的时候要分开来
	String[] sStockPath =
			new String[] {
					PROPERTY.取得sh出力目录(),
					PROPERTY.取得sz出力目录()
			};
	String[] s数据格式扩展名 =
			new String[] {
					"txt", // 0:未知
					"DAY", // 1:钱龙
					"QDA", // 2:飞狐
					"day", // 3:通达信
					"DAD"  // 4:分析家
			};
	/**
	 * 1=钱龙
	 * 2=飞狐
	 */
	static String sOutPutDataType =PROPERTY.取得出力数据格式();

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
		new StockDataConvertTool().输出到文件(PROPERTY.取得入力文件路径());
	}
	// 每次只读入一个文件
	// 还要说明出力的文件夹在哪里
	// 例
	//    inputFileFullPathName=‘’
	//    shpath= ‘d://’
	//    szpath= ‘d://’
	// 另外，股票代码的首位如果是【6】，则是上海，就往【shpath】的路径下放
	// 其余往【szpath】的路径下放
	private void 输出到文件(String inputFileFullPathName) {
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
	/**
	 *
	 * @param str
	 */
	private void 解析每一行的数据(String str) {
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
	/**
	 *
	 * @param str
	 */
	private String 取得市场路径(String s股票代号) {
		if(s股票代号.charAt(0)=='6') {
			return sStockPath[0];
		}
		return sStockPath[1];
	}

	/**
	 *
	 * @param str
	 */
	private boolean 判断是不是实际的数据(String[] s) {
		// 如果第一项不为数字，则是标题行，需要掠过
		// 下例的时候也会报错：
		// 603982	Ȫ������	--  	--  	--  	0.00	0	0
		// 所以要确保所有的数据都是数值的时候才放行
		if(
				NumberUtils.isDigits(s[0])
				&& s[2].matches("[-+]?[0-9]*\\.?[0-9]+")
				&& s[3].matches("[-+]?[0-9]*\\.?[0-9]+")
				&& s[4].matches("[-+]?[0-9]*\\.?[0-9]+")
				&& s[5].matches("[-+]?[0-9]*\\.?[0-9]+")
				&& NumberUtils.isDigits(s[6])
				&& NumberUtils.isDigits(s[7])

		) {
			return true;
		}
		return false;
	}


	private static boolean checkBeforeReadfile(File file) {
		if (file.exists()) {
			if (file.isFile() && file.canRead()) {
				return true;
			}
		}
		return false;
	}
	private void write() {

		File file = new File("./test.dat");
		try{
			if(!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.write(OutputDataUtil钱龙.getOutputData("20060915","6098","7260","6098","7059","319796","487649"));
			System.out.println("over");
			oos.close();
			fos.close();

		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	// 指定文件名
	// 指定要导入的数据
	// 就可以直接生成文件了。
	private void write2(String sFileName, String[] sData) {

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
	    	  dataOutputStream.write(outputDataUtil.getOutputData(sData));

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
