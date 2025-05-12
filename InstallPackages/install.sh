#!/bin/bash

echo "Installing Java Temurin 21..."
curl -L -o temurin-21.zip "https://api.adoptium.net/v3/binary/latest/21/ga/linux/x64/jdk/hotspot/normal/adoptium?project=jdk"
echo "Setting up Expand Archive"
unzip temurin-21.zip -d /usr/local/java/temurin-21
echo "Setting up JAVA_HOME"
echo "export JAVA_HOME=/usr/local/java/temurin-21" >> ~/.bashrc
echo "export PATH=\$JAVA_HOME/bin:\$PATH" >> ~/.bashrc
source ~/.bashrc
echo "Java Temurin 21 has been installed and environment variables set."
echo "Writing up Fels package"
echo "export PATH=\$PATH:\$PWD" >> ~/.bashrc
echo "Successfully written up fels package"
echo "Fels successfully installed on your PC"
