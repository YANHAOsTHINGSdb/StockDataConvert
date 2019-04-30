import org.apache.commons.lang3.StringUtils;

import com.mycompany.common.PROPERTY;

import ConvertTool.impl.StockDataHistoryDayConvertTool;
import ConvertTool.impl.StockDataOneDayConvertTool;

public class StockDataTool {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		if(StringUtils.isEmpty(args[0])) {

		}
		switch(args[0]) {
		case "0":
			new StockDataOneDayConvertTool().输出到文件(PROPERTY.取得入力文件路径());
			break;
		case "1":
			new StockDataHistoryDayConvertTool().输出到文件(PROPERTY.取得下载数据保存路径());
			break;
		}
	}

}
