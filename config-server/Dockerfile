FROM openjdk:17

WORKDIR /app
#jar package is built by maven, and the name is service-reg-0.0.1-SNAPSHOT.jar
COPY target/config-server-0.0.1-SNAPSHOT.jar /app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "config-server-0.0.1-SNAPSHOT.jar"]