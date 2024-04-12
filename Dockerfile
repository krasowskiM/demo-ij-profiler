FROM openjdk:17-jdk-alpine
MAINTAINER Maciek
COPY target/demo-ij-profiler-0.0.1-SNAPSHOT.jar demo-ij-profiler-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/demo-ij-profiler-0.0.1-SNAPSHOT.jar"]