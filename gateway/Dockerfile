FROM openjdk:17

WORKDIR /app
#jar package is built by maven, and the name is service-reg-0.0.1-SNAPSHOT.jar
COPY target/gateway-0.0.1-SNAPSHOT.jar /app/

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "gateway-0.0.1-SNAPSHOT.jar"]
