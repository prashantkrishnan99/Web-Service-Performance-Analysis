Instructions
==========

1. First build and run the sample project in the `webservice` directory.
2. Next, build and run the client project in the `client` directory.

webservice
==========
* implements a "hello world" web service used for testing only

Build and run
--------------------------------
1. `mvn clean install`
2. `mvn org.apache.tomcat.maven:tomcat7-maven-plugin:run`


client
======
* implements the load-test GUI

How to run GUI for load test
--------------------------------
1. `mvn clean install`
2. `mvn org.apache.tomcat.maven:tomcat7-maven-plugin:run`
3. Go to [http://localhost:8088/client/](http://localhost:8088/client/)

How to run the load test using the command-line:
--------------------------------
* `mvn clean install`
* `mvn exec:java -Dexec.mainClass="edu.csula.cs594.client.CliClient"`
