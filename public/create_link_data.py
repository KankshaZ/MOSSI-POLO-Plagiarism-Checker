import json

with open("public/output/output1.json") as f:
    output_moss = json.load(f)

with open("public/output/output2.json") as f:
    output_polo = json.load(f)

output_data = []
for obj in output_polo:
    output_data.append({"source": obj["file1"]["file_name"], "target": obj["file2"]["file_name"], "value": obj["file1"]["similarity_percent"]})

with open("links.json", 'w') as f:
    json.dump(output_data, f)