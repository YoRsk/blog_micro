FROM openjdk:17

WORKDIR /app

COPY target/comment-ms-0.0.1-SNAPSHOT.jar /app/

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "comment-ms-0.0.1-SNAPSHOT.jar"]

