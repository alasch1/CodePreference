@ECHO ON

REM		Hard coded information
SET JAVA_HOME=%JDK_8%
SET SBT_COMMAND_TEST=test
SET SBT_COMMAND=dist


REM		Execute the SBT tests. (temporary commented out)
::	echo Execute the SBT tests.
::	CALL sbt.bat %SBT_COMMAND_TEST%
::	if %ERRORLEVEL% NEQ 0 GOTO BAD_EXECUTION
::	echo OK.
::	echo.


REM		Execute the SBT.
echo Execute the SBT.
CALL sbt.bat %SBT_COMMAND%
if %ERRORLEVEL% NEQ 0 GOTO BAD_EXECUTION
echo OK.
echo.



REM		Exit OK.
exit /b 0


:BAD_EXECUTION
echo.
echo Error while executing the activator.
echo.
exit /b 2

