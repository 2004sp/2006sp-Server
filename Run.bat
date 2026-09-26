@echo off
setlocal
cd /d "%~dp0"

if not defined PRS_AUDIT443 set "PRS_AUDIT443=true"

rem Promote the fixed revision-443 server after the previous process releases server.jar.
if exist "dist\server-totallevelfix.jar" move /y "dist\server-totallevelfix.jar" "dist\server.jar" >nul

where java >nul 2>&1
if errorlevel 1 (
    echo ERROR: java was not found. Install Java and add its bin folder to PATH.
    pause
    exit /b 1
)

if not exist "dist\server.jar" (
    echo ERROR: dist\server.jar was not found. Run Build.bat first.
    pause
    exit /b 1
)

echo Revision 443 audit: %PRS_AUDIT443%
java -Xmx1024m -Dprs.audit443=%PRS_AUDIT443% -Dprs.traceGameplay=true -jar "dist\server.jar"
set "EXIT_CODE=%ERRORLEVEL%"
echo.
echo Server exited with code %EXIT_CODE%.
pause
exit /b %EXIT_CODE%
