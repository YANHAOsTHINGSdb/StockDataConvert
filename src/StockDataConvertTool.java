import org.apache.commons.lang3.StringUtils;

import ConvertTool.impl.PROPERTY;
import ConvertTool.impl.StockDataHistoryDayConvertTool;
import ConvertTool.impl.StockDataOneDayConvertTool;

public class StockDataConvertTool {

	public static void main(String[] args) {

		String s方案 = null;
		if(args.length==0 || StringUtils.isEmpty(args[0])) {
			// 如果没有入力值，就走【0】方案
			s方案 = "0";
		}else {
			s方案 = args[0];
		}
		
		switch(s方案) {
		case "0":
			new StockDataOneDayConvertTool().输出到文件(PROPERTY.取得入力文件路径());
			break;
		case "1":
			new StockDataHistoryDayConvertTool().输出到文件(PROPERTY.取得下载数据保存路径());
			break;
		}
	}

}
