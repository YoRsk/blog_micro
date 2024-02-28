FROM openjdk:17

WORKDIR /app
#jar package is built by maven, and the name is service-reg-0.0.1-SNAPSHOT.jar
COPY target/service-reg-0.0.1-SNAPSHOT.jar /app/

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "service-reg-0.0.1-SNAPSHOT.jar"]


