import json

with open("output/output.json") as f:
    output_polo = json.load(f)

with open("output/output_plagiarised.json") as f:
    output_moss = json.load(f)

output_data = []
for obj in output_polo:
    output_data.append({"source": obj["file1"]["file_name"], "target": obj["file2"]["file_name"], "value": obj["file1"]["similarity_percent"]})

with open("links.json", 'w') as f:
    json.dump(output_data, f)