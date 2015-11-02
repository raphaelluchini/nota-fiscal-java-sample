# NotaFiscalJavaSample
REST API using Spark framework sample

##Getting Started OSX/Linux:
```
git clone https://github.com/raphaelluchini/NotaFiscalJavaSample.git
cd NotaFiscalJavaSample
mvn compile && mvn exec:java
```

##Using Eclipse:
```
File > Import > Maven > Existing Maven Projects > (Browse your folder project) > Finish
Run > Run
```

##Using NetBeans:
```
File > Open Project > (Select your project folder)
Run
```

##mySQL
Database connect information are located in com/devsolutions/database/MySQLAdapter.java
Check if they are the same of your computer
```
public static String USERNAME = "root";
public static String PASSWORD = "";
public static String PORT_NUMBER = "3306";
public static String HOST_NAME = "127.0.0.1";
public static String DATABASE_NAME = "notafiscal";
```

Creating mySQL database
```
mysql < notafiscal.sql
```