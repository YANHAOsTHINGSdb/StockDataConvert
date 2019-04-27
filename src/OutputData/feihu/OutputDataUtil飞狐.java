package OutputData.feihu;

import OutputData.OutputDataUtil;
import OutputData.OutputDataUtil爸爸;

public class OutputDataUtil飞狐 extends OutputDataUtil爸爸 implements OutputDataUtil{
//	static public byte[] getOutputData(String string1, String string2, String string3, String string4, String string5,
//			String string6, String string7) {
//		//--------------------------
//		// 先将读入的字符串转成int的数组
//		//--------------------------
//		int[] inputDataArray= {
//			Integer.parseInt(string1)
//			,Integer.parseInt(string2)
//			,Integer.parseInt(string3)
//			,Integer.parseInt(string4)
//			,Integer.parseInt(string5)
//			,Integer.parseInt(string6)
//			,Integer.parseInt(string7)
//		};
//		return createOutputData(inputDataArray);
//	}

	public byte[] getOutputData(String[] datas) {
		//--------------------------
		// 先将读入的字符串转成int的数组
		//--------------------------
//		int[] inputDataArray= {
//			Integer.parseInt(datas[0])
//			,(int)(Float.parseFloat(datas[1]) * 1000)
//			,(int)(Float.parseFloat(datas[2]) * 1000)
//			,(int)(Float.parseFloat(datas[4]) * 1000) //
//			,(int)(Float.parseFloat(datas[3]) * 1000) //
//			,Integer.parseInt(datas[5])
//			,Integer.parseInt(datas[6])
//		};
		DayDataBean飞狐 dayDataBean飞狐 = new DayDataBean飞狐();
		dayDataBean飞狐.setF开盘(Float.parseFloat(datas[1]));
		dayDataBean飞狐.setF最高(Float.parseFloat(datas[2]));
		dayDataBean飞狐.setF最低(Float.parseFloat(datas[3]));
		dayDataBean飞狐.setF收盘(Float.parseFloat(datas[4]));
		dayDataBean飞狐.setF成交量(Float.parseFloat(datas[5]));
		dayDataBean飞狐.setF成交金额(Float.parseFloat(datas[6]));

		dayDataBean飞狐.setDataSize(0x1);
		dayDataBean飞狐.setHeader(0xffffffe2);
		dayDataBean飞狐.setI成交次数(0x0);
		dayDataBean飞狐.setStockChName(取得市场代号(datas[7]).concat(datas[7]).toCharArray());
		dayDataBean飞狐.setStockCount(0x1);
		dayDataBean飞狐.setStockName(datas[8].toCharArray());
		dayDataBean飞狐.setType(0x00000101);
		dayDataBean飞狐.setUTCtime(getUTCtime(datas[0]));
		return createOutputData(dayDataBean飞狐);
	}



	private byte[] createOutputData(DayDataBean飞狐 inputDataArray) {
		//--------------------------
		// 把每个int转成byte
		// 将转成byte做倒序
		// 最后进行重组
		//--------------------------
		byte[] outputData最终 = new byte[72];
		byte[] output = null;
		int i游标 = 0;

		// int header;			// header：ffffffe2
		//output = 倒叙(inputDataArray.getHeader());
		output = convertInttoByte(inputDataArray.getHeader());
		i游标 = outputData(outputData最终, output,i游标);

		// int type;			// type：00000101
		output = convertInttoByte(inputDataArray.getType());
		i游标 = outputData(outputData最终, output,i游标);

		// int stockCount;		// 股票数：00000001
		output = convertInttoByte(inputDataArray.getStockCount());
		i游标 = outputData(outputData最终, output,i游标);

		// char stockName[];	// size=12 SZ002067空空
		output = convertChartoByte(inputDataArray.getStockChName());
		i游标 = outputData(outputData最终, output,i游标);

		// char stockChName[];	// size=12 景兴纸业
		output = convertGB2312ChartoByte(inputDataArray.getStockName());
		i游标 = outputData(outputData最终, output,i游标);

		// int dataSize;		// 日线个数：00000001
		output = convertInttoByte(inputDataArray.getDataSize());
		i游标 = outputData(outputData最终, output,i游标);

		// int UTCtime;		// UTC time：5cc24a00
		output = convertInttoByte(inputDataArray.getUTCtime());
		i游标 = outputData(outputData最终, output,i游标);

		// float s; 			// 开盘：407ccccd
		output = convertFloattoByte(inputDataArray.getF开盘());
		i游标 = outputData(outputData最终, output,i游标);

		// float h; 			// 最高
		output = convertFloattoByte(inputDataArray.getF最高());
		i游标 = outputData(outputData最终, output,i游标);

		// float l; 			// 最低
		output = convertFloattoByte(inputDataArray.getF最低());
		i游标 = outputData(outputData最终, output,i游标);

		// float e; 			// 收盘
		output = convertFloattoByte(inputDataArray.getF收盘());
		i游标 = outputData(outputData最终, output,i游标);

		// float c; 			// 成交量
		output = convertFloattoByte(inputDataArray.getF成交量());
		i游标 = outputData(outputData最终, output,i游标);

		// float k; 			// 成交金额
		output = convertFloattoByte(inputDataArray.getF成交金额());
		i游标 = outputData(outputData最终, output,i游标);

		// int t; 				// 成交次数
		output = convertInttoByte(inputDataArray.getI成交次数());
		i游标 = outputData(outputData最终, output,i游标);

		return outputData最终;
	}

	private int outputData(byte[] outputData最终, byte[] output, int i游标) {
		for(byte out : output) {
			outputData最终[i游标]=out;
			i游标++;
		}
		return i游标;
	}



}
