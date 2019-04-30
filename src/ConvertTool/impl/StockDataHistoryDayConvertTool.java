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

import com.mycompany.common.PROPERTY;

import OutputData.OutputDataUtil;
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
		byte[] resultByte深沪股票 = new byte[0];

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
			byte[] header = null;
			byte[] type = null;
			byte[] 股票数 = null;
			byte[] resultByteO一只股票 = 将入力文件的内容转成Byte (file);
			resultByte深沪股票 = new byte[resultByteO一只股票.length + resultByte深沪股票.length];
		}
		String outFileName = PROPERTY.取得飞狐用导入数据文件名();
		outFileName = StringUtils.isEmpty(outFileName)?"historyDataForFeihuSoftWare": outFileName;
		write(PROPERTY.取得sh出力目录().concat(outFileName).concat(".".concat(s数据格式扩展名[Integer.parseInt(sOutPutDataType)])),resultByte深沪股票);
	}

	/**
	 *
	 * @param file
	 * @return
	 */
	byte[] 将入力文件的内容转成Byte (File file) {
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
		DayDataOutputBean dayDataOutputBean飞狐 = new DayDataOutputBean();
	    try{
	        if (checkBeforeReadfile(file)){
	          //BufferedReader br = new BufferedReader(new FileReader(file));
	          BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
	          String str;
	          while((str = br.readLine()) != null){
	            System.out.println(str);

	            byte[] resultByteO一行 = 解析每一行的数据(str, dayDataOutputBean飞狐);
	            股票代码 = dayDataOutputBean飞狐.getStockName();
	            股票中文名 = dayDataOutputBean飞狐.getStockChName();
	            // 没有做整合处理
	            resultByte一个文件 = new byte[resultByteO一行.length + resultByte一个文件.length];
	            i日线个数++;
	          }
	          dayDataOutputBean飞狐.setDataSize(日线个数);
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
		String[] s = str.split("\\t");
		if (s.length < 8) return null;
		// 判断是不是实际的数据
		if(判断是不是实际的数据(s)) {
			// 只有实际的数据才进行后续处理
			// 先取得股票代号，做成文件名
			// 再取得入力的值
			// 输出实体文件

			// 先取得股票代号，做成文件名
			// 日期	股票代码	名称	开盘价	最高价	收盘价	最低价	收盘价	成交量	成交金额
			String[] sData = new String[] {s[0].replace("-","").replace("/",""), s[2],s[3],s[4],s[5],s[6],s[7],s[0],s[1]};
			return getByte(sData,dayDataOutputBean飞狐);
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