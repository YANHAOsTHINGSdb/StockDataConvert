package ConvertTool.impl;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.math.NumberUtils;

public class StockData爸爸 {

	String[] s数据格式扩展名 =
			new String[] {
					"txt", // 0:未知
					"DAY", // 1:钱龙
					"QDA", // 2:飞狐
					"day", // 3:通达信
					"DAD"  // 4:分析家
			};
	// 日期！，每次只取得一个日期的文件。
	String sDate = null;
	// 出力的路径
	// 因为sz与sh是在同一个文件里的。所以出力的时候要分开来
	String[] sStockPath =
			new String[] {
					PROPERTY.取得sh出力目录(),
					PROPERTY.取得sz出力目录()
			};

	/**
	 * 1=钱龙
	 * 2=飞狐
	 */
	static String sOutPutDataType =PROPERTY.取得出力数据格式();


	/**
	 *
	 * @param str
	 */
	protected boolean 判断是不是实际的数据(String[] s) {
		// 如果第一项不为数字，则是标题行，需要掠过
		// 下例的时候也会报错：
		// 603982	Ȫ������	--  	--  	--  	0.00	0	0
		// 所以要确保所有的数据都是数值的时候才放行
		if(
				NumberUtils.isDigits(s[0])
				&& s[2].matches("[-+]?[0-9]*\\.?[0-9]+")
				&& s[3].matches("[-+]?[0-9]*\\.?[0-9]+")
				&& s[4].matches("[-+]?[0-9]*\\.?[0-9]+")
				&& s[5].matches("[-+]?[0-9]*\\.?[0-9]+")
				&& NumberUtils.isDigits(s[6])
				&& NumberUtils.isDigits(s[7])

		) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param str
	 */
	public String 取得市场路径(String s股票代号) {
		if(s股票代号.charAt(0)=='6') {
			return sStockPath[0];
		}
		return sStockPath[1];
	}

	/**
	 *
	 * @param file
	 * @return
	 */
	protected static boolean checkBeforeReadfile(File file) {
		if (file.exists()) {
			if (file.isFile() && file.canRead()) {
				return true;
			}
		}
		return false;
	}

	protected void write(String sFileName,  byte[] byteData) {
		//ByteArrayOutputStream byteArrayOutputStream = null;
		DataOutputStream dataOutputStream = null;
	      try{

	    	  FileOutputStream fos = new FileOutputStream(sFileName);

	          // create byte array output stream
	    	  // byteArrayOutputStream = new ByteArrayOutputStream();

	          // create data output stream
	    	  dataOutputStream = new DataOutputStream(fos);

	          // write to the stream from integer array
	    	  dataOutputStream.write(byteData);

	          // flushes bytes to underlying output stream
	    	  dataOutputStream.flush();

	          System.out.println("over");

	       }catch(Exception e){
	          // if any error occurs
	          e.printStackTrace();
	       }finally{

	          if(dataOutputStream!=null)
				try {
					dataOutputStream.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
	       }
	}
}
