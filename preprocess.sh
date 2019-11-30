#!/usr/bin/env bash
mkdir -p temp
mkdir -p out
root="$1"
for path in "$root"/*.java
do
	filename=`basename "$path"`
	java -jar ./google-java-format-1.7-all-deps.jar "$path" > "temp/$filename"
	errors=`./pmd-bin-6.20.0/bin/run.sh pmd -d "temp/$filename" -R rules.xml 2> /dev/null`
	numbers=`echo "$errors" | grep -Po ':\d+' | sed 's/://' | sed 's/\([0-9]\+\)/\1d;/'`
	sed -i "$numbers" "temp/$filename"
	java PreProcessing "temp/$filename" "out/$filename"
done