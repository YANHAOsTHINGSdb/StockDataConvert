echo off
rem ============================================================
rem
rem    网络数据转飞虎格式数据的工具
rem
rem ============================================================

rem -------------------------------------------------------------
rem		最后调用Java工具进行格式转换
rem -------------------------------------------------------------
rem StockDataConvert.jar 1 代表出力历史数据
rem StockDataConvert.jar   如果什么也不写，代表出力当天数据
java -jar StockDataConvert.jar 1

rem 感谢使用
timeout 10 /nobreak