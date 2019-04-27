package OutputData.feihu;

import lombok.Data;

@Data
public class DayDataBean飞狐  implements java.io.Serializable {
	int header;			// header：ffffffe2
	int type;			// type：00000101
	int stockCount;		// 股票数：00000001
	char stockName[];	// size=12 SZ002067空空
	char stockChName[];	// size=12 景兴纸业
	int dataSize;		// 日线个数：00000001
	int UTCtime;		// UTC time：5cc24a00
	float f开盘; 			// 开盘：407ccccd
	float f最高; 			// 最高
	float f最低; 			// 最低
	float f收盘; 			// 收盘
	float f成交量; 			// 成交量
	float f成交金额; 			// 成交金额
	int i成交次数; 				// 成交次数
}
