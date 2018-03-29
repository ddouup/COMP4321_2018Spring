Group 1
DOU, Daihui   (ddou@connect.ust.hk) 
TANG, Can-yao   (ctangaf@connect.ust.hk) 
YANG, Mingyuan   (myangah@connect.ust.hk)

compile:
javac -cp lib/jdbm-1.0.jar InvertedIndex.java
javac -cp .:lib/* Crawler.java
javac -cp .:lib/* Launcher.java
javac -cp .:lib/* YmyTest.java

run:
java -cp lib/*:. Launcher
java -cp lib/*:. YmyTest

Database:
project.db

