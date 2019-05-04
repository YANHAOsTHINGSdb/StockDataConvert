package ConvertTool.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import OutputData.OutputDataUtil;
import OutputData.OutputDataUtil爸爸;
import OutputData.feihu.DayDataOutputBean;
import OutputData.feihu.OutputDataUtil飞狐;
import OutputData.qianlong.OutputDataUtil钱龙;

public class StockDataHistoryDayConvertTool extends StockData爸爸 implements ConvertTool.ConvertTool {

	@Override
	public void 输出到文件(String inputFileFolderName) {

		// 从文件名取得日期
		// 解析读入的文件
		//   要逐行解析
		//     取得每行的【股票代号】、以及信息明细
		//     调用工具输出文件。

		// 从文件名取得日期
		// 文件名：沪深Ａ股20190415
		//    后8位为日期
		// this.sDate = inputFileFullPathName.substring(inputFileFullPathName.length()-8, inputFileFullPathName.length())
		if(StringUtils.isEmpty(inputFileFolderName)) {
			System.out.println("err=没有指定入力文件夹。");
			return;
		}

		// 接下来要逐行读入文本文件的数据
		// 进行分析，如果OK，就直接输出文件。

		List<File> fileList = (List<File>)FileUtils.listFiles(new File(inputFileFolderName),null,false);//列出该目录下的所有文件，不递归
		byte[] outputTofile = new byte[0];
		byte[] resultByte深沪股票 = new byte[0];

		byte[] header = null;
		byte[] type = null;
		byte[] 股票数 = null;
		int i股票数 =0;
		for(File file : fileList) {
			/*=======================================
						|-----header				⬅️
						|-----type
						|-----股票数
								|------股票代码
								|------股票中文名
								|------日线个数
										|------开
										|------高
										|------低
										|------收
			=======================================*/

			// 解析文件返回出力内容
			DayDataOutputBean dayDataOutputBean飞狐 = new DayDataOutputBean();
			byte[] resultByteO一只股票 = 将入力文件的内容转成Byte (file, dayDataOutputBean飞狐);

			// 【dayDataOutputBean飞狐】是从子函数里返回的值
			if(header == null)header = dayDataOutputBean飞狐.getHeader();
			if(type == null)type = dayDataOutputBean飞狐.getType();

			// 股票个数加1
			if(resultByteO一只股票 != null && resultByteO一只股票.length > 1 ) {
				// 整合出力数据
				resultByte深沪股票 = OutputDataUtil爸爸.数组合并(resultByte深沪股票, resultByteO一只股票);

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

	/**
	 *
	 * @param file
	 * @return
	 */
	byte[] 将入力文件的内容转成Byte (File file, DayDataOutputBean dayDataOutputBean飞狐) {
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

	    try{
	        if (checkBeforeReadfile(file)){
		      // 出现乱码的对治方案
	          // BufferedReader br = new BufferedReader(new FileReader(file));
	          BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
	          String str;
	          while((str = br.readLine()) != null){
	            System.out.println(str);
	            // 解析每一行返回出力内容
	            byte[] resultByteO一行 = 解析每一行的数据(str, dayDataOutputBean飞狐);

	            // 【dayDataOutputBean飞狐】是从子函数里返回的值
	            // 采用最后出现的股票名称
	            if(dayDataOutputBean飞狐.getStockName() != null)股票代码 = dayDataOutputBean飞狐.getStockName();
	            if(dayDataOutputBean飞狐.getStockChName() != null)股票中文名 = dayDataOutputBean飞狐.getStockChName();


	            if (resultByteO一行 != null) {
		            // 日线数据合并
		            resultByte一个文件 = OutputDataUtil爸爸.数组合并(resultByteO一行, resultByte一个文件);
	            	i日线个数++;
	            }
	          }
	          // 取得【日线个数】的byte值
	          if(日线个数 == null)日线个数 = OutputDataUtil爸爸.convertInttoBytePublic(i日线个数);

	          // 最终整合
	          if (resultByte一个文件 != null && resultByte一个文件.length > 1) {
	        	  resultByte一个文件 = OutputDataUtil爸爸.数组合并(股票代码, 股票中文名, 日线个数, resultByte一个文件);
	          }else {
	        	  resultByte一个文件 = null;
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
		return resultByte一个文件;

	}

	/**
	 * 要把一行拆成一个数组
	 * @param str
	 * @return
	 */
	private byte[] 解析每一行的数据(String str, DayDataOutputBean dayDataOutputBean飞狐) {
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
		// 把数据分解成数组，
		String[] s = str.split(",");
		if (s.length < 8) return null;
		// 判断是不是实际的数据
		if(判断是不是实际的数据(s)) {
			// 只有实际的数据才进行后续处理
			// 先取得股票代号，做成文件名
			// 再取得入力的值
			// 输出实体文件

			// 先取得股票代号，做成文件名
			// 日期	股票代码	名称	开盘价	最高价	收盘价	最低价	收盘价	成交量	成交金额

			/**
				0 UTC
				1 K
				2 G
				3 L
				4 E
				5 C
				6 K
				7 '600734
				8 jingxingzhiye
			 */
			String[] sData = new String[] {s[0].replace("-","").replace("/",""), s[3],s[4],s[5],s[6],s[7],s[8],s[1].replace("'",""),s[2]};

			//
			return getByte(sData, dayDataOutputBean飞狐);

		}

		return null;


	}
	/**
	 * 再把数组转成byte
	 * @param sData
	 * @return
	 */
	private byte[] getByte(String[] sData, DayDataOutputBean dayDataOutputBean飞狐) {
		OutputDataUtil outputDataUtil=null;
		switch(sOutPutDataType) {
		case "1" : // 钱龙格式数据分析
			outputDataUtil = new OutputDataUtil钱龙();
			break;
		case "2" : // 飞狐格式数据分析
			outputDataUtil = new OutputDataUtil飞狐();
			break;
		}
		return outputDataUtil.getOutputData(sData, dayDataOutputBean飞狐);
	}


}
