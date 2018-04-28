# COMP4321_2018Spring
Group 1 <br>
DOU, Daihui   (ddou@connect.ust.hk) <br>
TANG, Can-yao   (ctangaf@connect.ust.hk) <br>
YANG, Mingyuan   (myangah@connect.ust.hk) <br>
## File Structure
Replace ROOT on Tomcat by this one. <br>
Put all .class file in WEB-INF/classes <br>
Put all .jar file in WEB-INF/classes <br>
Put .jsp files in pages/ <br>
## Crawler
compile:
```
javac -cp lib/jdbm-1.0.jar InvertedIndex.java
javac -cp .:lib/* Crawler.java
javac -cp .:lib/* Launcher.java
```
run:
```
java -cp lib/*:. Launcher
```
## Data Process(sort and calculate weight)
compile:
```
javac -cp .:lib/* DataProcess.java
```
run:
```
java -cp lib/*:. DataProcess
```
## Search(Interface used by the server)
compile:
```
javac -cp .:lib/* SearchEngine.java
```
run:
```
java -cp lib/*:. SearchEngine
```


## Test Program
compile:
```
javac -cp .:lib/* YmyTest.java
```
run:
```
java -cp lib/*:. YmyTest
```

## TODO:
```
1.Server
2.Phrase search
3.autocomplete
4.pagerank
5.others...

```

## Questions:
```
1. Link is a file?
2. Chinese version?
3. Authentication required?
```
