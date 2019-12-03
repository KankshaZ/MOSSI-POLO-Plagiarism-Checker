# MOSSI-POLO-Plagiarism-Checker

## DESCRIPTION

MOSS (for a Measure Of Software Similarity) is a tool to detect plagiarism in files containing code. Our project MOSSI POLO (MOSS Ink Plagiarism and Language Optimization) aims to build a better plagiarism detector than current practice (MOSS) by introducing new processing steps. We aim to try 3 techniques of processing prior to sending files to MOSS, and analyse the improvements in our project. Apart from displaying copied areas of code in visually appealing manner (better than MOSS does), the improvements are displayed on charts depicting analysis of multiple techniques, and a graph is created with files as nodes, where the edges vary with the amount of plagiarism.

## INSTALLATION
- Download javaparser-core-3.6.11.jar
- Add it to your classpath (don't forget to `source .bashrc`)
Make sure you have Java installed.
- Clone the repository 

        git clone https://github.com/KankshaZ/MOSSI-POLO-Plagiarism-Checker.git

## EXECUTION
cd into repository     
    
    cd MOSSI-POLO-Plagiarism-Checker`
    
Run the following commands
    
    bash download_deps.sh`
    javac PreProcessing.java
    bash preprocess.sh dataset1	
    python3 -m http.server`

In your browser, go to 
    
    http://localhost:8000/public/bar.html``
    
Click 'next' to view the next visualisation.
