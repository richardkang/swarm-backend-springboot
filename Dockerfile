FROM openjdk:8-jdk-alpine
VOLUME /tmp
VOLUME /logs
RUN echo "Asia/Taipei" > /etc/timezone

COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]