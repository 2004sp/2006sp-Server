@echo off
setlocal
cd /d "%~dp0"

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

java -Xmx1024m -jar "dist\server.jar"
set "EXIT_CODE=%ERRORLEVEL%"
echo.
echo Server exited with code %EXIT_CODE%.
pause
exit /b %EXIT_CODE%
