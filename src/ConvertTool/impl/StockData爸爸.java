package ConvertTool.impl;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
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
	String[] sDateDll = null;
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
	protected static boolean 判断是不是实际的数据(String[] s) {
		// 如果第一项不为数字，则是标题行，需要掠过
		// 下例的时候也会报错：
		// 603982	Ȫ������	--  	--  	--  	0.00	0	0
		// 所以要确保所有的数据都是数值的时候才放行
		if(
				NumberUtils.isDigits(s[0].replace("-","").replace("/",""))
				&& isFloat(s[3])
				&& isFloat(s[4])
				&& isFloat(s[5])
				&& isFloat(s[6])
				&& isFloat(s[7])
				&& isFloat(s[8])
				&& !isZore(s[7])

//				&& NumberUtils.isDigits(s[3])
//				&& NumberUtils.isDigits(s[4])
//				&& NumberUtils.isDigits(s[5])
//				&& NumberUtils.isDigits(s[6])
//				&& NumberUtils.isDigits(s[7])
//				&& NumberUtils.isDigits(s[8])

		) {
			return true;
		}
		return false;
	}

	private static boolean isZore(String str) {
		if(Float.parseFloat(str) == 0.0) {
			return true;
		}
		return false;
	}

	private static boolean isFloat(String str) {
		//check if float
	    try{
	        Float.parseFloat(str);
	    }catch(NumberFormatException e){
	    	return false;
	    }
		return true;
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

	public String 取得市场路径2(String s股票代号) {
		if(s股票代号.equals("1")) {
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

		//
		File file = new File(FilenameUtils.getFullPath(sFileName));

		if(!file.exists()) {
			file.mkdir();
		}

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

	protected static boolean 判断是不是实际的数据ForDLLData(String[] s) {
		/**
			市场		0【统一替换成 0:上证指数 1:深证指数】
			代码		1【代码】
			活跃度	2
			现价		3【收盘】
			昨收	4
			开盘		5【开盘】
			最高	6【最高】
			最低	7【最低】
			时间		8
			保留	9
			总量		10【成交量】
			现量		11
			总金额	12【成交额】
			内盘		13
			外盘		14
			保留	15
			保留	16
			买一价	17
			卖一价	18
			买一量	19
			卖一量	20
			买二价	21
			卖二价	22
			买二量	23
			卖二量	24
			买三价	25
			卖三价	26
			买三量	27
			卖三量	28
			买四价	29
			卖四价	30
			买四量	31
			卖四量	32
			买五价	33
			卖五价	34
			买五量	35
			卖五量	36
			保留	37
			保留	38
			保留	39
			保留	40
			保留	41
			涨速		42
			活跃度	43
		 */
		if((s[0].equals("1") && s[1].charAt(0)!='6')) {
			return false;
		}
		if((s[0].equals("0") && s[1].charAt(0)=='3')) {
			if(!s[1].equals("399001")) {
				return false;
			}
		}
		
		if(s[1].equals("002067")) {
			s[1] = "002067";
		}
		if((s[0].equals("0") || s[0].equals("1"))
				&& isFloat(s[3])
				&& isFloat(s[5])
				&& isFloat(s[6])
				&& isFloat(s[7])
				&& isFloat(s[10])
				&& isFloat(s[12])
				&& !isZore(s[10])
				&& !isZore(s[3])
				&& !isZore(s[5])
				&& !isZore(s[6])
				&& !isZore(s[7])
				
//				&& NumberUtils.isDigits(s[3])
//				&& NumberUtils.isDigits(s[4])
//				&& NumberUtils.isDigits(s[5])
//				&& NumberUtils.isDigits(s[6])
//				&& NumberUtils.isDigits(s[7])
//				&& NumberUtils.isDigits(s[8])

		) {
			return true;
		}
		return false;
	}
}
