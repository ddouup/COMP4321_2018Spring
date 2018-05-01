# COMP4321_2018Spring
Group 1 <br>
DOU, Daihui   (ddou@connect.ust.hk) <br>
TANG, Can-yao   (ctangaf@connect.ust.hk) <br>
YANG, Mingyuan   (myangah@connect.ust.hk) <br>
## File Structure
Replace ROOT on Tomcat by this one. <br>
Put all .class file in WEB-INF/classes <br>
Put all .jar file in WEB-INF/lib <br>
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
javac -cp .:lib/* Launcher2.java
```
run:
```
java -cp lib/*:. DataProcess
java -cp lib/*:. Launcher2
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
