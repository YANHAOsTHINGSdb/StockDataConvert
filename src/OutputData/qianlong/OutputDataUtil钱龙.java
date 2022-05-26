package OutputData.qianlong;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import OutputData.OutputDataUtil;
import OutputData.OutputDataUtil爸爸;
import OutputData.feihu.DayDataOutputBean;
import OutputData.feihu.财务DataOutputBean飞狐;

public class OutputDataUtil钱龙 extends OutputDataUtil爸爸 implements OutputDataUtil {

	static public byte[] getOutputData(String string1, String string2, String string3, String string4, String string5,
			String string6, String string7) {
		//--------------------------
		// 先将读入的字符串转成int的数组
		//--------------------------
		int[] inputDataArray= {
			Integer.parseInt(string1)
			,Integer.parseInt(string2)
			,Integer.parseInt(string3)
			,Integer.parseInt(string4)
			,Integer.parseInt(string5)
			,Integer.parseInt(string6)
			,Integer.parseInt(string7)
		};
		return createOutputData(inputDataArray);
	}

	public byte[] getOutputData(String[] datas, DayDataOutputBean dayDataOutputBean飞狐) {
		//--------------------------
		// 先将读入的字符串转成int的数组
		//--------------------------
		int[] inputDataArray= {
			Integer.parseInt(datas[0])
			,(int)(Float.parseFloat(datas[1]) * 1000)
			,(int)(Float.parseFloat(datas[2]) * 1000)
			,(int)(Float.parseFloat(datas[4]) * 1000) //
			,(int)(Float.parseFloat(datas[3]) * 1000) //
			,Integer.parseInt(datas[5])
			,Integer.parseInt(datas[6])
		};
		return createOutputData(inputDataArray);
	}

	private static byte[] createOutputData(int[] inputDataArray) {
		//--------------------------
		// 把每个int转成byte
		// 将转成byte做倒序
		// 最后进行重组
		//--------------------------
		byte[] outputData = new byte[40];
		int i = 0;
		for(int inputData : inputDataArray) {
			byte[] output = convertInttoByte(inputData);
			for(byte out : output) {
				outputData[i]=out;
				i++;
			}
		}
		return outputData;
	}

	// java的二进制存储是倒着的
	// 所以要想吧东西原封不动的转移给C语言的程序，
	// 就需要倒叙输出。
	static private byte[] 倒叙(int inputData) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(inputData);
		return buffer.array();
	}

	@Override
	public byte[] getOutputData财务(String[] sData, 财务DataOutputBean飞狐 财务dataOutputBean飞狐) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
