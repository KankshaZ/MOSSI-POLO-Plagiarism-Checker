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
# # perl moss.pl temp/A1.java temp/A2.java temp/A3.java temp/A4.java temp/A5.java temp/A6.java temp/A7.java temp/A8.java temp/A9.java temp/Master.java > MOSS.txt
# # perl moss.pl out/A1.java out/A2.java out/A3.java out/A4.java out/A5.java out/A6.java out/A7.java out/A8.java out/A9.java out/Master.java > POLO.txt
# perl moss.pl out/assignment_20.java out/assignment_19.java out/assignment_18.java out/assignment_17.java out/assignment_16.java out/assignment_15.java out/assignment_14.java out/assignment_13.java out/assignment_12.java out/assignment_11.java > POLO.txt
# perl moss.pl dataset1/assignment_20.java dataset1/assignment_19.java dataset1/assignment_18.java dataset1/assignment_17.java dataset1/assignment_16.java dataset1/assignment_15.java dataset1/assignment_14.java dataset1/assignment_13.java dataset1/assignment_12.java dataset1/assignment_11.java > MOSS.txt


# import json

# with open("public/output/output2.json") as f:
#     output_polo = json.load(f)

# output_data = []
# for obj in output_polo:
#     output_data.append({"source": obj["file1"]["file_name"], "target": obj["file2"]["file_name"], "value": obj["file1"]["similarity_percent"]})

# with open("links.json", 'w') as f:
#     json.dump(output_data, f)