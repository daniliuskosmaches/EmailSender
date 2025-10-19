# ====== Этап сборки (build) ======
FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app
COPY . .

# Собираем jar (Maven)
RUN ./mvnw clean package -DskipTests

# ====== Этап запуска (runtime) ======
FROM eclipse-temurin:25-jre

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
