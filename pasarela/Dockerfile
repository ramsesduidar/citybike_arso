FROM maven:3.8.5-jdk-8 AS builder

WORKDIR /app/
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn package

FROM openjdk:8
COPY --from=builder /app/target/pasarela-0.0.1-SNAPSHOT.jar /root.jar
CMD ["java", "-jar", "/root.jar"]

EXPOSE 8090