股票数据转换工具
	1、文本转钱龙格式tool.bat   把【沪深Ａ股20190415.txt】的数据转成钱龙格式数据
	2、下载转飞狐格式tool.ba	把从网上下载到的历史数据转成飞狐为主的格式数据
	
具体参考配置文件（stockDataConvert.property）


文件一览
---------------------------------------------
code/SH.txt					上海股票代码表
code/SZ.txt					深圳股票代码表
StockDataConvert.jar		转换工具
stockDataConvert.property	配置文件
文本转钱龙格式tool.bat		文本转钱龙格式工具
下载转飞狐格式tool.bat		下载转飞狐格式工具
download					下载的数据
StockDataConvert_lib		必要的辅助运行文件

	
说明：关于【文本转钱龙格式tool.bat】
	入力文件需要扩展名【txt】文件
	且文件名最后一定是【yyyyMMdd】形式的日期
		例、沪深Ａ股20190415.txt
		
	确保每行的书都是如下格顺序
	------------------------------------------------
	  代码  名称  今开  最高  最低  现价  总量  总金额
	------------------------------------------------


api-ms-win-*.dll			在纯XP环境下会报错。网上提示要加这么一堆东西
StockDataConvert.exe		在Win32环境下的主程序（转换工具）
StockDataConvert.jar		JAVA主程序
stockDataConvert.property	外部配置文件（具体见上面说明）
StockDataConvertTool.exe4j	JAR转EXE的配置文件
tool2.0.bat					下载+转换
tool2.1.bat					Only转换

exe4j工具的使用说明
https://www.jianshu.com/p/3d472e75634b

使用exe4j把jar转换成exe文件时，报错java.lang.NoClassDefFoundError: org/eclipse/swt/widgets/Composite
https://blog.csdn.net/jia611/article/details/42060945


exe做成手顺
1 export jar(runable) to localhost
2 2番目選択、生成されるJARに必須ラブラリーをパッケージ
3 exe4j main class に下記設定する
  org.eclipse.jdt.internal.jarinjarloader.JarRsrcLoader