@echo off

REM IF ERRORLEVEL 0 will return TRUE whether the errorlevel is 0, 1 or 5 or 64 
REM IF NOT ERRORLEVEL 3 means if ERRORLEVEL is less than 3 ( 2, 1, 0 or a negative number)
REM To check for a specific error level N, you can use the following construct:
REM IF ERRORLEVEL N IF NOT ERRORLEVEL N+1 COMMAND
REM A preferred method of checking Errorlevels is to use the %ERRORLEVEL% variable:
REM IF %ERRORLEVEL% NEQ 0 Echo An error was found
REM Information found in: https://ss64.com/nt/errorlevel.html

::  # check prerequistes
mvn -version | find "apache-maven" > nul
if %errorlevel% neq 0 (
   echo There is no maven installed. Apache Maven 3 is required to run.
   pause
   exit
)
mvn -version | find "apache-maven-3" > nul
if %errorlevel% neq 0 (
   echo Apache Maven 3 is required to run. A lower version is installed.
   pause
   exit
)

:: # run test and create allure report files 
call mvn test allure:report

:: # create allure report
call mvn allure:aggregate

:: # show allure report
echo Copy .\target\site\allure-maven-plugin\history to .\target\allure-results\history
xcopy .\target\site\allure-maven-plugin\history .\target\allure-results\history /Y /Q /I /S
call mvn allure:serve
