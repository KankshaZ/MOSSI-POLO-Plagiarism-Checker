import requests
from bs4 import BeautifulSoup
from csv import writer
import time
from datetime import datetime
import sys
from pprint import pprint
import json

BASE_URL = "http://moss.stanford.edu/results/"

def build_file_structure(col_tag):
    file_strucure = {}
    file_text = col_tag.a.text
    file_strucure["file_name"] = file_text.split("(")[0].strip()
    file_strucure["similarity_percent"] = file_text.split("(")[1].split("%")[0]
    return file_strucure

def get_moss_output(fname, suffix):
    # To calculate the runtime of the program
    start_time = time.time()
    
    headers = {"User-Agent": "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, "
                             "like Gecko) Chrome/77.0.3865.90 Mobile Safari/537.36"}
    
    # request_code = "625087940"
    # request_code = "997036419"
    # request_code = "727330323"
    # request_code = "956166494"
    with open(fname, 'rb') as fh:
        # for line in fh:
        #     line = fh.read()
        # url = str(line)
        # url = line.replace('\n', '')
        # print (list(fh)[-1])
        url = list(fh)[-1].decode("utf-8").replace('\n', '')
    # url = BASE_URL + request_code
    
    print(url)
    
    res = requests.get(url, headers=headers)  # Get the response    
    soup = BeautifulSoup(res.text, features="lxml")  # Get the html page

    all_files = [] # stores the dictionary of all the 2-file combination (len = nC2)

    table_row_list = soup.find("table").find_all("tr") # Get the list of all the rows in table
    for row in table_row_list[1:]: # Skip the first row (head of the table)
        col = row.find_all("td") # get the entire column list {file1, file2, similarity}

        per_two_file_dict = {}    

        lines_matched = col[2].text[:-1]
        per_two_file_dict["lines_matched"] = lines_matched

        # Get the common lines page
        url_to_vis_page = col[0].a['href'].split("/")[-1]
        idx = url_to_vis_page.index(".")
        url_to_vis_page = url + "/" + url_to_vis_page[:idx] + "-top" + url_to_vis_page[idx:]
        res_next = requests.get(url_to_vis_page, headers=headers)  # Get the response    
        soup_lines_common = BeautifulSoup(res_next.text, features="lxml")  # Get the html page
        
        copied_lines_table = soup_lines_common.find("table").find_all("tr")[1:]
        files_copied_lines = []
        for copy_row in copied_lines_table:
            # For each row of the table, we have 4 columns [0, 2 columns are file1 and file2 lines that are copied]
            copy_col = copy_row.find_all("td")
            files_tuple = (copy_col[0].text[:-1], copy_col[2].text[:-1])
            files_copied_lines.append(files_tuple)
        per_two_file_dict["copied_lines_list"] = files_copied_lines
        
        for i in range(2):
            per_two_file_dict["file" + str(i + 1)] = build_file_structure(col[i])
        
        all_files.append(per_two_file_dict)

    # return all_files
    # pprint(all_files[0])

    with open('public/output/output' + suffix + '.json', 'w') as out_file:
        json.dump(all_files, out_file)

    print("--Program run time: %s seconds" % round((time.time() - start_time)))


if __name__ == '__main__':
    fname = sys.argv[1]
    # print(fname)
    suffix = sys.argv[2]
    # print(suffix)
    get_moss_output(fname, suffix)
