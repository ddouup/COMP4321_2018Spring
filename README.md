# COMP4321_2018Spring
Group 1 <br>
DOU, Daihui   (ddou@connect.ust.hk) <br>
TANG, Can-yao   (ctangaf@connect.ust.hk) <br>
YANG, Mingyuan   (myangah@connect.ust.hk) <br>
## File Structure
Put all .class file in ROOT/WEB-INF/classes <br>
Put all .jar file in ROOT/WEB-INF/lib <br>
Put .jsp files in ROOT/pages/ <br>
Put index.html files in ROOT/ <br>
Put .css files in ROOT/css/ <br>
Put .js files in ROOT/js/ <br>
Put .img files in ROOT/images/ <br>
## Crawler
compile:
```
javac -cp .:lib/* InvertedIndex.java
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
run (query hardcoded in searchEngine.java):
```
java -cp lib/*:. SearchEngine
```

## TODO:
```
1.autocomplete
2.pagerank
3.others...

```
