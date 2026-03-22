FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY java/pom.xml .
COPY java/mvnw .
COPY java/.mvn .mvn

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY java/src src

RUN ./mvnw clean package -DskipTests -Dfile.encoding=UTF-8

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]