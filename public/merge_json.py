import json

with open("public/output/output2.json") as f:
    output_polo = json.load(f)

with open("public/output/output1.json") as f:
    output_moss = json.load(f)

files = set()
for obj in output_polo:
    files.add(obj["file1"]["file_name"])
    files.add(obj["file2"]["file_name"])

polo_lookup = dict()
for obj in output_polo:
    polo_lookup[(obj["file1"]["file_name"], obj["file2"]["file_name"])] = obj["file1"]
    polo_lookup[(obj["file2"]["file_name"], obj["file1"]["file_name"])] = obj["file2"]

moss_lookup = dict()
for obj in output_moss:
    moss_lookup[(obj["file1"]["file_name"], obj["file2"]["file_name"])] = obj["file1"]
    moss_lookup[(obj["file2"]["file_name"], obj["file1"]["file_name"])] = obj["file2"]

master_copy = "Master.java"
output_data = []
for f in files:
    if f == master_copy:
        continue
    output_data.append({"categorie": f, "values": [{"value": polo_lookup[(f, master_copy)]["similarity_percent"], "rate": "POLO"}, {"value": moss_lookup[(f, master_copy)]["similarity_percent"], "rate": "MOSS"}]})

with open("output_processed.json", 'w') as f:
    json.dump(output_data, f)
