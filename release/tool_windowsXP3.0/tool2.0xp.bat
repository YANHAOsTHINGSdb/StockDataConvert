echo off
rem ============================================================
rem
rem    网络数据转飞虎格式数据的工具
rem
rem ============================================================

rem chcp 936
rem -------------------------------------------------------------
rem		首先读取Property文件里的信息
rem -------------------------------------------------------------
for /f "delims== tokens=1,2" %%G in (StockDataConvert.property) do ( 

	set %%G=%%H
	rem echo %%G%
	rem echo %%H%

	if downloadStartDate == %%G ( set sDownloadStartDate=%%H )
	if downloadEndDate == %%G ( set sDownloadEndDate=%%H )
	if URL == %%G ( set sURL=%%H )
	if downloadSavedPath == %%G ( set sLocalPath=%%H )
	if SH_CodeListPath == %%G ( set sSH_CodeListPath=%%H )
	if SZ_CodeListPath == %%G ( set sSZ_CodeListPath=%%H )
)

set "sDownloadStartDate=%sDownloadStartDate: =%"
set "sDownloadEndDate=%sDownloadEndDate: =%"
set "sURL=%sURL: =%"
set "sLocalPath=%sLocalPath: =%"
set "sSH_CodeListPath=%sSH_CodeListPath: =%"
set "sSZ_CodeListPath=%sSZ_CodeListPath: =%"

echo sDownloadStartDate=%sDownloadStartDate%
echo sDownloadEndDate=%sDownloadEndDate%
echo sURL=%sURL%
echo sLocalPath=%sLocalPath%
echo sSH_CodeListPath=%sSH_CodeListPath%
echo sSZ_CodeListPath=%sSZ_CodeListPath%

rem -------------------------------------------------------------
rem		然后对每个信息进行CHK
rem -------------------------------------------------------------

rem 如果没有输入【开始时间】，取系统时间
if "%sDownloadStartDate%"=="" (
	set sDownloadStartDate=%date:~0,4%%date:~5,2%%date:~8,2%
)
echo sDownloadStartDate=%sDownloadStartDate%


rem 如果没有输入【终了时间】，取系统时间
if "%sDownloadEndDate%"=="" (
	set sDownloadEndDate=%date:~0,4%%date:~5,2%%date:~8,2%
)
echo sDownloadEndDate=%sDownloadEndDate%

rem 如果没有输入【本地保存路径】，处理终止
echo sLocalPath=%sLocalPath%
if "%sLocalPath%"=="" (
	rem 如果没有输入【本地保存路径】(LocalPath)，处理终止
	exit 0
) else (
	rem 删除本地路径下的所有文件，确保下载无障碍
rem	if exist %sLocalPath% ( 
rem		rd /s /Q %sLocalPath% 
rem	) 
	if not exist %sLocalPath% (
		md %sLocalPath%
	)
)

rem 如果没有输入【下载URL】，处理终止
if "%sURL%"=="" (
	rem 如果没有输入【本地保存路径】(URL)，处理终止
	exit 0
) else (
	echo sURL=%sURL%
)

rem -------------------------------------------------------------
rem		由于SH、SZ股票代码文件是分开的。所以下载要分开进行
rem -------------------------------------------------------------

rem 如果没有输入【上海股票代码文件地址】，就不下载上海股票历史数据
if "%sSH_CodeListPath%"=="" (
	echo 因为没有输入【上海股票代码文件地址】，所以就不下载上海股票历史数据了
) else (
	rem for I in (%sSH_CodeListPath%) 
	if exist %sSZ_CodeListPath% (
		for /f "delims=" %%I in (%sSH_CodeListPath%) do (
			wget --user-agent="Mozilla/5.0 （Windows; U; Windows NT 6.1; en-US） AppleWebKit/534.16 （KHTML， like Gecko） Chrome/10.0.648.204 Safari/534.1#d6" -nv --tries=5 --timeout=5 -O "%sLocalPath%\%%I.csv" "%sURL%?code=0%%I&start=%sDownloadStartDate%&end=%sDownloadEndDate%&fields=TOPEN;HIGH;LOW;TCLOSE;VOTURNOVER;VATURNOVER"

			rem 1 second
			ping 192.0.2.2 -n 1 -w 1000 > nul
		)
	) else (
		echo 【上海股票代码文件不存在】
	)
)

rem 上海综合指数下载
wget --user-agent="Mozilla/5.0 （Windows; U; Windows NT 6.1; en-US） AppleWebKit/534.16 （KHTML， like Gecko） Chrome/10.0.648.204 Safari/534.1#d6" -nv --tries=5 --timeout=5 -O "%sLocalPath%\1A0001.csv" "%sURL%?code=0000001&start=%sDownloadStartDate%&end=%sDownloadEndDate%&fields=TOPEN;HIGH;LOW;TCLOSE;VOTURNOVER;VATURNOVER"

rem 如果没有输入【深圳股票代码文件地址】，就不下载深圳股票历史数据
if "%sSZ_CodeListPath%"=="" (
	echo 因为没有输入【深圳股票代码文件地址】，所以就不下载深圳股票历史数据了
) else (
	if exist %sSZ_CodeListPath% (
		for /f "delims=" %%I in (%sSZ_CodeListPath%) do (
			wget --user-agent="Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3 (.NET CLR 3.5.30729)" -nv --tries=5 --timeout=5 -O "%sLocalPath%\%%I.csv" "%sURL%?code=1%%I&start=%sDownloadStartDate%&end=%sDownloadEndDate%&fields=TOPEN;HIGH;LOW;TCLOSE;VOTURNOVER;VATURNOVER"
			
			rem 1 second
			ping 192.0.2.2 -n 1 -w 1000 > nul
		)
	) else (
		echo 【深圳股票代码文件不存在】
	)
)


rem -------------------------------------------------------------
rem		最后调用Java工具进行格式转换
rem -------------------------------------------------------------
rem StockDataConvert.jar 1 代表出力历史数据
rem StockDataConvert.jar   如果什么也不写，代表出力当天数据
StockDataConvert.exe

rem 感谢使用