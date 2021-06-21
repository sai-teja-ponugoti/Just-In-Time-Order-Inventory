Execute the jar file:

    java -jar inventory.jar -f [input csv file name] -o [output csv file name]

    java -jar inventory.jar -f input.csv -o output.csv

Steps to create new/modify Jar file:
    1. javac *.java
    2. Create a new jar file
        jar cfm jarFileName.jar Manifest.txt *.class
    3. Modify existing jar file:
        jar ufm inventory.jar Manifest.txt *.class

