maven-jetty-hsqldb-mem
======================

Run Jetty 9.0.5 and HSQLDB 2.3.0 in memory from Maven.

How this works and usage
---

In the Maven lifecycle it will compile a .war file and then the exec-maven-plugin will 
run App.java which bootstraps the HSQLDB and Jetty and deploys the .war file to Jetty.
Potentially you can use any type of web framework, servlets, filters etc that works on Jetty.

I have added a simple servlet which renders a JSP file and stubbed a simple database entity. 
You have access to the full power of JPA/EntityManager or you can use EclipseLink/JDBC directly your choice.


Setup
---

* Install Java 7 JDK (http://www.oracle.com/technetwork/java/javase/downloads/index.html).
* Install Maven (download link and installation instructions at the official homepage http://maven.apache.org/download.cgi).
* Open the project in your choice of IDE/Editor.
* Run the project from command line in the projects folder or IDE/Editor with "mvn deploy".
* Browse to [http://localhost:8080](http://localhost:8080).
* Stop the server with CTRL+C in the terminal or by killing the process from your IDE.
