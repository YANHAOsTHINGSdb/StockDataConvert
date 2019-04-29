@echo off
setlocal
echo 转换开始

java -jar StockDataConvert.jar


echo 转换结束
endlocal

timeout 10 /nobreak