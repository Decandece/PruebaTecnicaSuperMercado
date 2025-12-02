# -------------------------------------------------------------------
# ETAPA 1: BUILD (Compilación)
# -------------------------------------------------------------------
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copiamos archivos de dependencias primero (Optimización de caché)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw

# Descargamos dependencias (se cachea si pom.xml no cambia)
RUN ./mvnw dependency:go-offline -B

# Copiamos el código fuente y construimos el JAR
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# Verificamos que el JAR se creó correctamente
RUN find /app/target -name "*.jar" -type f

# -------------------------------------------------------------------
# ETAPA 2: RUNNER (Ejecución)
# -------------------------------------------------------------------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Creamos un usuario no-root para mayor seguridad
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Copiamos el JAR compilado
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

# Usamos exec para que Java reciba señales correctamente
ENTRYPOINT ["java", "-jar", "app.jar"]