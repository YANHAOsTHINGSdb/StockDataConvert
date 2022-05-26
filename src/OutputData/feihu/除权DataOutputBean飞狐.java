package OutputData.feihu;

import lombok.Data;

@Data
public class 除权DataOutputBean飞狐 {
	private byte[] header;			// header : d6ff ffff
	private byte[] type;			// type   : 0000 0001
	private byte[] stockCount;		// 股票数  : 0000 0001

	private byte[] start;			// size=8 : ffff ffff
	private byte[] stockCode;		// size=24 : 535a 3030 3030 3031 0000 0000
	private byte[] 除权个数;			// 除权个数  : 0000 0001

	private byte[] UTCtime;			// 除权日    : 5cc2 4a00
	private byte[] 送股;				// 送股   : 0000 0000 * 10
	private byte[] 配股;				// 配股   : 0000 003F * 10
	private byte[] 配股价;			// 配股价 : 0000 0041 * 1
	private byte[] 分红;				// 分红          : cdcc 4c3e * 10

}
