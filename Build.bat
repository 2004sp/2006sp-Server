@echo off
setlocal EnableExtensions EnableDelayedExpansion
cd /d "%~dp0"
set "NO_PAUSE=0"
if /I "%~1"=="--no-pause" set "NO_PAUSE=1"

where javac >nul 2>&1
if errorlevel 1 (
    echo ERROR: javac was not found. Install a JDK and add its bin folder to PATH.
    if "%NO_PAUSE%"=="0" pause
    exit /b 1
)

where jar >nul 2>&1
if errorlevel 1 (
    echo ERROR: jar was not found. Install a JDK and add its bin folder to PATH.
    if "%NO_PAUSE%"=="0" pause
    exit /b 1
)

set "SOURCE_DIR=src\main\java"
set "LIB_DIR=lib"
set "BUILD_DIR=build"
set "CLASSES_DIR=%BUILD_DIR%\classes"
set "SOURCE_LIST=%BUILD_DIR%\sources.txt"
set "MANIFEST=%BUILD_DIR%\MANIFEST.MF"
set "DIST_DIR=dist"
set "OUTPUT_JAR=%DIST_DIR%\server.jar"
set "TEMP_JAR=%BUILD_DIR%\server.jar"

if exist "%BUILD_DIR%" rmdir /s /q "%BUILD_DIR%"
mkdir "%CLASSES_DIR%"
if errorlevel 1 goto :build_failed
if not exist "%DIST_DIR%" mkdir "%DIST_DIR%"
if errorlevel 1 goto :build_failed

for /r "%SOURCE_DIR%" %%F in (*.java) do (
    set "SOURCE_FILE=%%F"
    echo "!SOURCE_FILE:\=/!">>"%SOURCE_LIST%"
)

echo Compiling server...
javac -Xmaxerrs 5000 -classpath "%LIB_DIR%\*" -d "%CLASSES_DIR%" @"%SOURCE_LIST%"
if errorlevel 1 goto :build_failed

(
    echo Manifest-Version: 1.0
    echo Class-Path: ../lib/commons-compress-1.0.jar ../lib/javac++.jar ../lib/joda-time-2.3.jar ../lib/sqlite-jdbc-3.53.4.0.jar ../lib/slf4j-api-2.0.17.jar ../lib/slf4j-nop-2.0.17.jar
    echo Main-Class: com.rs2.launcher.ControlPanel
    echo.
)>"%MANIFEST%"

echo Packaging server...
jar cfm "%TEMP_JAR%" "%MANIFEST%" -C "%CLASSES_DIR%" .
if errorlevel 1 goto :build_failed

move /y "%TEMP_JAR%" "%OUTPUT_JAR%" >nul
if errorlevel 1 goto :build_failed

if "%NO_PAUSE%"=="0" ping 127.0.0.1 -n 2 >nul
rmdir /s /q "%BUILD_DIR%"
echo.
echo Build complete: %OUTPUT_JAR%
if "%NO_PAUSE%"=="0" pause
exit /b 0

:build_failed
echo.
echo ERROR: Build failed. Temporary output was left in "%BUILD_DIR%".
if "%NO_PAUSE%"=="0" pause
exit /b 1
