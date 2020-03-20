FROM hub.c.163.com/library/java:8-alpine
MAINTAINER liuzhi
VOLUME /tmp
COPY ./target/mavenV-1.0-SNAPSHOT.jar  /app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 9000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
