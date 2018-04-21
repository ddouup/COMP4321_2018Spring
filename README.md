# COMP4321_2018Spring
Group 1 <br>
DOU, Daihui   (ddou@connect.ust.hk) <br>
TANG, Can-yao   (ctangaf@connect.ust.hk) <br>
YANG, Mingyuan   (myangah@connect.ust.hk) <br>
## File Structure
Replace ROOT on Tomcat by this one. <br>
Put all .class file in WEB-INF/classes <br>
Put .jsp files in WEB-INF/content <br>
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
1. Update Key_Docid_index to be -> Key: Word; Content: "Id,tf"
2. Search
3. Server

```

## Questions:
```
1. Link is a file?
2. Chinese version?
3. Authentication required?
```
