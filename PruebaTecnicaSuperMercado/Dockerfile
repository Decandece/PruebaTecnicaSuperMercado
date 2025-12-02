FROM eclipse-temurin:21-jdk-alpine
ARG JAR_FILE=target/PruebaTecnicaSuperMercado-0.0.1.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]