@echo off
echo Installing Java Temurin 21...
curl -L -o temurin-21.zip "https://api.adoptium.net/v3/binary/latest/21/ga/windows/x64/jdk/hotspot/normal/adoptium?project=jdk"

echo Setting up Expand Archive...
powershell -Command "Expand-Archive -Path temurin-21.zip -DestinationPath C:\java\temurin-21"

echo Setting up JAVA_HOME...
setx JAVA_HOME "C:\java\temurin-21"
setx PATH "%JAVA_HOME%\bin;%PATH%"

echo Java Temurin 21 has been installed and environment variables set.

echo Writing up Fels package...
setx PATH "%PATH%;%CD%"

echo Successfully written up Fels package.
echo Fels successfully installed on your PC.
pause
