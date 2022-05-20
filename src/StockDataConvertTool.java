import ConvertTool.impl.PROPERTY;
import ConvertTool.impl.StockDataHistoryDayConvertTool;
import ConvertTool.impl.StockDataOneDayConvertTool;

public class StockDataConvertTool {

	public static String s方案 = null;
	public static void main(String[] args) {

		s方案 = PROPERTY.取得解析方案();

		switch(s方案) {
		case "0":
			new StockDataOneDayConvertTool().输出到文件(PROPERTY.取得入力文件路径());
			break;
		case "1":
			new StockDataHistoryDayConvertTool().输出到文件(PROPERTY.取得下载数据保存路径());
			break;
		case "2":
			new StockDataOneDayConvertTool().输出到文件2();
			break;
		case "3":
			// 指定某头天的历史数据
			new StockDataOneDayConvertTool().输出到文件2();
			break;
		}
	}

}
