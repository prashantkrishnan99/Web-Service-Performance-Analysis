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

Web services
--------------------------------

/client/v1/project/
* /client/v1/project/create - http://localhost:8088/client/v1/project/create?uri=http%3a%2f%2flocalhost%3a8080%2fwebservice%2fv1%2fhello&projectname=hello
* /client/v1/project/list - http://localhost:8088/client/v1/project/list?uri=http%3a%2f%2flocalhost%3a8080%2fwebservice%2fv1%2fhello&count=1000

/client/v1/loadtest/
1. /client/v1/loadtest/start - [http://localhost:8088/client/v1/loadtest/start?uri=http%3a%2f%2flocalhost%3a8080%2fwebservice%2fv1%2fhello&count=1000](http://localhost:8088/client/v1/start?uri=http%3a%2f%2flocalhost%3a8080%2fwebservice%2fv1%2fhello&count=1000)
2. /client/v1/loadtest/stop - [http://localhost:8088/client/v1/loadtest/stop?uri=http%3a%2f%2flocalhost%3a8080%2fwebservice%2fv1%2fhello](http://localhost:8088/client/v1/stop?uri=http%3a%2f%2flocalhost%3a8080%2fwebservice%2fv1%2fhello)
3. /client/v1/loadtest/status - [http://localhost:8088/client/v1/loadtest/status?uri=http%3a%2f%2flocalhost%3a8080%2fwebservice%2fv1%2fhello](http://localhost:8088/client/v1/status?uri=http%3a%2f%2flocalhost%3a8080%2fwebservice%2fv1%2fhello)

How to run the load test using the command-line:
--------------------------------
* `mvn clean install`
* `mvn exec:java -Dexec.mainClass="edu.csula.cs594.client.CliClient"`
