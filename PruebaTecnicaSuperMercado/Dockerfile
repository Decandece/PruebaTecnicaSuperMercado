# -------------------------------------------------------------------
# ETAPA 1: BUILD (Compilación)
# Usamos JDK completo para tener herramientas de Maven
# -------------------------------------------------------------------
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# 1. Copiamos archivos de dependencias primero (Optimización de caché)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# 2. Copiamos el código fuente y construimos el JAR
COPY src ./src
RUN ./mvnw clean package -DskipTests

# -------------------------------------------------------------------
# ETAPA 2: RUNNER (Ejecución)
# Usamos JRE ligero, solo para correr la app (sin herramientas de build)
# -------------------------------------------------------------------
FROM eclipse-temurin:21-jre

WORKDIR /app

# 3. Traemos solo el JAR compilado de la etapa anterior ("builder")
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]