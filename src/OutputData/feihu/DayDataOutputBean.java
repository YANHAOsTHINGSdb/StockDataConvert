package OutputData.feihu;

import lombok.Data;

@Data
public class DayDataOutputBean {
	byte[] header;			// header：ffffffe2
	byte[] type;			// type：00000101
	byte[] stockCount;		// 股票数：00000001
	byte[] stockName;	// size=12 SZ002067空空
	byte[] stockChName;	// size=12 景兴纸业
	byte[] dataSize;		// 日线个数：00000001
	byte[] UTCtime;		// UTC time：5cc24a00
	byte[] f开盘; 			// 开盘：407ccccd
	byte[] f最高; 			// 最高
	byte[] f最低; 			// 最低
	byte[] f收盘; 			// 收盘
	byte[] f成交量; 			// 成交量
	byte[] f成交金额; 			// 成交金额
	byte[] i成交次数; 				// 成交次数
}
