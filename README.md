# COMP4321_2018Spring
* Crawler
* Indexer
* Test Program
## Crawler
compile:
```
javac -cp lib/jdbm-1.0.jar InvertedIndex.java
javac -cp .:lib/* Crawler.java
```
run:
```
java -cp lib/*:. Crawler
```
## Indexer
compile:
## Test Program
compile:
## TODO:
```
1. Fetch page title, last modification date, size
2. Recursively fetch the required number of pages using a breadth-first strategy
3. Check duplicate and out-of-date page
```
