@echo off
cd /d "%~dp0"
if not exist build mkdir build
javac -encoding UTF-8 -d build src\main\java\com\barber\*.java
if errorlevel 1 (
 echo JDK 17 veya ustu gerekli. Derleme basarisiz.
 pause
 exit /b 1
)
xcopy /E /I /Y src\main\resources\* build\ >nul
java -cp build com.barber.BarberApp
pause
