package OutputData.TDX;

import lombok.Data;

@Data
public class DayDataInputBean通达信   implements java.io.Serializable {
	int date;   // e.g. 20100304
	int open;           // *0.01 开盘价
	int high;           // *0.01 最高价
	int low;            // *0.01 最低价
	int close;          // *0.01 收盘价
	float amount;        // 成交额
	int vol;             // 成交量(手)
	int reserved;
}
