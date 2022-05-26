package OutputData.feihu;

import ConvertTool.impl.PROPERTY;
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

	public byte[] getOutputData(String[] datas, DayDataOutputBean dayDataOutputBean飞狐) {
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
		DayDataInputBean飞狐 dayDataBean飞狐 = new DayDataInputBean飞狐();
		dayDataBean飞狐.setF开盘(Float.parseFloat(datas[1]));
		dayDataBean飞狐.setF最高(Float.parseFloat(datas[2]));
		dayDataBean飞狐.setF最低(Float.parseFloat(datas[3]));
		dayDataBean飞狐.setF收盘(Float.parseFloat(datas[4]));
		// 【日线】与【深圳综合】的成交量要除以100
		if(PROPERTY.取得解析方案().equals("2")) {
			dayDataBean飞狐.setF成交量(Float.parseFloat(datas[5]));
		}else {
			dayDataBean飞狐.setF成交量(Float.parseFloat(datas[5])/100);
		}


		dayDataBean飞狐.setF成交金额(Float.parseFloat(datas[6]));

		dayDataBean飞狐.setDataSize(0x1);
		dayDataBean飞狐.setHeader(0xffffffe2);
		dayDataBean飞狐.setI成交次数(0x0);
		if(PROPERTY.取得解析方案().equals("2")) {
			dayDataBean飞狐.setStockChName(取得市场代号2(datas[9]).concat(datas[7]).toCharArray());
		}else {
			dayDataBean飞狐.setStockChName(取得市场代号(datas[7]).concat(datas[7]).toCharArray());
		}

		// 因为飞狐软件将上海综合指数视为【SH1A0001】
		// 但是下载的数据为【000001】
		if(datas[8].equals("上证指数") && (datas[7].replace("'", "").equals("000001"))){
			dayDataBean飞狐.setStockChName("SH1A0001".toCharArray());
			dayDataBean飞狐.setF成交量(Float.parseFloat(datas[5]));
		}

		dayDataBean飞狐.setStockCount(0x1);
		dayDataBean飞狐.setStockName(datas[8].toCharArray());
		dayDataBean飞狐.setType(0x00000101);
		dayDataBean飞狐.setUTCtime(getUTCtime(datas[0].concat(" 09:00:00")));

		return createOutputData(dayDataBean飞狐, dayDataOutputBean飞狐);
	}

	public byte[] getOutputData财务(String[] sData, 财务DataOutputBean飞狐 财务dataOutputBean飞狐) {
		// 市场 证券代码 日期 权息数据类别 派息金额 配股价 送股数 配股数
		财务DataInputBean飞狐 财务dataInputBean飞狐 = new 财务DataInputBean飞狐();



		财务dataInputBean飞狐.set市场(sData[0]);
		财务dataInputBean飞狐.set证券代码(sData[1]);
		财务dataInputBean飞狐.set流通股本(Float.parseFloat(sData[2]));
		财务dataInputBean飞狐.set所属省份(Float.parseFloat(sData[3]));
		财务dataInputBean飞狐.set所属行业(Float.parseFloat(sData[4]));
		财务dataInputBean飞狐.set账务更新日期(getUTCtime(sData[5].concat(" 08:00:00")));
		财务dataInputBean飞狐.set上市日期(Float.parseFloat(sData[6]));
		财务dataInputBean飞狐.set总股本(Float.parseFloat(sData[7]));
		财务dataInputBean飞狐.set国家股(Float.parseFloat(sData[8]));
		财务dataInputBean飞狐.set发起人法人股(Float.parseFloat(sData[9]));
		财务dataInputBean飞狐.set法人股(Float.parseFloat(sData[10]));
		财务dataInputBean飞狐.setB股(Float.parseFloat(sData[11]));
		财务dataInputBean飞狐.setH股(Float.parseFloat(sData[12]));
		财务dataInputBean飞狐.set职工股(Float.parseFloat(sData[13]));
		财务dataInputBean飞狐.set总资产(Float.parseFloat(sData[14])/10);
		财务dataInputBean飞狐.set流动资产(Float.parseFloat(sData[15])/10);
		财务dataInputBean飞狐.set固定资产(Float.parseFloat(sData[16])/10);
		财务dataInputBean飞狐.set无形资产(Float.parseFloat(sData[17])/10);
		财务dataInputBean飞狐.set股东人数(Float.parseFloat(sData[18]));
		财务dataInputBean飞狐.set流动负债  (Float.parseFloat(sData[19])/10);
		财务dataInputBean飞狐.set长期负债(Float.parseFloat(sData[20])/10);
		财务dataInputBean飞狐.set资本公积金(Float.parseFloat(sData[21])/10);
		财务dataInputBean飞狐.set净资产(Float.parseFloat(sData[22])/10);
		财务dataInputBean飞狐.set主营收入(Float.parseFloat(sData[23])/10);
		财务dataInputBean飞狐.set主营利润(Float.parseFloat(sData[24])/10);
		财务dataInputBean飞狐.set应收帐款(Float.parseFloat(sData[25])/10);
		财务dataInputBean飞狐.set营业利润(Float.parseFloat(sData[26])/10);
		财务dataInputBean飞狐.set投资收益(Float.parseFloat(sData[27])/10);
		财务dataInputBean飞狐.set经营现金流(Float.parseFloat(sData[28])/10);
		财务dataInputBean飞狐.set总现金流(Float.parseFloat(sData[29])/10);
		财务dataInputBean飞狐.set存贷(Float.parseFloat(sData[30]));
		财务dataInputBean飞狐.set利润总额(Float.parseFloat(sData[31])/10);
		财务dataInputBean飞狐.set税后利润(Float.parseFloat(sData[32])/10);
		财务dataInputBean飞狐.set净利润(Float.parseFloat(sData[33])/10);
		财务dataInputBean飞狐.set未分利润(Float.parseFloat(sData[34])/10);
		财务dataInputBean飞狐.set保留(Float.parseFloat(sData[35]));
		财务dataInputBean飞狐.set保留1(Float.parseFloat(sData[36]));
		财务dataInputBean飞狐.set股东权益(财务dataInputBean飞狐.get净资产());
		//float f净资产 = 财务dataInputBean飞狐.get净资产();
		//float f总股本 = 财务dataInputBean飞狐.get总股本();
		//财务dataInputBean飞狐.set每股净资(f净资产/f总股本);

		财务dataInputBean飞狐.set每股净资(财务dataInputBean飞狐.get净资产()/财务dataInputBean飞狐.get总股本());
		财务dataInputBean飞狐.set每股公积金(财务dataInputBean飞狐.get资本公积金()/财务dataInputBean飞狐.get总股本());
		财务dataInputBean飞狐.set每股收益(财务dataInputBean飞狐.get净利润()/财务dataInputBean飞狐.get总股本());
		财务dataInputBean飞狐.set每股未分配(财务dataInputBean飞狐.get未分利润()/财务dataInputBean飞狐.get总股本());


		return createOutputData财务(财务dataInputBean飞狐, 财务dataOutputBean飞狐);
	}


	private String 取得市场代号2(String s市场代号) {
		if(s市场代号.equals("0")) {
			return "SZ";
		}
		return "SH";
	}


	/**
	 *
	 * @param inputDataArray
	 * @return
	 */
	private byte[] createOutputData(DayDataInputBean飞狐 inputDataArray, DayDataOutputBean dayDataOutputBean飞狐) {
		//--------------------------
		// 把每个int转成byte
		// 将转成byte做倒序
		// 最后进行重组
		//--------------------------
		byte[] outputData最终 = new byte[32];
		byte[] output = null;
		int i游标 = 0;

		// int header;			// header：ffffffe2
		//output = 倒叙(inputDataArray.getHeader());
		output = convertInttoByte(inputDataArray.getHeader());
		dayDataOutputBean飞狐.setHeader(output);
//		i游标 = outputData(outputData最终, output,i游标);

		// int type;			// type：00000101
		output = convertInttoByte(inputDataArray.getType());
		dayDataOutputBean飞狐.setType(output);
//		i游标 = outputData(outputData最终, output,i游标);

		// int stockCount;		// 股票数：00000001
		output = convertInttoByte(inputDataArray.getStockCount());
		dayDataOutputBean飞狐.setDataSize(output);
//		i游标 = outputData(outputData最终, output,i游标);

		// char stockName[];	// size=12 SZ002067空空
		output = convertChartoByte(inputDataArray.getStockChName());
		dayDataOutputBean飞狐.setStockName(output);
//		i游标 = outputData(outputData最终, output,i游标);

		// char stockChName[];	// size=12 景兴纸业
		output = convertGB2312ChartoByte(inputDataArray.getStockName());
		dayDataOutputBean飞狐.setStockChName(output);
//		i游标 = outputData(outputData最终, output,i游标);

		// int dataSize;		// 日线个数：00000001
		output = convertInttoByte(inputDataArray.getDataSize());
		dayDataOutputBean飞狐.setDataSize(output);
//		i游标 = outputData(outputData最终, output,i游标);

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



	/**
	 *
	 * @param 除权dataInputBean飞狐
	 * @param 除权dataOutputBean飞狐
	 * @return
	 */
	private byte[] createOutputData财务(财务DataInputBean飞狐 财务dataInputBean飞狐, 财务DataOutputBean飞狐 财务dataOutputBean飞狐) {
		/**
		 * 	UTCtime;		// 除权日 : 5cc2 4a00
			送股;			// 送股   : 0000 0000 * 10
			配股;			// 配股   : 0000 003F * 10
			配股价;			// 配股价 : 0000 0041 * 1
			分红;			// 分红   : cdcc 4c3e * 10
		 */
		//--------------------------
		// 把每个int转成byte
		// 将转成byte做倒序
		// 最后进行重组
		//--------------------------
		/**
		 * 	0076 773f
			0000 0000
			0000 0000
			0000 0000
			9a99 193e
		 */
		byte[] outputData最终 = {};
		byte[] output = null;
		output =convertInttoByte(0xffffffd7);
		财务dataOutputBean飞狐.setHeader(output);

		output =convertInttoByte(0x00000101);
		财务dataOutputBean飞狐.setType(output);

		output =convertInttoByte(0x00000000);
		财务dataOutputBean飞狐.set空白(output);


		财务dataInputBean飞狐.setStockCode(取得市场代号2(财务dataInputBean飞狐.get市场()).concat(财务dataInputBean飞狐.get证券代码()).toCharArray());
		output = convertChartoByte(财务dataInputBean飞狐.getStockCode());
		财务dataOutputBean飞狐.set股票代码(output);

		output =convertInttoByte(财务dataInputBean飞狐.get账务更新日期());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get总股本());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get国家股());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get发起人法人股());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get法人股());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.getB股());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.getH股());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get流通股本());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get职工股());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.getA2转配股());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get总资产());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get流动资产());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get固定资产());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get无形资产());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get长期投资());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get流动负债());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get长期负债());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get资本公积金());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get每股公积金());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get股东权益());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get主营收入());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get主营利润());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get其他利润());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get营业利润());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get投资收益());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get补贴收入());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get营业外收支());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get损益调整());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get利润总额());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get税后利润());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get净利润());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get未分配利润());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get每股未分配());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get每股收益());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get每股净资());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get调整每股净资());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get股东权益比率());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get净资收益率());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get经营现金流入());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get经营现金流出());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get经营现金流量());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get投资现金流入());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get投资现金流出());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get投资现金流量());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get筹资现金流入());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get筹资现金流出());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get筹资现金流量());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get现金及等价物());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get应收帐款周转率());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get存活周转率());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get股东总数());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get发行价());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get发行量());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get主营业务增长率());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get税后利润增长率());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get净资产增长率());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		output = convertFloattoByte(财务dataInputBean飞狐.get总资产增长率());
		outputData最终 = OutputDataUtil爸爸.数组合并2(outputData最终, output);
		return outputData最终;
	}

}
