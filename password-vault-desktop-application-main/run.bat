@echo off
echo --- Starting Password Vault Desktop Application ---
echo.
mvn compile exec:java
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] There was a problem running the application.
    echo Make sure Maven and Java are in your PATH.
    pause
)
