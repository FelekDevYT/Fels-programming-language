@echo off
echo Installing Java temurin 21...
PowerShell -Command "Invoke-WebRequest -Uri https://api.adoptium.net/v3/binary/latest/21/ga/windows/x64/jdk/hotspot/normal/adoptium?project=jdk -OutFile temurin-21.zip"
echo Setting up Expand Archive
PowerShell -Command "Expand-Archive -Path temurin-21.zip -DestinationPath 'C:\Program Files\Java\Temurin-21'"
echo Setting up ItemProperty
PowerShell -Command "Set-ItemProperty -Path 'HKLM:\SYSTEM\CurrentControlSet\Control\Session Manager\Environment' -Name 'JAVA_HOME' -Value 'C:\Program Files\Java\Temurin-21'"
echo Setting up Java variables
PowerShell -Command "[Environment]::SetEnvironmentVariable('Path', [Environment]::GetEnvironmentVariable('Path', [EnvironmentVariableTarget]::Machine) + ';C:\Program Files\Java\Temurin-21\bin', [EnvironmentVariableTarget]::Machine)"
echo Java Temurin 21 has been installed and environment variables set.
echo Writting up Fels package
setx PATH "%PATH%;%cd%;\fels.bat"
echo Successfull writting up fels package
pause
