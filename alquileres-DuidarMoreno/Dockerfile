FROM maven:3.8.5-jdk-8 AS builder

WORKDIR /app/
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn package

FROM tomcat:9-jdk8
WORKDIR /usr/local/tomcat/webapps/
COPY --from=builder /app/target/alquileres-DuidarMoreno-0.0.1-SNAPSHOT.war ROOT.war

EXPOSE 8080
 
CMD ["catalina.sh", "run"]