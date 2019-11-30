#!/usr/bin/env bash
if [ -d "pmd-bin-6.20.0"]; then
	wget https://github.com/pmd/pmd/releases/download/pmd_releases%2F6.20.0/pmd-bin-6.20.0.zip
	unzip pmd-bin-6.20.0.zip
fi

if [ -d "google-java-format-1.7-all-deps.jar"]; then
	wget https://github.com/google/google-java-format/releases/download/google-java-format-1.7/google-java-format-1.7-all-deps.jar
fi
